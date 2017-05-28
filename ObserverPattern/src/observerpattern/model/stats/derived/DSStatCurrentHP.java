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
public class DSStatCurrentHP extends DSStatDerived {

    public DSStatCurrentHP() {

        // Record the name of this derived stat
        super("CURRENT_HP", "HIT_POINTS");

        // Not all dependency stats are mandatory
        setDependencyMandatory(false);

        // Register the core stats that this derived stats needs
        addCoreStat("MAX_HP");
        addCoreStat("CURRENT_DAMAGE");


    }

    // Called to calculate the current value of this derived stat from its core stats
    @Override
    protected Float calculate() {

        Float fNewValue = 0.0f;

        Float fMaxHP = mapDependencyStats.get("MAX_HP");
        Float fCurrentDamage = mapDependencyStats.get("CURRENT_DAMAGE");

        fCurrentDamage = fCurrentDamage == null ? 0.0f : fCurrentDamage;

        if (fMaxHP != null) {
            fNewValue = fMaxHP - fCurrentDamage;
        }

        return fNewValue;

    }
}
