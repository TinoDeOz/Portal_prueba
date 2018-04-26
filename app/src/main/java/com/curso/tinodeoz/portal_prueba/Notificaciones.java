package com.curso.tinodeoz.portal_prueba;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;

import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.DatePicker;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

public class Notificaciones extends Fragment {

    private  int dia,mes,ano;

    Obj_enteros suma= new Obj_enteros();
    Datos datos,datos2;

    Connection connect;
    String ConnectionResult="";
    Boolean esSatisfactorio=false;

    TableLayout tabla;
    WebView web;
    Button nueva,salir;
    Button consulta, atras, siguiente;
    TextView txtdistrito,juzgado,materia,opcion1,opcion2,no_expediente,no_causa,txtfcha;
    EditText numero_expediente, numero_causa,fecha;
    Spinner  distrito,opc1,opc2;
    Spinner ACTOPAN,APAN,ATOTONILCO,HUEJUTLA,HUICHAPAN,IXMIQUILPAN,
            JACALA,METZTITLAN,MOLANGO, MIXQUIAHUALA,PACHUCA,TENANGO,
            TIZAYUCA,TULA,TULANCINGO;

    ProgressDialog pDialog;

    String[] string_distrito={"Selecciona Aqui:","ACTOPAN","APAN","HUEJUTLA DE REYES","HUICHAPAN DE VILLAGRAN","IXMIQUILPAN","MIXQUIAHUALA DE JUAREZ","PACHUCA DE SOTO ","TIZAYUCA","TULA DE ALLENDE","TULANCINGO DE BRAVO"};
    String[] string_actopan={"Selecciona Aqui:","PRIMERO CIVIL Y FAMILIAR ACTOPAN","SEGUNDO CIVIL Y FAMILIAR ACTOPAN","PENAL ACTOPAN."};
    String[] string_apan={"Selecciona Aqui:","PRIMERO CIVIL Y FAMILIAR APAN.","SEGUNDO CIVIL Y FAMILIAR APAN.","PENAL APAN."};
    String[] string_huejutla={"Selecciona Aqui:","CIVIL Y FAMILIAR HUEJUTLA","PENAL DE JALTOCAN EN HUEJUTLA"};
    String[] string_huichapan={"Selecciona Aqui:","CIVIL Y FAMILIAR HUICHAPAN","PENAL HUICHAPAN"};
    String[] string_ixmi={"Selecciona Aqui:","CIVIL Y FAMILIAR IXMIQUILPAN","PENAL IXMIQUILPAN"};
    String[] string_mixqui={"Selecciona Aqui:","CIVIL Y FAMILIAR MIXQUIAHUALA","PENAL MIXQUIAHUALA"};
    String[] string_pachuca={"Selecciona Aqui:","PRIMERO DE LO PENAL PACHUCA","SEGUNDO DE LO PENAL PACHUCA","TERCERO DE LO PENAL PACHUCA","CUARTO DE LO PENAL PACHUCA","PRIMERO FAMILIAR DE PACHUCA","SEGUNDO FAMILIAR DE PACHUCA","TERCERO FAMILIAR DE PACHUCA","PRIMERO DE LO CIVIL PACHUCA","SEGUNDO DE LO CIVIL PACHUCA","TERCERO DE LO CIVIL PACHUCA","CUARTO DE LO CIVIL PACHUCA","PRIMERO DE LO MERCANTIL PACHUCA","SEGUNDO DE LO MERCANTIL PACHUCA","PRIMERO ESPECIALIZADO PARA ADOLECENTES","SEGUNDO ESPECIALIZADO PARA ADOLECENTES"};
    String[] string_tiza={"Selecciona Aqui:","CIVIL Y FAMILIAR TIZAYUCA","PENAL TIZAYUCA"};
    String[] string_tula={"Selecciona Aqui:","PRIMERO CIVIL Y FAMILIAR TULA","SEGUNDO CIVIL Y FAMILIAR TULA","TERCERO CIVIL Y FAMILIAR TULA","PRIMERO PENAL TULA","SEGUNDO PENAL TULA"};
    String[] string_tulancingo={"Selecciona Aqui:","PRIMERO CIVIL Y FAMILIAR TULANCINGO","SEGUNDO CIVIL Y FAMILIAR TULANCINGO","TERCERO CIVIL Y FAMILIAR TULANCINGO","PRIMERO PENAL TULANCINGO","SEGUNDO PENAL TULANCINGO"};
    String[] string_civiles={"Selecciona Aqui:","No.Expediente","Fecha de Notificación."};
    String[] string_penales={"Selecciona Aqui:","No.Causa","Fecha de Notificación."};

    public void inicio(View v){

        txtfcha=(TextView)v.findViewById(R.id.lbl_noti_fecha);
        fecha=(EditText)v.findViewById(R.id.txt_fecha);
        juzgado=(TextView)v.findViewById(R.id.lbl_noti_juzgado);
        opcion1=(TextView)v.findViewById(R.id.lbl_noti_opciones);
        opcion2=(TextView)v.findViewById(R.id.lbl_noti_opciones2);
        txtdistrito=(TextView)v.findViewById(R.id.lbl_noti_distrito);
        no_expediente=(TextView)v.findViewById(R.id.lbl_no_expediente);
        no_causa=(TextView)v.findViewById(R.id.lbl_no_causa);

        numero_expediente=(EditText)v.findViewById(R.id.txt_numero_expediente);
        numero_causa=(EditText)v.findViewById(R.id.txt_numero_causa);

        consulta=(Button)v.findViewById(R.id.btn_noti_consultar);
        atras=(Button)v.findViewById(R.id.atras);
        siguiente=(Button)v.findViewById(R.id.siguiente);

        ACTOPAN=(Spinner)v.findViewById(R.id.spinner_actopan);
        APAN=(Spinner)v.findViewById(R.id.spinner_noti_apan);
        HUEJUTLA=(Spinner)v.findViewById(R.id.spinner_huejutla);
        HUICHAPAN=(Spinner)v.findViewById(R.id.spinner_huichapan);
        IXMIQUILPAN=(Spinner)v.findViewById(R.id.spinner_ixmiqulpan);
        MIXQUIAHUALA=(Spinner)v.findViewById(R.id.spinner_mixquiahuala);
        PACHUCA=(Spinner)v.findViewById(R.id.spinner_pachuca);
        TIZAYUCA=(Spinner)v.findViewById(R.id.spinner_tizayuca);
        TULA=(Spinner)v.findViewById(R.id.spinner_tula);
        TULANCINGO=(Spinner)v.findViewById(R.id.spinner_tulancingo);

        distrito=(Spinner)v.findViewById(R.id.spinner_noti_distrito);
        opc1=(Spinner)v.findViewById(R.id.spinner_noti_opc_civil);
        opc2=(Spinner)v.findViewById(R.id.spinner_noti_opc_penal) ;
        tabla=(TableLayout)v.findViewById(R.id.Tabla_btn_noti);


        juzgado.setVisibility(View.GONE);
        opcion1.setVisibility(View.GONE);
        opcion2.setVisibility(View.GONE);
        no_expediente.setVisibility(View.GONE);
        no_causa.setVisibility(View.GONE);

        numero_expediente.setVisibility(View.GONE);
        numero_causa.setVisibility(View.GONE);
        consulta.setVisibility(View.GONE);

        ACTOPAN.setVisibility(View.GONE);
        APAN.setVisibility(View.GONE);
        HUEJUTLA.setVisibility(View.GONE);
        HUICHAPAN.setVisibility(View.GONE);
        IXMIQUILPAN.setVisibility(View.GONE);
        MIXQUIAHUALA.setVisibility(View.GONE);
        PACHUCA.setVisibility(View.GONE);
        TIZAYUCA.setVisibility(View.GONE);
        TULA.setVisibility(View.GONE);
        TULANCINGO.setVisibility(View.GONE);
        opc1.setVisibility(View.GONE);
        opc2.setVisibility(View.GONE);

        fecha.setVisibility(View.GONE);
        txtfcha.setVisibility(View.GONE);
        atras.setVisibility(View.GONE);
        siguiente.setVisibility(View.GONE);
    }

    public void llenado_spiners(View v){
        ArrayAdapter<String> adaptador=  new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,string_distrito);
        distrito.setAdapter(adaptador);

