/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package observerpattern.model.stats.derived;

import java.util.List;
import observerpattern.model.stats.framework.DSStat;
import observerpattern.model.stats.framework.DSStatFactory;

/**
 *
 * @author JaneW
 */
public class DSEquipmentManager {

    private DSDnDStatContainer stats;
    private DSStatFactory weaponMeleeStats, weaponRangedStats, armorStats, shieldStats;
    private String sCurrentMeleeWeapon, sCurrentRangedWeapon, sCurrentArmor, sCurrentShield;

    public DSEquipmentManager() {
        
        sCurrentMeleeWeapon = sCurrentRangedWeapon = sCurrentArmor =sCurrentShield = null;

        weaponMeleeStats = new DSStatFactory("melee_weapon_stats");
        weaponMeleeStats.load();

        weaponRangedStats = new DSStatFactory("ranged_weapon_stats");
        weaponRangedStats.load();

        armorStats = new DSStatFactory("armor_stats");
        armorStats.load();

        shieldStats = new DSStatFactory("shield_stats");
        shieldStats.load();
    }

    public void setModel(DSDnDStatContainer stats) {
        this.stats = stats;
    }

    // Check if the character is proficient in the specified armor.
    public boolean isArmorProficient(String sArmorName) {
        boolean isProficient = false;

        // Firstly see if the character is proficient in the item by name...
        DSStat statItemProficiency = stats.getStat("PROFICIENCY_ARMOR_" + sArmorName);

        if (statItemProficiency != null && statItemProficiency.getValue() > 0.0f) {
            isProficient = true;
        }

        return isProficient;
    }

    // Check if the character is proficient in the specified shield.
    public boolean isShieldProficient(String sShieldName) {
        boolean isProficient = false;

        // Firstly see if the character is proficient in the item by name...
        DSStat statItemProficiency = stats.getStat("PROFICIENCY_SHIELD_" + sShieldName);

        if (statItemProficiency != null && statItemProficiency.getValue() > 0.0f) {
            isProficient = true;
        }

        return isProficient;
    }

    // Check if the character is proficient in the specified weapon.
    public boolean isMeleeWeaponProficient(String sWeaponName) {
        boolean isProficient = false;

        // Firstly see if the character is proficient in the weapon by name...
        DSStat statWeaponProficiency = stats.getStat("PROFICIENCY_WEAPON_" + sWeaponName);

        if (statWeaponProficiency != null && statWeaponProficiency.getValue() > 0.0f) {
            isProficient = true;
        } // Next see if the character is proficient in the weapon type...
        else {

            List<DSStat> statsWeapon = this.weaponMeleeStats.getStats(sWeaponName);

            DSStat statWeaponType = null;

            // Get all of the stats for this weapon from the factory and find the weapon type stat
            for (DSStat stat : statsWeapon) {

                if (stat.getName().equals("ITEM_MELEE_WEAPON_TYPE")) {
                    statWeaponType = stat;
                }

            }

            // If we found the type of weapon
            if (statWeaponType != null) {

                // Use the category of this stat to determine if proficient
                DSStat statWeaponTypeProficiency = stats.getStat("PROFICIENCY_WEAPON_" + statWeaponType.getCategory());

                if (statWeaponTypeProficiency != null && statWeaponTypeProficiency.getValue() > 0.0f) {
                    isProficient = true;
                }
            }
        }
        return isProficient;
    }

    // Check if the character is proficient in the specified weapon.
    public boolean isRangedWeaponProficient(String sWeaponName) {
        boolean isProficient = false;

        // Firstly see if the character is proficient in the weapon by name...
        DSStat statWeaponProficiency = stats.getStat("PROFICIENCY_WEAPON_" + sWeaponName);

        if (statWeaponProficiency != null && statWeaponProficiency.getValue() > 0.0f) {
            isProficient = true;
        } // Next see if the character is proficient in the weapon type...
        else {

            List<DSStat> statsWeapon = this.weaponRangedStats.getStats(sWeaponName);

            DSStat statWeaponType = null;

            // Get all of the stats for this weapon from the factory and find the weapon type stat
            for (DSStat stat : statsWeapon) {

                if (stat.getName().equals("ITEM_RANGED_WEAPON_TYPE")) {
                    statWeaponType = stat;
                }

            }

            // If we found the type of weapon
            if (statWeaponType != null) {

                // Use the category of this stat to determine if proficient
                DSStat statWeaponTypeProficiency = stats.getStat("PROFICIENCY_WEAPON_" + statWeaponType.getCategory());

                if (statWeaponTypeProficiency != null && statWeaponTypeProficiency.getValue() > 0.0f) {
                    isProficient = true;
                }
            }
        }
        return isProficient;
    }

