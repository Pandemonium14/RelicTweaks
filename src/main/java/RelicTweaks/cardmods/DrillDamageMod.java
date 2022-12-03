package RelicTweaks.cardmods;

import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class DrillDamageMod extends AbstractDamageModifier {
    @Override
    public AbstractDamageModifier makeCopy() {
        return new DrillDamageMod();
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCreature target, AbstractCard card) {
        if (target.currentBlock > 0 && type == DamageInfo.DamageType.NORMAL) {
            return damage * 1.5f;
        } else {
            return damage;
        }
    }
}
