package RelicTweaks;

import RelicTweaks.relics.TweakedBlueCandle;
import RelicTweaks.relics.TweakedDarkstonePeriapt;
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
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BlueCandle;
import com.megacrit.cardcrawl.relics.DarkstonePeriapt;
import com.megacrit.cardcrawl.relics.JuzuBracelet;
import com.megacrit.cardcrawl.vfx.combat.HealEffect;
import sun.security.provider.SHA;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings({"unused", "WeakerAccess"})
@SpireInitializer
public class RelicTweaksMod implements PostInitializeSubscriber, EditStringsSubscriber, EditRelicsSubscriber  {

    public static final String modID = "relictweaks"; //TODO: Change this.

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

    @Override
    public void receivePostInitialize() {



        removeRelic(new DarkstonePeriapt());
        removeRelic(new BlueCandle());
    }


    @Override
    public void receiveEditStrings() {
        BaseMod.loadCustomStringsFile(RelicStrings.class, makePath("localization/eng/RelicStrings.json"));
    }

    @Override
    public void receiveEditRelics() {

        BaseMod.addRelic(new TweakedDarkstonePeriapt(), RelicType.SHARED);
        BaseMod.addRelic(new TweakedBlueCandle(), RelicType.SHARED);


    }

    //hooks

    public static void onEnterRoom(MapRoomNode node) {
        if (node == null) return;
        for (AbstractRelic relic : AbstractDungeon.player.relics) {
            if ("?".equals(node.getRoomSymbol(true)))
            if (relic instanceof JuzuBracelet) {
                AbstractDungeon.effectsQueue.add(new HealEffect(relic.currentX, relic.currentY, 2));
            }
        }
    }

    public static void removeRelic(AbstractRelic relic) {
        //from functional hashmap
        BaseMod.removeRelic(relic);

        //from Compendium lists (doesn't work yet)
        switch (relic.tier) {
            case COMMON:
                ArrayList<AbstractRelic> listC = RelicLibrary.commonList;
                listC.remove(relic);
                break;
            case UNCOMMON:
                BaseMod.logger.info("Removing an uncommon relic???.???????????");
                ArrayList<AbstractRelic> listU = RelicLibrary.uncommonList;
                listU.remove(relic);
                break;
            case RARE:
                ArrayList<AbstractRelic> listR = RelicLibrary.rareList;
                listR.remove(relic);
                break;
            case SHOP:
                ArrayList<AbstractRelic> listSh = RelicLibrary.shopList;
                listSh.remove(relic);
                break;
            case SPECIAL:
                ArrayList<AbstractRelic> listSp = RelicLibrary.specialList;
                listSp.remove(relic);
                break;
        }
    }

}
