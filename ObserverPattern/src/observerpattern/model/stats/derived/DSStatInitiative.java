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
public class DSStatInitiative extends DSStatDerived {

    public DSStatInitiative() {

        // Record the name of this derived stat
        super("INITIATIVE", "INITIATIVE");

        // Register the core stats that this derived stats needs
        addCoreStat("LVL");
        addCoreStat("ABILITY_DEX_MODIFIER");
        
    }

    // Called to calculate the current value of this derived stat from its core stats
    @Override
    protected Float calculate() {

        Float fNewValue = 0.0f;

        Float fDexModifier = mapDependencyStats.get("ABILITY_DEX_MODIFIER");
        Float fLevel = mapDependencyStats.get("LVL");

        fNewValue = fDexModifier + (float) Math.floor(fLevel / 2.0f);

        return fNewValue;

    }
}
