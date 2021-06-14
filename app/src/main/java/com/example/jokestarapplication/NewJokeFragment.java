package com.example.jokestarapplication;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.DateTime;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NewJokeFragment extends Fragment {

    private Spinner spCategories;
    private List<JokeCategory> categoryList;
    private EditText etnewJoke;
    private Button btsend;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

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
        String currentCategory = getSelectedCategory(v).getName();
        String currentUserName = mAuth.getCurrentUser().getDisplayName();
        String currentUserId = mAuth.getCurrentUser().getUid();
        Joke joke = new Joke(etnewJoke.getText().toString(), Calendar.getInstance().getTime(), currentCategory, currentUserName, currentUserId ); //TODO: add author
        Log.d("SEND_JOKE", "Joke JSON: " + joke.text + " ::: " + joke.postedDate + " ::: " + joke.author );
        addJokeToFirebase(this.getContext(), joke);
        etnewJoke.setText("");
    }

    private void addJokeToFirebase(Context ctx, Joke joke) {
        // Add a new joke as a document with a generated ID to collection "Jokes".
        db.collection("Jokes")
                .add(joke)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("SEND_JOKE", "DocumentSnapshot added with ID: " + documentReference.getId());
                        Toast.makeText(ctx, "Gesendet in Kategorie " + joke.category, Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("SEND_JOKE", "Error adding document", e);
                        Toast.makeText(ctx, "Konnte den Witz nicht posten", Toast.LENGTH_LONG).show();
                    }
                });
    }
}