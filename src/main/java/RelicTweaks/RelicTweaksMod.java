package RelicTweaks;

import RelicTweaks.relics.TweakedBlueCandle;
import RelicTweaks.relics.TweakedDarkstonePeriapt;
import RelicTweaks.relics.TweakedNilrysCodex;
import basemod.BaseMod;
import basemod.ReflectionHacks;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.vfx.combat.HealEffect;
import sun.security.provider.SHA;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings({"unused", "WeakerAccess"})
@SpireInitializer
public class RelicTweaksMod implements PostInitializeSubscriber, EditStringsSubscriber, EditRelicsSubscriber  {

    public static final String modID = "relictweaks"; //TODO: Change this.

    public static final ArrayList<String> relicsToRemove = getRelicsToRemove();

    public static String makeID(String idText) {
        return modID + ":" + idText;
    }

    public RelicTweaksMod() {
        BaseMod.subscribe(this);
    }

    public static String makePath(String resourcePath) {
        return modID + "Resources/" + resourcePath;
    }

    public static String makeImagePath(String resourcePath) {
        return modID + "Resources/images/" + resourcePath;
    }

    public static void initialize() {
        RelicTweaksMod thismod = new RelicTweaksMod();
    }

    private static ArrayList<String> getRelicsToRemove() {
        ArrayList<String> result = new ArrayList<>();
        result.add(DarkstonePeriapt.ID);
        result.add(BlueCandle.ID);
        result.add(NilrysCodex.ID);
        return result;
    }

    @Override
    public void receivePostInitialize() {



        removeRelic(new DarkstonePeriapt());
        removeRelic(new BlueCandle());
        removeRelic(new NilrysCodex());
    }


    @Override
    public void receiveEditStrings() {
        BaseMod.loadCustomStringsFile(RelicStrings.class, makePath("localization/eng/RelicStrings.json"));
    }

    @Override
    public void receiveEditRelics() {

        BaseMod.addRelic(new TweakedDarkstonePeriapt(), RelicType.SHARED);
        BaseMod.addRelic(new TweakedBlueCandle(), RelicType.SHARED);
        BaseMod.addRelic(new TweakedNilrysCodex(), RelicType.SHARED);

    }

    //hooks

    public static void onEnterRoom(MapRoomNode node) {
        if (node == null) return;
        for (AbstractRelic relic : AbstractDungeon.player.relics) {
            if ("?".equals(node.getRoomSymbol(true)))
            if (relic instanceof JuzuBracelet) {
                AbstractDungeon.player.heal(4, true);
            }
        }
    }

    public static void removeRelic(AbstractRelic relic) {
        //from functional hashmap
        BaseMod.removeRelic(relic);

    }

}
