package RelicTweaks.patches.relics;


import RelicTweaks.RelicTweaksMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Matryoshka;

@SpirePatch2(clz = Matryoshka.class, method = "onChestOpen")
public class MatryoshkaPatch {

    @SpirePrefixPatch
    public static SpireReturn tweak(AbstractRelic __instance, boolean bossChest) {
        if (!RelicTweaksMod.getConfigForKey("Matryoshka")) return SpireReturn.Continue();
        if (!bossChest && __instance.counter > 0) {// 22 23
            --__instance.counter;// 24
            __instance.flash();// 26
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, __instance));// 27
            int roll = AbstractDungeon.relicRng.random(99);
            if (roll < 49) {// 29
                AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractRelic.RelicTier.COMMON);// 30
            } else if (roll < 84){
                AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractRelic.RelicTier.UNCOMMON);// 32
            } else {
                AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractRelic.RelicTier.RARE);
            }

            if (__instance.counter == 0) {// 34
                __instance.setCounter(-2);// 35
                __instance.description = __instance.DESCRIPTIONS[2];// 36
            } else {
                __instance.description = __instance.DESCRIPTIONS[1];// 38
            }
        }


        return SpireReturn.Return();
    }
}
