package com.example.jokestarapplication;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JokeCategory  {
    private String name;
    private List<Joke> jokes;

    public JokeCategory(String name) {
        this.name = name;
        this.jokes= new ArrayList<>();
    }

    public JokeCategory(String name, List<Joke> jokes) {
        this.name = name;
        this.jokes = jokes;
    }

    public String getName() {
        return name;
    }

    public String getJokeNumString() {
        return String.valueOf(jokes.size());
    }

    public List<Joke> getJokes() {
        return jokes;
    }

    @Override
    public String toString() {
        return name;
    }

    public void addJoke(Joke joke) {
        jokes.add(joke);
    }
}

