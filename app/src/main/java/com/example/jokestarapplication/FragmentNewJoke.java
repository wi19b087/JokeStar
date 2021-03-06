package com.example.jokestarapplication;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FragmentNewJoke extends Fragment {

    private Spinner spCategories;
    private List<JokeCategory> categoryList;
    private EditText etnewJoke;
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
        if (getArguments() != null) {
            categoryList = (List<JokeCategory>) getArguments().getSerializable("Categories");
        }
        ArrayAdapter<JokeCategory> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, categoryList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategories.setAdapter(adapter);

        etnewJoke = view.findViewById(R.id.etnewJoke);

        Button btsend = view.findViewById(R.id.btsend);
        btsend.setOnClickListener(v -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                SendJoke();
            } else {
                Toast.makeText(view.getContext(), "Bitte melden Sie sich an um einen Witz zu senden", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    public JokeCategory getSelectedCategory() {
        return (JokeCategory) spCategories.getSelectedItem();
    }

    public void SendJoke() {
        String currentCategory = getSelectedCategory().getName();
        String currentUserName = mAuth.getCurrentUser().getDisplayName();
        String currentUserId = mAuth.getCurrentUser().getUid();
        Joke joke = new Joke(etnewJoke.getText().toString(), Calendar.getInstance().getTime(), currentCategory, currentUserName, currentUserId, new ArrayList<Comment>());
        getSelectedCategory().addJoke(joke);
        Log.d("SEND_JOKE", "Joke JSON: " + joke.getText() + " ::: " + joke.getPostedDate() + " ::: " + joke.getAuthor());
        addJokeToFirebase(this.getContext(), joke);
        etnewJoke.setText("");

        ((ActivityMain) getActivity()).updateCategories(categoryList);
    }

    private void addJokeToFirebase(Context ctx, Joke joke) {
        // Add a new joke as a document with a generated ID to collection "Jokes".
        db.collection("Jokes")
                .add(joke)
                .addOnSuccessListener(documentReference -> {
                    Log.d("SEND_JOKE", "DocumentSnapshot added with ID: " + documentReference.getId());
                    Toast.makeText(ctx, "Gesendet in Kategorie " + joke.getCategory(), Toast.LENGTH_LONG).show();
                })
                .addOnFailureListener(e -> {
                    Log.d("SEND_JOKE", "Error adding document", e);
                    Toast.makeText(ctx, "Konnte den Witz nicht posten", Toast.LENGTH_LONG).show();
                });
    }
}