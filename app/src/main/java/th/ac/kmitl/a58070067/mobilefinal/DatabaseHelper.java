package th.ac.kmitl.a58070067.mobilefinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    //DATABASE NAME
    public static final String DATABASE_NAME = "database.db";

    //DATABASE VERSION
    public static final int DATABASE_VERSION = 1;

    //TABLE NAME
    public static final String TABLE_USERS = "users";

    //TABLE USERS COLUMNS
    //ID COLUMN @primaryKey
    public static final String KEY_ID = "id";

    //COLUMN user name
    public static final String KEY_USER_NAME = "username";
    //COLUMN user name
    public static final String KEY_NAME = "name";

    //COLUMN email
    public static final String KEY_AGE = "age";

    //COLUMN password
    public static final String KEY_PASSWORD = "password";

    //SQL for creating users table
    public static final String SQL_TABLE_USERS = " CREATE TABLE " + TABLE_USERS
            + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_USER_NAME + " TEXT, "
            + KEY_NAME + " TEXT, "
            + KEY_AGE + " INTEGER, "
            + KEY_PASSWORD + " TEXT"
            + " ) ";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Create Table when oncreate gets called
        sqLiteDatabase.execSQL(SQL_TABLE_USERS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //drop table to create new one if database version updated
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_USERS);
    }

    //using this method we can add users to user table
    public void addUser(User user) {

        //get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        //create content values to insert
        ContentValues values = new ContentValues();

        //Put username in  @values
        values.put(KEY_USER_NAME, user.getUser_id());

        //Put username in  @values
        values.put(KEY_NAME, user.getName());

        //Put age in  @values
        values.put(KEY_AGE, user.getAge());

        //Put password in  @values
        values.put(KEY_PASSWORD, user.getPassword());

        // insert row
        long todo_id = db.insert(TABLE_USERS, null, values);
    }

    public User Authenticate(User user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,// Selecting Table
                new String[]{KEY_ID, KEY_USER_NAME, KEY_NAME,KEY_AGE, KEY_PASSWORD},//Selecting columns want to query
                KEY_USER_NAME + "=?",
                new String[]{user.getUser_id()},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            //if cursor has value then in user database there is user associated with this given email
            User user1 = new User(cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4));

            //Match both passwords check they are same or not
            if (user.getPassword().equalsIgnoreCase(user1.getPassword())) {
                return user1;
            }
        }

        //if user password does not matches or there is no record with that email then return @false
        return null;
    }

    public boolean isEmailExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,// Selecting Table
                new String[]{KEY_ID, KEY_USER_NAME, KEY_NAME,KEY_AGE, KEY_PASSWORD},//Selecting columns want to query
                KEY_USER_NAME + "=?",
                new String[]{username},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            //if cursor has value then in user database there is user associated with this given email so return true
            return true;
        }

        //if email does not exist return false
        return false;
    }

    public void updateUser(User user,String user_id) {

        SQLiteDatabase db  = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("username", user.getUser_id());
        values.put("name", user.getName());
        values.put("age", user.getAge());
        values.put("password", user.getPassword());

        db.update("users", values, "username=\""+user_id+"\" AND name=\""+user.getName()+"\" AND age=\""+user.getAge()+"\" AND password=\""+user.getPassword()+"\"", null);

        db.close();
    }


}