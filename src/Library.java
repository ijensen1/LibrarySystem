import java.util.Arrays;

/**
 * Class to handle inventory of a specific library, all method inputs are case in-sensitive. Uses 2d arrays to hold information and has methods for data query.
 */
public class Library {
    final byte BOOKS = 0, DVDS = 1, CDS = 2;

    //Variables for library data
    private Borrowable[][] inventory = new Borrowable[3][];   //arrays to hold data on library's books, DVDs, and CDs
    private String libraryName;

    //error messages meant for user, or Jack, depending on how he implements things
    private final String errorInvalidType = "Invalid entry type. Valid entry types are \"book\", \"dvd\", and \"cd\" (case insensitive)";
    private final String errorEntryNotFound = "Could not locate any entries containing that data";

    /**
     * Constructor, populates arrays with data saved in files for that library.
     * @param libraryName name of the library.
     */
    public Library(String libraryName) {
        //Set library's name to whatever was entered.
        this.libraryName = libraryName;

        //Loading possible files that could exist for library items.
        inventory[BOOKS] = Persistence.loadBorrowables(libraryName + "/books.txt");
        inventory[DVDS] = Persistence.loadBorrowables(libraryName + "/dvds.txt");
        inventory[CDS] = Persistence.loadBorrowables(libraryName + "/cds.txt");
    }

    public String getLibraryName() {
        return libraryName;
    }

    /**
     * Add an entry to a library's inventory.
     * @param type book, cd, or dvd.
     * @param title title of the entry.
     * @param genre1 a genre of the entry.
     * @param genre2 a second genre of the entry.
     */
    void add(byte type, String title, String creator, String genre1, String genre2){
        inventory[type] = Arrays.copyOf(inventory[type], inventory[type].length + 1);
        inventory[type][inventory[type].length - 1] = new Borrowable(libraryName, type, title, creator, genre1, genre2); //Make a Borrowable of the new entry and put at end of books
    }

    /**
     * Overload of add to allow books with just 1 genre to be easily added.
     * @param type book, cd, or dvd.
     * @param title title of the entry.
     * @param genre the books genre.
     */
    public void add(byte type, String title, String creator, String genre){
        add(type, title, creator, genre, "noGenre2");
    }

    /**
     * Remove an entry from a library's inventory. Will only remove entries with an in/out tag of "in".
     * @param type book, cd, or dvd.
     * @param entry the borrowable.
     */
    public void remove(byte type, Borrowable entry) {
        Borrowable[] removed; //to hold data in removeSearch but exclude the entry being removed
        Borrowable removing;

        //checking if tags equal what was put in to be removed and that entry is in the library for each entry
        for (int i = 0; i < inventory[type].length; i++){
            if (inventory[type][i] == entry) {
                //fill out tempArray with entries' data (minus entry being removed) after right entry is found
                removed = new Borrowable[inventory[type].length - 1];
                for (int upToRemoved = 0; upToRemoved < i; upToRemoved++)
                    removed[upToRemoved] = inventory[type][upToRemoved];
                for (int afterRemoved = i + 1; afterRemoved < inventory[type].length; afterRemoved++)
                    removed[afterRemoved - 1] = inventory[type][afterRemoved];
                //update inventory
                inventory[type] = removed;
                return;
            }
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
