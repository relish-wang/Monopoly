package wang.relish.monopoly.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import wang.relish.monopoly.App;

/**
 * @author Relish Wang
 * @since 2018/02/19
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "monopoly";
    private static final int VERSION = 1;

    public DBHelper() {
        super(App.CONTEXT, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO 先删再建
    }
}
