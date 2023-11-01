package edu.uga.cs.statecapitalsquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;

import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private QuestionData questionData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    protected void onStart() {
        super.onStart();
        try {
            questionData = new QuestionData( this.getApplicationContext());
            if (questionData != null)
                questionData.open();
            InputStream in_s = getAssets().open("StateCapitals.csv");

            CSVReader reader = new CSVReader(new InputStreamReader(in_s));
            String[] nextRow;
            while ((nextRow = reader.readNext()) != null) {

                String state = nextRow[0];
                String capital = nextRow[1];
                String cityOne = nextRow[2];
                String cityTwo = nextRow[3];
                Question question = new Question(state, capital, cityOne, cityTwo);
                new QuestionDBwriter().execute(question);

            }
        } catch (Exception e) {
            Log.e("testing", e.toString());
        }
        questionData.close();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (questionData != null)
            questionData.open();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (questionData != null)
            questionData.close();

    }


    public class QuestionDBwriter extends AsyncTask<Question, Question> {

        @Override
        protected Question doInBackground(Question... questions) {
            questionData.storeQuestion(questions[0]);
            return questions[0];
        }

        @Override
        protected void onPostExecute(Question question) {
            //dont think we need anything for this occasion
        }
    }
}