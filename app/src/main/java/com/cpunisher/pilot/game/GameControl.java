package com.cpunisher.pilot.game;

import android.hardware.SensorEvent;
import com.cpunisher.pilot.entity.*;
import com.cpunisher.pilot.entity.item.Item;
import com.cpunisher.pilot.entity.item.Power;
import com.cpunisher.pilot.util.RankHelper;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class GameControl {

    private GameView gameView;
    private int score;
    private int level;
    private Player player;
    private List<Enemy> enemies;
    private List<Bullet> enemyBullets;
    private List<Bullet> playerBullets;
    private List<Item> items;
    private boolean over;

    public GameControl(GameView gameView) {
        this.gameView = gameView;
    }

    public void restart() {
        player = new Player(this);
        enemies = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        playerBullets = new LinkedList<>();
        items = new LinkedList<>();
        Item.registerItems();
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
        if (ticks % GameConstSettings.PLAYER_SHOOT == 0)
            getPlayer().shoot();
        player.move(ticks);

        Iterator<Item> iteratorItems = getItems().iterator();
        Entity item;
        while (iteratorItems.hasNext()) {
            item = iteratorItems.next();
            if (!item.isLiving()) {
                iteratorItems.remove();
                continue;
            }
            if (item.isCollisionWith(player)) {
                item.collisionWith(player);
            }
            item.move(ticks);
        }

        //敌人相关
        Iterator<Enemy> iteratorEnemies = getEnemies().iterator();
        Enemy enemy;
        while (iteratorEnemies.hasNext()) {
            enemy = iteratorEnemies.next();
            if (!enemy.isLiving()) {
                iteratorEnemies.remove();
                continue;
            }
            if (ticks % GameConstSettings.ENEMY_SHOOT == 0)
                enemy.shoot();
            enemy.move(ticks);
        }

        if (ticks % GameConstSettings.GENERATE_ENEMY == 0)
            generateEnemy();

        if (ticks % GameConstSettings.GENERATE_ITEM == 0)
            items.add(Item.generateRandomItem(this));

        //System.out.println("Enemies:" + enemies.size() + "  Enemy Bullets:" + enemyBullets.size() + "  Player Bullets:" + playerBullets.size());
    }

    public void generateEnemy() {
        enemies.add(new Enemy(level + 1, this));
    }

    public int getScore() {
        return score;
    }

    public void addScore(int inc) {
        score += inc;
        level = score / GameConstSettings.SCORE_EACH_LEVEL;
    }

    public int getLevel() {
        return level;
    }

    public void gameOver() {
        over = true;
        RankHelper.recordScore(gameView.getContext(), score);
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

    public List<Item> getItems() {
        return items;
    }

    public GameView getGameView() {
        return gameView;
    }
}
