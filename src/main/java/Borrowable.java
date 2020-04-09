import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Class that acts as a parent for items of the library's inventory.
 */
class Borrowable {
    private String type; //What type of Borrowable the object is, used in Json serialization and deserialization.

    private String home; //To hold the work's home
    private String title; //To hold the work's title
    private String inOut; //To hold whether or not the work is checked in or out
    private ArrayList<String> genres; //To hold the work's genres

    /**
     * Constructor to take in and set the tags to appropriate values.
     * @param type what type of Borrowable it is.
     * @param home The home library of the work.
     * @param title title of the work.
     * @param genres the genres of the work.
     */
    public Borrowable(String type, String home, String title, ArrayList<String> genres) {
        this.type = type;
        this.home = home;
        this.title = title;
        this.genres = genres;
        inOut = "in";
    }

    /**
     * Updates the inOut field of the Borrowable to "out" if it is currently "in".
     */
    public void checkOut(){
        if (inOut.equalsIgnoreCase("in")) {
            inOut = "out";
        } else {
            System.out.println("Entry already checked out.");
        }
    }

    /**
     * Updates the inOut field of the Borrowable to "in" if it is currently "out".
     */
    public void checkIn(){
        if (inOut.equalsIgnoreCase("out")) {
            inOut = "in";
        } else {
            System.out.println("Entry already checked in.");
        }
    }

    public String getHome() {
        return home;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    String getInOut() {
        return inOut;
    }

    void setInOut(String inOut) {
        this.inOut = inOut;
    }

    String[] getPeople(){
        return null;
    }

    /**
     * Shorthand method that then calls the main toJson after doing a little setup.
     * @return A Json formatted String of the Borrowable's data.
     */
    public String toJson(){

        final StringWriter writable = new StringWriter();
        try{
            this.toJson(writable);
        } catch (final IOException e) {

        }
        return writable.toString();

    }

    /**
     * Main toJson method that saves class data to the given Writer object using Json formatting.
     * @param writer the Writer object to store the data to.
     * @throws IOException Passes up writer's IOException.
     */
    public void toJson(Writer writer) throws IOException {

        final JsonObject json = new JsonObject();
        json.put("home", this.getHome());
        json.put("title", this.getTitle());
        json.put("inOut", this.getInOut());
        json.put("genres", this.getGenres());
        json.toJson(writer);

    }

    /**
     * Static function to construct a Borrowable from a JSON object
     * @param part Unused here. Look at Book.fromJson
     * @see Book
     * @param obj The JsonObject to construct the Borrowable from
     * @return the Borrowable returned
     */
    public static Borrowable fromJson(Borrowable part, JsonObject obj) {
        String type = obj.getString(Jsoner.mintJsonKey("type", null));
        String home = obj.getString(Jsoner.mintJsonKey("home", null));
        String title = obj.getString(Jsoner.mintJsonKey("title", null));
        ArrayList<String> genres = obj.getCollection(Jsoner.mintJsonKey("genres", null));
        String inOut = obj.getString(Jsoner.mintJsonKey("inOut", null));
        Borrowable returned = new Borrowable(type, home, title, genres);
        returned.setInOut(inOut);
        return returned;
    }

}
