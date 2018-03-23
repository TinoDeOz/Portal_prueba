package com.curso.tinodeoz.portal_prueba;
import android.annotation.SuppressLint;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Con_sql {

    String IP,DB,User,Pass;

    @SuppressLint("NewApi")
    public Connection connections(){
        IP="";
        DB="";
        User="";
        Pass="";


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        java.sql.Connection connection=null;
        String ConnectionURL="";

        try {
            ConnectionURL = "jdbc:jtds:sqlserver://200.79.183.181:1433;databaseName=tsjhciviles;user=Portal;password=Residencia2017;";
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection=DriverManager.getConnection(ConnectionURL);

        }catch (SQLException se){
            Log.e("Error en Sql",se.getMessage());
        }catch (ClassNotFoundException e){
            Log.e("Error en clase",e.getMessage());
        }catch (Exception ex){
            Log.e("Error en Exception",ex.getMessage());
        }

        return connection;

    }

    public Connection connectionstulancingo(){
        IP="";
        DB="";
        User="";
        Pass="";


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        java.sql.Connection connection=null;
        String ConnectionURL="";

        try {
            ConnectionURL = "jdbc:jtds:sqlserver://200.79.183.194:1433;databaseName=tsjhciviles;user=Portal;password=Residencia2017;";
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection=DriverManager.getConnection(ConnectionURL);

        }catch (SQLException se){
            Log.e("Error en Sql",se.getMessage());
        }catch (ClassNotFoundException e){
            Log.e("Error en clase",e.getMessage());
        }catch (Exception ex){
            Log.e("Error en Exception",ex.getMessage());
        }

        return connection;

    }

    public Connection connection_penales(){
        IP="";
        DB="";
        User="";
        Pass="";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        java.sql.Connection connection=null;
        String ConnectionURL="";

        try {
            ConnectionURL = "jdbc:jtds:sqlserver://200.79.183.181:1433;databaseName=tsjhpenales;user=Portal;password=Residencia2017;";
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection=DriverManager.getConnection(ConnectionURL);

        }catch (SQLException se){
            Log.e("Error en Sql",se.getMessage());
        }catch (ClassNotFoundException e){
            Log.e("Error en clase",e.getMessage());
        }catch (Exception ex){
            Log.e("Error en Exception",ex.getMessage());
        }
        return connection;

    }

    public Connection connection_notifica(){
        IP="";
        DB="";
        User="";
        Pass="";


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        java.sql.Connection connection=null;
        String ConnectionURL="";

        try {
            ConnectionURL = "jdbc:jtds:sqlserver://200.79.183.181:1433;databaseName=osorio_notifica;user=Portal;password=Residencia2017;";
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection=DriverManager.getConnection(ConnectionURL);

        }catch (SQLException se){
            Log.e("Error en Sql",se.getMessage());
        }catch (ClassNotFoundException e){
            Log.e("Error en clase",e.getMessage());
        }catch (Exception ex){
            Log.e("Error en Exception",ex.getMessage());
        }

        return connection;
    }

}