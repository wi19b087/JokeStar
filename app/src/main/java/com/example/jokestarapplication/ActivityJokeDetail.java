package com.example.jokestarapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ActivityJokeDetail extends AppCompatActivity {

    public static final String KEY_JOKEDETAILS = "JOKEDETAILS";
    private Joke joke;
    private TextView tvJokeText, tvCommentVotes, tvAuthor, tvPostedDate;
    private EditText etNewComment;
    private Button btCommentUp, btCommentDown, btSendComment;
    private RecyclerView recyclerView;
    private View include;
    private AdapterJokeDetail mAdapter;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_detail);

        joke = getIntent().getParcelableExtra(KEY_JOKEDETAILS);
        DocumentReference jokeRef = db.collection("Jokes").document(joke.documentId);
        Log.d("SEND_COMMENT", "Joke to update: " + joke);
        include = findViewById(R.id.include);
        tvAuthor = findViewById(R.id.tvAuthor);
        tvPostedDate = findViewById(R.id.tvPostDate);
        tvJokeText = include.findViewById(R.id.tvCommentText);
        tvCommentVotes = include.findViewById(R.id.tvCommentVotes);
        btCommentUp = include.findViewById(R.id.btCommentUp);
        btCommentDown = include.findViewById(R.id.btCommentDown);

        tvJokeText.setText(joke.getText());
        tvCommentVotes.setText(Integer.toString(joke.getVotes()));
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

        btCommentUp.setOnClickListener(v -> {
            joke.JokeVoteUp();
            tvCommentVotes.setText(Integer.toString(joke.getVotes()));
        });
        btCommentDown.setOnClickListener(v -> {
            joke.JokeVoteDown();
            tvCommentVotes.setText(Integer.toString(joke.getVotes()));
        });
    }


    private void SendComment() {
        String currentUserName = mAuth.getCurrentUser().getDisplayName();
        String currentUserId = mAuth.getCurrentUser().getUid();
        Comment comment = new Comment(etNewComment.getText().toString(), 0, currentUserName, currentUserId);
        joke.getComments().add(comment);
        Log.d("SEND_COMMENT", "Joke to update: " + joke);
        // Update joke with new comment in firebase
        DocumentReference jokeRef = db.collection("Jokes").document(joke.documentId);

        jokeRef.update("comments",  joke.getComments())
                .addOnSuccessListener(documentReference -> {
                    Log.d("SEND_COMMENT", "Comment added in joke ID: " + joke.documentId);
                    Toast.makeText(this, "Kommentar hinzugefügt", Toast.LENGTH_LONG).show();
                })
                .addOnFailureListener(e -> {
                    Log.d("SEND_COMMENT", "Error adding document", e);
                    Toast.makeText(this, "Kommentar konnte nicht hinzugefügt werden!", Toast.LENGTH_LONG).show();
                });
    }


}