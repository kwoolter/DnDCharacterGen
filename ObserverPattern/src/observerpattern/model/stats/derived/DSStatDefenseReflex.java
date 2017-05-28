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
public class DSStatDefenseReflex extends DSStatDefense {

    private String sDefenseName = "REF";

    public DSStatDefenseReflex() {

        // Record the name of this derived stat
        super("REF", "DEX", "INT");

        // Not all dependency stats are mandatory
        setDependencyMandatory(false);

        // Register all of the core stats that this derived stats needs
        addCoreStat("ITEM_SHIELD_ARMOR_CLASS");
        addCoreStat("PROFICIENCY_SHIELD");

    }

    // Called to calculate the current value of this derived stat from its core stats
    @Override
    protected Float calculate() {

        Float fNewValue = 0.0f;

        Float fShield = mapDependencyStats.get("ITEM_SHIELD_ARMOR_CLASS");
        Float fShieldProficiency = mapDependencyStats.get("PROFICIENCY_SHIELD");
        Float fDexModifier = mapDependencyStats.get("ABILITY_DEX_MODIFIER");
        Float fIntModifier = mapDependencyStats.get("ABILITY_INT_MODIFIER");
        Float fLevel = mapDependencyStats.get("LVL");
        Float fRaceBonus = mapDependencyStats.get("DEFENSE_" + sDefenseName + "CLASS_BONUS");
        Float fClassBonus = mapDependencyStats.get("DEFENSE_" + sDefenseName + "RACE_BONUS");

        // Default the optional parameters
        fShield = (fShield == null) ? 0.0f : fShield;
        fShieldProficiency = (fShieldProficiency == null) ? 0.0f : fShieldProficiency;
        fDexModifier = (fDexModifier == null) ? 0.0f : fDexModifier;
        fIntModifier = (fIntModifier == null) ? 0.0f : fIntModifier;
        fRaceBonus = (fRaceBonus == null) ? 0.0f : fRaceBonus;
        fClassBonus = (fClassBonus == null) ? 0.0f : fClassBonus;

        if (fLevel != null) {

            // Start of with 10 + Level/2 + Max(Int/Dex modifier) and race and/or class bonuses + shield bonus (if proficient)
            fNewValue = 10 + (float) Math.floor((fLevel / 2.0f)) + Math.max(fDexModifier, fIntModifier) + fRaceBonus + fClassBonus + (fShield * fShieldProficiency);

        } else {

            fNewValue = null;

        }

        return fNewValue;

    }
}
