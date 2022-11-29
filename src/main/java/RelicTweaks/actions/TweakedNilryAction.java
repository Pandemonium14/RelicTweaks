package RelicTweaks.actions;

import RelicTweaks.cardmods.NilryMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TweakedNilryAction extends AbstractGameAction {

    public TweakedNilryAction() {

    }

    @Override
    public void update() {
        AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat();
        CardModifierManager.addModifier(c, new NilryMod());
        c.upgrade();
        addToTop(new MakeTempCardInHandAction(c));
        isDone = true;
    }
}
