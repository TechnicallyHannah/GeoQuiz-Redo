package com.example.a712948.geoquiz;

/**
 * @author Hannah Paulson
 * @since 12/28/15.
 */
public class Question {
    private int mTextId;
    private boolean mAnswerTrue;

    public Question(int textId, boolean answerTrue) {
        mTextId = textId;
        mAnswerTrue = answerTrue;
    }

// Getters n Setters
    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public int getTextId() {
        return mTextId;
    }

    public void setTextId(int textId) {
        mTextId = textId;
    }

}
