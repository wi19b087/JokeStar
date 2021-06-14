package com.example.jokestarapplication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Joke {
    private String text;
    private int votes;
    private Date postedDate;
    private String Author;
    private List<Comment> comments;

    public Joke(String text, Date postedDate, String author) {
        this.text = text;
        this.votes = 0;
        this.postedDate = postedDate;
        Author = author;
        this.comments = new ArrayList<>();
    }
}
