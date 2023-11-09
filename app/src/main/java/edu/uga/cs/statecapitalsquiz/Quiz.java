package edu.uga.cs.statecapitalsquiz;

import java.util.List;
public class Quiz {
    private long id;

    private String date;

    private String result;

    //private List<Question> quizQuestions;

    public Quiz() {
        this.id = -1;
        this.date = null;
        this.result = null;
        //this.quizQuestions = null;
    }

    public Quiz(String date, String result) {
        this.id = -1;
        this.date = date;
        this.result = result;
        //this.quizQuestions = quizQuestions;

    }

    public String getDate() {
        return this.date;
    }


    public String getResult() {
        return this.result;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setResult(String result) {
        this.result = result;
    }
}


