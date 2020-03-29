package nl.avans.cinetopia.data_access;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Tag for logging
    private static final String TAG = DatabaseHelper.class.getSimpleName();

    // Database name
    public static final String DATABASE_NAME = "cinetopia.db";

    // Movie table
    public static final String TABLE_MOVIE = "table_movie";
    public static final String COL_ID = "id";
    public static final String COL_TITLE = "title";
    public static final String COL_OVERVIEW = "overview";
    public static final String COL_IMAGE_URL = "image_url";
    public static final String COL_RELEASE_DATE = "release_date";
    public static final String COL_RUNTIME = "runtime";
    public static final String COL_RATING = "rating";

    // Genre table
    public static final String TABLE_GENRE = "table_genre";

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
