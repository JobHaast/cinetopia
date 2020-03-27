package nl.avans.cinetopia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import nl.avans.cinetopia.R;
import nl.avans.cinetopia.domain.Genre;
import nl.avans.cinetopia.domain.Movie;

/**
 * Adapter class for our popular movies RecyclerView.
 */
public class PopularMoviesRecyclerViewAdapter extends RecyclerView.Adapter<PopularMoviesRecyclerViewAdapter.PopularMoviesViewHolder> {

    // Global attributes.
    private Context mContext;
    private ArrayList<Movie> mMovies;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public PopularMoviesRecyclerViewAdapter(Context context, ArrayList<Movie> movies) {
        this.mContext = context;
        this.mMovies = movies;
    }

    /**
     * Inflates a MovieViewHolder we can bind data to.
     */
    @NonNull
    @Override
    public PopularMoviesRecyclerViewAdapter.PopularMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item_main_movies, parent, false);
        return new PopularMoviesViewHolder(v);
    }

    /**
     * Binds movie data to the ViewHolder.
     */
    @Override
    public void onBindViewHolder(@NonNull PopularMoviesRecyclerViewAdapter.PopularMoviesViewHolder holder, int position) {
        // Retrieve current movie.
        Movie currentMovie = mMovies.get(position);

        // Retrieve the movie's data.
        String title = currentMovie.getTitle();
        ArrayList<Genre> genres = currentMovie.getGenres();
        double rating = currentMovie.getRating();
        String imageUrl = currentMovie.getImageUrl();

        // Build the genre string.
        StringBuilder genreString = new StringBuilder();

        for (int i = 0; i < genres.size(); i++) {
            String name = genres.get(i).getName();

            if (i < genres.size() - 1) {
                genreString.append(name).append(", ");
            } else {
                genreString.append(name);
            }
        }

        // Bind the data to the Views of our holder.
        holder.mTextViewTitle.setText(title);
        holder.mTextViewGenre.setText(genreString.toString());
        holder.mTextViewRating.setText(String.valueOf(rating));
        Picasso.get().load(imageUrl).fit().centerInside().into(holder.mImageViewPoster);
        Picasso.get().load("https://www.themoviedb.org/assets/2/v4/logos/208x226-stacked-green-9484383bd9853615c113f020def5cbe27f6d08a84ff834f41371f223ebad4a3c.png"
        ).fit().centerInside().into(holder.mImageViewTmdb);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public class PopularMoviesViewHolder extends RecyclerView.ViewHolder {

        // The Views in our list item.
        ImageView mImageViewPoster;
        ImageView mImageViewTmdb;
        TextView mTextViewTitle;
        TextView mTextViewGenre;
        TextView mTextViewRating;

        // Constructor.
        PopularMoviesViewHolder(View itemView) {
            super(itemView);
            mImageViewPoster = itemView.findViewById(R.id.iv_movie_list_picture);
            mImageViewTmdb = itemView.findViewById(R.id.iv_tmdb_logo);
            mTextViewTitle = itemView.findViewById(R.id.tv_movie_list_title);
            mTextViewGenre = itemView.findViewById(R.id.tv_movie_list_genre);
            mTextViewRating = itemView.findViewById(R.id.tv_movie_list_rating);

            // Set an OnClickListener on the MovieViewHolder so we can click on each list item.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
