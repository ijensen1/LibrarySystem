import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class AdminConsole {
    private ArrayList<Account> accounts;
    private ArrayList<Library> libraries;
    private static final String pass = "53fb8c10eded3d76424272f61308e0de71044d730834f9c7278ebe68a3735629631b37497f6b93da5141181aebcfaa9944a5cb2941c528a6c0cef9e002e7d7c5";
    public AdminConsole() {
        String[] libNames = Persistence.loadLibraryNames();
        libraries = new ArrayList<>(libNames.length);
        for (int i = 0; i < libraries.size(); i++) {
            libraries.set(i, new Library(libNames[i]));
        }
        accounts = new ArrayList<>();
        accounts.addAll(Arrays.asList(Persistence.loadAccounts()));
    }

    public void run() throws Exception {
        Scanner input = new Scanner(System.in);
        LibraryManager lm = new LibraryManager();
        System.out.println("Welcome to the Library Administrator access panel");

        while (true) {
            System.out.print("Please enter your password: ");
            if (!lm.passToHash(input.nextLine()).equals(pass)) {
                System.out.println("Password invalid. Please try again.");
            } else {
                System.out.println("Recognized.");
                break;
            }
        }
        System.out.println("Libraries in system: ");
        for (Library lib : libraries)
            System.out.println(lib.getLibraryName());
        System.out.print("Please enter the name of the library you are in: "); //Library selection, so we know when to ask for transfer
        String libName = input.nextLine();
        Library userLibrary = lm.chooseLibrary(libName, libraries); //And where the user is currently

        System.out.println("===============Actions are:================");
        System.out.println("    additem                        quit    ");
        String choice = input.nextLine();
        while (true) {
            if (choice.equals("additem")) {
                System.out.print("Please enter the type of item to add: book, cd, dvd, picturebook");
                String itemType = input.nextLine();
                if (itemType.equals("book")) {
                    System.out.print("Please enter the title of the " + itemType + ": ");
                    String title = input.nextLine();
                    ArrayList<String> genres = new ArrayList<>();
                    while (true) {
                        System.out.print("Please enter a genre, or done if all genres have been entered: ");
                        String genre = input.nextLine();
                        if (!genre.equals("done")) {
                            genres.add(genre);
                        } else {
                            break;
                        }
                    }
                    System.out.print("Please enter the ISBN of the book: ");
                    String isbn = input.nextLine();
                    System.out.print("Please enter the author of the book: ");
                    String author = input.nextLine();
                    System.out.print("Is this book hardcover? true/false");
                    boolean hardcover = Boolean.parseBoolean(input.nextLine());
                    userLibrary.add(new Book(userLibrary.getLibraryName(), title, genres.toArray(new String[0]), author, isbn, hardcover));
                }
                if (itemType.equals("cd")) {
                    System.out.print("Please enter the title of the " + itemType + ": ");
                    String title = input.nextLine();
                    ArrayList<String> genres = new ArrayList<>();
                    while (true) {
                        System.out.print("Please enter a genre, or done if all genres have been entered: ");
                        String genre = input.nextLine();
                        if (!genre.equals("done")) {
                            genres.add(genre);
                        } else {
                            break;
                        }
                    }
                    System.out.print("Please enter the producer of the CD: ");
                    String producer = input.nextLine();
                    System.out.print("Please enter the artist of the CD: ");
                    String artist = input.nextLine();
                    System.out.print("Please enter the rating of the CD: ");
                    String rating = input.nextLine();
                    ArrayList<String> songs = new ArrayList<>();
                    while (true) {
                        System.out.print("Please enter a song, or done if all songs have been entered: ");
                        String song = input.nextLine();
                        if (!song.equals("done")) {
                            songs.add(song);
                        } else {
                            break;
                        }
                    }
                    userLibrary.add(new CD(userLibrary.getLibraryName(), title, genres.toArray(new String[0]), producer, artist, rating, songs.toArray(new String[0])));

                }
                if (itemType.equals("dvd")) {
                    System.out.print("Please enter the title of the " + itemType + ": ");
                    String title = input.nextLine();
                    ArrayList<String> genres = new ArrayList<>();
                    while (true) {
                        System.out.print("Please enter a genre, or done if all genres have been entered: ");
                        String genre = input.nextLine();
                        if (!genre.equals("done")) {
                            genres.add(genre);
                        } else {
                            break;
                        }
                    }
                    System.out.print("Please enter the rating of the DVD: ");
                    String rating = input.nextLine();
                    System.out.print("Please enter the director of the DVD: ");
                    String director = input.nextLine();
                    ArrayList<String> actors = new ArrayList<>();
                    while (true) {
                        System.out.print("Please enter a actor, or done if all actors have been entered: ");
                        String actor = input.nextLine();
                        if (!actor.equals("done")) {
                            actors.add(actor);
                        } else {
                            break;
                        }
                    }
                    userLibrary.add(new DVD(userLibrary.getLibraryName(), title, genres.toArray(new String[0]), rating, director, actors.toArray(new String[0])));
                }
                if (itemType.equals("picturebook")) {
                    System.out.print("Please enter the title of the " + itemType + ": ");
                    String title = input.nextLine();
                    ArrayList<String> genres = new ArrayList<>();
                    while (true) {
                        System.out.print("Please enter a genre, or done if all genres have been entered: ");
                        String genre = input.nextLine();
                        if (!genre.equals("done")) {
                            genres.add(genre);
                        } else {
                            break;
                        }
                    }
                    System.out.print("Please enter the ISBN of the picture book: ");
                    String isbn = input.nextLine();
                    System.out.print("Please enter the author of the picture book: ");
                    String author = input.nextLine();
                    System.out.print("Is this picture book hardcover? true/false");
                    boolean hardcover = Boolean.parseBoolean(input.nextLine());
                    System.out.println("Please enter the illustrator of the book");
                    String illustrator = input.nextLine();
                    System.out.print("Is this picture book a pop up book? true/false");
                    boolean isPopUpBook = Boolean.parseBoolean(input.nextLine());
                    userLibrary.add(new PictureBook(userLibrary.getLibraryName(), title, genres.toArray(new String[0]), author, isbn, hardcover, illustrator, isPopUpBook));
                }
            }
            if (choice.equals("quit")) {
                System.out.println("Quitting...");
                break;
            }
        }
        lm.close(accounts, libraries);
    }

}