import java.util.ArrayList;

/**
 * To create a single book/dvd/cd.
 */
class Borrowable implements Persistable{
    private String home, //To hold the work's home
            title, //To hold the work's title
            inOut, //To hold whether or not the work is checked in or out
            genres[]; //To hold the work's genres

    Borrowable(){}

    /**
     * Constructor to take in and set the tags to appropriate values.
     * @param home The home library of the work.
     * @param title title of the work.
     * @param genres the genres of the work.
     */
    Borrowable(String home, String title, String... genres) {
        this.home = home;
        this.title = title;
        this.genres = genres;
        inOut = "in";
    }

    /**
     * Updates the inOut field of the Borrowable to "out" if it is currently "in".
     */
    public void checkOut(){
        if (inOut.equalsIgnoreCase("in")) {
            inOut = "out";
        } else {
            System.out.println("Entry already checked out.");
        }
    }

    /**
     * Updates the inOut field of the Borrowable to "in" if it is currently "out".
     */
    public void checkIn(){
        if (inOut.equalsIgnoreCase("out")) {
            inOut = "in";
        } else {
            System.out.println("Entry already checked in.");
        }
    }

    public String getHome() {
        return home;
    }

    String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    String getInOut() {
        return inOut;
    }

    void setInOut(String inOut) {
        this.inOut = inOut;
    }

    @Override
    public String makeString() {
        return null;
    }
}
