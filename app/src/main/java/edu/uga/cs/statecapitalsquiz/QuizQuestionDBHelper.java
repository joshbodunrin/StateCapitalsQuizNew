package edu.uga.cs.statecapitalsquiz;

import android.content.Context;
import android.util.Log;

import static edu.uga.cs.statecapitalsquiz.QuestionDBHelpers.QUESTIONS_COLUMN_ID;
import static edu.uga.cs.statecapitalsquiz.QuestionDBHelpers.TABLE_QUESTIONS;
import static edu.uga.cs.statecapitalsquiz.QuizDBHelper.QUIZ_COLUMN_ID;
import static edu.uga.cs.statecapitalsquiz.QuizDBHelper.TABLE_QUIZ;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class QuizQuestionDBHelper extends SQLiteOpenHelper {

    private static final String DEBUG_TAG = "QuizQuestionDBHelper";

    private static final String DB_NAME = "quizquestion.db";

    private static final int DB_VERSION = 1;

    public static final String TABLE_QUIZQUESTIONS = "quizquestions";

    public static final String QUIZQUESTION_COLUMN_ID = "_id";

    public static final String QUIZQUESTION_COLUMN_ANSWER = "answer";

    public static final String QUIZQUESTION_COLUMN_QUESTIONID = "questionid";

    public static final String QUIZQUESTION_COLUMN_QUIZID = "quizid";


    private static QuizQuestionDBHelper helperInstance;


    /*private static final String CREATE_QUIZQUESTIONS = "" +
            "create table " + TABLE_QUIZQUESTIONS + "("
            + QUIZQUESTION_COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, "
            + QUIZQUESTION_COLUMN_ANSWER + "TEXT,"
            + QUIZQUESTION_COLUMN_QUIZID + "INTEGER, "
            + " FOREIGN KEY ("+QUIZQUESTION_COLUMN_QUIZID+") REFERENCES "+TABLE_QUIZ+"("+QUIZ_COLUMN_ID+"
            + QUIZQUESTION_COLUMN_QUESTIONID + "INTEGER, "
            + " FOREIGN KEY ("+QUIZQUESTION_COLUMN_QUESTIONID+") REFERENCES "+TABLE_QUESTIONS+"("+QUESTIONS_COLUMN_ID
            +")";
    */
    private static final String CREATE_QUIZQUESTIONS =
            "CREATE TABLE " + TABLE_QUIZQUESTIONS + " (" +
                    QUIZQUESTION_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    QUIZQUESTION_COLUMN_ANSWER + " TEXT, " +
                    QUIZQUESTION_COLUMN_QUIZID + " INTEGER, " +
                    QUIZQUESTION_COLUMN_QUESTIONID + " INTEGER, " +
                    "FOREIGN KEY (" + QUIZQUESTION_COLUMN_QUIZID + ") REFERENCES " + TABLE_QUIZ + "(" + QUIZ_COLUMN_ID + "), " +
                    "FOREIGN KEY (" + QUIZQUESTION_COLUMN_QUESTIONID + ") REFERENCES " + TABLE_QUESTIONS + "(" + QUESTIONS_COLUMN_ID + ")" +
                    ")";
    private QuizQuestionDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static synchronized QuizQuestionDBHelper getInstance(Context context) {
        if (helperInstance == null) {
            helperInstance = new QuizQuestionDBHelper(context.getApplicationContext());

        }
        return helperInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUIZQUESTIONS);
        Log.d(DEBUG_TAG, "Table " + TABLE_QUIZQUESTIONS + " created" );
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_QUIZQUESTIONS);
        onCreate(db);
        Log.d(DEBUG_TAG, "Table " + TABLE_QUIZQUESTIONS + "upgraded");
    }



}
