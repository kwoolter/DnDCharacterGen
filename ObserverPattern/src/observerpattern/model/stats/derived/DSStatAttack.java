/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observerpattern.model.stats.derived;

import observerpattern.model.stats.framework.DSStatDerived;

/**
 *
 * @author KeithW
 */
public class DSStatAttack extends DSStatDerived {

    private String sAttackName, sAbility, sWeaponType;

    private static String[] sAttackNames = {"STR", "WIS", "INT", "DEX"};

    public DSStatAttack(String sAttackName, String sAbility) {
        super("ATTACK_" + sAbility, "ATTACK");
        this.sAttackName = sAttackName;
        this.sAbility = sAbility;

        switch (sAbility) {
            case "STR":
                sWeaponType = "MELEE";
                break;
            case "DEX":
                sWeaponType = "RANGED";
                break;
            default:
                sWeaponType = "MELEE";
                break;

        }

        // Not all dependency stats are mandatory
        setDependencyMandatory(false);

        addCoreStat("ABILITY_" + sAbility + "_MODIFIER");
        addCoreStat("LVL");
        addCoreStat("ITEM_" + this.sWeaponType + "_WEAPON_PROFICIENCY_BONUS");
        addCoreStat("PROFICIENCY_" + this.sWeaponType + "_WEAPON");
    }

    public String getsAttackName() {
        return sAttackName;
    }

    public static String[] getsAttackNames() {
        return sAttackNames;
    }

    // Called to calculate the current value of this derived stat from its core stats
    @Override
    protected Float calculate() {

        Float fNewValue = 0.0f;

        Float fAbilityModifier = mapDependencyStats.get("ABILITY_" + this.sAbility + "_MODIFIER");
        Float fLevel = mapDependencyStats.get("LVL");
        Float fProficiencyBonus = mapDependencyStats.get("ITEM_" + this.sWeaponType + "_WEAPON_PROFICIENCY_BONUS");
        Float fProficient = mapDependencyStats.get("PROFICIENCY_" + this.sWeaponType + "_WEAPON");

        if (fLevel != null && fAbilityModifier != null) {
            fProficiencyBonus = fProficiencyBonus == null ? 0.0f : fProficiencyBonus;
            fProficient = fProficient == null ? 0.0f : fProficient;
 
            fNewValue = fAbilityModifier + (fProficiencyBonus * fProficient) + (float) Math.floor(fLevel.floatValue() / 2.0f);

        }

        return fNewValue;

    }

}
