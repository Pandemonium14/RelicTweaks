package RelicTweaks.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BlueCandleAction extends AbstractGameAction {

    public BlueCandleAction() {

    }

    @Override
    public void update() {
        int nbCurses = 0;
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if (c.type == AbstractCard.CardType.CURSE) nbCurses++;
        }

        if (nbCurses > 0) {
            addToTop(new DamageAllEnemiesAction(AbstractDungeon.player, nbCurses, DamageInfo.DamageType.THORNS, AttackEffect.FIRE));
        }
        isDone = true;
    }
}
