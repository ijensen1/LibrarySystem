import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class to hold information about a DVD.
 */
public class DVD extends Borrowable implements Jsonable {
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
    DVD(String home, String title, ArrayList<String> genres, String rating, String director, String[] actors) {
        super(home, title, genres);
        this.rating = rating;
        this.director = director;
        this.actors = actors;
    }

    public DVD(){
        super();
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

    public String[] getPeople(){
        String[] people = new String[1 + actors.length];
        people[0] = director;
        for (int i = 1; i < people.length; i++){
            people[i] = actors[i - 1];
        }
        return people;
    }

    @Override
    public String toJson(){

        final StringWriter writable = new StringWriter();
        try{
            this.toJson(writable);
        } catch (final IOException e) {

        }
        return writable.toString();

    }

    @Override
    public void toJson(Writer writer) throws IOException {

        final JsonObject json = new JsonObject();
        json.put("home", this.getHome());
        json.put("title", this.getTitle());
        json.put("genres", this.getGenres());
        json.put("director", this.getDirector());
        json.put("rating", this.getRating());
        json.put("actors", this.getActors());
        json.toJson(writer);

    }
}
