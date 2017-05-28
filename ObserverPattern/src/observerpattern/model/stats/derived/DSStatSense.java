/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package observerpattern.model.stats.derived;

import observerpattern.model.stats.framework.DSStatDerived;

/**
 *
 * @author U394198
 */
public class DSStatSense extends DSStatDerived {

    private String sSenseName, sBaseSkillStat;
    
    private static String[] senseNames = { "INSIGHT", "PERCEPTION" };

    public DSStatSense(String sSense) {

        // Record the name of this derived stat
        super("SENSE_" + sSense, "SENSE");

        this.sSenseName = sSense;
        this.sBaseSkillStat = "SKILL_" + sSense;

        // Register the core stats that this derived stats needs
        addCoreStat(this.sBaseSkillStat);

    }

    public static String[] getSenseNames() {
        return senseNames;
    }
    
    

    // Called to calculate the current value of this derived stat from its core stats
    @Override
    protected Float calculate() {

        Float fNewValue = 0.0f;

        Float fSkill = mapDependencyStats.get(this.sBaseSkillStat);
        
        fNewValue = 10.0f + fSkill;

        return fNewValue;

    }
}
