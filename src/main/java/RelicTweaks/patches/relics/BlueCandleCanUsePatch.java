package RelicTweaks.patches.relics;


import RelicTweaks.RelicTweaksMod;
import RelicTweaks.relics.TweakedBlueCandle;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.CURSE;

@SpirePatch2(clz = AbstractCard.class, method = "canUse")
public class BlueCandleCanUsePatch {

    @SpirePrefixPatch
    public static SpireReturn<Boolean> checkForTweakedBlueCandle(AbstractCard __instance, AbstractMonster m) {
        if (__instance.type == CURSE && AbstractDungeon.player.hasRelic(TweakedBlueCandle.ID)) {
            return SpireReturn.Return(__instance.hasEnoughEnergy() && __instance.cardPlayable(m));
        } else {
            return SpireReturn.Continue();
        }
    }
}
