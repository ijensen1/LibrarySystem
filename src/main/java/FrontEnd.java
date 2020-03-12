import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class FrontEnd {
    private Scanner input;
    private ArrayList<Account> accounts;
    private ArrayList<Library> libraries;
    public FrontEnd() {
        String[] libNames = Persistence.loadLibraryNames();
        libraries = new ArrayList<Library>(libNames.length);
        for (int i = 0; i < libraries.size(); i++) {
            libraries.set(i, new Library(libNames[i]));
        }
        accounts = new ArrayList<Account>();
        accounts.addAll(Arrays.asList(Persistence.loadAccounts()));
    }
    public void run() {
        LibraryManager lm = new LibraryManager(); //Instancing it because we have instance variables already.
        input = new Scanner(System.in);

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
            userAccount = lm.login(email, password, accounts); //Login function searches accounts for a matching one based off of email and password hash
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
                String passhash = lm.passToHash(input.nextLine());
                accounts.add(new Account(firstName, lastName, phone, email, passhash)); //Adding the new account to the list
                Persistence.saveToFile(accounts.toArray(new Account[0]));
                lm.login(email, passhash, accounts); //Logging in, so userAccount is set correctly
                System.out.println("Registered! You are now logged in.");

            } else {
                System.err.println("Choice not recognized. Quitting...");
                System.exit(-1);
            }

        }
        //Done logging in! finally
        System.out.println("Libraries in system: ");
        for (Library lib : libraries)
            System.out.println(lib.getLibraryName());
        System.out.print("Please enter the name of the library you are in: "); //Library selection, so we know when to ask for transfer
        String libName = input.nextLine();
        userLibrary = lm.chooseLibrary(libName, libraries); //And where the user is currently
        if (userLibrary == null) {
            System.err.println("Library not found. Quitting...");
            System.exit(-1);
        }
        //Actions are: check in, search/checkout, transfer, quit
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
                    for (Borrowable book : userAccount.getCheckedOut()) {//Getting all books checked out, and set all of them to in
                        book.checkIn();
                    }
                    userAccount.setCheckedOut(new ArrayList<Borrowable>()); //Resetting back to empty after books are checked in
                }
            }
            if (choice.equals("search")) {
                System.out.println("Please select search type; title, creator, or genre: ");
                String searchType = input.nextLine().toLowerCase();
                System.out.println("Please enter item type: book, dvd, or cd: ");
                String itemType = input.nextLine();
                System.out.println("Please enter " + searchType + ": ");
                String searchTerm = input.nextLine();
                ArrayList<Borrowable> foundItems = new ArrayList<Borrowable>();
                String foundBranchName = userLibrary.getLibraryName(); //By default we found it here, but might change as we look
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
                        items = new ArrayList<Borrowable>(0);
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
