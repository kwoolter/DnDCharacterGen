/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observerpattern.view;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Timer;
import observerpattern.model.stats.derived.*;
import observerpattern.model.stats.framework.DSStat;
import observerpattern.model.stats.framework.DSStatDetails;

/**
 *
 * @author KeithW
 */
public class DSDnDStatViewer extends javax.swing.JPanel implements ActionListener {

    private DSDnDStatContainer stats = null;
    private final Color colorText, colorBackground, colorTextHighlight, colorTextBonus;
    private BufferedImage imgFormatted, imgOriginal;
    Float fImageOpacity, fOpacityDelta, fMaxOpacity, fMinOpacity;
    private final int[] iXColumnWidths = {10, 170, 220, 120, 205, 160, 160, 160, 160};
    Timer timerViewUpdater;

    /**
     * Creates new form DSDnDStatViewer
     */
    public DSDnDStatViewer() {
        initComponents();

        colorText = Color.WHITE;
        colorTextHighlight = Color.RED;
        colorTextBonus = Color.GREEN;
        colorBackground = Color.BLACK;

        this.fImageOpacity = 0.1f;
        this.fOpacityDelta = -0.002f;
        this.fMaxOpacity = 0.4f;
        this.fMinOpacity = 0.00f;

        try {
            this.imgOriginal = ImageIO.read(new File("./ObserverPattern/src/observerpattern/resources/dnd.jpg"));
            this.imgFormatted = new BufferedImage(imgOriginal.getWidth(), imgOriginal.getHeight(), java.awt.Transparency.TRANSLUCENT);
            Graphics2D g = imgFormatted.createGraphics();
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.fImageOpacity));
            g.drawImage(this.imgOriginal, null, null);
            g.dispose();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        this.timerViewUpdater = new Timer(20, this);
        this.timerViewUpdater.start();

    }

    public void setModel(DSDnDStatContainer stats) {
        this.stats = stats;

        repaint();

    }

    public String statNameToDisplayString(String sStatName) {

        String sDisplay = sStatName;

        if (stats.getStatValue(sStatName) != null) {

            DSStat selectedStat = stats.getStat(sStatName);
            DSStatDetails statDetail = stats.getStatDetails(selectedStat.getName());
            String sDisplayName = (statDetail == null) ? selectedStat.getName() : statDetail.getDisplayName();
            sDisplay = sDisplayName + "=" + selectedStat.getValue().intValue();

        }

        return sDisplay;

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        this.imgFormatted = new BufferedImage(imgOriginal.getWidth(), imgOriginal.getHeight(), java.awt.Transparency.TRANSLUCENT);
        Graphics2D g = imgFormatted.createGraphics();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.fImageOpacity));
        g.drawImage(this.imgOriginal, null, null);
        g.dispose();

        this.fImageOpacity += this.fOpacityDelta;

        if (this.fImageOpacity > fMaxOpacity) {
            this.fImageOpacity = this.fMaxOpacity;
            this.fOpacityDelta *= -1;
        }

        if (this.fImageOpacity < fMinOpacity) {
            this.fImageOpacity = this.fMinOpacity;
            this.fOpacityDelta *= -1;

        }

        repaint();

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        g.drawImage(this.imgFormatted, 0, 0, this.getWidth(), this.getHeight(), null);

        if (stats == null) {
            return;
        }

        DSStat selectedStat;
        DSStatDetails statDetail;
        String sStatName, sDisplayName;
        Float fStatValue;
        int iYOffset = 40, iYIncrement = 14, iColumn = 0;

        // Start by displaying the characters name, race, class and level
        DSStat statLevel = stats.getStat("LVL");
        DSStat statXP = stats.getStat("XP");

        this.setBackground(this.colorBackground);
        g.setColor(this.colorText);
        g.setFont(new Font("default", Font.BOLD, 16));
        g.drawString(stats.getName() + " the " + stats.getRace() + " " + stats.getDSClass() + " (XP=" + statXP.getValue().intValue() + ", LVL=" + statLevel.getValue().intValue() + ")", 8, 16);

        // Start at first column, top row
        int x = this.iXColumnWidths[iColumn++], y = iYOffset;

        // Abilities and ability modifiers
        g.setFont(new Font("default", Font.BOLD, 12));
        g.drawString("ABILITIES", x, y);
        g.setFont(new Font("default", Font.PLAIN, 12));
        y += iYIncrement;
        for (String ability : DSStatAbility.getAbilities()) {
            DSStat abilityStat = stats.getStat("ABILITY_" + ability);
            DSStat abilityModifier = stats.getStat("ABILITY_" + ability + "_MODIFIER");

            statDetail = stats.getStatDetails(abilityStat.getName());
            sDisplayName = (statDetail == null) ? abilityStat.getName() : statDetail.getDisplayName();

            g.drawString(sDisplayName + "=" + abilityStat.getValue().intValue() + " (" + abilityModifier.getValue().intValue() + ")", x, y);
            y += iYIncrement;

        }

        y += iYIncrement;

        // Defenses
        g.setFont(new Font("default", Font.BOLD, 12));
        g.drawString("DEFENSES", x, y);
        g.setFont(new Font("default", Font.PLAIN, 12));
        y += iYIncrement;
        String[] sDefenseNames = {"AC", "FORT", "REF", "WILL"};

        for (String statName : sDefenseNames) {
            g.drawString(statNameToDisplayString(statName), x, y);
            y += iYIncrement;
        }

        // If you are proficient in the equipped armor...
        fStatValue = stats.getStatValue("PROFICIENCY_ARMOR");
        if (fStatValue != null) {
            if (fStatValue > 0.0f) {
                sStatName = "ITEM_ARMOR_CLASS";
                // Display the armor bonus
                if (stats.getStatValue(sStatName) != null) {
                    g.setColor(this.colorTextBonus);
                    g.drawString(statNameToDisplayString(sStatName), x, y);
                    g.setColor(this.colorText);
                }
                // Else say you are not proficient
            } else {
                g.setColor(this.colorTextHighlight);
                g.drawString("NOT proficient in armor", x, y);
                g.setColor(this.colorText);
            }
        } else {
            g.drawString("No armor equipped", x, y);
        }

        y += iYIncrement;

        // If you are proficient in the equipped shield...
        fStatValue = stats.getStatValue("PROFICIENCY_SHIELD");
        if (fStatValue != null) {
            if (fStatValue > 0.0f) {
                sStatName = "ITEM_SHIELD_ARMOR_CLASS";
                if (stats.getStatValue(sStatName) != null && stats.getStatValue(sStatName).floatValue() != 0.0f) {
                    g.setColor(this.colorTextBonus);
                    g.drawString(statNameToDisplayString(sStatName), x, y);
                    g.setColor(this.colorText);
                    y += iYIncrement;
                }
            } // Else say you are not proficient
            else {
                g.setColor(this.colorTextHighlight);
                g.drawString("NOT proficient in shield", x, y);
                g.setColor(this.colorText);
            }
        } else {
            g.drawString("No shield equipped", x, y);
        }

        x += this.iXColumnWidths[iColumn++];
        y = iYOffset;

        //Basic attacks
        g.setFont(
                new Font("default", Font.BOLD, 12));
        g.drawString(
                "BASIC ATTACKS", x, y);
        g.setFont(
                new Font("default", Font.PLAIN, 12));
        y += iYIncrement;

        for (String sAttackName : DSStatAttack.getsAttackNames()) {
            if (stats.getStat("ATTACK_" + sAttackName) != null) {
                g.drawString(statNameToDisplayString("ATTACK_" + sAttackName), x, y);
                y += iYIncrement;
            }
        }

        fStatValue = stats.getStatValue("PROFICIENCY_MELEE_WEAPON");
        if (fStatValue
                != null) {
            if (fStatValue > 0.0f) {
                g.setColor(this.colorTextBonus);
                g.drawString("Proficient in melee weapon", x, y);
                g.setColor(this.colorText);
            } else {
                g.setColor(this.colorTextHighlight);
                g.drawString("NOT proficient in melee weapon", x, y);
                g.setColor(this.colorText);
            }
        } else {
            g.drawString("No melee weapon equipped", x, y);
        }

        y += iYIncrement;

        fStatValue = stats.getStatValue("PROFICIENCY_RANGED_WEAPON");
        if (fStatValue
                != null) {
            if (fStatValue > 0.0f) {
                g.setColor(this.colorTextBonus);
                g.drawString("Proficient in ranged weapon", x, y);
                g.setColor(this.colorText);
            } else {
                g.setColor(this.colorTextHighlight);
                g.drawString("NOT proficient in ranged weapon", x, y);
                g.setColor(this.colorText);
            }
        } else {
            g.drawString("No ranged weapon equipped", x, y);
        }

        y += iYIncrement * 2;

        // Attack damage
        g.setFont(new Font("default", Font.BOLD, 12));
        g.drawString(
                "BASIC ATTACK DAMAGE", x, y);
        g.setFont(
                new Font("default", Font.PLAIN, 12));
        y += iYIncrement;

        Float statWeapon = stats.getStatValue("ITEM_MELEE_WEAPON_DAMAGE_DICE");
        if (statWeapon
                != null) {
            sStatName = stats.getStatValue("ITEM_MELEE_WEAPON_DAMAGE_DICE").intValue() + "d" + stats.getStatValue("ITEM_MELEE_WEAPON_DAMAGE_DICE_SIDES").intValue();
            g.drawString("Melee Weapon Damage=" + sStatName, x, y);
            y += iYIncrement;

            String sAttackName = "STR";
            for (DSStatDamage.DamageStatType type : DSStatDamage.DamageStatType.values()) {
                sStatName = "DAMAGE_" + sAttackName + "_" + type.toString();
                if (stats.getStat(sStatName) != null) {
                    g.drawString(statNameToDisplayString(sStatName), x, y);
                    y += iYIncrement;
                }

            }
        } else {
            g.drawString("No melee weapon equipped", x, y);
            y += iYIncrement;
        }

        statWeapon = stats.getStatValue("ITEM_RANGED_WEAPON_DAMAGE_DICE");
        if (statWeapon
                != null) {
            sStatName = stats.getStatValue("ITEM_RANGED_WEAPON_DAMAGE_DICE").intValue() + "d" + stats.getStatValue("ITEM_RANGED_WEAPON_DAMAGE_DICE_SIDES").intValue();
            g.drawString("Ranged Weapon Damage=" + sStatName, x, y);
            y += iYIncrement;

            String sAttackName = "DEX";
            for (DSStatDamage.DamageStatType type : DSStatDamage.DamageStatType.values()) {
                sStatName = "DAMAGE_" + sAttackName + "_" + type.toString();
                if (stats.getStat(sStatName) != null) {
                    g.drawString(statNameToDisplayString(sStatName), x, y);
                    y += iYIncrement;
                }
            }
        } else {
            g.drawString("No ranged weapon equipped", x, y);
            y += iYIncrement;
        }

        y += iYIncrement / 2;

        // Initiative
        for (DSStat stat : stats.getStatsByCategory(
                "INITIATIVE")) {
            g.drawString(statNameToDisplayString(stat.getName()), x, y);
            y += iYIncrement;
        }

        x += this.iXColumnWidths[iColumn++];
        y = iYOffset;

        // Skills
        g.setFont(
                new Font("default", Font.BOLD, 12));
        g.drawString(
                "SKILLS", x, y);
        g.setFont(
                new Font("default", Font.PLAIN, 12));
        y += iYIncrement;
        for (String skill : DSStatSkill.getSkillNames()) {
            DSStat skillStat = stats.getStat("SKILL_" + skill);
            DSStat skillTrainedStat = stats.getStat("SKILL_" + skill + "_TRAINED");

            statDetail = stats.getStatDetails(skillStat.getName());
            sDisplayName = (statDetail == null) ? skillStat.getName() : statDetail.getDisplayName();

            String sDisplay = sDisplayName + "=" + skillStat.getValue().intValue();
            if (skillTrainedStat != null && skillTrainedStat.getValue() > 0.0) {
                g.setColor(this.colorTextBonus);
            } else {
                g.setColor(this.colorText);
            }

            g.drawString(sDisplay, x, y);
            y += iYIncrement;

        }

        // Skill penalties
        g.setColor(
                this.colorTextHighlight);
        sStatName = "ITEM_ARMOR_SKILL_PENALTY";

        if (stats.getStatValue(sStatName)
                != null && stats.getStatValue(sStatName).floatValue() != 0.0f) {
            g.drawString(statNameToDisplayString(sStatName), x, y);
            y += iYIncrement;
        }

        sStatName = "ITEM_SHIELD_SKILL_PENALTY";

        if (stats.getStatValue(sStatName)
                != null && stats.getStatValue(sStatName).floatValue() != 0.0f) {
            g.drawString(statNameToDisplayString(sStatName), x, y);
            y += iYIncrement;
        }

        g.setColor(
                this.colorText);

        x += this.iXColumnWidths[iColumn++];
        y = iYOffset;

        // Senses
        g.setFont(new Font("default", Font.BOLD, 12));
        g.drawString("SENSES", x, y);
        g.setFont(new Font("default", Font.PLAIN, 12));
        y += iYIncrement;
        for (DSStat stat : stats.getStatsByCategory("SENSE")) {
            g.drawString(statNameToDisplayString(stat.getName()), x, y);
            y += iYIncrement;
        }

        y += iYIncrement;

        // Hit Points
        g.setFont(
                new Font("default", Font.BOLD, 12));
        g.drawString(
                "HIT POINTS", x, y);
        g.setFont(
                new Font("default", Font.PLAIN, 12));
        y += iYIncrement;
        for (DSStat stat : stats.getStatsByCategory(
                "HIT_POINTS")) {
            g.drawString(statNameToDisplayString(stat.getName()), x, y);
            y += iYIncrement;
        }

        x += this.iXColumnWidths[iColumn++];
        y = iYOffset;

        // Speed
        g.setFont(new Font("default", Font.BOLD, 12));
        g.drawString("SPEED", x, y);
        g.setFont(new Font("default", Font.PLAIN, 12));
        y += iYIncrement;

        for (DSStat stat : stats.getStatsByCategory("SPEED")) {
            if (stat.getValue() != 0.0f) {
                // If the stat is negative then highlight it
                if (stat.getValue() < 0.0f) {
                    g.setColor(this.colorTextHighlight);
                }
                g.drawString(statNameToDisplayString(stat.getName()), x, y);
                g.setColor(this.colorText);
                y += iYIncrement;
            }
        }

        y += iYIncrement;

        // Languages
        g.setFont(new Font("default", Font.BOLD, 12));
        g.drawString("LANGUAGES", x, y);
        g.setFont(new Font("default", Font.PLAIN, 12));
        y += iYIncrement;
        for (DSStat stat : stats.getStatsByCategory(
                "LANGUAGE")) {
            statDetail = stats.getStatDetails(stat.getName());
            sDisplayName = (statDetail == null) ? stat.getName() : statDetail.getDisplayName();
            g.drawString(sDisplayName, x, y);
            y += iYIncrement;
        }

        y += iYIncrement;

        //Weight
        g.setFont(
                new Font("default", Font.BOLD, 12));
        g.drawString(
                "WEIGHT", x, y);
        g.setFont(
                new Font("default", Font.PLAIN, 12));
        y += iYIncrement;

        Float fTotalWeight = 0.0f;
        for (DSStat stat : stats.getStatsByCategory(
                "WEIGHT")) {
            fTotalWeight += stat.getValue();
            g.drawString(statNameToDisplayString(stat.getName()), x, y);
            y += iYIncrement;
        }

        g.setFont(
                new Font("default", Font.ITALIC, 12));
        g.drawString(
                "Total=" + fTotalWeight.intValue(), x, y);
        g.setFont(
                new Font("default", Font.PLAIN, 12));
        y += iYIncrement;

        sStatName = "WEIGHT_MAX_LOAD";

        if (stats.getStatValue(sStatName)
                != null && stats.getStatValue(sStatName).floatValue() != 0.0f) {
            g.drawString(statNameToDisplayString(sStatName), x, y);
            y += iYIncrement;
        }

        y += iYIncrement;

        // Value of items
        g.setFont(
                new Font("default", Font.BOLD, 12));
        g.drawString(
                "VALUE", x, y);
        g.setFont(
                new Font("default", Font.PLAIN, 12));
        y += iYIncrement;

        Float fTotalValue = 0.0f;
        for (DSStat stat : stats.getStatsByCategory(
                "VALUE")) {
            fTotalValue += stat.getValue();
            g.drawString(statNameToDisplayString(stat.getName()), x, y);
            y += iYIncrement;
        }

        g.setFont(
                new Font("default", Font.ITALIC, 12));
        g.drawString(
                "Total=" + fTotalValue.intValue(), x, y);
        g.setFont(
                new Font("default", Font.PLAIN, 12));
        y += iYIncrement;

        x += this.iXColumnWidths[iColumn++];
        y = iYOffset;

        // Conditions
        int iCountConditions = 0;

        g.setFont(new Font("default", Font.BOLD, 12));
        g.drawString("CONDITIONS", x, y);
        g.setFont(new Font("default", Font.PLAIN, 12));
        g.setColor(this.colorTextHighlight);
        y += iYIncrement;
        for (DSStat stat : stats.getStatsByCategory(
                "CONDITIONS")) {
            statDetail = stats.getStatDetails(stat.getName());
            sDisplayName = (statDetail == null) ? stat.getName() : statDetail.getDisplayName();
            if (stat.getValue() != 0.0f) {
                g.drawString(sDisplayName, x, y);
                y += iYIncrement;
                iCountConditions++;
            }
        }

        g.setColor(this.colorText);
        if (iCountConditions
                == 0) {
            g.drawString("None", x, y);
            y += iYIncrement;
        }

        y += iYIncrement;

        // Proficiencies
        g.setFont(
                new Font("default", Font.BOLD, 12));
        g.drawString(
                "PROFICIENCIES", x, y);
        g.setFont(
                new Font("default", Font.PLAIN, 12));
        y += iYIncrement;
        for (DSStat stat : stats.getStatsByCategory(
                "PROFICIENCY")) {
            if (stat.getValue() > 0.0f) {
                statDetail = stats.getStatDetails(stat.getName());
                sDisplayName = (statDetail == null) ? stat.getName() : statDetail.getDisplayName();
                g.drawString(sDisplayName, x, y);
                y += iYIncrement;
            }
        }

        x += this.iXColumnWidths[iColumn++];
        y = iYOffset;

        //stats.print();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 575, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 432, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
