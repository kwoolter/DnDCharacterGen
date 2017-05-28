/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package observerpattern.model.stats.framework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TreeMap;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author JaneW
 */
public class DSStatDictionary {

    private static String sDictionaryName = "stats_explain";

    private static Map<String, DSStatDetails> dictionary = new TreeMap();

    private static boolean bLoaded = false;

    public DSStatDictionary(String sName) {

    }

    public static void load() {
        System.out.println("Loading " + sDictionaryName + "...");

        if (DSStatDictionary.bLoaded == true) {
            System.out.println(sDictionaryName + " already loaded.");
            return;
        }

        Scanner s = null;
        try {
            String current = new File(".").getCanonicalPath();
            System.out.println("Current dir:" + current);

        } catch (Exception x) {

            System.err.println("STOP:" + x);

        }


        Path p1 = Paths.get(".\\ObserverPattern\\src\\observerpattern\\model\\stats\\data", sDictionaryName + ".csv");

        try {

            InputStream in = Files.newInputStream(p1);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            String sName, sCategory, sDisplayName, sDescription, sCalculation;

            System.out.println("Reading file " + p1 + "...");

            // Read in header
            line = reader.readLine();

            while ((line = reader.readLine()) != null) {

                s = new Scanner(line).useDelimiter(",");
                sName = s.next();
                sCategory = s.next();
                sDisplayName = s.next();
                sDescription = s.next();
                sCalculation = s.next();

                DSStatDetails stat = new DSStatDetails(sName, sCategory, sDisplayName, sDescription, sCalculation);

                System.out.println(stat.toString());

                dictionary.put(stat.getName(), stat);

            }
            reader.close();

            DSStatDictionary.bLoaded = true;

        } catch (IOException x) {

            System.err.println("STOP:" + x);

        }
        s.close();

    }

    public static DSStatDetails getStat(String sStat) {

        return dictionary.get(sStat);

    }

    // Get a set of the stat keys from the dictionary
    public static String[] getStatNames() {

        return (String[]) dictionary.keySet().toArray();
    }

    // Get the entire contents of the dictionary in a sorted list
    public static List<DSStatDetails> getStatDetails() {

        Collection<DSStatDetails> stats = dictionary.values();
        List<DSStatDetails> listStats = new ArrayList();
        listStats.addAll(stats);

        class CustomComparator implements Comparator<DSStatDetails> {

            @Override
            public int compare(DSStatDetails c1, DSStatDetails c2) {
                return c2.getDisplayName().compareTo(c1.getDisplayName());
            }

        }

        Collections.sort(listStats, new CustomComparator());

        return listStats;

    }

    // Get a sorted list of the categories of stats in the dictionary
    public static Set<String> getCategories() {
        Set<String> setCategories = new TreeSet();

        for (DSStatDetails stat : dictionary.values()) {
            setCategories.add(stat.getCategory());
        }

        return setCategories;

    }

    // Get a sorted list of the dictionary entries for a specific category
    public static List<DSStatDetails> getStatsByCategory(String sCategoryName) {

        List<DSStatDetails> listSubSet = new ArrayList();

        for (DSStatDetails stat : dictionary.values()) {
            if (stat.getCategory().equals(sCategoryName)) {
                listSubSet.add(stat);
            }
        }

        class CustomComparator implements Comparator<DSStatDetails> {

            @Override
            public int compare(DSStatDetails c1, DSStatDetails c2) {
                return c1.getDisplayName().compareTo(c2.getDisplayName());
            }

        }

        Collections.sort(listSubSet, new CustomComparator());

        return listSubSet;
    }

}
