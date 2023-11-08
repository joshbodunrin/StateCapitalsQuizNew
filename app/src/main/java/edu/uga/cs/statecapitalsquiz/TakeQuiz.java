package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TakeQuiz#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TakeQuiz extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String STATE = "Texas";
    private static final String CAPITAL = "capitaal";
    private static final String CITY1 = "cityyy1";
    private static final String CITY2 = "cityyyy2";
    private static final String DEBUG_TAG = "TakeQuizFragment";

    // TODO: Rename and change types of parameters

    private TextView score;
    private TextView qCounter;
    private TextView state;
    private RadioGroup answersGroup;
    private RadioButton answerA;
    private RadioButton answerB;
    private RadioButton answerC;
    private Button nextButton;
    private Question currentQuestion;
    private QuestionData questionData = null;
    private List<Question> questionList;

    private List<QuizQuestion> quizQuestions;
    //private Quiz quiz = new Quiz();

    private int questionNo = 1;
    private int correctNo = 0;


    public TakeQuiz() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param question Question object that will be asked in this instance.
     * @return A new instance of fragment TakeQuiz.
     */
    // TODO: Rename and change types and number of parameters
    public static TakeQuiz newInstance(Question question) {
        TakeQuiz fragment = new TakeQuiz();
        Bundle args = new Bundle();
        args.putString(STATE, question.getState());
        args.putString(CAPITAL, question.getCapital());
        args.putString(CITY1, question.getCityOne());
        args.putString(CITY2, question.getCityTwo());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            state = getArguments().getString(STATE);
            capital = getArguments().getString(CAPITAL);
            city1 = getArguments().getString(CITY1);
            city2 = getArguments().getString(CITY2);
        }*/



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_take_quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState ) {
        state = view.findViewById(R.id.questionText);
        answersGroup = view.findViewById(R.id.answers);
        answerA = view.findViewById(R.id.answerA);
        answerB = view.findViewById(R.id.answerB);
        answerC = view.findViewById(R.id.answerC);
        nextButton = view.findViewById(R.id.nextButton);
        qCounter = view.findViewById(R.id.questionCounter);
        score = view.findViewById(R.id.scoreCounter);
        nextButton.setOnClickListener(new nextQuestionButtonClickListener());

        questionList = new ArrayList<Question>();
        quizQuestions = new ArrayList<QuizQuestion>();

        //get instance of questionData
        questionData = new QuestionData(getActivity());

        //open db to read all questions in db
        questionData.open();


        //get all questions using AsyncTask class methods
        new TakeQuiz.QuestionDBReader().execute();

    }

    private class QuestionDBReader extends AsyncTask<Void, List<Question>> {

        @Override
        protected List<Question> doInBackground(Void... params) {
            List<Question> questions = questionData.retrieveAllQuestions();
            Log.d(DEBUG_TAG, "QuestionDBReader: Quizzes retrieved from db");
            return questions;

        }

        @Override
        protected void onPostExecute(List<Question> questionList) {
            Log.d(DEBUG_TAG, "QuestionDBReader: questionList.size(): " + questionList.size());
            Quiz nullQuiz = new Quiz();
            Collections.shuffle(questionList);
            for(int i=0; i<6; i++){
                Log.d(DEBUG_TAG, "adding a question maybe " + questionList.get(i).getCapital());
                QuizQuestion placeholder = new QuizQuestion(questionList.get(i).getCapital(), nullQuiz, questionList.get(i));
                Log.d(DEBUG_TAG, "placeholder value: " + placeholder);
                quizQuestions.add(placeholder);
                Log.d(DEBUG_TAG, "added new question");
            }
            score.setText("Score: " + correctNo );
            qCounter.setText("Question " + questionNo +"/6");
            state.setText(quizQuestions.get(questionNo - 1).getQuestion().getState());
            answerA.setText(quizQuestions.get(questionNo - 1).getQuestion().getCapital());
            answerB.setText(quizQuestions.get(questionNo - 1).getQuestion().getCityOne());
            answerC.setText(quizQuestions.get(questionNo - 1).getQuestion().getCityTwo());

        }

    }

    private class nextQuestionButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick( View v ) {
            int id = answersGroup.getCheckedRadioButtonId();
            if(id == -1)
            {
                Toast.makeText(v.getContext(), "Must Select an Answer!", Toast.LENGTH_LONG).show();
            }
            else if (questionNo < 6) {
                    if (id == answerA.getId()) {
                        correctNo++;
                        score.setText("Score: " + correctNo);
                    }
                    questionNo++;
                    qCounter.setText("Question " + questionNo + "/6");
                    state.setText(quizQuestions.get(questionNo - 1).getQuestion().getState());
                    answerA.setText(quizQuestions.get(questionNo - 1).getQuestion().getCapital());
                    answerB.setText(quizQuestions.get(questionNo - 1).getQuestion().getCityOne());
                    answerC.setText(quizQuestions.get(questionNo - 1).getQuestion().getCityTwo());
                }

        }
    }

}