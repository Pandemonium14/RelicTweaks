package RelicTweaks.patches;


import RelicTweaks.RelicTweaksMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;

@SpirePatch2(clz = RelicLibrary.class, method ="addToTierList")
public class RemoveFromCompendiumPatch {

    @SpirePrefixPatch
    public static SpireReturn removeTweakedRelics(AbstractRelic relic) {
        if (RelicTweaksMod.relicsToRemove.contains(relic.relicId)) {
            return SpireReturn.Return();
        }
        return SpireReturn.Continue();
    }
}
