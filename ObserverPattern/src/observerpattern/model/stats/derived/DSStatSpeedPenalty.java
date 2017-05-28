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
public class DSStatSpeedPenalty extends DSStatDerived {

    public DSStatSpeedPenalty() {

        // Record the name of this derived stat
        super("SPEED_PENALTY", "SPEED");

        // Not all dependency stats are mandatory
        setDependencyMandatory(false);

        // Register the core stats that this derived stats needs
        addCoreStat("ITEM_ARMOR_SPEED_PENALTY");
        addCoreStat("POWER_ENCUMBERED_SPEED");

    }

    // Called to calculate the current value of this derived stat from its core stats
    @Override
    protected Float calculate() {

        Float fNewValue = 0.0f;

        Float fArmorSpeedPenalty = mapDependencyStats.get("ITEM_ARMOR_SPEED_PENALTY");
        Float fRaceEncumberSpeed = mapDependencyStats.get("POWER_ENCUMBERED_SPEED");

        fArmorSpeedPenalty = (fArmorSpeedPenalty == null) ? 0.0f : fArmorSpeedPenalty;
        fRaceEncumberSpeed = (fRaceEncumberSpeed == null) ? 0.0f : fRaceEncumberSpeed;

        //  If their is an armor speed penalYy and you don't have a power to ovecome this...
        if (fArmorSpeedPenalty != 0 && fRaceEncumberSpeed == 0) {
            // add the armor penalty
            fNewValue += fArmorSpeedPenalty;
        }

        return fNewValue;

    }
}
