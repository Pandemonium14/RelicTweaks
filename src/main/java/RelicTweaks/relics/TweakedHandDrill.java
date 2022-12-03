package RelicTweaks.relics;

import RelicTweaks.RelicTweaksMod;
import RelicTweaks.cardmods.DrillDamageMod;
import basemod.abstracts.CustomRelic;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.evacipated.cardcrawl.mod.stslib.relics.DamageModApplyingRelic;
import com.megacrit.cardcrawl.actions.utility.ShowCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;

import java.util.ArrayList;
import java.util.List;

public class TweakedHandDrill extends CustomRelic implements DamageModApplyingRelic {

    public static final String ID = RelicTweaksMod.makeID("HandDrill");

    public TweakedHandDrill() {
        super(ID, "drill.png", RelicTier.COMMON, LandingSound.FLAT);// 15
    }// 16

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];// 20
    }

    @Override
    public boolean shouldPushMods(DamageInfo damageInfo, Object o, List<AbstractDamageModifier> list) {
        return o instanceof AbstractCard;
    }

    @Override
    public List<AbstractDamageModifier> modsToPush(DamageInfo damageInfo, Object o, List<AbstractDamageModifier> list) {
        ArrayList<AbstractDamageModifier> mods = new ArrayList<>();
        mods.add(new DrillDamageMod());
        return mods;
    }
}
