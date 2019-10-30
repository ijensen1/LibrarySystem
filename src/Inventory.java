import java.io.*;
import java.util.Scanner;

/**
 * Class to handle inventory of a specific library
 */
public class Inventory {
    //Variables
    private String[][] books = {}, dvds = {}, cds = {};   //arrays to hold data on library's books, DVDs, and CDs
    private String libraryName;

    //constants
    private final String splitter = "&";
    private final int parameterCount = 4;
    //parameter positions
    private final int titlePos = 0, genre1Pos = 1, genre2Pos = 2, inOutPos = 3;

    //error messages meant for user, or Jack, depending on how he implements things
    private final String errorInvalidType = "Invalid entry type. Valid entry types are \"book\", \"dvd\", and \"cd\" (case insensitive)";
    private final String errorEntryNotFound = "Could not locate any entries containing that data";

    /**
     * Constructor, populates arrays with data saved in files for that library
     * @param libraryName name of the library
     * @throws IOException may error if can't find file. assumes files for specified library are already made and are in the format libraryNameBook.txt
     */
    public Inventory(String libraryName) throws IOException{
        this.libraryName = libraryName;
        //Creates scanners to read data from files
        Scanner readBooks = new Scanner(new File(libraryName + "Books.txt")),
                readDVDs = new Scanner(new File(libraryName + "DVDs.txt")),
                readCDs = new Scanner(new File(libraryName + "CDs.txt"));

        String currentLine; //for holding the current line when working through files
        String[][] books, dvds, cds; //for holding a modified version of this.books, this.dvds, and this.cds that also holds the next entry in the file

        //reading files for books and putting that data in this.books
        while (readBooks.hasNextLine()){
            currentLine = readBooks.nextLine();
            books = new String[this.books.length + 1][parameterCount];
            for (int i = 0; i < this.books.length; i++){
                books[i] = this.books[i];
            }
            books[this.books.length] = currentLine.split(splitter, parameterCount);
            this.books = books;
        }

        //reading files for DVDs and putting that data in this.dvds
        while (readDVDs.hasNextLine()){
            currentLine = readDVDs.nextLine();
            dvds = new String[this.dvds.length + 1][parameterCount];
            for (int i = 0; i < this.dvds.length; i++){
                dvds[i] = this.dvds[i];
            }
            dvds[this.dvds.length] = currentLine.split(splitter,  parameterCount);
            this.dvds = dvds;
        }

        //reading files for CDs and putting that data in this.cds
        while (readCDs.hasNextLine()){
            currentLine = readCDs.nextLine();
            cds = new String[this.cds.length + 1][parameterCount];
            for (int i = 0; i < this.cds.length; i++){
                cds[i] = this.cds[i];
            }
            cds[this.cds.length] = currentLine.split(splitter, parameterCount);
            this.cds = cds;
        }
        //closing scanners
        readBooks.close();
        readDVDs.close();
        readCDs.close();
    }

    /**
     * Add an entry to a library's inventory
     * @param type book, cd, or dvd (case in-sensitive)
     * @param title title of the entry
     * @param genre1 a genre of the entry
     * @param genre2 a second genre of the entry
     */
    public void add(String type, String title, String genre1, String genre2){
        String[][] tempArray; //temporary array for holding a modified array with the new value added to it

        if (type.equalsIgnoreCase("book")) {
            //filling out tempArray with books data
            tempArray = new String[books.length + 1][parameterCount];
            for (int i = 0; i < books.length; i++){
                tempArray[i] = books[i];
            }
            tempArray[books.length] = new String[]{title, genre1, genre2, "in"};
            //setting books to tempArray (tempArray now contains what books needs)
            books = tempArray;
        } else if (type.equalsIgnoreCase("dvd")){
            //filling out tempArray with dvds data
            tempArray = new String[dvds.length + 1][parameterCount];
            for (int i = 0; i < dvds.length; i++){
                tempArray[i] = dvds[i];
            }
            tempArray[dvds.length] = new String[]{title, genre1, genre2, "in"};
            //setting books to tempArray (tempArray now contains what books needs)
            dvds = tempArray;
        } else if (type.equalsIgnoreCase("cd")){
            //filling out tempArray with cds data
            tempArray = new String[cds.length + 1][parameterCount];
            for (int i = 0; i < cds.length; i++){
                tempArray[i] = cds[i];
            }
            tempArray[cds.length] = new String[]{title, genre1, genre2, "in"};
            //setting books to tempArray (tempArray now contains what books needs)
            cds = tempArray;
        } else {
            System.out.println(errorInvalidType);
        }
    }

