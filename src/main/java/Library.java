import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class to handle inventory of a specific library, all method inputs are case in-sensitive. Uses 2d arrays to hold information and has methods for data query.
 */
public class Library {
    //Variables for library data
    private String libraryName;
    private ArrayList<Book> books = new ArrayList<Book>(0);
    private ArrayList<CD> cds = new ArrayList<CD>(0);
    private ArrayList<DVD> dvds = new ArrayList<DVD>(0);

    //error messages meant for user, or Jack, depending on how he implements things
    private final String errorInvalidType = "Invalid entry class, valid entry classes are Book, CD, and DVD";
    private final String errorEntryNotFound = "Could not locate any entries containing that data";

    /**
     * Constructor, populates arrays with data saved in files for that library.
     * @param libraryName name of the library.
     */
    public Library(String libraryName) {
        //Set library's name to whatever was entered.
        this.libraryName = libraryName;

//        Deserialize from files here *********************
    }

    public String getLibraryName() {
        return libraryName;
    }

    /**
     * Add a book to the library.
     * @param borrowable a Borrowable object to add to the library's inventory.
     */
    void add(Borrowable borrowable) throws Exception {
        if (borrowable instanceof Book){
            books.add((Book) borrowable);
        } else if (borrowable instanceof CD){
            cds.add((CD) borrowable);
        } else if (borrowable instanceof  DVD){
            dvds.add((DVD) borrowable);
        } else {
            throw new Exception(errorInvalidType);
        }
    }

    /**
     * Remove an entry from a library's inventory. Will only remove entries with an in/out tag of "in".
     * @param borrowable the Borrowable to remove from the inventory.
     */
    public void remove(Borrowable borrowable) throws Exception {
        if (borrowable instanceof Book){
            books.remove(borrowable);
        } else if (borrowable instanceof CD){
            cds.remove(borrowable);
        } else if (borrowable instanceof DVD){
            dvds.remove(borrowable);
        } else {
            throw new Exception(errorInvalidType);
        }
    }

    /**
     * Search inventory based on type and title.
     * @param type book, dvd, or cd.
     * @param title title of what you're searching for.
     * @return an array of entries that meet the search criteria.
     */
    public Borrowable[] searchName(byte type, String title){
        Borrowable[] results = new Borrowable[0]; //to hold search results
        for (Borrowable item : this.cds) {
            if (title.equalsIgnoreCase(item.getTitle())) {
                results = Arrays.copyOf(results, results.length + 1);
                results[results.length - 1] = item;
            }
        }
        for (Borrowable item : this.dvds) {
            if (title.equalsIgnoreCase(item.getTitle())) {
                results = Arrays.copyOf(results, results.length + 1);
                results[results.length - 1] = item;
            }
        }
        for (Borrowable item : this.books) {
            if (title.equalsIgnoreCase(item.getTitle())) {
                results = Arrays.copyOf(results, results.length + 1);
                results[results.length - 1] = item;
            }
        }
        return results;
    }

    /**
     * Search a library's inventor based on type and creator.
     * @param type book, dvd, or cd.
     * @param creator creator of entry you want to find (author, artist, director).
     * @return an array of Borrowables of works of that creator.
     */
    public Borrowable[] searchCreator(byte type, String creator){
        Borrowable[] results = new Borrowable[0]; //to hold search results
        for (CD item : this.cds) {
            if (creator.equalsIgnoreCase(item.getArtist())) {
                results = Arrays.copyOf(results, results.length + 1);
                results[results.length - 1] = item;
            }
        }
        for (DVD item : this.dvds) {
            if (creator.equalsIgnoreCase(item.getDirector())) {
                results = Arrays.copyOf(results, results.length + 1);
                results[results.length - 1] = item;
            }
        }
        for (Book item : this.books) {
            if (creator.equalsIgnoreCase(item.getAuthor())) {
                results = Arrays.copyOf(results, results.length + 1);
                results[results.length - 1] = item;
            }
        }
        return results;
    }

    /**
     * Search inventory based on type and genre.
     * @param type book, dvd, or cd.
     * @param genre genre being searched for.
     * @return an array of Borrowables of works of that genre.
     */
    public Borrowable[] searchGenre(byte type, String genre) {
        Borrowable[] results = new Borrowable[0]; //to hold search results
        //check each entry for if it has the wanted genre
        for (CD item : this.cds) {
            for (String itemgenre : item.getGenres()) {
                if (genre.equalsIgnoreCase(itemgenre)) {
                    results = Arrays.copyOf(results, results.length + 1);
                    results[results.length - 1] = item;
                }
            }
        }
        for (DVD item : this.dvds) {
            for (String itemgenre : item.getGenres()) {
                if (genre.equalsIgnoreCase(itemgenre)) {
                    results = Arrays.copyOf(results, results.length + 1);
                    results[results.length - 1] = item;
                }
            }
        }
        for (Book item : this.books) {
            for (String itemgenre : item.getGenres()) {
                if (genre.equalsIgnoreCase(itemgenre)) {
                    results = Arrays.copyOf(results, results.length + 1);
                    results[results.length - 1] = item;
                }
            }
        }

        return results;
    }

    /**
     * Writes all arrays to their appropriate files.
     */
    public void save() {
        Persistence.saveToFile(libraryName + "/books.txt", books.toArray(new Borrowable[0]));
        Persistence.saveToFile(libraryName + "/dvds.txt", dvds.toArray(new Borrowable[0]));
        Persistence.saveToFile(libraryName + "/cds.txt", cds.toArray(new Borrowable[0]));
    }
}
