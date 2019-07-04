package com.cpunisher.pilot.game;

import android.hardware.SensorEvent;
import com.cpunisher.pilot.entity.Bullet;
import com.cpunisher.pilot.entity.Enemy;
import com.cpunisher.pilot.entity.Player;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class GameControl {

    private GameView gameView;
    private int score;
    private int heart;
    private Player player;
    private List<Enemy> enemies;
    private List<Bullet> enemyBullets;
    private List<Bullet> playerBullets;
    private boolean over;

    public GameControl(GameView gameView) {
        this.gameView = gameView;
        this.heart = 3;
    }

    public void restart() {
        player = new Player(this);
        enemies = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        playerBullets = new LinkedList<>();
    }

    public void pause() {
    }


    public void onSensorChanged(SensorEvent event) {
        if (player != null && !isOver()) {
            float rate = 2.5f;
            player.setPosX((int) (player.getPosX() - event.values[0] * rate));
            player.setPosY((int) (player.getPosY() + event.values[1] * rate));
        }
    }

    public void processEntities(long ticks) {
        Iterator<Bullet> iteratorBullets = getPlayerBullets().iterator();
        Bullet bullet;

        //我方子弹
        while (iteratorBullets.hasNext()) {
            bullet = iteratorBullets.next();
            if (!bullet.isLiving()) {
                iteratorBullets.remove();
                continue;
            }

            bullet.move(ticks);
            for (Enemy e : enemies) {
                if (bullet.isCollisionWith(e)) {
                    bullet.collisionWith(e);
                }
            }
        }

        //敌方子弹
        iteratorBullets = getEnemyBullets().iterator();
        while (iteratorBullets.hasNext()) {
            bullet = iteratorBullets.next();
            if (!bullet.isLiving()) {
                iteratorBullets.remove();
                continue;
            }
            bullet.move(ticks);
            if (bullet.isCollisionWith(player)) {
                bullet.collisionWith(player);
            }
        }

        //玩家
        if (ticks % 20 == 0)
            getPlayer().shoot();
        player.move(ticks);

        //敌人相关
        Iterator<Enemy> iteratorEnemies = getEnemies().iterator();
        Enemy enemy;
        while (iteratorEnemies.hasNext()) {
            enemy = iteratorEnemies.next();
            if (!enemy.isLiving()) {
                iteratorEnemies.remove();
                continue;
            }
            if (ticks % 40 == 0)
                enemy.shoot();
            enemy.move(ticks);
        }

        if (ticks % 75 == 0)
            generateEnemy();

        //System.out.println("Enemies:" + enemies.size() + "  Enemy Bullets:" + enemyBullets.size() + "  Player Bullets:" + playerBullets.size());
    }

    public void generateEnemy() {
        enemies.add(new Enemy(GameControl.this));
    }

    public int getScore() {
        return score;
    }

    public void addScore(int inc) {
        score += inc;
    }

    public int getHeart() {
        return heart;
    }

    public void decHeart() {
        heart--;
        if (heart <= 0) {
            over = true;
        }
        player.setGodMode(100);
    }

    public boolean isOver() {
        return over;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Bullet> getPlayerBullets() {
        return playerBullets;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<Bullet> getEnemyBullets() {
        return enemyBullets;
    }

    public GameView getGameView() {
        return gameView;
    }
}
