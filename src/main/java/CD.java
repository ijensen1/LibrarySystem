import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;
import com.github.cliftonlabs.json_simple.Jsoner;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Class to hold information about a CD.
 */
public class CD extends Borrowable implements Jsonable {
    private String producer, artist, rating;
    private String[] songs;

    /**
     * Constructor to make a CD object.
     * @param home the home library of the CD.
     * @param title the title of the CD (album title).
     * @param genres the genres of the CD.
     * @param producer the company that produced the CD.
     * @param artist the artist who made the CD.
     * @param rating the rating of the CD.
     * @param songs the songs included on the CD.
     */
    CD(String home, String title, ArrayList<String> genres, String producer, String artist, String rating, String[] songs){
        super("cds", home, title, genres);
        this.producer = producer;
        this.artist = artist;
        this.rating = rating;
        this.songs = songs;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String[] getSongs() {
        return songs;
    }

    public void setSongs(String[] songs) {
        this.songs = songs;
    }

    /**
     * Method to get a list of all people associated with the CD.
     * @return a String[] containing the producer and rtist.
     */
    public String[] getPeople() {
        return new String[]{this.producer, artist};
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
        json.put("type", this.getType());
        json.put("home", this.getHome());
        json.put("title", this.getTitle());
        json.put("genres", this.getGenres());
        json.put("producer", this.getProducer());
        json.put("artist", this.getArtist());
        json.put("rating", this.getRating());
        json.put("songs", this.getSongs());
        json.toJson(writer);

    }

    public static CD fromJson(Borrowable part, JsonObject obj) {
        if (part == null) {
            part = Borrowable.fromJson(null, obj); //Were we passed null? Kick it back up to Borrowable fromJson to have it fill in it's part.
        }
        String producer = obj.getString(Jsoner.mintJsonKey("producer", null));
        String artist = obj.getString(Jsoner.mintJsonKey("artist", null));
        String rating = obj.getString(Jsoner.mintJsonKey("rating", null));
        ArrayList<String> songs = obj.getCollection(Jsoner.mintJsonKey("songs", null));
        return new CD(part.getHome(), part.getTitle(), part.getGenres(), producer, artist, rating, songs.toArray(new String[0]));
    }
}
