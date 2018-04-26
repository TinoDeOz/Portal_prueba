package com.curso.tinodeoz.portal_prueba;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
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

import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class AudienciasOrales extends Fragment {

    Connection connect;
    String ConnectionResult="";
    Boolean esSatisfactorio=false;


    TableLayout tabla;
    Datos datos_consulta, datos_consulta2,datos_consulta3, Seleccion;

    TextView txt_año,txt_mes,txt_distrito;


    WebView web;
    Button consulta;
    Spinner distrito, año,mes;
    ProgressDialog pDialog;

    String[] string_distrito={"Selecciona Aqui:","PACHUCA DE SOTO","TULA DE ALLENDE","TULANCINGO DE BRAVO"};
    String[] string_mes={"Selecciona Aqui:","ENERO","FEBRERO","MARZO","ABRIL","MAYO","JUNIO","JULIO", "AGOSTO","SEPTIEMBRE","OCTUBRE","NOVIEMBRE","DICIEMBRE"};
    String[] string_año={"Selecciona Aqui:","2018","2017","2016","2015","2014"};





    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AudienciasOrales() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AudienciasOrales newInstance(String param1, String param2) {
        AudienciasOrales fragment = new AudienciasOrales();
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
        View v=inflater.inflate(R.layout.fragment_audiencias_orales, container, false);
        mensaje();
        inicio(v);
        llenado_spiners(v);
        acciones();

        finales(v);
        nueva();
        salir();
        visibilidad(false);

        return v;
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void mensaje(){

        String text_web = "<html><body style=\"text-align:justify; font-size:12px; line-height:20px; color:white;\"> %s </body></html>";
        String texto= "La información aquí advertida, es resultado de los registros que obran en el Sistema de Información Jurisdiccional del Estado de Hidalgo (SIJEH), hasta este momento.";
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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////7
    public void inicio(View v){

        consulta=(Button)v.findViewById(R.id.btn_audienica_consultar);
        tabla=(TableLayout)v.findViewById(R.id.Tabla_btn_orales);



        distrito=(Spinner)v.findViewById(R.id.spinner_audiencia_distrito);
        año=(Spinner)v.findViewById(R.id.spinner_audiencia_año);
        mes=(Spinner)v.findViewById(R.id.spinner_audiencia_mes);

        txt_distrito=(TextView)v.findViewById(R.id.txt_audiencia_distrito);
        txt_año=(TextView)v.findViewById(R.id.txt_audiencia_año);
        txt_mes=(TextView)v.findViewById(R.id.txt_audiencia_mes);

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    Button nueva,salir;
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
                Fragment fra= new AudienciasOrales();
                FragmentTransaction trans= getFragmentManager().beginTransaction();
                getActivity().onBackPressed();// Elimina el fragment anterior
                trans.replace(R.id.content_frame, fra);
                trans.addToBackStack(null);
                trans.commit();
            }
        });


    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void llenado_spiners(View v){
        ArrayAdapter<String> adaptador=  new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,string_distrito);
        distrito.setAdapter(adaptador);

        ArrayAdapter<String> adaptador2=  new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,string_año);
        año.setAdapter(adaptador2);

        ArrayAdapter<String> adaptador3=  new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,string_mes);
        mes.setAdapter(adaptador3);
    }



    public void acciones(){

        distrito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==1) {

                    datos_consulta =new Datos();
                    datos_consulta.setID("IN(1,2,3,4,5,7,8,9,36,37,38,40,46,50,52,53,54,55,56,57,58,59,60,61,62,64,67,68,69,86,88,89,90,97,98)");
                    datos_consulta.setQUE_ES("PACHUCA DE SOTO.");
                    Seleccion =new Datos();
                    Seleccion.setID("1");

                }else if (position==2){

                    datos_consulta =new Datos();
                    datos_consulta.setID("IN(26,27,33,39,49,75)");
                    datos_consulta.setQUE_ES("TULA DE ALLENDE.");
                    Seleccion =new Datos();
                    Seleccion.setID("3");


                }else if (position==3) {

                    datos_consulta =new Datos();
                    datos_consulta.setID("IN(28,29,30,34,48,71)");
                    datos_consulta.setQUE_ES("TULANCINGO DE BRAVO.");
                    Seleccion =new Datos();
                    Seleccion.setID("2");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        año.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==1) {
                    datos_consulta2 =new Datos();
                    datos_consulta2.setAÑO("2018");

                }else if (position==2){

                    datos_consulta2 =new Datos();
                    datos_consulta2.setAÑO("2017");

                }else if (position==3) {

                    datos_consulta2 =new Datos();
                    datos_consulta2.setAÑO("2016");

                }
                else if (position==4){
                    datos_consulta2 =new Datos();
                    datos_consulta2.setAÑO("2015");
                }
                else if (position==5){
                    datos_consulta2 =new Datos();
                    datos_consulta2.setAÑO("2014");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==1) {
                    datos_consulta3 =new Datos();
                    datos_consulta3.setMES("1");
                    datos_consulta3.setQUE_ES("ENERO");

                }else if (position==2){

                    datos_consulta3 =new Datos();
                    datos_consulta3.setMES("2");
                    datos_consulta3.setQUE_ES("FEBRERO");

                }else if (position==3){

                    datos_consulta3 =new Datos();
                    datos_consulta3.setMES("3");
                    datos_consulta3.setQUE_ES("MARZO");

                }else if (position==4){

                    datos_consulta3 =new Datos();
                    datos_consulta3.setMES("4");
                    datos_consulta3.setQUE_ES("ABRIL");

                }else if (position==5){

                    datos_consulta3 =new Datos();
                    datos_consulta3.setMES("5");
                    datos_consulta3.setQUE_ES("MAYO");

                }else if (position==6){

                    datos_consulta3 =new Datos();
                    datos_consulta3.setMES("6");
                    datos_consulta3.setQUE_ES("JUNIO");

                }else if (position==7){

                    datos_consulta3 =new Datos();
                    datos_consulta3.setMES("7");
                    datos_consulta3.setQUE_ES("JULIO");

                }else if (position==8){

                    datos_consulta3 =new Datos();
                    datos_consulta3.setMES("8");
                    datos_consulta3.setQUE_ES("AGOSTO");
                }else if (position==9){

                    datos_consulta3 =new Datos();
                    datos_consulta3.setMES("9");
                    datos_consulta3.setQUE_ES("SEPTIEMBRE");

                }else if (position==10){

                    datos_consulta3 =new Datos();
                    datos_consulta3.setMES("10");
                    datos_consulta3.setQUE_ES("OCTUBRE");

                }else if (position==11){

                    datos_consulta3 =new Datos();
                    datos_consulta3.setMES("11");
                    datos_consulta3.setQUE_ES("NOVIEMBRE");

                }else if (position==12){

                    datos_consulta3 =new Datos();
                    datos_consulta3.setMES("12");
                    datos_consulta3.setQUE_ES("DICIEMBRE");

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        consulta.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                pDialog = new ProgressDialog(getActivity(),R.style.MyAlertDialogStyle);
                pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pDialog.setMessage("Procesando...");
                pDialog.setCancelable(true);
                pDialog.setMax(100);

                Consulta_AO AO=new Consulta_AO();
                AO.execute();

               /* String[] datos =new String[5];
                int x=0;
                try {

                    if (Seleccion.getID()=="1") {
                        Con_sql conStr = new Con_sql();
                        connect = conStr.connections();

                    }else if (Seleccion.getID()=="2") {
                        Con_sql conStr = new Con_sql();
                        connect = conStr.connectionstulancingo();
                    }

                    if (connect == null)
                    {
                        ConnectionResult = "Check Your Internet Access!";
                        Toast.makeText(getActivity(),ConnectionResult, Toast.LENGTH_SHORT).show();

                    }
                    else
                    {

                        Toast.makeText(getActivity(),"Esperé unos segundos...", Toast.LENGTH_SHORT).show();

                        String query = "SELECT * FROM Vta_ResiAudiOralMercantil where DATEPART(MONTH, FechaAudi)="+datos_consulta3.getMES()+ " and DATEPART(YEAR,FechaAudi)="+datos_consulta2.getAÑO()+" and IdJuzgado "+datos_consulta.getID();
                        Statement stmt = connect.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if(!rs.isBeforeFirst()){
                            Toast.makeText(getActivity(),"No se encontraron Datos", Toast.LENGTH_SHORT).show();
                        }

                        else {
                            Encabezado("#","No.expediente","Juzgado","Fecha y hora de la audiencia","Tipo de audiencia.");
                            txt_distrito.setText("AUDIENCIAS ORALES EN MATERIA MERCANTIL PARA EL MES DE "+datos_consulta3.getQUE_ES() +" DEL AÑO "+datos_consulta2.getAÑO()+"  DEL DISTRITO JUDICIAL DE "+datos_consulta.getQUE_ES());
                            txt_distrito.setPadding(50,50,50,0);



                            txt_mes.setVisibility(View.GONE);
                            distrito.setVisibility(View.GONE);
                            txt_año.setVisibility(View.GONE);
                            año.setVisibility(View.GONE);
                            mes.setVisibility(View.GONE);
                            consulta.setVisibility(View.GONE);
                            visibilidad(true);


                            while (rs.next()){
                                x=x+1;
                                datos[1]=rs.getString("Expediente");
                                datos[2]=rs.getString("Juzgado");
                                datos[3]=rs.getString("FechaAudi");
                                datos[4]=rs.getString("Tipo");

                                llenadoTabla(String.valueOf(x),datos[1],datos[2],datos[3],datos[4]);
                            }
                        }

                    }
                    Cambio_color(4);

                }catch (Exception ex)
                {
                    esSatisfactorio = false;
                    ConnectionResult = ex.getMessage();
                }
*/
            }
        });



    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////77
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public void Cambio_color(int alt_row) {
    int childViewCount = tabla.getChildCount();

    for (int i = 1; i < childViewCount; i++) {
        TableRow row = (TableRow) tabla.getChildAt(i);

        for (int j = 0; j < row.getChildCount(); j++) {

            TextView tv = (TextView) row.getChildAt(j);
            if (i % 4 != 1) {
                // tv.setBackground(getResources().getDrawable(R.drawable.alt_row_color));
                tv.setTextColor(Color.parseColor("#B1613e"));
            } else {
                //tv.setBackground(getResources().getDrawable(R.drawable.row_color));
                tv.setTextColor(Color.BLACK);
            }
        }
    }
}

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public void llenadoTabla(String txt1,String txt2,String txt3,String txt4,String txt5){
    TableRow.LayoutParams layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
            TableRow.LayoutParams.WRAP_CONTENT);

    TableRow row= new TableRow(getActivity());
    row.setLayoutParams(layoutFila);
    row.setGravity(Gravity.CENTER_VERTICAL);


    TextView txtTabla, txtTabla2,txtTabla3,txtTabla4,txtTabla5;



    txtTabla=new TextView(getActivity());
    txtTabla.setGravity(Gravity.CENTER_VERTICAL);
    txtTabla.setBackgroundColor(Color.TRANSPARENT);
    txtTabla.setText(txt1);
    txtTabla.setTextColor(Color.parseColor("#B1613e"));
    txtTabla.setWidth(50);
    row.addView(txtTabla);


    txtTabla2=new TextView(getActivity());
    txtTabla2.setGravity(Gravity.CENTER);
    txtTabla2.setBackgroundColor(Color.TRANSPARENT);
    txtTabla2.setText(txt2);
    txtTabla2.setTextColor(Color.parseColor("#B1613e"));
    txtTabla2.setWidth(400);
    row.addView(txtTabla2);


    txtTabla3=new TextView(getActivity());
    txtTabla3.setGravity(Gravity.CENTER);
    txtTabla3.setBackgroundColor(Color.TRANSPARENT);
    txtTabla3.setText(txt3);
    txtTabla3.setTextColor(Color.parseColor("#B1613e"));
    txtTabla3.setWidth(400);
    row.addView(txtTabla3);


    txtTabla4=new TextView(getActivity());
    txtTabla4.setGravity(Gravity.CENTER);
    txtTabla4.setBackgroundColor(Color.TRANSPARENT);
    txtTabla4.setText(txt4);
    txtTabla4.setTextColor(Color.parseColor("#B1613e"));
    txtTabla4.setWidth(400);
    row.addView(txtTabla4);


    txtTabla5=new TextView(getActivity());
    txtTabla5.setGravity(Gravity.CENTER);
    txtTabla5.setBackgroundColor(Color.TRANSPARENT);
    txtTabla5.setText(txt5);
    txtTabla5.setTextColor(Color.parseColor("#B1613e"));
    txtTabla5.setWidth(400);
    row.addView(txtTabla5);

    tabla.addView(row);

    TableRow borde= new TableRow(getActivity());
    row.setLayoutParams(layoutFila);

    TextView borde1, borde2,borde3,borde4,borde5,border1;

    borde1=new TextView(getActivity());
    borde1.setBackgroundColor(Color.parseColor("#000000"));
    borde1.setWidth(50);
    borde1.setHeight(10);
    borde.addView(borde1);


    borde2=new TextView(getActivity());
    borde2.setBackgroundColor(Color.parseColor("#000000"));
    borde2.setWidth(400);
    borde2.setHeight(10);
    borde.addView(borde2);

    borde3=new TextView(getActivity());
    borde3.setBackgroundColor(Color.parseColor("#000000"));
    borde3.setWidth(400);
    borde3.setHeight(10);
    borde.addView(borde3);

    borde4=new TextView(getActivity());
    borde4.setBackgroundColor(Color.parseColor("#000000"));
    borde4.setWidth(400);
    borde4.setHeight(10);
    borde.addView(borde4);

    borde5=new TextView(getActivity());
    borde5.setBackgroundColor(Color.parseColor("#000000"));
    borde5.setWidth(400);
    borde5.setHeight(10);
    borde.addView(borde5);

    //tabla.addView(row ,new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
    tabla.addView(borde);
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////77
    public void Encabezado(String txt1,String txt2,String txt3,String txt4,String txt5){
        TableRow.LayoutParams layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        Resources res = getResources();



        int x=130;

        TableRow row= new TableRow(getActivity());
        row.setLayoutParams(layoutFila);


        TextView txtTabla, txtTabla2,txtTabla3,txtTabla4,txtTabla5,txtTabla6;

        txtTabla=new TextView(getActivity());
        txtTabla.setGravity(Gravity.CENTER_HORIZONTAL);
        txtTabla.setBackgroundColor(Color.parseColor("#B1613e"));
        txtTabla.setText(txt1);
        txtTabla.setTextColor(Color.WHITE);
        txtTabla.setWidth(50);
        txtTabla.setHeight(x);
        row.addView(txtTabla);




        txtTabla2=new TextView(getActivity());
        txtTabla2.setGravity(Gravity.CENTER_HORIZONTAL);
        txtTabla2.setBackgroundColor(Color.parseColor("#B1613e"));
        txtTabla2.setText(txt2);
        txtTabla2.setTextColor(Color.WHITE);
        txtTabla2.setWidth(400);
        txtTabla2.setHeight(x);
        row.addView(txtTabla2);


        txtTabla3=new TextView(getActivity());
        txtTabla3.setGravity(Gravity.CENTER_HORIZONTAL);
        txtTabla3.setBackgroundColor(Color.parseColor("#B1613e"));
        txtTabla3.setText(txt3);
        txtTabla3.setTextColor(Color.WHITE);
        txtTabla3.setWidth(400);
        txtTabla3.setHeight(x);
        row.addView(txtTabla3);

        txtTabla4=new TextView(getActivity());
        txtTabla4.setGravity(Gravity.CENTER_HORIZONTAL);
        txtTabla4.setBackgroundColor(Color.parseColor("#B1613e"));
        txtTabla4.setText(txt4);
        txtTabla4.setTextColor(Color.WHITE);
        txtTabla4.setWidth(400);
        txtTabla4.setHeight(x);
        row.addView(txtTabla4);

        txtTabla5=new TextView(getActivity());
        txtTabla5.setGravity(Gravity.CENTER_HORIZONTAL);
        txtTabla5.setBackgroundColor(Color.parseColor("#B1613e"));
        txtTabla5.setText(txt5);
        txtTabla5.setTextColor(Color.WHITE);
        txtTabla5.setWidth(400);
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

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////77

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

    private class Consulta_AO extends AsyncTask<Void, Integer, Void>{
        String Obj_mes="";
        String Obj_año="";
        String Obj_id="";
        String Selec_ID="";
        String[][] datos =new String[100][5];
        int x=0;
        Con_sql conStr = new Con_sql();
        String Resultado="";
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////7
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Selec_ID=Seleccion.getID();
            Obj_mes=datos_consulta3.getQUE_ES();
            Obj_año=datos_consulta2.getQUE_ES();
            Obj_id=datos_consulta.getID();
            pDialog.setProgress(0);
            pDialog.show();

            pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    Consulta_AO.this.cancel(true);
                }
            });
        }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        @Override
        protected Void doInBackground(Void... params) {

            try {

                if (Selec_ID=="1") {
                    connect = conStr.connections();

                }else if (Selec_ID=="2") {

                    connect = conStr.connectionstulancingo();
                }else if (Selec_ID=="3") {

                    connect = conStr.connection_tula();
                }

                if (connect == null)
                {
                    ConnectionResult = "Check Your Internet Access!";
                    Resultado="no";
                }
                else
                {
                    //String query = "SELECT * FROM Vta_ResiAudiOralMercantil where DATEPART(MONTH, FechaAudi)="+Obj_mes+ " and DATEPART(YEAR,FechaAudi)="+Obj_año+" and IdJuzgado "+Obj_id;
                    String query = "SELECT * FROM Vta_ResiAudiOralMercantil where DATEPART(MONTH, FechaAudi)="+datos_consulta3.getMES()+ " and DATEPART(YEAR,FechaAudi)="+datos_consulta2.getAÑO()+" and IdJuzgado "+datos_consulta.getID();
                    Statement stmt = connect.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if(!rs.isBeforeFirst()){
                        Resultado="no";
                        ConnectionResult = "No se encontraron datos.";
                    }

                    else {

                        Resultado="si";

                        while (rs.next()){
                            x=x+1;
                            datos[x][1]=rs.getString("Expediente");
                            datos[x][2]=rs.getString("Juzgado");
                            datos[x][3]=rs.getString("FechaAudi");
                            datos[x][4]=rs.getString("Tipo");
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
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int progreso = values[0].intValue();
            pDialog.setProgress(progreso);
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pDialog.dismiss();

            if (Resultado=="no"){
                Toast.makeText(getActivity(),ConnectionResult, Toast.LENGTH_LONG).show();

            }else if(Resultado=="si") {
                Toast.makeText(getActivity(), "Tarea finalizada!", Toast.LENGTH_SHORT).show();

                txt_distrito.setText("AUDIENCIAS ORALES EN MATERIA MERCANTIL PARA EL MES DE " + datos_consulta3.getQUE_ES() + " DEL AÑO " + datos_consulta2.getAÑO() + "  DEL DISTRITO JUDICIAL DE " + datos_consulta.getQUE_ES());
                txt_distrito.setPadding(50, 50, 50, 0);

                Encabezado("#", "No.expediente", "Juzgado", "Fecha y hora de la audiencia", "Tipo de audiencia.");

                txt_mes.setVisibility(View.GONE);
                distrito.setVisibility(View.GONE);
                txt_año.setVisibility(View.GONE);
                año.setVisibility(View.GONE);
                mes.setVisibility(View.GONE);
                consulta.setVisibility(View.GONE);
                visibilidad(true);

                for (int i = 1; i <= x; i++) {
                    llenadoTabla(String.valueOf(i), datos[i][1], datos[i][2], datos[i][3], datos[i][4]);
                }

                Cambio_color(4);
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Toast.makeText(getActivity(), "Tarea cancelada!", Toast.LENGTH_SHORT).show();

        }
    }
}
