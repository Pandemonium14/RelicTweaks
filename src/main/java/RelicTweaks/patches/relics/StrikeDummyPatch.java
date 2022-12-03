package RelicTweaks.patches.relics;


import RelicTweaks.RelicTweaksMod;
import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.relics.StrikeDummy;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

@SpirePatch2(clz = StrikeDummy.class, method = "atDamageModify")
public class StrikeDummyPatch {

    @SpirePostfixPatch
    public static float checkForCommon(AbstractCard c, float damage, float __result) {
        if (!RelicTweaksMod.getConfigForKey("Strike Dummy")) return __result;
        if (__result == damage) {
            return c.rarity == AbstractCard.CardRarity.COMMON ? damage + 3f : damage;
        } else {
            return __result;
        }
    }

}