    // Load the stats relevant to the specified weapon
    public void equipMeleeWeapon(String sWeaponName) {

        List<DSStat> statsWeapon = this.weaponMeleeStats.getStats(sWeaponName);

        if (statsWeapon != null) {

            //Remove all stats from the current equipped item
            this.stats.deleteStatsByOwner(this.sCurrentMeleeWeapon);

            // Get all of the stats for this weapon from the factory and load them
            for (DSStat stat : statsWeapon) {

                this.stats.updateStat(stat);

            }

            // Set the new weapon as teh current one
            this.sCurrentMeleeWeapon = sWeaponName;

            DSStat statWeaponproficiency = new DSStat("PROFICIENCY_MELEE_WEAPON", "PROFICIENCY");
            if (this.isMeleeWeaponProficient(sWeaponName) == true) {
                statWeaponproficiency.setValue(1.0f);
            } else {
                statWeaponproficiency.setValue(0.0f);
            }

            this.stats.updateStat(statWeaponproficiency);
        }

    }

    // Load the stats relevant to the specified weapon
    public void equipRangedWeapon(String sWeaponName) {

        List<DSStat> statsWeapon = this.weaponRangedStats.getStats(sWeaponName);

        if (statsWeapon != null) {

            //Remove all stats from the current equipped item
            this.stats.deleteStatsByOwner(this.sCurrentRangedWeapon);

            // Get all of the stats for this weapon from the factory and load them
            for (DSStat stat : statsWeapon) {

                this.stats.updateStat(stat);

            }

            this.sCurrentRangedWeapon = sWeaponName;

            DSStat statWeaponproficiency = new DSStat("PROFICIENCY_RANGED_WEAPON", "PROFICIENCY");
            if (this.isRangedWeaponProficient(sWeaponName) == true) {
                statWeaponproficiency.setValue(1.0f);
            } else {
                statWeaponproficiency.setValue(0.0f);
            }

            this.stats.updateStat(statWeaponproficiency);
        }
    }

    // Load the stats relevant to the specified armor
    public void equipArmor(String sItemName) {

        List<DSStat> statsItem = this.armorStats.getStats(sItemName);

        if (statsItem != null) {

            //Remove all stats from the current equipped item
            this.stats.deleteStatsByOwner(this.sCurrentArmor);

            // Get all of the stats for this armor from the factory and load them
            for (DSStat stat : statsItem) {

                this.stats.updateStat(stat);

            }

            this.sCurrentArmor = sItemName;

            DSStat statItemProficiency = new DSStat("PROFICIENCY_ARMOR", "PROFICIENCY");
            if (this.isArmorProficient(sItemName) == true) {
                statItemProficiency.setValue(1.0f);
            } else {
                statItemProficiency.setValue(0.0f);
            }

            this.stats.updateStat(statItemProficiency);

        }
    }

    // Load the stats relevant to the specified shield
    public void equipShield(String sItemName) {

        List<DSStat> statsItem = this.shieldStats.getStats(sItemName);

        if (statsItem != null) {

            //Remove all stats from the current equipped item
            this.stats.deleteStatsByOwner(this.sCurrentShield);

            // Get all of the stats for this shield from the factory and load them
            for (DSStat stat : statsItem) {

                this.stats.updateStat(stat);

            }

            this.sCurrentShield = sItemName;

            DSStat statItemProficiency = new DSStat("PROFICIENCY_SHIELD", "PROFICIENCY");
            if (sItemName.equals("NONE") || this.isShieldProficient(sItemName) == true) {
                statItemProficiency.setValue(1.0f);
            } else {
                statItemProficiency.setValue(0.0f);
            }

            this.stats.updateStat(statItemProficiency);

        }
    }

}
