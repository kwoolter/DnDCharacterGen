/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package observerpattern.model.stats.framework;

import java.util.Objects;

/**
 *
 * @author U394198
 */
// Abstract base class fro all types of stat
public class DSStat implements Comparable<DSStat> {

    private String sName, sCategory;
    private Float fValue;
    private int iOwner;

    public DSStat(String sName, String sCategory, Float fValue, int iOwner) {
        this.sName = sName;
        this.sCategory = sCategory;
        this.fValue = fValue;
        this.iOwner = iOwner;
    }

    public DSStat(String sName, String sCategory, Float fValue) {
        this.sName = sName;
        this.sCategory = sCategory;
        this.fValue = fValue;
        this.iOwner = 0;
    }

    public DSStat(String sName, String sCategory) {
        this.sName = sName;
        this.sCategory = sCategory;
        this.fValue = 0.0f;
        this.iOwner = 0;
    }

    public DSStat(String sName, Float fValue) {
        this.sName = sName;
        this.fValue = fValue;
        this.sCategory = "UNKNOWN";
        this.iOwner = 0;
    }

    public DSStat(String sName) {
        this.sName = sName;
        this.fValue = 0.0f;
        this.sCategory = "UNKNOWN";
        this.iOwner = 0;
    }

    public DSStat(DSStat statCopy) {
        this.sName = statCopy.getName();
        this.sCategory = statCopy.getCategory();
        this.iOwner = statCopy.getOwner();
        this.fValue = statCopy.getValue();
    }

    public Float getValue() {
        return fValue;
    }

    public void setValue(Float fValue) {
        this.fValue = fValue;
    }

    public void addValue(Float fIncrement) {
        setValue(this.fValue + fIncrement);
    }

    public String getName() {
        return sName;
    }

    public String getCategory() {
        return sCategory;
    }

    public int getOwner() {
        return iOwner;
    }

    public void setOwner(int iOwner) {
        this.iOwner = iOwner;
    }

    public void setOwner(String sOwner) {
        this.setOwner(sOwner.hashCode());
    }

    @Override
    public String toString() {

        return sCategory + ":" + sName + "=(" + fValue.toString() + ") " + this.iOwner;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DSStat other = (DSStat) obj;

        return this.sName.equals(other.getName());
    }

    @Override
    public int compareTo(DSStat statCompare) {
        return this.getName().compareTo(statCompare.getName());
    }

}
