package RelicTweaks.patches.relics;


import RelicTweaks.RelicTweaksMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.TinyChest;
import com.megacrit.cardcrawl.rewards.chests.AbstractChest;
import com.megacrit.cardcrawl.rewards.chests.LargeChest;

@SpirePatch2(clz = AbstractDungeon.class, method = "getRandomChest")
public class GetRandomChestPatch {

    @SpirePostfixPatch
    public static AbstractChest checkForTinyChest(AbstractChest __result) {
        if (AbstractDungeon.player.hasRelic(TinyChest.ID) && RelicTweaksMod.getConfigForKey("Tiny Chest")) {
            AbstractRelic tinyChest = AbstractDungeon.player.getRelic(TinyChest.ID);
            if ("?".equals(AbstractDungeon.currMapNode.getRoomSymbol(true)) && tinyChest.counter == 0) {
                return new LargeChest();
            }
        }
        return __result;
    }
}
