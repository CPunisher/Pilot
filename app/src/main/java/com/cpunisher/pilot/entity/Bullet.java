package com.cpunisher.pilot.entity;

import android.graphics.Canvas;
import com.cpunisher.pilot.game.GameConstSettings;
import com.cpunisher.pilot.game.GameControl;
import com.cpunisher.pilot.R;

public class Bullet extends Entity {

    /** -1为上 1为下 **/
    private int direction;

    public Bullet(int posX, int posY, int direction, GameControl gameControl) {
        super(30, 90, gameControl);
        this.bindTexture(R.drawable.bullet);
        this.direction = direction;
        this.posX = posX;
        this.posY = posY;
    }

    @Override
    public void move(long ticks) {
        this.setPosY(this.posY + GameConstSettings.BULLET_SPEED * direction);
    }

    @Override
    public void draw(Canvas canvas, long ticks) {
        drawTexture(canvas);
    }

    @Override
    public void collisionWith(Entity target) {
        if (this.direction == -1 && target instanceof Enemy) {
            Enemy enemy = (Enemy) target;
            enemy.decHeart(1);
            this.setDead();
        }
        if (this.direction == 1 && target instanceof Player) {
            Player player = (Player) target;
            if (!player.isGodMode()) {
                gameControl.decHeart(1);
                this.setDead();
            }
        }
    }

    @Override
    public void setPosY(int posY) {
        if (!this.isOutY())
            this.posY = posY;
        else
            this.setDead();
    }
}
