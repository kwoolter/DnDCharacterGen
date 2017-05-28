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
public class DSStatHealingSurgesPerDay extends DSStatDerived {

    public DSStatHealingSurgesPerDay() {

        // Record the name of this derived stat
        super("HEALING_SURGES_PER_DAY", "HIT_POINTS");

        // Register the core stats that this derived stats needs
        addCoreStat("ABILITY_CON_MODIFIER");
        addCoreStat("CLASS_HEALING_SURGES_PER_DAY");
        

    }

    // Called to calculate the current value of this derived stat from its core stats
    @Override
    protected Float calculate() {

        Float fNewValue = 0.0f;

        Float fModifier = mapDependencyStats.get("ABILITY_CON_MODIFIER");
        Float fHealingSurgesPerDay = mapDependencyStats.get("CLASS_HEALING_SURGES_PER_DAY");
        
        fNewValue = fModifier + fHealingSurgesPerDay;

        return fNewValue;

    }
}
