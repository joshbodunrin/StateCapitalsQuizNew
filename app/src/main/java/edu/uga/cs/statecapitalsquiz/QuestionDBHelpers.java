package edu.uga.cs.statecapitalsquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class QuestionDBHelpers extends SQLiteOpenHelper {

    private static final String DEBUG_TAG = "QuestionDBHelper";

    private static final String DB_NAME = "questions.db";

    private static final int DB_VERSION = 1;

    public static final String TABLE_QUESTIONS = "questions";

    public static final String QUESTIONS_COLUMN_ID = "_id";
    public static final String QUESTIONS_COLUMN_STATE = "state";
    public static final String QUESTIONS_COLUMN_CAPITAL = "capital";
    public static final String QUESTIONS_COLUMN_CITYONE = "city_one";
    public static final String QUESTIONS_COLUMN_CITYTWO = "city_two";

    private static QuestionDBHelpers helperInstance;

    private static final String CREATE_QUESTIONS =
            "create table " + TABLE_QUESTIONS + " ( "
            + QUESTIONS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + QUESTIONS_COLUMN_STATE + "TEXT, "
            + QUESTIONS_COLUMN_CAPITAL + "TEXT, "
            + QUESTIONS_COLUMN_CITYONE + "TEXT, "
            + QUESTIONS_COLUMN_CITYTWO + "TEXT"
            + ")";


    private QuestionDBHelpers(Context context) {
        super (context, DB_NAME, null, DB_VERSION);
    }

    public static synchronized  QuestionDBHelpers getInstance(Context context) {
        if (helperInstance == null) {
            helperInstance = new QuestionDBHelpers(context.getApplicationContext());

        }
        return helperInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUESTIONS);
        Log.d( DEBUG_TAG, "Questions" +TABLE_QUESTIONS + " created");
    }


    // do not think we will need this function
    // question table should not be being upgraded we already have the questions set from csv
    // put in just in case can remove later if needed
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_QUESTIONS);
        onCreate(db);

    }

}
