package com.curso.tinodeoz.portal_prueba;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static java.lang.String.format;


public class consignaciones extends Fragment {

    Connection connect;
    String ConnectionResult="";
    Boolean esSatisfactorio=false;


    TableLayout tabla;
    Datos datos_consulta,Seleccion;


    WebView web;
    TextView txt_juzgado,txt_exp,txt_distrito;
    EditText no_expediente;
    Button consulta;
    Spinner distrito, juzgado1,juzgado2, juzgado3;
    ProgressDialog pDialog;


    String[] string_distrito={"Selecciona aquí:","PACHUCA DE SOTO","TULA DE ALLENDE","TULANCINGO DE BRAVO"};
    String[] string_juzgado={"Selecciona aquí:","PRIMERO CIVIL PACHUCA","SEGUNDO CIVIL PACHUCA","TERCERO CIVIL PACHUCA","CUARTO CIVIL PACHUCA",
            "PRIMERO MERCANTIL PACHUCA","SEGUNDO MERCANTIL PACHUCA","TERCERO MERCANTIL PACHUCA","PRIMERO FAMILIAR PACHUCA","SEGUNDO FAMILIAR PACHUCA","TERCERO FAMILIAR PACHUCA","CUARTO FAMILIAR PACHUCA"};
    String[] string_juzgado2={"Selecciona aquí:","PRIMERO CIVIL Y FAMILIAR DE DE TULA","SEGUNDO CIVIL Y FAMILIAR DE TULA","TERCERO CIVIL Y FAMILIAR DE DE TULA"};

    String[] string_juzgado3={"Selecciona aquí:","PRIMERO CIVIL Y FAMILIAR DE TULANCINGO","SEGUNDO CIVIL Y FAMILIAR DE TULANCINGO","TERCERO CIVIL Y FAMILIAR DE TULANCINGO"};





    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public consignaciones() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static consignaciones newInstance(String param1, String param2) {
        consignaciones fragment = new consignaciones();
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


        View v=inflater.inflate(R.layout.fragment_consignaciones, container, false);
        inicio(v);
        llenado_spiners(v);
        acciones(v);




        String text_web = "<html><body style=\"text-align:justify; font-size:12px; line-height:20px; color:white;\"> %s </body></html>";
        String texto="Este servicio es de carácter informativo y está orientado hacia los justiciables y comunidad jurídica a fin de consultar el registro de las consignaciones que han sido recibidas en la Dirección del Fondo Judicial. <br>Este servicio busca orientarle para calcular el tiempo de entrega de las consignaciones derivadas de las controversias que representa el pago de pensiones alimenticias o rentas.<br> El usuario deberá de conocer el número de expediente y juzgado de radicación.";
        web= new WebView(getContext());
        web.loadData(format(text_web, texto), "text/html; charset=utf-8","UTF-8");
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



        finales(v);
        nueva();
        salir();
        visibilidad(false);




        return v;
    }

    public void inicio(View v){
        txt_juzgado=(TextView)v.findViewById(R.id.txt_consig_juzgado);
        txt_distrito=(TextView)v.findViewById(R.id.txt_consig_distrito);
        txt_exp=(TextView)v.findViewById(R.id.txt_consig_expediente);
        consulta=(Button)v.findViewById(R.id.consig_consultar);
        no_expediente=(EditText)v.findViewById(R.id.txt_consig_no_expediente) ;


        distrito=(Spinner)v.findViewById(R.id.spinner_consig_distrito);
        juzgado1=(Spinner)v.findViewById(R.id.spinner_consig_juzgado1);
        juzgado2=(Spinner)v.findViewById(R.id.spinner_consig_juzgado2);
        juzgado3=(Spinner)v.findViewById(R.id.spinner_consig_juzgado3);

        tabla=(TableLayout)v.findViewById(R.id.Tabla_btn_consignaciones);


        txt_juzgado.setVisibility(View.GONE);
        juzgado1.setVisibility(View.GONE);
        juzgado2.setVisibility(View.GONE);
        juzgado3.setVisibility(View.GONE);
        txt_exp.setVisibility(View.GONE);
        no_expediente.setVisibility(View.GONE);
        consulta.setVisibility(View.GONE);
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
                Fragment fra= new consignaciones();
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

        ArrayAdapter<String> adaptador2=  new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,string_juzgado);
        juzgado1.setAdapter(adaptador2);

