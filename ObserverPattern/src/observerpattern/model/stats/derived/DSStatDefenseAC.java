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
public class DSStatDefenseAC extends DSStatDerived {

    private String sDefenseName = "AC";

    public DSStatDefenseAC() {

        // Record the name of this derived stat
        super("AC", "DEFENSE");

        // Not all dependency stats are mandatory
        setDependencyMandatory(false);

        // Register all of the core stats that this derived stats needs
        addCoreStat("ABILITY_DEX_MODIFIER");
        addCoreStat("ABILITY_INT_MODIFIER");
        addCoreStat("LVL");
        addCoreStat("ITEM_ARMOR_CLASS");
        addCoreStat("ITEM_SHIELD_ARMOR_CLASS");
        addCoreStat("DEFENSE_"+sDefenseName + "CLASS_BONUS");
        addCoreStat("DEFENSE_"+sDefenseName + "RACE_BONUS");
        

    }

    // Called to calculate the current value of this derived stat from its core stats
    @Override
    protected Float calculate() {

        Float fNewValue = 0.0f;

        Float fArmor = mapDependencyStats.get("ITEM_ARMOR_CLASS");
        Float fShield = mapDependencyStats.get("ITEM_SHIELD_ARMOR_CLASS");
        Float fDexModifier = mapDependencyStats.get("ABILITY_DEX_MODIFIER");
        Float fIntModifier = mapDependencyStats.get("ABILITY_INT_MODIFIER");
        Float fLevel = mapDependencyStats.get("LVL");
        Float fRaceBonus = mapDependencyStats.get("DEFENSE_"+sDefenseName + "CLASS_BONUS");
        Float fClassBonus = mapDependencyStats.get("DEFENSE_"+sDefenseName + "RACE_BONUS");

        // Default the optional parameters
        fArmor = (fArmor == null) ? 0.0f : fArmor;
        fShield = (fShield == null) ? 0.0f : fShield;
        fDexModifier = (fDexModifier == null) ? 0.0f : fDexModifier;
        fIntModifier = (fIntModifier == null) ? 0.0f : fIntModifier;
        fRaceBonus = (fRaceBonus == null) ? 0.0f : fRaceBonus;
        fClassBonus = (fClassBonus == null) ? 0.0f : fClassBonus;

        if (fLevel != null) {

            // Start of with 10 + Level/2 + and race or class bonuses
            fNewValue = 10 + (float) Math.floor((fLevel / 2.0f)) + fRaceBonus + fClassBonus;

            // Ig you are not wearing any armor then add the better of DEX and INT modifiers
            if (fArmor.equals(0.0f)) {
                fNewValue += (fDexModifier > fIntModifier) ? fDexModifier : fIntModifier;
            } // Else add the Armor class for the armor that you are wearing plus a shield bonus if equipped
            else {
                fNewValue += fArmor + fShield;
            }

        } else {

            fNewValue = null;

        }

        return fNewValue;

    }
}
