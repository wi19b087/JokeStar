package com.example.jokestarapplication;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Joke implements Parcelable {
    private String text;
    private int votes;
    private Date postedDate;
    private String author;
    private String authorId;
    private String category;
    private List<Comment> comments;

    public Joke(String text, Date postedDate, String category, String author, String authorId, List<Comment> arrayList) {
        this.text = text;
        this.votes = 0;
        this.postedDate = postedDate;
        this.author = author;
        this.authorId = authorId;
        this.category = category;
        this.comments = arrayList;
    }


    protected Joke(Parcel in) {
        text = in.readString();
        votes = in.readInt();
        postedDate = new Date(in.readLong());
        author = in.readString();
        authorId = in.readString();
        category = in.readString();
        comments = in.createTypedArrayList(Comment.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeInt(votes);
        dest.writeLong(postedDate.getTime());
        dest.writeString(author);
        dest.writeString(authorId);
        dest.writeString(category);
        dest.writeTypedList(comments);
    }

    public static final Creator<Joke> CREATOR = new Creator<Joke>() {
        @Override
        public Joke createFromParcel(Parcel in) {
            return new Joke(in);
        }

        @Override
        public Joke[] newArray(int size) {
            return new Joke[size];
        }
    };

    public String getText() {
        return text;
    }

    public int getVotes() {
        return votes;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public String getAuthor() {
        return author;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getCategory() {
        return category;
    }

    public List<Comment> getComments() {
        return comments;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public void JokeVoteUp() {
        votes += 1;
    }

    public void JokeVoteDown() {
        votes -= 1;
    }
}