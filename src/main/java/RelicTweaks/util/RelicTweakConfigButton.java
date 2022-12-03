package RelicTweaks.util;

import RelicTweaks.RelicTweaksMod;
import basemod.ModLabel;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.ModToggleButton;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.megacrit.cardcrawl.helpers.FontHelper;

public class RelicTweakConfigButton extends ModLabeledToggleButton {

    public RelicTweakConfigButton(String label, String configKey, float x, float y, ModPanel panel) {
        super(label, x, y, Color.WHITE, getFont(), RelicTweaksMod.getConfigForKey(configKey), panel, (ModLabel) ->{},
                (button) -> {
                    RelicTweaksMod.configMap.put(configKey, button.enabled);
                    RelicTweaksMod.saveConfig(RelicTweaksMod.modConfig, configKey);
                });
    }

    private static BitmapFont getFont() {
        return FontHelper.charDescFont;
    }
}