        ArrayAdapter<String> adaptador3=  new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,string_juzgado2);
        juzgado2.setAdapter(adaptador3);

        ArrayAdapter<String> adaptador4=  new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,string_juzgado3);
        juzgado3.setAdapter(adaptador4);

    }

    public void acciones(final View v){

        distrito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==1){
                    txt_juzgado.setVisibility(View.VISIBLE);
                    juzgado1.setVisibility(View.VISIBLE);
                    juzgado2.setVisibility(View.GONE);
                    juzgado3.setVisibility(View.GONE);
                    Seleccion =new Datos();
                    Seleccion.setID("1");


                } else if (position==2) {
                    txt_juzgado.setVisibility(View.VISIBLE);
                    juzgado2.setVisibility(View.VISIBLE);
                    juzgado1.setVisibility(View.GONE);
                    juzgado3.setVisibility(View.GONE);
                    Seleccion =new Datos();
                    Seleccion.setID("2");

                }else if (position==3) {
                    txt_juzgado.setVisibility(View.VISIBLE);
                    juzgado3.setVisibility(View.VISIBLE);
                    juzgado1.setVisibility(View.GONE);
                    juzgado2.setVisibility(View.GONE);
                    Seleccion =new Datos();
                    Seleccion.setID("3");
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


                } else if (position==2) {
                    seleccion_juzgado(v);
                    datos_consulta =new Datos();
                    datos_consulta.setID("2");

                }
                else if (position==3) {
                    seleccion_juzgado(v);
                    datos_consulta =new Datos();
                    datos_consulta.setID("3");

                }
                else if (position==4) {
                    seleccion_juzgado(v);
                    datos_consulta =new Datos();
                    datos_consulta.setID("36");

                }
                else if (position==5) {
                    seleccion_juzgado(v);
                    datos_consulta =new Datos();
                    datos_consulta.setID("38");

                }else if (position==6) {
                    seleccion_juzgado(v);
                    datos_consulta =new Datos();
                    datos_consulta.setID("46");

                }else if (position==7) {
                    seleccion_juzgado(v);
                    datos_consulta =new Datos();
                    datos_consulta.setID("97");

                }else if (position==8) {
                    seleccion_juzgado(v);
                    datos_consulta =new Datos();
                    datos_consulta.setID("5");

                }else if (position==9) {
                    seleccion_juzgado(v);
                    datos_consulta =new Datos();
                    datos_consulta.setID("40");

                }else if (position==10) {
                    seleccion_juzgado(v);
                    datos_consulta =new Datos();
                    datos_consulta.setID("52");
                }else if (position==11) {
                    seleccion_juzgado(v);
                    datos_consulta =new Datos();
                    datos_consulta.setID("98");
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
                    datos_consulta.setID("26");


                } else if (position==2) {
                    seleccion_juzgado(v);
                    datos_consulta =new Datos();
                    datos_consulta.setID("33");

                }
                else if (position==3) {
                    seleccion_juzgado(v);
                    datos_consulta =new Datos();
                    datos_consulta.setID("49");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        juzgado3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position==1){
                    seleccion_juzgado(v);
                    datos_consulta =new Datos();
                    datos_consulta.setID("28");


                } else if (position==2) {
                    seleccion_juzgado(v);
                    datos_consulta =new Datos();
                    datos_consulta.setID("30");

                }
                else if (position==3) {
                    seleccion_juzgado(v);
                    datos_consulta =new Datos();
                    datos_consulta.setID("48");

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

                Consulta_Consig Consig = new Consulta_Consig();
                Consig.execute();

                /*
                String[] datos =new String[3];
                String query="";
                try {

                    if (Seleccion.getID()=="1") {
                        Con_sql conStr = new Con_sql();
                        connect = conStr.connections();
                        query = "SELECT  Top 5 de_fecha FROM Vta_ResiDepositos Where IdJuzgado="+datos_consulta.getID()+ "and Expediente='"+no_expediente.getText().toString()+"'Order by de_fecha DESC;";

                    }else if (Seleccion.getID()=="2") {
                        Con_sql conStr = new Con_sql();
                        connect = conStr.connections();
                        query = "SELECT  Top 5 de_fecha FROM Vta_ResiDepositosTula Where IdJuzgado="+datos_consulta.getID()+ "and Expediente='"+no_expediente.getText().toString()+"'Order by de_fecha DESC;";


                    }else if (Seleccion.getID()=="3") {
                        Con_sql conStr = new Con_sql();
                        connect = conStr.connectionstulancingo();
                         query = "SELECT  Top 5 de_fecha FROM Vta_ResiDepositos Where IdJuzgado="+datos_consulta.getID()+ "and Expediente='"+no_expediente.getText().toString()+"'Order by de_fecha DESC;";

                    }

                    if (connect == null)
                    {
                        ConnectionResult = "Check Your Internet Access!";
                        Toast.makeText(getActivity(),ConnectionResult, Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        Toast.makeText(getActivity(),"Esperé unos segundos...", Toast.LENGTH_SHORT).show();
                        while (no_expediente.getText().toString().length()<11){
                            String ejemplo=no_expediente.getText().toString();
                            ejemplo="0"+ejemplo;
                            no_expediente.setText(ejemplo);
                        }


                        Statement stmt = connect.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if(!rs.isBeforeFirst()){
                            Toast.makeText(getActivity(),"No se encontraron Datos", Toast.LENGTH_SHORT).show();
                        }

                        else {
                            Encabezado("Fecha");
                            txt_juzgado.setVisibility(View.GONE);
                            txt_distrito.setText("Ultimos 5 Depositos.");
                            distrito.setVisibility(View.GONE);
                            juzgado1.setVisibility(View.GONE);
                            juzgado2.setVisibility(View.GONE);
                            txt_exp.setVisibility(View.GONE);
                            no_expediente.setVisibility(View.GONE);
                            consulta.setVisibility(View.GONE);
                            visibilidad(true);


                            while (rs.next()){
                                datos[1]=rs.getString("de_fecha").toString();
                                llenadoTabla(datos[1]);
                            }
                        }

                    }
                    //Toast.makeText(getActivity(),datos[1], Toast.LENGTH_SHORT).show();



                }catch (Exception ex)
                {
                    esSatisfactorio = false;
                    ConnectionResult = ex.getMessage();
                }

                */
            }
        });



    }

    public void llenadoTabla(String txt1, String txt2){
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
        txtTabla.setTextSize(16);
        txtTabla.setTextColor(Color.parseColor("#B1613e"));
        txtTabla.setWidth(500);
        row.addView(txtTabla);

        txtTabla2=new TextView(getActivity());
        txtTabla2.setGravity(Gravity.CENTER_HORIZONTAL);
        txtTabla2.setBackgroundColor(Color.TRANSPARENT);
        txtTabla2.setText(txt2);
        txtTabla2.setTextSize(16);
        txtTabla2.setTextColor(Color.parseColor("#B1613e"));
        txtTabla2.setWidth(10);
        //row.addView(txtTabla2);


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
        borde2.setWidth(10);
        borde2.setHeight(10);
        //borde.addView(borde2);


        //tabla.addView(row ,new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        tabla.addView(borde);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////77
    public void Encabezado(String txt1, String txt2){
        TableRow.LayoutParams layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);

        TableRow row= new TableRow(getActivity());
        row.setLayoutParams(layoutFila);


        TextView txtTabla, txtTabla2;

        txtTabla=new TextView(getActivity());
        txtTabla.setGravity(Gravity.CENTER_HORIZONTAL);
        txtTabla.setTextSize(16);
        txtTabla.setBackgroundColor(Color.parseColor("#B1613e"));
        txtTabla.setText(txt1);
        txtTabla.setTextColor(Color.WHITE);
        txtTabla.setWidth(500);
        row.addView(txtTabla);

        txtTabla2=new TextView(getActivity());
        txtTabla2.setGravity(Gravity.CENTER_HORIZONTAL);
        txtTabla2.setTextSize(16);
        txtTabla2.setBackgroundColor(Color.parseColor("#B1613e"));
        txtTabla2.setText(txt2);
        txtTabla2.setTextColor(Color.WHITE);
        txtTabla2.setWidth(10);
        //row.addView(txtTabla2);

        tabla.addView(row);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////77
    public void seleccion_juzgado(View v){

        txt_exp.setVisibility(View.VISIBLE);
        no_expediente.setVisibility(View.VISIBLE);
        consulta.setVisibility(View.VISIBLE);

    }

    private void aceptar() {
        Toast.makeText(getActivity(),"Bienvenido Al Sistema ",Toast.LENGTH_LONG).show();

    }

    private void cancelar() {

        getActivity().finish();
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private class Consulta_Consig extends AsyncTask<Void, Integer,Void>{
        String[][] datos =new String[100][3];
        String query="";
        Con_sql conStr = new Con_sql();
        String ex="";
        String Selec_ID="";
        String Resultado="";
        int x=0;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setProgress(0);
            pDialog.show();

            while (no_expediente.getText().toString().length()<11){
                String ejemplo=no_expediente.getText().toString();
                ejemplo="0"+ejemplo;
                no_expediente.setText(ejemplo);
            }

            ex=no_expediente.getText().toString();
            Selec_ID=Seleccion.getID();


            pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    Consulta_Consig.this.cancel(true);
                }
            });
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {

                if (Selec_ID=="1") {
                    connect = conStr.connections();
                    query = "SELECT  Top 5 CONVERT(VARCHAR, de_fecha, 111)de_fecha, Correspondiente FROM Vta_ResiDepositos Where IdJuzgado="+datos_consulta.getID()+ " and Expediente='"+ex+"' Order by de_fecha DESC;";

                }else if (Selec_ID=="2") {

                    // connect = conStr.connections();
                    // query = "SELECT  Top 5 CONVERT(VARCHAR, de_fecha, 105)de_fecha FROM Vta_ResiDepositosTula Where IdJuzgado="+datos_consulta.getID()+ "and Expediente='"+ex+"'Order by de_fecha DESC;";

                    connect= conStr.connection_tula();
                    query = "SELECT  Top 5 CONVERT(VARCHAR, de_fecha, 111)de_fecha, Correspondiente FROM Vta_ResiDepositos Where IdJuzgado="+datos_consulta.getID()+ " and Expediente='"+ex+"' Order by de_fecha DESC;";


                }else if (Selec_ID=="3") {
                    connect = conStr.connectionstulancingo();
                    query = "SELECT  Top 5 CONVERT(VARCHAR, de_fecha, 111)de_fecha, Correspondiente FROM Vta_ResiDepositos Where IdJuzgado="+datos_consulta.getID()+ " and Expediente='"+ex+"' Order by de_fecha DESC;";
                }

                if (connect == null)
                {
                    ConnectionResult = "Check Your Internet Access!";
                    Resultado="no";

                }
                else
                {
                    Statement stmt = connect.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if(!rs.isBeforeFirst()){
                       ConnectionResult="No se encontraron Datos";
                        Resultado="no";
                    }

                    else {


                        while (rs.next()){
                            x=x+1;
                            datos[x][1]=rs.getString("de_fecha").toString();
                            datos[x][2]=rs.getString("Correspondiente").toString();

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

            }else{
                //Encabezado("Fecha\n Año/Mes/Día","Correspondiete\n a:");
                Encabezado("Fecha\n Año/Mes/Día","");
                txt_juzgado.setVisibility(View.GONE);
                txt_distrito.setText("Ultimos 5 Depositos.");
                distrito.setVisibility(View.GONE);
                juzgado1.setVisibility(View.GONE);
                juzgado2.setVisibility(View.GONE);
                txt_exp.setVisibility(View.GONE);
                no_expediente.setVisibility(View.GONE);
                consulta.setVisibility(View.GONE);
                visibilidad(true);



                for (int i=1;i<=x;i++) {
                    String txt1=datos[i][2];
                    txt1=txt1.replace("11/","Noviembre/");
                    txt1=txt1.replace("12/","Diciembre/");
                    txt1=txt1.replace("1/","Enero/");
                    txt1=txt1.replace("2/","febrero/");
                    txt1=txt1.replace("3/","Marzo/");
                    txt1=txt1.replace("4/","Abril/");
                    txt1=txt1.replace("5/","Mayo/");
                    txt1=txt1.replace("6/","Junio/");
                    txt1=txt1.replace("7/","Julio/");
                    txt1=txt1.replace("8/","Agosto/");
                    txt1=txt1.replace("9/","Septiembre/");
                    txt1=txt1.replace("10/","Octubre/");

                    //llenadoTabla(datos[i][1], txt1);
                    llenadoTabla(datos[i][1], "");

                }
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Toast.makeText(getActivity(), "Tarea cancelada!", Toast.LENGTH_SHORT).show();
        }
    }






}
