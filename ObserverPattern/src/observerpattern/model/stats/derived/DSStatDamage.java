/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observerpattern.model.stats.derived;

import java.util.Random;
import observerpattern.model.stats.framework.DSStatDerived;

/**
 *
 * @author KeithW
 */
public class DSStatDamage extends DSStatDerived {

    private String sAbility, sWeaponType;

    public enum DamageStatType {

        MIN, MAX, ROLL
    };
    private DamageStatType type;

    public DSStatDamage(String sAbility, DamageStatType type) {

        super("DAMAGE_" + sAbility + "_" + type.toString(), "ATTACK");
        this.sAbility = sAbility;
        this.type = type;

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

        addCoreStat("ABILITY_" + sAbility + "_MODIFIER");
        addCoreStat("ITEM_" + sWeaponType + "_WEAPON_DAMAGE_DICE");
        addCoreStat("ITEM_" + sWeaponType + "_WEAPON_DAMAGE_DICE_SIDES");

    }

    // Called to calculate the current value of this derived stat from its core stats
    @Override
    protected Float calculate() {

        Float fNewValue = 0.0f;

        Float fAbilityModifier = mapDependencyStats.get("ABILITY_" + this.sAbility + "_MODIFIER");
        Float fDamageDice = mapDependencyStats.get("ITEM_" + sWeaponType + "_WEAPON_DAMAGE_DICE");
        Float fDamageDiceSides = mapDependencyStats.get("ITEM_" + sWeaponType + "_WEAPON_DAMAGE_DICE_SIDES");

        fNewValue = fAbilityModifier;

        switch (this.type) {
            case MIN:
                fNewValue += fDamageDice;
                break;
            case MAX:
                fNewValue += fDamageDice * fDamageDiceSides;
                break;
            case ROLL:
                Random rand = new Random();
                for (int i = 0; i < fDamageDice.intValue(); i++) {
                    fNewValue += rand.nextInt(fDamageDiceSides.intValue());
                }
                break;
            default:
                break;
        }

        return fNewValue;

    }
}
