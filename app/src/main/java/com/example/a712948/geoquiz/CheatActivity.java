package com.example.a712948.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {
    private boolean mAnswer;
    private TextView mAnswerView;
    private Button mAnswerButton;
    private boolean mIsCheater;
    private static final String EXTRA_ANSWER_IS_TRUE = "answer";
    private static final String ANSWER_IS_SHOWN = "shown";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAnswer = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        mAnswerView = (TextView) findViewById(R.id.answerTextView);
        mAnswerButton = (Button) findViewById(R.id.showAnswerButton);


        if (savedInstanceState != null) {
            setShownResults(savedInstanceState.getBoolean(ANSWER_IS_SHOWN));
            mIsCheater = savedInstanceState.getBoolean(ANSWER_IS_SHOWN);
            mAnswer = savedInstanceState.getBoolean(EXTRA_ANSWER_IS_TRUE, false);
            Log.i("instance cheater", mIsCheater + "");
            Log.i("instance answer", mAnswer + "");

            if (mAnswer) {
                mAnswerView.setText(R.string.button_true);
            } else {
                mAnswerView.setText(R.string.button_false);
            }
        }
        
        mAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                boolean cheated = mIsCheater;
                boolean answer = mAnswer;
                Log.i("onClick cheater", cheated + "");
                int resID = 0;
                if (answer) {
                    resID = R.string.button_true;
                } else {
                    resID = R.string.button_false;
                }
                setShownResults(true);
                mAnswerView.setText(resID);
            }
        });
    }

    private void setShownResults(Boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(ANSWER_IS_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
        mIsCheater = true;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i("TAG", "onSaveInstanceState");
        savedInstanceState.putBoolean(ANSWER_IS_SHOWN, mIsCheater);
        savedInstanceState.putBoolean(EXTRA_ANSWER_IS_TRUE, mAnswer);
        Log.i("CHEATER : ", mIsCheater + "");
    }

}
