/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observerpattern.view;

import java.awt.Graphics;
import javax.swing.DefaultListModel;
import observerpattern.model.stats.derived.DSException;
import observerpattern.model.stats.derived.DSSkillManager;
import observerpattern.model.stats.derived.DSStatSkill;

/**
 *
 * @author KeithW
 */
public class DSSkillPickerView extends javax.swing.JPanel {

    private DefaultListModel<String> availableModel, trainedModel;
    private DSSkillManager skills;
    private int iMaxSkills;

    /**
     * Creates new form DSSkillPickerView
     */
    public DSSkillPickerView() {
        initComponents();

        availableModel = new DefaultListModel();
        trainedModel = new DefaultListModel();

        this.jListAvailable.setModel(availableModel);
        this.jListTrained.setModel(trainedModel);

        // Initial stats is that the selection buttons are disabled until you load in a model
        this.jButtonRemove.setEnabled(false);
        this.jButtonAdd.setEnabled(false);

    }

    // Point the view at the Model
    public void setModel(DSSkillManager skills) {

        this.skills = skills;

        // Populate the list boxes with the choices available from the model
        load();

        // Update test to show how many skills you can train in
        this.iMaxSkills = skills.getMaxSkills();
        this.jLabelTrained.setText("Trained (max=" + this.iMaxSkills + ")");

        // Set the selections in the list boxes
        this.jListAvailable.setSelectedIndex(0);
        this.jListTrained.setSelectedIndex(0);

    }

    // Load the list box models with the skills that are available from the Skill model
    private void load() {

        // Clear the two list box models
        this.trainedModel.clear();
        this.availableModel.clear();

        // For each skill...
        for (String sSkillName : DSStatSkill.getSkillNames()) {

            try {
                // If you are already tarined in it the add it to the trained model
                if (skills.isTrained(sSkillName)) {
                    trainedModel.addElement(sSkillName);
                }

                // If the skill is available to be trained in than add to teh available model
                if (skills.isTrainable(sSkillName)) {
                    availableModel.addElement(sSkillName);
                }
            } catch (DSException e) {
                System.err.println(e);
            }
        }
    }

    //  Method to train in the selected skill
    private void add() {

        // Get which skill is selected
        int iAvailableSelected = this.jListAvailable.getSelectedIndex();

        // If a akill is selected....
        if (iAvailableSelected >= 0) {

            // Get the name of the skill
            String sAvailableSelected = this.availableModel.elementAt(iAvailableSelected);

            System.out.println("Training in " + sAvailableSelected);

            try {

                // Train in the skill that was selected
                this.skills.train(sAvailableSelected);

                // eload the list box models to reflact what has changed
                load();

                // Highlight the next available skill in the list box
                iAvailableSelected = iAvailableSelected == this.availableModel.size() ? this.availableModel.size() - 1 : iAvailableSelected;
                this.jListAvailable.setSelectedIndex(iAvailableSelected);

                // Highlighted the trained skill in teh trained list box
                this.jListTrained.setSelectedIndex(this.trainedModel.indexOf(sAvailableSelected));

            } catch (DSException e) {
                System.err.println(e);
            }
        }
    }

    // Method to UN-train in the selected skill
    private void remove() {

        // Get which skill is selected
        int iSelected = this.jListTrained.getSelectedIndex();

        // If a skill is selected...
        if (iSelected >= 0) {

            // Get the name of the selected skill
            String sSelected = this.trainedModel.getElementAt(this.jListTrained.getSelectedIndex());

            System.out.println("UN-Training in " + sSelected);

            try {
                // Un-train in the selected skill
                this.skills.untrain(sSelected);

                // Reload the list box models based on the change
                load();

                //  Highlight the next available skill in the trained list box
                iSelected = iSelected == this.trainedModel.size() ? this.trainedModel.size() - 1 : iSelected;
                this.jListTrained.setSelectedIndex(iSelected);

                // Highlight the untrained skill in the available list box
                this.jListAvailable.setSelectedIndex(this.availableModel.indexOf(sSelected));

            } catch (DSException e) {
                System.err.println(e);

            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jListTrained = new javax.swing.JList();
        jButtonAdd = new javax.swing.JButton();
        jButtonRemove = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListAvailable = new javax.swing.JList();
        jLabelTrained = new javax.swing.JLabel();
        jLabelAvailable = new javax.swing.JLabel();

        jListTrained.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jListTrained.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListTrainedMouseClicked(evt);
            }
        });
        jListTrained.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListTrainedValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jListTrained);

        jButtonAdd.setText("<< Train <<");
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });

        jButtonRemove.setText(">> Remove >>");
        jButtonRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRemoveActionPerformed(evt);
            }
        });

        jListAvailable.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jListAvailable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListAvailableMouseClicked(evt);
            }
        });
        jListAvailable.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListAvailableValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(jListAvailable);

        jLabelTrained.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabelTrained.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTrained.setText("Trained");

        jLabelAvailable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabelAvailable.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelAvailable.setText("Available");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jLabelTrained, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonRemove, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jLabelAvailable, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTrained)
                    .addComponent(jLabelAvailable))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonRemove)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
        // TODO add your handling code here:

        add();

    }//GEN-LAST:event_jButtonAddActionPerformed

    private void jButtonRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRemoveActionPerformed
        // TODO add your handling code here:

        remove();
    }//GEN-LAST:event_jButtonRemoveActionPerformed

    private void jListAvailableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListAvailableMouseClicked
        // TODO add your handling code here:

        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            evt.consume();
            add();
        }

    }//GEN-LAST:event_jListAvailableMouseClicked

    private void jListTrainedMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListTrainedMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            evt.consume();
            remove();
        }

    }//GEN-LAST:event_jListTrainedMouseClicked

    // Called when the selection in the Trained list is changed
    private void jListTrainedValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListTrainedValueChanged
        // TODO add your handling code here:

        // Find the currently selcted trained skill
        int iSelected = this.jListTrained.getSelectedIndex();

        // If a skill is selected....
        if (iSelected >= 0) {

            // Get the name of the skill...
            String sSelected = this.trainedModel.getElementAt(this.jListTrained.getSelectedIndex());

            try {
                // If you can't un-train this skill then disable the remove button
                if (this.skills.isUntrainable(sSelected) == false) {
                    this.jButtonRemove.setEnabled(false);
                } // Otherwise it is OK to enable the Remove button
                else {
                    this.jButtonRemove.setEnabled(true);
                }
            } catch (DSException e) {
                System.err.println(e);
            }
        } // If no skill is selected then disable the Remove button
        else {
            this.jButtonRemove.setEnabled(false);
        }
    }//GEN-LAST:event_jListTrainedValueChanged

    // Called when the selection in the Available list is changed
    private void jListAvailableValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListAvailableValueChanged

        int iSelected = this.jListAvailable.getSelectedIndex();

        // If nothing is selected then disable the Add button
        if (iSelected < 0) {
            this.jButtonAdd.setEnabled(false);
        } // Otherwise enable it
        else {
            this.jButtonAdd.setEnabled(true);
        }


    }//GEN-LAST:event_jListAvailableValueChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonRemove;
    private javax.swing.JLabel jLabelAvailable;
    private javax.swing.JLabel jLabelTrained;
    private javax.swing.JList jListAvailable;
    private javax.swing.JList jListTrained;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
