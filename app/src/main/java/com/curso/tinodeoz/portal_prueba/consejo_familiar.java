package com.curso.tinodeoz.portal_prueba;

import android.content.Context;
import android.content.DialogInterface;
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
import java.sql.Statement;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link consejo_familiar.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link consejo_familiar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class consejo_familiar extends Fragment {

    Connection connect;
    String ConnectionResult="";
    Boolean esSatisfactorio=false;


    WebView web;
    TextView txt_juzgado,txt_exp,txt_distrito;
    EditText no_expediente;
    Button consulta;
    Spinner distrito, juzgado1,juzgado2;
    TableLayout tabla;

    Datos datos_consulta, Seleccion;


    String[] string_distrito={"Selecciona Aqui:","PACHUCA DE SOTO","TULANCINGO DE BRAVO"};
    String[] string_juzgado={"Selecciona Aqui:","PRIMERO FAMILIAR PACHUCA","SEGUNDO FAMILIAR PACHUCA","TERCERO FAMILIAR PACHUCA"};
    String[] string_juzgado2={"Selecciona Aqui:","PRIMERO CIVIL Y FAMILIAR DE TULANCINGO","SEGUNDO CIVIL Y FAMILIAR DE TULANCINGO","TERCERO Y FAMILIAR DE TULANCINGO"};





    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public consejo_familiar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment consejo_familiar.
     */
    // TODO: Rename and change types and number of parameters
    public static consejo_familiar newInstance(String param1, String param2) {
        consejo_familiar fragment = new consejo_familiar();
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
        View v=inflater.inflate(R.layout.fragment_consejo_familiar, container, false);
        mensaje();
        inicio(v);
        llenado_spiners(v);
        acciones(v);

        finales(v);
        visibilidad(false);
        salir();

        nueva();




        return v;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void mensaje(){

        String text_web = "<html><body style=\"text-align:justify; font-size:12px; line-height:20px; color:white;\"> %s </body></html>";
        String texto= "En esta sección usted podrá consultar la fecha y hora en que le ha sido programada la intervención del Consejo de Familia para su expediente. Para ello solo bastará con que escriba el número y año del expediente.";

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
    txt_juzgado=(TextView)v.findViewById(R.id.txt_familiar_juzgado);
    txt_exp=(TextView)v.findViewById(R.id.txt_familiar_expediente);
    consulta=(Button)v.findViewById(R.id.btn_familiar_consultar);
    no_expediente=(EditText)v.findViewById(R.id.txt_familiar_no_expediente) ;


    distrito=(Spinner)v.findViewById(R.id.spinner_familiar_distrito);
    juzgado1=(Spinner)v.findViewById(R.id.spinner_familiar_juzgado);
    juzgado2=(Spinner)v.findViewById(R.id.spinner_familiar_juzgado2);

    txt_distrito=(TextView)v.findViewById(R.id.txt_familiar_distrito);
    tabla=(TableLayout)v.findViewById(R.id.Tabla_btn_familiar);

    txt_juzgado.setVisibility(View.GONE);
    juzgado1.setVisibility(View.GONE);
    juzgado2.setVisibility(View.GONE);
    txt_exp.setVisibility(View.GONE);
    no_expediente.setVisibility(View.GONE);
    consulta.setVisibility(View.GONE);


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
                Fragment fra= new consejo_familiar();
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
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////77
    public void acciones(final View v){

        distrito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==1){
                    txt_juzgado.setVisibility(View.VISIBLE);
                    juzgado1.setVisibility(View.VISIBLE);
                    juzgado2.setVisibility(View.GONE);
                    Seleccion =new Datos();
                    Seleccion.setID("1");


                } else if (position==2) {
                    txt_juzgado.setVisibility(View.VISIBLE);
                    juzgado2.setVisibility(View.VISIBLE);
                    juzgado1.setVisibility(View.GONE);
                    Seleccion =new Datos();
                    Seleccion.setID("2");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        juzgado1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position==1) {
                    seleccion_juzgado(v);
                    datos_consulta =new Datos();
                    datos_consulta.setID("5");

                }else if (position==2){
                    seleccion_juzgado(v);
                    datos_consulta =new Datos();
                    datos_consulta.setID("40");

                }else if (position==3) {
                    seleccion_juzgado(v);
                    datos_consulta =new Datos();
                    datos_consulta.setID("52");

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
                String[] datos =new String[3];
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
                        while (no_expediente.getText().toString().length()<11){
                            String ejemplo=no_expediente.getText().toString();
                            ejemplo="0"+ejemplo;
                            no_expediente.setText(ejemplo);
                        }

                        String query = "SELECT FechaIntervencion  FROM Vta_ResiIntevencionesCF Where IdJuzgado="+datos_consulta.getID()+ "and Expediente='"+no_expediente.getText().toString()+"'";
                        Statement stmt = connect.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if(!rs.isBeforeFirst()){
                            Toast.makeText(getActivity(),"No se encontraron Datos", Toast.LENGTH_SHORT).show();
                        }

                        else {

                            Encabezado("Fecha De Intervencion.");
                            txt_juzgado.setVisibility(View.GONE);
                            txt_distrito.setVisibility(View.GONE);
                            distrito.setVisibility(View.GONE);
                            juzgado1.setVisibility(View.GONE);
                            juzgado2.setVisibility(View.GONE);
                            txt_exp.setVisibility(View.GONE);

                            no_expediente.setVisibility(View.GONE);
                            consulta.setVisibility(View.GONE);

                            visibilidad(true);

                            while (rs.next()){
                                datos[1]=rs.getString("FechaIntervencion").toString();

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

            }
        });





    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void llenadoTabla(String txt1){
        TableRow.LayoutParams layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);

        TableRow row= new TableRow(getActivity());
        row.setLayoutParams(layoutFila);
        row.setGravity(Gravity.CENTER_VERTICAL);


        TextView txtTabla;

        txtTabla=new TextView(getActivity());
        txtTabla.setGravity(Gravity.CENTER_HORIZONTAL);
        txtTabla.setBackgroundColor(Color.TRANSPARENT);
        txtTabla.setText(txt1);
        txtTabla.setTextSize(18);
        txtTabla.setTextColor(Color.parseColor("#B1613e"));
        txtTabla.setWidth(700);
        row.addView(txtTabla);


        tabla.addView(row);

        TableRow borde= new TableRow(getActivity());
        row.setLayoutParams(layoutFila);


        TextView borde1;

        borde1=new TextView(getActivity());
        borde1.setBackgroundColor(Color.parseColor("#000000"));
        borde1.setWidth(700);
        borde1.setHeight(10);
        borde.addView(borde1);



        //tabla.addView(row ,new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        tabla.addView(borde);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////77
    public void Encabezado(String txt1){
        TableRow.LayoutParams layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);

        TableRow row= new TableRow(getActivity());
        row.setLayoutParams(layoutFila);


        TextView txtTabla, txtTabla2;

        txtTabla=new TextView(getActivity());
        txtTabla.setGravity(Gravity.CENTER_HORIZONTAL);
        txtTabla.setTextSize(18);
        txtTabla.setBackgroundColor(Color.parseColor("#B1613e"));
        txtTabla.setText(txt1);
        txtTabla.setTextColor(Color.WHITE);
        txtTabla.setWidth(700);
        row.addView(txtTabla);

        tabla.addView(row);
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////77


    public void seleccion_juzgado(View v){

        txt_exp.setVisibility(View.VISIBLE);
        no_expediente.setVisibility(View.VISIBLE);
        consulta.setVisibility(View.VISIBLE);

        // txt_exp.setVisibility(View.GONE);
        // no_expediente.setVisibility(View.GONE);
        // consulta.setVisibility(View.GONE);
    }











    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
