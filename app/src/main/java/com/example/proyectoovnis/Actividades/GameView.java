package com.example.proyectoovnis.Actividades;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.proyectoovnis.HiloEjecutor.GameLoopThread;
import com.example.proyectoovnis.Modelos.Misil;
import com.example.proyectoovnis.Modelos.Nave;
import com.example.proyectoovnis.Modelos.Ovni;
import com.example.proyectoovnis.R;

import java.util.ArrayList;


public class GameView extends SurfaceView implements SensorEventListener {

    private MediaPlayer sonidoempieza;


    private ArrayList<Ovni> listaovnis = new ArrayList<Ovni>();
    private ArrayList<Misil> listamisiles = new ArrayList<>();
    private Bitmap bmpnave;
    private Bitmap bmpovnis;
    private Bitmap bmpmisil;
    public static  Boolean comprueba = false;
    private Nave nave;
    public static float ejeX = 0;
    public static String X;
    private int borde = 12;
    private SurfaceHolder holder;
    public static int alto;
    int tamanio = 60;
    public static int ancho;
    private SensorManager smAdministrador;
    private GameLoopThread gameLoopThread;
    private int x = 0;
    private int xSpeed = 10;
    private long lastClick;
    private SoundPool soundPool;
    private int idDisparo;
    private   int score = 0;

    public GameView(final Context context) {
        super(context);
        principio(context);
        gameLoopThread = new GameLoopThread(this);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                gameLoopThread.setRunning(false);
                while (retry) {
                    try {
                        gameLoopThread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                    }
                }
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                gameLoopThread.setRunning(true);
                comprueba = false;
                gameLoopThread.start();


            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
            }
        });


        dibujo();

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        pulsacion();

        return super.onTouchEvent(event);
    }

    @Override
    public  void onDraw(Canvas canvas) {
        mecanicajuego(canvas);


    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        movimientosensores(event);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    // METODOS DE LA CLASE PRINCIPAL


    public void principio(Context context) {

        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        idDisparo = soundPool.load(context, R.raw.disparo, 0);
        sonidoempieza = MediaPlayer.create(getContext(), R.raw.galaxia);
        sonidoempieza.start();
         smAdministrador = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        Sensor snsRotacion = smAdministrador.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        smAdministrador.registerListener(this, snsRotacion, SensorManager.SENSOR_DELAY_FASTEST);
        alto = getResources().getDisplayMetrics().heightPixels;
        ancho = getResources().getDisplayMetrics().widthPixels;


    }


    public void dibujo() {


        // dibujamos los bitmap de nave y ovnis
        bmpnave = BitmapFactory.decodeResource(getResources(), R.drawable.dibujonave);
        bmpovnis = BitmapFactory.decodeResource(getResources(), R.drawable.navestart);
        bmpmisil = BitmapFactory.decodeResource(getResources(), R.drawable.misil);

        nave = new Nave(this, bmpnave);


        // rellena el array con 10 ovnis
        for (int i = 0; i < 10; i++) {

            listaovnis.add(new Ovni(this, bmpovnis));


        }


    }

    public void movimientosensores(SensorEvent event) {


        ejeX -= event.values[0];
        X = Float.toString(ejeX);

        if (ejeX < (tamanio - 70)) {
            ejeX = (tamanio - 70);
        } else if (ejeX > (ancho - (tamanio + 150))) {
            ejeX = ancho - (tamanio + 150);

        }
        invalidate();


    }


    public void pulsacion() {

            soundPool.play(idDisparo, 1, 1, 1, 1, 1);
        if (System.currentTimeMillis() - lastClick > 200) {
            lastClick = System.currentTimeMillis();
            listamisiles.add(new Misil(this, bmpmisil));
        }

    }

    public void mecanicajuego(Canvas canvas) {
        if (x == getWidth() - bmpnave.getWidth()) {
            xSpeed = -1;
        }
        if (x == 0) {
            xSpeed = 1;
        }
        x = x + xSpeed;
        canvas.drawColor(Color.GRAY);

        Paint pincelfinjuego = new Paint();
        Paint pincelpuntos = new Paint();
        pincelpuntos.setColor(Color.GREEN);
        pincelpuntos.setTextSize(50);
        Log.e("medida", "" + ancho);
        canvas.drawText("SCORE " + score, ancho - 1350, alto - 2400, pincelpuntos);

        for (int i = 0; i < listaovnis.size(); i++) {
            listaovnis.get(i).onDraw(canvas);
        }


        if (listaovnis.size() != 0) {
            if (listamisiles.size() != 0) {
                for (int j = 0; j < listamisiles.size(); j++) {
                    int b = 0;

                    listamisiles.get(j).onDraw(canvas);
                    int tamanioarray = listaovnis.size();

                    for (int i = tamanioarray - 1; i >= 0; i--) {
                        Ovni ovni = listaovnis.get(i);


                        if (ovni.isCollition(listamisiles.get(j).getX(), listamisiles.get(j).getY())) {
                            listaovnis.remove(ovni);
                            score = score + 15;


                            listaovnis.add(new Ovni(this, bmpovnis));


                        }

                        if (listaovnis.get(i).getY() > alto - 200) {
                            listaovnis.remove(i);
                            listaovnis.add(new Ovni(this, bmpovnis));
                        }

                    }

                    if (listamisiles.get(j).getY() < 0) {
                        listamisiles.remove(listamisiles.get(j));
                    }
                }


            }
            for (int i = 0; i < listaovnis.size(); i++) {
                Ovni ovni = listaovnis.get(i);
                if (ovni.isCollition(nave.getX(), nave.getY())) {
                    pincelfinjuego.setColor(Color.GREEN);
                    pincelfinjuego.setTextSize(100);
                    sonidoempieza.stop();
                    canvas.drawText("GAME OVER", ancho - 1100, alto - 1500, pincelfinjuego);
                    canvas.drawText(MainActivity.nombre.getText().toString() + " has obtenido " + score + " puntos", ancho - 1450, alto - 1200, pincelfinjuego);
                    gameLoopThread.setRunning(false);
                    comprueba = true;


                }
            }

            nave.onDraw(canvas);


        }


        if (comprueba== true){
            System.out.println("---------parado");
            sonidoempieza.stop();
            soundPool.stop(idDisparo);
            smAdministrador.unregisterListener(this);
            gameLoopThread.setRunning(false);

        }







    }


}


