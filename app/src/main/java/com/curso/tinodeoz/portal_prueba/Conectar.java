package com.curso.tinodeoz.portal_prueba;


import android.os.AsyncTask;
import android.os.StrictMode;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conectar extends AsyncTask <Void,Integer,Boolean> {
    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            String ConnectionURL = "jdbc:jtds:sqlserver://200.79.183.181:1433;databaseName=tsjhciviles;user=Portal;password=Residencia2017;";
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            Connection conexion = DriverManager.getConnection(ConnectionURL);

            if (conexion==null){
                System.out.println("Conectado");
                return false;
            }

        }catch (NoClassDefFoundError e){
            System.out.println("No Conectado");

        }catch (ClassNotFoundException e){
            System.out.println("No Conectado");

        }catch (Exception e){
            System.out.println("No Conectado");
            return false;
        }
        System.out.println("Conectado");
        return true;
    }






    }





