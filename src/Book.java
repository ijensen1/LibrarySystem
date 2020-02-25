public class Book extends Borrowable{
    private String author;

    Book(String home, String title, String creator, String... genres) {
        super(home, title, genres);
    }
}
