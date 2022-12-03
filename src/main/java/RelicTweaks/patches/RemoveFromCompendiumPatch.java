package RelicTweaks.patches;


import RelicTweaks.RelicTweaksMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;

@SpirePatch2(clz = RelicLibrary.class, method ="addToTierList")
public class RemoveFromCompendiumPatch {

    @SpirePrefixPatch
    public static SpireReturn removeTweakedRelics(AbstractRelic relic) {
        ArrayList<String> list = RelicTweaksMod.getRelicsToRemove();
        if (list.contains(relic.relicId)) {
            return SpireReturn.Return();
        }
        return SpireReturn.Continue();
    }
}
