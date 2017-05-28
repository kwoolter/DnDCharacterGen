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
public class DSStatHealingSurgeValue extends DSStatDerived {

    public DSStatHealingSurgeValue() {

        // Record the name of this derived stat
        super("HEALING_SURGE_VALUE", "HIT_POINTS");

        // Not all dependency stats are mandatory
        setDependencyMandatory(false);

        // Register the core stats that this derived stats needs
        addCoreStat("ABILITY_CON_MODIFIER");
        addCoreStat("RACE_HEALING_SURGE_BONUS");
        addCoreStat("MAX_HP");


    }

    // Called to calculate the current value of this derived stat from its core stats
    @Override
    protected Float calculate() {

        Float fNewValue = 0.0f;

        Float fMaxHP = mapDependencyStats.get("MAX_HP");
        Float fHealingSurgeBonus = mapDependencyStats.get("RACE_HEALING_SURGE_BONUS");
        Float fConstitutionModifier = mapDependencyStats.get("ABILITY_CON_MODIFIER");

        if (fMaxHP != null && fConstitutionModifier != null) {
            fNewValue = fMaxHP / 4;

            if (fHealingSurgeBonus != null && fHealingSurgeBonus > 0.0f) {
                fNewValue += fConstitutionModifier;
            }
        }

        return fNewValue;

    }
}
