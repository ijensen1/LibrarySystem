/**
 * Class to hold information about a DVD.
 */
public class DVD extends Borrowable{
    private String director, rating;
    private String[] actors;

    /**
     * Constructor to make a DVD object.
     * @param home the home library of the DVD.
     * @param title the title of the DVD.
     * @param genres the genres of the DVD.
     * @param rating the rating of the DVD.
     * @param director the director of the DVD.
     * @param actors the actors in the DVD.w
     */
    DVD(String home, String title, String[] genres, String rating, String director, String[] actors) {
        super(home, title, genres);
        this.rating = rating;
        this.director = director;
        this.actors = actors;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String[] getActors() {
        return actors;
    }

    public void setActors(String[] actors) {
        this.actors = actors;
    }
}
