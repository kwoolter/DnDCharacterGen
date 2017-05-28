/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package observerpattern.model.stats.framework;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author U394198
 */
public class DSStatCore extends DSStat implements PropertyChangeListener {

    // List to store all iof the listeners on this core stat
    private List<PropertyChangeListener> listener = new ArrayList();

    // Constructors
    public DSStatCore(DSStat stat) {
        super(stat.getName(), stat.getCategory(), stat.getValue(), stat.getOwner());

    }

    public DSStatCore(String sName) {

        // Initialise the name of this stat
        super(sName);
    }

    public DSStatCore(String sName, String sCategory) {
        super(sName, sCategory);
    }

    public DSStatCore(String sName, Float fValue) {
        super(sName, "UNKNOWN", fValue);
    }

    public DSStatCore(String sName, String sCategory, Float fValue) {
        super(sName, sCategory, fValue);
    }

    public DSStatCore(String sName, String sCategory, Float fValue, int iOwner) {
        super(sName, sCategory, fValue, iOwner);
    }

    // A core stats has no dependent stats
    public ArrayList<String> getDependentStatNames() {
        return null;
    }

    public int getNumberOfListeners() {
        return listener.size();
    }

    // For core stats we override the setValue method so that all listeners get notified
    @Override
    public void setValue(Float fValue) {

        // Get the current value of this stat
        Float fOldValue = super.getValue();

        fValue = (fValue == null) ? -999.0f : fValue;

        // If the new value is different...
        if (fOldValue.equals(fValue) == false) {

            // Set the current value to the new value specified
            super.setValue(fValue);

            // Notify all listeners of the changes to this stat
            notifyListeners(this,
                    this.getName(),
                    fOldValue.toString(),
                    getValue().toString());
        }

    }

    // publish stat value to listeners without any changing stat value
    public void touch() {

        //System.out.println(getName() + " touched");
        // Notify all listeners of the changes to this stat
        notifyListeners(this,
                this.getName(),
                getValue().toString(),
                getValue().toString());

    }

    //  Publish the stat change to all listeners
    private void notifyListeners(Object object, String property, String oldValue, String newValue) {

        //  Loop through all listeners and notfiy the change in this stat
        for (PropertyChangeListener name : listener) {
            name.propertyChange(new PropertyChangeEvent(this, property, oldValue, newValue));
        }
    }

    // Add a new listener to this core stat
    public void addChangeListener(PropertyChangeListener newListener) {
        listener.add(newListener);

    }

    // If we go notified of a dependent stat change then this is an error as we have no dependencies
    @Override
    public void propertyChange(PropertyChangeEvent event) {

        String sStatChanged = event.getPropertyName();

        System.err.println("Non-derived stat " + this.getName() + " got notified of Dependent stat changed: " + sStatChanged + " [old -> "
                + event.getOldValue() + "] | [new -> " + event.getNewValue() + "]");
    }

    public void clearListeners() {
        this.listener.clear();
    }
}
