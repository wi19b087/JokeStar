package com.example.jokestarapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class Comment implements Parcelable {
    private String text;
    private int votes;
    private String author;

    public Comment(String text, int votes, String author) {
        this.text = text;
        this.votes = votes;
        this.author = author;
    }

    protected Comment(Parcel in) {
        text = in.readString();
        votes = in.readInt();
        author = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeInt(votes);
        dest.writeString(author);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    public String getText() {
        return text;
    }

    public int getVotes() {
        return votes;
    }

    public void CommentVoteUp() {
        votes += 1;
    }

    public void CommentVoteDown() {
        votes -= 1;
    }
}
