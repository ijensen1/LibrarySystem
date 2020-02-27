public class Book extends Borrowable{
    private String author;

    Book(String home, String title, String[] genres, String author, String isbn, boolean hardcover) {
        super(home, title, genres);
    }
}
