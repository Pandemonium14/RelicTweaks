package RelicTweaks.cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class NilryMod extends AbstractCardModifier {

    public static final String ID = "relicstweaks:NilryModifier";

    public NilryMod() {}

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.isEthereal = true;
        card.exhaust = true;
    }



    @Override
    public AbstractCardModifier makeCopy() {
        return new NilryMod();
    }
}
