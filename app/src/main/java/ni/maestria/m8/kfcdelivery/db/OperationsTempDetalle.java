package ni.maestria.m8.kfcdelivery.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import ni.maestria.m8.kfcdelivery.models.DetallePedido;

/**
 * Created by Eder Xavier Rojas on 26/12/2014.
 */
public class OperationsTempDetalle {
    DBOpenHelper dbOpenHelper = null;
    Context context = null;
    private SQLiteDatabase db = null;

    static String TABLE = "temp_detalle";

    public OperationsTempDetalle(Context context) {
        this.context = context;
        if(dbOpenHelper==null)
            dbOpenHelper = new DBOpenHelper(context);
    }

    public long insert(DetallePedido detalle)
    {
        db = dbOpenHelper .getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("menu", detalle.getMenu());
        cv.put("cantidad", detalle.getCantidad());
        cv.put("total", detalle.getSubTotal());
        return db.insert(TABLE, null, cv);
    }

    public ArrayList<DetallePedido> getTempDetallePedido()
    {
        ArrayList<DetallePedido> detalles = new ArrayList<>();

        db = dbOpenHelper.getWritableDatabase();
        String [] cols = new  String [] {"_id", "menu", "cantidad","precio", "total"};
        Cursor cursor = db.query(TABLE, cols , null, null, null, null, null);

        while(cursor.moveToNext())
        {
            DetallePedido detallePedido = new DetallePedido();
            detallePedido.setMenu(cursor.getString(cursor.getColumnIndex("menu")));
            detallePedido.setCantidad(cursor.getInt(cursor.getColumnIndex("cantidad")));
            detallePedido.setPrecio(cursor.getFloat(cursor.getColumnIndex("precio")));
            detallePedido.setSubTotal(cursor.getFloat(cursor.getColumnIndex("total")));
            detalles.add(detallePedido);
        }
        cursor.close();
        db.close();
        return detalles;
    }

    public DetallePedido getMenuTemp(String menu)
    {
        db = dbOpenHelper.getWritableDatabase();
        String [] cols = new  String [] {"_id", "menu", "cantidad", "total"};
        //Cursor cursor = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy)
        Cursor cursor = db.query(TABLE, cols, "menu='"+menu+"'", null, null, null, null);
        DetallePedido detallePedido = null;
        if(cursor.moveToFirst())
        {
            detallePedido = new DetallePedido();
            detallePedido.setMenu(cursor.getString(cursor.getColumnIndex("menu")));
            detallePedido.setCantidad(cursor.getInt(cursor.getColumnIndex("cantidad")));
            detallePedido.setSubTotal(cursor.getFloat(cursor.getColumnIndex("total")));
        }
        return detallePedido;
    }

    public int delete(String menu)
    {
        db = dbOpenHelper.getWritableDatabase();
        return db.delete(TABLE, "menu='"+menu+"'", null);
    }
    public int delete()
    {
        db = dbOpenHelper.getWritableDatabase();
        return db.delete(TABLE,null, null);
    }

    public int update(DetallePedido detalle)
    {
        db = dbOpenHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("cantidad", detalle.getCantidad());
        cv.put("total", detalle.getSubTotal());

        return db.update(TABLE, cv, "menu='"+detalle.getMenu()+"'", null);
    }


}
