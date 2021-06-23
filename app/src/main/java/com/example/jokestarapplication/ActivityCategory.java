package com.example.jokestarapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityCategory extends AppCompatActivity {

    public static final String KEY_EXTRACATEGORY = "JokeList";
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

        mAdapter = new AdapterJokeList(jokeCategory.getJokes());
        rvJokeList.setAdapter(mAdapter);

        mAdapter.setOnListItemClickListener(item -> {
            Intent i = new Intent(ActivityCategory.this, ActivityJokeDetail.class);
            i.putExtra(ActivityJokeDetail.KEY_JOKEDETAILS, item);
            startActivity(i);
        });
    }
}