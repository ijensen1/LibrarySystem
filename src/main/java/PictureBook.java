import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;
import com.github.cliftonlabs.json_simple.Jsoner;

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
        this.setType("picturebooks");
        this.illustrator = illustrator;
        this.isPopUpBook = isPopUpBook;
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

    /**
     * Method to get a list of all people associated with the picture book.
     * @return a String[] containing the author and illustrator.
     */
    public String[] getPeople(){
        return new String[]{this.getAuthor(), illustrator};
    }

    /**
     * Shorthand method that then calls the main toJson after doing a little setup.
     * @return A Json formatted String of the PictureBook's data.
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

    public static PictureBook fromJson(Borrowable part, JsonObject obj) {
        if (part == null) {
            part = Book.fromJson(null, obj); //Were we passed null? Kick it back up to Book fromJson to have it fill in it's part.
        }
        Book partBook = (Book) part;
        boolean isPopUpBook = obj.getBoolean(Jsoner.mintJsonKey("isPopUpBook", null));
        String illustrator = obj.getString(Jsoner.mintJsonKey("illustrator", null));
        return new PictureBook(part.getHome(), part.getTitle(), part.getGenres(), partBook.getAuthor(), partBook.getIsbn(), partBook.isHardcover(), illustrator, isPopUpBook);
    }
}
