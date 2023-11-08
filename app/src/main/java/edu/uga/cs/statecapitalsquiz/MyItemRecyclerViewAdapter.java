package edu.uga.cs.statecapitalsquiz;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import edu.uga.cs.statecapitalsquiz.placeholder.PlaceholderContent.PlaceholderItem;
import edu.uga.cs.statecapitalsquiz.databinding.FragmentViewQuizzesBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    public static final String DEBUG_TAG = "QuizRecyclerViewAdapter";

    private final List<Quiz> mValues;

    private final Context context;


    public MyItemRecyclerViewAdapter(Context context, List<Quiz> items) {
        this.context = context;
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView date;
        public final TextView result;
        //public PlaceholderItem mItem;

        public ViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            result = itemView.findViewById(R.id.result);
        }


/*
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

 */
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Quiz quiz = mValues.get(position);
        Log.d(DEBUG_TAG, "onBindViewHolder" + quiz);

        holder.result.setText(quiz.getResult());
        holder.date.setText(quiz.getDate());

    }
}