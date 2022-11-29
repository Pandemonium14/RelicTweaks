package RelicTweaks.patches.relics;


import RelicTweaks.cardmods.NilryMod;
import basemod.ReflectionHacks;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.HashMap;


@SpirePatch2(clz = AbstractCard.class, method = "renderCard")
public class NilryGlowPatch {

    private static float purpleTimer;
    private static final Color purple = Color.PURPLE.cpy();
    private static HashMap<String,Color> colors = new HashMap<>();
    private static final Color normalGlow = ReflectionHacks.getPrivateStatic(AbstractCard.class, "BLUE_BORDER_GLOW_COLOR");

    @SpirePrefixPatch
    public static void nilryGlow(AbstractCard __instance) {
        if (CardModifierManager.hasModifier(__instance, NilryMod.ID)) {
            Color originalGlow = __instance.glowColor;
            colors.put(__instance.toString(), originalGlow);
            purpleTimer += Gdx.graphics.getDeltaTime();


            if (originalGlow.equals(normalGlow)) {
                __instance.glowColor = purple;
            } else {
                Color newGlow = new Color(originalGlow);
                float purpleFactor = (MathUtils.cos(purpleTimer * 2f) + 1)/2f;
                newGlow.b = newGlow.b * (1 - purpleFactor) + purple.b * purpleFactor;
                newGlow.r = newGlow.r * (1 - purpleFactor) + purple.r * purpleFactor;
                newGlow.g = newGlow.g * (1 - purpleFactor) + purple.g * purpleFactor;
                __instance.glowColor = newGlow;
            }
        }
    }

    @SpirePostfixPatch
    public static void restoreGlowColor(AbstractCard __instance) {
        if (colors.containsKey(__instance.toString())) {
            __instance.glowColor = colors.remove(__instance.toString());
        }
    }
}
