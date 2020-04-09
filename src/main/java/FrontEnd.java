import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Class to provide an interface for the end user. Calls many methods from LibraryManager to do this.
 * Also handles account creation
 */
public class FrontEnd {
    private ArrayList<Account> accounts;
    private ArrayList<Library> libraries;

    /**
     * Constructor that gathers library and account data.
     */
    public FrontEnd() {
        String[] libNames = Persistence.loadLibraryNames();
        if (libNames == null) {
            System.out.println("Error loading library names. Exiting...");
            System.exit(-1);
        }
        libraries = new ArrayList<>();
        for (String name : libNames) {
            libraries.add(new Library(name));
        }
        accounts = new ArrayList<>();
        accounts.addAll(Arrays.asList(Persistence.loadAccounts()));
    }

    /**
     * Method to give prompts and collect responses from the user, performing requested actions.
     */
    public void run() {
        LibraryManager lm = new LibraryManager(); //Instancing it because we have instance variables already.
        Scanner input = new Scanner(System.in);

        Account userAccount = null; //This is the user account you log in to at the start.
        Library userLibrary; //This is the library location you select.
        System.out.println("Welcome to the TVHS Library System. Please log in or register: ");
        System.out.println("    ==login==                                  ==register==    ");
        String choice = input.nextLine().toLowerCase();
        if (choice.equals("login")) {
            System.out.print("Please enter your email address: ");
            String email = input.nextLine();
            System.out.print("Please enter your password: ");
            String password = input.nextLine();
            userAccount = lm.login(email, LibraryManager.passToHash(password), accounts); //Login function searches accounts for a matching one based off of email and password hash

            if (userAccount == null) {
                System.err.println("User account not found. Quitting...");
                System.exit(-1);
            }
            System.out.println("Welcome, " + userAccount.getFirstName() + "!");
        } else {
            if (choice.equals("register")) { //New account! Getting information...
                System.out.println("Thank you for registering! Please enter your first name: ");
                String firstName = input.nextLine();
                System.out.print("Please enter your last name: ");
                String lastName = input.nextLine();
                System.out.print("Please enter your phone number: ");
                String phone = input.nextLine();
                System.out.print("Please enter your email address. You'll use this for logging in: ");
                String email = input.nextLine();
                System.out.println("Please choose a password: ");
                String passhash = LibraryManager.passToHash(input.nextLine());
                accounts.add(new Account(firstName, lastName, phone, email, passhash)); //Adding the new account to the list
                Persistence.saveToFile(accounts.toArray(new Account[0]));
                userAccount = lm.login(email, passhash, accounts); //Logging in, so userAccount is set correctly
                System.out.println("Registered! You are now logged in, " + userAccount.getFirstName());

            } else {
                System.err.println("Choice not recognized. Quitting...");
                System.exit(-1);
            }

        }
        //Done logging in! finally
        System.out.println("Libraries in system: ");
        for (Library lib : libraries)
            System.out.println(lib.getLibraryName());
        System.out.print("Please enter the name of the library you are in: ");
        String libName = input.nextLine();
        userLibrary = lm.chooseLibrary(libName, libraries); //Where the user is currently, also which books to load into inventory

        if (userLibrary == null) {
            System.err.println("Library not found. Quitting...");
            System.exit(-1);
        }
        //Actions are: check in, search/checkout, quit

        System.out.println(); //extra line to clean things up
        boolean done = false;
        while (!done) { //This is the main frontend loop. All commands entered here
            if (userAccount.getCheckedOut().size() > 0) {
                System.out.println("Books held:");
                for (Borrowable book : userAccount.getCheckedOut()) {
                    System.out.println(book.getTitle()); //Letting the user know what books they have out
                }
            } else {
                System.out.println("No books checked out currently. Search for some! ");
            }

            System.out.println("===============Actions are:================");
            System.out.println("    checkin  search/checkout  quit   ");
            System.out.print(": ");
            choice = input.nextLine().toLowerCase();
            if (choice.equals("checkin")) {
                if (userAccount.getCheckedOut().size() == 0) {
                    System.out.println("Error: No books held. ");
                } else {
                    System.out.println("Checking in all books...");
                    userAccount.setCheckedOut(new ArrayList<Borrowable>()); //Remove books from user inventory/hand
                }
            }
            if (choice.equals("search")) {
                System.out.println("Please select search type; title, creator, or genre: ");
                String searchType = input.nextLine().toLowerCase();
                System.out.println("Please enter item type: book, dvd, or cd: ");
                String itemType = input.nextLine();
                System.out.println("Please enter " + searchType + ": ");
                String searchTerm = input.nextLine();
                ArrayList<Borrowable> foundItems = new ArrayList<>();
                for (Library lib : libraries) { //Searching with different search types
                    try {
                        if (searchType.equals("title")) {
                            ArrayList<Borrowable> results = lib.searchTitle(itemType, searchTerm);
                            if (results.size() > 0) { //Did we find anything?
                                foundItems.addAll(results);
                            }
                            break;
                        }
                        if (searchType.equals("creator")) {
                            ArrayList<Borrowable> results = lib.searchPerson(itemType, searchTerm);
                            if (results.size() > 0) {
                                foundItems.addAll(results);
                            }
                            break;
                        }
                        if (searchType.equals("genre")) {
                            ArrayList<Borrowable> results = lib.searchGenre(itemType, searchTerm);
                            if (results.size() > 0) {
                                foundItems.addAll(results);
                            }
                            break;
                        }
                        if (foundItems.isEmpty()) {
                            System.out.println("No items found.");
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("We done messed up: " + e.toString());
                        System.exit(-1);
                    }
                }
                if (!foundItems.isEmpty()) {
                    System.out.println("Found items:");
                    for (int i = 0; i < foundItems.size(); i++) {
                        System.out.println(i + ": " + foundItems.get(i).getTitle());
                    }
                    System.out.print("Please select which item you want:");
                    int index = Integer.parseInt(input.nextLine());
                    foundItems.get(index).checkOut();
                    ArrayList<Borrowable> items; //Get items, or empty list if none checked out already
                    try {
                        items = userAccount.getCheckedOut();
                    } catch (NullPointerException e) {
                        items = new ArrayList<>(0);
                    }
                    items.add(foundItems.get(index));
                    userAccount.setCheckedOut(items); //Save to account
                    System.out.println("Checked in " + foundItems.get(index).getTitle());

                } else {
                    System.out.println("Item not found!");
                }
            }

            if (choice.equals("quit")) {
                System.out.println("Quitting..."); //End the loop, which calls lm.close() and then exits main
                done = true;
            }
        }
        lm.close(accounts, libraries);
    }
}
