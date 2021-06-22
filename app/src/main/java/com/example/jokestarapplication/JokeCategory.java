package com.example.jokestarapplication;

import android.os.Parcel;
import android.os.Parcelable;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JokeCategory implements Parcelable {
    private String name;
    private List<Joke> jokes;

    public JokeCategory(String name) {
        this.name = name;
        this.jokes = new ArrayList<>();
    }

    protected JokeCategory(Parcel in) {
        name = in.readString();
        jokes = in.createTypedArrayList(Joke.CREATOR);
    }

    public JokeCategory(String name, List<Joke> jokes) {
        this.name = name;
        this.jokes = jokes;
    }

    public static final Creator<JokeCategory> CREATOR = new Creator<JokeCategory>() {
        @Override
        public JokeCategory createFromParcel(Parcel in) {
            return new JokeCategory(in);
        }

        @Override
        public JokeCategory[] newArray(int size) {
            return new JokeCategory[size];
        }
    };

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
    public @NotNull String toString() {
        return name;
    }

    public void addJoke(Joke joke) {
        jokes.add(joke);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeTypedList(jokes);
    }
}

