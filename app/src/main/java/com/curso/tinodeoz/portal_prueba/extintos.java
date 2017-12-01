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


public class extintos extends Fragment {

    Connection connect;
    String ConnectionResult="";
    Boolean esSatisfactorio=false;

    TableLayout tabla;
    Datos datos_consulta;


    WebView web;
    TextView txt_materia,txt_exp, txtJuszgado;
    EditText no_expediente;
    Button consulta;
    Spinner juzgado,materia;

    String[] string_juzgado={"Selecciona Aqui:","TERCERO PENAL PACHUCA","PRIMERO MIXTO MENOR PACHUCA","SEGUNDO MIXTO MENOR PACHUCA"};
    String[] string_materia={"Selecciona Aqui:","MATERIA CIVIL Y MERCANTIL","MATERIA PENAL"};

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public extintos() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static extintos newInstance(String param1, String param2) {
        extintos fragment = new extintos();
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
        View v=inflater.inflate(R.layout.fragment_extintos, container, false);
        mensaje();
        inicio(v);
        llenado_spiners(v);
        accciones(v);

        finales(v);
        nueva();
        salir();
        visibilidad(false);




        return v;
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////7
public void mensaje(){

    String text_web = "<html><body style=\"text-align:justify; font-size:12px; line-height:20px; color:white;\"> %s </body></html>";
    String texto= "Con este servicio usted podrá conocer la ubicación actual de su expediente o causa que se encontraba anteriormente radicado en algunos de los juzgados extintos del Estado, aquí se le proporcionara el número de expediente o causa actual y el juzgado en el que se radica actualmente.  Para ello será necesario seleccionar el Juzgado en donde se encontraba su asunto anteriormente, tratándose de juzgados mixtos deberá indicar la materia a la cual pertenece su asunto y por ultimo ingresar el número de expediente o causa que se quiere consultar. ";
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
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////7
public void inicio(View v){

    txt_exp=(TextView)v.findViewById(R.id.txt_ext_no_causa);
    txtJuszgado=(TextView)v.findViewById(R.id.txt_extintos_juzgado);
    txt_materia=(TextView)v.findViewById(R.id.txt_extintos_materia);
    consulta=(Button)v.findViewById(R.id.btn_extintos_consultar);
    no_expediente=(EditText)v.findViewById(R.id.txt_extinto_no_causa) ;


    tabla=(TableLayout)v.findViewById(R.id.Tabla_btn_extintos);

    juzgado=(Spinner)v.findViewById(R.id.spinner_extintos_juzgado);
    materia=(Spinner)v.findViewById(R.id.spinner_extintos_materia);

    txt_exp.setVisibility(View.GONE);
    materia.setVisibility(View.GONE);
    txt_materia.setVisibility(View.GONE);
    no_expediente.setVisibility(View.GONE);
    consulta.setVisibility(View.GONE);


}

    public void llenado_spiners(View v){
        ArrayAdapter<String> adaptador=  new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,string_juzgado);
        juzgado.setAdapter(adaptador);

        ArrayAdapter<String> adaptador2=  new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,string_materia);
        materia.setAdapter(adaptador2);

    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
                Fragment fra= new extintos();
                FragmentTransaction trans= getFragmentManager().beginTransaction();
                getActivity().onBackPressed();// Elimina el fragment anterior
                trans.replace(R.id.content_frame, fra);
                trans.addToBackStack(null);
                trans.commit();
            }
        });


    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void accciones(View v){

        juzgado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position==1){
                    datos_consulta =new Datos();
                    datos_consulta.setID("9");
                    datos_consulta.setQUE_ES("PENAL");


            txt_exp.setText("Número De Causa Anterior:");
            txt_exp.setVisibility(View.VISIBLE);
            no_expediente.setVisibility(View.VISIBLE);
            consulta.setVisibility(View.VISIBLE);

            materia.setVisibility(View.GONE);
            txt_materia.setVisibility(View.GONE);

        }else if(position==2){
            materia.setVisibility(View.VISIBLE);
            txt_materia.setVisibility(View.VISIBLE);
                    datos_consulta =new Datos();
                    datos_consulta.setID("4");

        }else if(position==3){
            materia.setVisibility(View.VISIBLE);
            txt_materia.setVisibility(View.VISIBLE);
                    datos_consulta =new Datos();
                    datos_consulta.setID("37");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
});
        materia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position==1){

                    txt_exp.setText("Número de expediente anterior:");
                    txt_exp.setVisibility(View.VISIBLE);
                    no_expediente.setVisibility(View.VISIBLE);
                    consulta.setVisibility(View.VISIBLE);

                    datos_consulta.setQUE_ES("MERCANTIL");

                }else if(position==2){
                    txt_exp.setText("Número De Causa Anterior:");
                    txt_exp.setVisibility(View.VISIBLE);
                    no_expediente.setVisibility(View.VISIBLE);
                    consulta.setVisibility(View.VISIBLE);
                    datos_consulta.setQUE_ES("PENAL");

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
                String[] datos =new String[3];;
                try {

                    if(datos_consulta.getQUE_ES()=="MERCANTIL") {


                        Toast.makeText(getActivity(),"Esperé unos segundos...", Toast.LENGTH_SHORT).show();
                        while (no_expediente.getText().toString().length()<11){
                            String ejemplo=no_expediente.getText().toString();
                            ejemplo="0"+ejemplo;
                            no_expediente.setText(ejemplo);
                        }

                        Con_sql conStr = new Con_sql();
                        connect = conStr.connections();
                        query = "SELECT  ExpeNuevo,JuzNuevo  FROM Vta_ResiExpeExtinto where JuzAnte="+datos_consulta.getID()+ " and ExpeAnte='"+no_expediente.getText().toString()+"'";

                        CAMPO1="ExpeNuevo";
                        CAMPO2="JuzNuevo";

                    }else if (datos_consulta.getQUE_ES()=="PENAL"){

                        while (no_expediente.getText().toString().length()<9){
                            String ejemplo=no_expediente.getText().toString();
                            ejemplo="0"+ejemplo;
                            no_expediente.setText(ejemplo);
                        }

                        Con_sql conStr = new Con_sql();
                        connect = conStr.connection_penales();
                        query = "SELECT Nva_causa, Nvo_Juzgado FROM Vta_ResiCausaExtinta where Juzga_anterior="+datos_consulta.getID()+ " and NoCausa='"+no_expediente.getText().toString()+"'";
                        CAMPO1="Nva_causa";
                        CAMPO2="Nvo_Juzgado";


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
                            Encabezado("NÚMERO DE EXPEDIENTE ACTUAL","JUZGADO EN EL QUE SE RADICO");
                            txt_exp.setVisibility(View.GONE);
                            materia.setVisibility(View.GONE);
                            txt_materia.setVisibility(View.GONE);
                            no_expediente.setVisibility(View.GONE);
                            consulta.setVisibility(View.GONE);
                            juzgado.setVisibility(View.GONE);
                            txtJuszgado.setVisibility(View.GONE);
                            visibilidad(true);

                            while (rs.next()){
                                datos[1]=rs.getString(CAMPO1).toString();
                                datos[2]=rs.getString(CAMPO2).toString();
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
        });



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
        txtTabla.setWidth(600);
        row.addView(txtTabla);


        txtTabla2=new TextView(getActivity());
        txtTabla2.setGravity(Gravity.CENTER_HORIZONTAL);
        txtTabla2.setBackgroundColor(Color.TRANSPARENT);
        txtTabla2.setText(txt2);
        txtTabla2.setTextColor(Color.parseColor("#B1613e"));
        txtTabla2.setWidth(600);

        row.addView(txtTabla2);
        tabla.addView(row);

        TableRow borde= new TableRow(getActivity());
        row.setLayoutParams(layoutFila);


        TextView borde1, borde2;

        borde1=new TextView(getActivity());
        borde1.setBackgroundColor(Color.parseColor("#000000"));
        borde1.setWidth(600);
        borde1.setHeight(10);
        borde.addView(borde1);


        borde2=new TextView(getActivity());
        borde2.setBackgroundColor(Color.parseColor("#000000"));
        borde2.setWidth(600);
        borde2.setHeight(10);
        borde.addView(borde2);

        //tabla.addView(row ,new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
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
        txtTabla.setWidth(600);
        row.addView(txtTabla);


        txtTabla2=new TextView(getActivity());
        txtTabla2.setGravity(Gravity.CENTER_HORIZONTAL);
        txtTabla2.setBackgroundColor(Color.parseColor("#B1613e"));
        txtTabla2.setText(txt2);
        txtTabla2.setTextColor(Color.WHITE);
        txtTabla2.setWidth(600);
        row.addView(txtTabla2);

       TextView txtTabla6=new TextView(getActivity());
        txtTabla6.setGravity(Gravity.CENTER_HORIZONTAL);
        txtTabla6.setBackgroundColor(Color.TRANSPARENT);
        txtTabla6.setWidth(200);
        row.addView(txtTabla6);


        tabla.addView(row);
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////77








    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
