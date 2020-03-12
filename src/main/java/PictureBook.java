/**
 * Class to hold information about a picture book.
 */
public class PictureBook extends Book {
    protected String illustrator;

    /**
     * Constructor to make a Book object.
     * @param home the home library of the book.
     * @param title the title of the book.
     * @param genres the genres of the book.
     * @param author the author of the book.
     * @param isbn the ISBN number of the book.
     * @param hardcover whether or not the book is hardcover.
     * @param illustrator the picture book's illustrator.
     */
    PictureBook(String home, String title, String[] genres, String author, String isbn, boolean hardcover, String illustrator) {
        super(home, title, genres, author, isbn, hardcover);
        this.illustrator = illustrator;
    }

    public String getIllustrator() {
        return illustrator;
    }

    public void setIllustrator(String illustrator) {
        this.illustrator = illustrator;
    }

    public String[] getPeople(){
        return new String[]{author, illustrator};
    }
}
