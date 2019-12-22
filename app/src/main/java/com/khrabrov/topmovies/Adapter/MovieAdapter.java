package com.khrabrov.topmovies.Adapter;

import android.annotation.SuppressLint;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.khrabrov.topmovies.Model.Result;
import com.khrabrov.topmovies.R;
import com.squareup.picasso.Picasso;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView poster;
        private TextView averageVote;
        private TextView dateRelease;
        private TextView overView;

        @SuppressLint("ClickableViewAccessibility")
        private ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view);
            poster = itemView.findViewById(R.id.poster);
            averageVote = itemView.findViewById(R.id.average_vote);
            dateRelease = itemView.findViewById(R.id.release_date);
            overView = itemView.findViewById(R.id.overview);

            //Прокрутка внутри recycler
            overView.setOnTouchListener((v, event) -> {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            });

            overView.setMovementMethod(new ScrollingMovementMethod());
        }
    }

    private List<Result> listOfMovies;

    public MovieAdapter(List<Result> listOfMovies) {
        this.listOfMovies = listOfMovies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movies_card, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(listOfMovies.get(position).getTitle());
        holder.averageVote.setText(listOfMovies.get(position).getVoteAverage().toString());
        holder.dateRelease.setText(listOfMovies.get(position).getReleaseDate());
        holder.overView.setText(listOfMovies.get(position).getOverview());

        try {
            Picasso.get().load("https://image.tmdb.org/t/p/w500/" + listOfMovies.get(position).getPosterPath()).into(holder.poster);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return listOfMovies.size();
    }


}
