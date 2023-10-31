package edu.uga.cs.statecapitalsquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;
import java.util.ArrayList;


public class QuizData {

    public static final String DEBUG_TAG = "QuizData";

    private SQLiteDatabase db;

    private SQLiteOpenHelper quizDBHelper;

    private static final String[] allColumns = {
            QuizDBHelper.QUIZ_COLUMN_RESULT,
            QuizDBHelper.QUIZ_COLUMN_ID,
            QuizDBHelper.QUIZ_COLUMN_QUIZDATE
    };

    public QuizData(Context context) {
        this.quizDBHelper = QuizDBHelper.getInstance(context);
    }

    public void open() {
        db = quizDBHelper.getWritableDatabase();
        Log.d(DEBUG_TAG, "QuizData: db open");
    }

    public void close() {
        if (quizDBHelper != null) {
            quizDBHelper.close();
            Log.d(DEBUG_TAG, "QuizData: db closed");
        }
    }

    public List<Quiz> retrieveAllQuiz() {
        return null;
    }
    public Quiz storeQuiz(Quiz quiz) {

        ContentValues values = new ContentValues();
        values.put(QuizDBHelper.QUIZ_COLUMN_RESULT, quiz.getResult());
        values.put(QuizDBHelper.QUIZ_COLUMN_QUIZDATE, quiz.getDate());

        long id = db.insert( QuizDBHelper.TABLE_QUIZ, null, values);
        quiz.setId(id);

        Log.d( DEBUG_TAG,"Stored new quiz with id: " + String.valueOf(quiz.getId()));
        return quiz;

    }
}
