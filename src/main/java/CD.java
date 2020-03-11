/**
 * Class to hold information about a CD.
 */
public class CD extends Borrowable{
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
    CD(String home, String title, String[] genres, String producer, String artist, String rating, String[] songs){
        super(home, title, genres);
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

    public String[] getPeople() {
        return new String[]{artist};
    }
}
