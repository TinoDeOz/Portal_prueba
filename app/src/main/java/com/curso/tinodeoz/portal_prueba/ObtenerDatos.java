package com.curso.tinodeoz.portal_prueba;


import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObtenerDatos {


    Connection connect;
    String ConnectionResult="";
    Boolean esSatisfactorio=false;


    public List<Map<Integer,String>> getdata(String sentencia){

        List<Map<Integer, String>> data = null;
        data = new ArrayList<Map<Integer, String>>();
        try
        {
            Con_sql conStr=new Con_sql();
            connect =conStr.connections();        // Connect to database
            if (connect == null)
            {
                ConnectionResult = "Check Your Internet Access!";
            }
            else
            {
                // Change below query according to your own database.
                String query = sentencia;
                //String query = "SELECT Fecha, Perfil from Vta_ResiUbicacion Where IdJuzgado=1 and Expediente='000021/2009'";

                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()){
                    Map<Integer,String> datanum=new HashMap<Integer, String>();
                    Map<Integer,String> datanum2=new HashMap<Integer, String>();
                    datanum.put(null,rs.getString("Fecha"));
                    datanum2.put(null,rs.getString("Perfil"));
                    data.add(datanum);
                    data.add(datanum2);

                }


                ConnectionResult = " successful";
                esSatisfactorio=true;
                connect.close();
            }
        }
        catch (Exception ex)
        {
            esSatisfactorio = false;
            ConnectionResult = ex.getMessage();
        }

        return data;
    }


}
