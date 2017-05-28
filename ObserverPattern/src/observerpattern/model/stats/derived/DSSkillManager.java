/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observerpattern.model.stats.derived;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import observerpattern.model.stats.framework.DSStat;

/**
 *
 * @author KeithW
 */
public class DSSkillManager {

    private DSDnDStatContainer stats;
    private Set<DSStat> trained, available, options;
    private Map<String, DSStat> currentState;
    private int iMaxSkills;

    public DSSkillManager() {
        trained = new HashSet();
        available = new HashSet();
        options = new HashSet();
        currentState = new HashMap();

    }

    public void setStats(DSDnDStatContainer stats) {
        this.stats = stats;
    }

    public int getMaxSkills() {
        return iMaxSkills;
    }

    public DSStat getSkill(String sSkillName) {

        DSStat selected = this.stats.getStat("SKILL_" + sSkillName + "_TRAINED");

        if (selected == null) {
            selected = new DSStat("SKILL_" + sSkillName + "_TRAINED", "SKILL", 0.0f);
        }

        return selected;

    }

    public boolean isTrained(String sSkillName) {

        boolean bTrained = true;

        DSStat selected = this.stats.getStat("SKILL_" + sSkillName + "_TRAINED");

        if (selected == null || selected.getValue() == 0.0f) {
            bTrained = false;
        }

        return bTrained;

    }

    // Rule to see if uot are allowed to train in a specified skill
    public boolean isTrainable(String sSkillName) throws DSException {

        boolean bTrainable = false;

        if (this.stats == null) {
            throw new DSException("No stats loaded to train in " + sSkillName);
        }

        // Ascertain how many skills we CAN train in and how many we are currently trained in
        int iCurrentTrained, iMaxTrainable;
        DSStat skillsMax = this.stats.getStat("CLASS_SKILLS_AT_LVL1");
        iMaxTrainable = skillsMax.getValue().intValue();
        iCurrentTrained = getNumberOfTrainedSkills();

        // Ascertain are we already trained, is the skill available and if this is a special choice skill
        DSStat skillTrained = this.stats.getStat("SKILL_" + sSkillName + "_TRAINED");
        DSStat skillAvailable = this.stats.getStat("SKILL_" + sSkillName + "_AVAILABLE");
        DSStat skillChoice = this.stats.getStat("SKILL_" + sSkillName + "_CHOICE");

        boolean bTrained, bAvailable, bChoice;
        bTrained = (skillTrained == null) ? false : skillTrained.getValue() > 0.0f;
        bAvailable = (skillAvailable == null) ? false : skillAvailable.getValue() > 0.0f;
        bChoice = (skillChoice == null) ? false : skillChoice.getValue() > 0.0f;

        // OK, now for the rules....
        // Preliminary checks; free slots, we have not already trained in it and this skill available to train in
        if (iCurrentTrained < iMaxTrainable
                && bTrained == false
                && (bAvailable == true || bChoice == true)) {

            //  If you have already made your choice skill
            if (isOptionChosen() == true) {
                // So you can train as long as the requested skill is not also a choice skill
                if (bChoice == false) {
                    bTrainable = true;
                }
            } // ...you haven't made you choice yet so you can only train if this is one of your choices
            // OR you will still have enough slots to make your choice
            else if (bChoice == true || iCurrentTrained + 1 < iMaxTrainable) {
                bTrainable = true;
            }

        }

        return bTrainable;
    }

    // Rules to determine if you are allowed to untrain a skill
    public boolean isUntrainable(String sSkillName) throws DSException {

        boolean bUntrainable = false;

        if (this.stats == null) {
            throw new DSException("No stats loaded to train in " + sSkillName);
        }

        DSStat skillClassTrained = this.stats.getStat("SKILL_" + sSkillName + "_CLASS_TRAINED");

        // You can only untrain this skill if it is NOT a class skill
        if (skillClassTrained == null || skillClassTrained.getValue() == 0.0f) {

            bUntrainable = true;

        }

        return bUntrainable;
    }
    
    
    // Method to determine if a specified skill is a choice skill
    public boolean isChoosable(String sSkillName) throws DSException {

        boolean bChoosable = true;
        
        if (this.stats == null) {
            throw new DSException("No stats loaded to train in " + sSkillName);
        }

        DSStat skillClassTrained = this.stats.getStat("SKILL_" + sSkillName + "_CHOICE");

        if (skillClassTrained == null || skillClassTrained.getValue() == 0.0f) {

            bChoosable = false;

        }
        
        return bChoosable;
    }

    public int getNumberOfTrainedSkills() {

        int iCurrentTrained = 0;
        for (String skill : DSStatSkill.getSkillNames()) {

            DSStat skillTrained = this.stats.getStat("SKILL_" + skill + "_TRAINED");

            if (skillTrained != null && skillTrained.getValue() > 0.0f) {
                iCurrentTrained++;
            }
        }

        return iCurrentTrained;

    }

