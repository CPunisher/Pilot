package com.cpunisher.pilot.entity;

import android.graphics.*;
import com.cpunisher.pilot.game.GameControl;

public abstract class Entity {

    protected int posX, posY;
    protected boolean isDead;
    protected int width, height;
    protected GameControl gameControl;

    private Bitmap texture;

    public Entity(int width, int height, GameControl gameControl) {
        this.width = width;
        this.height = height;
        this.gameControl = gameControl;
    }

    public abstract void move(long ticks);

    public abstract void draw(Canvas canvas, long ticks);

    public abstract void collisionWith(Entity target);

    public boolean isCollisionWith(Entity target) {
        return Math.abs(this.posX - target.posX) <= (this.width +
                target.width) / 2 && Math.abs(this.posY - target.posY) <= (this.height + target.height) / 2;
    }

    protected void drawTexture(Canvas canvas) {
        canvas.drawBitmap(texture, new Rect(0, 0, texture.getWidth(), texture.getHeight()),
                new Rect(getLeft(), getTop(), getRight() ,getBottom()), null);
    }

    protected void bindTexture(int id) {
        texture = BitmapFactory.decodeResource(gameControl.getGameView().getResources(), id);
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosX(int posX) {
        if (posX - width / 2 >= 0 && posX + width / 2 <= gameControl.getGameView().getWidth())
            this.posX = posX;
    }

    public void setPosY(int posY) {
        if (posY - height / 2 >= 0 && posY + height / 2 <= gameControl.getGameView().getHeight())
            this.posY = posY;
    }

    public boolean isOutX() {
        return posX - width / 2 < 0 || posX + width / 2 > gameControl.getGameView().getWidth();
    }

    public boolean isOutY() {
        return posY - height / 2 < 0 || posY + height / 2 > gameControl.getGameView().getHeight();
    }

    public void setDead() {
        this.isDead = true;
    }

    public boolean isLiving() {
        return !isDead;
    }

    public int getLeft() { return posX - width / 2; }

    public int getRight() { return posX + width / 2; }

    public int getTop() { return posY - height / 2; }

    public int getBottom() { return posY + height / 2; }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
