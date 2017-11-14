package com.curso.tinodeoz.portal_prueba;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

import java.sql.ResultSet;
import java.sql.Statement;

public class Portafolio extends Fragment {

    DB base;
    Data_Portafolio nuevo_registro;

    Button consulta,agregar,mostrar,nuevaa,salir;
    TableLayout tabla;


    public void inicio(View v){

        agregar=(Button)v.findViewById(R.id.Agregar_Expediente);
        mostrar=(Button)v.findViewById(R.id.Mostrar);
        tabla=(TableLayout)v.findViewById(R.id.Tabla_portafolio);
        nuevaa=(Button)v.findViewById(R.id.nueva_consulta);
        salir=(Button)v.findViewById(R.id.Salir);

        nuevaa.setVisibility(View.GONE);
        salir.setVisibility(View.GONE);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void agregar() {

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fra = new ubi_exp();
                FragmentTransaction trans = getFragmentManager().beginTransaction();
                //getActivity().onBackPressed();// Elimina el fragment anterior
                trans.replace(R.id.content_frame, fra);
                trans.addToBackStack(null);
                trans.commit();
            }
        });

    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////7
    public void mostrar(){

        mostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String []contenido= new String[7];
                String frase="";
                String columnas []={"distrito","juzgado","expediente","ubicacion","fecha"};

                //String columnas []={"distrito","juzgado"};

                SQLiteDatabase db=base.getReadableDatabase();

                try {    Cursor c=db.query("portafolio",columnas,null,null,null,null,null);

                    int dis,juz,exp,ubi,fech;

                    dis=c.getColumnIndex("distrito");
                    juz=c.getColumnIndex("juzgado");
                    exp=c.getColumnIndex("expediente");
                    ubi=c.getColumnIndex("ubicacion");
                    fech=c.getColumnIndex("fecha");
                    Encabezado("Distrito","Juzgado","No.de Expediente","Ubicacion","Fecha");
                    nuevaa.setVisibility(View.VISIBLE);
                    salir.setVisibility(View.VISIBLE);
                    agregar.setVisibility(View.GONE);
                    mostrar.setVisibility(View.GONE);

                    if(c.moveToFirst()){
                        do{

                        contenido[1] = c.getString(dis);
                        contenido[2] = c.getString(juz);
                        contenido[3] = c.getString(exp);
                        contenido[4] = c.getString(ubi);
                        contenido[5] = c.getString(fech);
                        llenadoTabla(contenido[1],contenido[2],contenido[3],contenido[4],contenido[5]);

                    } while (c.moveToNext());

                    }

                    //frase ="El total de matas es"+c.getString(id)+c.getString(an)+c.getString(lar)+c.getString(to);
                    base.closeDB();
                    Toast.makeText(getActivity(),"GUARDADO", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    base.closeDB();
                    Toast.makeText(getActivity(),"Error al guardar", Toast.LENGTH_SHORT).show();

                }



            }
        });






    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void nueva() {

       nuevaa.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Fragment fra = new Portafolio();
               FragmentTransaction trans = getFragmentManager().beginTransaction();
              // getActivity().onBackPressed();// Elimina el fragment anterior
               trans.replace(R.id.content_frame, fra);
               trans.addToBackStack(null);
               trans.commit();
           }
       });

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////77777

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////77777
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
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


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
        txtTabla.setWidth(400);
        row.addView(txtTabla);


        txtTabla2=new TextView(getActivity());
        txtTabla2.setGravity(Gravity.CENTER_HORIZONTAL);
        txtTabla2.setBackgroundColor(Color.TRANSPARENT);
        txtTabla2.setText(txt2);
        txtTabla2.setTextColor(Color.parseColor("#B1613e"));
        txtTabla2.setWidth(400);
        row.addView(txtTabla2);


        txtTabla3=new TextView(getActivity());
        txtTabla3.setGravity(Gravity.CENTER_VERTICAL);
        txtTabla3.setBackgroundColor(Color.TRANSPARENT);
        txtTabla3.setText(txt3);
        txtTabla3.setTextColor(Color.parseColor("#B1613e"));
        txtTabla3.setWidth(400);
        row.addView(txtTabla3);


        txtTabla4=new TextView(getActivity());
        txtTabla4.setGravity(Gravity.CENTER_VERTICAL);
        txtTabla4.setBackgroundColor(Color.TRANSPARENT);
        txtTabla4.setText(txt4);
        txtTabla4.setTextColor(Color.parseColor("#B1613e"));
        txtTabla4.setWidth(400);
        row.addView(txtTabla4);


        txtTabla5=new TextView(getActivity());
        txtTabla5.setGravity(Gravity.CENTER_VERTICAL);
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
        borde1.setWidth(400);
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
        txtTabla.setWidth(400);
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







    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Portafolio() {
        // Required empty public constructor
    }


    public static Portafolio newInstance(String param1, String param2) {
        Portafolio fragment = new Portafolio();
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
        View v=inflater.inflate(R.layout.fragment_portafolio, container, false);

        base= new DB(getActivity());
        inicio(v);
        nueva();
        mostrar();
        salir();
        agregar();

        return v;
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
