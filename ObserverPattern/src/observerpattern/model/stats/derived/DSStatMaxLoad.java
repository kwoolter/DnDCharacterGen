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
public class DSStatMaxLoad extends DSStatDerived {

    public DSStatMaxLoad() {

        // Record the name of this derived stat
        super("WEIGHT_MAX_LOAD", "MISC");

        // Register the core stats that this derived stats needs
        addCoreStat("ABILITY_STR");
        
    }

    // Called to calculate the current value of this derived stat from its core stats
    @Override
    protected Float calculate() {

        Float fNewValue = 0.0f;

        Float fStrength = mapDependencyStats.get("ABILITY_STR");
        
        fNewValue = fStrength * 10;

        return fNewValue;

    }
}
