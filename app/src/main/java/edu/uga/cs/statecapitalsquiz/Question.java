package edu.uga.cs.statecapitalsquiz;

public class Question {

    private long id;
    private String state;
    private String capital;
    private String cityOne;
    private String cityTwo;

    public Question() {
        this.id = -1;
        this.state = null;
        this.capital = null;
        this.cityOne = null;
        this.cityTwo = null;
    }

    public Question(String state, String capital, String cityOne, String cityTwo) {
        this.id = -1;
        this.state = state;
        this.capital = capital;
        this.cityOne = cityOne;
        this.cityTwo = cityTwo;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setState(String state) {
        this.state = state;
    }
    public void setCapital(String capital) {
        this.capital = capital;
    }

    public void setCityOne(String cityOne) {
        this.cityOne = cityOne;
    }
    public void setCityTwo(String cityTwo) {
        this.cityTwo = cityTwo;
    }

    public long getId(){
        return this.id;
    }

    public String getState() {
        return this.state;
    }

    public String getCapital() {
        return this.capital;
    }

    public String getCityOne() {
        return this.cityOne;
    }

    public String getCityTwo() {
        return this.cityTwo;
    }



}
