package com.example.jokestarapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class FragmentMain extends Fragment implements AdapterCategoryList.ListItemClickListener{

    private RecyclerView rvcategories;
    private List<JokeCategory> categories;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        if (getArguments() != null) {
            categories = (List<JokeCategory>) getArguments().getSerializable("Categories");
        }

        // Add the following lines to create RecyclerView
        rvcategories = view.findViewById(R.id.rvcategories);
        rvcategories.setHasFixedSize(true);
        rvcategories.setLayoutManager(new LinearLayoutManager(view.getContext()));
        AdapterCategoryList mAdapter = new AdapterCategoryList(categories, getContext(), this);
        rvcategories.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onResume() {
        if (getArguments() != null) {
            categories = (List<JokeCategory>) getArguments().getSerializable("Categories");
        }
        rvcategories.getAdapter().notifyDataSetChanged();
        super.onResume();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onListItemClick(JokeCategory item) {
        Intent i = new Intent(getContext(), ActivityCategory.class);
        i.putExtra(ActivityCategory.KEY_EXTRACATEGORY, item);
        startActivity(i);
    }
}