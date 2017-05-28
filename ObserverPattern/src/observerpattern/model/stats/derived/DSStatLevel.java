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
public class DSStatLevel extends DSStatDerived {

    // How much XP you need to obtain a level
    private static final int[] iXPLevels = {0, 1000, 2250, 3750, 5500,
        7500, 10000, 13000, 16500, 20500,
        26000, 32000, 39000, 47000, 57000,
        69000, 83000, 99000, 119000, 143000};

    public DSStatLevel() {

        // Record the name of this derived stat
        super("LVL", "MISC");

        // Register the core stats that this derived stats needs
        addCoreStat("XP");

    }

    public static int[] getXPLevels() {
        return iXPLevels;
    }

    public static int getXPAtLevel(int iLevel) {

        return iXPLevels[iLevel - 1];

    }

    // Called to calculate the current value of this derived stat from its core stats
    @Override
    protected Float calculate() {

        Float fXP = mapDependencyStats.get("XP");

        int iLevel = 0;

        // Loop through the level tiers until your XP is no longer greater than the tier
        while (iLevel < iXPLevels.length && fXP.intValue() >= iXPLevels[iLevel]) {
            iLevel++;
        }

        Float fNewValue = (float) iLevel;

        return fNewValue;

    }
}
