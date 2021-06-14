package com.example.jokestarapplication;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.type.DateTime;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NewJokeFragment extends Fragment {

    private Spinner spCategories;
    private List<JokeCategory> categoryList;
    private EditText etnewJoke;
    private Button btsend;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_joke, container, false);
        
        spCategories = view.findViewById(R.id.spcategories);
        categoryList = (List<JokeCategory>) getArguments().getSerializable("Categories");
        ArrayAdapter<JokeCategory> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, categoryList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategories.setAdapter(adapter);

        etnewJoke = view.findViewById(R.id.etnewJoke);

        btsend = view.findViewById(R.id.btsend);
        btsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendJoke(v);
            }
        });

        return view;
    }

    public JokeCategory getSelectedCategory(View v){
        return  (JokeCategory) spCategories.getSelectedItem();
    }

    public void SendJoke(View v){
        Joke joke = new Joke(etnewJoke.getText().toString(), Calendar.getInstance().getTime(), "TEMP"); //TODO: add author
        getSelectedCategory(v).getJokes().add(joke);
        Toast.makeText(this.getContext(), "Gesendet", Toast.LENGTH_LONG).show();
        etnewJoke.setText("");


    }
}