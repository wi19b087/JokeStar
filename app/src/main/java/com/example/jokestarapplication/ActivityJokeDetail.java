package com.example.jokestarapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ActivityJokeDetail extends AppCompatActivity {

    public static final String KEY_JOKEDETAILS = "JOKEDETAILS";
    private Joke joke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_detail);

        joke = getIntent().getParcelableExtra(KEY_JOKEDETAILS);

    }
}