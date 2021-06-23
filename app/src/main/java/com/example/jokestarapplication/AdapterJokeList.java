package com.example.jokestarapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterJokeList extends RecyclerView.Adapter<AdapterJokeList.JokeViewHolder> {

    private List<Joke> mItems;
    private ListItemClickListener mListItemClickListener;
    FirebaseFirestore db = FirebaseFirestore.getInstance();





    public AdapterJokeList(List<Joke> mItems) {
        this.mItems = mItems;
    }

    @NonNull
    @NotNull
    @Override
    public JokeViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.list_item_joke, parent, false);

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

    public void setOnListItemClickListener(ListItemClickListener listItemClickListener) {
        mListItemClickListener = listItemClickListener;
    }

    public class JokeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvJokeText, tvPointsTotal;
        private Button btJokeUp, btJokeDown, btJokeComments;

        public JokeViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvJokeText = itemView.findViewById(R.id.tvJokeText);
            tvPointsTotal = itemView.findViewById(R.id.tvPointsTotal);
            btJokeUp = itemView.findViewById(R.id.btJokeUp);
            btJokeDown = itemView.findViewById(R.id.btJokeDown);
            btJokeComments = itemView.findViewById(R.id.btJokeComments);

            btJokeUp.setOnClickListener(this);
            btJokeDown.setOnClickListener(this);
            btJokeComments.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        public void bind(int position) {
            tvJokeText.setText(mItems.get(position).getText());
            tvPointsTotal.setText(Integer.toString(mItems.get(position).getVotes()));
        }

        @Override
        public void onClick(View v) {

            int clickedIndex = getAdapterPosition();
            Joke joke = mItems.get(clickedIndex);
            DocumentReference jokeRef = db.collection("Jokes").document(joke.documentId);
            switch (v.getId()) {
                case R.id.btJokeUp:
                    joke.JokeVoteUp();
                    // Update vote in firestore
                    jokeRef.update("votes", joke.votes );
                    break;

                case R.id.btJokeDown:
                    joke.JokeVoteDown();
                    // Update vote in firestore
                    jokeRef.update("votes", joke.votes );
                    break;

                case R.id.btJokeComments:
                    joke = mItems.get(clickedIndex);
                    if (mListItemClickListener != null) {
                        mListItemClickListener.onListItemClick(joke);
                    }
                    break;
            }
            notifyDataSetChanged();
        }
    }
}
