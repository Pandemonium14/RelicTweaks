package RelicTweaks.patches.relics;


import RelicTweaks.RelicTweaksMod;
import RelicTweaks.actions.MawBankEffect;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.MawBank;


public class MawBankPatch {

    @SpirePatch2(clz = MawBank.class, method = "onSpendGold")
    public static class OnSpendGold {


        @SpirePrefixPatch
        public static SpireReturn tweak(MawBank __instance) {
            if (!RelicTweaksMod.getConfigForKey("Maw Bank")) return SpireReturn.Continue();
            if (!__instance.usedUp) {
                AbstractDungeon.effectsQueue.add(new MawBankEffect(__instance));
            }
            return SpireReturn.Return();
        }
    }

    @SpirePatch2(clz = MawBank.class, method = "onEnterRoom")
    public static class OnEnterRoom {

        @SpirePrefixPatch
        public static void incrementCounter(MawBank __instance) {
            if (!RelicTweaksMod.getConfigForKey("Maw Bank")) return;
            __instance.counter += 12;
            if (__instance.counter % 2 != 0) __instance.counter ++;
        }
    }
}

