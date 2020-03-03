package com.example.proyectoovnis.Actividades;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class ActividadPrincipal extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

}




