public class CD extends Borrowable{
    private String artist;

    CD(String home, String title, String[] genres, String artist, String rating){
        super(home, title, genres);
    }
}
