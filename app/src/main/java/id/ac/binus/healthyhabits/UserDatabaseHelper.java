package id.ac.binus.healthyhabits;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class UserDatabaseHelper extends SQLiteOpenHelper {

    private static final String TABLE_USER = "user";
    private static final String TABLE_PLAN = "plan";

    public UserDatabaseHelper(@Nullable Context context) {
        super(context, "HealhyHabits", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryUser = String.format("CREATE TABLE IF NOT EXISTS %s (user_id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT UNIQUE, email TEXT UNIQUE, password TEXT, userImage INTEGER)", TABLE_USER);
        String queryPlan = String.format("CREATE TABLE IF NOT EXISTS %s (plan_id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER, plan_desc TEXT, plan_date TEXT, FOREIGN KEY(user_id) REFERENCES %s(user_id))", TABLE_PLAN, TABLE_USER);
        db.execSQL(queryUser);
        db.execSQL(queryPlan);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String queryUser ="DROP TABLE IF EXISTS " + TABLE_USER;
        String queryPlan ="DROP TABLE IF EXISTS " + TABLE_PLAN;
        db.execSQL(queryUser);
        db.execSQL(queryPlan);
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

        long status = db.insert(TABLE_USER, null, values);
        db.close();
        return status != -1;
    }

    public boolean login(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USER + " WHERE username=? AND password=?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        boolean isLoggedIn = cursor != null && cursor.getCount() > 0;
        if (cursor != null) cursor.close();
        return isLoggedIn;
    }

    public boolean editProfilePicture(String username, int newUserImage){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userImage", newUserImage);

        int rowsUpdated = db.update(TABLE_USER, values, "username=?", new String[]{username});
        db.close();
        return rowsUpdated > 0;
    }

    public boolean isUsernameUnique(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USER + " WHERE username=?";
        Cursor cursor = db.rawQuery(query, new String[]{username});

        boolean isUnique = cursor.getCount() == 0;
        cursor.close();
        db.close();
        return isUnique;
    }

    public boolean isEmailUnique(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USER + " WHERE email=?";
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
            String query = "SELECT email FROM " + TABLE_USER + " WHERE username = ?";
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
        int userImage = R.drawable.default_profpic;
        Cursor cursor = null;

        try {
            String query = "SELECT userImage FROM " + TABLE_USER + " WHERE username = ?";
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
            String query = "SELECT * FROM " + TABLE_USER; // Replace with your table name
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

    public int getUserIdByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        int userId = -1;

        Cursor cursor = db.rawQuery("SELECT user_id FROM user WHERE username = ?", new String[]{username});
        if (cursor != null && cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"));
        }

        if (cursor != null) cursor.close();
        db.close();
        return userId;
    }

    public boolean insertPlan(int userId, String date, String planDesc){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("plan_date", date);
        values.put("plan_desc", planDesc);

        long status = db.insert(TABLE_PLAN, null, values);
        db.close();
        if(status == -1)
            return false;
        else
            return true;
    }

    public boolean deletePlanById(int planId){
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("plan", "plan_id = ?", new String[]{String.valueOf(planId)});
        db.close();
        return result > 0;
    }

    public Cursor getPlanByDate(int userId, String date){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_PLAN + " WHERE user_id = ? AND plan_date = ? ORDER BY user_id ASC";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId), date});
        return cursor;
    }


    public Cursor getDistinctDatesWithPlans(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT DISTINCT date FROM plan WHERE user_id = " + userId;
        Cursor cursor = db.rawQuery(query, null);
        return  cursor;
    }



}
