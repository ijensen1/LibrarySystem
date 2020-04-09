import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;
import com.github.cliftonlabs.json_simple.Jsoner;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Class to hold information about a book.
 */
public class Book extends Borrowable implements Jsonable {
    private String author, isbn;
    private boolean hardcover;

    /**
     * Constructor to make a Book object.
     * @param home the home library of the book.
     * @param title the title of the book.
     * @param genres the genres of the book.
     * @param author the author of the book.
     * @param isbn the ISBN number of the book.
     * @param hardcover whether or not the book is hardcover.
     */
    Book(String home, String title, ArrayList<String> genres, String author, String isbn, boolean hardcover) {
        super("books", home, title, genres);
        this.author = author;
        this.isbn = isbn;
        this.hardcover = hardcover;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Boolean isHardcover() {
        return hardcover;
    }

    public void setHardcover(boolean hardcover) {
        this.hardcover = hardcover;
    }

    /**
     * Method to get a list of all people associated with the book.
     * @return a String[] containing the author.
     */
    public String[] getPeople(){
        return new String[]{author};
    }

    /**
     * Shorthand method that then calls the main toJson after doing a little setup.
     * @return A Json formatted String of the Book's data.
     */
    @Override
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
    @Override
    public void toJson(Writer writer) throws IOException {

        final JsonObject json = new JsonObject();
        json.put("type", this.getType());
        json.put("title", this.getTitle());
        json.put("home", this.getHome());
        json.put("author", this.getAuthor());
        json.put("isbn", this.getIsbn());
        json.put("isHardcover", this.isHardcover());
        json.put("genres", this.getGenres());
        json.toJson(writer);

    }


    public static Book fromJson(Borrowable part, JsonObject obj) {
        if (part == null) {
            part = Borrowable.fromJson(null, obj); //Were we passed null? Kick it back up to Borrowable fromJson to have it fill in it's part.
        }
        String author = obj.getString(Jsoner.mintJsonKey("author", null));
        String isbn = obj.getString(Jsoner.mintJsonKey("isbn", null));
        boolean isHardcover = obj.getBoolean(Jsoner.mintJsonKey("isHardcover", null));
        return new Book(part.getHome(), part.getTitle(), part.getGenres(), author, isbn, isHardcover);
    }


}
