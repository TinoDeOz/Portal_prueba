package com.curso.tinodeoz.portal_prueba;

import android.content.ContentValues;
import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DB extends SQLiteOpenHelper {


    private static final  String NAME_DATABASE="DB";
    private static final int VERSION_DATABASE=1;
    private static final  String ID="id";
    private static final  String DISTRITO ="distrito";
    private static final  String IDJUZGADO ="idjuzgado";
    private static final  String JUZGADO ="juzgado";
    private static final  String EXPEDIENTE ="expediente";
    private static final  String UBICACION ="ubicacion";
    private static final  String FECHA ="fecha";
    private static final  String TABLE_SIS="portafolio";

    private static final  String CREATE_TABLE_PERSON="create table "+ TABLE_SIS +" ( "+
            ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            DISTRITO +" TEXT, "+
            JUZGADO +" TEXT, "+
            IDJUZGADO +" TEXT, "+
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
            values.put(IDJUZGADO,porta.getIDJuzgado());
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

    public void Agregar (String Dis,String Juz,String Id,String Exp,String Ubi,String Fec ){
        try{
            SQLiteDatabase db =this.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put(DISTRITO,Dis);
            values.put(JUZGADO,Juz);
            values.put(IDJUZGADO,Id);
            values.put(EXPEDIENTE,Exp);
            values.put(UBICACION,Ubi);
            values.put(FECHA,Fec);

            long id= db.insert(TABLE_SIS,null,values);
            db.close();
            Log.d("sql","New user inserted into sqlite: "+id);
        }catch (Exception e){
            closeDB();
        }

    }


    public void borrar(String exp,String juz){
        try {
            SQLiteDatabase db =this.getWritableDatabase();
            db.delete(TABLE_SIS,"expediente='"+exp+"' and juzgado='"+juz+"'",null);
            closeDB();
        }catch (Exception e) {

            closeDB();
        }
    }

    public void borrar2(String exp){
        try {
            SQLiteDatabase db =this.getWritableDatabase();
            db.delete(TABLE_SIS,"expediente='"+exp+"'",null);
            closeDB();
        }catch (Exception e) {
            closeDB();
        }
    }

}
