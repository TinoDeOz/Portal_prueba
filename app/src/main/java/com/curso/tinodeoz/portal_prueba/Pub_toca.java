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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class Pub_toca extends Fragment {

    Connection connect;
    String ConnectionResult="";
    Boolean esSatisfactorio=false;


    TableLayout tabla;
    Datos datos_consulta;


    WebView web;
    TextView txt_no_toca, txt_pub_sala;
    EditText numero_toca;
    Spinner sala;
    Button consulta;
    ProgressDialog pDialog;

    String[] string_sala={"Selecciona aquí:","Primera sala civil y familiar","Segunda sala civil y familiar"};

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Pub_toca() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Pub_toca newInstance(String param1, String param2) {
        Pub_toca fragment = new Pub_toca();
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
        View v=inflater.inflate(R.layout.fragment_pub_toca, container, false);
        mensaje();
        inicio(v);
        llenado_spiners(v);
        acciones(v);
        finales(v);
        nueva();
        salir();
        visibilidad(false);


        return v;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////7


    public void mensaje(){

        String text_web = "<html><body style=\"text-align:justify; font-size:12px; line-height:20px; color:white;\"> %s </body></html>";
        String texto= "En este modulo se encuentran disponibles aquellas publicaciones de toca registradas a partir del 23 de mayo de 2012..";
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
/////////////////////////////////////////////////////////////////////////

    public void inicio(View v){
        txt_no_toca=(TextView) v.findViewById(R.id.txt_no_toca);
        numero_toca=(EditText)v.findViewById(R.id.txt_numero_publi_toca);
        consulta=(Button)v.findViewById(R.id.btn_publi_toca_consultar);
        sala=(Spinner)v.findViewById(R.id.spinner_sala_toca);
        tabla=(TableLayout)v.findViewById(R.id.Tabla_btn_publicacion);

        txt_pub_sala=(TextView)v.findViewById(R.id.txt_sala);
        txt_no_toca.setVisibility(View.GONE);
        numero_toca.setVisibility(View.GONE);
        consulta.setVisibility(View.GONE);
    }
///////////////////////////////////////////////////////////////
    public void llenado_spiners(View v){
        ArrayAdapter<String> adaptador=  new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,string_sala);
        sala.setAdapter(adaptador);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////77

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
                Fragment fra= new Pub_toca();
                FragmentTransaction trans= getFragmentManager().beginTransaction();
                getActivity().onBackPressed();// Elimina el fragment anterior
                trans.replace(R.id.content_frame, fra);
                trans.addToBackStack(null);
                trans.commit();
            }
        });


    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void acciones(View v){

        sala.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==1){
                    txt_no_toca.setVisibility(View.VISIBLE);
                    numero_toca.setVisibility(View.VISIBLE);
                    consulta.setVisibility(View.VISIBLE);
                    datos_consulta=new Datos();
                    datos_consulta.setID("56");

                }else if(position==2){
                    txt_no_toca.setVisibility(View.VISIBLE);
                    numero_toca.setVisibility(View.VISIBLE);
                    consulta.setVisibility(View.VISIBLE);
                    datos_consulta=new Datos();
                    datos_consulta.setID("57");
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

                Consulta_publicacion Con_Pub = new Consulta_publicacion();
                Con_Pub.execute();
                /*
                String[] datos =new String[5];

                int x=0;
                try {

                    Con_sql conStr=new Con_sql();
                    connect =conStr.connections();

                    if (connect == null)
                    {
                        ConnectionResult = "Check Your Internet Access!";
                        Toast.makeText(getActivity(),ConnectionResult, Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        Toast.makeText(getActivity(),"Esperé unos segundos...", Toast.LENGTH_SHORT).show();
                        while (numero_toca.getText().toString().length()<9){
                            String ejemplo=numero_toca.getText().toString();
                            ejemplo="0"+ejemplo;
                            numero_toca.setText(ejemplo);
                        }

                        String query = " SELECT publicacion, juicio,acuerdo, Sintesis FROM Vta_ResiNotificaSalas where id_sala="+datos_consulta.getID()+" and num_toca='"+numero_toca.getText().toString()+"'";
                        Statement stmt = connect.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if(!rs.isBeforeFirst()){
                            Toast.makeText(getActivity(),"No se encontraron Datos", Toast.LENGTH_SHORT).show();
                        }

                        else {
                            Encabezado("#","FECHA DE PUBLICACIÓN","JUICIO", "FECHA DE RESOLUCIÓN", "SÍNTESIS");
                            txt_no_toca.setVisibility(View.GONE);
                            numero_toca.setVisibility(View.GONE);
                            consulta.setVisibility(View.GONE);
                            sala.setVisibility(View.GONE);
                            txt_pub_sala.setPadding(20,50,20,50);
                            txt_pub_sala.setText("Este servicio es de carácter informativo, de ningún modo debe considerarse como una notificación con validez legal.");
                            visibilidad(true);


                            while (rs.next()){
                                x=x+1;
                                datos[1]=rs.getString("publicacion");
                                datos[2]=rs.getString("juicio");
                                datos[3]=rs.getString("acuerdo");
                                datos[4]=rs.getString("Sintesis");

                                llenadoTabla(String.valueOf(x),datos[1],datos[2],datos[3],datos[4]);
                            }
                        }

                    }

                }catch (Exception ex)
                {
                    esSatisfactorio = false;
                    ConnectionResult = ex.getMessage();
                }
               */
            }
        });
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void llenadoTabla(final String txt1, final String txt2, final String txt3, final String txt4, final String txt5){
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
        txtTabla2.setGravity(Gravity.CENTER_HORIZONTAL);
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
        txtTabla5.setText(Html.fromHtml("<u>Ver Sintesis</u>"));
        txtTabla5.setTextColor(Color.BLACK);
        txtTabla5.setWidth(700);
        txtTabla5.setPadding(0,20,0,20);
        row.addView(txtTabla5);


        txtTabla5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_no_toca.setText("Numero de Toca:\n" +
                        numero_toca.getText().toString()+"\n \n" +
                        "Fecha de publicación:\n" +txt2+"\n \n" +
                        "Juicio:\n"+txt3+"\n \n" +
                        "Fecha de resolución:\n"+ txt4+"\n \n" +
                        "Sintesis:\n"+txt5);

                tabla.removeAllViews();
                txt_no_toca.setTextSize(16);
                txt_no_toca.setVisibility(View.VISIBLE);
                txt_no_toca.setPadding(15,10,10,15);

            }
        });





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
        borde5.setWidth(700);
        borde5.setHeight(10);
        borde.addView(borde5);

        //tabla.addView(row ,new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        tabla.addView(borde);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////77
    public void Encabezado(String txt1,String txt2,String txt3,String txt4,String txt5){
        TableRow.LayoutParams layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);

        int x=200;

        TableRow row= new TableRow(getActivity());
        row.setLayoutParams(layoutFila);
        row.setGravity(Gravity.CENTER_VERTICAL);


        TextView txtTabla, txtTabla2,txtTabla3,txtTabla4,txtTabla5,txtTabla6;

        txtTabla=new TextView(getActivity());
        txtTabla.setGravity(Gravity.CENTER);
        txtTabla.setBackgroundColor(Color.parseColor("#B1613e"));
        txtTabla.setText(txt1);
        txtTabla.setTextColor(Color.WHITE);
        txtTabla.setWidth(50);
        txtTabla.setHeight(x);
        row.addView(txtTabla);




        txtTabla2=new TextView(getActivity());
        txtTabla2.setGravity(Gravity.CENTER);
        txtTabla2.setBackgroundColor(Color.parseColor("#B1613e"));
        txtTabla2.setText(txt2);
        txtTabla2.setTextColor(Color.WHITE);
        txtTabla2.setMinWidth(400);
        txtTabla2.setMaxWidth(500);
        txtTabla2.setHeight(x);
        row.addView(txtTabla2);


        txtTabla3=new TextView(getActivity());
        txtTabla3.setGravity(Gravity.CENTER);
        txtTabla3.setBackgroundColor(Color.parseColor("#B1613e"));
        txtTabla3.setText(txt3);
        txtTabla3.setTextColor(Color.WHITE);
        txtTabla3.setMinWidth(400);
        txtTabla3.setMaxWidth(500);
        txtTabla3.setHeight(x);
        row.addView(txtTabla3);

        txtTabla4=new TextView(getActivity());
        txtTabla4.setGravity(Gravity.CENTER);
        txtTabla4.setBackgroundColor(Color.parseColor("#B1613e"));
        txtTabla4.setText(txt4);
        txtTabla4.setTextColor(Color.WHITE);
        txtTabla4.setMinWidth(400);
        txtTabla4.setMaxWidth(500);
        txtTabla4.setHeight(x);
        row.addView(txtTabla4);

        txtTabla5=new TextView(getActivity());
        txtTabla5.setGravity(Gravity.CENTER);
        txtTabla5.setBackgroundColor(Color.parseColor("#B1613e"));
        txtTabla5.setText(txt5);
        txtTabla5.setTextColor(Color.WHITE);
        txtTabla5.setMinWidth(800);
        txtTabla5.setMaxWidth(900);
        txtTabla5.setHeight(x);

        row.addView(txtTabla5);

        txtTabla6=new TextView(getActivity());
        txtTabla6.setGravity(Gravity.CENTER);
        txtTabla6.setBackgroundColor(Color.TRANSPARENT);
        txtTabla6.setWidth(200);
        txtTabla6.setHeight(x);
        row.addView(txtTabla6);

        tabla.addView(row);
    }
    public void Cambio_color(int alt_row) {
        int childViewCount = tabla.getChildCount();

        for (int i = 1; i < childViewCount; i++) {
            TableRow row = (TableRow) tabla.getChildAt(i);

            for (int j = 0; j < row.getChildCount()-1; j++) {

                TextView tv = (TextView) row.getChildAt(j);
                if (i % 3 != 0) {
                    // tv.setBackground(getResources().getDrawable(R.drawable.alt_row_color));
                    tv.setTextColor(Color.parseColor("#B1613e"));
                } else {
                    //tv.setBackground(getResources().getDrawable(R.drawable.row_color));
                    tv.setTextColor(Color.BLACK);
                }
            }
        }
    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public class Consulta_publicacion extends AsyncTask<Void, Integer, Void>{
        String[][] datos =new String[500][5];
        Con_sql conStr=new Con_sql();
        int x=0;
        String toca="";
        String Resultado="";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            toca=numero_toca.getText().toString();
            pDialog.setProgress(0);
            pDialog.show();

            pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    Consulta_publicacion.this.cancel(true);
                }
            });
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                connect =conStr.connections();

                if (connect == null)
                {
                    ConnectionResult = "Check Your Internet Access!";
                    Resultado="no";

                }
                else
                {
                    while (toca.length()<9){
                        String ejemplo=toca;
                        ejemplo="0"+ejemplo;
                        toca=ejemplo;
                    }

                    String query = " SELECT CONVERT(VARCHAR, publicacion, 105)publicacion, juicio,acuerdo, Sintesis FROM Vta_ResiNotificaSalas where id_sala="+datos_consulta.getID()+" and num_toca='"+toca+"'";
                    Statement stmt = connect.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if(!rs.isBeforeFirst()){
                        ConnectionResult="No se encontraron Datos";
                        Resultado="no";
                    }

                    else {

                        while (rs.next()){
                            x=x+1;
                            datos[x][1]=rs.getString("publicacion");
                            datos[x][2]=rs.getString("juicio");
                            datos[x][3]=rs.getString("acuerdo");
                            datos[x][4]=rs.getString("Sintesis");
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
        if (Resultado.equals("no")){
            Toast.makeText(getActivity(),ConnectionResult,Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(getActivity(), "Tarea finalizada!", Toast.LENGTH_SHORT).show();
            Encabezado("#","FECHA DE PUBLICACIÓN","JUICIO", "FECHA DE RESOLUCIÓN", "SÍNTESIS");
            txt_no_toca.setVisibility(View.GONE);
            numero_toca.setVisibility(View.GONE);
            consulta.setVisibility(View.GONE);
            sala.setVisibility(View.GONE);
            txt_pub_sala.setPadding(20,50,20,50);
            txt_pub_sala.setText("Este servicio es de carácter informativo, de ningún modo debe considerarse como una notificación con validez legal.");
            visibilidad(true);

            for (int i=1;i<=x;i++){
                llenadoTabla(String.valueOf(i),datos[i][1],datos[i][2],datos[i][3],datos[i][4]);
            }
            Cambio_color(3);
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
