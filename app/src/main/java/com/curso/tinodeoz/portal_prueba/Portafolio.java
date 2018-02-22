package com.curso.tinodeoz.portal_prueba;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Portafolio extends Fragment {

    Connection connect;
    String ConnectionResult="";
    Boolean esSatisfactorio=false;


    RelativeLayout RL;
    DB base;
    Data_Portafolio nuevo_registro;

    Button consulta,agregar,mostrar,nuevaa,salir, btn_eliminar, Btn_opc_eliminar, Actualizar;
    TableLayout tabla;
    Spinner opc,distrito, juzgado1,juzgado2;
    TextView opcion,txt_juzgado,txt_exp, txt_distrito, txt_eliminar;
    EditText Expediente;


    String[] string_opcion={"Selecciona Aqui:","POR DISTRITO","POR JUZGADO","TODOS LOS EXPEDIENTES"};
    String[] string_distrito={"Selecciona Aqui:","PACHUCA DE SOTO","TULANCINGO DE BRAVO"};
    String[] string_juzgado={"Selecciona Aqui:","PRIMERO CIVIL PACHUCA","SEGUNDO CIVIL PACHUCA","TERCERO CIVIL PACHUCA","CUARTO CIVIL PACHUCA",
            "PRIMERO MERCANTIL PACHUCA","SEGUNDO MERCANTIL PACHUCA","PRIMERO FAMILIAR PACHUCA","SEGUNDO FAMILIAR PACHUCA","TERCERO FAMILIAR PACHUCA","PRIMERO CIVIL Y FAMILIAR DE TULANCINGO","SEGUNDO CIVIL Y FAMILIAR DE TULANCINGO","TERCERO CIVIL Y FAMILIAR DE TULANCINGO"};
    //String[] string_juzgado2={"Selecciona Aqui:","PRIMERO CIVIL Y FAMILIAR DE TULANCINGO","SEGUNDO CIVIL Y FAMILIAR DE TULANCINGO","TERCERO Y FAMILIAR DE TULANCINGO"};



    public void inicio(View v){
        RL= (RelativeLayout)v.findViewById(R.id.Relative) ;
        Btn_opc_eliminar=(Button)v.findViewById(R.id.borrar);
        Actualizar=(Button)v.findViewById(R.id.Actualizar);
        txt_eliminar=(TextView)v.findViewById(R.id.txt_expediente);
        Expediente=(EditText)v.findViewById(R.id.txt_no_expediente);
        btn_eliminar=(Button)v.findViewById(R.id.eliminar);



        opcion=(TextView)v.findViewById(R.id.txt_busqueda);
        opc=(Spinner)v.findViewById(R.id.spinner_busqueda);
        txt_distrito=(TextView)v.findViewById(R.id.txt_distrito);
        txt_juzgado=(TextView)v.findViewById(R.id.txt_juzgado);
        distrito=(Spinner)v.findViewById(R.id.spinner_distrito);
        juzgado1=(Spinner)v.findViewById(R.id.spinner_juzgado);
        juzgado2=(Spinner)v.findViewById(R.id.spinner_juzgado2);

        agregar=(Button)v.findViewById(R.id.Agregar_Expediente);
        mostrar=(Button)v.findViewById(R.id.Mostrar);
        tabla=(TableLayout)v.findViewById(R.id.Tabla_portafolio);
        nuevaa=(Button)v.findViewById(R.id.nueva_consulta);
        salir=(Button)v.findViewById(R.id.Salir);

        nuevaa.setVisibility(View.GONE);
        salir.setVisibility(View.GONE);
        txt_juzgado.setVisibility(View.GONE);
        juzgado1.setVisibility(View.GONE);
        juzgado2.setVisibility(View.GONE);

        distrito.setVisibility(View.GONE);
        txt_distrito.setVisibility(View.GONE);
        opc.setVisibility(View.GONE);
        opcion.setVisibility(View.GONE);

        Btn_opc_eliminar.setVisibility(View.GONE);
        txt_eliminar.setVisibility(View.GONE);
        Expediente.setVisibility(View.GONE);
        btn_eliminar.setVisibility(View.GONE);
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void llenado_spiners(){
        ArrayAdapter<String> adaptador=  new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,string_distrito);
        distrito.setAdapter(adaptador);

        ArrayAdapter<String> adaptador2=  new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,string_juzgado);
        juzgado1.setAdapter(adaptador2);

        //ArrayAdapter<String> adaptador3=  new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,string_juzgado2);
        //juzgado2.setAdapter(adaptador3);

        ArrayAdapter<String> adaptador4=  new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,string_opcion);
        opc.setAdapter(adaptador4);
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void acciones(final View v){

        opc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==1){
                    txt_distrito.setVisibility(View.VISIBLE);
                    distrito.setVisibility(View.VISIBLE);
                    txt_juzgado.setVisibility(View.GONE);
                    juzgado1.setVisibility(View.GONE);



                } else if (position==2) {
                    txt_distrito.setVisibility(View.GONE);
                    distrito.setVisibility(View.GONE);
                    txt_juzgado.setVisibility(View.VISIBLE);
                    juzgado1.setVisibility(View.VISIBLE);


                } else if (position==3) {
                    Consulta("");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        distrito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==1){
                    nuevo_registro= new Data_Portafolio();
                    nuevo_registro.setDistrito("Pachuca de Soto.");
                    Consulta("distrito='"+nuevo_registro.getDistrito()+"'");


                } else if (position==2) {
                    nuevo_registro= new Data_Portafolio();
                    nuevo_registro.setDistrito("Tulancingo de Bravo.");
                    Consulta("distrito='"+nuevo_registro.getDistrito()+"'");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        juzgado1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nuevo_registro= new Data_Portafolio();
                if (position==1){
                    nuevo_registro.setJuzgado("PRIMERO CIVIL PACHUCA");
                    Consulta("juzgado='"+nuevo_registro.getJuzgado()+"'");

                } else if (position==2) {
                    nuevo_registro.setJuzgado("SEGUNDO CIVIL PACHUCA");
                    Consulta("juzgado='"+nuevo_registro.getJuzgado()+"'");
                }
                else if (position==3) {
                    nuevo_registro.setJuzgado("TERCERO CIVIL PACHUCA");
                    Consulta("juzgado='"+nuevo_registro.getJuzgado()+"'");

                }
                else if (position==4) {
                    nuevo_registro.setJuzgado("CUARTO CIVIL PACHUCA");
                    Consulta("juzgado='"+nuevo_registro.getJuzgado()+"'");
                }
                else if (position==5) {
                    nuevo_registro.setJuzgado("PRIMERO MERCANTIL PACHUCA");
                    Consulta("juzgado='"+nuevo_registro.getJuzgado()+"'");

                }else if (position==6) {
                    nuevo_registro.setJuzgado("SEGUNDO MERCANTIL PACHUCA");
                    Consulta("juzgado='"+nuevo_registro.getJuzgado()+"'");

                }else if (position==7) {
                    nuevo_registro.setJuzgado("PRIMERO FAMILIAR PACHUCA");
                    Consulta("juzgado='"+nuevo_registro.getJuzgado()+"'");

                }else if (position==8) {

                    nuevo_registro.setJuzgado("SEGUNDO FAMILIAR PACHUCA");
                    Consulta("juzgado='"+nuevo_registro.getJuzgado()+"'");

                }else if (position==9) {
                    nuevo_registro.setJuzgado("TERCERO FAMILIAR PACHUCA");
                    Consulta("juzgado='"+nuevo_registro.getJuzgado()+"'");
                }else if (position==10) {
                    nuevo_registro.setJuzgado("PRIMERO CIVIL Y FAMILIAR DE TULANCINGO");
                    Consulta("juzgado='"+nuevo_registro.getJuzgado()+"'");

                }else if (position==11) {
                    nuevo_registro.setJuzgado("SEGUNDO CIVIL Y FAMILIAR DE TULANCINGO");
                    Consulta("juzgado='"+nuevo_registro.getJuzgado()+"'");

                }else if (position==12) {
                    nuevo_registro.setJuzgado("TERCERO CIVIL Y FAMILIAR DE TULANCINGO");
                    Consulta("juzgado='"+nuevo_registro.getJuzgado()+"'");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
                opc.setVisibility(View.VISIBLE);
                opcion.setVisibility(View.VISIBLE);
                mostrar.setVisibility(View.GONE);
                agregar.setVisibility(View.GONE);
                Actualizar.setVisibility(View.GONE);
            }
        });
    }

    public void ActualizarTabla(String query){

        String []contenido= new String[8];
        String[] datos =new String[3];
        String frase="";
        String columnas []={"distrito","juzgado","expediente","ubicacion","fecha","idjuzgado"};

        //String columnas []={"distrito","juzgado"};

        SQLiteDatabase db=base.getReadableDatabase();

        try {    Cursor c=db.query("portafolio",columnas,query,null,null,null,null);

            int dis,juz,exp,ubi,fech,idjuz;

            dis=c.getColumnIndex("distrito");
            juz=c.getColumnIndex("juzgado");
            idjuz=c.getColumnIndex("idjuzgado");
            exp=c.getColumnIndex("expediente");
            ubi=c.getColumnIndex("ubicacion");
            fech=c.getColumnIndex("fecha");

            Encabezado("Distrito","Juzgado","No.de Expediente","Ubicacion","Fecha");
            nuevaa.setVisibility(View.VISIBLE);
            salir.setVisibility(View.VISIBLE);
            Btn_opc_eliminar.setVisibility(View.GONE);
            agregar.setVisibility(View.GONE);
            mostrar.setVisibility(View.GONE);
            txt_juzgado.setVisibility(View.GONE);
            juzgado1.setVisibility(View.GONE);
            juzgado2.setVisibility(View.GONE);
            distrito.setVisibility(View.GONE);
            txt_distrito.setVisibility(View.GONE);
            opc.setVisibility(View.GONE);
            opcion.setVisibility(View.GONE);

            if(c.moveToFirst()){
                do{

                    contenido[1] = c.getString(dis);
                    contenido[2] = c.getString(juz);
                    contenido[3] = c.getString(exp);
                    contenido[4] = c.getString(ubi);
                    contenido[5] = c.getString(fech);
                    contenido[6] = c.getString(idjuz);
                    try {
                    if ( contenido[1].equals("Pachuca de Soto.")){
                        Con_sql conStr = new Con_sql();
                        connect = conStr.connections();

                    }
                   else if ( contenido[1].equals("Tulancingo de Bravo.")){
                        Con_sql conStr = new Con_sql();
                        connect = conStr.connectionstulancingo();


                       // llenadoTabla(contenido[1]+" "+contenido[6],contenido[2],contenido[3],contenido[4],contenido[5]);
                    }


                            if (connect == null)
                            {
                                ConnectionResult = "Check Your Internet Access!";
                                Toast.makeText(getActivity(),ConnectionResult, Toast.LENGTH_SHORT).show();

                            }
                            else
                            {

                                String consulta = "Select * from Vta_ResiUbicacion Where IdJuzgado="+contenido[6]+ " and Expediente='"+contenido[3]+"' Order by Fecha DESC;";
                                Statement stmt = connect.createStatement();
                                ResultSet rs = stmt.executeQuery(consulta);
                                if(!rs.isBeforeFirst()){
                                    Toast.makeText(getActivity(),"No se encontraron Datos", Toast.LENGTH_SHORT).show();
                                }

                                else {

                                    while (rs.next()){

                                        datos[1]=rs.getString("Fecha");
                                        datos[2]=rs.getString("Perfil");

                                    }
                                /*    nuevo_registro.setExpediente(contenido[3]);
                                    nuevo_registro.setUbicacion(datos[2]);
                                    nuevo_registro.setFecha(datos[1]+"tt");
                                    nuevo_registro.setIDJuzgado(contenido[6]);
                                    nuevo_registro.setJuzgado(contenido[2]);
                                    nuevo_registro.setDistrito(contenido[1]);*/

                                    base.borrar(contenido[3],contenido[2]);
                                    base.Agregar(contenido[1],contenido[2],contenido[6],contenido[3],datos[2],datos[1]);
                                    llenadoTabla(contenido[1],contenido[2],contenido[3],datos[2],datos[1] );
                                }

                            }

                        }catch (Exception ex){
                            esSatisfactorio = false;
                            ConnectionResult = ex.getMessage();
                            Toast.makeText(getActivity(),"Error"+ ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }

                       // base.closeDB();


                } while (c.moveToNext());
            }
            base.closeDB();
            Toast.makeText(getActivity(),"¡CONSULTA COMPLETADA!", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            base.closeDB();
            Toast.makeText(getActivity(),"No se encontraron datos", Toast.LENGTH_SHORT).show();
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public void Consulta(String query){

    String []contenido= new String[7];
    String frase="";
    String columnas []={"distrito","juzgado","expediente","ubicacion","fecha"};

    //String columnas []={"distrito","juzgado"};

    SQLiteDatabase db=base.getReadableDatabase();

    try {    Cursor c=db.query("portafolio",columnas,query,null,null,null,null);

        int dis,juz,exp,ubi,fech;

        dis=c.getColumnIndex("distrito");
        juz=c.getColumnIndex("juzgado");
        exp=c.getColumnIndex("expediente");
        ubi=c.getColumnIndex("ubicacion");
        fech=c.getColumnIndex("fecha");
        Encabezado("Distrito","Juzgado","No.de Expediente","Ubicacion","Fecha");
        nuevaa.setVisibility(View.VISIBLE);
        salir.setVisibility(View.VISIBLE);
        //Btn_opc_eliminar.setVisibility(View.VISIBLE);
        agregar.setVisibility(View.GONE);
        mostrar.setVisibility(View.GONE);
        txt_juzgado.setVisibility(View.GONE);
        juzgado1.setVisibility(View.GONE);
        juzgado2.setVisibility(View.GONE);
        distrito.setVisibility(View.GONE);
        txt_distrito.setVisibility(View.GONE);
        opc.setVisibility(View.GONE);
        opcion.setVisibility(View.GONE);

        if(c.moveToFirst()){
            do{

                contenido[1] = c.getString(dis);
                contenido[2] = c.getString(juz);
                contenido[3] = c.getString(exp);
                contenido[4] = c.getString(ubi);
                contenido[5] = c.getString(fech);

                if ( contenido[1].equals("Pachuca de Soto.")){
                    llenadoTabla(contenido[1],contenido[2],contenido[3],contenido[4],contenido[5]);
                }
                if ( contenido[1].equals("Tulancingo de Bravo.")){
                    llenadoTabla(contenido[1],contenido[2],contenido[3],contenido[4],contenido[5]);
                }
            } while (c.moveToNext());

        }

        base.closeDB();
        Toast.makeText(getActivity(),"¡CONSULTA COMPLETADA!", Toast.LENGTH_SHORT).show();
    }catch (Exception e){
        base.closeDB();
        Toast.makeText(getActivity(),"No se encontraron datos", Toast.LENGTH_SHORT).show();

    }
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void Actualizar_Datos(){
        Actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActualizarTabla("");
                Actualizar.setVisibility(View.GONE);
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
public void Eliminar(){
    Btn_opc_eliminar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Btn_opc_eliminar.setVisibility(View.GONE);
            nuevaa.setVisibility(View.GONE);
            salir.setVisibility(View.GONE);
            txt_eliminar.setVisibility(View.VISIBLE);
            Expediente.setVisibility(View.VISIBLE);
            btn_eliminar.setVisibility(View.VISIBLE);
            RL.setVisibility(View.GONE);
        }
    });
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////77
    public void Consulta_Eliminar(final String Exp, final String ju){
    /*btn_eliminar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    });*/
        String text_web = "<html><body style=\"text-align:justify; font-size:12px; line-height:20px; color:white;\"> %s </body></html>";
        String texto= "¿Esta seguro de eliminar el expediente numero  "+Exp+ "  del Juzgado "+ju+"  de su portafolio movíl?";
        WebView web2= new WebView(getContext());
        web2.loadData(String.format(text_web, texto), "text/html; charset=utf-8","UTF-8");
        web2.setBackgroundColor(Color.parseColor("#00FFFFFF"));

        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getActivity());
        dialogo1.setTitle("Confirmar");
        dialogo1.setCancelable(false);
        dialogo1.setView(web2);

        dialogo1.setPositiveButton("Eliminar y Salir", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                base.borrar(Exp,ju);
                base.closeDB();
                Toast.makeText(getActivity(),"Eliminado Satisfactoriamente.",Toast.LENGTH_LONG).show();
                Fragment fra= new prueba();
                FragmentTransaction trans= getFragmentManager().beginTransaction();
                getActivity().onBackPressed();// Elimina el fragment anterior
                trans.replace(R.id.content_frame, fra);
                trans.addToBackStack(null);
                trans.commit();

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
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void llenadoTabla(String txt1, final String txt2, String txt3, String txt4, String txt5){
        TableRow.LayoutParams layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);

        TableRow row= new TableRow(getActivity());
        row.setLayoutParams(layoutFila);
        row.setGravity(Gravity.CENTER_VERTICAL);


        final TextView txtTabla, txtTabla2,txtTabla3,txtTabla4,txtTabla5;



        txtTabla=new TextView(getActivity());
        txtTabla.setGravity(Gravity.CENTER);
        txtTabla.setBackgroundColor(Color.TRANSPARENT);
        txtTabla.setText(txt1);
        txtTabla.setTextColor(Color.parseColor("#B1613e"));
        txtTabla.setWidth(400);
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
        //txtTabla3.setTextColor(Color.BLUE);
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

        TextView txtTabla7=new TextView(getActivity());
        txtTabla7.setGravity(Gravity.CENTER);
        txtTabla7.setBackgroundColor(Color.TRANSPARENT);
        txtTabla7.setTextColor(Color.BLUE);
        txtTabla7.setText("Eliminar del portafolio.");
        txtTabla7.setWidth(400);
        row.addView(txtTabla7);

        txtTabla7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              Consulta_Eliminar(txtTabla3.getText().toString(),txtTabla2.getText().toString());
            }
        });


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

        TextView borde6=new TextView(getActivity());
        borde6.setBackgroundColor(Color.parseColor("#000000"));
        borde6.setWidth(400);
        borde6.setHeight(10);
        borde.addView(borde6);

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

        TextView txtTabla7=new TextView(getActivity());
        txtTabla7.setGravity(Gravity.CENTER_HORIZONTAL);
        txtTabla7.setBackgroundColor(Color.parseColor("#B1613e"));
        txtTabla7.setTextColor(Color.WHITE);
        txtTabla7.setText("Eliminar");
        txtTabla7.setWidth(400);
        txtTabla7.setHeight(x);
        row.addView(txtTabla7);


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
        acciones(v);
        nueva();
        mostrar();
        salir();
        agregar();
        llenado_spiners();
        Eliminar();
        //Consulta_Eliminar();
        Actualizar_Datos();
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
