package com.example.jokestarapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class ActivityCategory extends AppCompatActivity {



    public static final String KEY_EXTRACATEGORY = "JokeList";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private JokeCategory jokeCategory;
    private TextView tvCategoryName;
    private RecyclerView rvJokeList;
    private AdapterJokeList mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        rvJokeList = findViewById(R.id.rvJokeList);
        tvCategoryName = findViewById(R.id.tvCategoryName);

        jokeCategory = getIntent().getParcelableExtra(KEY_EXTRACATEGORY);
        tvCategoryName.setText(jokeCategory.getName());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvJokeList.setLayoutManager(layoutManager);
        rvJokeList.setHasFixedSize(true);

        // Fetch jokes for selected jokeCategory
        // List<Joke> jokesLocal = jokeCategory.getJokes(); // this is only local saved jokes
        fetchJokesAndSetAdapter();
    }

    private void fetchJokesAndSetAdapter() {
        List<Joke> jokesFromFirestore = new ArrayList<Joke>();
        db.collection("Jokes")
                .whereEqualTo("category", jokeCategory.getName())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                /*
                                String currentDocumentId = document.getId();
                                //document.getData().get("author");
                                Map jokeFromDb = document.getData();
                                Joke currentJoke = new Joke();
                                currentJoke.documentId = document.getId().toString();
                                currentJoke.author = jokeFromDb.get("author").toString();
                                currentJoke.authorId = jokeFromDb.get("authorId").toString();
                                currentJoke.category = jokeFromDb.get("category").toString();
                                currentJoke.comments = (List) jokeFromDb.get("comments");
                                //currentJoke.postedDate = (Date) jokeFromDb.get("postedDate");
                                currentJoke.postedDate = (Date) Calendar.getInstance().getTime();
                                currentJoke.text = jokeFromDb.get("text").toString();
                                currentJoke.votes = ((Long) jokeFromDb.get("votes")).intValue();
                                jokesFromFirestore.add(currentJoke);
                                //Log.d("TAG", document.getId() + " => " + document.getData());
                                // Log.d("TAG", "GET_DOCS_FROM_FIRESTORE: " + document.getId() + " => " + document.getData());
                                //Log.d("TAG", "GET_DOCS_FROM_FIRESTORE_AUTHOR: " + document.getData().get("author"));

                                Log.d("TAG", "GET_DOCS_FROM_FIRESTORE_: " + currentJoke.author);
                                Log.d("TAG", "GET_DOCS_FROM_FIRESTORE_: " + currentJoke.authorId);
                                Log.d("TAG", "GET_DOCS_FROM_FIRESTORE_: " + currentJoke.category);
                                Log.d("TAG", "GET_DOCS_FROM_FIRESTORE_: " + currentJoke.comments);
                                Log.d("TAG", "GET_DOCS_FROM_FIRESTORE_: " + currentJoke.postedDate);
                                Log.d("TAG", "GET_DOCS_FROM_FIRESTORE_: " + currentJoke.text);
                                Log.d("TAG", "GET_DOCS_FROM_FIRESTORE_: " + currentJoke.votes);

                                 */
                                Joke currentJoke = document.toObject(Joke.class);
                                currentJoke.documentId = document.getId().toString();
                                jokesFromFirestore.add(currentJoke);

                            }
                            //Set Adapter after all jokes are fetched
                            mAdapter = new AdapterJokeList(jokesFromFirestore);
                            rvJokeList.setAdapter(mAdapter);
                            mAdapter.setOnListItemClickListener(item -> {
                                Intent i = new Intent(ActivityCategory.this, ActivityJokeDetail.class);
                                i.putExtra(ActivityJokeDetail.KEY_JOKEDETAILS, item);
                                startActivity(i);
                            });
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}