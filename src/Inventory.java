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
        String[][] tempArray; //temporary array to hold data for type but exclude the entry being removed
        if (type.equalsIgnoreCase("book")){
            //checking if tags equal what was put in to be removed and that book is in the library for each entry
            for (int entry = 0; entry < books.length; entry++){
                if (books[entry][titlePos].equalsIgnoreCase(title) &&
                        books[entry][creatorPos].equalsIgnoreCase(creator) &&
                        books[entry][inOutPos].equalsIgnoreCase("in")){
                    //fill out tempArray with books's data (minus entry being removed) after right entry is found
                    tempArray = new String[books.length - 1][tagCount];
                    for (int upToRemoved = 0; upToRemoved < entry; upToRemoved++)
                        tempArray[upToRemoved] = books[upToRemoved];
                    for (int afterRemoved = entry + 1; afterRemoved < books.length; afterRemoved++)
                        tempArray[afterRemoved - 1] = books[afterRemoved];
                    //change books to the updated catalog
                    books = tempArray;
                    return;
                }
                System.out.println(errorEntryNotFound);
            }
        } else if (type.equalsIgnoreCase("dvd")){
            //checking if tags equal what was put in to be removed and that dvd is in the library for each entry
            for (int entry = 0; entry < dvds.length; entry++){
                if (dvds[entry][titlePos].equalsIgnoreCase(title) &&
                        dvds[entry][creatorPos].equalsIgnoreCase(creator) &&
                        dvds[entry][inOutPos].equalsIgnoreCase("in")){
                    //fill out tempArray with dvd's data (minus entry being removed) after right entry is found
                    tempArray = new String[dvds.length - 1][tagCount];
                    for (int upToRemoved = 0; upToRemoved < entry; upToRemoved++)
                        tempArray[upToRemoved] = dvds[upToRemoved];
                    for (int afterRemoved = entry + 1; afterRemoved < dvds.length; afterRemoved++)
                        tempArray[afterRemoved - 1] = dvds[afterRemoved];
                    //change dvds to the updated catalog
                    dvds = tempArray;
                    return;
                }
                System.out.println(errorEntryNotFound);
            }
        } else if (type.equalsIgnoreCase("cd")){
            //checking if tags equal what was put in to be removed and that cd is in the library for each entry
            for (int entry = 0; entry < cds.length; entry++){
                if (cds[entry][titlePos].equalsIgnoreCase(title) &&
                        cds[entry][creatorPos].equalsIgnoreCase(creator) &&
                        cds[entry][inOutPos].equalsIgnoreCase("in")){
                    //fill out tempArray with cds's data (minus entry being removed) after right entry is found
                    tempArray = new String[cds.length - 1][tagCount];
                    for (int upToRemoved = 0; upToRemoved < entry; upToRemoved++)
                        tempArray[upToRemoved] = cds[upToRemoved];
                    for (int afterRemoved = entry + 1; afterRemoved < cds.length; afterRemoved++)
                        tempArray[afterRemoved - 1] = cds[afterRemoved];
                    //change cds to the updated catalog
                    cds = tempArray;
                    return;
                }
                System.out.println(errorEntryNotFound);
            }
        } else {
            System.out.println(errorInvalidType);
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
        int[] results = new int[0], tempArray; //array to hold search results and a tempArray used for updating results
        if (type.equalsIgnoreCase("book")){
            //checking each entry for if it meets the search criteria
            for (int entry = 0; entry < books.length; entry++){
                if (books[entry][titlePos].equalsIgnoreCase(title) && books[entry][creatorPos].equalsIgnoreCase(creator)){
                    //filling out tempArray with results data + position of the newest find of the book
                    tempArray = new int[results.length + 1];
                    for (int i = 0; i < results.length; i++)
                        tempArray[i] = results [i];
                    tempArray[results.length] = entry;
                    //updating results
                    results = tempArray;
                }
                System.out.println(errorEntryNotFound);
            }
        } else if (type.equalsIgnoreCase("dvd")){
            //checking each entry for if it meets the search criteria
            for (int entry = 0; entry < dvds.length; entry++){
                if (dvds[entry][titlePos].equalsIgnoreCase(title) && dvds[entry][creatorPos].equalsIgnoreCase(creator)){
                    //filling out tempArray with results data + position of the newest find of the dvd
                    tempArray = new int[results.length + 1];
                    for (int i = 0; i < results.length; i++)
                        tempArray[i] = results [i];
                    tempArray[results.length] = entry;
                    //updating results
                    results = tempArray;
                }
                System.out.println(errorEntryNotFound);
            }
        } else if (type.equalsIgnoreCase("cd")){
            //checking each entry for if it meets the search criteria
            for (int entry = 0; entry < cds.length; entry++){
                if (cds[entry][titlePos].equalsIgnoreCase(title) && cds[entry][creatorPos].equalsIgnoreCase(creator)){
                    //filling out tempArray with results data + position of the newest find of the cd
                    tempArray = new int[results.length + 1];
                    for (int i = 0; i < results.length; i++)
                        tempArray[i] = results [i];
                    tempArray[results.length] = entry;
                    //updating results
                    results = tempArray;
                }
                System.out.println(errorEntryNotFound);
            }
        } else {
            System.out.println(errorInvalidType);
            return null;
        }
        return results;
    }

    /**
     * Gets entry data from an index
     * @param type book, cd, or dvd (case sensitive)
     * @param index index of wanted entry
     * @return entry data as a String[]
     */
    public String[] getPosition(String type, int index){
        if (type.equalsIgnoreCase("book"))
            return books[index];
        else if (type.equalsIgnoreCase("dvd"))
            return dvds[index];
        else if (type.equalsIgnoreCase("cd"))
            return cds[index];
        else
            System.out.println(errorInvalidType);
            return null;
    }

    /**
     * Gets a single tag of a single entry
     * @param type book, dvd, or cd
     * @param index index of entry
     * @param tag index of wanted tag
     * @return a String holding the tag's value
     */
    public String getTag(String type, int index, int tag){
        if (type.equalsIgnoreCase("book"))
            return books[index][tag];
        else if (type.equalsIgnoreCase("dvd"))
            return dvds[index][tag];
        else if (type.equalsIgnoreCase("cd"))
            return cds[index][tag];
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
        String[][] results = new String[0][tagCount], tempArray; //array to hold results and tempArray used for updating results
        if (type.equalsIgnoreCase("book")){
            //check each entry for if it has the right title
            for (int entry = 0; entry < books.length; entry++){
                if (title.equalsIgnoreCase(books[entry][titlePos])){
                    //fill out tempArray with result's data + newest find
                    tempArray = new String[results.length + 1][tagCount];
                    for (int i = 0; i < results.length; i++)
                        tempArray[i] = results [i];
                    tempArray[results.length] = books[entry];
                    //updating results
                    results = tempArray;
                }
            }
        } else if (type.equalsIgnoreCase("dvd")) {
            //check each entry for if it has the right title
            for (int entry = 0; entry < dvds.length; entry++) {
                if (title.equalsIgnoreCase(dvds[entry][titlePos])){
                    //fill out tempArray with result's data + newest find
                    tempArray = new String[results.length + 1][tagCount];
                    for (int i = 0; i < results.length; i++)
                        tempArray[i] = results[i];
                    tempArray[results.length] = dvds[entry];
                    //updating results
                    results = tempArray;
                }
            }
        } else if (type.equalsIgnoreCase("cd")){
            //check each entry for if it has the right title
            for (int entry = 0; entry < cds.length; entry++){
                if (title.equalsIgnoreCase(cds[entry][titlePos])){
                    //fill out tempArray with result's data + newest find
                    tempArray = new String[results.length + 1][tagCount];
                    for (int i = 0; i < results.length; i++)
                        tempArray[i] = results [i];
                    tempArray[results.length] = cds[entry];
                    //updating results
                    results = tempArray;
                }
            }
        } else {
            System.out.println(errorInvalidType);
            return null;
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
        String[][] results = new String[0][tagCount], tempArray; //array to hold results and tempArray used for updating results
        if (type.equalsIgnoreCase("book")){
            //check each entry for if it has the right creator (author)
            for (int entry = 0; entry < books.length; entry++){
                if (creator.equalsIgnoreCase(books[entry][creatorPos])){
                    //fill out tempArray with result's data + newest find
                    tempArray = new String[results.length + 1][tagCount];
                    for (int i = 0; i < results.length; i++)
                        tempArray[i] = results [i];
                    tempArray[results.length] = books[entry];
                    //updating results
                    results = tempArray;
                }
            }
        } else if (type.equalsIgnoreCase("dvd")) {
            //check each entry for if it has the right creator (director)
            for (int entry = 0; entry < dvds.length; entry++) {
                if (creator.equalsIgnoreCase(dvds[entry][creatorPos])){
                    //fill out tempArray with result's data + newest find
                    tempArray = new String[results.length + 1][tagCount];
                    for (int i = 0; i < results.length; i++)
                        tempArray[i] = results[i];
                    tempArray[results.length] = dvds[entry];
                    //updating results
                    results = tempArray;
                }
            }
        } else if (type.equalsIgnoreCase("cd")){
            //check each entry for if it has the right creator (artist)
            for (int entry = 0; entry < cds.length; entry++){
                if (creator.equalsIgnoreCase(cds[entry][creatorPos])){
                    //fill out tempArray with result's data + newest find
                    tempArray = new String[results.length + 1][tagCount];
                    for (int i = 0; i < results.length; i++)
                        tempArray[i] = results [i];
                    tempArray[results.length] = cds[entry];
                    //updating results
                    results = tempArray;
                }
            }
        } else {
            System.out.println(errorInvalidType);
            return null;
        }
        return results;
    }

    /**
     * Search inventory based on type and genre
     * @param type book, dvd, or cd
     * @param genre genre being searched for
     * @return a String[][] containing the data of all entries of that genre
     */
    public String[][] searchGenre(String type, String genre){
        String[][] results = new String[0][tagCount], tempArray; //array to hold results and tempArray used for updating results
        if (type.equalsIgnoreCase("book")){
            //check each entry for if it has the selected genre in either of its genre tags
            for (int entry = 0; entry < books.length; entry++){
                if (genre.equalsIgnoreCase(books[entry][genre1Pos]) || genre.equalsIgnoreCase(books[entry][genre2Pos])){
                    //filling out tempArray with results data + data for newest entry
                    tempArray = new String[results.length + 1][tagCount];
                    for (int i = 0; i < results.length; i++)
                        tempArray[i] = results [i];
                    tempArray[results.length] = books[entry];
                    //updating results
                    results = tempArray;
                }
            }
        } else if (type.equalsIgnoreCase("dvd")) {
            //check each entry for if it has the selected genre in either of its genre tags
            for (int entry = 0; entry < dvds.length; entry++) {
                if (genre.equalsIgnoreCase(dvds[entry][genre1Pos]) || genre.equalsIgnoreCase(dvds[entry][genre2Pos])) {
                    //filling out tempArray with results data + data for newest entry
                    tempArray = new String[results.length + 1][tagCount];
                    for (int i = 0; i < results.length; i++)
                        tempArray[i] = results[i];
                    tempArray[results.length] = dvds[entry];
                    results = tempArray;
                }
            }
        } else if (type.equalsIgnoreCase("cd")){
            //check each entry for if it has the selected genre in either of its genre tags
            for (int entry = 0; entry < cds.length; entry++){
                if (genre.equalsIgnoreCase(cds[entry][genre1Pos]) || genre.equalsIgnoreCase(cds[entry][genre2Pos])){
                    //filling out tempArray with results data + data for newest entry
                    tempArray = new String[results.length + 1][tagCount];
                    for (int i = 0; i < results.length; i++)
                        tempArray[i] = results [i];
                    tempArray[results.length] = cds[entry];
                    //updating results
                    results = tempArray;
                }
            }
        } else {
            System.out.println(errorInvalidType);
            return null;
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
        if (type.equalsIgnoreCase("book")){
            //checking each entry for if it meets the given tags and is checked out
            for (int entry = 0; entry < books.length; entry++){
                if (books[entry][titlePos].equalsIgnoreCase(title) &&
                            books[entry][creatorPos].equalsIgnoreCase(creator) &&
                            books[entry][inOutPos].equalsIgnoreCase("out")){
                    //marking book as checked in
                    books[entry][inOutPos] = "in";
                    return;
                }
                System.out.println(errorEntryNotFound);
            }
        } else if (type.equalsIgnoreCase("dvd")){
            //checking each entry for if it meets the given tags and is checked out
            for (int entry = 0; entry < dvds.length; entry++){
                if (dvds[entry][titlePos].equalsIgnoreCase(title) &&
                            dvds[entry][creatorPos].equalsIgnoreCase(creator) &&
                            dvds[entry][inOutPos].equalsIgnoreCase("out")){
                    //marking dvd as checked in
                    dvds[entry][inOutPos] = "in";
                    return;
                }
                System.out.println(errorEntryNotFound);
            }
        } else if (type.equalsIgnoreCase("cd")){
            //checking each entry for if it meets the given tags and is checked out
            for (int entry = 0; entry < cds.length; entry++){
                if (cds[entry][titlePos].equalsIgnoreCase(title) &&
                            cds[entry][creatorPos].equalsIgnoreCase(creator) &&
                            cds[entry][inOutPos].equalsIgnoreCase("out")){
                    //marking cd as checked in
                    cds[entry][inOutPos] = "in";
                    return;
                }
                System.out.println(errorEntryNotFound);
            }
        } else {
            System.out.println(errorInvalidType);
        }
    }

    /**
     * Marks the first occurrence of that entry with an in/out tag of "in" as "out"
     * @param type book, dvd, or cd
     * @param title tile of entry
     * @param creator creator of entry you want to find (author, artist, director)
     */
    public void checkOut(String type, String title, String creator){
        //checking each entry for if it meets the given tags and is checked in
        String[][] searchThrough;
        if (type.equalsIgnoreCase("book"))
            searchThrough = books;
        else if (type.equalsIgnoreCase("dvd"))

        if (type.equalsIgnoreCase("book")){
            for (int entry = 0; entry < books.length; entry++){
                if (books[entry][titlePos].equalsIgnoreCase(title) &&
                        books[entry][creatorPos].equalsIgnoreCase(creator) &&
                        books[entry][inOutPos].equalsIgnoreCase("in")){
                    //marking book as checked out
                    books[entry][inOutPos] = "out";
                    return;
                }
                System.out.println(errorEntryNotFound);
            }
        } else if (type.equalsIgnoreCase("dvd")){
            //checking each entry for if it meets the given tags and is checked in
            for (int entry = 0; entry < dvds.length; entry++){
                if (dvds[entry][titlePos].equalsIgnoreCase(title) &&
                        dvds[entry][creatorPos].equalsIgnoreCase(creator) &&
                        dvds[entry][inOutPos].equalsIgnoreCase("in")){
                    //marking dvd as checked out
                    dvds[entry][inOutPos] = "out";
                    return;
                }
                System.out.println(errorEntryNotFound);
            }
        } else if (type.equalsIgnoreCase("cd")){
            //checking each entry for if it meets the given tags and is checked in
            for (int entry = 0; entry < cds.length; entry++){
                if (cds[entry][titlePos].equalsIgnoreCase(title) &&
                        cds[entry][creatorPos].equalsIgnoreCase(creator) &&
                        cds[entry][inOutPos].equalsIgnoreCase("in")){
                    //marking cd as checked out
                    cds[entry][inOutPos] = "out";
                    return;
                }
                System.out.println(errorEntryNotFound);
            }
        } else {
            System.out.println(errorInvalidType);
        }
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
