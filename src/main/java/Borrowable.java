import java.util.ArrayList;
import java.util.List;

/**
 * To create a single book/dvd/cd.
 */
class Borrowable {
    private String home; //To hold the work's home
    private String title; //To hold the work's title
    private String inOut; //To hold whether or not the work is checked in or out
    private ArrayList<String> genres; //To hold the work's genres

    /**
     * Constructor to take in and set the tags to appropriate values.
     * @param home The home library of the work.
     * @param title title of the work.
     * @param genres the genres of the work.
     */
    Borrowable(String home, String title, ArrayList<String> genres) {
        this.home = home;
        this.title = title;
        this.genres = genres;
        inOut = "in";
    }

    public Borrowable() {

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

    /**
     * Class to determine whether or not two Borrowable objects are equal.
     */
    public boolean equals(Borrowable borrowable){
//        return String equality of their serializations
        return false;
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

    public List<String> getGenres() {
        return (List) genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    String getInOut() {
        return inOut;
    }

    void setInOut(String inOut) {
        this.inOut = inOut;
    }

    String[] getPeople(){
        return null;
    };

    @Deprecated
    public String makeString() {
        return null;
    }

}
