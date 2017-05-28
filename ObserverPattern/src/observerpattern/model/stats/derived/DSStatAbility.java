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
public class DSStatAbility extends DSStatDerived {

    private String sBaseAbility;
    private static String[] abilities = {"STR", "CON", "DEX", "INT", "WIS", "CHA" };

    public DSStatAbility(String sAbility) {

        // Record the name of this derived stat
        super("ABILITY_" + sAbility, "ABILITY");

        this.sBaseAbility = sAbility;

        // Not all dependency stats are mandatory
        setDependencyMandatory(false);

        // Register the core stats that this derived stats needs
        addCoreStat("ABILITY_" + sAbility + "_ROLL");
        addCoreStat("ABILITY_" + sAbility + "_RACE_BONUS");

    }

    public static String[] getAbilities() {
        return abilities;
    }
    
    

    // Called to calculate the current value of this derived stat from its core stats
    @Override
    protected Float calculate() {

        Float fNewValue = 0.0f;

        Float fAbilityRoll = mapDependencyStats.get("ABILITY_" + this.sBaseAbility + "_ROLL");
        Float fRaceBonus = mapDependencyStats.get("ABILITY_" + this.sBaseAbility + "_RACE_BONUS");

        // Default the optional parameters
        fRaceBonus = (fRaceBonus == null) ? 0.0f : fRaceBonus;

        if (fAbilityRoll != null) {

            fNewValue = fAbilityRoll + fRaceBonus;
            
        } else {

            fNewValue = null;

        }

        return fNewValue;

    }
}
