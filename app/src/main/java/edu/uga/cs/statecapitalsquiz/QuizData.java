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

    public boolean isDBOpen() {
        return db.isOpen();
    }

    public void close() {
        if (quizDBHelper != null) {
            quizDBHelper.close();
            Log.d(DEBUG_TAG, "QuizData: db closed");
        }
    }

    public List<Quiz> retrieveAllQuiz() {
        ArrayList<Quiz> quizList = new ArrayList<>();
        Cursor cursor = null;
        int colIndex;

        try {
            cursor = db.query(QuizDBHelper.TABLE_QUIZ, allColumns,
                    null, null, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    if (cursor.getColumnCount() >= 3) {
                        colIndex = cursor.getColumnIndex(QuizDBHelper.QUIZ_COLUMN_ID);
                        long id = cursor.getLong(colIndex);
                        colIndex = cursor.getColumnIndex(QuizDBHelper.QUIZ_COLUMN_QUIZDATE);
                        String date = cursor.getString(colIndex);
                        colIndex = cursor.getColumnIndex(QuizDBHelper.QUIZ_COLUMN_RESULT);
                        String result = cursor.getString(colIndex);

                        // create new Quiz obj
                        Quiz quiz = new Quiz(date,result);
                        quiz.setId(id);

                        quizList.add(quiz);
                        Log.d(DEBUG_TAG, "Retrieved Quiz: " + quiz);
                    }
                }
            }
            if (cursor != null)
                Log.d(DEBUG_TAG, "Number of records from DB:  " + cursor.getCount());
            else
                Log.d(DEBUG_TAG, "Number of records from DB: 0");
        } catch (Exception e) {
            Log.d(DEBUG_TAG, "Exception caught:  " + e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return quizList;
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
