package RelicTweaks.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public abstract class BaseRelic extends CustomRelic {


    public BaseRelic(String id, String imgName, RelicTier tier, LandingSound sfx) {
        super(id, imgName, tier, sfx);
    }

    protected static AbstractPlayer adp() {
        return AbstractDungeon.player;
    }
}