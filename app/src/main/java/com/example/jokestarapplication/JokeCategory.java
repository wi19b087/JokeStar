package com.example.jokestarapplication;

import java.util.LinkedList;
import java.util.List;

public class JokeCategory {
    private String name;
    private List<Joke> jokes;

    public JokeCategory(String name) {
        this.name = name;
        this.jokes= new LinkedList<>();
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
}
