/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package observerpattern.model.stats.derived;

import observerpattern.model.stats.framework.DSStatDerived;

/**
 *
 * @author U394198
 */
public class DSStatSkill extends DSStatDerived {

    private String sName, sBaseStat;
    private boolean bArmorPenalty;

    private static final String[] skillNames = {"ACROBATICS", "ARCANA", "ATHLETICS", "BLUFF", "DUNGEONEERING", "ENDURANCE", "HEAL", "HISTORY", "DIPLOMACY", "INSIGHT", "INTIMIDATE", "NATURE", "PERCEPTION", "RELIGION", "STEALTH", "STREETWISE", "THIEVERY"};
    private static final String[] skillBaseStat = {"ACROBATICS", "ARCANA", "ATHLETICS", "BLUFF", "DUNGEONEERING", "ENDURANCE", "HEAL", "HISTORY", "DIPLOMACY", "INSIGHT", "NATURE", "PERCEPTION", "RELIGION", "STEALTH", "STREETWISE", "THIEVERY"};

    public DSStatSkill(String sName, String sBaseStat, boolean bArmorPenalty) {

        // Record the name of this derived stat
        super("SKILL_" + sName, "SKILL");

        // Not all dependency stats are mandatory
        setDependencyMandatory(false);

        // Record the name of the skill and the ability that it is based on
        this.sName = sName;
        this.sBaseStat = sBaseStat;
        this.bArmorPenalty = bArmorPenalty;

        // Register the core stats that this derived stats needs
        addCoreStat("LVL");
        addCoreStat("ABILITY_" + this.sBaseStat + "_MODIFIER");
        addCoreStat("SKILL_" + this.sName + "_TRAINED");
        addCoreStat("SKILL_" + this.sName + "_CLASS_TRAINED");
        addCoreStat("SKILL_" + this.sName + "_RACE_BONUS");

        if (bArmorPenalty == true) {
            addCoreStat("ITEM_ARMOR_SKILL_PENALTY");
            addCoreStat("ITEM_SHIELD_SKILL_PENALTY");
        }

    }

    public static String[] getSkillNames() {
        return skillNames;
    }

    // Called to calculate the current value of this derived stat from its core stats
    @Override
    protected Float calculate() {

        Float fNewValue = 0.0f;

        Float fArmorPenalty = mapDependencyStats.get("ITEM_ARMOR_SKILL_PENALTY");
        Float fShieldPenalty = mapDependencyStats.get("ITEM_SHIELD_SKILL_PENALTY");
        Float fAbilityModifier = mapDependencyStats.get("ABILITY_" + this.sBaseStat + "_MODIFIER");
        Float fLevel = mapDependencyStats.get("LVL");
        Float fSkillClassTrained = mapDependencyStats.get("SKILL_" + this.sName + "_CLASS_TRAINED");
        Float fSkillTrainedBonus = mapDependencyStats.get("SKILL_" + this.sName + "_TRAINED");
        Float fSkillRaceBonus = mapDependencyStats.get("SKILL_" + this.sName + "_RACE_BONUS");

        // Default the optional parameters
        fArmorPenalty = (fArmorPenalty == null) ? 0.0f : fArmorPenalty;
        fShieldPenalty = (fShieldPenalty == null) ? 0.0f : fShieldPenalty;
        fSkillTrainedBonus = (fSkillTrainedBonus == null) ? 0.0f : fSkillTrainedBonus;
        fSkillClassTrained = (fSkillClassTrained == null) ? 0.0f : 5.0f;
        fSkillRaceBonus = (fSkillRaceBonus == null) ? 0.0f : fSkillRaceBonus;

        // If we have our essential stats...
        if (fAbilityModifier != null && fLevel != null) {

            // Skill = relevant ability modifier + 1/2 lvel + bonuses...
            fNewValue = fAbilityModifier + (float) Math.floor((fLevel / 2.0f)) + Math.max(fSkillClassTrained, fSkillTrainedBonus) + fSkillRaceBonus;

            // If this skill takes a penalty for armor and/or shield then add on the penalties ...
            if (this.bArmorPenalty == true) {
                fNewValue += fArmorPenalty + fShieldPenalty;
            }
        }

        return fNewValue;

    }
}
