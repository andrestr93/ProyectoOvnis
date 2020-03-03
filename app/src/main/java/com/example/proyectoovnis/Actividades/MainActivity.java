package com.example.proyectoovnis.Actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.proyectoovnis.HiloEjecutor.GameLoopThread;
import com.example.proyectoovnis.R;

public class MainActivity extends AppCompatActivity {


    public SharedPreferences sp;
    public final String key = "keynombre";
    public static EditText nombre;
    CheckBox chequea;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        chequea = findViewById(R.id.check);
        nombre = findViewById(R.id.edittitulo);
        sp = getSharedPreferences("datos", Context.MODE_PRIVATE);
        nombre.setText(sp.getString("nombre", ""));


    }



    public void lanzarJuego(View view) {


        iniciajuego();
        if (chequea.isChecked()) {
            sp = getSharedPreferences("datos", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("nombre", nombre.getText().toString());
            editor.commit();


        }

    }


    public void iniciajuego() {
        Intent i = new Intent(this, ActividadPrincipal.class);
        startActivity(i);

    }







    /*
        public void aceptar(View v) {

         Para editar los valores de las preferencias
    sp = PreferenceManager.getDefaultSharedPreferences(this);
    {
        if (chequea.isChecked()) {
            sp = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor edit = sp.edit();
            edit.putString("prefeuser", user.getText().toString());
            user.setEnabled(false);
            chequea.setEnabled(false);
            edit.commit(); //actualiza las preferencias

        }else{


        }


    }
}






     */


}
