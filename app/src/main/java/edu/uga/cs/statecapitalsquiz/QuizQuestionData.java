package edu.uga.cs.statecapitalsquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class QuizQuestionData {

    public static final String DEBUG_TAG = "QuizQuestionData";

    private SQLiteDatabase db;

    private SQLiteOpenHelper quizQuestionDBHelper;

    private static final String[] allColumns = {
            QuizQuestionDBHelper.QUIZQUESTION_COLUMN_ID,
            QuizQuestionDBHelper.QUIZQUESTION_COLUMN_ANSWER,
            QuizQuestionDBHelper.QUIZQUESTION_COLUMN_QUIZID,
            QuizQuestionDBHelper.QUIZQUESTION_COLUMN_QUESTIONID
    };

    public QuizQuestionData(Context context) {
        this.quizQuestionDBHelper = QuestionDBHelpers.getInstance(context);
    }

    public void open() {
        db = quizQuestionDBHelper.getWritableDatabase();
        Log.d(DEBUG_TAG, "QuizQuestionData: db open");
    }

    public void close() {
        if (quizQuestionDBHelper != null) {
            quizQuestionDBHelper.close();
            Log.d(DEBUG_TAG, "QuizQuestionData: db closed");
        }
    }

    public boolean isDBopen() {return db.isOpen();}

    public QuizQuestion storeQuizQuestion(QuizQuestion quizQuestion) {

        ContentValues values = new ContentValues();
        values.put(QuizQuestionDBHelper.QUIZQUESTION_COLUMN_ANSWER, quizQuestion.getAnswer());
        values.put(QuizQuestionDBHelper.QUIZQUESTION_COLUMN_QUESTIONID,quizQuestion.getQuestion().getId());
        values.put(QuizQuestionDBHelper.QUIZQUESTION_COLUMN_QUIZID, quizQuestion.getQuiz().getId());

        long id = db.insert(QuizQuestionDBHelper.TABLE_QUIZQUESTIONS, null, values);
        quizQuestion.setId(id);
        Log.d(DEBUG_TAG, "Stored new quiz question with id: " + String.valueOf(quizQuestion.getId()));

        return quizQuestion;

    }
}
