package nl.avans.cinetopia.domain;

public class Rating {
    private static final String TAG = Rating.class.getSimpleName();

    private double rating;

    public Rating(double rating){
        if(checkRating(rating)){
            this.rating = rating;
        }else{
            this.rating = -1;
        }
    }

    private boolean checkRating(double rating){
        return rating<=10 && rating>=1.0;
    }

    public static String getTAG() {
        return TAG;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
