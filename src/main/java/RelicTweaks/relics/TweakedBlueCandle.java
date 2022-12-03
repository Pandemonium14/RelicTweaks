package RelicTweaks.relics;

import RelicTweaks.RelicTweaksMod;
import RelicTweaks.actions.BlueCandleAction;
import com.evacipated.cardcrawl.mod.stslib.relics.CardRewardSkipButtonRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BlueCandle;

public class TweakedBlueCandle extends AbstractRelic {
    public static final String ID = RelicTweaksMod.makeID("Blue Candle");
    public static final int HP_LOSS = 1;

    public TweakedBlueCandle() {
        super(ID, "blueCandle.png", RelicTier.UNCOMMON, LandingSound.MAGICAL);// 15
    }// 16

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];// 20
    }

    public AbstractRelic makeCopy() {
        return new TweakedBlueCandle();// 25
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.CURSE) {// 30
            flash();// 31
            this.addToBot(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, 1, AbstractGameAction.AttackEffect.FIRE));// 32
            card.exhaust = true;// 38
            action.exhaustCard = true;// 39
        }
    }

    @Override
    public void onPlayerEndTurn() {
        addToBot(new BlueCandleAction());
    }

}
