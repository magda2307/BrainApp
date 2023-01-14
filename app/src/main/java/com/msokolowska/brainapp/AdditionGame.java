package com.msokolowska.brainapp;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AdditionGame extends AppCompatActivity implements View.OnClickListener {
    private Button answer1, answer2, answer3, answer4, playAgainButton;
    private TextView equation, timerText, scoreText, infoText;
    private List<Integer> numbers;
    private Random rand;
    private int equationResult, score, totalQuestions;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.additiongame);

        initVariables();
        initViews();
        setOnClickListeners();
        setEquation();
        setAnswers();
        startTimer();
    }

    private void initVariables() {
        numbers = new ArrayList<>();
        rand = new Random();
        equationResult = 0;
        score = 0;
        totalQuestions = 0;
        timer = new CountDownTimer(60000, 1000) {
            int timeRemaining = 60;

            public void onTick(long millisUntilFinished) {
                timeRemaining = (int) millisUntilFinished / 1000;
                timerText.setText(String.valueOf(timeRemaining));
            }

            @Override
            public void onFinish() {
                infoText.setText("Time's up! Your score: " + score);
                playAgainButton.setVisibility(View.VISIBLE);
            }
        };
    }

    private void initViews() {
        answer1 = findViewById(R.id.button1);
        answer2 = findViewById(R.id.button2);
        answer3 = findViewById(R.id.button3);
        answer4 = findViewById(R.id.button4);
        equation = findViewById(R.id.equation);
        timerText = findViewById(R.id.timer);
        scoreText = findViewById(R.id.score);
        infoText = findViewById(R.id.info);
        playAgainButton = findViewById(R.id.playAgainButton);
    }

    private void setOnClickListeners() {
        answer1.setOnClickListener(this);
        answer2.setOnClickListener(this);
        answer3.setOnClickListener(this);
        answer4.setOnClickListener(this);
        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    private void setEquation() {
        numbers.clear();
        int a = rand.nextInt(100);
        int b = rand.nextInt(100);
        equationResult = Math.abs(a - b);
        equation.setText(String.valueOf(a > b ? a - b : b - a));
        numbers.add(equationResult);
    }

    private void setAnswers() {
        for (int i = 1; i < 4; i++) {
            int randomNumber = rand.nextInt(11) - 5 + equationResult;
            numbers.add(randomNumber);
        }
        Collections.shuffle(numbers);
        answer1.setText(String.valueOf(numbers.get(0)));
        answer2.setText(String.valueOf(numbers.get(1)));
        answer3.setText(String.valueOf(numbers.get(2)));
        answer4.setText(String.valueOf(numbers.get(3)));
    }

    @Override
    public void onClick(View view) {
        totalQuestions++;
        if (String.valueOf(equationResult).equals(((Button) view).getText().toString())) {
            Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT).show();
            score++;
            timer.cancel();
            startTimer();
        } else {
            Toast.makeText(getApplicationContext(), "Incorrect!", Toast.LENGTH_SHORT).show();
            timer.cancel();
            startTimer();
        }
        scoreText.setText(score + "/" + totalQuestions);
        setEquation();
        setAnswers();
    }

    private void startTimer() {
        timer.start();
    }

    private void resetGame() {
        score = 0;
        totalQuestions = 0;
        timer.cancel();
        timer = new CountDownTimer(60000, 1000) {
            int timeRemaining = 60;

            public void onTick(long millisUntilFinished) {
                timeRemaining = (int) millisUntilFinished / 1000;
                timerText.setText(String.valueOf(timeRemaining));
            }

            @Override
            public void onFinish() {
                infoText.setText("Time's up! Your score: " + score);
                playAgainButton.setVisibility(View.VISIBLE);
            }
        };
        playAgainButton.setVisibility(View.INVISIBLE);
        startTimer();
        setEquation();
        setAnswers();
        scoreText.setText(score + "/" + totalQuestions);
    }
}
