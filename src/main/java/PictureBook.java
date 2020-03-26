import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Class to hold information about a picture book.
 */
public class PictureBook extends Book implements Jsonable {
    protected String illustrator;
    protected boolean isPopUpBook;

    /**
     * Constructor to make a Book object.
     * @param home the home library of the book.
     * @param title the title of the book.
     * @param genres the genres of the book.
     * @param author the author of the book.
     * @param isbn the ISBN number of the book.
     * @param hardcover whether or not the book is hardcover.
     * @param illustrator the picture book's illustrator.
     * @param isPopUpBook whether or not the picture book is also a pop-up book.
     */
    PictureBook(String home, String title, ArrayList<String> genres, String author, String isbn, boolean hardcover, String illustrator, boolean isPopUpBook) {
        super(home, title, genres, author, isbn, hardcover);
        this.illustrator = illustrator;
        this.isPopUpBook = isPopUpBook;
    }

    public PictureBook(){
        super();
    }

    public String getIllustrator() {
        return illustrator;
    }

    public void setIllustrator(String illustrator) {
        this.illustrator = illustrator;
    }

    public Boolean isPopUpBook() {
        return isPopUpBook;
    }

    public void setPopUpBook(boolean popupBook) {
        isPopUpBook = popupBook;
    }

    public String[] getPeople(){
        return new String[]{this.getAuthor(), illustrator};
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
        json.put("author", this.getAuthor());
        json.put("isbn", this.getIsbn());
        json.put("isHardcover", this.isHardcover());
        json.put("illustrator", this.getIllustrator());
        json.put("isPopUpBook", this.isPopUpBook());
        json.toJson(writer);

    }
}
