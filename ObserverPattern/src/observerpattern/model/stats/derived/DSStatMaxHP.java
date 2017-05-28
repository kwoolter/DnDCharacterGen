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
public class DSStatMaxHP extends DSStatDerived {

    public DSStatMaxHP() {

        // Record the name of this derived stat
        super("MAX_HP", "HIT_POINTS");

        // Register the core stats that this derived stats needs
        addCoreStat("LVL");
        addCoreStat("ABILITY_CON");
        addCoreStat("CLASS_LVL1_HP");
        addCoreStat("CLASS_HP_PER_LVL");


    }

    // Called to calculate the current value of this derived stat from its core stats
    @Override
    protected Float calculate() {

        Float fNewValue = 0.0f;

        Float fConstitution = mapDependencyStats.get("ABILITY_CON");
        Float fLevel = mapDependencyStats.get("LVL");
        Float fLevel1HP = mapDependencyStats.get("CLASS_LVL1_HP");
        Float fHPPerLevel = mapDependencyStats.get("CLASS_HP_PER_LVL");

        fNewValue = fConstitution + fLevel1HP + ((fLevel-1) * fHPPerLevel);

        return fNewValue;



    }
}
