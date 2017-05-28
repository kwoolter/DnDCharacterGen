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
public class DSStatDefense extends DSStatDerived {

    private String sBaseStatName1, sBaseStatName2, sRaceBonusStatName, sClassBonusStatName;

    public DSStatDefense(String sDefenseName, String sBaseStatName1, String sBaseStatName2) {

        // Record the name of this derived stat
        super(sDefenseName, "DEFENSE");

        // Not all dependency stats are mandatory
        setDependencyMandatory(false);

        // Store the ability modifiers that will be used in the calc
        this.sBaseStatName1 = "ABILITY_" + sBaseStatName1 + "_MODIFIER";
        this.sBaseStatName2 = "ABILITY_" + sBaseStatName2 + "_MODIFIER";

        // Store the bonuses that might come into play
        this.sClassBonusStatName = "DEFENSE_" + sDefenseName + "_CLASS_BONUS";
        this.sRaceBonusStatName = "DEFENSE_" + sDefenseName + "_RACE_BONUS";

        // Register all of the core stats that this derived stats needs
        addCoreStat(this.sBaseStatName1);
        addCoreStat(this.sBaseStatName2);
        addCoreStat("LVL");
        addCoreStat(sClassBonusStatName);
        addCoreStat(sRaceBonusStatName);

    }

    // Called to calculate the current value of this derived stat from its core stats
    @Override
    protected Float calculate() {

        Float fNewValue = 0.0f;

        Float fBaseStatValue1 = mapDependencyStats.get(this.sBaseStatName1);
        Float fBaseStatValue2 = mapDependencyStats.get(this.sBaseStatName2);
        Float fLevel = mapDependencyStats.get("LVL");
        Float fRaceBonus = mapDependencyStats.get(this.sRaceBonusStatName);
        Float fClassBonus = mapDependencyStats.get(this.sClassBonusStatName);

        // Default the optional parameters
        fRaceBonus = (fRaceBonus == null) ? 0.0f : fRaceBonus;
        fClassBonus = (fClassBonus == null) ? 0.0f : fClassBonus;

        if (fBaseStatValue1 != null && fBaseStatValue2 != null && fLevel != null) {

            fNewValue = 10 + (float) Math.floor((fLevel / 2.0f)) + Math.max(fBaseStatValue1, fBaseStatValue2) + fRaceBonus + fClassBonus;
            
        } else {
            
            fNewValue = null;
            
        }

        return fNewValue;

    }
}
