package com.example.proyectoovnis.Modelos;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.proyectoovnis.Actividades.GameView;


public class Ovni {

    private   int width ;
    private  int  height;
    private int x =(int) (Math.random()*(GameView.ancho));
    private int y = (int) (Math.random()*100);
    private int xSpeed = 5;
    private Bitmap bmp;
    private GameView gameView;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Bitmap getBmp() {
        return bmp;
    }

    public Ovni(GameView gameView  , Bitmap bmp) {
        this.gameView = gameView;
        this.bmp = bmp;
        this.width = bmp.getWidth() ;
        this.height = bmp.getHeight() ;
    }
    private void update() {
        if (x > gameView.getWidth() - width - xSpeed) {
            xSpeed = -5;
        }
        if (x + xSpeed < 0) {
            xSpeed = 5;
        }
        x = x + xSpeed;
        y = y + 15;

    }



    public void onDraw(Canvas canvas) {
        update();
        canvas.drawBitmap(bmp, x, y, null);
    }

    public boolean isCollition(float x2, float y2) {
        return x2 > x && x2 < x + width && y2 > y && y2 < y + height;
    }


}