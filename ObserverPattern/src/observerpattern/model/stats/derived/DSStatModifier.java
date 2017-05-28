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
public class DSStatModifier extends DSStatDerived {

    private String sBaseStatName;

    public DSStatModifier(String sBaseStatName) {

        // Record the name of this derived stat
        super("ABILITY_" + sBaseStatName + "_MODIFIER", "ABILITY_MODIFIER");

        this.sBaseStatName = "ABILITY_" + sBaseStatName;

        // Register the core stats that this derived stats needs
        addCoreStat(this.sBaseStatName);

    }

    // Called to calculate the current value of this derived stat from its core stats
    @Override
    protected Float calculate() {

        Float fNewValue = 0.0f;

        Float fBaseStatValue = mapDependencyStats.get(this.sBaseStatName);

        fNewValue = (float) Math.floor((fBaseStatValue - 10.0f)/2.0f);

        this.setDerivedValue(fNewValue);

        return fNewValue;
    }
}
