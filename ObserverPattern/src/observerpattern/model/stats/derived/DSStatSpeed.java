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
public class DSStatSpeed extends DSStatDerived {

    public DSStatSpeed() {

        // Record the name of this derived stat
        super("SPEED", "SPEED");

        // Not all dependency stats are mandatory
        setDependencyMandatory(false);

        // Register the core stats that this derived stats needs
        addCoreStat("SPEED_RACE_BASE");
        addCoreStat("SPEED_PENALTY");

    }

    // Called to calculate the current value of this derived stat from its core stats
    @Override
    protected Float calculate() {

        Float fNewValue = 0.0f;

        Float fRaceSpeed = mapDependencyStats.get("SPEED_RACE_BASE");
        Float fSpeedPenalty = mapDependencyStats.get("SPEED_PENALTY");

        fSpeedPenalty = (fSpeedPenalty == null) ? 0.0f : fSpeedPenalty;

        if (fRaceSpeed != null) {
            fNewValue = fRaceSpeed + fSpeedPenalty;

        }

        return fNewValue;

    }
}