    // Method called when you want to train in a specified skill
    public void train(String sNewSkill) throws DSException {

        if (this.stats == null) {
            throw new DSException("No stats loaded to train in " + sNewSkill);
        }

        //  Check that we are allowed to train in this skill...
        if (isTrainable(sNewSkill) == true) {
            
            // If we are then get tha skill training stat...
            DSStat skillTrained = this.stats.getStat("SKILL_" + sNewSkill + "_TRAINED");
            
            // Create a stat to record the training if we hadn't got one already..
            if (skillTrained == null) {
                skillTrained = new DSStat("SKILL_" + sNewSkill + "_TRAINED", "SKILL",5.0f);
            }

            // Set training value to +5 then update.
            skillTrained.setValue(5.0f);
            this.stats.updateStat(skillTrained);
        }
    }

    // Method called when you want to unrain a skill i.e. forget you trained in it
    public void untrain(String sNewSkill) throws DSException {

        if (this.stats == null) {
            throw new DSException("No stats loaded to train in " + sNewSkill);
        }

        // If you are allowed to untrain this skill...
        if (isUntrainable(sNewSkill) == true) {
            
            // Get the skill training stat
            DSStat skillTrained = this.stats.getStat("SKILL_" + sNewSkill + "_TRAINED");
            
            // Create a stat to record the training if we hadn't got one already..
            if (skillTrained == null) {
                skillTrained = new DSStat("SKILL_" + sNewSkill + "_TRAINED", "SKILL");
            }

            // Set training value to 0 then update.
            skillTrained.setValue(0.0f);
            this.stats.updateStat(skillTrained);
        }

    }

    // Where a character has to pick between two skills, have they already made that choice?
    private boolean isOptionChosen() {

        // Assume the choice has not yet been made
        boolean bChosen = false;

        // Loop through all of the available skills
        for (String sSkillName : DSStatSkill.getSkillNames()) {
            
            // Get the stats for the selected skill
            DSStat skillTrained = this.stats.getStat("SKILL_" + sSkillName + "_TRAINED");
            DSStat skillChoice = this.stats.getStat("SKILL_" + sSkillName + "_CHOICE");

            boolean bTrained, bChoice;
            
            // set the booleans based on the value of the stats
            bTrained = (skillTrained == null) ? false : skillTrained.getValue() > 0.0f;
            bChoice = (skillChoice == null) ? false : skillChoice.getValue() > 0.0f;

            // If the selected skill is a choice skill and you are trained in it the the choise is made
            if (bChoice = true && bTrained == true) {
                bChosen = true;
            }

        }

        return bChosen;

    }

    // Load up all of the data from the stats from the container
    // ...then build up the current state from these stats.
    public void initialise() {

        if (this.stats == null) {
            return;
        }

        DSStat skillsMax = this.stats.getStat("CLASS_SKILLS_AT_LVL1");
        this.iMaxSkills = skillsMax.getValue().intValue();

        for (String skill : DSStatSkill.getSkillNames()) {

            DSStat skillTrained = this.stats.getStat("SKILL_" + skill + "_TRAINED");
            DSStat skillAvailable = this.stats.getStat("SKILL_" + skill + "_AVAILABLE");
            DSStat skillChoice = this.stats.getStat("SKILL_" + skill + "_CHOICE");

            if (skillTrained != null && skillTrained.getValue() > 0.0f) {
                this.trained.add(skillTrained);
            }

            if (skillAvailable != null && skillAvailable.getValue() > 0.0f) {
                this.available.add(skillAvailable);
            }

            if (skillChoice != null && skillChoice.getValue() > 0.0f) {
                this.options.add(skillChoice);
            }

        }

        // Firstly set all of the stats to be "unavailable"
        for (String sStatName : DSStatSkill.getSkillNames()) {
            DSStat stat = new DSStat(sStatName, "UNKNOWN", 0.0f);
            this.currentState.put(sStatName, stat);
        }

        // Now go through the trained stats and set the state
        for (DSStat stat : this.trained) {
            DSStat selected = this.currentState.get(stat.getName());
            if (selected != null) {
                selected.setValue(1.0f);
            }

        }

        // Now go through the available stats and set the state
        for (DSStat stat : this.available) {
            DSStat selected = this.currentState.get(stat.getName());
            if (selected != null) {
                selected.setValue(2.0f);
            }

        }

    }

    public void print() {

        System.out.println("Trained Skills (Max=" + this.iMaxSkills + ")");

        for (String sSkillName : DSStatSkill.getSkillNames()) {
            DSStat skillTrained = this.stats.getStat("SKILL_" + sSkillName + "_TRAINED");
            if (skillTrained != null && skillTrained.getValue() > 0.0f) {
                System.out.println(sSkillName);

            }
        }

    }
}
