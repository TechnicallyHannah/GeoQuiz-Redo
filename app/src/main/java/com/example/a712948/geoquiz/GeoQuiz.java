package com.example.a712948.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GeoQuiz extends AppCompatActivity {
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private TextView mQuestion;
    private Button mPrevious;
    private Button mCheatButton;
    private int mCurrentIndex = 0;
    private boolean mIsCheater;
    private static final String INDEX_KEY = "index";
    private static final String EXTRA_ANSWER_IS_TRUE = "answer";
    private static final int REQUEST_CODE_KEY = 0;
    private static final String ANSWER_IS_SHOWN = "shown";

    private Question[] mQuestions = {
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_quiz);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(INDEX_KEY, 0);
        }

        mQuestion = (TextView) findViewById(R.id.question_text);
        setQuestion();
        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GeoQuiz.this, CheatActivity.class);
                boolean answerIsTrue = mQuestions[mCurrentIndex].isAnswerTrue();
                i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
                startActivityForResult(i, REQUEST_CODE_KEY);
            }
        });
        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });
        mNextButton = (Button) findViewById(R.id.next);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestions.length;
                setQuestion();
            }
        });
        mQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestions.length;
                setQuestion();
            }
        });
        mPrevious = (Button) findViewById(R.id.previous);
        mPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex == 0) {
                    mCurrentIndex = mQuestions.length - 1;
                    setQuestion();
                } else {
                    mCurrentIndex = (mCurrentIndex - 1) % mQuestions.length;
                    setQuestion();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_geo_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setQuestion() {
        int question = mQuestions[mCurrentIndex].getTextId();
        mQuestion.setText(question);
    }
    private static boolean wasAnswerShown(Intent results){
        return results.getBooleanExtra(ANSWER_IS_SHOWN, false);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        if(requestCode == REQUEST_CODE_KEY){
            if(data == null){
                return;
            }
            mIsCheater = wasAnswerShown(data);
        }
    }

    private void checkAnswer(Boolean userPressedTrue) {
        boolean isAnswerTrue = mQuestions[mCurrentIndex].isAnswerTrue();
        boolean cheated = mIsCheater;

        if(!mIsCheater) {
            if (userPressedTrue == isAnswerTrue) {
                Toast.makeText(GeoQuiz.this, R.string.correct,
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(GeoQuiz.this, R.string.incorrect,
                        Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(GeoQuiz.this, "You cheated",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i("TAG", "onSaveInstanceState");
        savedInstanceState.putInt(INDEX_KEY, mCurrentIndex);
    }
}
