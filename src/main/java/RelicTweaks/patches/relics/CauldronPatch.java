package RelicTweaks.patches.relics;


import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.relics.Cauldron;

@SpirePatch2(clz = Cauldron.class, method = "onEquip")
public class CauldronPatch {

    @SpirePrefixPatch
    public static void addPotionSlot() {
        AbstractDungeon.player.potionSlots++;
        AbstractDungeon.player.potions.add(new PotionSlot(AbstractDungeon.player.potionSlots - 1));
    }

}
