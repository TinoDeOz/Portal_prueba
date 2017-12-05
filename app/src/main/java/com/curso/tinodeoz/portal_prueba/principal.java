package com.curso.tinodeoz.portal_prueba;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

public class principal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

       // String text = "<html><body style=\"text-align:justify; background-color:#72ffffff;\"> %s </body></html>";
        String text = "<html><body style=\"text-align:justify; font-size:14px; line-height:20px; background-color:#72ffffff;\"> %s </body></html>";

        String data = "";

        String info="El consejo de la Judicatura trabaja para acercar servicios de utilidad a la comunidad jurídica y a los justiciables. Es por ello que pone a su la aplicacion movil denominada APP-PJHIDALGO.<br>" +
                "<br>APP-PJHIDALGO le ofrece acceso a información de carácter informativo, relativa a los asuntos radicados en primera instancia como es la publicación electrónica de las listas de notificaciones de todos los juzgados del Estado, o las demás consultas que se ofrecen respecto de los doce juzgados que conocen de las materias civil y familiar de los distritus judiciales de Pachuca y Tulancingo. Para esta versión de la app es posible consultar datos de los asuntos radicados en las Salas de Segunda Instancia de las materias civil y familiar.<br>" +
                "<br>Esperando que los datos aquí presentados le sean de utilidad, sea usted bienvenido.<br>";

        WebView webView = (WebView) findViewById(R.id.WebView1);
        WebView webView2 = (WebView) findViewById(R.id.WebView2);
        webView.loadData(String.format(text, info), "text/html; charset=utf-8","UTF-8");
        webView2.loadData(String.format(text, data),  "text/html; charset=utf-8","UTF-8");

        //webView.setBackgroundColor(Color.parseColor("#42ffffff"));
        webView.setBackgroundColor(Color.parseColor("#00FFFFFF"));

    }

    public void ir_menu(View v) {
        Intent ini= new Intent(this, menu2.class);
        startActivity(ini);
    }


}
