package com.curso.tinodeoz.portal_prueba;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class menu extends AppCompatActivity {
    TextView TV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        fuente();

        /*AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Importante");
        dialogo1.setMessage("? Acepta la ejecuci¨®n de este programa en modo prueba ?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                aceptar();
            }
        });
        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                cancelar();
            }
        });
        dialogo1.show();*/

    }

    private void aceptar() {
        Toast.makeText(getApplicationContext(),"Informacion ",Toast.LENGTH_LONG).show();

    }
    private void cancelar() {
        finish();
    }

    public void fuente(){

        TV = (TextView)findViewById(R.id.txtmazo);
        String font_path = "Fuentes/GaraSans.ttf";                                                                                     //donde tiene que buscar ) de nuetra fuente
        String font_path2 = "Fuentes/GoudySans-Italic.ttf";

        Typeface TF = Typeface.createFromAsset(getAssets(),font_path);
        Typeface TF1 = Typeface.createFromAsset(getAssets(),font_path2);
        TV.setTypeface(TF1);

    }

}
