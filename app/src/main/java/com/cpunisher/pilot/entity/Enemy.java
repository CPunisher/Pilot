package com.cpunisher.pilot.entity;

import android.graphics.Canvas;
import com.cpunisher.pilot.R;
import com.cpunisher.pilot.game.GameControl;

import java.util.Random;

public class Enemy extends Entity {

    private static final int SPEED = 3;

    public Enemy(GameControl gameControl) {
        super(gameControl.getGameView().getWidth() / 12, gameControl.getGameView().getHeight() / 9, gameControl);
        this.bindTexture(R.drawable.enemy);

        Random random = new Random();
        int x = (int) (random.nextFloat() * gameControl.getGameView().getWidth());
        this.setPosX(x);
        this.setPosY(0);
    }

    @Override
    public void move(long ticks) {
        this.setPosY(this.posY + SPEED);
    }

    @Override
    public void draw(Canvas canvas, long ticks) {
        drawTexture(canvas);
    }

    @Override
    public void collisionWith(Entity target) {

    }

    @Override
    public void setPosY(int posY) {
        if (posY + height / 2 <= gameControl.getGameView().getHeight())
            this.posY = posY;
        else
            this.setDead();
    }

    public void shoot() {
        gameControl.getEnemyBullets().add(new Bullet(getPosX(), getPosY(), 1, gameControl));
    }
}
