package nl.avans.cinetopia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import nl.avans.cinetopia.R;
import nl.avans.cinetopia.domain.Genre;
import nl.avans.cinetopia.domain.Movie;

/**
 * Adapter class for our popular movies RecyclerView.
 */
public class PopularMoviesRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;

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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }

        return viewHolder;

//        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item_main_movies, parent, false);
//        return new PopularMoviesViewHolder(v);
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.list_item_main_movies, parent, false);
        viewHolder = new PopularMoviesViewHolder(v1);
        return viewHolder;
    }

    /**
     * Binds movie data to the ViewHolder.
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // Retrieve current movie.
        Movie currentMovie = mMovies.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                final PopularMoviesViewHolder movieVH = (PopularMoviesViewHolder) holder;
                ArrayList<Genre> genres = currentMovie.getGenres();

                movieVH.mTextViewTitle.setText(currentMovie.getTitle());

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

                movieVH.mTextViewGenre.setText(genreString.toString());

                movieVH.mTextViewRating.setText(String.valueOf(currentMovie.getRating()));
                Picasso.get().load(currentMovie.getImageUrl()).fit().centerInside().into(movieVH.mImageViewPoster);
                Picasso.get().load("https://www.themoviedb.org/assets/2/v4/logos/208x226-stacked-green-9484383bd9853615c113f020def5cbe27f6d08a84ff834f41371f223ebad4a3c.png"
                ).fit().centerInside().into(movieVH.mImageViewTmdb);

                break;

            case LOADING:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mMovies == null ? 0 : mMovies.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == mMovies.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    /*
    Helpers
    ________________________________________________________________________________________________
    */

    public void add(Movie r) {
        mMovies.add(r);
        notifyItemInserted(mMovies.size() - 1);
    }

    public void addAll(List<Movie> movieResults) {
        for (Movie result : movieResults) {
            add(result);
        }
    }

    public void remove(Movie r) {
        int position = mMovies.indexOf(r);
        if (position > -1) {
            mMovies.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Movie());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = mMovies.size() - 1;
        Movie result = getItem(position);

        if (result != null) {
            mMovies.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Movie getItem(int position) {
        return mMovies.get(position);
    }

        /*
    View holders
    ________________________________________________________________________________________________
    */

    protected class PopularMoviesViewHolder extends RecyclerView.ViewHolder {

        // The Views in our list item.
        ImageView mImageViewPoster;
        ImageView mImageViewTmdb;
        TextView mTextViewTitle;
        TextView mTextViewGenre;
        TextView mTextViewRating;
        ProgressBar mProgress;

        // Constructor.
        PopularMoviesViewHolder(View itemView) {
            super(itemView);
            mImageViewPoster = itemView.findViewById(R.id.iv_movie_list_picture);
            mImageViewTmdb = itemView.findViewById(R.id.iv_tmdb_logo);
            mTextViewTitle = itemView.findViewById(R.id.tv_movie_list_title);
            mTextViewGenre = itemView.findViewById(R.id.tv_movie_list_genre);
            mTextViewRating = itemView.findViewById(R.id.tv_movie_list_rating);
            mProgress = itemView.findViewById(R.id.movie_progress);


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

    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }
}
