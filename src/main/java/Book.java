/**
 * Class to hold information about a book.
 */
public class Book extends Borrowable{
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

    public boolean isHardcover() {
        return hardcover;
    }

    public void setHardcover(boolean hardcover) {
        this.hardcover = hardcover;
    }
}
