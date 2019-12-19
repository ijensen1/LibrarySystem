/**
 * To create a single book/dvd/cd.
 */
class Borrowable {
    private String home, //To hold the work's home
            title, //To hold the work's title
            creator, //To hold the work's creator
            genre1, //To hold the work's first genre
            genre2, //To hold the work's second genre
            inOut; //To hold whether or not the work is checked in or out
    private byte type;

    /**
     * Constructor to take in and set the tags to appropriate values.
     * @param title title of the work.
     * @param creator creator of the work.
     * @param genre1 first genre of the work.
     * @param genre2 second genre of the work.
     */
    Borrowable(String home, byte type, String title, String creator, String genre1, String genre2) {
        this.type = type;
        this.title = title;
        this.creator = creator;
        this.genre1 = genre1;
        this.genre2 = genre2;
        inOut = "in";
    }

    /**
     * Method to convert the Borrowable's tags into a single String.
     * @return a String of the Borrowable's tags.
     */
    String makeString(){
        return (home + Persistence.splitter +
                type + Persistence.splitter +
                title + Persistence.splitter +
                creator + Persistence.splitter +
                genre1 + Persistence.splitter +
                genre2 + Persistence.splitter +
                inOut);
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

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    String getCreator() {
        return creator;
    }

    void setCreator(String creator) {
        this.creator = creator;
    }

    String getGenre1() {
        return genre1;
    }

    void setGenre1(String genre1) {
        this.genre1 = genre1;
    }

    String getGenre2() {
        return genre2;
    }

    void setGenre2(String genre2) {
        this.genre2 = genre2;
    }

    String getInOut() {
        return inOut;
    }

    void setInOut(String inOut) {
        this.inOut = inOut;
    }
}
