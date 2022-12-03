package RelicTweaks.patches.relics;


import RelicTweaks.RelicTweaksMod;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepEffect;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch2(clz = CampfireSleepEffect.class, method = "update")
public class DreamCatcherPatch {


    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {

            Matcher finalMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "isEmpty");

            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
        }
    }

    @SpireInsertPatch(locator = Locator.class, localvars = {"rewardCards"})
    public static void tweakDreamCatcher(CampfireSleepEffect __instance, ArrayList<AbstractCard> rewardCards) {
        if (!RelicTweaksMod.getConfigForKey("Dream Catcher")) return;
        for (AbstractCard c : rewardCards) {
            c.upgrade();
        }
    }
}
