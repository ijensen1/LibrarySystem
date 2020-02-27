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
     * Returns the indexes of all instances of an entry.
     * @param type book, dvd, or cd.
     * @param title title of entry being looked for.
     * @param creator creator of entry you want to find (author, artist, director).
     * @return int[] holding indexes of every instance of the wanted object (for if multiple copies exist).
     */
    public int[] findIndex(byte type, String title, String creator){
        int[] results = new int[0]; //to hold search results and tempArray used for updating results

        //check each entry for if it has the selected creator
        for (int entry = 0; entry < inventory[type].length; entry++) {
            if ((inventory[type][entry].getTitle().equalsIgnoreCase(title)) && (inventory[type][entry].getCreator().equalsIgnoreCase(creator))) {
                //updating results if there is a match
                results = Arrays.copyOf(results, 1);
                results[results.length - 1] = entry;
            }
        }
        return results;
    }

    /**
     * Gets entry data from an index number.
     * @param type book, cd, or dvd.
     * @param entry index of wanted entry.
     * @return the wanted borrowable object, null if out of bounds.
     */
    public Borrowable getIndex(byte type, int entry){
        if (inventory[type].length > entry)
            return inventory[type][entry];
        else
            return null;
    }

    /**
     * Search inventory based on type and title.
     * @param type book, dvd, or cd.
     * @param title title of what you're searching for.
     * @return an array of entries that meet the search criteria.
     */
    public Borrowable[] searchName(byte type, String title){
        Borrowable[] results = new Borrowable[0]; //to hold search results

        //check each entry for if it has the wanted title
        for (int entry = 0; entry < inventory[type].length; entry++) {
            if (title.equalsIgnoreCase(inventory[type][entry].getTitle())) {
                //updating results
                results = Arrays.copyOf(results, results.length + 1);
                results[results.length - 1] = inventory[type][entry];
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

        //check each entry for if it has the wanted creator
        for (int entry = 0; entry < inventory[type].length; entry++) {
            if (creator.equalsIgnoreCase(inventory[type][entry].getCreator())) {
                //updating results
                results = Arrays.copyOf(results, results.length + 1);
                results[results.length - 1] = inventory[type][entry];
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
        for (int entry = 0; entry < inventory[type].length; entry++) {
            if (genre.equalsIgnoreCase(inventory[type][entry].getGenre1()) || genre.equalsIgnoreCase(inventory[type][entry].getGenre2())) {
                //updating results
                results = Arrays.copyOf(results, results.length + 1);
                results[results.length - 1] = inventory[type][entry];
            }
        }
        return results;
    }

    /**
     * Writes all arrays to their appropriate files.
     */
    public void save() {
        Persistence.saveToFile(libraryName + "/books.txt", inventory[0]);
        Persistence.saveToFile(libraryName + "/dvds.txt", inventory[1]);
        Persistence.saveToFile(libraryName + "/cds.txt", inventory[2]);
    }
}
