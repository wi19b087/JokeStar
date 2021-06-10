package com.example.jokestarapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import java.util.LinkedList;
import java.util.List;

public class MainFragment extends Fragment {

    private RecyclerView rvcategories;
    private List<JokeCategory> categories;

    private onFragmentBtnSelected listener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        categories = DemoData();

        // Add the following lines to create RecyclerView
        rvcategories = view.findViewById(R.id.rvcategories);
        rvcategories.setHasFixedSize(true);
        rvcategories.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvcategories.setAdapter(new CategoryListAdapter(categories));


        Button button=view.findViewById(R.id.load);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonSelected();
            }
        });


        return view;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof onFragmentBtnSelected)
            listener = (onFragmentBtnSelected) context;
        else
            throw new ClassCastException(context.toString() + "must implement listener");
    }


    public interface onFragmentBtnSelected {
        void onButtonSelected();
    }

    private List<JokeCategory> DemoData() {
        List<JokeCategory> data = new LinkedList<>();
        data.add(new JokeCategory("Short Jokes"));
        data.add(new JokeCategory("Long Jokes"));
        data.add(new JokeCategory("One Liner"));
        data.add(new JokeCategory("Dumb Jokes"));
        data.add(new JokeCategory("Chuck Norris"));

        return data;
    }
}