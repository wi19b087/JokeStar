package com.example.jokestarapplication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Joke {
    public String text;
    public int votes;
    public Date postedDate;
    public String author;
    public String authorId;
    public String category;
    public List<Comment> comments;

    public Joke(String text, Date postedDate,String category, String author, String authorId ) {
        this.text = text;
        this.votes = 0;
        this.postedDate = postedDate;
        this.author = author;
        this.authorId = authorId;
        this.category = category;
        this.comments = new ArrayList<>();
    }
}