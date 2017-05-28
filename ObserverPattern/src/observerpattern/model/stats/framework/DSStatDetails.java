/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observerpattern.model.stats.framework;

/**
 *
 * @author KeithW
 */
public class DSStatDetails implements Comparable<DSStatDetails> {

    private String sStatName, sCategory, sDisplayName, sDescription, sCalculation;

    public DSStatDetails(String sStatName, String sCategory, String sDisplayName, String sDescription, String sCalculation) {
        this.sStatName = sStatName;
        this.sCategory = sCategory;
        this.sDisplayName = sDisplayName;
        this.sDescription = sDescription;
        this.sCalculation = sCalculation;
    }

    public String getName() {
        return sStatName;
    }

    public String getCategory() {
        return sCategory;
    }

    public String getDisplayName() {
        return sDisplayName;
    }

    public String getDescription() {
        return sDescription;
    }

    public String getCalculation() {
        return sCalculation;
    }

    @Override
    public String toString() {
        return sDisplayName;
    }

    @Override
    public int compareTo(DSStatDetails statCompare) {
        return this.getDisplayName().compareTo(statCompare.getDisplayName());
    }

}
