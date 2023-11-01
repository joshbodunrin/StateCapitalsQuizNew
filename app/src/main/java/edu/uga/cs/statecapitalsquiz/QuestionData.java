package edu.uga.cs.statecapitalsquiz;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class QuestionData {
    public static final String DEBUG_TAG = "QuestionData";

    private SQLiteDatabase db;

    private SQLiteOpenHelper questionDBHelpers;

    private static final String[] allColumns = {
            edu.uga.cs.statecapitalsquiz.QuestionDBHelpers.QUESTIONS_COLUMN_ID,
            edu.uga.cs.statecapitalsquiz.QuestionDBHelpers.QUESTIONS_COLUMN_CITYTWO,
            edu.uga.cs.statecapitalsquiz.QuestionDBHelpers.QUESTIONS_COLUMN_CITYONE,
            edu.uga.cs.statecapitalsquiz.QuestionDBHelpers.QUESTIONS_COLUMN_CAPITAL,
            edu.uga.cs.statecapitalsquiz.QuestionDBHelpers.QUESTIONS_COLUMN_STATE
    };
    public QuestionData(Context context) {
        this.questionDBHelpers = edu.uga.cs.statecapitalsquiz.QuestionDBHelpers.getInstance(context);

    }

    public void open() {
        db = questionDBHelpers.getWritableDatabase();
        Log.d(DEBUG_TAG, "QuestionData: db open");
    }

    public void close() {
        if (questionDBHelpers != null) {
            questionDBHelpers.close();
        }
        Log.d(DEBUG_TAG, "QuestionData: db closed");
    }

    public boolean isDBOpen() {return db.isOpen();}

    public List<Question> retrieveAllQuestions() {
        ArrayList<Question> questions = new ArrayList<>();
        Cursor cursor = null;
        int columnIndex;

        try {
            // will implement later
            cursor = db.query(QuestionDBHelpers.TABLE_QUESTIONS,allColumns,
                    null, null, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    if (cursor.getColumnCount() >= 5) {
                        columnIndex = cursor.getColumnIndex(QuestionDBHelpers.QUESTIONS_COLUMN_ID);
                        long id = cursor.getLong(columnIndex);
                        columnIndex = cursor.getColumnIndex(QuestionDBHelpers.QUESTIONS_COLUMN_CITYONE);
                        String cityOne = cursor.getString(columnIndex);
                        columnIndex = cursor.getColumnIndex(QuestionDBHelpers.QUESTIONS_COLUMN_CITYTWO);
                        String cityTwo = cursor.getString(columnIndex);
                        columnIndex = cursor.getColumnIndex(QuestionDBHelpers.QUESTIONS_COLUMN_CAPITAL);
                        String capital = cursor.getString(columnIndex);
                        columnIndex = cursor.getColumnIndex(QuestionDBHelpers.QUESTIONS_COLUMN_STATE);
                        String state = cursor.getString(columnIndex);

                        Question question = new Question(state,capital,cityOne,cityTwo);
                        question.setId(id);

                        questions.add(question);
                        Log.d(DEBUG_TAG, "Retrieved question: " + question);
                    }
                }
            }
            //if (cursor != null)
                //Log.d(DEBUG_TAG, "Number of records")
        } catch (Exception e) {
            Log.d(DEBUG_TAG, "Excemption caught:  " + e);
        } finally {
             if (cursor != null) {
                 cursor.close();
             }
        }
        return questions;
    }

    public Question storeQuestion(Question question) {

        ContentValues values = new ContentValues();
        values.put(QuestionDBHelpers.QUESTIONS_COLUMN_STATE, question.getState());
        values.put(QuestionDBHelpers.QUESTIONS_COLUMN_CAPITAL, question.getCapital());
        values.put(QuestionDBHelpers.QUESTIONS_COLUMN_CITYONE, question.getCityOne());
        values.put(QuestionDBHelpers.QUESTIONS_COLUMN_CITYTWO, question.getCityTwo());

        long id = db.insert(QuestionDBHelpers.TABLE_QUESTIONS,null, values);
        question.setId(id);

        Log.d(DEBUG_TAG, "Stored new question with id: " + String.valueOf(question.getId()));

        return question;
    }
}
