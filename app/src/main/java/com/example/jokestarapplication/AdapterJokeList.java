package com.example.jokestarapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterJokeList extends RecyclerView.Adapter<AdapterJokeList.JokeViewHolder> {

    private List<Joke> mItems;
    private ListItemClickListener mListItemClickListener;

    public AdapterJokeList(List<Joke> mItems) {
        this.mItems = mItems;
    }

    @NonNull
    @NotNull
    @Override
    public JokeViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.joke_list_item, parent, false);

        return new JokeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterJokeList.JokeViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return (mItems == null) ? 0 : mItems.size();
    }

    interface ListItemClickListener {
        void onListItemClick(Joke item);
    }

    public class JokeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvJokeText, tvPointsTotal;
        private Button btJokeComments, btJokeUp, btJokeDown;

        public JokeViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvJokeText = itemView.findViewById(R.id.tvJokeText);
            tvPointsTotal = itemView.findViewById(R.id.tvPointsTotal);
            btJokeComments = itemView.findViewById(R.id.btJokeComments);
            btJokeUp = itemView.findViewById(R.id.btJokeUp);
            btJokeDown = itemView.findViewById(R.id.btJokeDown);
            itemView.setOnClickListener(this);
        }

        public void bind(int position) {
            tvJokeText.setText(mItems.get(position).getText());
            tvPointsTotal.setText(Integer.toString(mItems.get(position).getVotes()));
        }

        @Override
        public void onClick(View v) {
            if (mListItemClickListener != null) {
                int clickedIndex = getAdapterPosition();
                Joke joke = mItems.get(clickedIndex);
                mListItemClickListener.onListItemClick(joke);
            }

        }
    }
}
