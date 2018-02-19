package wang.relish.monopoly.entity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import wang.relish.monopoly.db.DBHelper;
import wang.relish.monopoly.util.ToastUtil;

/**
 * @author Relish Wang
 * @since 2018/02/19
 */
public class User implements Serializable {

    private long id;
    private String name;
    private double money;

    public static List<User> getUsers() {
        DBHelper helper = new DBHelper();
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = null;
        List<User> users = new ArrayList<>();
        try {
            cursor = db.rawQuery("select id,name,money from user", null);
            User user = null;
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    user = new User();
                    user.setId(cursor.getLong(0));
                    user.setName(cursor.getString(1));
                    user.setMoney(cursor.getDouble(2));
                    users.add(user);
                } while (cursor.moveToNext());
            }
            return users;
        } catch (Exception e) {
            return users;
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    /**
     * 获取用户的最大ID
     */
    public static long getMaxId() {
        DBHelper helper = new DBHelper();
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("select max(id) from user", null);
            if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
                return cursor.getLong(0);
            } else {
                return 0;
            }
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    public static boolean isExist(String name) {
        DBHelper helper = new DBHelper();
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("select * from user where name = ?", new String[]{name});
            return cursor != null && cursor.getCount() > 0;
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    public static boolean createUser(String name) {
        if (TextUtils.isEmpty(name)) {
            ToastUtil.show("名字不得为空");
            return false;
        }
        if (isExist(name)) {
            ToastUtil.show("用户已存在");
            return false;
        }
        DBHelper helper = new DBHelper();
        SQLiteDatabase db = helper.getWritableDatabase();
        long maxId = getMaxId() + 1;
        db.execSQL("insert into user(id,name,money) values(?,?,?)", new String[]{maxId + "", name, "0"});
        return isExist(name);
    }
}
