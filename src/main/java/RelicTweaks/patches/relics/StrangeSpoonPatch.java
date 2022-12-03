package RelicTweaks.patches.relics;


import RelicTweaks.RelicTweaksMod;
import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.StrangeSpoon;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

@SpirePatch2(clz = UseCardAction.class, method = "update")
public class StrangeSpoonPatch {

    @SpireInstrumentPatch
    public static ExprEditor checkForModifier() {
        return new ExprEditor() {
            public void edit(MethodCall m)
                    throws CannotCompileException
            {
                if (m.getClassName().equals(Random.class.getName())
                        && m.getMethodName().equals("randomBoolean"))
                    m.replace("{$_ = RelicTweaks.patches.relics.StrangeSpoonPatch.trigger();}");
            }
        };
    }

    public static boolean trigger() {
        if (!RelicTweaksMod.getConfigForKey("Strange Spoon")) return AbstractDungeon.cardRandomRng.randomBoolean();
        AbstractRelic spoon = AbstractDungeon.player.getRelic(StrangeSpoon.ID);
        if (spoon != null) {
            if (spoon.counter < 2) {
                spoon.counter ++;
                return true;
            }
        }
        return false;
    }
}
