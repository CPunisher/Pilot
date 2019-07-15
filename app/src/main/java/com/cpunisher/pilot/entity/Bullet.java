package com.cpunisher.pilot.entity;

import android.graphics.Canvas;
import com.cpunisher.pilot.game.GameConstSettings;
import com.cpunisher.pilot.game.GameControl;
import com.cpunisher.pilot.R;

public class Bullet extends Entity {

    /** -1为上 1为下 **/
    private int direction;
    private int power;

    public Bullet(int posX, int posY, int direction, int power, GameControl gameControl) {
        super(30, 90, gameControl);
        this.bindTexture(direction == -1 ? R.drawable.bullet_player : R.drawable.bullet_enemy);
        this.direction = direction;
        this.posX = posX;
        this.posY = posY;
        this.power = power;
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
            enemy.decHeart(power);
            this.setDead();
        }
        if (this.direction == 1 && target instanceof Player) {
            Player player = (Player) target;
            if (!player.isGodMode()) {
                player.decHeart(power);
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
