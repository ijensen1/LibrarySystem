import java.io.*;
import java.util.Scanner;

/**
 * Class to handle inventory of a specific library, all method inputs are case in-sensitive
 */
public class Inventory {
    //Variables for library data
    private String[][] books = {}, dvds = {}, cds = {};   //arrays to hold data on library's books, DVDs, and CDs
    private String libraryName;

    //constants
    private final String splitter = "&"; //used between tags for when reading from or saving to a file
    private final int tagCount = 5; //how many tags each entry has. tags are title, creator, genre1, genre2, and inOut

    //tag positions
    private final int titlePos = 0, creatorPos = 1, genre1Pos = 2, genre2Pos = 3, inOutPos = 4;

    //error messages meant for user, or Jack, depending on how he implements things
    private final String errorInvalidType = "Invalid entry type. Valid entry types are \"book\", \"dvd\", and \"cd\" (case insensitive)";
    private final String errorEntryNotFound = "Could not locate any entries containing that data";

    /**
     * Constructor, populates arrays with data saved in files for that library
     * @param libraryName name of the library
     * @throws IOException may throw IOException, should not though as I made it so it only makes a Scanner for a file if the file exists
     */
    public Inventory(String libraryName) throws IOException{
        this.libraryName = libraryName;
        //making Files
        System.out.println("Ryker was here");
        File bookFile = new File(libraryName + "Books.txt"),
                dvdFile = new File(libraryName + "DVDs.txt"),
                cdFile = new File(libraryName + "CDs.txt");

        //variables for reading file data
        String currentLine; //for holding the current line when working through files
        String[][] books, dvds, cds; //for holding a modified version of this.books, this.dvds, and this.cds that also holds the next entry in the file

        //checking if a file for the library's books exists
        if (bookFile.exists()){
            Scanner readBooks = new Scanner(bookFile);
            //reading files for books and putting that data in this.books
            while (readBooks.hasNextLine()){
                currentLine = readBooks.nextLine();
                books = new String[this.books.length + 1][tagCount];
                for (int i = 0; i < this.books.length; i++){
                    books[i] = this.books[i];
                }
                books[this.books.length] = currentLine.split(splitter, tagCount);
                this.books = books;
            }
            //closing Scanner
            readBooks.close();
            System.out.println("Loaded " + this.books.length + " books");
        }

        //checking if a file for the library's dvds exists
        if (dvdFile.exists()){
            Scanner readDVDs = new Scanner(dvdFile);
            //reading files for dvds and putting that data in this.dvds
            while (readDVDs.hasNextLine()){
                currentLine = readDVDs.nextLine();
                dvds = new String[this.dvds.length + 1][tagCount];
                for (int i = 0; i < this.dvds.length; i++){
                    dvds[i] = this.dvds[i];
                }
                dvds[this.dvds.length] = currentLine.split(splitter,  tagCount);
                this.dvds = dvds;
            }
            //closing Scanner
            readDVDs.close();
            System.out.println("Loaded " + this.dvds.length + " DVDs");
        }

        //checking if a file for the library's cds exists
        if (cdFile.exists()){
            Scanner readCDs = new Scanner(cdFile);
            //reading files for cds and putting that data in this.cds
            while (readCDs.hasNextLine()){
                currentLine = readCDs.nextLine();
                cds = new String[this.cds.length + 1][tagCount];
                for (int i = 0; i < this.cds.length; i++){
                    cds[i] = this.cds[i];
                }
                cds[this.cds.length] = currentLine.split(splitter, tagCount);
                this.cds = cds;
            }
            //closing Scanner
            readCDs.close();
            System.out.println("Loaded " + this.cds.length + " CDs");
        }
    }

    /**
     * Add an entry to a library's inventory
     * @param type book, cd, or dvd
     * @param title title of the entry
     * @param genre1 a genre of the entry
     * @param genre2 a second genre of the entry
     */
    public void add(String type, String title, String creator, String genre1, String genre2){
        String[][] tempArray; //temporary array for holding a modified array with the new value added to it

        if (type.equalsIgnoreCase("book")) {
            //filling out tempArray with books data + new entry
            tempArray = new String[books.length + 1][tagCount];
            for (int i = 0; i < books.length; i++){
                tempArray[i] = books[i];
            }
            tempArray[books.length] = new String[]{title, creator, genre1, genre2, "in"};
            //updating books
            books = tempArray;
        } else if (type.equalsIgnoreCase("dvd")){
            //filling out tempArray with dvds data + new entry
            tempArray = new String[dvds.length + 1][tagCount];
            for (int i = 0; i < dvds.length; i++){
                tempArray[i] = dvds[i];
            }
            tempArray[dvds.length] = new String[]{title, creator, genre1, genre2, "in"};
            //updating dvds
            dvds = tempArray;
        } else if (type.equalsIgnoreCase("cd")){
            //filling out tempArray with cds data + new entry
            tempArray = new String[cds.length + 1][tagCount];
            for (int i = 0; i < cds.length; i++){
                tempArray[i] = cds[i];
            }
            tempArray[cds.length] = new String[]{title, creator, genre1, genre2, "in"};
            //updating cds
            cds = tempArray;
        } else {
            System.out.println(errorInvalidType);
        }
    }

