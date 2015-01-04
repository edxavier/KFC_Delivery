package ni.maestria.m8.kfcdelivery.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Eder Xavier Rojas on 26/12/2014.
 */
public class DBOpenHelper extends SQLiteOpenHelper {
    static  String DB_NAME = "kfc";
    static int DB_VERSION = 5;
    static String TABLE_RESTAURANTS = "restaurants";
    static String TABLE_COMMENTS = "comments";
    static String TABLE_COMBOS = "combos";


    public DBOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table temp_detalle ( _id int primary key, menu text, precio float, cantidad int, total float)";
        db.execSQL(sql);

        sql = "create table "+OperationsUser.TABLE+" ( _id int primary key, social_id text, avatar text, nombre text, telefono text)";
        db.execSQL(sql);

        sql = "create table "+TABLE_RESTAURANTS+" ( _id int primary key, nombre text, direccion text, coordenadas text, telefono text, distancia float)";
        db.execSQL(sql);

        sql = "create table "+TABLE_COMMENTS+" ( _id int primary key, comentario text, cliente text, avatar text)";
        db.execSQL(sql);

        sql = "create table "+TABLE_COMBOS+" ( _id int primary key, nombre text, descripcion text, precio text, imgUrl text)";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS temp_detalle");
        db.execSQL("DROP TABLE IF EXISTS "+OperationsUser.TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_RESTAURANTS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_COMMENTS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_COMBOS);
        onCreate(db);
    }
}