    /**
     * Remove an entry from a library's inventory. Will only remove entries with an in/out parameter of "in"
     * @param type book, cd, or dvd (case in-sensitive)
     * @param title title of entry
     * @param genre1 first genre of entry (used to make sure correct entry is removed if repeated titles)
     * @param genre2 second genre of entry
     */
    public void remove(String type, String title, String genre1, String genre2) {

    }

    public int[] findIndex(String type, String title, String genre1, String genre2){
        int[] results = new int[0], tempArray;
        if (type.equalsIgnoreCase("book")){
            for (int entry = 0; entry < books.length; entry++){
                if (books[entry][titlePos].equalsIgnoreCase(title) &&
                        (books[entry][genre1Pos].equalsIgnoreCase(genre1) || books[entry][genre2Pos].equalsIgnoreCase(genre1)) &&
                        (books[entry][genre1Pos].equalsIgnoreCase(genre2) || books[entry][genre2Pos].equalsIgnoreCase(genre2))){
                    tempArray = new int[results.length + 1];
                    for (int i = 0; i < results.length; i++)
                        tempArray[i] = results [i];
                    tempArray[results.length] = entry;
                    results = tempArray;
                }
                System.out.println(errorEntryNotFound);
            }
        } else if (type.equalsIgnoreCase("dvd")){
            for (int entry = 0; entry < dvds.length; entry++){
                if (dvds[entry][titlePos].equalsIgnoreCase(title) &&
                        (dvds[entry][genre1Pos].equalsIgnoreCase(genre1) || dvds[entry][genre2Pos].equalsIgnoreCase(genre1)) &&
                        (dvds[entry][genre1Pos].equalsIgnoreCase(genre2) || dvds[entry][genre2Pos].equalsIgnoreCase(genre2))){
                    tempArray = new int[results.length + 1];
                    for (int i = 0; i < results.length; i++)
                        tempArray[i] = results [i];
                    tempArray[results.length] = entry;
                    results = tempArray;
                }
                System.out.println(errorEntryNotFound);
            }
        } else if (type.equalsIgnoreCase("cd")){
            for (int entry = 0; entry < cds.length; entry++){
                if (cds[entry][titlePos].equalsIgnoreCase(title) &&
                        (cds[entry][genre1Pos].equalsIgnoreCase(genre1) || cds[entry][genre2Pos].equalsIgnoreCase(genre1)) &&
                        (cds[entry][genre1Pos].equalsIgnoreCase(genre2) || cds[entry][genre2Pos].equalsIgnoreCase(genre2))){
                    tempArray = new int[results.length + 1];
                    for (int i = 0; i < results.length; i++)
                        tempArray[i] = results [i];
                    tempArray[results.length] = entry;
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
     * Gets entry data from an array position
     * @param type book, cd, or dvd (case sensitive)
     * @param index index of wanted entry
     * @return entry data as a String[]
     */
    public String[] getPosition(String type, int index){
        if (type.equalsIgnoreCase("book"))
            return  books[index];
        else if (type.equalsIgnoreCase("dvd"))
            return dvds[index];
        else if (type.equalsIgnoreCase("cd"))
            return cds[index];
        else
            System.out.println(errorInvalidType);
            return null;
    }

    /**
     * Gets a single parameter of a single entry
     * @param type book, dvd, or cd (case in-sensitive)
     * @param index index of entry
     * @param parameter index of wanted parameter
     * @return a String holding the parameter's value
     */
    public String getParameter(String type, int index, int parameter){
        if (type.equalsIgnoreCase("book"))
            return books[index][parameter];
        else if (type.equalsIgnoreCase("dvd"))
            return dvds[index][parameter];
        else if (type.equalsIgnoreCase("cd"))
            return cds[index][parameter];
        else{
            System.out.println(errorInvalidType);
            return null;
        }
    }

    /**
     * Search inventory based on type and name
     * @param type book, dvd, or cd (case in-sensitive)
     * @param title title of what you're searching for
     * @return a String[][] containing the data of all entries with that title
     */
    public String[][] searchName(String type, String title){
        String[][] results = new String[0][4],
                tempArray;
        if (type.equalsIgnoreCase("book")){
            for (int entry = 0; entry < books.length; entry++){
                if (title.equalsIgnoreCase(books[entry][titlePos])){
                    tempArray = new String[results.length + 1][parameterCount];
                    for (int i = 0; i < results.length; i++)
                        tempArray[i] = results [i];
                    tempArray[results.length] = books[entry];
                    results = tempArray;
                }
            }
        } else if (type.equalsIgnoreCase("dvd")) {
            for (int entry = 0; entry < dvds.length; entry++) {
                if (title.equalsIgnoreCase(dvds[entry][titlePos])) {
                    tempArray = new String[results.length + 1][parameterCount];
                    for (int i = 0; i < results.length; i++)
                        tempArray[i] = results[i];
                    tempArray[results.length] = dvds[entry];
                    results = tempArray;
                }
            }
        } else if (type.equalsIgnoreCase("cd")){
            for (int entry = 0; entry < cds.length; entry++){
                if (title.equalsIgnoreCase(cds[entry][titlePos])){
                    tempArray = new String[results.length + 1][parameterCount];
                    for (int i = 0; i < results.length; i++)
                        tempArray[i] = results [i];
                    tempArray[results.length] = cds[entry];
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
     * @param type book, dvd, or cd (case in-sensitive)
     * @param genre genre being searched for
     * @return a String[][] containing the data of all entries of that genre
     */
    public String[][] searchGenre(String type, String genre){
        String[][] results = new String[0][4],
                tempArray;
        if (type.equalsIgnoreCase("book")){
            for (int entry = 0; entry < books.length; entry++){
                if (genre.equalsIgnoreCase(books[entry][genre1Pos]) || genre.equalsIgnoreCase(books[entry][genre2Pos])){
                    tempArray = new String[results.length + 1][parameterCount];
                    for (int i = 0; i < results.length; i++)
                        tempArray[i] = results [i];
                    tempArray[results.length] = books[entry];
                    results = tempArray;
                }
            }
        } else if (type.equalsIgnoreCase("dvd")) {
            for (int entry = 0; entry < dvds.length; entry++) {
                if (genre.equalsIgnoreCase(dvds[entry][genre1Pos]) || genre.equalsIgnoreCase(dvds[entry][genre2Pos])) {
                    tempArray = new String[results.length + 1][parameterCount];
                    for (int i = 0; i < results.length; i++)
                        tempArray[i] = results[i];
                    tempArray[results.length] = dvds[entry];
                    results = tempArray;
                }
            }
        } else if (type.equalsIgnoreCase("cd")){
            for (int entry = 0; entry < cds.length; entry++){
                if (genre.equalsIgnoreCase(cds[entry][genre1Pos]) || genre.equalsIgnoreCase(cds[entry][genre2Pos])){
                    tempArray = new String[results.length + 1][parameterCount];
                    for (int i = 0; i < results.length; i++)
                        tempArray[i] = results [i];
                    tempArray[results.length] = cds[entry];
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
     * Marks the first occurrence of that entry with an in/out parameter of "out" as "in"
     * @param type book, dvd, or cd (case in-sensitive)
     * @param title tile of entry
     * @param genre1 first genre of entry (used to make sure correct entry is removed if repeated titles)
     * @param genre2 second genre of entry
     */
    public void checkIn(String type, String title, String genre1, String genre2){
        if (type.equalsIgnoreCase("book")){
            for (int entry = 0; entry < books.length; entry++){
                if (books[entry][titlePos].equalsIgnoreCase(title) &&
                            (books[entry][genre1Pos].equalsIgnoreCase(genre1) || books[entry][genre2Pos].equalsIgnoreCase(genre1)) &&
                            (books[entry][genre1Pos].equalsIgnoreCase(genre2) || books[entry][genre2Pos].equalsIgnoreCase(genre2)) &&
                            books[entry][inOutPos].equalsIgnoreCase("out")){
                    books[entry][inOutPos] = "in";
                    return;
                }
                System.out.println(errorEntryNotFound);
            }
        } else if (type.equalsIgnoreCase("dvd")){
            for (int entry = 0; entry < dvds.length; entry++){
                if (dvds[entry][titlePos].equalsIgnoreCase(title) &&
                            (dvds[entry][genre1Pos].equalsIgnoreCase(genre1) || dvds[entry][genre2Pos].equalsIgnoreCase(genre1)) &&
                            (dvds[entry][genre1Pos].equalsIgnoreCase(genre2) || dvds[entry][genre2Pos].equalsIgnoreCase(genre2)) &&
                            dvds[entry][inOutPos].equalsIgnoreCase("out")){
                    dvds[entry][inOutPos] = "in";
                    return;
                }
                System.out.println(errorEntryNotFound);
            }
        } else if (type.equalsIgnoreCase("cd")){
            for (int entry = 0; entry < cds.length; entry++){
                if (cds[entry][titlePos].equalsIgnoreCase(title) &&
                            (cds[entry][genre1Pos].equalsIgnoreCase(genre1) || cds[entry][genre2Pos].equalsIgnoreCase(genre1)) &&
                            (cds[entry][genre1Pos].equalsIgnoreCase(genre2) || cds[entry][genre2Pos].equalsIgnoreCase(genre2)) &&
                            cds[entry][inOutPos].equalsIgnoreCase("out")){
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
     * Marks the first occurrence of that entry with an in/out parameter of "in" as "out"
     * @param type book, dvd, or cd (case in-sensitive)
     * @param title tile of entry
     * @param genre1 first genre of entry (used to make sure correct entry is removed if repeated titles)
     * @param genre2 second genre of entry
     */
    public void checkOut(String type, String title, String genre1, String genre2){
        if (type.equalsIgnoreCase("book")){
            for (int entry = 0; entry < books.length; entry++){
                if (books[entry][titlePos].equalsIgnoreCase(title) &&
                        (books[entry][genre1Pos].equalsIgnoreCase(genre1) || books[entry][genre2Pos].equalsIgnoreCase(genre1)) &&
                        (books[entry][genre1Pos].equalsIgnoreCase(genre2) || books[entry][genre2Pos].equalsIgnoreCase(genre2)) &&
                        books[entry][inOutPos].equalsIgnoreCase("in")){
                    books[entry][inOutPos] = "out";
                    return;
                }
                System.out.println(errorEntryNotFound);
            }
        } else if (type.equalsIgnoreCase("dvd")){
            for (int entry = 0; entry < dvds.length; entry++){
                if (dvds[entry][titlePos].equalsIgnoreCase(title) &&
                        (dvds[entry][genre1Pos].equalsIgnoreCase(genre1) || dvds[entry][genre2Pos].equalsIgnoreCase(genre1)) &&
                        (dvds[entry][genre1Pos].equalsIgnoreCase(genre2) || dvds[entry][genre2Pos].equalsIgnoreCase(genre2)) &&
                        dvds[entry][inOutPos].equalsIgnoreCase("in")){
                    dvds[entry][inOutPos] = "out";
                    return;
                }
                System.out.println(errorEntryNotFound);
            }
        } else if (type.equalsIgnoreCase("cd")){
            for (int entry = 0; entry < cds.length; entry++){
                if (cds[entry][titlePos].equalsIgnoreCase(title) &&
                        (cds[entry][genre1Pos].equalsIgnoreCase(genre1) || cds[entry][genre2Pos].equalsIgnoreCase(genre1)) &&
                        (cds[entry][genre1Pos].equalsIgnoreCase(genre2) || cds[entry][genre2Pos].equalsIgnoreCase(genre2)) &&
                        cds[entry][inOutPos].equalsIgnoreCase("in")){
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
     * @throws IOException will throw an error if it can not write to file. will make a new file so should not throw unless disk is full
     */
    public void save() throws IOException{
        //PrintWriters to write to files
        PrintWriter saveBooks = new PrintWriter(libraryName + "Books.txt");
        PrintWriter saveDVDs = new PrintWriter(libraryName + "DVDs.txt");
        PrintWriter saveCDs = new PrintWriter(libraryName + "CDs.txt");
        //save book data
        for (int line = 0; line < books.length; line++){
            for (int word = 0; word < parameterCount; word++){
                saveBooks.print(books[line][word]);
                if (word != parameterCount - 1)
                    saveBooks.print(splitter);
            }
            saveBooks.println();
        }
        //save DVD data
        for (int line = 0; line < dvds.length; line++){
            for (int word = 0; word < parameterCount; word++){
                saveDVDs.print(dvds[line][word]);
                if (word != parameterCount - 1)
                    saveDVDs.print(splitter);
            }
            saveDVDs.println();
        }
        //save CD data
        for (int line = 0; line < cds.length; line++){
            for (int word = 0; word < parameterCount; word++){
                saveCDs.print(cds[line][word]);
                if (word != parameterCount - 1)
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
