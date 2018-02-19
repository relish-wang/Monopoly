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

    private static final String CREATE_USER = "create table user(" +
            "id integer primary key," +
            "name text," +
            "money double);";
    private static final String CREATE_RECORD = "create table record(" +
            "id integer primary key," +
            "createTime timestamp," +
            "payerId integer," +
            "payeeId integer," +
            "money double);";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_RECORD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS record");
        onCreate(db);
    }
}
