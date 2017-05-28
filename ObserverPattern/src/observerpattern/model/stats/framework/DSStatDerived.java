/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package observerpattern.model.stats.framework;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author U394198
 */
// Abstract class for all types of derived stat
// All concrete classes must implement the calculate() method
//
public abstract class DSStatDerived extends DSStatCore {

    private boolean bDependentsMandatory = true;

    // Abstract method that gets called when it is time to calculate the value of the derived stat
    protected abstract Float calculate();
    // Map that store the core stats and their current values
    protected Map<String, Float> mapDependencyStats = new HashMap();
    // List where the derived stat stores the list of core stats that it is interested in
    protected ArrayList<String> listDependencyStatNames = new ArrayList();

    // Constructor
    public DSStatDerived(String sName) {

        // Initialise the name of this stat
        super(sName, "UNKNOWN");

    }

    public DSStatDerived(String sName, String sCategory) {

        // Initialise the name of this stat
        super(sName, sCategory);

    }

    @Override
    public void setValue(Float fValue) {
        System.err.println(this.getName() + " is a derived stats and you tried to call setValue(" + fValue + ")");
    }

    protected void setDerivedValue(Float fValue) {
        super.setValue(fValue);
    }

    protected boolean isDependencyMandatory() {
        return this.bDependentsMandatory;

    }

    protected void setDependencyMandatory(boolean bMandatory) {
        this.bDependentsMandatory = bMandatory;
    }

    //  Provide the list of core stats that this derived stat is interested in
    @Override
    public ArrayList<String> getDependentStatNames() {
        return listDependencyStatNames;
    }

    // Adds a new core stat to the list of stats this derived stat is based on
    protected void addCoreStat(String sStat) {
        this.listDependencyStatNames.add(sStat);
    }

    //  Method called when one of the dependent stats has changed
    @Override
    public void propertyChange(PropertyChangeEvent event) {

        String sStatChanged = event.getPropertyName();

//        System.out.println(this.getName() + ": Dependent stat changed: " + sStatChanged + " [old -> "
//                + event.getOldValue() + "] | [new -> " + event.getNewValue() + "]");

        // Check to see if we are interested in the stat that we have been notified about...
        if (this.listDependencyStatNames.contains(sStatChanged) == false) {

            System.err.println(this.getName() + " got a notification for a stat we are not interested in: " + event.toString());

        } else {

            // Store the latest value of the core stat
            mapDependencyStats.put(sStatChanged, Float.parseFloat(event.getNewValue().toString()));

            // Calculate the new value of the derived stat and store it
            if (isDependencyMandatory() == false || gotAllData() == true) {

                Float fNewValue = calculate();

                setDerivedValue(fNewValue);

                fNewValue = (fNewValue == null) ? -999.0f : fNewValue;

                // System.out.println("Calculated " + this.getName() + "=(" + this.getValue().toString() + ")");

            } else {
                System.err.println(this.getName() + " has not got all of the dependency data to be calculated");
            }
        }
    }

    // Check to see if we have values loaded for all of teh core stats that we need to calculate our derived stat
    protected boolean gotAllData() {

        boolean bAllGood = true;

        for (int i = 0; i < this.listDependencyStatNames.size(); i++) {
            String key = this.listDependencyStatNames.get(i);
            if (mapDependencyStats.get(key) == null) {
                bAllGood = false;
                System.err.println(this.getName() + " has not got dependent stat " + key + " available.");
            }
        }

        return bAllGood;
    }
}
