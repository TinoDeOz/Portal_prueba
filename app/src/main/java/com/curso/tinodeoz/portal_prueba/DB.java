package com.curso.tinodeoz.portal_prueba;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DB extends SQLiteOpenHelper {


    private static final  String NAME_DATABASE="DB";
    private static final int VERSION_DATABASE=1;
    private static final  String ID="id";
    private static final  String DISTRITO ="distrito";
    private static final  String JUZGADO ="juzgado";
    private static final  String EXPEDIENTE ="expediente";
    private static final  String UBICACION ="ubicacion";
    private static final  String FECHA ="fecha";
    private static final  String TABLE_SIS="portafolio";

    private static final  String CREATE_TABLE_PERSON="create table "+ TABLE_SIS +" ( "+
            ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            DISTRITO +" TEXT, "+
            JUZGADO +" TEXT, "+
            EXPEDIENTE +" TEXT, "+
            UBICACION +" TEXT, "+
            FECHA +" TEXT )";




    public DB(Context context) {
        super(context, NAME_DATABASE,null,VERSION_DATABASE );
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PERSON);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXIST "+ TABLE_SIS);
        onCreate(db);
    }

    public void closeDB(){
        SQLiteDatabase db = this.getReadableDatabase();
        if (db !=null && db.isOpen())
            db.close();
    }

    public void Agregar_portafolio(Data_Portafolio porta){
        try{
            SQLiteDatabase db =this.getWritableDatabase();

        ContentValues values = new ContentValues();


        values.put(DISTRITO,porta.getDistrito());
        values.put(JUZGADO,porta.getJuzgado());
        values.put(EXPEDIENTE,porta.getExpediente());
        values.put(UBICACION,porta.getUbicacion());
        values.put(FECHA,porta.getFecha());

        long id= db.insert(TABLE_SIS,null,values);
        db.close();
        Log.d("sql","New user inserted into sqlite: "+id);
    }catch (Exception e){
            closeDB();
        }

    }


    public void borrar(String exp){
        try {
            SQLiteDatabase db =this.getWritableDatabase();
            db.delete(TABLE_SIS,"expediente='"+exp+"'",null);
            closeDB();
        }catch (Exception e) {
            closeDB();
        }
    }

}
