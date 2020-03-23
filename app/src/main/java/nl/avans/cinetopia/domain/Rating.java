package nl.avans.cinetopia.domain;

public class Rating {
    private static final String TAG = Rating.class.getSimpleName();

    private int rating;

    public Rating(int rating){
        if(checkRating(rating)){
            this.rating = rating;
        }else{
            this.rating = -1;
        }
    }

    private boolean checkRating(int rating){
        return rating<=10 && rating>=0;
    }
}
