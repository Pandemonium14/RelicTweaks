package RelicTweaks;

import RelicTweaks.relics.TweakedHandDrill;
import RelicTweaks.relics.TweakedBlueCandle;
import RelicTweaks.relics.TweakedCeramicFish;
import RelicTweaks.relics.TweakedDarkstonePeriapt;
import RelicTweaks.relics.TweakedNilrysCodex;
import RelicTweaks.util.ImageHelper;
import RelicTweaks.util.RelicTweakConfigButton;
import basemod.BaseMod;
import basemod.ModPanel;
import basemod.ReflectionHacks;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.HealEffect;
import sun.security.provider.SHA;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.*;

@SuppressWarnings({"unused", "WeakerAccess"})
@SpireInitializer
public class RelicTweaksMod implements PostInitializeSubscriber, EditStringsSubscriber, EditRelicsSubscriber, OnStartBattleSubscriber, PostBattleSubscriber {

    public static final String modID = "relictweaks";

    public static SpireConfig modConfig;
    private static ArrayList<String> configKeyList = new ArrayList<>();
    public static HashMap<String, Boolean> configMap = new HashMap<>();

    public static String makeID(String idText) {
        return modID + ":" + idText;
    }

    public RelicTweaksMod() {
        BaseMod.subscribe(this);

        makeKeyList();

        Properties defaultConfig = new Properties();
        for (String key : configKeyList) {
            defaultConfig.put(key, true);
        }

        try {
            modConfig = new SpireConfig("RelicTweaks", "RelicTweaksConfig", defaultConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            modConfig.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String key : configKeyList) {
            if (!modConfig.has(key)) {
                modConfig.setBool(key, true);
            }
        }
        try {
            modConfig.save();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (String configKey : configKeyList) {
            if (modConfig.has(configKey)) {
                boolean value = modConfig.getBool(configKey);
                configMap.put(configKey, value);
            }
        }


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


    public static ArrayList<String> getRelicsToRemove() {
        ArrayList<String> result = new ArrayList<>();
        if (getConfigForKey("Darkstone Periapt")) result.add(DarkstonePeriapt.ID);
        if (getConfigForKey("Blue Candle")) result.add(BlueCandle.ID);
        if (getConfigForKey("Nilry's Codex")) result.add(NilrysCodex.ID);
        if (getConfigForKey("Ceramic Fish")) result.add(CeramicFish.ID);
        if (getConfigForKey("Hand Drill")) result.add(HandDrill.ID);
        return result;
    }

    @Override
    public void receivePostInitialize() {
        ModPanel panel = new ModPanel();

        float x = 450f;
        float y = 700f;
        for (String key : configKeyList) {
            RelicTweakConfigButton button = new RelicTweakConfigButton(key, key, x, y, panel);
            panel.addUIElement(button);
            y -= 50f;
            if (y <= 200) {
                x += 400f;
                y = 700f;
            }
        }
        BaseMod.registerModBadge(ImageMaster.loadImage(makeImagePath("ui/Badge.png")), "Relic Tweaks", "Pandemonium", "Tweaks some annoying or underwhelming relics from Vanilla", panel);


        if (getConfigForKey("Darkstone Periapt")) removeRelic(new DarkstonePeriapt());
        if (getConfigForKey("Blue Candle")) removeRelic(new BlueCandle());
        if (getConfigForKey("Nilry's Codex")) removeRelic(new NilrysCodex());
        if (getConfigForKey("Ceramic Fish")) removeRelic(new CeramicFish());
        if (getConfigForKey("Hand Drill")) removeRelic(new HandDrill());
    }


    @Override
    public void receiveEditStrings() {
        loadJsonStringsFromFileWithConfig(makePath("localization/eng/RelicStrings.json"));
    }

    @Override
    public void receiveEditRelics() {
        if (getConfigForKey("Hand Drill")) BaseMod.addRelic(new TweakedHandDrill(), RelicType.SHARED);
        if (getConfigForKey("Darkstone Periapt")) BaseMod.addRelic(new TweakedDarkstonePeriapt(), RelicType.SHARED);
        if (getConfigForKey("Blue Candle")) BaseMod.addRelic(new TweakedBlueCandle(), RelicType.SHARED);
        if (getConfigForKey("Nilry's Codex")) BaseMod.addRelic(new TweakedNilrysCodex(), RelicType.SHARED);
        if (getConfigForKey("Ceramic Fish")) BaseMod.addRelic(new TweakedCeramicFish(), RelicType.SHARED);
    }

    //hooks

    public static void onEnterRoom(MapRoomNode node) {
        if (!getConfigForKey("Juzu Bracelet")) return;
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

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        if (!getConfigForKey("Strange Spoon")) return;
        AbstractRelic spoon = AbstractDungeon.player.getRelic(StrangeSpoon.ID);
        if (spoon != null) {
            spoon.counter = 0;
        }
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        if (!getConfigForKey("Strange Spoon")) return;
        AbstractRelic spoon = AbstractDungeon.player.getRelic(StrangeSpoon.ID);
        if (spoon != null) {
            spoon.counter = -1;
        }
    }


    private static void makeKeyList() {
        configKeyList.clear();
        configKeyList.add("Ceramic Fish");
        configKeyList.add("Maw Bank");
        configKeyList.add("Cauldron");
        configKeyList.add("Blue Candle");
        configKeyList.add("Nilry's Codex");
        configKeyList.add("Darkstone Periapt");
        configKeyList.add("Juzu Bracelet");
        configKeyList.add("Matryoshka");
        configKeyList.add("Strike Dummy");
        configKeyList.add("Strange Spoon");
        configKeyList.add("Dream Catcher");
        configKeyList.add("Specimen");
        configKeyList.add("Hand Drill");
        configKeyList.add("Tiny Chest");
    }

    public static void saveConfig(SpireConfig config, String key) {
        try {
            config.setBool(key, getConfigForKey(key));
            config.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean getConfigForKey(String key) {
        return configMap.get(key);
    }


    private static void loadJsonStringsWithConfig(Type stringType, String jsonString) {
        BaseMod.logger.info("loadJsonStrings: " + stringType.getTypeName());
        HashMap typeTokens = ReflectionHacks.getPrivateStatic(BaseMod.class, "typeTokens");
        Type typeToken = (Type) typeTokens.get(stringType);
        String modName = "RelicTweaks";
        Map<String,RelicStrings> localizationStrings = ReflectionHacks.getPrivateStatic(LocalizedStrings.class, "relics");
        Map<String,RelicStrings> map = new HashMap(BaseMod.gson.fromJson(jsonString, typeToken));

        Map map2 = new HashMap();

        for (String k : map.keySet()) {
            String kNoSpaces = k.replace(" ","");
            RelicStrings strings = map.get(k);
            boolean shouldAdd = false;
            for (String configKey : configKeyList) {
                String configKeyNoSpaces = configKey.replace(" ","");
                if (kNoSpaces.contains(configKeyNoSpaces)) {
                    shouldAdd = getConfigForKey(configKey);
                    break;
                }
            }
            if (shouldAdd) localizationStrings.put(k,map.get(k));
        }
        ReflectionHacks.setPrivateStaticFinal(LocalizedStrings.class, "relics", localizationStrings);
    }

    private static void loadJsonStringsFromFileWithConfig(String path) {
        loadJsonStringsWithConfig(RelicStrings.class, Gdx.files.internal(path).readString(String.valueOf(StandardCharsets.UTF_8)));
    }
}
