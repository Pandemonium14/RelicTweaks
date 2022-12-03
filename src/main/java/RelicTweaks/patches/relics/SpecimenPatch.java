package RelicTweaks.patches.relics;

import RelicTweaks.RelicTweaksMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.relics.TheSpecimen;

@SpirePatch2(clz = TheSpecimen.class, method = "onMonsterDeath")
public class SpecimenPatch {

    private static final int POISON_AMOUNT = 6;

    @SpirePrefixPatch
    public static SpireReturn tweak(TheSpecimen __instance, AbstractMonster m) {
        if (!RelicTweaksMod.getConfigForKey("Specimen")) return SpireReturn.Continue();
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if (!mo.isDeadOrEscaped() && !mo.isDying) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, AbstractDungeon.player, new PoisonPower(mo, AbstractDungeon.player, POISON_AMOUNT)));
            }
        }
        return SpireReturn.Return();
    }
}
