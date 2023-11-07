package edu.uga.cs.statecapitalsquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class QuizDBHelper extends SQLiteOpenHelper {

    private static final String DEBUG_TAG = "QuizDBHelper";

    private static final String DB_NAME = "quiz.db";

    private static final int DB_VERSION = 1;

    public static final String TABLE_QUIZ = "quiz";

    public static final String QUIZ_COLUMN_ID = "_id";

    public static final String QUIZ_COLUMN_QUIZDATE = "date";

    public static final String QUIZ_COLUMN_RESULT = "result";

    private static QuizDBHelper helperInstance;

    private static final String CREATE_QUIZ =
            "create table " + TABLE_QUIZ + " ( "
            + QUIZ_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + QUIZ_COLUMN_QUIZDATE + " TEXT , "
            + QUIZ_COLUMN_RESULT + " TEXT"
            + ")";

    private QuizDBHelper( Context context ) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // Accessing the single instance, synchronized so only one thread can execute
    public static synchronized QuizDBHelper getInstance(Context context) {
        if (helperInstance == null) {
            helperInstance = new QuizDBHelper(context.getApplicationContext());
        }
        return helperInstance;
    }

    @Override
    // creates db if not created yet using tags from above
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUIZ);
        Log.d(DEBUG_TAG, "Table" + TABLE_QUIZ + " created");
    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL ("drop table if exists " + TABLE_QUIZ);
        onCreate( db);
        Log.d (DEBUG_TAG, "Table " + TABLE_QUIZ + "upgraded");
    }
}