        ArrayAdapter<String> adaptador2=  new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,string_actopan);
        ACTOPAN.setAdapter(adaptador2);

        ArrayAdapter<String> adaptador3=  new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,string_apan);
        APAN.setAdapter(adaptador3);

        ArrayAdapter<String> adaptador4=  new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,string_huejutla);
        HUEJUTLA.setAdapter(adaptador4);

        ArrayAdapter<String> adaptador5=  new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,string_huichapan);
        HUICHAPAN.setAdapter(adaptador5);

        ArrayAdapter<String> adaptador6=  new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,string_ixmi);
        IXMIQUILPAN.setAdapter(adaptador6);

        ArrayAdapter<String> adaptador7=  new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,string_mixqui);
        MIXQUIAHUALA.setAdapter(adaptador7);

        ArrayAdapter<String> adaptador8=  new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,string_pachuca);
        PACHUCA.setAdapter(adaptador8);

        ArrayAdapter<String> adaptador9=  new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,string_tiza);
        TIZAYUCA.setAdapter(adaptador9);

        ArrayAdapter<String> adaptador10=  new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,string_tula);
        TULA.setAdapter(adaptador10);

        ArrayAdapter<String> adaptador11=  new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,string_tulancingo);
        TULANCINGO.setAdapter(adaptador11);

        ArrayAdapter<String> adaptador12=  new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,string_civiles);
        opc1.setAdapter(adaptador12);

        ArrayAdapter<String> adaptador13=  new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,string_penales);
        opc2.setAdapter(adaptador13);
    }


    public void visibilidad_sp(){
        ACTOPAN.setVisibility(View.GONE);
        APAN.setVisibility(View.GONE);
        HUEJUTLA.setVisibility(View.GONE);
        HUICHAPAN.setVisibility(View.GONE);
        IXMIQUILPAN.setVisibility(View.GONE);
        MIXQUIAHUALA.setVisibility(View.GONE);
        PACHUCA.setVisibility(View.GONE);
        TIZAYUCA.setVisibility(View.GONE);
        TULA.setVisibility(View.GONE);
        TULANCINGO.setVisibility(View.GONE);
        opc1.setVisibility(View.GONE);
        opc2.setVisibility(View.GONE);
    }


    public void seleccion_juzgado2(String juz){
        datos=new Datos();
        datos.setID(juz);
        datos.setQUE_ES("PENALES");
        opcion2.setVisibility(View.VISIBLE);
        opc2.setVisibility(View.VISIBLE);
        opcion1.setVisibility(View.GONE);
        opc1.setVisibility(View.GONE);
    }


    public void seleccion_juzgado(String juz){
        datos=new Datos();
        datos.setID(juz);
        datos.setQUE_ES("NORMAL");
        opcion1.setVisibility(View.VISIBLE);
        opc1.setVisibility(View.VISIBLE);
        opcion2.setVisibility(View.GONE);
        opc2.setVisibility(View.GONE);
    }

    public void spiner_dist(){

        distrito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==1){
                    visibilidad_sp();
                    juzgado.setVisibility(View.VISIBLE);
                    ACTOPAN.setVisibility(View.VISIBLE);
                } else if(position==2){
                    visibilidad_sp();
                    juzgado.setVisibility(View.VISIBLE);
                    APAN.setVisibility(View.VISIBLE);
                }else if(position==3){
                    visibilidad_sp();
                    juzgado.setVisibility(View.VISIBLE);
                    HUEJUTLA.setVisibility(View.VISIBLE);
                }else if(position==4){
                    visibilidad_sp();
                    juzgado.setVisibility(View.VISIBLE);
                    HUICHAPAN.setVisibility(View.VISIBLE);
                }else if(position==5){
                    visibilidad_sp();
                    juzgado.setVisibility(View.VISIBLE);
                    IXMIQUILPAN.setVisibility(View.VISIBLE);
                }else if(position==6){
                    visibilidad_sp();
                    juzgado.setVisibility(View.VISIBLE);
                    MIXQUIAHUALA.setVisibility(View.VISIBLE);
                }else if(position==7){
                    visibilidad_sp();
                    juzgado.setVisibility(View.VISIBLE);
                    PACHUCA.setVisibility(View.VISIBLE);
                }else if(position==8){
                    visibilidad_sp();
                    juzgado.setVisibility(View.VISIBLE);
                    TIZAYUCA.setVisibility(View.VISIBLE);
                }else if(position==9){
                    visibilidad_sp();
                    juzgado.setVisibility(View.VISIBLE);
                    TULA.setVisibility(View.VISIBLE);
                }else if(position==10){
                    visibilidad_sp();
                    juzgado.setVisibility(View.VISIBLE);
                    TULANCINGO.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void acciones(){

        ACTOPAN.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==1){
                   seleccion_juzgado("10");
                } else if(position==2){
                    seleccion_juzgado("44");
                }else if(position==3){
                    seleccion_juzgado2("11");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        APAN.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position==1){
                    seleccion_juzgado("13");
                } else if(position==2){
                    seleccion_juzgado("47");
                }else if(position==3){
                    seleccion_juzgado2("12");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        HUEJUTLA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==1){
                    seleccion_juzgado("15");
                } else if(position==2){
                    seleccion_juzgado2("45");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        HUICHAPAN.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==1){
                    seleccion_juzgado("16");
                } else if(position==2){
                    seleccion_juzgado2("51");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        IXMIQUILPAN.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position==1){
                    seleccion_juzgado("17");
                } else if(position==2){
                    seleccion_juzgado2("35");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        MIXQUIAHUALA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==1){
                    seleccion_juzgado("20");
                } else if(position==2){
                    seleccion_juzgado2("43");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        PACHUCA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position==1){
                    seleccion_juzgado2("7");
                } else if(position==2){
                    seleccion_juzgado2("8");
                }else if(position==3){
                    seleccion_juzgado2("9");
                }else if(position==4){
                    seleccion_juzgado2("50");
                }else if(position==5){
                    seleccion_juzgado("5");
                }else if(position==6){
                    seleccion_juzgado("40");
                }else if(position==7){
                    seleccion_juzgado("52");
                }else if(position==8){
                    seleccion_juzgado("1");
                }else if(position==9){
                    seleccion_juzgado("2");
                }else if(position==10){
                    seleccion_juzgado("3");
                }else if(position==11){
                    seleccion_juzgado("36");
                }else if(position==12){
                    seleccion_juzgado("38");
                }else if(position==13){
                    seleccion_juzgado("46");
                }else if(position==14){
                    seleccion_juzgado("53");
                    datos.setQUE_ES("ADOLECENTES");
                }else if(position==15){
                    seleccion_juzgado("73");
                    datos.setQUE_ES("ADOLECENTES");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        TIZAYUCA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==1){
                    seleccion_juzgado("24");
                } else if(position==2){
                    seleccion_juzgado2("42");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        TULA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==1){
                    seleccion_juzgado("26");
                } else if(position==2){
                    seleccion_juzgado("33");
                }else if(position==3){
                    seleccion_juzgado("49");
                }else if(position==4){
                    seleccion_juzgado2("27");
                }else if(position==5){
                    seleccion_juzgado2("39");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        TULANCINGO.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==1){
                    seleccion_juzgado("28");
                } else if(position==2){
                    seleccion_juzgado("30");
                }else if(position==3){
                    seleccion_juzgado("48");
                }else if(position==4){
                    seleccion_juzgado2("29");
                }else if(position==5){
                    seleccion_juzgado2("34");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        opc1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==1){
                    //////////////////// Por expediente
                    no_expediente.setVisibility(View.VISIBLE);
                    numero_expediente.setVisibility(View.VISIBLE);
                    no_causa.setVisibility(View.GONE);
                    numero_causa.setVisibility(View.GONE);
                    fecha.setVisibility(View.GONE);
                    txtfcha.setVisibility(View.GONE);
                    consulta.setVisibility(View.VISIBLE);
                    datos.setMES("EXPEDIENTE");
                } else if(position==2){
                    //////Por fecha
                    no_expediente.setVisibility(View.GONE);
                    numero_expediente.setVisibility(View.GONE);
                    no_causa.setVisibility(View.GONE);
                    numero_causa.setVisibility(View.GONE);
                    fecha.setVisibility(View.VISIBLE);
                    txtfcha.setVisibility(View.VISIBLE);
                    consulta.setVisibility(View.VISIBLE);
                    datos.setMES("FECHA");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        opc2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==1){
                    //////////////////// Por Causa
                    no_expediente.setVisibility(View.GONE);
                    numero_expediente.setVisibility(View.GONE);
                    no_causa.setVisibility(View.VISIBLE);
                    numero_causa.setVisibility(View.VISIBLE);
                    fecha.setVisibility(View.GONE);
                    txtfcha.setVisibility(View.GONE);
                    consulta.setVisibility(View.VISIBLE);
                    datos.setMES("EXPEDIENTE");
                } else if(position==2){
                    //////Por fecha
                    no_expediente.setVisibility(View.GONE);
                    numero_expediente.setVisibility(View.GONE);
                    no_causa.setVisibility(View.GONE);
                    numero_causa.setVisibility(View.GONE);
                    fecha.setVisibility(View.VISIBLE);
                    txtfcha.setVisibility(View.VISIBLE);
                    consulta.setVisibility(View.VISIBLE);
                    datos.setMES("FECHA");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void consulta_final(){

        consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pDialog = new ProgressDialog(getActivity(),R.style.MyAlertDialogStyle);
                pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pDialog.setMessage("Procesando...");
                pDialog.setCancelable(true);
                pDialog.setMax(100);

                if(datos.getQUE_ES()=="NORMAL"){

                    //NORMAL(11);
                    Noti_Normal Noti=new Noti_Normal();
                    Noti.execute();

                }else if (datos.getQUE_ES()=="PENALES") {
                   //PENALES();
                    Noti_penales Penales=new Noti_penales();
                    Penales.execute();

                }else if (datos.getQUE_ES()=="ADOLECENTES"){
                    //ADOLECENTES();
                    Noti_Adolecentes Noti_ad = new Noti_Adolecentes();
                    Noti_ad.execute();

                }
            }
        });
    }

    public void altTableRow(int min, int max) {
        int childViewCount = tabla.getChildCount();
        suma.setTotal(childViewCount-1);
        //
        for (int i = 1; i < childViewCount; i++) {
            TableRow row = (TableRow) tabla.getChildAt(i);
            row.setVisibility(View.GONE);
        }

        if(childViewCount-1<max){
            for (int i = 1; i < childViewCount; i++) {
                TableRow row = (TableRow) tabla.getChildAt(i);
                row.setVisibility(View.VISIBLE);
                atras.setVisibility(View.GONE);
                siguiente.setVisibility(View.GONE);
            }

        }else{
            for (int i = min; i <= max; i++) {
                TableRow row = (TableRow) tabla.getChildAt(i);
                row.setVisibility(View.VISIBLE);
            }

        }
    }

    private void NORMAL(int w) {
        String[] da =new String[7];
        int veces=0;
        try {

            Con_sql conStr=new Con_sql();
            connect =conStr.connection_notifica();
            String query="";

            if (connect == null)
            {
                ConnectionResult = "Check Your Internet Access!";
                Toast.makeText(getActivity(),ConnectionResult, Toast.LENGTH_SHORT).show();

            }
            else
            {
                //Toast.makeText(getActivity(),"Esperé unos segundos...", Toast.LENGTH_SHORT).show();
                if(datos.getMES()=="FECHA"){
                    query ="SELECT *  FROM Vta_ResiNotificaJuzgado where id_juzgado="+datos.getID()+" and  DATEPART(MONTH,[Fecha de Publicación])="+datos2.getMES()+" and DATEPART(YEAR,[Fecha de Publicación])="+datos2.getAÑO()+" and DATEPART(DAY,[Fecha de Publicación])="+datos2.getDIA()+" ORDER BY \"Fecha de Publicación\" DESC;";
                }else if (datos.getMES()=="EXPEDIENTE"){

                    while (numero_expediente.getText().toString().length()<w){
                        String ejemplo=numero_expediente.getText().toString();
                        ejemplo="0"+ejemplo;
                        numero_expediente.setText(ejemplo);
                    }
                    query ="SELECT  *  FROM Vta_ResiNotificaJuzgado where id_juzgado="+datos.getID()+" and Número='"+numero_expediente.getText().toString()+"'";
                }

                //Toast.makeText(getActivity(),query, Toast.LENGTH_SHORT).show();

                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if(!rs.isBeforeFirst()){
                    Toast.makeText(getActivity(),"No se encontraron Datos", Toast.LENGTH_SHORT).show();
                }

                else {

                    Encabezado2("No. de Notificación.","No.expediente","Fecha de Publicación.","Juicio.","Fecha de Resolución.","Síntesis.");

                    txtdistrito.setVisibility(View.GONE);
                    distrito.setVisibility(View.GONE);
                    juzgado.setVisibility(View.GONE);
                    opcion1.setVisibility(View.GONE);
                    opcion2.setVisibility(View.GONE);
                    no_expediente.setVisibility(View.GONE);
                    no_causa.setVisibility(View.GONE);
                    numero_expediente.setVisibility(View.GONE);
                    numero_causa.setVisibility(View.GONE);
                    consulta.setVisibility(View.GONE);
                    ACTOPAN.setVisibility(View.GONE);
                    APAN.setVisibility(View.GONE);
                    HUEJUTLA.setVisibility(View.GONE);
                    HUICHAPAN.setVisibility(View.GONE);
                    IXMIQUILPAN.setVisibility(View.GONE);
                    MIXQUIAHUALA.setVisibility(View.GONE);
                    PACHUCA.setVisibility(View.GONE);
                    TIZAYUCA.setVisibility(View.GONE);
                    TULA.setVisibility(View.GONE);
                    TULANCINGO.setVisibility(View.GONE);
                    opc1.setVisibility(View.GONE);
                    opc2.setVisibility(View.GONE);
                    fecha.setVisibility(View.GONE);
                    txtfcha.setVisibility(View.GONE);
                    visibilidad(true);
                    atras.setVisibility(View.VISIBLE);
                    siguiente.setVisibility(View.VISIBLE);


                    while (rs.next()){
                        Encabezado3();
                        veces=veces+1;
                        da[1]=rs.getString(1);
                        da[2]=rs.getString(2);
                        da[3]=rs.getString(3);
                        da[4]=rs.getString(4);
                        da[5]=rs.getString(5);
                        da[6]=rs.getString(8);

                        llenadoTabla2(da[1],da[2],da[3],da[6],da[4],da[5]);
                    }
                }
            }

            Cambio_color(4);
            altTableRow(1,10);
            suma.setMin(1);
            suma.setMax(10);

        }catch (Exception ex)
        {
            esSatisfactorio = false;
            ConnectionResult = ex.getMessage();
        }


    }

    private void PENALES() {

        String[] da =new String[7];
        try {

            Con_sql conStr=new Con_sql();
            connect =conStr.connection_notifica();
            String query="";

            if (connect == null)
            {
                ConnectionResult = "Check Your Internet Access!";
                Toast.makeText(getActivity(),ConnectionResult, Toast.LENGTH_SHORT).show();

            }
            else
            {
                Toast.makeText(getActivity(),"Esperé unos segundos...", Toast.LENGTH_SHORT).show();
                if(datos.getMES()=="FECHA"){
                    query ="SELECT *  FROM Vta_ResiNotificaJuzgado where id_juzgado="+datos.getID()+" and  DATEPART(MONTH,[Fecha de Publicación])="+datos2.getMES()+" and DATEPART(YEAR,[Fecha de Publicación])="+datos2.getAÑO()+" and DATEPART(DAY,[Fecha de Publicación])="+datos2.getDIA()+" ORDER BY \"Fecha de Publicación\" DESC;";
                }else if (datos.getMES()=="EXPEDIENTE"){

                    while (numero_causa.getText().toString().length()<9){
                        String ejemplo=numero_causa.getText().toString();
                        ejemplo="0"+ejemplo;
                        numero_causa.setText(ejemplo);
                    }
                    query ="SELECT  *  FROM Vta_ResiNotificaJuzgado where id_juzgado="+datos.getID()+" and Número='"+numero_causa.getText().toString()+"'";
                }

                //Toast.makeText(getActivity(),query, Toast.LENGTH_SHORT).show();


                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if(!rs.isBeforeFirst()){
                    Toast.makeText(getActivity(),"No se encontraron Datos", Toast.LENGTH_SHORT).show();
                }

                else {

                    Encabezado2("No. de Notificación.","No.expediente","Fecha de Publicación.","Juicio.","Fecha de Resolución.","Síntesis.");

                    txtdistrito.setVisibility(View.GONE);
                    distrito.setVisibility(View.GONE);
                    juzgado.setVisibility(View.GONE);
                    opcion1.setVisibility(View.GONE);
                    opcion2.setVisibility(View.GONE);
                    no_expediente.setVisibility(View.GONE);
                    no_causa.setVisibility(View.GONE);
                    numero_expediente.setVisibility(View.GONE);
                    numero_causa.setVisibility(View.GONE);
                    consulta.setVisibility(View.GONE);
                    ACTOPAN.setVisibility(View.GONE);
                    APAN.setVisibility(View.GONE);
                    HUEJUTLA.setVisibility(View.GONE);
                    HUICHAPAN.setVisibility(View.GONE);
                    IXMIQUILPAN.setVisibility(View.GONE);
                    MIXQUIAHUALA.setVisibility(View.GONE);
                    PACHUCA.setVisibility(View.GONE);
                    TIZAYUCA.setVisibility(View.GONE);
                    TULA.setVisibility(View.GONE);
                    TULANCINGO.setVisibility(View.GONE);
                    opc1.setVisibility(View.GONE);
                    opc2.setVisibility(View.GONE);
                    fecha.setVisibility(View.GONE);
                    txtfcha.setVisibility(View.GONE);
                    visibilidad(true);
                    atras.setVisibility(View.VISIBLE);
                    siguiente.setVisibility(View.VISIBLE);


                    while (rs.next()){
                       // Encabezado2("No. de Notificación.","No.expediente","Fecha de Publicación.","Juicio.","Fecha de Resolución.","Síntesis.");
                        Encabezado3();
                        da[1]=rs.getString(1);
                        da[2]=rs.getString(2);
                        da[3]=rs.getString(3);
                        da[4]=rs.getString(4);
                        da[5]=rs.getString(5);
                        da[6]=rs.getString(8);

                        //Toast.makeText(getActivity(),da[1], Toast.LENGTH_SHORT).show();
                        llenadoTabla2(da[1],da[2],da[3],da[6],da[4],da[5]);

                    }
                }

            }

            //Toast.makeText(getActivity(),datos[1], Toast.LENGTH_SHORT).show();

            Cambio_color(4);
            suma.setMin(1);
            suma.setMax(10);
            altTableRow(1,10);


        }catch (Exception ex)
        {
            esSatisfactorio = false;
            ConnectionResult = ex.getMessage();
        }

    }

    private void ADOLECENTES() {
        String[] da =new String[6];
        try {

            Con_sql conStr=new Con_sql();
            connect =conStr.connection_notifica();
            String query="";

            if (connect == null)
            {
                ConnectionResult = "Check Your Internet Access!";
                Toast.makeText(getActivity(),ConnectionResult, Toast.LENGTH_SHORT).show();

            }
            else
            {
                Toast.makeText(getActivity(),"Esperé unos segundos...", Toast.LENGTH_SHORT).show();
                if(datos.getMES()=="FECHA"){
                 query ="SELECT *  FROM Vta_ResiNotificaJuzAdoles where id_juzgado="+datos.getID()+" and  DATEPART(MONTH,[Fecha de Publicación])="+datos2.getMES()+" and DATEPART(YEAR,[Fecha de Publicación])="+datos2.getAÑO()+" and DATEPART(DAY,[Fecha de Publicación])="+datos2.getDIA()+" ORDER BY \"Fecha de Publicación\" DESC;";
                }else if (datos.getMES()=="EXPEDIENTE"){
                   query ="SELECT  *  FROM Vta_ResiNotificaJuzAdoles where id_juzgado="+datos.getID()+" and Número='"+numero_expediente.getText().toString()+"'";
                }

                //Toast.makeText(getActivity(),query, Toast.LENGTH_SHORT).show();

                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if(!rs.isBeforeFirst()){
                    Toast.makeText(getActivity(),"No se encontraron Datos", Toast.LENGTH_SHORT).show();
                }

                else {

                    txtdistrito.setVisibility(View.GONE);
                    distrito.setVisibility(View.GONE);
                    juzgado.setVisibility(View.GONE);
                    opcion1.setVisibility(View.GONE);
                    opcion2.setVisibility(View.GONE);
                    no_expediente.setVisibility(View.GONE);
                    no_causa.setVisibility(View.GONE);
                    numero_expediente.setVisibility(View.GONE);
                    numero_causa.setVisibility(View.GONE);
                    consulta.setVisibility(View.GONE);
                    ACTOPAN.setVisibility(View.GONE);
                    APAN.setVisibility(View.GONE);
                    HUEJUTLA.setVisibility(View.GONE);
                    HUICHAPAN.setVisibility(View.GONE);
                    IXMIQUILPAN.setVisibility(View.GONE);
                    MIXQUIAHUALA.setVisibility(View.GONE);
                    PACHUCA.setVisibility(View.GONE);
                    TIZAYUCA.setVisibility(View.GONE);
                    TULA.setVisibility(View.GONE);
                    TULANCINGO.setVisibility(View.GONE);
                    opc1.setVisibility(View.GONE);
                    opc2.setVisibility(View.GONE);
                    fecha.setVisibility(View.GONE);
                    txtfcha.setVisibility(View.GONE);
                    visibilidad(true);
                    atras.setVisibility(View.VISIBLE);
                    siguiente.setVisibility(View.VISIBLE);
                    Encabezado("No. de Notificación.","No.expediente","Fecha de Publicación.","Fecha de Resolución.","Síntesis.");


                    while (rs.next()){

                        Encabezado("","","","","");

                        da[1]=rs.getString(1);
                        da[2]=rs.getString(2);
                        da[3]=rs.getString(3);
                        da[4]=rs.getString(4);
                        da[5]=rs.getString(5);

                        //Toast.makeText(getActivity(),da[1], Toast.LENGTH_SHORT).show();
                        llenadoTabla(da[1],da[2],da[3],da[4],da[5]);

                    }
                }

            }

            //Toast.makeText(getActivity(),datos[1], Toast.LENGTH_SHORT).show();
            Cambio_color(4);
            suma.setMin(1);
            suma.setMax(10);
            altTableRow(1,10);


        }catch (Exception ex)
        {
            esSatisfactorio = false;
            ConnectionResult = ex.getMessage();
        }



    }

    public void selector_fecha(){
        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c= Calendar.getInstance();
              dia=c.get(Calendar.DAY_OF_MONTH);
               mes=c.get(Calendar.MONTH);
              ano=c.get(Calendar.YEAR);
                // dia=1;
                //mes=1;
                //ano=2017;

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        fecha.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                        datos2=new Datos();
                        datos2.setDIA(String.valueOf(dayOfMonth));
                        datos2.setMES(String.valueOf(monthOfYear+1));
                        datos2.setAÑO(String.valueOf(year));
                    }
                }
                        ,ano,mes,dia);
                datePickerDialog.show();
            }
        });
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void Cambio_color(int alt_row) {
    int childViewCount = tabla.getChildCount();

    for (int i = 1; i < childViewCount; i++) {
        TableRow row = (TableRow) tabla.getChildAt(i);

        for (int j = 0; j < row.getChildCount()-1; j++) {

            TextView tv = (TextView) row.getChildAt(j);
            if (i % 4 != 0) {
                // tv.setBackground(getResources().getDrawable(R.drawable.alt_row_color));
                tv.setTextColor(Color.parseColor("#B1613e"));
            } else {
                //tv.setBackground(getResources().getDrawable(R.drawable.row_color));
                tv.setTextColor(Color.BLACK);
            }
        }
    }
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void llenadoTabla(final String txt1, final String txt2, final String txt3, final String txt4, final String txt5){
        TableRow.LayoutParams layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);

        TableRow row= new TableRow(getActivity());
        row.setLayoutParams(layoutFila);
        row.setGravity(Gravity.CENTER_VERTICAL);

        TextView txtTabla, txtTabla2,txtTabla3,txtTabla4,txtTabla5;

        txtTabla=new TextView(getActivity());
        txtTabla.setGravity(Gravity.CENTER_HORIZONTAL);
        txtTabla.setBackgroundColor(Color.TRANSPARENT);
        //txtTabla.setBackgroundResource(R.drawable.border);
        txtTabla.setText(txt1);
        txtTabla.setTextColor(Color.parseColor("#B1613e"));
        txtTabla.setMinWidth(300);
        txtTabla.setMaxWidth(500);
        row.addView(txtTabla);


        txtTabla2=new TextView(getActivity());
        txtTabla2.setGravity(Gravity.CENTER);
        txtTabla2.setBackgroundColor(Color.TRANSPARENT);
        txtTabla2.setText(txt2);
        txtTabla2.setTextColor(Color.parseColor("#B1613e"));
        txtTabla2.setWidth(300);
        row.addView(txtTabla2);


        txtTabla3=new TextView(getActivity());
        txtTabla3.setGravity(Gravity.CENTER);
        txtTabla3.setBackgroundColor(Color.TRANSPARENT);
        txtTabla3.setText(txt3);
        txtTabla3.setTextColor(Color.parseColor("#B1613e"));
        txtTabla3.setWidth(300);
        row.addView(txtTabla3);


        txtTabla4=new TextView(getActivity());
        txtTabla4.setGravity(Gravity.CENTER);
        txtTabla4.setBackgroundColor(Color.TRANSPARENT);
        txtTabla4.setText(txt4);
        txtTabla4.setTextColor(Color.parseColor("#B1613e"));
        txtTabla4.setWidth(300);
        row.addView(txtTabla4);


        txtTabla5=new TextView(getActivity());
        txtTabla5.setGravity(Gravity.CENTER);
        txtTabla5.setBackgroundColor(Color.TRANSPARENT);
        txtTabla5.setText(Html.fromHtml("<u>Ver Sintesis</u>"));
        //txtTabla5.setText(txt5);
        txtTabla5.setTextColor(Color.BLACK);
        txtTabla5.setWidth(500);

        txtTabla5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtdistrito.setVisibility(View.VISIBLE);
                juzgado.setVisibility(View.VISIBLE);
                txtdistrito.setTextSize(16);
                txtdistrito.setText("No. de Notificacion:\n"+txt1+"\n\n" +
                        "Expediente:\n"+txt2+"\n\n" +
                        "Fecha de Publicación:\n"+txt3+"\n\n" +
                        "Fecha de Resolucion:\n"+txt4+"\n\n");
                juzgado.setTextSize(16);
                juzgado.setText("SINTESIS:\n"+"\n"+txt5);
                tabla.removeAllViews();
                atras.setVisibility(View.GONE);
                siguiente.setVisibility(View.GONE);

            }
        });

        row.addView(txtTabla5);
        tabla.addView(row);

        TableRow borde= new TableRow(getActivity());
        row.setLayoutParams(layoutFila);

        TextView borde1, borde2,borde3,borde4,borde5,border1;

        borde1=new TextView(getActivity());
        borde1.setBackgroundColor(Color.parseColor("#000000"));
        borde1.setWidth(250);
        borde1.setHeight(10);
        borde.addView(borde1);


        borde2=new TextView(getActivity());
        borde2.setBackgroundColor(Color.parseColor("#000000"));
        borde2.setWidth(300);
        borde2.setHeight(10);
        borde.addView(borde2);

        borde3=new TextView(getActivity());
        borde3.setBackgroundColor(Color.parseColor("#000000"));
        borde3.setWidth(300);
        borde3.setHeight(10);
        borde.addView(borde3);

        borde4=new TextView(getActivity());
        borde4.setBackgroundColor(Color.parseColor("#000000"));
        borde4.setWidth(300);
        borde4.setHeight(10);
        borde.addView(borde4);

        borde5=new TextView(getActivity());
        borde5.setBackgroundColor(Color.parseColor("#000000"));
        borde5.setWidth(500);
        borde5.setHeight(10);
        borde.addView(borde5);

        //tabla.addView(row ,new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        tabla.addView(borde);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void Encabezado(String txt1,String txt2,String txt3,String txt4,String txt5){
        TableRow.LayoutParams layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        Resources res = getResources();



        int x=150;

        TableRow row= new TableRow(getActivity());
        row.setLayoutParams(layoutFila);


        TextView txtTabla, txtTabla2,txtTabla3,txtTabla4,txtTabla5,txtTabla6;

        txtTabla=new TextView(getActivity());
        txtTabla.setGravity(Gravity.CENTER_HORIZONTAL);
        txtTabla.setBackgroundColor(Color.parseColor("#B1613e"));
        txtTabla.setText(txt1);
        txtTabla.setTextColor(Color.WHITE);
        txtTabla.setMinWidth(300);
        txtTabla.setMaxWidth(400);
        txtTabla.setHeight(x);


        row.addView(txtTabla);


        txtTabla2=new TextView(getActivity());
        txtTabla2.setGravity(Gravity.CENTER_HORIZONTAL);
        txtTabla2.setBackgroundColor(Color.parseColor("#B1613e"));
        txtTabla2.setText(txt2);
        txtTabla2.setTextColor(Color.WHITE);
        txtTabla2.setMinWidth(300);
        txtTabla2.setMaxWidth(400);
        txtTabla2.setHeight(x);
        row.addView(txtTabla2);


        txtTabla3=new TextView(getActivity());
        txtTabla3.setGravity(Gravity.CENTER_HORIZONTAL);
        txtTabla3.setBackgroundColor(Color.parseColor("#B1613e"));
        txtTabla3.setText(txt3);
        txtTabla3.setTextColor(Color.WHITE);
        txtTabla3.setMinWidth(300);
        txtTabla3.setMaxWidth(400);
        txtTabla3.setHeight(x);
        row.addView(txtTabla3);

        txtTabla4=new TextView(getActivity());
        txtTabla4.setGravity(Gravity.CENTER_HORIZONTAL);
        txtTabla4.setBackgroundColor(Color.parseColor("#B1613e"));
        txtTabla4.setText(txt4);
        txtTabla4.setTextColor(Color.WHITE);
        txtTabla4.setMinWidth(300);
        txtTabla4.setMaxWidth(400);
        txtTabla4.setHeight(x);
        row.addView(txtTabla4);

        txtTabla5=new TextView(getActivity());
        txtTabla5.setGravity(Gravity.CENTER_HORIZONTAL);
        txtTabla5.setBackgroundColor(Color.parseColor("#B1613e"));
        txtTabla5.setText(txt5);
        txtTabla5.setTextColor(Color.WHITE);
        txtTabla5.setMinWidth(300);
        txtTabla5.setMaxWidth(400);
        txtTabla5.setHeight(x);
        row.addView(txtTabla5);

        txtTabla6=new TextView(getActivity());
        txtTabla6.setGravity(Gravity.CENTER_HORIZONTAL);
        txtTabla6.setBackgroundColor(Color.TRANSPARENT);
        txtTabla6.setWidth(200);
        txtTabla6.setHeight(x);
        row.addView(txtTabla6);

        tabla.addView(row);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void Encabezado4(String txt1,String txt2,String txt3,String txt4,String txt5){
        TableRow.LayoutParams layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        Resources res = getResources();



        int x=10;

        TableRow row= new TableRow(getActivity());
        row.setLayoutParams(layoutFila);


        TextView txtTabla, txtTabla2,txtTabla3,txtTabla4,txtTabla5,txtTabla6;

        txtTabla=new TextView(getActivity());
        txtTabla.setGravity(Gravity.CENTER_HORIZONTAL);
        txtTabla.setBackgroundColor(Color.parseColor("#B1613e"));
        txtTabla.setText(txt1);
        txtTabla.setTextColor(Color.WHITE);
        txtTabla.setWidth(250);
        txtTabla.setHeight(x);
        row.addView(txtTabla);

        txtTabla2=new TextView(getActivity());
        txtTabla2.setGravity(Gravity.CENTER_HORIZONTAL);
        txtTabla2.setBackgroundColor(Color.parseColor("#B1613e"));
        txtTabla2.setText(txt2);
        txtTabla2.setTextColor(Color.WHITE);
        txtTabla2.setWidth(300);
        txtTabla2.setHeight(x);
        row.addView(txtTabla2);


        txtTabla3=new TextView(getActivity());
        txtTabla3.setGravity(Gravity.CENTER_HORIZONTAL);
        txtTabla3.setBackgroundColor(Color.parseColor("#B1613e"));
        txtTabla3.setText(txt3);
        txtTabla3.setTextColor(Color.WHITE);
        txtTabla3.setWidth(300);
        txtTabla3.setHeight(x);
        row.addView(txtTabla3);

        txtTabla4=new TextView(getActivity());
        txtTabla4.setGravity(Gravity.CENTER_HORIZONTAL);
        txtTabla4.setBackgroundColor(Color.parseColor("#B1613e"));
        txtTabla4.setText(txt4);
        txtTabla4.setTextColor(Color.WHITE);
        txtTabla4.setWidth(300);
        txtTabla4.setHeight(x);
        row.addView(txtTabla4);

        txtTabla5=new TextView(getActivity());
        txtTabla5.setGravity(Gravity.CENTER_HORIZONTAL);
        txtTabla5.setBackgroundColor(Color.parseColor("#B1613e"));
        txtTabla5.setText(txt5);
        txtTabla5.setTextColor(Color.WHITE);
        txtTabla5.setWidth(1100);
        txtTabla5.setHeight(x);
        row.addView(txtTabla5);

        txtTabla6=new TextView(getActivity());
        txtTabla6.setGravity(Gravity.CENTER_HORIZONTAL);
        txtTabla6.setBackgroundColor(Color.TRANSPARENT);
        txtTabla6.setWidth(200);
        txtTabla6.setHeight(x);
        row.addView(txtTabla6);

        tabla.addView(row);
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void llenadoTabla2(final String txt1, final String txt2, final String txt3, final String txt7, final String txt4, final String txt5){
    TableRow.LayoutParams layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
            TableRow.LayoutParams.WRAP_CONTENT);

    TableRow row= new TableRow(getActivity());
    row.setLayoutParams(layoutFila);
    row.setGravity(Gravity.CENTER_VERTICAL);


    TextView txtTabla, txtTabla2,txtTabla3,txtTabla4,txtTabla5 ,txtTabla9;



    txtTabla=new TextView(getActivity());
    txtTabla.setGravity(Gravity.CENTER_HORIZONTAL);
    txtTabla.setBackgroundColor(Color.TRANSPARENT);
    txtTabla.setText(txt1);
    txtTabla.setTextColor(Color.parseColor("#B1613e"));
    txtTabla.setWidth(250);
    row.addView(txtTabla);


    txtTabla2=new TextView(getActivity());
    txtTabla2.setGravity(Gravity.CENTER_HORIZONTAL);
    txtTabla2.setBackgroundColor(Color.TRANSPARENT);
    txtTabla2.setText(txt2);
    txtTabla2.setTextColor(Color.parseColor("#B1613e"));
    txtTabla2.setWidth(300);
    row.addView(txtTabla2);

    txtTabla3=new TextView(getActivity());
    txtTabla3.setGravity(Gravity.CENTER);
    txtTabla3.setBackgroundColor(Color.TRANSPARENT);
    txtTabla3.setText(txt3);
    txtTabla3.setTextColor(Color.parseColor("#B1613e"));
    txtTabla3.setWidth(300);
    row.addView(txtTabla3);

    txtTabla9=new TextView(getActivity());
    txtTabla9.setGravity(Gravity.CENTER);
    txtTabla9.setBackgroundColor(Color.TRANSPARENT);
    txtTabla9.setText(txt7);
    txtTabla9.setTextColor(Color.parseColor("#B1613e"));
    txtTabla9.setWidth(400);
    row.addView(txtTabla9);

    txtTabla4=new TextView(getActivity());
    txtTabla4.setGravity(Gravity.CENTER);
    txtTabla4.setBackgroundColor(Color.TRANSPARENT);
    txtTabla4.setText(txt4);
    txtTabla4.setTextColor(Color.parseColor("#B1613e"));
    txtTabla4.setWidth(300);
    row.addView(txtTabla4);

    txtTabla5=new TextView(getActivity());
    txtTabla5.setGravity(Gravity.CENTER);
    txtTabla5.setBackgroundColor(Color.TRANSPARENT);
    txtTabla5.setText(Html.fromHtml("<u>Ver Sintesis</u>"));
    //txtTabla5.setText(txt5);
    txtTabla5.setTextColor(Color.BLACK);
    txtTabla5.setWidth(500);

    txtTabla5.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            txtdistrito.setVisibility(View.VISIBLE);
            juzgado.setVisibility(View.VISIBLE);
            txtdistrito.setTextSize(16);
            txtdistrito.setText("No. de Notificacion:\n"+txt1+"\n\n" +
                    "Expediente:\n"+txt2+"\n\n"+
                    "Juicio:\n"+txt7+"\n\n"+
                    "Fecha de Publicación:\n"+txt3+"\n\n" +
                    "Fecha de Resolucion:\n"+txt4+"\n\n");
            juzgado.setTextSize(16);
            juzgado.setText("SINTESIS:\n"+"\n"+txt5);
            tabla.removeAllViews();
            atras.setVisibility(View.GONE);
            siguiente.setVisibility(View.GONE);
        }
    });

    row.addView(txtTabla5);

    tabla.addView(row);

}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////77
    public void Encabezado3(){
        TableRow.LayoutParams layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        Resources res = getResources();



        int x=10;

        TableRow row= new TableRow(getActivity());
        row.setLayoutParams(layoutFila);


        TextView txtTabla, txtTabla2,txtTabla3,txtTabla4,txtTabla5,txtTabla6,txtTabla7;

        txtTabla=new TextView(getActivity());
        txtTabla.setGravity(Gravity.CENTER_HORIZONTAL);
        txtTabla.setBackgroundColor(Color.parseColor("#B1613e"));

        txtTabla.setTextColor(Color.WHITE);
        txtTabla.setMinWidth(300);
        txtTabla.setMaxWidth(400);
        txtTabla.setHeight(x);
        row.addView(txtTabla);

        txtTabla2=new TextView(getActivity());
        txtTabla2.setGravity(Gravity.CENTER_HORIZONTAL);
        txtTabla2.setBackgroundColor(Color.parseColor("#B1613e"));

        txtTabla2.setTextColor(Color.WHITE);
        txtTabla2.setMinWidth(300);
        txtTabla2.setMaxWidth(400);
        txtTabla2.setHeight(x);
        row.addView(txtTabla2);


        txtTabla3=new TextView(getActivity());
        txtTabla3.setGravity(Gravity.CENTER_HORIZONTAL);
        txtTabla3.setBackgroundColor(Color.parseColor("#B1613e"));

        txtTabla3.setTextColor(Color.WHITE);
        txtTabla3.setMinWidth(300);
        txtTabla3.setMaxWidth(400);
        txtTabla3.setHeight(x);
        row.addView(txtTabla3);

        txtTabla7=new TextView(getActivity());
        txtTabla7.setGravity(Gravity.CENTER_HORIZONTAL);
        txtTabla7.setBackgroundColor(Color.parseColor("#B1613e"));

        txtTabla7.setTextColor(Color.WHITE);
        txtTabla7.setMinWidth(600);
        txtTabla7.setMaxWidth(700);
        txtTabla7.setHeight(x);
        row.addView(txtTabla7);

        txtTabla4=new TextView(getActivity());
        txtTabla4.setGravity(Gravity.CENTER_HORIZONTAL);
        txtTabla4.setBackgroundColor(Color.parseColor("#B1613e"));

        txtTabla4.setTextColor(Color.WHITE);
        txtTabla4.setMinWidth(300);
        txtTabla4.setMaxWidth(400);
        txtTabla4.setHeight(x);
        row.addView(txtTabla4);

        txtTabla5=new TextView(getActivity());
        txtTabla5.setGravity(Gravity.CENTER_HORIZONTAL);
        txtTabla5.setBackgroundColor(Color.parseColor("#B1613e"));
        txtTabla5.setTextColor(Color.WHITE);
        txtTabla5.setMinWidth(500);
        txtTabla5.setMaxWidth(600);
        txtTabla5.setHeight(x);
        //txtTabla5.setMaxLines(3);
        //txtTabla5.setMovementMethod(new ScrollingMovementMethod());



        row.addView(txtTabla5);


        txtTabla6=new TextView(getActivity());
        txtTabla6.setGravity(Gravity.CENTER_HORIZONTAL);
        txtTabla6.setBackgroundColor(Color.TRANSPARENT);
        txtTabla6.setWidth(200);
        txtTabla6.setHeight(x);
        row.addView(txtTabla6);

        tabla.addView(row);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void Encabezado2(String txt1,String txt2,String txt3,String txt7,String txt4,String txt5){
        TableRow.LayoutParams layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        Resources res = getResources();

        int x=150;

        TableRow row= new TableRow(getActivity());
        row.setLayoutParams(layoutFila);

        TextView txtTabla, txtTabla2,txtTabla3,txtTabla4,txtTabla5,txtTabla6,txtTabla7;

        txtTabla=new TextView(getActivity());
        txtTabla.setGravity(Gravity.CENTER_HORIZONTAL);
        txtTabla.setBackgroundColor(Color.parseColor("#B1613e"));
        txtTabla.setText(txt1);
        txtTabla.setTextColor(Color.WHITE);
        txtTabla.setMinWidth(300);
        txtTabla.setMaxWidth(400);
        txtTabla.setHeight(x);
        row.addView(txtTabla);

        txtTabla2=new TextView(getActivity());
        txtTabla2.setGravity(Gravity.CENTER_HORIZONTAL);
        txtTabla2.setBackgroundColor(Color.parseColor("#B1613e"));
        txtTabla2.setText(txt2);
        txtTabla2.setTextColor(Color.WHITE);
        txtTabla2.setMinWidth(300);
        txtTabla2.setMaxWidth(400);
        txtTabla2.setHeight(x);
        row.addView(txtTabla2);

        txtTabla3=new TextView(getActivity());
        txtTabla3.setGravity(Gravity.CENTER_HORIZONTAL);
        txtTabla3.setBackgroundColor(Color.parseColor("#B1613e"));
        txtTabla3.setText(txt3);
        txtTabla3.setTextColor(Color.WHITE);
        txtTabla3.setMinWidth(300);
        txtTabla3.setMaxWidth(400);
        txtTabla3.setHeight(x);
        row.addView(txtTabla3);

        txtTabla7=new TextView(getActivity());
        txtTabla7.setGravity(Gravity.CENTER_HORIZONTAL);
        txtTabla7.setBackgroundColor(Color.parseColor("#B1613e"));
        txtTabla7.setText(txt7);
        txtTabla7.setTextColor(Color.WHITE);
        txtTabla7.setMinWidth(400);
        txtTabla7.setMaxWidth(500);
        txtTabla7.setHeight(x);
        row.addView(txtTabla7);

        txtTabla4=new TextView(getActivity());
        txtTabla4.setGravity(Gravity.CENTER_HORIZONTAL);
        txtTabla4.setBackgroundColor(Color.parseColor("#B1613e"));
        txtTabla4.setText(txt4);
        txtTabla4.setTextColor(Color.WHITE);
        txtTabla4.setMinWidth(300);
        txtTabla4.setMaxWidth(400);
        txtTabla4.setHeight(x);
        row.addView(txtTabla4);

        txtTabla5=new TextView(getActivity());
        txtTabla5.setGravity(Gravity.CENTER_HORIZONTAL);
        txtTabla5.setBackgroundColor(Color.parseColor("#B1613e"));
        txtTabla5.setText(txt5);
        txtTabla5.setTextColor(Color.WHITE);
        txtTabla5.setMinWidth(500);
        txtTabla5.setMaxWidth(600);
        txtTabla5.setHeight(x);
        row.addView(txtTabla5);

        txtTabla6=new TextView(getActivity());
        txtTabla6.setGravity(Gravity.CENTER_HORIZONTAL);
        txtTabla6.setBackgroundColor(Color.TRANSPARENT);
        txtTabla6.setWidth(200);
        txtTabla6.setHeight(x);
        row.addView(txtTabla6);

        tabla.addView(row);
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////77
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void paginacion(){

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (suma.getMin()>2 && suma.getPagina().equals("normal")) {
                    suma.setMin(suma.getMin() - 10);
                    suma.setMax(suma.getMax()-10);
                    altTableRow(suma.getMin(),suma.getMax());
                    //Cambio_color(4);
                }
                else if (suma.getMin()>2 && suma.getPagina().equals("final")) {
                    suma.setPagina("normal");
                    altTableRow(suma.getMin(),suma.getMax());
                    siguiente.setVisibility(View.VISIBLE);
                   // Cambio_color(4);
                }
            }
        });

        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                suma.setMin(suma.getMin() + 10);
                suma.setMax(suma.getMax()+10);

                if(suma.getMax()< suma.getTotal() && suma.getMin()< suma.getTotal()){
                    suma.setPagina("normal");
                    altTableRow(suma.getMin(),suma.getMax());
                    atras.setVisibility(View.VISIBLE);
                    //Cambio_color(4);
            }
            else if (suma.getMin()> suma.getTotal() || suma.getMax()> suma.getTotal()) {

                    //int ab=suma.getMax()-suma.getTotal();
                    //int cd=10-ab;

                    siguiente.setVisibility(View.INVISIBLE);
                    suma.setMax(suma.getMax()-10);
                    suma.setMin(suma.getMin()-10);
                    suma.setPagina("final");
                    altTableRow(suma.getTotal()-10, suma.getTotal());
                    //Cambio_color(4);

                }
            }
        });

    }

    public void finales(View v){

        nueva=(Button)v.findViewById(R.id.nueva_consulta);
        salir=(Button)v.findViewById(R.id.Salir);
    }

    public void visibilidad(Boolean a){
        if (a){
            nueva.setVisibility(View.VISIBLE);
            salir.setVisibility(View.VISIBLE);
        }else if (a==false) {
            nueva.setVisibility(View.GONE);
            salir.setVisibility(View.GONE);
        }
    }

    public void salir(){

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text_web = "<html><body style=\"text-align:justify; font-size:12px; line-height:20px; color:white;\"> %s </body></html>";
                String texto= "¿Seguro que desea salir de la aplicación?";
                web= new WebView(getContext());
                web.loadData(String.format(text_web, texto), "text/html; charset=utf-8","UTF-8");
                web.setBackgroundColor(Color.parseColor("#00FFFFFF"));

                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getActivity());
                dialogo1.setTitle("Importante");
                dialogo1.setCancelable(false);
                dialogo1.setView(web);

                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        int p = android.os.Process.myPid();
                        android.os.Process.killProcess(p);

                    }
                });
                dialogo1.setNegativeButton("Regresar Al Menu", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        Fragment fra= new prueba();
                        FragmentTransaction trans= getFragmentManager().beginTransaction();
                        getActivity().onBackPressed();// Elimina el fragment anterior
                        trans.replace(R.id.content_frame, fra);
                        trans.addToBackStack(null);
                        trans.commit();
                    }
                });
                dialogo1.show();
            }
        });

    }

    public void nueva(){

        nueva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fra= new Notificaciones();
                FragmentTransaction trans= getFragmentManager().beginTransaction();
                getActivity().onBackPressed();// Elimina el fragment anterior
                trans.replace(R.id.content_frame, fra);
                trans.addToBackStack(null);
                trans.commit();
            }
        });


    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void mensaje(){

        String text_web = "<html><body style=\"text-align:justify; font-size:12px; line-height:20px; color:white;\"> %s </body></html>";
        String texto= "Puede consultar en línea las listas de notificaciones que se han publicado en los organos jurisdiccionales.";
        web= new WebView(getContext());
        web.loadData(String.format(text_web, texto), "text/html; charset=utf-8","UTF-8");
        web.setBackgroundColor(Color.parseColor("#00FFFFFF"));

        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getActivity());
        dialogo1.setTitle("Importante");
        dialogo1.setCancelable(false);
        dialogo1.setView(web);

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
        dialogo1.show();



    }

    private void aceptar() {
        Toast.makeText(getActivity(),"Bienvenido Al Sistema ",Toast.LENGTH_LONG).show();

    }
    private void cancelar() {

        getActivity().finish();
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////7
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Notificaciones() {
        // Required empty public constructor
    }

    public static Notificaciones newInstance(String param1, String param2) {
        Notificaciones fragment = new Notificaciones();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_notificaciones, container, false);
        mensaje();
        inicio(v);
        llenado_spiners(v);
        spiner_dist();
        acciones();
        selector_fecha();
        consulta_final();
        finales(v);
        visibilidad(false);
        nueva();
        salir();
        paginacion();

        return v;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private class Noti_Normal extends AsyncTask<Void, Integer,Void>{
        String[][] da =new String[500][7];
        int veces=0;
        Con_sql conStr=new Con_sql();
        String Que_es="";
        String Resultado="";
        String Ex="";
        int x=0;
        String query="";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Que_es=datos.getMES();
            pDialog.setProgress(0);
            pDialog.show();

            pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    Noti_Normal.this.cancel(true);
                }
            });


            while (numero_expediente.getText().toString().length()<11) {
                String ejemplo = numero_expediente.getText().toString();
                ejemplo = "0" + ejemplo;
                numero_expediente.setText(ejemplo);
            }
            Ex =numero_expediente.getText().toString();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                connect =conStr.connection_notifica();

                if (connect == null)
                {
                    ConnectionResult = "Check Your Internet Access!";
                    Resultado="no";
                }
                else
                {
                    if(Que_es=="FECHA"){
                        query ="SELECT *  FROM Vta_ResiNotificaJuzgado where id_juzgado="+datos.getID()+" and  DATEPART(MONTH,[Fecha de Publicación])="+datos2.getMES()+" and DATEPART(YEAR,[Fecha de Publicación])="+datos2.getAÑO()+" and DATEPART(DAY,[Fecha de Publicación])="+datos2.getDIA()+" ORDER BY \"Fecha de Publicación\" DESC;";
                    }else if (Que_es=="EXPEDIENTE"){
                        query ="SELECT  *  FROM Vta_ResiNotificaJuzgado where id_juzgado="+datos.getID()+" and Número='"+Ex+"'";
                    }

                    Statement stmt = connect.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if(!rs.isBeforeFirst()){
                        ConnectionResult="No se encontraron Datos";
                        Resultado="no";
                    }
                    else {
                        while (rs.next()){
                            x=x+1;
                            veces=veces+1;
                            da[x][1]=rs.getString(1);
                            da[x][2]=rs.getString(2);
                            da[x][3]=rs.getString(3);
                            da[x][4]=rs.getString(4);
                            da[x][5]=rs.getString(5);
                            da[x][6]=rs.getString(8);

                        }
                    }
                }

            }
            catch (Exception ex)
            {
                esSatisfactorio = false;
                ConnectionResult = ex.getMessage();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            pDialog.dismiss();
            if (Resultado=="no"){
                Toast.makeText(getActivity(),ConnectionResult, Toast.LENGTH_LONG).show();

            }else{

                Encabezado2("No. de Notificación.","No.expediente","Fecha de Publicación.","Juicio.","Fecha de Resolución.","Síntesis.");
                Toast.makeText(getActivity(), "Tarea finalizada!", Toast.LENGTH_SHORT).show();
                txtdistrito.setVisibility(View.GONE);
                distrito.setVisibility(View.GONE);
                juzgado.setVisibility(View.GONE);
                opcion1.setVisibility(View.GONE);
                opcion2.setVisibility(View.GONE);
                no_expediente.setVisibility(View.GONE);
                no_causa.setVisibility(View.GONE);
                numero_expediente.setVisibility(View.GONE);
                numero_causa.setVisibility(View.GONE);
                consulta.setVisibility(View.GONE);
                ACTOPAN.setVisibility(View.GONE);
                APAN.setVisibility(View.GONE);
                HUEJUTLA.setVisibility(View.GONE);
                HUICHAPAN.setVisibility(View.GONE);
                IXMIQUILPAN.setVisibility(View.GONE);
                MIXQUIAHUALA.setVisibility(View.GONE);
                PACHUCA.setVisibility(View.GONE);
                TIZAYUCA.setVisibility(View.GONE);
                TULA.setVisibility(View.GONE);
                TULANCINGO.setVisibility(View.GONE);
                opc1.setVisibility(View.GONE);
                opc2.setVisibility(View.GONE);
                fecha.setVisibility(View.GONE);
                txtfcha.setVisibility(View.GONE);
                visibilidad(true);
                atras.setVisibility(View.VISIBLE);
                siguiente.setVisibility(View.VISIBLE);

                for (int i=1;i<=x;i++){
                    Encabezado3();
                    llenadoTabla2(da[i][1],da[i][2],da[i][3],da[i][6],da[i][4],da[i][5]);
                }

                Cambio_color(4);
                altTableRow(1,10);
                suma.setMin(1);
                suma.setMax(10);

            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int progreso = values[0].intValue();
            pDialog.setProgress(progreso);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Toast.makeText(getActivity(),"Tarea Cancelada!",Toast.LENGTH_LONG).show();
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private class Noti_penales extends AsyncTask<Void,Integer, Void>{

        String[][] da =new String[500][7];
        int veces=0;
        Con_sql conStr=new Con_sql();
        String Que_es="";
        String Resultado="";
        String Ex="";
        int x=0;
        String query="";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Que_es=datos.getMES();
            pDialog.setProgress(0);
            pDialog.show();


            pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    Noti_penales.this.cancel(true);
                }
            });

            while (numero_causa.getText().toString().length()<9){
                String ejemplo=numero_causa.getText().toString();
                ejemplo="0"+ejemplo;
                numero_causa.setText(ejemplo);

            Ex=numero_causa.getText().toString();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                connect =conStr.connection_notifica();


                if (connect == null)
                {
                    ConnectionResult = "Check Your Internet Access!";
                    Resultado="no";
                }
                else
                {
                    if(Que_es=="FECHA"){
                        query ="SELECT *  FROM Vta_ResiNotificaJuzgado where id_juzgado="+datos.getID()+" and  DATEPART(MONTH,[Fecha de Publicación])="+datos2.getMES()+" and DATEPART(YEAR,[Fecha de Publicación])="+datos2.getAÑO()+" and DATEPART(DAY,[Fecha de Publicación])="+datos2.getDIA()+" ORDER BY \"Fecha de Publicación\" DESC;";
                    }else if (Que_es=="EXPEDIENTE"){

                        query ="SELECT  *  FROM Vta_ResiNotificaJuzgado where id_juzgado="+datos.getID()+" and Número='"+Ex+"'";
                    }

                    //Toast.makeText(getActivity(),query, Toast.LENGTH_SHORT).show();


                    Statement stmt = connect.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if(!rs.isBeforeFirst()){
                        ConnectionResult="No se encontraron Datos";
                        Resultado="no";
                    }

                    else {

                        while (rs.next()){
                            x=x+1;

                            da[x][1]=rs.getString(1);
                            da[x][2]=rs.getString(2);
                            da[x][3]=rs.getString(3);
                            da[x][4]=rs.getString(4);
                            da[x][5]=rs.getString(5);
                            da[x][6]=rs.getString(8);

                        }
                    }

                }

            }catch (Exception ex)
            {
                esSatisfactorio = false;
                ConnectionResult = ex.getMessage();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            pDialog.dismiss();
            if (Resultado=="no"){

                Toast.makeText(getActivity(),ConnectionResult, Toast.LENGTH_LONG).show();

            }else{
                Toast.makeText(getActivity(), "Tarea finalizada!", Toast.LENGTH_SHORT).show();
                Encabezado2("No. de Notificación.","No.expediente","Fecha de Publicación.","Juicio.","Fecha de Resolución.","Síntesis.");

                txtdistrito.setVisibility(View.GONE);
                distrito.setVisibility(View.GONE);
                juzgado.setVisibility(View.GONE);
                opcion1.setVisibility(View.GONE);
                opcion2.setVisibility(View.GONE);
                no_expediente.setVisibility(View.GONE);
                no_causa.setVisibility(View.GONE);
                numero_expediente.setVisibility(View.GONE);
                numero_causa.setVisibility(View.GONE);
                consulta.setVisibility(View.GONE);
                ACTOPAN.setVisibility(View.GONE);
                APAN.setVisibility(View.GONE);
                HUEJUTLA.setVisibility(View.GONE);
                HUICHAPAN.setVisibility(View.GONE);
                IXMIQUILPAN.setVisibility(View.GONE);
                MIXQUIAHUALA.setVisibility(View.GONE);
                PACHUCA.setVisibility(View.GONE);
                TIZAYUCA.setVisibility(View.GONE);
                TULA.setVisibility(View.GONE);
                TULANCINGO.setVisibility(View.GONE);
                opc1.setVisibility(View.GONE);
                opc2.setVisibility(View.GONE);
                fecha.setVisibility(View.GONE);
                txtfcha.setVisibility(View.GONE);
                visibilidad(true);
                atras.setVisibility(View.VISIBLE);
                siguiente.setVisibility(View.VISIBLE);

                for (int i=1;i<=x;i++){
                    Encabezado3();
                    llenadoTabla2(da[i][1],da[i][2],da[i][3],da[i][6],da[i][4],da[i][5]);
                }

                Cambio_color(4);
                altTableRow(1,10);
                suma.setMin(1);
                suma.setMax(10);

            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int progreso = values[0].intValue();
            pDialog.setProgress(progreso);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Toast.makeText(getActivity(),"Tarea Cancelada!",Toast.LENGTH_LONG).show();
        }

    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private class Noti_Adolecentes extends AsyncTask<Void, Integer, Void>{
        String[][] da =new String[500][6];
        Con_sql conStr=new Con_sql();
        String Que_es="";
        String Resultado="";
        String Ex="";
        int x=0;
        String query="";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog.setProgress(0);
            pDialog.show();
            Que_es=datos.getMES();

            pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    Noti_Adolecentes.this.cancel(true);
                }
            });

            while (numero_expediente.getText().toString().length()<11){
                String ejemplo=numero_expediente.getText().toString();
                ejemplo="0"+ejemplo;
                numero_expediente.setText(ejemplo);

                Ex=numero_expediente.getText().toString();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                connect =conStr.connection_notifica();

                if (connect == null)
                {
                    ConnectionResult = "Check Your Internet Access!";
                    Resultado="no";
                }
                else
                {
                    if(Que_es=="FECHA"){
                        query ="SELECT *  FROM Vta_ResiNotificaJuzAdoles where id_juzgado="+datos.getID()+" and  DATEPART(MONTH,[Fecha de Publicación])="+datos2.getMES()+" and DATEPART(YEAR,[Fecha de Publicación])="+datos2.getAÑO()+" and DATEPART(DAY,[Fecha de Publicación])="+datos2.getDIA()+" ORDER BY \"Fecha de Publicación\" DESC;";
                    }else if (Que_es=="EXPEDIENTE"){

                        query ="SELECT  *  FROM Vta_ResiNotificaJuzAdoles where id_juzgado="+datos.getID()+" and Número='"+Ex+"'";
                    }

                    Statement stmt = connect.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if(!rs.isBeforeFirst()){
                        ConnectionResult="No se encontraron datos";
                        Resultado="no";
                    }

                    else {

                        while (rs.next()){
                            x=x+1;

                            da[x][1]=rs.getString(1);
                            da[x][2]=rs.getString(2);
                            da[x][3]=rs.getString(3);
                            da[x][4]=rs.getString(4);
                            da[x][5]=rs.getString(5);
                        }
                    }

                }

            }catch (Exception ex)
            {
                esSatisfactorio = false;
                ConnectionResult = ex.getMessage();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            pDialog.dismiss();
            if (Resultado=="no"){

                Toast.makeText(getActivity(),ConnectionResult, Toast.LENGTH_LONG).show();
                nueva();

            }else{

                Toast.makeText(getActivity(), "Tarea finalizada!", Toast.LENGTH_SHORT).show();
                Encabezado("No. de Notificación.","No.expediente","Fecha de Publicación.","Fecha de Resolución.","Síntesis.");

                txtdistrito.setVisibility(View.GONE);
                distrito.setVisibility(View.GONE);
                juzgado.setVisibility(View.GONE);
                opcion1.setVisibility(View.GONE);
                opcion2.setVisibility(View.GONE);
                no_expediente.setVisibility(View.GONE);
                no_causa.setVisibility(View.GONE);
                numero_expediente.setVisibility(View.GONE);
                numero_causa.setVisibility(View.GONE);
                consulta.setVisibility(View.GONE);
                ACTOPAN.setVisibility(View.GONE);
                APAN.setVisibility(View.GONE);
                HUEJUTLA.setVisibility(View.GONE);
                HUICHAPAN.setVisibility(View.GONE);
                IXMIQUILPAN.setVisibility(View.GONE);
                MIXQUIAHUALA.setVisibility(View.GONE);
                PACHUCA.setVisibility(View.GONE);
                TIZAYUCA.setVisibility(View.GONE);
                TULA.setVisibility(View.GONE);
                TULANCINGO.setVisibility(View.GONE);
                opc1.setVisibility(View.GONE);
                opc2.setVisibility(View.GONE);
                fecha.setVisibility(View.GONE);
                txtfcha.setVisibility(View.GONE);
                visibilidad(true);
                atras.setVisibility(View.VISIBLE);
                siguiente.setVisibility(View.VISIBLE);

                for (int i=1;i<=x;i++){
                   // Encabezado4("","","","","");
                    llenadoTabla(da[i][1],da[i][2],da[i][3],da[i][4],da[i][5]);
                }

                Cambio_color(4);
                altTableRow(1,10);
                suma.setMin(1);
                suma.setMax(10);

            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int progreso = values[0].intValue();
            pDialog.setProgress(progreso);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Toast.makeText(getActivity(),"Tarea Cancelada!",Toast.LENGTH_LONG).show();
        }
    }

}
