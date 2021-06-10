package com.example.jokestarapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder> {

    private List<JokeCategory> mItems;

    public CategoryListAdapter(List<JokeCategory> mItems) {
        this.mItems = mItems;
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.category_list_item;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return (mItems==null) ? 0 : mItems.size();
    }


    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        private TextView tvCatName;
        private TextView tvCatNum;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCatName = itemView.findViewById(R.id.tvCatName);
            tvCatNum = itemView.findViewById(R.id.tvCatNum);
        }

        public TextView getTvCatName() {
            return tvCatName;
        }

        public TextView getTvCatNum() {
            return tvCatNum;
        }

        public void bind(int position) {
            tvCatName.setText(mItems.get(position).getName());
            tvCatNum.setText(mItems.get(position).getJokeNumString());
        }
    }
}

