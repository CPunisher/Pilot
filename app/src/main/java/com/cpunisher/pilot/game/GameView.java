package com.cpunisher.pilot.game;

import android.content.Context;
import android.content.Intent;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import com.cpunisher.pilot.MainActivity;
import com.cpunisher.pilot.R;
import com.cpunisher.pilot.entity.Bullet;
import com.cpunisher.pilot.entity.Enemy;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private final Bitmap BACKGROUND = BitmapFactory.decodeResource(getResources(), R.drawable.universe);
    private final Bitmap HEART = BitmapFactory.decodeResource(getResources(), R.drawable.heart);

    private SurfaceHolder surfaceHolder;
    private Canvas canvas;
    private boolean isDrawing;

    // 绘制背景的移动
    private Matrix matrix;
    private Bitmap bg;
    private int startY;

    private GameControl gameControl;
    private long preTime;
    private long ticks;

    private Paint paint;

    public GameView(Context context) {
        super(context, null);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);

        this.post(new Runnable() {
            @Override
            public void run() {
                float scaleX = 1.0f * GameView.this.getWidth() / BACKGROUND.getWidth();
                matrix = new Matrix();
                matrix.setScale(scaleX, 1.0f);
                startY = BACKGROUND.getHeight() - GameView.this.getHeight();
            }
        });

        gameControl = new GameControl(this);
        paint = new Paint();

        initView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isDrawing = true;

        gameControl.restart();
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isDrawing = false;
        gameControl.pause();
    }

    private void draw(long ticks) {
        try {
            //绘制背景
            canvas = surfaceHolder.lockCanvas();
            bg = Bitmap.createBitmap(BACKGROUND, 0, startY, BACKGROUND.getWidth(), this.getHeight(), matrix, false);
            canvas.drawBitmap(bg, 0, 0, null);

            //绘制玩家、敌人和子弹
            gameControl.getPlayer().draw(canvas, ticks);
            for (Enemy enemy : gameControl.getEnemies()) {
                if (enemy.isLiving()) {
                    enemy.draw(canvas, ticks);
                }
            }
            for (Bullet bullet : gameControl.getEnemyBullets()) {
                if (bullet.isLiving()) {
                    bullet.draw(canvas, ticks);
                }
            }
            for (Bullet bullet : gameControl.getPlayerBullets()) {
                if (bullet.isLiving()) {
                    bullet.draw(canvas, ticks);
                }
            }

            if (!gameControl.isOver()) {
                //绘制计分板
                paint.setAntiAlias(true);
                paint.setColor(Color.WHITE);
                paint.setTextSize(50.0f);
                canvas.drawText("分数:" + gameControl.getScore(), 10.0f, 50.0f, paint);

                //绘制生命爱心
                for (int i = 0; i < gameControl.getHeart(); i++) {
                    canvas.drawBitmap(HEART, 10.0f + (HEART.getWidth() + 10.0f) * i, 75.0f, null);
                }

            } else {
                drawDeadView(canvas, gameControl.getScore());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void run() {
        while (isDrawing) {
            long currentTime = System.currentTimeMillis();
            // 1tick = 20ms
            if (currentTime - preTime <= 20) {
                try {
                    Thread.sleep(currentTime - preTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                preTime = currentTime;
            }

            ticks++;

            draw(ticks);

            if (!gameControl.isOver()) {
                gameControl.processEntities(ticks);

                // 移动背景
                if (startY < 3) {
                    startY = BACKGROUND.getHeight() - this.getHeight();
                } else {
                    startY -= 3;
                }
            }

            preTime = currentTime;
        }
    }

    private void drawDeadView(Canvas canvas, int score) {
        Paint.Align align = paint.getTextAlign();
        paint.setTextSize(200.0f);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Game Over", this.getWidth() / 2, this.getHeight() / 3, paint);
        paint.setTextSize(100.0f);
        canvas.drawText("分数:" + score, this.getWidth() / 2, this.getHeight() / 3 + 100.0f, paint);
        paint.setTextSize(75.0f);
        canvas.drawText("触摸屏幕返回标题界面", this.getWidth() / 2, this.getHeight() / 3 * 2, paint);

        paint.setTextAlign(align);
    }

    private void restart(View v) {

    }

    private void returnToTitle(View v) {

    }

    private void initView() {
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        setFocusable(true);
        setKeepScreenOn(true);
        setFocusableInTouchMode(true);
    }

    public GameControl getGameControl() {
        return gameControl;
    }
}
