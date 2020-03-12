/**
 * Class to hold information about a picture book.
 */
public class PictureBook extends Book {
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
    PictureBook(String home, String title, String[] genres, String author, String isbn, boolean hardcover, String illustrator, boolean isPopUpBook) {
        super(home, title, genres, author, isbn, hardcover);
        this.illustrator = illustrator;
        this.isPopUpBook = isPopUpBook;
    }

    public String getIllustrator() {
        return illustrator;
    }

    public void setIllustrator(String illustrator) {
        this.illustrator = illustrator;
    }

    public boolean isPopUpBook() {
        return isPopUpBook;
    }

    public void setPopUpBook(boolean popupBook) {
        isPopUpBook = popupBook;
    }

    public String[] getPeople(){
        return new String[]{author, illustrator};
    }
}
