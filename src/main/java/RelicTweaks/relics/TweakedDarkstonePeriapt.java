package RelicTweaks.relics;

import RelicTweaks.RelicTweaksMod;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.DarkstonePeriapt;

public class TweakedDarkstonePeriapt extends BaseRelic {
    public static final String ID = RelicTweaksMod.makeID("Darkstone Periapt");
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings(ID);

    public TweakedDarkstonePeriapt() {
        super(ID, "darkstone.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.MAGICAL);// 16
    }

    @Override
    public void atBattleStartPreDraw() {
        boolean flashed = false;
        for (AbstractCard c : adp().drawPile.group) {
            if (c.type == AbstractCard.CardType.CURSE) {
                addToTop(new DiscardSpecificCardAction(c, adp().drawPile));
                if (!flashed) flash();
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return strings.DESCRIPTIONS[0];
    }
}