import java.io.File;
import java.io.IOException;
import java.nio.file.LinkPermission;
import java.util.Scanner;
import java.util.Arrays;
import java.io.*;
/**
 * Frontend that instances the accounts, loads the libraries, and takes input.
 * Never fear, main is here!
 *
 * There could have been a separate FrontEnd class, but all it really would have been doing is calling methods from this one, so I held off.
 */
public class LibraryManager {
  private Account[] accounts;
  private Library[] libraries;
  private Scanner input;
  public static void main(String[] args) {
      LibraryManager lm = new LibraryManager(); //Instancing it because we have instance variables already.

      Account userAccount = null; //This is the user account you log in to at the start.
      Library userLibrary; //This is the library location you select.
      System.out.println("Welcome to the TVHS Library System. Please log in or register: ");
      System.out.println("    ==login==                                  ==register==    ");
      String choice = lm.input.nextLine().toLowerCase();
      if (choice.equals("login")) {
          System.out.print("Please enter your email address");
          userAccount = lm.login(lm.input.nextLine()); //Login function searches accounts for a matching one based off of email
          if (userAccount == null) {
              System.err.println("User account not found. Quitting...");
              System.exit(-1);
          }
          System.out.println("Welcome, " + userAccount.getFirstName() + "!");
      } else {
          if (choice.equals("register")) { //New account! Getting information...
              System.out.println("Thank you for registering! Please enter your first name: ");
              String firstName = lm.input.nextLine();
              System.out.print("Please enter your last name: ");
              String lastName = lm.input.nextLine();
              System.out.print("Please enter your phone number: ");
              String phone = lm.input.nextLine();
              System.out.print("Please enter your email address. You'll use this for logging in: ");
              String email = lm.input.nextLine();
              lm.accounts = Arrays.copyOf(lm.accounts, lm.accounts.length+1);
              lm.accounts[lm.accounts.length-1] = new Account(firstName, lastName, phone, email); //Adding the new account to the list
              Persistence.saveToFile(lm.accounts);
              lm.login(email); //Logging in, so userAccount is set correctly
              System.out.println("Registered! You are now logged in.");

          } else {
              System.err.println("Choice not recognized. Quitting...");
              System.exit(-1);
          }

      }
      //Done logging in! finally
      System.out.println("Libraries in system: ");
      for (Library lib : lm.libraries)
          System.out.println(lib.getLibraryName());
      System.out.print("Please enter the name of the library you are in: "); //Library selection, so we know when to ask for transfer
      String libName = lm.input.nextLine();
      userLibrary = lm.chooseLibrary(libName); //And where the user is currently
      if (userLibrary == null) {
          System.err.println("Library not found. Quitting...");
          System.exit(-1);
      }
      //Actions are: check in, search/checkout, transfer, quit
      System.out.println(); //extra line to clean things up
      boolean done = false;
      while (!done) { //This is the main frontend loop. All commands entered here
          System.out.println(userAccount.getCheckedOut().length);
          if (userAccount.getCheckedOut().length > 0) {
              System.out.println("Books held:");
              for (Borrowable book : userAccount.getCheckedOut()) {
                  System.out.println(book.getTitle()); //Letting the user know what books they have out
              }
          } else {
              System.out.println("No books checked out currently. Search for some! ");
          }
          System.out.println("===============Actions are:================");
          System.out.println("  checkin  search/checkout  transfer  quit ");
          System.out.print(": ");
          choice = lm.input.nextLine().toLowerCase();
          if (choice.equals("checkin")) {
              if (userAccount.getCheckedOut().length == 0) {
                  System.out.println("Error: No books held. ");
              } else {
                  System.out.println("Checking in all books...");
                  for (Borrowable book : userAccount.getCheckedOut()) {//Getting all books checked out, and set all of them to in
                      book.checkIn();
                  }
                  userAccount.setCheckedOut(new Borrowable[0]); //Resetting back to empty after books are checked in
              }
          }
          if (choice.equals("search")) {
              System.out.println("Please select search type; title, creator, or genre: ");
              String searchType = lm.input.nextLine().toLowerCase();
              System.out.println("Please enter item type: 0 for books, 1 for DVDs, 2 for CDs: ");
              byte itemType = Byte.parseByte(lm.input.nextLine());
              System.out.println("Please enter " + searchType + ": ");
              String searchTerm = lm.input.nextLine();
              Borrowable foundItem = null;
              String foundBranchName = userLibrary.getLibraryName(); //By default we found it here, but might change as we look
              boolean local = true;
              for (Library lib : lm.libraries) { //Searching with different search types
                  if (searchType.equals("title")) {
                      Borrowable[] results = lib.searchName(itemType, searchTerm);
                      if (results.length > 0) { //Did we find anything?
                          foundItem = results[0];
                      }
                      break;
                  }
                  if (searchType.equals("creator")) {
                      Borrowable[] results = lib.searchCreator(itemType, searchTerm);
                      if (results.length > 0) {
                          foundItem = results[0];
                      }
                      break;
                  }
                  if (searchType.equals("genre")) {
                      Borrowable[] results = lib.searchGenre(itemType, searchTerm);
                      if (results.length > 0) {
                          foundItem = results[0];
                      }
                      break;
                  }

                  if (!foundItem.getHome().equals(userLibrary.getLibraryName())) { //Did we find this item here or somewhere else?
                      System.out.println("Found in other library");
                      local = false;
                      foundBranchName = lib.getLibraryName();
                  }
              }
              if (!foundItem.equals(null)) {
                  if (local) {
                      System.out.println("Found " + foundItem.getTitle() + "! Would you like to check it out? Y/N");
                      if (lm.input.nextLine().equalsIgnoreCase("y")) {
                          foundItem.checkOut();
                          Borrowable[] items; //Get items, or empty list if none checked out already
                          try {
                              items = userAccount.getCheckedOut();
                          } catch (NullPointerException e) {
                              items = new Borrowable[0];
                          }
                          items = Arrays.copyOf(items, items.length+1);
                          items[items.length-1] = foundItem; //Add new item
                          userAccount.setCheckedOut(items); //Save to account
                      }
                  } else {
                      System.out.println("Sorry! That item isn't in this branch. You'll need to transfer it from " + foundBranchName + " first.");
                  }
              } else {
                  System.out.println("Item not found!");
              }
          }
          if (choice.equals("transfer")) { //Transfer item from one library inventory to another and check out
              System.out.println("Which branch are you transferring from? ");
              Library otherLib = lm.chooseLibrary(lm.input.nextLine());
              if (!otherLib.equals(null)) { //found library?
                  System.out.println("Please enter title of the item you are looking for: ");
                  String title = lm.input.nextLine();
                  System.out.println("Please enter the creator of the item you are looking for: ");
                  String creator = lm.input.nextLine();
                  System.out.println("Please enter item type: 0 for books, 1 for DVDs, 2 for CDs: ");
                  byte itemType = Byte.parseByte(lm.input.nextLine());
                  Borrowable item = otherLib.getIndex(itemType, otherLib.findIndex(itemType, title, creator)[0]);
                  if (!item.equals(null)) { //found item?
                      userLibrary.add(itemType, item.getTitle(), item.getCreator(), item.getGenre1(), item.getGenre2());
                      otherLib.remove(itemType, item); //Take out from one library, add to another
                      item.checkOut();
                      Borrowable[] items;
                      try {
                          items = userAccount.getCheckedOut();
                      } catch (NullPointerException e) {
                          items = new Borrowable[0];
                      }
                      items = Arrays.copyOf(items, items.length+1);//Add to user account inventory
                      items[items.length-1] = item;
                      userAccount.setCheckedOut(items);

                  } else {
                      System.out.println("Item not found");
                  }
              } else {
                  System.out.println("Library not found");
              }
          }
          if (choice.equals("quit")) {
              System.out.println("Quitting..."); //End the loop, which calls lm.close() and then exits main
              done = true;
          }
      }
      lm.close();
  }

    /**
     * Base constructor for library manager. Called by main, don't worry about this.
     */
  public LibraryManager() {
      String[] libNames = Persistence.loadLibraryNames();
      input = new Scanner(System.in);
      libraries = new Library[libNames.length];
      for (int i = 0; i < libraries.length; i++) {
        libraries[i] = new Library(libNames[i]);
      }
      accounts = Persistence.loadAccounts();
  }
  /**
   * Sets active account, given email
   * @param email the email to log in with
   * @return the account selected
   */
  public Account login(String email) {
      for (Account acnt : accounts) {
          if (acnt.getEmail().equals(email)) {
              return acnt;
          }
      }
      return null;
  }
  /**
   * Find library from loaded list by name
   * @param libName the name to search for
   * @return the library found, or null if none found
   */
  public Library chooseLibrary(String libName) {
      for (Library lib : libraries) {
          if (lib.getLibraryName().equals(libName)) {
              return lib;
          }
      }
      return null;
  }
  /**
   * Method to save all resources to files. main calls this just before exiting.
   */
  public void close() {
    Persistence.saveToFile(accounts);
    for (Library lib : libraries) {
      lib.save();
    }
  }
}
