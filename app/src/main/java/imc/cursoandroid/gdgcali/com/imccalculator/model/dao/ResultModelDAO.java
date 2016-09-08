package imc.cursoandroid.gdgcali.com.imccalculator.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import imc.cursoandroid.gdgcali.com.imccalculator.access.IagreeDBHandler;
import imc.cursoandroid.gdgcali.com.imccalculator.model.ResultModel;

/**
 * Created by joseberna on 8/09/16.
 */
public class ResultModelDAO {
    private Context context;
    SQLiteDatabase db = null;


    public ResultModelDAO(Context context) {
        this.context = context;
    }

    public boolean saveRecord(ResultModel resultModel) throws Exception {
        IagreeDBHandler handler = IagreeDBHandler.getIagreeDBHandler(context);
        db = handler.getWritableDatabase();
        Cursor cursor = null;
        long returnValue = 0;
        String[] projection = null;
        String selection = "ID = ?";
        String[] selectionArgs = {"" + resultModel.getId()};
        cursor = db.query("iagree_imc", projection, selection, selectionArgs, null, null, null);

        ContentValues values = new ContentValues();
        values.put("id", resultModel.getId());
        values.put("peso", resultModel.getPeso());
        values.put("altura", resultModel.getAltura());
        values.put("imc", resultModel.getImc());

        if (cursor != null && cursor.moveToNext()) {
            returnValue = db.update("iagree_imc", values, selection, selectionArgs);
            Toast.makeText(context, "Update", Toast.LENGTH_LONG).show();
        } else {
            returnValue = db.insert("iagree_imc", null, values);
            Toast.makeText(context, "Insert", Toast.LENGTH_LONG).show();

        }

        db.close();
        return true;
    }

    public Cursor getAllResultModels() throws Exception {
        Cursor cursor = null;
        IagreeDBHandler handler = IagreeDBHandler.getIagreeDBHandler(context);
        cursor = handler.getReadableDatabase().rawQuery("Select id ,peso, altura, imc from iagree_imc", null);
        db.close();
        return cursor;
    }


}
