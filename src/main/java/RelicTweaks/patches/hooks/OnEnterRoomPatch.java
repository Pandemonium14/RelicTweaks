package RelicTweaks.patches.hooks;

import RelicTweaks.RelicTweaksMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;

@SpirePatch2(clz = AbstractDungeon.class, method = "nextRoomTransition", paramtypez = {SaveFile.class})
public class OnEnterRoomPatch {


    @SpirePostfixPatch
    public static void relicTweaksOnEnterRoom() {
        RelicTweaksMod.onEnterRoom(AbstractDungeon.nextRoom);
    }

}
