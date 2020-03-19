import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Class to hold information about a book.
 */
public class Book extends Borrowable implements Jsonable {
    protected String author, isbn;
    protected boolean hardcover;

    /**
     * Constructor to make a Book object.
     * @param home the home library of the book.
     * @param title the title of the book.
     * @param genres the genres of the book.
     * @param author the author of the book.
     * @param isbn the ISBN number of the book.
     * @param hardcover whether or not the book is hardcover.
     */
    Book(String home, String title, String[] genres, String author, String isbn, boolean hardcover) {
        super(home, title, genres);
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

    public String[] getPeople(){
        return new String[]{author};
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
        json.put("title", this.getTitle());
        json.put("Home", this.getHome());
        json.put("Author", this.getAuthor());
        json.put("isbn", this.getIsbn());
        json.put("hardcover", this.isHardcover());
        json.put("genre", this.getGenres());
        json.toJson(writer);

    }


}
