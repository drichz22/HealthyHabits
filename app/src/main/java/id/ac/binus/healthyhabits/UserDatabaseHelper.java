package id.ac.binus.healthyhabits;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class UserDatabaseHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "user";

    public UserDatabaseHelper(@Nullable Context context) {
        super(context, "HealhyHabits", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format("CREATE TABLE IF NOT EXISTS %s (user_id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT UNIQUE, email TEXT UNIQUE, password TEXT, userImage INTEGER)", TABLE_NAME);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query ="DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
        onCreate(db);
    }

    public boolean daftar(String username, String email, String password, int userImage){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        int defaultImageResource = R.drawable.default_profpic;
        values.put("username", username);
        values.put("email", email);
        values.put("password", password);
        values.put("userImage", userImage != -1 ? userImage : defaultImageResource);

        long status = db.insert(TABLE_NAME, null, values);
        db.close();
        return status != -1;
    }

    public boolean login(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE username=? AND password=?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        boolean isLoggedIn = cursor != null && cursor.getCount() > 0;
        if (cursor != null) cursor.close();
        return isLoggedIn;
    }

    public boolean isUsernameUnique(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE username=?";
        Cursor cursor = db.rawQuery(query, new String[]{username});

        boolean isUnique = cursor.getCount() == 0;
        cursor.close();
        db.close();
        return isUnique;
    }

    public boolean isEmailUnique(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE email=?";
        Cursor cursor = db.rawQuery(query, new String[]{email});

        boolean isUnique = cursor.getCount() == 0;
        cursor.close();
        db.close();
        return isUnique;
    }

    public String getEmailbyUsername(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        String email = "";
        Cursor cursor = null;

        try {
            String query = "SELECT email FROM " + TABLE_NAME + " WHERE username = ?";
            cursor = db.rawQuery(query, new String[]{username});

            if (cursor != null && cursor.moveToFirst()) {
                email = cursor.getString(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return email;
    }

    public int getImagebyUsername(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        int userImage = -1;
        Cursor cursor = null;

        try {
            String query = "SELECT userImage FROM " + TABLE_NAME + " WHERE username = ?";
            cursor = db.rawQuery(query, new String[]{username});

            if (cursor != null && cursor.moveToFirst()) {
                userImage = cursor.getInt(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return userImage;
    }

    public void getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT * FROM " + TABLE_NAME; // Replace with your table name
            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                    String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                    String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
                    int userImage = cursor.getInt(cursor.getColumnIndexOrThrow("userImage"));

                    // Log data for inspection in Logcat
                    Log.d("DBHelper", "User: " + username + ", Email: " + email +
                            ", Password: " + password + ", Image: " + userImage);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("DBHelper", "Error reading data", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

}
