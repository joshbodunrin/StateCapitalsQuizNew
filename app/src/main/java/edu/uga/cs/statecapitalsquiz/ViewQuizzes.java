package edu.uga.cs.statecapitalsquiz;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.uga.cs.statecapitalsquiz.placeholder.PlaceholderContent;

/**
 * A fragment representing a list of Items.
 */
public class ViewQuizzes extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";

    private static final String DEBUG_TAG = "ViewQuizzesFragment";

    private QuizData quizData = null;

    private List<Quiz> quizList;

    private RecyclerView recyclerView;

    private MyItemRecyclerViewAdapter recyclerViewAdapter;
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ViewQuizzes() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ViewQuizzes newInstance(int columnCount) {
        ViewQuizzes fragment = new ViewQuizzes();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_quizzes_list, container, false);
/*
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(PlaceholderContent.ITEMS));
        }
 */
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerView = getView().findViewById(R.id.list);

        //using linear layout
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        quizList = new ArrayList<Quiz>();

        //get instance of quizDate
        quizData = new QuizData(getActivity());

        //open db to read all quizzes in db
        quizData.open();

        //get all quizzes using AsyncTask class methods
        new QuizDBReader().execute();
    }

    private class QuizDBReader extends AsyncTask<Void, List<Quiz>> {

        @Override
        protected  List<Quiz> doInBackground(Void... params) {
            List<Quiz> quizzes = quizData.retrieveAllQuiz();
            Log.d(DEBUG_TAG, "QuizDBReader: Quizzes retrieved from db");
            return quizzes;

        }

        @Override
        protected void onPostExecute(List<Quiz> quizzesList) {
            Log.d(DEBUG_TAG, "QuizDBReader: quizList.size(): " + quizzesList.size());
            quizList.addAll(quizzesList);

            //create recyclerAdapter and set it to quizzes that were just read
            recyclerViewAdapter = new MyItemRecyclerViewAdapter(getActivity(), quizList);
            recyclerView.setAdapter(recyclerViewAdapter);

        }

    }
    @Override
    public void onResume() {
        super.onResume();

        //open
        if (quizData != null && !quizData.isDBOpen()) {
            quizData.open();
            Log.d(DEBUG_TAG, "ViewQuizzesFragement.onResume(): opening DB");

        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if (quizData != null) {
            quizData.close();
            Log.d(DEBUG_TAG, "ViewQuizzesFragment.onPause: closing DB");

        }
    }
}