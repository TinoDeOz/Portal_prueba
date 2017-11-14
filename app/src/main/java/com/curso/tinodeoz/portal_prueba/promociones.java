package com.curso.tinodeoz.portal_prueba;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class promociones extends Fragment {

    Connection connect;
    String ConnectionResult="";
    Boolean esSatisfactorio=false;

    Datos datos_consulta;


    WebView web;
    TextView txt_tipo,txt_exp, txtdistrito;
    EditText no_expediente;
    Button consulta;



    Spinner distrito, inicial,posterior;
    String[] string_distrito={"Selecciona Aqui:","PACHUCA DE SOTO","TULANCINGO DE BRAVO"};
    String[] string_tipo={"Selecciona Aqui:","Inicial","Posterior"};






    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public promociones() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment promociones.
     */
    // TODO: Rename and change types and number of parameters
    public static promociones newInstance(String param1, String param2) {
        promociones fragment = new promociones();
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

        View v=inflater.inflate(R.layout.fragment_promociones, container, false);
        inicio(v);
        llenado_spiners(v);
        accion(v);



        String text_web = "<html><body style=\"text-align:justify; font-size:12px; line-height:20px; color:white;\"> %s </body></html>";
        String texto="Con este servicio usted podrá conocer si la promoción ingresada en la oficialía de partes ya ha sido acordada. Para ello será necesario seleccionar el Distrito, indicar el tipo al cual corresponde y digitar el folio de dicho documento, mismo que se encuentra impreso en el sello electrónico del acuse.";
        web= new WebView(getContext());
        web.loadData(String.format(text_web, texto), "text/html; charset=utf-8","UTF-8");
        web.setBackgroundColor(Color.parseColor("#00FFFFFF"));

        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getActivity());
        dialogo1.setTitle("Importante");
        dialogo1.setCancelable(false);
        dialogo1.setView(web);

        dialogo1.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
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
        txtdistrito=(TextView)v.findViewById(R.id.txt_promo_distrito);
        txt_tipo=(TextView)v.findViewById(R.id.txt_promo);
        txt_exp=(TextView)v.findViewById(R.id.txt_promo_expediente);
        consulta=(Button)v.findViewById(R.id.promo_consultar);

        no_expediente=(EditText)v.findViewById(R.id.txt_promo_no_expediente) ;

        distrito=(Spinner)v.findViewById(R.id.spinner_promo_distrito);
        inicial=(Spinner)v.findViewById(R.id.spinner_promo_inicial);
        posterior=(Spinner)v.findViewById(R.id.spinner_promo_posterior);

        txt_tipo.setVisibility(View.GONE);
        inicial.setVisibility(View.GONE);
        posterior.setVisibility(View.GONE);
        txt_exp.setVisibility(View.GONE);
        no_expediente.setVisibility(View.GONE);
        consulta.setVisibility(View.GONE);


    }

    public void llenado_spiners(View v){
        ArrayAdapter<String> adaptador=  new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,string_distrito);
        distrito.setAdapter(adaptador);

        ArrayAdapter<String> adaptador2=  new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,string_tipo);
        inicial.setAdapter(adaptador2);
        posterior.setAdapter(adaptador2);


    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////777
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
                Fragment fra= new promociones();
                FragmentTransaction trans= getFragmentManager().beginTransaction();
                getActivity().onBackPressed();// Elimina el fragment anterior
                trans.replace(R.id.content_frame, fra);
                trans.addToBackStack(null);
                trans.commit();
            }
        });


    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





public void accion(final View v){
    distrito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position==1){
                txt_tipo.setVisibility(View.VISIBLE);
                inicial.setVisibility(View.VISIBLE);
                datos_consulta =new Datos();
                datos_consulta.setID("IN(1,2,3,4,5,7,8,9,36,37,38,40,46,50,52,53,54,55,56,57,58,59,60,61,62,64,67,68,69,86,88,89,90)");




            } else if (position==2) {
                txt_tipo.setVisibility(View.VISIBLE);
                inicial.setVisibility(View.VISIBLE);
                datos_consulta =new Datos();
                datos_consulta.setID("IN(28,29,30,34,48,71)");

            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });


    inicial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            if (position==1){
                seleccion_juzgado(v);
                posterior.setVisibility(View.GONE);
                datos_consulta.setQUE_ES("INICIAL");

            } else if (position==2) {
                seleccion_juzgado(v);
                posterior.setVisibility(View.GONE);
                datos_consulta.setQUE_ES("POSTERIOR");
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });




    consulta.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String CAMPO1="";
            String CAMPO2="";
            String query="";
            String[] datos =new String[4];
            try {

                if(datos_consulta.getQUE_ES()=="INICIAL") {

                    Con_sql conStr = new Con_sql();
                    connect = conStr.connections();
                    query = "SELECT Vta_ResiAcuerdosInicial.Expediente,Juzgados.nombre FROM Vta_ResiAcuerdosInicial, Juzgados where id_expediente ="+no_expediente.getText().toString()+" and IdJuzgado "+ datos_consulta.getID()+" and Vta_ResiAcuerdosInicial.IdJuzgado=Juzgados.id_juzgado";
                    //CAMPO1="Vta_ResiAcuerdosInicial.Expediente";
                    //CAMPO2="Juzgados.nombre";

                    CAMPO1="Expediente";
                    CAMPO2="nombre";
                }else if (datos_consulta.getQUE_ES()=="POSTERIOR"){

                    Con_sql conStr = new Con_sql();
                    connect = conStr.connections();
                    query = "SELECT Vta_ResiAcuerdosPromos.Expediente,Juzgados.nombre FROM Vta_ResiAcuerdosPromos,Juzgados where IdPosterior="+no_expediente.getText().toString()+" and IdJuzgado "+ datos_consulta.getID()+" and Vta_ResiAcuerdosPromos.IdJuzgado=Juzgados.id_juzgado";
                   // CAMPO1="Vta_ResiAcuerdosPromos.Expediente";
                    //CAMPO2="Juzgados.nombre";

                    CAMPO1="Expediente";
                    CAMPO2="nombre";


                }

                if (connect == null)
                {
                    ConnectionResult = "Check Your Internet Access!";
                    Toast.makeText(getActivity(),ConnectionResult, Toast.LENGTH_SHORT).show();
                }
                else
                {


                    Statement stmt = connect.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if(!rs.isBeforeFirst()){
                        Toast.makeText(getActivity(),"No se encontraron Datos", Toast.LENGTH_SHORT).show();
                    }

                    else {

                        txt_tipo.setVisibility(View.GONE);
                        inicial.setVisibility(View.GONE);
                        posterior.setVisibility(View.GONE);
                        txt_exp.setVisibility(View.GONE);
                        no_expediente.setVisibility(View.GONE);
                        consulta.setVisibility(View.GONE);
                        distrito.setVisibility(View.GONE);
                        visibilidad(true);

                        while (rs.next()){
                            datos[1]=rs.getString(CAMPO1).toString();
                            datos[2]=rs.getString(CAMPO2).toString();
                            txtdistrito.setText("LA PROMOCIÓN CON FOLIO "+no_expediente.getText()+" DEL EXPEDIENTE "+datos[1]+" DEL JUZGADO "+datos[2]+" YA FUE ACORDADA");
                            txtdistrito.setPadding(50,200,50,200);

                        }



                    }

                }

            }catch (Exception ex)
            {
                esSatisfactorio = false;
                ConnectionResult = ex.getMessage();
            }
        }
    });





}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