    /**
     * Remove an entry from a library's inventory. Will only remove entries with an in/out tag of "in"
     * @param type book, cd, or dvd
     * @param title title of entry
     * @param creator creator of entry you want to remove (author, artist, director)
     */
    public void remove(String type, String title, String creator) {
        String[][] removeSearch, //array to hold the appropriate String[][] that we are looking through to find what to remove
                removed; //to hold data in removeSearch but exclude the entry being removed

        //setting removeSearch to appropriate String[][]
        if (type.equalsIgnoreCase("book"))
            removeSearch = books;
        else if (type.equalsIgnoreCase("dvd"))
            removeSearch = dvds;
        else if (type.equalsIgnoreCase("cd"))
            removeSearch = cds;
        else {
            System.out.println(errorInvalidType);
            return;
        }

        //checking if tags equal what was put in to be removed and that entry is in the library for each entry
        for (int entry = 0; entry < removeSearch.length; entry++){
            if (removeSearch[entry][titlePos].equalsIgnoreCase(title) &&
                    removeSearch[entry][creatorPos].equalsIgnoreCase(creator) &&
                    removeSearch[entry][inOutPos].equalsIgnoreCase("in")){
                //fill out tempArray with entries' data (minus entry being removed) after right entry is found
                removed = new String[removeSearch.length - 1][tagCount];
                for (int upToRemoved = 0; upToRemoved < entry; upToRemoved++)
                    removed[upToRemoved] = removeSearch[upToRemoved];
                for (int afterRemoved = entry + 1; afterRemoved < books.length; afterRemoved++)
                    removed[afterRemoved - 1] = removeSearch[afterRemoved];
                //change String[][] for type to the updated catalog
                if (type.equalsIgnoreCase("book"))
                    books = removed;
                else if (type.equalsIgnoreCase("dvd"))
                    dvds = removed;
                else
                    cds = removed;
                return;
            }
            System.out.println(errorEntryNotFound);
        }
    }

    /**
     * Returns the indexes of all instances of an entry
     * @param type book, dvd, or cd
     * @param title title of entry being looked for
     * @param creator creator of entry you want to find (author, artist, director)
     * @return int[] holding indexes of every instance (for if multiple copies exist)
     */
    public int[] findIndex(String type, String title, String creator){
        String[][] searchingThrough; //to hold type's String[][] for searching through
        int[] results = new int[0], tempArray; //to hold search results and tempArray used for updating results

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
            if (title.equalsIgnoreCase(searchingThrough[entry][titlePos]) && creator.equalsIgnoreCase(searchingThrough[entry][creatorPos])) {
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
     * Gets entry data from an index number
     * @param type book, cd, or dvd (case sensitive)
     * @param entry index of wanted entry
     * @return entry data as a String[]
     */
    public String[] getIndex(String type, int entry){
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
     * Gets a single tag of a single entry
     * @param type book, dvd, or cd
     * @param entry index of entry
     * @param tag index of wanted tag
     * @return a String holding the tag's value
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
     * Search inventory based on type and title
     * @param type book, dvd, or cd
     * @param title title of what you're searching for
     * @return a String[][] containing the data of all entries with that title
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
     * Search a library's inventor based on type and creator
     * @param type book, dvd, or cd
     * @param creator creator of entry you want to find (author, artist, director)
     * @return String[][] of all works by that creator
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
     * Search inventory based on type and genre
     * @param type book, dvd, or cd
     * @param genre genre being searched for
     * @return a String[][] containing the data of all entries of that genre
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
     * Marks the first occurrence of that entry with an in/out tag of "out" as "in"
     * @param type book, dvd, or cd
     * @param title tile of entry
     * @param creator creator of entry you want to find (author, artist, director)
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
     * Marks the first occurrence of that entry with an in/out tag of "in" as "out"
     * @param type book, dvd, or cd
     * @param title tile of entry
     * @param creator creator of entry you want to find (author, artist, director)
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
     * Writes all arrays to their appropriate files
     * @throws IOException will throw an error if it can not write to file. can make a new file, so should not throw unless disk is full
     */
    public void save() throws IOException{
        //PrintWriters to write to files
        PrintWriter saveBooks = new PrintWriter(libraryName + "Books.txt");
        PrintWriter saveDVDs = new PrintWriter(libraryName + "DVDs.txt");
        PrintWriter saveCDs = new PrintWriter(libraryName + "CDs.txt");
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
