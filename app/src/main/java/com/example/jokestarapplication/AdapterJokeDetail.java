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

public class AdapterJokeDetail extends RecyclerView.Adapter<AdapterJokeDetail.CommentViewHolder> {

    private List<Comment> mItems;
    private Context mContext;
    private ListItemClickListener mListItemClickListener;

    public AdapterJokeDetail(List<Comment> comments) {
        mItems = comments;
    }

    @NonNull
    @NotNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.list_item_comment, parent, false);

        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterJokeDetail.CommentViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return (mItems == null) ? 0 : mItems.size();
    }

    interface ListItemClickListener {
        void onListItemClick(Comment item);
    }

    public void setOnListItemClickListener(ListItemClickListener listItemClickListener) {
        mListItemClickListener = listItemClickListener;
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvText, tvVotes;
        private Button btVoteUp, btVoteDown;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.tvCommentText);
            tvVotes = itemView.findViewById(R.id.tvCommentVotes);
            btVoteUp = itemView.findViewById(R.id.btCommentUp);
            btVoteDown = itemView.findViewById(R.id.btCommentDown);

            btVoteUp.setOnClickListener(this);
            btVoteDown.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }


        public void bind(int position) {
            tvText.setText(mItems.get(position).getText());
            tvVotes.setText(Integer.toString(mItems.get(position).getVotes()));
        }

        @Override
        public void onClick(View v) {

            int clickedIndex = getAdapterPosition();
            Comment comment = mItems.get(clickedIndex);
            switch (v.getId()) {
                case R.id.btCommentUp:
                    comment.CommentVoteUp();
                    break;
                case R.id.btCommentDown:
                    comment.CommentVoteDown();
                    break;
            }
            notifyDataSetChanged();
        }
    }
}
