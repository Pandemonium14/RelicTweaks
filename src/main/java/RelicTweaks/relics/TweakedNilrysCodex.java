package RelicTweaks.relics;

import RelicTweaks.RelicTweaksMod;
import RelicTweaks.actions.TweakedNilryAction;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.CodexAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.NilrysCodex;

public class TweakedNilrysCodex extends CustomRelic {
    public static final String ID = RelicTweaksMod.makeID("Nilry's Codex");

    public TweakedNilrysCodex() {
        super(ID, "codex.png", RelicTier.SPECIAL, LandingSound.MAGICAL);// 11
    }// 12

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];// 16
    }

    @Override
    public void atTurnStart() {
        addToBot(new TweakedNilryAction());
    }

    public AbstractRelic makeCopy() {
        return new TweakedNilrysCodex();// 27
    }
}
