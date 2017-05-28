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
public class DSStatConditionDying extends DSStatDerived {

    public DSStatConditionDying() {

        // Record the name of this derived stat
        super("CONDITION_DYING", "CONDITIONS");

        // Register the core stats that this derived stats needs
        addCoreStat("CURRENT_HP");


    }

    // Called to calculate the current value of this derived stat from its core stats
    @Override
    protected Float calculate() {

        Float fNewValue = 0.0f;

        Float fCurrentHP = mapDependencyStats.get("CURRENT_HP");

        if (fCurrentHP <= 0.0f) {
            fNewValue = 1.0f;
        }

        return fNewValue;

    }
}
