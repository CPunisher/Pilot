package com.cpunisher.pilot.entity;

import android.graphics.Canvas;
import com.cpunisher.pilot.game.GameControl;
import com.cpunisher.pilot.R;

public class Player extends Entity {

    private int godModeCount;

    public Player(GameControl gameControl) {
        super(gameControl.getGameView().getWidth() / 12, gameControl.getGameView().getHeight() / 9, gameControl);
        this.bindTexture(R.drawable.player);
        this.setPosX(gameControl.getGameView().getWidth() / 2);
        this.setPosY(gameControl.getGameView().getHeight() * 2 / 3);
    }

    @Override
    public void draw(Canvas canvas, long ticks) {
        if (!isGodMode() || ticks % 15 != 0) {
            drawTexture(canvas);
        }
        if (isGodMode()) {
            godModeCount--;
        }
    }

    @Override
    public void move(long ticks) {

    }

    @Override
    public void collisionWith(Entity target) {

    }

    public void shoot() {
        gameControl.getPlayerBullets().add(new Bullet(getPosX(), getPosY(), -1, gameControl));
    }

    public boolean isGodMode() {
        return godModeCount > 0;
    }

    public void setGodMode(int dur) {
        this.godModeCount = dur;
    }
}
