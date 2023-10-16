package com.itbd.examnierteacher.datamanage;

public class setQuestion {

    private String QuestionNumber;
    private String WriteQuestion;
    private String OptionOne;
    private String OptionTwo;
    private String OptionThree;
    private String OptionFour;

    private String Correct;

    public setQuestion() {
    }

    public setQuestion(String questionNumber, String writeQuestion, String optionOne, String optionTwo, String optionThree, String optionFour, String correct) {
        QuestionNumber = questionNumber;
        WriteQuestion = writeQuestion;
        OptionOne = optionOne;
        OptionTwo = optionTwo;
        OptionThree = optionThree;
        OptionFour = optionFour;
        Correct = correct;
    }

    public String getQuestionNumber() {
        return QuestionNumber;
    }

    public void setQuestionNumber(String questionNumber) {
        QuestionNumber = questionNumber;
    }

    public String getWriteQuestion() {
        return WriteQuestion;
    }

    public void setWriteQuestion(String writeQuestion) {
        WriteQuestion = writeQuestion;
    }

    public String getOptionOne() {
        return OptionOne;
    }

    public void setOptionOne(String optionOne) {
        OptionOne = optionOne;
    }

    public String getOptionTwo() {
        return OptionTwo;
    }

    public void setOptionTwo(String optionTwo) {
        OptionTwo = optionTwo;
    }

    public String getOptionThree() {
        return OptionThree;
    }

    public void setOptionThree(String optionThree) {
        OptionThree = optionThree;
    }

    public String getOptionFour() {
        return OptionFour;
    }

    public void setOptionFour(String optionFour) {
        OptionFour = optionFour;
    }

    public String getCorrect() {
        return Correct;
    }

    public void setCorrect(String correct) {
        Correct = correct;
    }
}
