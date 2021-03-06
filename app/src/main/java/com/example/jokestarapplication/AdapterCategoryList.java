package com.example.jokestarapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterCategoryList extends RecyclerView.Adapter<AdapterCategoryList.CategoryViewHolder> {

    private List<JokeCategory> mItems;
    private Context mContext;
    private ListItemClickListener mListItemClickListener;

    public AdapterCategoryList(List<JokeCategory> mItems, Context mContext, ListItemClickListener mListItemClickListener) {
        this.mItems = mItems;
        this.mContext = mContext;
        this.mListItemClickListener = mListItemClickListener;
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.list_item_category;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.list_item_category, parent, false);

        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return (mItems == null) ? 0 : mItems.size();
    }

    interface ListItemClickListener {
        void onListItemClick(JokeCategory item);
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvCatName, tvCatNum;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCatName = itemView.findViewById(R.id.tvCatName);
            tvCatNum = itemView.findViewById(R.id.tvCatNum);
            itemView.setOnClickListener(this);
        }

        public void bind(int position) {
            tvCatName.setText(mItems.get(position).getName());
            tvCatNum.setText(mItems.get(position).getJokeNumString());
        }

        @Override
        public void onClick(View v) {
            if (mListItemClickListener != null) {
                int clickedIndex = getAdapterPosition();
                JokeCategory jokeCategory = mItems.get(clickedIndex);
                mListItemClickListener.onListItemClick(jokeCategory);
            }
        }
    }
}

