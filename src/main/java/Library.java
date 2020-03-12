import java.util.ArrayList;

/**
 * Class to handle inventory of a specific library, all method inputs are case in-sensitive. Uses 2d arrays to hold information and has methods for data query.
 */
public class Library {
    //Variables for library data
    private String libraryName;
    private ArrayList<Book> books = new ArrayList<>(0);
    private ArrayList<CD> cds = new ArrayList<>(0);
    private ArrayList<DVD> dvds = new ArrayList<>(0);

    //error messages
    private Exception invalidType = new Exception("Exception: Invalid Entry Type");

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
     * @exception Exception will not take in a Borrowable that's not a Book, DVD, or CD.
     */
    void add(Borrowable borrowable) throws Exception {
        if (borrowable instanceof Book){
            books.add((Book) borrowable);
        } else if (borrowable instanceof CD){
            cds.add((CD) borrowable);
        } else if (borrowable instanceof  DVD){
            dvds.add((DVD) borrowable);
        } else {
            throw invalidType;
        }
    }

    /**
     * Remove an entry from a library's inventory.
     * @param borrowable the Borrowable to remove from the inventory.
     * @exception Exception will not take in a Borrowable that's not a Book, DVD, or CD.
     */
    public void remove(Borrowable borrowable) throws Exception {
        if (borrowable instanceof Book){
            books.remove(borrowable);
        } else if (borrowable instanceof CD){
            cds.remove(borrowable);
        } else if (borrowable instanceof DVD){
            dvds.remove(borrowable);
        } else {
            throw invalidType;
        }
    }

    /**
     * Search inventory based on type and title.
     * @param type whether a book, cd, or dvd is wanted.
     * @param title title of what you're searching for.
     * @return an ArrayList of works with that title.
     * @exception Exception will not take in a Borrowable that's not a Book, DVD, or CD.
     */
    public ArrayList<Borrowable> searchTitle(String type, String title) throws Exception {
        ArrayList<Borrowable> results = new ArrayList<>(10);
        ArrayList<Borrowable> searchThrough = new ArrayList<>();
        if (type.equalsIgnoreCase("book")){
            searchThrough.addAll(books);
        } else if (type.equalsIgnoreCase("dvd")){
            searchThrough.addAll(dvds);
        } else if (type.equalsIgnoreCase("cd")){
            searchThrough.addAll(cds);
        } else {
            throw invalidType;
        }

        for (Borrowable item : searchThrough) {
            if (title.equalsIgnoreCase(item.getTitle())) {
                results.add(item);
            }
        }

        return results;
    }

    /**
     * Search a library's inventor based on type and person.
     * @param type whether a book, dvd, or cd is wanted.
     * @param person the person being searched for, could be author, artist, director, actor, etc.
     * @return an ArrayList of Borrowables of works featuring that person.
     * @exception Exception will not take in a Borrowable that's not a Book, DVD, or CD.
     */
    public ArrayList<Borrowable> searchPerson(String type, String person) throws Exception {
        ArrayList<Borrowable> results = new ArrayList<>(10);
        ArrayList<Borrowable> searchThrough = new ArrayList<>();
        if (type.equalsIgnoreCase("book")){
            searchThrough.addAll(books);
        } else if (type.equalsIgnoreCase("dvd")){
            searchThrough.addAll(dvds);
        } else if (type.equalsIgnoreCase("cd")){
            searchThrough.addAll(cds);
        } else {
            throw invalidType;
        }

        for (Borrowable item : searchThrough){
            for (String persons : item.getPeople()){
                if (persons.equalsIgnoreCase(person)){
                    results.add(item);
                    break;
                }
            }
        }

        return results;
    }

    /**
     * Search inventory based on type and genre.
     * @param genre genre being searched for.
     * @return an ArrayList of Borrowables of works of that genre.
     * @exception Exception will not take in a Borrowable that's not a Book, DVD, or CD.
     */
    public ArrayList<Borrowable> searchGenre(String type, String genre) throws Exception {
        ArrayList<Borrowable> results = new ArrayList<>(10);
        ArrayList<Borrowable> searchThrough = new ArrayList<>();
        if (type.equalsIgnoreCase("book")){
            searchThrough.addAll(books);
        } else if (type.equalsIgnoreCase("dvd")){
            searchThrough.addAll(dvds);
        } else if (type.equalsIgnoreCase("cd")){
            searchThrough.addAll(cds);
        } else {
            throw invalidType;
        }

        for (Borrowable item : searchThrough){
            for (String genres : item.getGenres()){
                if (genres.equalsIgnoreCase(genre)){
                    results.add(item);
                    break;
                }
            }
        }

        return results;
    }

    /**
     * Save library's collections to their appropriate files.
     */
    public void save() {
        Persistence.saveToFile(libraryName + "/books.txt", books.toArray(new Borrowable[0]));
        Persistence.saveToFile(libraryName + "/dvds.txt", dvds.toArray(new Borrowable[0]));
        Persistence.saveToFile(libraryName + "/cds.txt", cds.toArray(new Borrowable[0]));
    }
}
