package com.example.jokestarapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class ActivityJokeDetail extends AppCompatActivity {

    public static final String KEY_JOKEDETAILS = "JOKEDETAILS";
    private Joke joke;
    private TextView tvJokeText, tvJokeVote, tvAuthor, tvPostedDate;
    private EditText etNewComment;
    private Button btJokeUp, btJokeDown, btSendComment;
    private RecyclerView recyclerView;
    private View include;
    private AdapterJokeDetail mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_detail);

        joke = getIntent().getParcelableExtra(KEY_JOKEDETAILS);

        include = findViewById(R.id.include);
        tvAuthor = findViewById(R.id.tvAuthor);
        tvPostedDate = findViewById(R.id.tvPostDate);
        tvJokeText = include.findViewById(R.id.tvCommentText);
        tvJokeVote = include.findViewById(R.id.tvCommentVotes);
        btJokeUp = include.findViewById(R.id.btCommentUp);
        btJokeDown = include.findViewById(R.id.btCommentDown);

        tvJokeText.setText(joke.getText());
        tvJokeVote.setText(Integer.toString(joke.getVotes()));
        tvAuthor.setText(joke.getAuthor());
        tvPostedDate.setText(joke.getPostedDate().toString());
        etNewComment = findViewById(R.id.etNewComment);
        btSendComment = findViewById(R.id.btSendComment);


        recyclerView = findViewById(R.id.rvComments);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        mAdapter = new AdapterJokeDetail(joke.getComments());
        recyclerView.setAdapter(mAdapter);

        btSendComment.setOnClickListener(v -> {
            SendComment();
            mAdapter.notifyDataSetChanged();
        });

        btJokeUp.setOnClickListener(v -> {
            joke.JokeVoteUp();
            tvJokeVote.setText(Integer.toString(joke.getVotes()));
        });
        btJokeDown.setOnClickListener(v -> {
            joke.JokeVoteDown();
            tvJokeVote.setText(Integer.toString(joke.getVotes()));
        });
    }


    private void SendComment() {
        Comment comment = new Comment(etNewComment.getText().toString(), 0, "author"); //TODO: add author
        joke.getComments().add(comment);
    }
}