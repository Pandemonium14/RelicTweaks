package RelicTweaks.actions;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.MawBank;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class MawBankEffect extends AbstractGameEffect {

    private final MawBank b;

    public MawBankEffect(MawBank bank) {
        b = bank;
    }

    @Override
    public void update() {
        if (b.counter > AbstractDungeon.player.gold) {
            b.setCounter(-2);
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {

    }

    @Override
    public void dispose() {

    }
}
