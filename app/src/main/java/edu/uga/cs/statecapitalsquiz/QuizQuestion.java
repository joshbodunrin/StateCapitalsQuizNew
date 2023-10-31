package edu.uga.cs.statecapitalsquiz;

public class QuizQuestion {

    private long id;

    private String answer;

    private Quiz quiz;

    private Question question;

    public QuizQuestion() {
        this.id = -1;
        this.answer = null;
        this.quiz = null;
        this.question = null;
    }

    public QuizQuestion(String answer, Quiz quiz, Question question) {
        this.id = -1;
        this.answer = answer;
        this.quiz = quiz;
        this.question = question;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return this.id;
    }

    public Quiz getQuiz() {
        return this.quiz;
    }

    public Question getQuestion() {
        return this.question;
    }

    public String getAnswer() {
        return this.answer;
    }
}
