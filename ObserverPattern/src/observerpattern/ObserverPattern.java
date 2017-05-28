/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package observerpattern;

import observerpattern.model.stats.derived.DSStatDefense;
import observerpattern.model.stats.derived.DSStatAbility;
import observerpattern.model.stats.derived.DSStatInitiative;
import observerpattern.model.stats.derived.DSStatModifier;
import observerpattern.model.stats.derived.DSStatHealingSurgesPerDay;
import observerpattern.model.stats.derived.DSStatDefenseAC;
import observerpattern.model.stats.derived.DSStatSkill;
import observerpattern.model.stats.derived.DSStatMaxHP;
import observerpattern.model.stats.framework.DSStatContainer;
import observerpattern.model.stats.framework.DSStatCore;
import observerpattern.model.stats.framework.DSStat;
import observerpattern.model.stats.framework.DSStatFactory;
import observerpattern.model.stats.derived.DSDnDStatContainer;

import java.util.List;
import observerpattern.view.DSMainFrame;

/**
 *
 * @author U394198
 */
public class ObserverPattern {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DSMainFrame().setVisible(true);
            }
        });

    }

}
