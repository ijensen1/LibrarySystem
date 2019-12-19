import java.io.File;
import java.io.IOException;
import java.nio.file.LinkPermission;
import java.util.Scanner;
import java.util.Arrays;
import java.io.*;

public class LibraryManager {
  private Account[] accounts;
  private Library[] libraries;
  private Scanner input;
  public static void main(String[] args) {
      LibraryManager lm = new LibraryManager();

      Account userAccount = null;
      Library userLibrary;
      Borrowable[] itemsHeld = new Borrowable[0]; //This holds all the items you are checking out or in. It's literally what you are holding.
      System.out.println("Welcome to the TVHS Library System. Please log in or register: ");
      System.out.println("    ==login==                                  ==register==    ");
      String choice = lm.input.nextLine().toLowerCase();
      if (choice.equals("login")) {
          System.out.print("Please enter your email address");
          userAccount = lm.login(lm.input.nextLine());
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
      System.out.print("Please enter the name of the library you are in: ");
      String libName = lm.input.nextLine();
      userLibrary = lm.chooseLibrary(libName);
      if (userLibrary == null) {
          System.err.println("Library not found. Quitting...");
          System.exit(-1);
      }
      //Actions are: check in, check out, transfer, search
      System.out.println(); //extra line to clean things up
      boolean done = false;
      while (!done) {
          if (userAccount.getCheckedOut().length > 0) {
              System.out.println("Books held:");
              for (Borrowable book : userAccount.getCheckedOut()) {
                  System.out.println(book.getTitle());
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
                  for (Borrowable book : userAccount.getCheckedOut()) {
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
              String foundBranchName = userLibrary.getLibraryName();
              boolean local = true;
              for (Library lib : lm.libraries) {
                  if (searchType.equals("title")) {
                      Borrowable[] results = lib.searchName(itemType, searchTerm);
                      if (results.length > 0) {
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

                  if (foundItem.getHome().equals(userLibrary.getLibraryName())) {
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
                          Borrowable[] items;
                          try {
                              items = userAccount.getCheckedOut();
                          } catch (NullPointerException e) {
                              items = new Borrowable[0];
                          }
                          items = Arrays.copyOf(items, items.length+1);
                          items[items.length-1] = foundItem;
                          userAccount.setCheckedOut(items);
                      }
                  } else {
                      System.out.println("Sorry! That item isn't in this branch. You'll need to transfer it from " + foundBranchName + " first.");
                  }
              } else {
                  System.out.println("Item not found!");
              }
          }
          if (choice.equals("transfer")) {

          }
          if (choice.equals("quit")) {
              System.out.println("Quitting...");
              done = true;
          }
      }
      lm.close();
  }
  public LibraryManager() {
      String[] libNames = Persistence.loadLibraryNames();
      input = new Scanner(System.in);
      libraries = new Library[libNames.length];
      for (int i = 0; i < libraries.length; i++) {
        libraries[i] = new Library(libNames[i]);
      }
      accounts = Persistence.loadAccounts();
  }
  public Account login(String email) {
      for (Account acnt : accounts) {
          if (acnt.getEmail().equals(email)) {
              return acnt;
          }
      }
      return null;
  }
  public Library chooseLibrary(String libName) {
      for (Library lib : libraries) {
          if (lib.getLibraryName().equals(libName)) {
              return lib;
          }
      }
      return null;
  }
  public void close() {
    Persistence.saveToFile(accounts);
    for (Library lib : libraries) {
      lib.save();
    }
  }
}
