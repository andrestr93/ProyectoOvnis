package com.example.proyectoovnis.Modelos;

import android.graphics.Canvas;
import android.graphics.Bitmap;

import com.example.proyectoovnis.Actividades.GameView;


public class Misil {
    private final int width;
    private final int height;
    private float x = (GameView.ejeX +40);
    private int y = GameView.alto-250;
    private GameView gameView;
    private Bitmap bmp;

    public Misil(GameView gameView, Bitmap bmp) {
        this.gameView = gameView;
        this.bmp = bmp;
        this.width = bmp.getWidth();
        this.height = bmp.getHeight();
    }


    public boolean isCollition(float x2, float y2) {
        return x2 > x && x2 < x + width && y2 > y && y2 < y + height;
    }

    private void update() {
        if (y > -140) {
            /*if (x > gameView.getWidth() - bmp.getWidth() - xSpeed) {
                xSpeed = -8;

            }
            if (x + xSpeed < 0) {
                xSpeed = 8;

            }
            x = x + xSpeed;*/
            y = y -35;

        } else {

        }
    }

    public void onDraw(Canvas canvas) {
        update();
        canvas.drawBitmap(bmp, x, y, null);
    }

    public int getY() {
        return y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}



