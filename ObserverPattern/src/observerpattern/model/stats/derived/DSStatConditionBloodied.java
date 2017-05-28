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
public class DSStatConditionBloodied extends DSStatDerived {

    public DSStatConditionBloodied() {

        // Record the name of this derived stat
        super("CONDITION_BLOODIED", "CONDITIONS");

        // Register the core stats that this derived stats needs
        addCoreStat("MAX_HP");
        addCoreStat("CURRENT_HP");


    }

    // Called to calculate the current value of this derived stat from its core stats
    @Override
    protected Float calculate() {

        Float fNewValue = 0.0f;

        Float fMaxHP = mapDependencyStats.get("MAX_HP");
        Float fCurrentHP = mapDependencyStats.get("CURRENT_HP");

        if (fCurrentHP > 0.0f && fCurrentHP <= fMaxHP / 2.0f) {
            fNewValue = 1.0f;
        }

        return fNewValue;

    }
}
