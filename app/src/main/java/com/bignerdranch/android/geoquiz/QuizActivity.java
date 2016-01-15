package com.bignerdranch.android.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_SCORE = "score";

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private TextView mQuestionTextView;
    private TextView mScoreTextView;

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    private int mCurrentIndex = 0;
    private int score = 0;

    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getTextResId(); //gets the ID of the current question
        mQuestionTextView.setText(question);
    }

    private void nextQuestion(){
        mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length; // This is brilliant
        updateQuestion();
    }

    private void prevQuestion(){
        if(mCurrentIndex>0){
            mCurrentIndex--;
        }
        else{
            mCurrentIndex = mQuestionBank.length - 1;
        }

        updateQuestion();
    }

    private void updateScore(){
        String scoreText = Integer.toString(score) + "  points";
        mScoreTextView.setText(scoreText);
    }


    private void checkAnswer(boolean userPressedTrue){ //Terrible parameter name
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;

        if (userPressedTrue == answerIsTrue){
            messageResId = R.string.correct_toast;
            score++;
            updateScore();
            nextQuestion();
        } else {
            messageResId = R.string.incorrect_toast;
            score--;
            updateScore();
            nextQuestion();
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Why does everything go here?
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onStart() called");
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion();
            }
        });

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener(){
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

        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                nextQuestion();
            }
        });

        mPrevButton = (ImageButton) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener(){
            @Override
        public void onClick(View v){
                prevQuestion();
            }
        });

        mScoreTextView = (TextView) findViewById(R.id.score_text_view);

        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        updateQuestion();
        updateScore();
    }

        @Override
        public void onSaveInstanceState (Bundle savedInstanceState){
            super.onSaveInstanceState(savedInstanceState); // The redundancy is interesting
            Log.i(TAG, "onSaveInstanceState");
            savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
            savedInstanceState.putInt(KEY_SCORE,score);
        }

        @Override
        public void onStart() {
            super.onStart();
            Log.d(TAG, "onStart() called");
        }

        @Override
        public void onPause() {
            super.onPause();
            Log.d(TAG, "onPause() called");
        }

        @Override
        public void onResume() {
            super.onResume();
            Log.d(TAG, "onResume() called");
        }

        @Override
        public void onStop() {
            super.onStop();
            Log.d(TAG, "onStop() called");
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            Log.d(TAG, "onDestroy() called");
        }
}
