package ni.maestria.m8.kfcdelivery.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Eder Xavier Rojas on 26/12/2014.
 */
public class DBOpenHelper extends SQLiteOpenHelper {
    static  String DB_NAME = "kfc";
    static int DB_VERSION = 2;


    public DBOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table temp_detalle ( _id int primary key, menu text, precio float, cantidad int, total float)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS temp_detalle");
        onCreate(db);
    }
}
