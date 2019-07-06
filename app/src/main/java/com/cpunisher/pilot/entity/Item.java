package com.cpunisher.pilot.entity;

import android.graphics.Canvas;
import com.cpunisher.pilot.game.GameConstSettings;
import com.cpunisher.pilot.game.GameControl;

import java.util.Random;

public abstract class Item extends Entity {

    public Item(GameControl gameControl) {
        super(40, 40, gameControl);

        Random random = new Random();
        int x = (int) (random.nextFloat() * gameControl.getGameView().getWidth());
        this.setPosX(x);
        this.setPosY(0);
    }

    @Override
    public void move(long ticks) {
        this.setPosY(this.posY + GameConstSettings.ENEMY_SPEED);
    }

    @Override
    public void draw(Canvas canvas, long ticks) {
        drawTexture(canvas);
    }

    public abstract void collisionWithPlayer(Player player);

    @Override
    public void collisionWith(Entity target) {
        if (target instanceof Player) {
            Player player = (Player) target;
            collisionWithPlayer(player);
            this.setDead();
        }
    }

    @Override
    public void setPosY(int posY) {
        if (posY + height / 2 <= gameControl.getGameView().getHeight())
            this.posY = posY;
        else
            this.setDead();
    }
}
