/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package observerpattern.model.stats.framework;

import observerpattern.model.stats.framework.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.NoSuchElementException;

/**
 *
 * @author JaneW
 */
public class DSStatFactory {
    
    private String sFactoryName;

    private Map<String, List<DSStat>> mapStatGroups = new HashMap();

    public DSStatFactory(String sName) {
       
        this.sFactoryName = sName;
    }

    public void load() {
        System.out.println("Loading " + this.sFactoryName + "...");
        Scanner s = null;

        Path p1 = Paths.get(".\\src\\observerpattern\\model\\stats\\data", this.sFactoryName + ".csv");

        try {

            InputStream in = Files.newInputStream(p1);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            String sGroup, sName, sCategory;
            Float fStatValue;


            System.out.println("Reading file...");
            
            //Read header line
            line = reader.readLine();
            s = new Scanner(line).useDelimiter(",");


            // Start reading teh rest of the data file...
            while ((line = reader.readLine()) != null) {

                s = new Scanner(line).useDelimiter(",");
                sGroup = s.next();
                sName = s.next();
                sCategory = s.next();
                fStatValue = s.nextFloat();

                DSStat stat = new DSStat(sName, sCategory, fStatValue, sGroup.hashCode());

                System.out.println(sGroup + ":" + stat.toString());
                
                List<DSStat> listStats = this.mapStatGroups.get(sGroup);
                if(listStats == null) {
                    listStats = new ArrayList();
                    this.mapStatGroups.put(sGroup, listStats);
                }

                listStats.add(stat);
            }
            reader.close();

        } catch (IOException x) {

            System.err.println("STOP:" + x);

        }
        s.close();

    }




    
    public List<DSStat> getStats(String sGroup) {
        
        return mapStatGroups.get(sGroup);
        
    }
    
    
    public List<String> getGroups() {
        
        Set<String> setGroups = mapStatGroups.keySet();
        List<String> listGroups = new ArrayList();
        listGroups.addAll(setGroups);
        
        Collections.sort(listGroups);
        
        return listGroups;
    }
}
