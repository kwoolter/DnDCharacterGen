/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observerpattern.model.stats.derived;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import observerpattern.model.stats.framework.*;

/**
 *
 * @author KeithW
 */
public class DSDnDStatContainer {

    private DSStatContainer stats;
    private String sName, sRace, sClass;

    public DSDnDStatContainer(String sName, String sRace, String sClass) {
        this.sName = sName;
        this.sRace = sRace;
        this.sClass = sClass;

        stats = new DSStatContainer(sName + " the " + sClass + " " + sRace);

    }

    public void updateStat(DSStat stat) {

        stats.updateStat(stat);

    }

    public DSStat getStat(String sStatName) {

        return stats.getStatCopy(sStatName);
    }

    public DSStatDetails getStatDetails(String sStatName) {
        return stats.getStatDetails(sStatName);
    }

    public Float getStatValue(String sName) {
        return stats.getStatValue(sName);
    }

    public String getName() {
        return sName;
    }

    public String getRace() {
        return sRace;
    }

    public String getDSClass() {
        return sClass;
    }

    // Assign ability scores randomly using the standard array
    public void rollAbilities() {

        // Create the standard array
        List<Float> listStandardArray = new ArrayList();

        listStandardArray.add(10.0f);
        listStandardArray.add(11.0f);
        listStandardArray.add(12.0f);
        listStandardArray.add(13.0f);
        listStandardArray.add(14.0f);
        listStandardArray.add(16.0f);

        // Shuffle it...
        // Collections.shuffle(listStandardArray, new Random(10));
        Collections.shuffle(listStandardArray);

        // ...and assign it to the ability rolls that are the core stats
        stats.updateStat(new DSStat("ABILITY_STR_ROLL", "ABILITY", listStandardArray.get(0)));
        stats.updateStat(new DSStat("ABILITY_CON_ROLL", "ABILITY", listStandardArray.get(1)));
        stats.updateStat(new DSStat("ABILITY_DEX_ROLL", "ABILITY", listStandardArray.get(2)));
        stats.updateStat(new DSStat("ABILITY_INT_ROLL", "ABILITY", listStandardArray.get(3)));
        stats.updateStat(new DSStat("ABILITY_WIS_ROLL", "ABILITY", listStandardArray.get(4)));
        stats.updateStat(new DSStat("ABILITY_CHA_ROLL", "ABILITY", listStandardArray.get(5)));

    }

    public void print() {

        stats.print();

    }

    public Set<DSStat> getStatsByCategory(String sCategory) {
        return this.stats.getStatsByCategory(sCategory);
    }

    public Set<DSStat> getStatsByOwner(String sOwner) {
        return stats.getStatsByOwner(sOwner);
    }

    public void deleteStatsByOwner(String sOwner) {
        stats.deleteStatsByOwner(sOwner);
    }

    //  Methos to build all of teh core and derived stats for a DnD character
    public void initialise() {

        this.stats.initialise();

        // Load in the reference data for races and classes
        DSStatFactory raceStats = new DSStatFactory("race_stats");
        DSStatFactory classStats = new DSStatFactory("class_stats");

        raceStats.load();
        classStats.load();

        // Load up the Race based Stats
        List<DSStat> listRaceBonuses = raceStats.getStats(sRace);

        for (DSStat entry : listRaceBonuses) {
            stats.addStat(new DSStatCore(entry));
        }

        // Load up the Class based stats
        List<DSStat> lisClassBonuses = classStats.getStats(sClass);

        for (DSStat entry : lisClassBonuses) {
            stats.addStat(new DSStatCore(entry));
        }

        // Ability Stats
        stats.addStat(new DSStatCore("XP", "ABILITY", 0.0f));
        stats.addStat(new DSStatLevel());

        // Randomly roll the ability scores
        rollAbilities();

        //.. and set the ability stats based on these rolls + other bonuses
        for (String ability : DSStatAbility.getAbilities()) {
            stats.addStat(new DSStatAbility(ability));
            stats.addStat(new DSStatModifier(ability));
        }

        // Defense Stats
        stats.addStat(new DSStatDefense("FORT", "STR", "CON"));
        stats.addStat(new DSStatDefense("WILL", "WIS", "CHA"));
        stats.addStat(new DSStatDefenseReflex());
        stats.addStat(new DSStatDefenseAC2());

        // Attacks
        for (String sAbility : DSStatAttack.getsAttackNames()) {
            stats.addStat(new DSStatAttack("BASIC", sAbility));
        }

        // Damage
        for (DSStatDamage.DamageStatType type : DSStatDamage.DamageStatType.values()) {
            stats.addStat(new DSStatDamage("STR", type));
            stats.addStat(new DSStatDamage("DEX", type));
        }

        // Skill stats
        stats.addStat(new DSStatSkill("ACROBATICS", "DEX", true));
        stats.addStat(new DSStatSkill("ARCANA", "INT", false));
        stats.addStat(new DSStatSkill("ATHLETICS", "STR", true));
        stats.addStat(new DSStatSkill("BLUFF", "CHA", false));
        stats.addStat(new DSStatSkill("DIPLOMACY", "CHA", false));
        stats.addStat(new DSStatSkill("DUNGEONEERING", "WIS", false));
        stats.addStat(new DSStatSkill("ENDURANCE", "CON", true));
        stats.addStat(new DSStatSkill("HEAL", "WIS", false));
        stats.addStat(new DSStatSkill("HISTORY", "INT", false));
        stats.addStat(new DSStatSkill("INSIGHT", "WIS", false));
        stats.addStat(new DSStatSkill("INTIMIDATE", "CHA", false));
        stats.addStat(new DSStatSkill("NATURE", "WIS", false));
        stats.addStat(new DSStatSkill("PERCEPTION", "WIS", false));
        stats.addStat(new DSStatSkill("RELIGION", "INT", false));
        stats.addStat(new DSStatSkill("STEALTH", "DEX", true));
        stats.addStat(new DSStatSkill("STREETWISE", "CHA", false));
        stats.addStat(new DSStatSkill("THIEVERY", "DEX", true));

        // Other stats
        stats.addStat(new DSStatInitiative());
        stats.addStat(new DSStatMaxHP());
        stats.addStat(new DSStatCurrentHP());
        stats.addStat(new DSStatCore("CURRENT_DAMAGE", "HIT_POINTS", 15.0f));
        stats.addStat(new DSStatHealingSurgesPerDay());
        stats.addStat(new DSStatHealingSurgeValue());
        stats.addStat(new DSStatSpeed());
        stats.addStat(new DSStatSpeedPenalty());
        stats.addStat(new DSStatMaxLoad());

        // Senses
        stats.addStat(new DSStatSense("INSIGHT"));
        stats.addStat(new DSStatSense("PERCEPTION"));

        // Conditions
        stats.addStat(new DSStatConditionBloodied());
        stats.addStat(new DSStatConditionDying());

        // Rebuild the whole lot to resolve any missing subscriptions
        stats.refresh();

    }

    public void refresh() {
        stats.refresh();
    }
}
