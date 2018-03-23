package com.curso.tinodeoz.portal_prueba;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;

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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class ubi_exp extends Fragment {


    DB base;

    Data_Portafolio nuevo_registro;

    Button nueva,salir,guardar;
    Connection connect;
    String ConnectionResult="";
    Boolean esSatisfactorio=false;


    WebView web, web2;
    TextView txt_juzgado,txt_exp, txt_distrito;
    EditText no_expediente;
    Button consulta;
    TableLayout tabla;


    Datos datos_consulta,Seleccion;

    Spinner distrito, juzgado1,juzgado2;
    String[] string_distrito={"Selecciona Aqui:","PACHUCA DE SOTO","TULANCINGO DE BRAVO"};
    String[] string_juzgado={"Selecciona Aqui:","PRIMERO CIVIL PACHUCA","SEGUNDO CIVIL PACHUCA","TERCERO CIVIL PACHUCA","CUARTO CIVIL PACHUCA",
            "PRIMERO MERCANTIL PACHUCA","SEGUNDO MERCANTIL PACHUCA","PRIMERO FAMILIAR PACHUCA","SEGUNDO FAMILIAR PACHUCA","TERCERO FAMILIAR PACHUCA"};
    String[] string_juzgado2={"Selecciona Aqui:","PRIMERO CIVIL Y FAMILIAR DE TULANCINGO","SEGUNDO CIVIL Y FAMILIAR DE TULANCINGO","TERCERO CIVIL Y FAMILIAR DE TULANCINGO"};


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ubi_exp() {
        // Required empty public constructor
    }

    public static ubi_exp newInstance(String param1, String param2) {
        ubi_exp fragment = new ubi_exp();
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
        View v=inflater.inflate(R.layout.fragment_ubi_exp, container, false);
        inicio(v);



        String text_web = "<html><body style=\"text-align:justify; font-size:12px; line-height:20px; color:white;\"> %s </body></html>";
        String texto="Este servicio es de carácter informativo y está orientado a los abogados o justiciables respecto de los asuntos en los que tienen participación, por lo que se deberá de conocer el número de expediente, exhorto o tercería y el juzgado de radicación para que pueda servir para establecer en dónde se halla un expediente al momento mismo en que se hace la consulta, ya sea una área del juzgado (comisaría, actuaría, secretaría de acuerdos, etc.) o en otra instancia. <br>Ello permite deducir si es que el trámite del expediente guarda un estado específico, como si una promoción ya fue acordada y ya se encuentra lista para ser notificada; si un expediente permanece en el archivo central (archivo de concentración) o si ya fue ha sido devuelto al juzgado, o si un expediente permanece en la segunda instancia o si ya ha regresado al juzgado.";
        web= new WebView(getContext());
        web.loadData(String.format(text_web, texto), "text/html; charset=utf-8","UTF-8");
        web.setBackgroundColor(Color.parseColor("#00FFFFFF"));

        String text = "<html><body style=\"text-align:justify; font-size:14px; line-height:20px; background-color:#72ffffff;\"> %s </body></html>";
        String info=getString(R.string.nota);

        web2.loadData(String.format(text, info), "text/html; charset=utf-8","UTF-8");
        web2.setBackgroundColor(Color.parseColor("#00FFFFFF"));


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


        base= new DB(getActivity());
        llenado_spiners(v);
        acciones(v);
        finales(v);
        nueva();
        salir();
        visibilidad(false);
        guardar(v);
        return v;

    }



    public void mensaje(){

        String text_web = "<html><body style=\"text-align:justify; font-size:12px; line-height:20px; color:white;\"> %s </body></html>";
        String texto= "¿Esta Seguro De Guardar Y Salir?";
        WebView web2= new WebView(getContext());
        web2.loadData(String.format(text_web, texto), "text/html; charset=utf-8","UTF-8");
        web2.setBackgroundColor(Color.parseColor("#00FFFFFF"));

        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getActivity());
        dialogo1.setTitle("Confirmar");
        dialogo1.setCancelable(false);
        dialogo1.setView(web2);

        dialogo1.setPositiveButton("Guardar y Salir", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                base.borrar(nuevo_registro.getExpediente(),nuevo_registro.getJuzgado());
                base.Agregar_portafolio(nuevo_registro);
                base.closeDB();
                aceptar2();
            }
        });
        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                dialogo1.cancel();
                //cancelar2();
            }
        });
        dialogo1.show();



    }


    private void aceptar2() {
        Toast.makeText(getActivity(),"Guardado Satisfactoriamente.",Toast.LENGTH_LONG).show();
        Fragment fra= new prueba();
        FragmentTransaction trans= getFragmentManager().beginTransaction();
        getActivity().onBackPressed();// Elimina el fragment anterior
        trans.replace(R.id.content_frame, fra);
        trans.addToBackStack(null);
        trans.commit();
    }
    private void cancelar2() {
        getActivity().finish();

    }

    public void finales(View v){

        guardar=(Button)v.findViewById(R.id.guardar);
        guardar.setVisibility(View.GONE);

        nueva=(Button)v.findViewById(R.id.nueva_consulta);
        salir=(Button)v.findViewById(R.id.Salir);

        nueva.setVisibility(View.GONE);
        salir.setVisibility(View.GONE);
    }

    public void visibilidad(Boolean a){
        if (a){
            nueva.setVisibility(View.VISIBLE);
            salir.setVisibility(View.VISIBLE);
            guardar.setVisibility(View.VISIBLE);
        }else if (a==false) {
            nueva.setVisibility(View.GONE);
            salir.setVisibility(View.GONE);
            guardar.setVisibility(View.GONE);
        }
    }


    public void salir(){
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fra= new prueba();
                FragmentTransaction trans= getFragmentManager().beginTransaction();
                getActivity().onBackPressed();// Elimina el fragment anterior
                trans.replace(R.id.content_frame, fra);
                trans.addToBackStack(null);
                trans.commit();
            }
        });

    }


    public void nueva(){

        nueva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fra= new ubi_exp();
                FragmentTransaction trans= getFragmentManager().beginTransaction();
                getActivity().onBackPressed();// Elimina el fragment anterior
                trans.replace(R.id.content_frame, fra);
                trans.addToBackStack(null);
                trans.commit();
            }
        });


    }


    public void guardar(final View vv){
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mensaje();
            }
        });

    }


    public void inicio(View v){
        txt_distrito=(TextView)v.findViewById(R.id.txt_distrito);
        txt_juzgado=(TextView)v.findViewById(R.id.txt_juzgado);
        distrito=(Spinner)v.findViewById(R.id.spinner_distrito);
        juzgado1=(Spinner)v.findViewById(R.id.spinner_juzgado);
        juzgado2=(Spinner)v.findViewById(R.id.spinner_juzgado2);

        txt_exp=(TextView)v.findViewById(R.id.txt_expediente);
        consulta=(Button)v.findViewById(R.id.consultar);
        web2 = (WebView) v.findViewById(R.id.WebView_nota);
        no_expediente=(EditText)v.findViewById(R.id.txt_no_expediente) ;
        tabla=(TableLayout)v.findViewById(R.id.Tabla_btn1);

        txt_juzgado.setVisibility(View.GONE);
        juzgado1.setVisibility(View.GONE);
        juzgado2.setVisibility(View.GONE);
        txt_exp.setVisibility(View.GONE);
        web2.setVisibility(View.GONE);
        no_expediente.setVisibility(View.GONE);
        consulta.setVisibility(View.GONE);


    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static String agregarCeros(String string, int largo)
    {
        String ceros = "";

        int cantidad = largo - string.length();

        if (cantidad >= 1)
        {
            for(int i=0;i<cantidad;i++)
            {
                ceros += "0";
            }

            return (ceros + string);
        }
        else
            return string;
    }



    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void llenado_spiners(View v){
        ArrayAdapter<String> adaptador=  new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,string_distrito);
        distrito.setAdapter(adaptador);

        ArrayAdapter<String> adaptador2=  new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,string_juzgado);
        juzgado1.setAdapter(adaptador2);

        ArrayAdapter<String> adaptador3=  new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,string_juzgado2);
        juzgado2.setAdapter(adaptador3);
    }

    public void acciones(final View v){

        distrito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==1){

                    txt_juzgado.setVisibility(View.VISIBLE);
                    juzgado1.setVisibility(View.VISIBLE);
                    juzgado2.setVisibility(View.GONE);
                    nuevo_registro= new Data_Portafolio();
                    nuevo_registro.setDistrito("Pachuca de Soto.");
                    Seleccion =new Datos();
                    Seleccion.setID("1");


                } else if (position==2) {
                    txt_juzgado.setVisibility(View.VISIBLE);
                    juzgado2.setVisibility(View.VISIBLE);
                    juzgado1.setVisibility(View.GONE);
                    nuevo_registro= new Data_Portafolio();
                    nuevo_registro.setDistrito("Tulancingo de Bravo.");
                    Seleccion =new Datos();
                    Seleccion.setID("2");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        juzgado2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==1){
                    seleccion_juzgado(v);
                    datos_consulta =new Datos();
                    datos_consulta.setID("28");
                    nuevo_registro.setJuzgado("PRIMERO CIVIL Y FAMILIAR DE TULANCINGO");
                    nuevo_registro.setIDJuzgado("28");

                } else if (position==2) {
                    seleccion_juzgado(v);
                    datos_consulta =new Datos();
                    datos_consulta.setID("30");
                    nuevo_registro.setJuzgado("SEGUNDO CIVIL Y FAMILIAR DE TULANCINGO");
                    nuevo_registro.setIDJuzgado("30");

                }
                else if (position==3) {
                    seleccion_juzgado(v);
                    datos_consulta =new Datos();
                    datos_consulta.setID("48");
                    nuevo_registro.setJuzgado("TERCERO CIVIL Y FAMILIAR DE TULANCINGO");
                    nuevo_registro.setIDJuzgado("48");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        juzgado1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position==1){
            seleccion_juzgado(v);
            datos_consulta =new Datos();
            datos_consulta.setID("1");
            nuevo_registro.setJuzgado("PRIMERO CIVIL PACHUCA");
            nuevo_registro.setIDJuzgado("1");



        } else if (position==2) {
            seleccion_juzgado(v);
            datos_consulta =new Datos();
            datos_consulta.setID("2");
            nuevo_registro.setJuzgado("SEGUNDO CIVIL PACHUCA");
            nuevo_registro.setIDJuzgado("2");
        }
        else if (position==3) {
            seleccion_juzgado(v);
            datos_consulta =new Datos();
            datos_consulta.setID("3");
            nuevo_registro.setJuzgado("TERCERO CIVIL PACHUCA");
            nuevo_registro.setIDJuzgado("3");

        }
        else if (position==4) {
            seleccion_juzgado(v);
            datos_consulta =new Datos();
            datos_consulta.setID("36");
            nuevo_registro.setJuzgado("CUARTO CIVIL PACHUCA");
            nuevo_registro.setIDJuzgado("36");
        }
        else if (position==5) {
            seleccion_juzgado(v);
            datos_consulta =new Datos();
            datos_consulta.setID("38");
            nuevo_registro.setJuzgado("PRIMERO MERCANTIL PACHUCA");
            nuevo_registro.setIDJuzgado("38");
        }
        else if (position==6) {
            seleccion_juzgado(v);
            datos_consulta =new Datos();
            datos_consulta.setID("46");
            nuevo_registro.setJuzgado("SEGUNDO MERCANTIL PACHUCA");
            nuevo_registro.setIDJuzgado("46");
        }
        else if (position==7) {
            seleccion_juzgado(v);
            datos_consulta =new Datos();
            datos_consulta.setID("5");
            nuevo_registro.setJuzgado("PRIMERO FAMILIAR PACHUCA");
            nuevo_registro.setIDJuzgado("5");
        }
        else if (position==8) {
            seleccion_juzgado(v);
            datos_consulta =new Datos();
            datos_consulta.setID("40");
            nuevo_registro.setJuzgado("SEGUNDO FAMILIAR PACHUCA");
            nuevo_registro.setIDJuzgado("40");
        }
        else if (position==9) {
            seleccion_juzgado(v);
            datos_consulta =new Datos();
            datos_consulta.setID("52");
            nuevo_registro.setJuzgado("TERCERO FAMILIAR PACHUCA");
            nuevo_registro.setIDJuzgado("52");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
});


        consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               exp_consulta();
           }
        });
    }


    public void exp_consulta(){
        String[] datos =new String[3];
        try {
            if (Seleccion.getID()=="1") {
                Con_sql conStr = new Con_sql();
                connect = conStr.connections();
                Toast.makeText(getActivity(),"Esperé unos segundos...", Toast.LENGTH_SHORT).show();

            }else if (Seleccion.getID()=="2") {
                Con_sql conStr = new Con_sql();
                connect = conStr.connectionstulancingo();
                Toast.makeText(getActivity(),"Esperé unos segundos...", Toast.LENGTH_SHORT).show();
            }

            if (connect == null){
                ConnectionResult = "Check Your Internet Access!";
                Toast.makeText(getActivity(),ConnectionResult, Toast.LENGTH_SHORT).show();
            }
            else {
                while (no_expediente.getText().toString().length()<11){
                    String ejemplo=no_expediente.getText().toString();
                    ejemplo="0"+ejemplo;
                    no_expediente.setText(ejemplo);
                }
                String query = "Select Perfil,CONVERT(VARCHAR, Fecha, 105)Fecha,Motivo,Expediente,IdJuzgado from Vta_ResiUbicacion Where IdJuzgado="+datos_consulta.getID()+ "and Expediente='"+no_expediente.getText().toString()+"'Order by Fecha DESC;";
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if(!rs.isBeforeFirst()){
                    Toast.makeText(getActivity(),"No se encontraron Datos", Toast.LENGTH_SHORT).show();
                }else {
                    Encabezado("Fecha","Area");
                    txt_juzgado.setVisibility(View.GONE);
                    txt_distrito.setVisibility(View.GONE);
                    distrito.setVisibility(View.GONE);
                    juzgado1.setVisibility(View.GONE);
                    juzgado2.setVisibility(View.GONE);
                    txt_exp.setVisibility(View.GONE);
                    web2.setVisibility(View.GONE);
                    no_expediente.setVisibility(View.GONE);
                    consulta.setVisibility(View.GONE);
                    visibilidad(true);


                    while (rs.next()){

                        datos[1]=rs.getString("Fecha");
                        datos[2]=rs.getString("Perfil");

                        nuevo_registro.setExpediente(rs.getString("Expediente"));
                        nuevo_registro.setUbicacion(datos[2]);
                        nuevo_registro.setFecha(datos[1]);

                        llenadoTabla(datos[1],datos[2]);
                    }
                }

            }
            //Toast.makeText(getActivity(),datos[1], Toast.LENGTH_SHORT).show();

        }catch (Exception ex)
        {
            esSatisfactorio = false;
            ConnectionResult = ex.getMessage();
        }
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public void llenadoTabla(String txt1,String txt2){
    TableRow.LayoutParams layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
            TableRow.LayoutParams.WRAP_CONTENT);

    TableRow row= new TableRow(getActivity());
    row.setLayoutParams(layoutFila);
    row.setGravity(Gravity.CENTER_VERTICAL);


    TextView txtTabla, txtTabla2;

    txtTabla=new TextView(getActivity());
    txtTabla.setGravity(Gravity.CENTER_HORIZONTAL);
    txtTabla.setBackgroundColor(Color.TRANSPARENT);
    txtTabla.setText(txt1);
    txtTabla.setTextColor(Color.parseColor("#B1613e"));
    txtTabla.setWidth(500);
    row.addView(txtTabla);

    txtTabla2=new TextView(getActivity());
    txtTabla2.setGravity(Gravity.CENTER_HORIZONTAL);
    txtTabla2.setBackgroundColor(Color.TRANSPARENT);
    txtTabla2.setText(txt2);
    txtTabla2.setTextColor(Color.parseColor("#B1613e"));
    txtTabla2.setWidth(500);

    row.addView(txtTabla2);
    tabla.addView(row);

    TableRow borde= new TableRow(getActivity());
    row.setLayoutParams(layoutFila);


    TextView borde1, borde2;

    borde1=new TextView(getActivity());
    borde1.setBackgroundColor(Color.parseColor("#000000"));
    borde1.setWidth(500);
    borde1.setHeight(10);
    borde.addView(borde1);

    borde2=new TextView(getActivity());
    borde2.setBackgroundColor(Color.parseColor("#000000"));
    borde2.setWidth(500);
    borde2.setHeight(10);
    borde.addView(borde2);

   tabla.addView(borde);
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////77
public void Encabezado(String txt1,String txt2){
    TableRow.LayoutParams layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
            TableRow.LayoutParams.WRAP_CONTENT);

    TableRow row= new TableRow(getActivity());
    row.setLayoutParams(layoutFila);


    TextView txtTabla, txtTabla2;

    txtTabla=new TextView(getActivity());
    txtTabla.setGravity(Gravity.CENTER_HORIZONTAL);

    txtTabla.setBackgroundColor(Color.parseColor("#B1613e"));
    txtTabla.setText(txt1);
    txtTabla.setTextColor(Color.WHITE);
    txtTabla.setWidth(500);
    row.addView(txtTabla);


    txtTabla2=new TextView(getActivity());
    txtTabla2.setGravity(Gravity.CENTER_HORIZONTAL);
    txtTabla2.setBackgroundColor(Color.parseColor("#B1613e"));
    txtTabla2.setText(txt2);
    txtTabla2.setTextColor(Color.WHITE);
    txtTabla2.setWidth(500);

    row.addView(txtTabla2);
    tabla.addView(row);
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////77
    public void seleccion_juzgado(View v){
    txt_exp.setVisibility(View.VISIBLE);
    web2.setVisibility(View.VISIBLE);
    no_expediente.setVisibility(View.VISIBLE);
    consulta.setVisibility(View.VISIBLE);

    }

    private void aceptar() {
        Toast.makeText(getActivity(),"Bienvenido Al Sistema ",Toast.LENGTH_LONG).show();

    }
    private void cancelar() {

        getActivity().finish();
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////7777

    // TODO: Rename method, update argument and hook method into UI event
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

}