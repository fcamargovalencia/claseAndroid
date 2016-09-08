package imc.cursoandroid.gdgcali.com.imccalculator.access;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by joseberna on 8/09/16.
 */
public class IagreeDBHandler extends SQLiteOpenHelper {


    public static final String PREFERENCES_DATABASEHANDLER = "PREFERENCES_DATABASEHANDLER";
    public static final String DATABASE_DOWNLOADED = "databaseDownloaded";
    // All Static variables Database Version
    private static final String DATABASE_VERSION = "user_version";
    private static final int DATABASE_DEFAULT_VERSION = 1;
    public static final String DATABASE_NAME = "IAGREE.DB";
    private static IagreeDBHandler instance;


    public static synchronized IagreeDBHandler getIagreeDBHandler(Context context) {
        SharedPreferences settings = context.getSharedPreferences(
                PREFERENCES_DATABASEHANDLER, Context.MODE_PRIVATE);

        int version = settings.getInt(DATABASE_VERSION,
                DATABASE_DEFAULT_VERSION);

        if (instance == null)
            instance = new IagreeDBHandler(context, version);


        return instance;
    }

    public static final void closeConnection() {
        if (instance != null) {
            instance.close();
            instance = null;
        }
    }


    public IagreeDBHandler(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS iagree_imc "
                    + "("
                    + "   id               		INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                    + "   peso               	VARCHAR(200) NULL DEFAULT NULL ,"
                    + "   altura               	VARCHAR(200) NULL DEFAULT NULL ,"
                    + "   imc               	VARCHAR(200) NULL DEFAULT NULL "
                    + ");");
            db.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
