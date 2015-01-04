package ni.maestria.m8.kfcdelivery.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import ni.maestria.m8.kfcdelivery.models.Comment;
import ni.maestria.m8.kfcdelivery.models.MenuCombos;
import ni.maestria.m8.kfcdelivery.models.Sucursal;

/**
 * Created by Eder Xavier Rojas on 03/01/2015.
 */
public class OperationsCommentRestaurants {

    DBOpenHelper dbOpenHelper = null;
    Context context = null;
    private SQLiteDatabase db = null;

    public OperationsCommentRestaurants(Context context) {
        this.context = context;
        if(dbOpenHelper==null)
            dbOpenHelper = new DBOpenHelper(context);
    }

    public long insert(Sucursal sucursal)
    {
        db = dbOpenHelper .getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("_id",sucursal.getId());
        cv.put("nombre",sucursal.getNombre());
        cv.put("direccion",sucursal.getDireccion());
        cv.put("coordenadas",sucursal.getCoordenadas());
        cv.put("telefono",sucursal.getTelefono());
        cv.put("distancia",sucursal.getDistancia());
        return db.insertWithOnConflict(DBOpenHelper.TABLE_RESTAURANTS,null, cv,SQLiteDatabase.CONFLICT_IGNORE);
        //return db.insert(DBOpenHelper.TABLE_RESTAURANTS, null, cv);
    }

    public long insert(Comment comment)
    {
        db = dbOpenHelper .getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("comentario",comment.getComment());
        cv.put("cliente",comment.getClient());
        cv.put("avatar",comment.getImgUrl());
        return db.insertWithOnConflict(DBOpenHelper.TABLE_COMMENTS,null, cv,SQLiteDatabase.CONFLICT_IGNORE);
        //return db.insert(DBOpenHelper.TABLE_RESTAURANTS, null, cv);
    }

    public long insert(MenuCombos menuCombos)
    {
        db = dbOpenHelper .getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nombre",menuCombos.getNombre());
        cv.put("descripcion",menuCombos.getDescripcion());
        cv.put("precio",menuCombos.getPrecio());
        cv.put("imgUrl",menuCombos.getImgUrl());
        return db.insertWithOnConflict(DBOpenHelper.TABLE_COMBOS,null, cv,SQLiteDatabase.CONFLICT_IGNORE);
        //return db.insert(DBOpenHelper.TABLE_RESTAURANTS, null, cv);
    }

    public int deleteComments()
    {
        db = dbOpenHelper.getWritableDatabase();
        return db.delete(DBOpenHelper.TABLE_COMMENTS,null, null);
    }

    public int deleteCombos()
    {
        db = dbOpenHelper.getWritableDatabase();
        return db.delete(DBOpenHelper.TABLE_COMBOS,null, null);
    }

    public int deleteSucursales()
    {
        db = dbOpenHelper.getWritableDatabase();
        return db.delete(DBOpenHelper.TABLE_RESTAURANTS,null, null);
    }

    public ArrayList<Sucursal> getSucursalesCache()
    {
        ArrayList<Sucursal> sucursals = new ArrayList<>();

        db = dbOpenHelper.getWritableDatabase();
        String [] cols = new  String [] {"_id", "nombre", "direccion","telefono", "coordenadas", "distancia"};
        Cursor cursor = db.query(DBOpenHelper.TABLE_RESTAURANTS, cols , null, null, null, null, null);

        while(cursor.moveToNext())
        {
            Sucursal sucursal = new Sucursal();
            sucursal.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
            sucursal.setDireccion(cursor.getString(cursor.getColumnIndex("direccion")));
            sucursal.setTelefono(cursor.getString(cursor.getColumnIndex("telefono")));
            sucursal.setCoordenadas(cursor.getString(cursor.getColumnIndex("coordenadas")));
            sucursal.setDistancia(cursor.getFloat(cursor.getColumnIndex("distancia")));
            sucursals.add(sucursal);
        }
        cursor.close();
        db.close();
        return sucursals;
    }

    public ArrayList<Comment> getComentariosCache()
    {
        ArrayList<Comment> comments = new ArrayList<>();

        db = dbOpenHelper.getWritableDatabase();
        String [] cols = new  String [] {"_id", "cliente", "comentario","avatar"};
        Cursor cursor = db.query(DBOpenHelper.TABLE_COMMENTS, cols , null, null, null, null, null);

        while(cursor.moveToNext())
        {
            Comment comment = new Comment();
            comment.setClient(cursor.getString(cursor.getColumnIndex("cliente")));
            comment.setComment(cursor.getString(cursor.getColumnIndex("comentario")));
            comment.setImgUrl(cursor.getString(cursor.getColumnIndex("avatar")));

            comments.add(comment);
        }
        cursor.close();
        db.close();
        return comments;
    }

    public ArrayList<MenuCombos> getCombosCache()
    {
        ArrayList<MenuCombos> menuComboses = new ArrayList<>();

        db = dbOpenHelper.getWritableDatabase();
        String [] cols = new  String [] {"_id", "nombre", "descripcion","imgUrl", "precio"};
        Cursor cursor = db.query(DBOpenHelper.TABLE_COMBOS, cols , null, null, null, null, null);

        while(cursor.moveToNext())
        {
            MenuCombos combos = new MenuCombos();
            combos.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
            combos.setDescripcion(cursor.getString(cursor.getColumnIndex("descripcion")));
            combos.setImgUrl(cursor.getString(cursor.getColumnIndex("imgUrl")));
            combos.setPrecio(cursor.getString(cursor.getColumnIndex("precio")));

            menuComboses.add(combos);
        }
        cursor.close();
        db.close();
        return menuComboses;
    }



}
