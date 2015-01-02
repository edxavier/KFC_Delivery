package ni.maestria.m8.kfcdelivery.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ni.maestria.m8.kfcdelivery.models.Cliente;

/**
 * Created by Eder Xavier Rojas on 01/01/2015.
 */
public class OperationsUser {
    DBOpenHelper dbOpenHelper = null;
    Context context = null;
    private SQLiteDatabase db = null;

    static String TABLE = "usuario";

    public OperationsUser(Context context) {
        this.context = context;
        if(dbOpenHelper==null)
            dbOpenHelper = new DBOpenHelper(context);
    }


    public long insert(Cliente cliente)
    {
        db = dbOpenHelper .getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("social_id",cliente.getSocialId());
        cv.put("avatar",cliente.getImgUrl());
        cv.put("nombre",cliente.getNombre());
        cv.put("telefono",cliente.getTelefono());
        return db.insert(OperationsUser.TABLE, null, cv);
    }

    public int delete()
    {
        db = dbOpenHelper.getWritableDatabase();
        return db.delete(OperationsUser.TABLE,null, null);
    }

    public Cliente getUser()
    {
        db = dbOpenHelper.getWritableDatabase();
        String [] cols = new  String [] {"_id", "social_id", "avatar", "nombre", "telefono"};
        //Cursor cursor = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy)
        Cursor cursor = db.query(TABLE, cols,null, null, null, null, null);
        Cliente cliente = null;
        if(cursor.moveToFirst())
        {
            cliente = new Cliente();
            cliente.setSocialId(cursor.getString(cursor.getColumnIndex("social_id")));
            cliente.setImgUrl(cursor.getString(cursor.getColumnIndex("avatar")));
            cliente.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
            cliente.setTelefono(cursor.getString(cursor.getColumnIndex("telefono")));

        }
        return cliente;
    }


}
