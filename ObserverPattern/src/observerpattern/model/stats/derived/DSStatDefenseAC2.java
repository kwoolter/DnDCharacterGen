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
public class DSStatDefenseAC2 extends DSStatDefense {

    private String sDefenseName = "AC";

    public DSStatDefenseAC2() {

        // Record the name of this derived stat
        super("AC", "DEX", "INT");

        // Not all dependency stats are mandatory
        setDependencyMandatory(false);

        // Register all of the core stats that this derived stats needs
        addCoreStat("ITEM_ARMOR_WEIGHT");
        addCoreStat("ITEM_ARMOR_CLASS");
        addCoreStat("ITEM_SHIELD_ARMOR_CLASS");
        addCoreStat("PROFICIENCY_SHIELD");
        addCoreStat("PROFICIENCY_ARMOR");

    }

    // Called to calculate the current value of this derived stat from its core stats
    @Override
    protected Float calculate() {

        Float fNewValue = 0.0f;

        Float fArmorWeight = mapDependencyStats.get("ITEM_ARMOR_WEIGHT");
        Float fArmor = mapDependencyStats.get("ITEM_ARMOR_CLASS");
        Float fArmorProficiency = mapDependencyStats.get("PROFICIENCY_ARMOR");
        Float fShield = mapDependencyStats.get("ITEM_SHIELD_ARMOR_CLASS");
        Float fShieldProficiency = mapDependencyStats.get("PROFICIENCY_SHIELD");
        Float fDexModifier = mapDependencyStats.get("ABILITY_DEX_MODIFIER");
        Float fIntModifier = mapDependencyStats.get("ABILITY_INT_MODIFIER");
        Float fLevel = mapDependencyStats.get("LVL");
        Float fRaceBonus = mapDependencyStats.get("DEFENSE_" + sDefenseName + "CLASS_BONUS");
        Float fClassBonus = mapDependencyStats.get("DEFENSE_" + sDefenseName + "RACE_BONUS");

        // Default the optional parameters
        fArmorWeight = (fArmorWeight == null) ? 0.0f : fArmorWeight;
        fArmor = (fArmor == null) ? 0.0f : fArmor;
        fArmorProficiency = (fArmorProficiency == null) ? 0.0f : fArmorProficiency;
        fShield = (fShield == null) ? 0.0f : fShield;
        fShieldProficiency = (fShieldProficiency == null) ? 0.0f : fShieldProficiency;
        fDexModifier = (fDexModifier == null) ? 0.0f : fDexModifier;
        fIntModifier = (fIntModifier == null) ? 0.0f : fIntModifier;
        fRaceBonus = (fRaceBonus == null) ? 0.0f : fRaceBonus;
        fClassBonus = (fClassBonus == null) ? 0.0f : fClassBonus;

        if (fLevel != null) {

            // Start of with 10 + Level/2 + race bonus + class bonus + armor (if proficient) + shield bonus (if proficient)
            fNewValue = 10 + (float) Math.floor((fLevel / 2.0f)) + fRaceBonus + fClassBonus + (fArmor * fArmorProficiency) + (fShield * fShieldProficiency);

            // If you are wearing light armor or no armor then also add the better of DEX and INT modifiers
            if (fArmorWeight <= 25.0f) {
                fNewValue += Math.max(fDexModifier, fIntModifier);
            }

        } else {

            fNewValue = null;

        }

        return fNewValue;

    }
}
