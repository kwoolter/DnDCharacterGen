/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package observerpattern.model.stats.framework;

import java.util.*;

/**
 *
 * @author U394198
 */
public class DSStatContainer {

    private final String sName;
    private Map<String, DSStatCore> mapStats = new TreeMap();

    public DSStatContainer(String sName) {
        this.sName = sName;

    }

    // Get the name of this conatiner
    public String getName() {
        return sName;
    }

    public void initialise() {
        DSStatDictionary.load();
    }

    // Add a new stat to the container
    public void addStat(DSStatCore statNew) {

        // Add the new stat to the map
        mapStats.put(statNew.getName(), statNew);

        // ...and register the interest of this stat on the other stats in the container
        registerInterest(statNew);

        // attach any listeners to this new stat
        attachListeners(statNew);

        // and refresh them...
        statNew.touch();

    }

    // Look across the container for stats that are interested in this one and attach them
    private void attachListeners(DSStatCore statNew) {

        for (DSStatCore stat : this.mapStats.values()) {

            ArrayList<String> subjects = stat.getDependentStatNames();

            if (subjects != null && subjects.contains(statNew.getName())) {

                statNew.addChangeListener(stat);

            }

        }

        statNew.touch();

    }

    // Refresh all of the subscriptions of the stats in the container
    public void refresh() {

        // First clear all existing listeners
        for (DSStatCore stat : this.mapStats.values()) {

            stat.clearListeners();

        }

        // Rebuild listeners for each stat
        for (DSStatCore stat : this.mapStats.values()) {

            registerInterest(stat);

        }

        // ...and finally make sure al stats are up to date
        for (DSStatCore stat : this.mapStats.values()) {

            stat.touch();

        }

    }

    // Take the specified stat and record its interes in all of the stats that it is dependent on
    private void registerInterest(DSStatCore stat) {

        // Get the list of core stats that this derived stat is interested in
        ArrayList<String> subjects = stat.getDependentStatNames();

        // If we found some depenedency stats names
        if (subjects != null && subjects.size() > 0) {

            //  For each interested stat
            for (int i = 0; i < subjects.size(); i++) {
                String key = subjects.get(i);

                // Get the dependency stat from the container map
                DSStatCore statDependency = this.mapStats.get(key);

                // If we couldn't find the dependency stat then log error
                if (statDependency == null) {
                    System.err.println(stat.getName() + " trying to register interest with non-existant stat " + key);
                } else {

                    // Register the interest of the new stat with the dependency stat
                    statDependency.addChangeListener(stat);
                    System.out.println(stat.getName() + " is interested in " + statDependency.getName());

                }

            }
        }
    }

    // Touch all stats in the container to trigger refresh
    public void touch() {
        for (DSStatCore stat : this.mapStats.values()) {
            stat.touch();
        }

    }

    // Method to give you access to the underlying stat object
    public DSStatCore getStat(String sName) {

        if (mapStats == null) {
            return null;
        } else {
            return mapStats.get(sName);
        }

    }

    // Method to return the value of a specified stat
    public Float getStatValue(String sName) {

        Float fValue = null;

        if (mapStats != null) {
            DSStat stat = mapStats.get(sName);

            if (stat != null) {
                fValue = stat.getValue();
            }
        }

        return fValue;

    }

    //  Method to return the details of a specified stat
    public DSStatDetails getStatDetails(String sStatName) {

        return DSStatDictionary.getStat(sStatName);
    }

    // Method to provide a copy of the specified stat
    public DSStat getStatCopy(String sStatName) {

        DSStat statCopy = null;

        if (mapStats != null) {
            DSStatCore stat = mapStats.get(sStatName);
            if (stat != null) {
                statCopy = new DSStat(stat);
            }
        }

        return statCopy;

    }

    // Update a state to a new value or create if does not in existance
    public void updateStat(DSStat statNew) {

        if (mapStats != null) {

            DSStatCore stat = mapStats.get(statNew.getName());

            // If the stat does not exist then add it to the map
            if (stat == null) {

                addStat(new DSStatCore(statNew));

            } else {

                stat.setValue(statNew.getValue());
            }

        } else {
            System.err.println("Map of stats is null");
        }

    }

    // Get all of the stats related to teh specified category
    public Set<DSStat> getStatsByCategory(String sCategory) {

        Set<DSStat> results = new TreeSet();

        for (DSStatCore stat : this.mapStats.values()) {

            if (stat.getCategory().equals(sCategory)) {
                results.add(new DSStat(stat));
            }
        }

        return results;
    }

    // Get all of the stats owned by the specified owner
    public Set<DSStat> getStatsByOwner(String sOwner) {

        Set<DSStat> results = new TreeSet();

        //this.print();
        for (DSStatCore stat : this.mapStats.values()) {

            if (stat.getOwner() == sOwner.hashCode()) {
                results.add(new DSStat(stat));
            }
        }

        return results;
    }

    // Delete all of the stats owned be a specified owner
    // Note this has to be done as a two step process...
    // 1. collect the keys of the stats to be deleted from the map
    // 2. delete the stats for the collected keys
    // This is because you get concurrency exceptions of you try and iterate through a map ans delete items at the same time
    public void deleteStatsByOwner(String sOwner) {
        
        Set<String> setStatsToDelete = new HashSet();

        // Firstly iterate through the stats collecting the names of the stats that belong to the specified owner
        if (sOwner != null && this.mapStats != null && this.mapStats.size() > 0) {

            for (DSStatCore stat : this.mapStats.values()) {

                // If the owner of the stats matches the hash code of the specificed owner then collect it
                if (stat.getOwner() == sOwner.hashCode()) {
                    
                    setStatsToDelete.add(stat.getName());

                }
            }
        }
        
        // Once we have collacted the names of the stats for deleted, remove them all
        for(String sSelected : setStatsToDelete) {
            this.mapStats.remove(sSelected);
        }

    }

    
    // Print all of teh stats in the container sorted by category
    public void print() {
        System.out.println(this.getName() + ": Printing " + mapStats.size() + " stats...");

        // Create a map to organize the stats into a sorted list by category
        Map<String, Map<String, DSStatCore>> mapCategories = new TreeMap();

        // For each stat in the container...
        for (DSStatCore entry : this.mapStats.values()) {

            // see if there is already a sorted list for the category of this stat....
            Map<String, DSStatCore> stats = mapCategories.get(entry.getCategory());

            // if there isn't then create one and add it to the collection of categories
            if (stats == null) {
                stats = new TreeMap();
                mapCategories.put(entry.getCategory(), stats);
            }

            // add the stat to the sorted list that we are building for this category
            stats.put(entry.getName(), entry);
        }

        //  get all of the category lists from the sorted map...
        for (Map<String, DSStatCore> entry : mapCategories.values()) {

            // print each stat in the list for that category...
            for (DSStatCore stat : entry.values()) {
                int iListeners = stat.getNumberOfListeners();
                if (iListeners > 0) {
                    System.out.println(stat + ", listeners=" + iListeners);
                } else {
                    System.out.println(stat);
                }
            }
        }
    }
}
