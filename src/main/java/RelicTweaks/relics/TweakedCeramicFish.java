package RelicTweaks.relics;

import RelicTweaks.RelicTweaksMod;
import basemod.abstracts.CustomRelic;
import com.evacipated.cardcrawl.mod.stslib.relics.CardRewardSkipButtonRelic;
import com.evacipated.cardcrawl.mod.stslib.relics.OnSkipCardRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.CeramicFish;
import com.megacrit.cardcrawl.rewards.RewardItem;

public class TweakedCeramicFish extends CustomRelic implements CardRewardSkipButtonRelic {
    public static final String ID = RelicTweaksMod.makeID("Ceramic Fish");
    private static final int GOLD_AMT = 9;

    public TweakedCeramicFish() {
        super(ID, "ceramic_fish.png", RelicTier.COMMON, LandingSound.FLAT);// 12
    }// 13

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 9 + this.DESCRIPTIONS[1];// 17
    }


    public boolean canSpawn() {
        return Settings.isEndless || AbstractDungeon.floorNum <= 48;// 37
    }

    public AbstractRelic makeCopy() {
        return new TweakedCeramicFish();// 42
    }

    @Override
    public void onClickedButton() {
        AbstractDungeon.player.gainGold(GOLD_AMT);
        CardCrawlGame.sound.play("GOLD_GAIN");
        flash();
    }

    @Override
    public String getButtonLabel() {
        return DESCRIPTIONS[2];
    }
}
