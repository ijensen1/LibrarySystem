import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

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
        String[][] tempArray; //temporary array for holding a modified array with the new value added to it

        inventory[type] = Arrays.copyOf(inventory[type], inventory[type].length + 1);
        inventory[type][inventory[type].length - 1] = new Borrowable(type, title, creator, genre1, genre2); //Make a Borrowable of the new entry and put at end of books
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
     * @param title title of entry.
     * @param creator creator of entry you want to remove (author, artist, director).
     */
    public Borrowable remove(byte type, String title, String creator) {
        Borrowable[] removed; //to hold data in removeSearch but exclude the entry being removed
        Borrowable removing;

        //checking if tags equal what was put in to be removed and that entry is in the library for each entry
        for (int entry = 0; entry < inventory[type].length; entry++){
            if (inventory[type][entry].getTitle().equalsIgnoreCase(title) &&
                    inventory[type][entry].getCreator().equalsIgnoreCase(creator) &&
                    inventory[type][entry].getInOut().equalsIgnoreCase("in")) {
                removing = inventory[type][entry];
                //fill out tempArray with entries' data (minus entry being removed) after right entry is found
                removed = new Borrowable[inventory[type].length - 1];
                for (int upToRemoved = 0; upToRemoved < entry; upToRemoved++)
                    removed[upToRemoved] = inventory[type][upToRemoved];
                for (int afterRemoved = entry + 1; afterRemoved < inventory[type].length; afterRemoved++)
                    removed[afterRemoved - 1] = inventory[type][afterRemoved];
                //change String[][] for type to the updated catalog
                inventory[type] = removed;
                return removing;
            }
        }
        return null;
    }

    /**
     * Returns the indexes of all instances of an entry.
     * @param type book, dvd, or cd.
     * @param title title of entry being looked for.
     * @param creator creator of entry you want to find (author, artist, director).
     * @return int[] holding indexes of every instance (for if multiple copies exist).
     */
    public int[] findIndex(byte type, String title, String creator){
        int[] results = new int[0]; //to hold search results and tempArray used for updating results

        //check each entry for if it has the selected creator
        for (int entry = 0; entry < .length; entry++) {
            if ((search[entry].getTitle().equalsIgnoreCase(title)) && (search[entry].getCreator().equalsIgnoreCase(creator))) {
                //filling out tempArray with results data + data for newest entry
                tempArray = new int[results.length + 1];
                for (int i = 0; i < results.length; i++)
                    tempArray[i] = results[i];
                tempArray[results.length] = entry;
                //updating results
                results = tempArray;
            }
        }
        return results;
    }

    /**
     * Gets entry data from an index number.
     * @param type book, cd, or dvd (case sensitive).
     * @param entry index of wanted entry.
     * @return entry data as a String[].
     */
    public Borrowable getIndex(String type, int entry){
        if (type.equalsIgnoreCase("book"))
            return books[entry];
        else if (type.equalsIgnoreCase("dvd"))
            return dvds[entry];
        else if (type.equalsIgnoreCase("cd"))
            return cds[entry];
        else
            System.out.println(errorInvalidType);
        return null;
    }

    /**
     * Gets a single tag of a single entry.
     * @param type book, dvd, or cd.
     * @param entry index of entry.
     * @param tag index of wanted tag.
     * @return a String holding the tag's value.
     */
    public String getTag(String type, int entry, int tag){
        if (type.equalsIgnoreCase("book"))
            return books[entry][tag];
        else if (type.equalsIgnoreCase("dvd"))
            return dvds[entry][tag];
        else if (type.equalsIgnoreCase("cd"))
            return cds[entry][tag];
        else{
            System.out.println(errorInvalidType);
            return null;
        }
    }

    /**
     * Search inventory based on type and title.
     * @param type book, dvd, or cd.
     * @param title title of what you're searching for.
     * @return a String[][] containing the data of all entries with that title.
     */
    public String[][] searchName(String type, String title){
        String[][] searchingThrough; //to hold type's String[][] for searching through
        String[][] results = new String[0][tagCount], tempArray; //to hold search results and tempArray used for updating results

        //putting appropriate String[][] in searchingThrough[][]
        if (type.equalsIgnoreCase("book"))
            searchingThrough = books;
        else if (type.equalsIgnoreCase("dvd"))
            searchingThrough = dvds;
        else if (type.equalsIgnoreCase("cd"))
            searchingThrough = cds;
        else {
            System.out.println(errorInvalidType);
            return null;
        }

        //check each entry for if it has the selected creator
        for (int entry = 0; entry < searchingThrough.length; entry++) {
            if (title.equalsIgnoreCase(searchingThrough[entry][titlePos])) {
                //filling out tempArray with results data + data for newest entry
                tempArray = new String[results.length + 1][tagCount];
                for (int i = 0; i < results.length; i++)
                    tempArray[i] = results[i];
                tempArray[results.length] = searchingThrough[entry];
                //updating results
                results = tempArray;
            }
        }
        return results;
    }

    /**
     * Search a library's inventor based on type and creator.
     * @param type book, dvd, or cd.
     * @param creator creator of entry you want to find (author, artist, director).
     * @return String[][] of all works by that creator.
     */
    public String[][] searchCreator(String type, String creator){
        String[][] searchingThrough; //to hold the appropriate String[][] to search through
        String[][] results = new String[0][tagCount], tempArray; //to hold search results and tempArray used for updating results

        //putting appropriate String[][] in searchingThrough[][]
        if (type.equalsIgnoreCase("book"))
            searchingThrough = books;
        else if (type.equalsIgnoreCase("dvd"))
            searchingThrough = dvds;
        else if (type.equalsIgnoreCase("cd"))
            searchingThrough = cds;
        else {
            System.out.println(errorInvalidType);
            return null;
        }

        //check each entry for if it has the selected creator
        for (int entry = 0; entry < searchingThrough.length; entry++) {
            if (creator.equalsIgnoreCase(searchingThrough[entry][creatorPos])) {
                //filling out tempArray with results data + data for newest entry
                tempArray = new String[results.length + 1][tagCount];
                for (int i = 0; i < results.length; i++)
                    tempArray[i] = results[i];
                tempArray[results.length] = searchingThrough[entry];
                //updating results
                results = tempArray;
            }
        }
        return results;
    }

    /**
     * Search inventory based on type and genre.
     * @param type book, dvd, or cd.
     * @param genre genre being searched for.
     * @return a String[][] containing the data of all entries of that genre.
     */
    public String[][] searchGenre(String type, String genre) {
        String[][] searchingThrough; //to hold the appropriate String[][] to search through
        String[][] results = new String[0][tagCount], tempArray; //to hold search results and tempArray used for updating results

        //putting appropriate String[][] in searchingThrough[][]
        if (type.equalsIgnoreCase("book"))
            searchingThrough = books;
        else if (type.equalsIgnoreCase("dvd"))
            searchingThrough = dvds;
        else if (type.equalsIgnoreCase("cd"))
            searchingThrough = cds;
        else {
            System.out.println(errorInvalidType);
            return null;
        }

        //check each entry for if it has the selected genre in either of its genre tags
        for (int entry = 0; entry < searchingThrough.length; entry++) {
            if (genre.equalsIgnoreCase(searchingThrough[entry][genre1Pos]) || genre.equalsIgnoreCase(searchingThrough[entry][genre2Pos])) {
                //filling out tempArray with results data + data for newest entry
                tempArray = new String[results.length + 1][tagCount];
                for (int i = 0; i < results.length; i++)
                    tempArray[i] = results[i];
                tempArray[results.length] = searchingThrough[entry];
                //updating results
                results = tempArray;
            }
        }
        return results;
    }

    /**
     * Marks the first occurrence of that entry with an in/out tag of "out" as "in".
     * @param type book, dvd, or cd.
     * @param title tile of entry.
     * @param creator creator of entry you want to find (author, artist, director).
     */
    public void checkIn(String type, String title, String creator){
        //putting the appropriate String[][] to look through for the wanted entry in a String[][] called checkingIn
        String[][] checkingIn;
        if (type.equalsIgnoreCase("book"))
            checkingIn = books;
        else if (type.equalsIgnoreCase("dvd"))
            checkingIn = dvds;
        else if (type.equalsIgnoreCase("cd"))
            checkingIn = cds;
        else {
            System.out.println(errorInvalidType);
            return;
        }
        //looking for the entry that's wanted to be checked out and making sure its checked in
        for (int entry = 0; entry < checkingIn.length; entry++){
            if (checkingIn[entry][titlePos].equalsIgnoreCase(title) &&
                    checkingIn[entry][creatorPos].equalsIgnoreCase(creator) &&
                    checkingIn[entry][inOutPos].equalsIgnoreCase("in")){
                //marking book as checked out
                checkingIn[entry][inOutPos] = "out";
                if (type.equalsIgnoreCase("book"))
                    books = checkingIn;
                else if (type.equalsIgnoreCase("dvd"))
                    dvds = checkingIn;
                else
                    cds = checkingIn;
                return;
            }
        }
        System.out.println(errorEntryNotFound);
    }

    /**
     * Marks the first occurrence of that entry with an in/out tag of "in" as "out".
     * @param type book, dvd, or cd.
     * @param title tile of entry.
     * @param creator creator of entry you want to find (author, artist, director).
     */
    public void checkOut(String type, String title, String creator){
        //putting the appropriate String[][] to look through for the wanted entry in a String[][] called checkingOut
        String[][] checkingOut;
        if (type.equalsIgnoreCase("book"))
            checkingOut = books;
        else if (type.equalsIgnoreCase("dvd"))
            checkingOut = dvds;
        else if (type.equalsIgnoreCase("cd"))
            checkingOut = cds;
        else {
            System.out.println(errorInvalidType);
            return;
        }
        //looking for the entry that's wanted to be checked out and making sure its checked in
        for (int entry = 0; entry < checkingOut.length; entry++){
            if (checkingOut[entry][titlePos].equalsIgnoreCase(title) &&
                    checkingOut[entry][creatorPos].equalsIgnoreCase(creator) &&
                    checkingOut[entry][inOutPos].equalsIgnoreCase("in")){
                //marking book as checked out
                checkingOut[entry][inOutPos] = "out";
                if (type.equalsIgnoreCase("book"))
                    books = checkingOut;
                else if (type.equalsIgnoreCase("dvd"))
                    dvds = checkingOut;
                else
                    cds = checkingOut;
                return;
            }
        }
        System.out.println(errorEntryNotFound);
    }

    /**
     * Writes all arrays to their appropriate files.
     * @throws IOException will throw an error if it can not write to file. can make a new file, so should not throw unless disk is full.
     */
    public void save() throws IOException{
        //Making directories if they don't already exist
        File path = new File(dataPath + libraryName);
        boolean madePath = path.mkdirs();
        //PrintWriters to write to files
        PrintWriter saveBooks = new PrintWriter(dataPath + libraryName + "/Books.txt");
        PrintWriter saveDVDs = new PrintWriter(dataPath + libraryName + "/DVDs.txt");
        PrintWriter saveCDs = new PrintWriter(dataPath + libraryName + "/CDs.txt");
        //save book data
        for (int line = 0; line < books.length; line++){
            for (int word = 0; word < tagCount; word++){
                saveBooks.print(books[line][word]);
                if (word != tagCount - 1)
                    saveBooks.print(splitter);
            }
            saveBooks.println();
        }
        //save DVD data
        for (int line = 0; line < dvds.length; line++){
            for (int word = 0; word < tagCount; word++){
                saveDVDs.print(dvds[line][word]);
                if (word != tagCount - 1)
                    saveDVDs.print(splitter);
            }
            saveDVDs.println();
        }
        //save CD data
        for (int line = 0; line < cds.length; line++){
            for (int word = 0; word < tagCount; word++){
                saveCDs.print(cds[line][word]);
                if (word != tagCount - 1)
                    saveCDs.print(splitter);
            }
            saveCDs.println();
        }
        //closing PrintWriters
        saveBooks.close();
        saveDVDs.close();
        saveCDs.close();
    }
}
