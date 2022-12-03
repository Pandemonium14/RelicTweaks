package RelicTweaks.patches.relics;

import RelicTweaks.RelicTweaksMod;
import RelicTweaks.relics.TweakedNilrysCodex;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.CursedTome;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.relics.NilrysCodex;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch2(clz = CursedTome.class, method = "randomBook")
public class CursedTomePatch {

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            // finalMatcher is the line that we want to insert our patch before -
            // in this example we are using a `MethodCallMatcher` which is a type
            // of matcher that matches a method call based on the type of the calling
            // object and the name of the method being called. Here you can see that
            // we're expecting the `end` method to be called on a `SpireBatch`
            Matcher finalMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "get");

            // the `new ArrayList<Matcher>()` specifies the prerequisites before the line can be matched -
            // the LineFinder will search for all the prerequisites in order before it will match the finalMatcher -
            // since we didn't specify any prerequisites here, the LineFinder will simply find the first expression
            // that matches the finalMatcher.
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
        }
    }
    @SpireInsertPatch(locator = Locator.class, localvars = {"possibleBooks"})
    public static void tweakNilry(CursedTome __instance, ArrayList<AbstractRelic> possibleBooks) {
        if (!RelicTweaksMod.getConfigForKey("Nilry's Codex")) return;
        AbstractRelic nilry = null;
        AbstractRelic circlet = null;
        for (AbstractRelic r : possibleBooks) {
            if (r instanceof NilrysCodex) nilry = r;
            if (r instanceof Circlet) circlet = r;
        }
        if (nilry != null) possibleBooks.remove(nilry);
        if (circlet != null) possibleBooks.remove(circlet);

        if (!AbstractDungeon.player.hasRelic(TweakedNilrysCodex.ID)) {
            possibleBooks.add(RelicLibrary.getRelic(TweakedNilrysCodex.ID).makeCopy());
        }
        if (possibleBooks.size() == 0) possibleBooks.add(new Circlet());
    }
}
