/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observerpattern.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Timer;
import observerpattern.model.stats.derived.DSDnDStatContainer;
import observerpattern.model.stats.derived.DSEquipmentManager;
import observerpattern.model.stats.derived.DSSkillManager;
import observerpattern.model.stats.derived.DSStatAbility;
import observerpattern.model.stats.framework.DSStat;
import observerpattern.model.stats.framework.DSStatDictionary;
import observerpattern.model.stats.framework.DSStatFactory;

/**
 *
 * @author KeithW
 */
public class DSMainFrame extends javax.swing.JFrame implements ActionListener {

    private DefaultComboBoxModel racesModel, classesModel;
    private DSDnDStatContainer player;
    private DSSkillManager skills;
    private DSDnDStatViewer playerView;
    private DSSkillPickerView skillsView;
    private DSAbilityRollerView abilityRoller;
    private DSEquipmentPickerView equipmentPickerView;
    private DSStatHelperView statHelperView;
    private DSEquipmentManager equipment;
    private DSStatFactory raceStats, classStats, armorStats, shieldStats;
    private Timer timer1;

    /**
     * Creates new form DSMainFrame
     */
    public DSMainFrame() {
        initComponents();

        DSStatDictionary.load();

        this.setMinimumSize(new Dimension(1120, 755));

        racesModel = new DefaultComboBoxModel();
        classesModel = new DefaultComboBoxModel();

        playerView = new DSDnDStatViewer();
        abilityRoller = new DSAbilityRollerView();
        skillsView = new DSSkillPickerView();
        equipmentPickerView = new DSEquipmentPickerView();
        statHelperView = new DSStatHelperView();

        this.add(playerView);
        this.add(abilityRoller);
        this.add(skillsView);
        this.add(equipmentPickerView);
        this.add(statHelperView);

        statHelperView.initialise();

        // Load in the reference data for races and classes
        raceStats = new DSStatFactory("race_stats");
        classStats = new DSStatFactory("class_stats");

        raceStats.load();
        classStats.load();

        // Load in the reference data for armor, shields and weapons
        armorStats = new DSStatFactory("armor_stats");
        armorStats.load();

        shieldStats = new DSStatFactory("shield_stats");
        shieldStats.load();

        for (String key : raceStats.getGroups()) {
            racesModel.addElement(key);
        }

        for (String key : classStats.getGroups()) {
            classesModel.addElement(key);
        }

        this.jComboRaces.setModel(racesModel);
        this.jComboClasses.setModel(classesModel);

        this.jComboClasses.setSelectedIndex(0);
        this.jComboRaces.setSelectedIndex(0);

        this.timer1 = new Timer(200, this);

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.

        int yPosition = this.jPanelControls.getHeight();

        abilityRoller.setBounds(0, yPosition, this.getWidth(), 30);

        yPosition += abilityRoller.getHeight();

        this.equipmentPickerView.setBounds(0, yPosition, this.getWidth(), 30);

        yPosition += equipmentPickerView.getHeight();

        skillsView.setBounds(0, yPosition, this.getWidth(), 150);

        yPosition += skillsView.getHeight();

        playerView.setBounds(0, yPosition, this.getWidth(), 340);

        yPosition += playerView.getHeight();

        statHelperView.setBounds(0, yPosition, this.getWidth(), 122);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        this.playerView.repaint();
        this.statHelperView.repaint();
        
    }

        /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelControls = new javax.swing.JPanel();
        jComboRaces = new javax.swing.JComboBox();
        jTextName = new javax.swing.JTextField();
        jComboClasses = new javax.swing.JComboBox();
        jbuttonRoll = new javax.swing.JButton();
        jButtonReCalc = new javax.swing.JButton();
        jButtonTest = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanelControls.setBackground(new java.awt.Color(153, 153, 153));

        jComboRaces.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jTextName.setText("jTextField1");

        jComboClasses.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jbuttonRoll.setText("Roll");
        jbuttonRoll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbuttonRollActionPerformed(evt);
            }
        });

        jButtonReCalc.setText("ReCalc");
        jButtonReCalc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReCalcActionPerformed(evt);
            }
        });

        jButtonTest.setText("Tester");
        jButtonTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTestActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelControlsLayout = new javax.swing.GroupLayout(jPanelControls);
        jPanelControls.setLayout(jPanelControlsLayout);
        jPanelControlsLayout.setHorizontalGroup(
            jPanelControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelControlsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextName, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboRaces, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboClasses, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbuttonRoll)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonReCalc)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(jButtonTest)
                .addContainerGap())
        );
        jPanelControlsLayout.setVerticalGroup(
            jPanelControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelControlsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboRaces, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboClasses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbuttonRoll)
                    .addComponent(jButtonReCalc)
                    .addComponent(jButtonTest))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelControls, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelControls, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 255, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbuttonRollActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbuttonRollActionPerformed

        // Stop the view update timer as no point updating while we roll a new character
        timer1.stop();

        String sName = this.jTextName.getText();
        String sRace = (String) racesModel.getSelectedItem();
        String sClass = (String) classesModel.getSelectedItem();

        player = new DSDnDStatContainer(sName, sRace, sClass);
        player.initialise();
        playerView.setModel(player);
        statHelperView.setModel(player);
        abilityRoller.setModel(player);

        this.skills = new DSSkillManager();
        this.skills.setStats(player);
        this.skills.initialise();
        this.skillsView.setModel(skills);

        this.equipment = new DSEquipmentManager();
        this.equipment.setModel(player);
        this.equipmentPickerView.setModel(equipment);

        // Now ok to start updating the view again
        timer1.start();

    }//GEN-LAST:event_jbuttonRollActionPerformed

    private void jButtonReCalcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReCalcActionPerformed
        // TODO add your handling code here:

                this.player.print();
        playerView.repaint();
    }//GEN-LAST:event_jButtonReCalcActionPerformed

    private void jButtonTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTestActionPerformed
        // TODO add your handling code here:

        Set<DSStat> stats = this.player.getStatsByOwner("ELF");

        for (DSStat stat : stats) {
            System.out.println(stat);
        }
    }//GEN-LAST:event_jButtonTestActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DSMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DSMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DSMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DSMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new DSMainFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonReCalc;
    private javax.swing.JButton jButtonTest;
    private javax.swing.JComboBox jComboClasses;
    private javax.swing.JComboBox jComboRaces;
    private javax.swing.JPanel jPanelControls;
    private javax.swing.JTextField jTextName;
    private javax.swing.JButton jbuttonRoll;
    // End of variables declaration//GEN-END:variables
}
