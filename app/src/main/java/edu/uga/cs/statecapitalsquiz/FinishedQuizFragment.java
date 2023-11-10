package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FinishedQuizFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FinishedQuizFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "firstParam";



    // TODO: Rename and change types of parameters
    private int correctNo;

    private Button button;
    private TextView textView;


    public FinishedQuizFragment() {
        correctNo = -1;
        // Required empty public constructor
    }

    public FinishedQuizFragment(int correctNo){
        this.correctNo = correctNo;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param correctNo Parameter 1.
     * @return A new instance of fragment FinishedQuizFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FinishedQuizFragment newInstance(int correctNo) {
        FinishedQuizFragment fragment = new FinishedQuizFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, correctNo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            correctNo = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_finished_quiz, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState ) {
        button = view.findViewById(R.id.finishButton);
        textView = view.findViewById(R.id.textView3);
        textView.setText("You Scored: " + correctNo +"/6");
        button.setOnClickListener(new FinishedQuizFragment.startButtonClickListener());
    }

    private class startButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction().replace( R.id.fragmentContainerView, new HomeScreen()).addToBackStack("finish screen" ).commit();
        }
    }
}