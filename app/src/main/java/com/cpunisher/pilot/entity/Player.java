package com.cpunisher.pilot.entity;

import android.graphics.Canvas;
import com.cpunisher.pilot.game.GameConstSettings;
import com.cpunisher.pilot.game.GameControl;
import com.cpunisher.pilot.R;

public class Player extends Entity {

    private int power;
    private int godModeCount;
    private int heart;

    public Player(GameControl gameControl) {
        super(gameControl.getGameView().getWidth() / 12, gameControl.getGameView().getHeight() / 9, gameControl);
        this.bindTexture(R.drawable.player);
        this.setPosX(gameControl.getGameView().getWidth() / 2);
        this.setPosY(gameControl.getGameView().getHeight() * 2 / 3);

        this.power = GameConstSettings.START_POWER;
        this.heart = GameConstSettings.START_HEART;
    }

    @Override
    public void draw(Canvas canvas, long ticks) {
        if (!isGodMode() || ticks % GameConstSettings.GOD_MODE_FLASH != 0) {
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
        gameControl.getPlayerBullets().add(new Bullet(getPosX(), getPosY(), -1, power, gameControl));
    }

    public void setPower(int power) {
        this.power = Math.min(power, GameConstSettings.MAX_POWER);
    }

    public int getPower() {
        return power;
    }

    public int getHeart() {
        return heart;
    }

    public void decHeart(int dec) {
        heart -= dec;
        if (heart <= 0) {
            gameControl.gameOver();
        }
        this.setPower(GameConstSettings.START_POWER);
        this.setGodMode(GameConstSettings.GOD_TICKS);
    }

    public void incHeart(int inc) {
        heart = Math.min(heart + inc, GameConstSettings.MAX_HEART);
    }

    public boolean isGodMode() {
        return godModeCount > 0;
    }

    public void setGodMode(int dur) {
        this.godModeCount = dur;
    }
}
