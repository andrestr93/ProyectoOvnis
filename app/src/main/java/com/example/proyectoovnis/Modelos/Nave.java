package com.example.proyectoovnis.Modelos;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.proyectoovnis.Actividades.GameView;

public class Nave {


    private int width;
    private int height;
    private int x = (int) GameView.ejeX;
    private int y ;
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

    public Nave(GameView gameView, Bitmap bmp) {
        this.gameView = gameView;
        this.bmp = bmp;
        this.width = bmp.getWidth();
        this.height = bmp.getHeight();
        this.y = GameView.alto-bmp.getWidth();
    }

    private void update() {

        x = (int)GameView.ejeX ;


    }


    public void onDraw(Canvas canvas) {
        update();
        canvas.drawBitmap(bmp, x, y, null);
    }

    public boolean isCollition(float x2, float y2) {
        return x2 > x && x2 < x + width && y2 > y && y2 < y + height;
    }


}
