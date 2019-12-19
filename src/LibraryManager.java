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
  private static final int ERR_NOT_IN = -1;
  private static final int ERR_LIBRARY_NOT_FOUND = -2;
  public static void main(String[] args) {
      LibraryManager lm = new LibraryManager();

      Account userAccount;
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
          if (itemsHeld.length > 0) {
              System.out.println("Books held:");
              for (Borrowable book : itemsHeld) {
                  System.out.println(book.getTitle());
              }
          } else {
              System.out.println("No books held currently. Search for some! ");
          }
          System.out.println("===============Actions are:================");
          System.out.println(" checkin  checkout  search  transfer  quit ");
          System.out.print(": ");
          choice = lm.input.nextLine().toLowerCase();
          if (choice.equals("checkin")) {
              if (itemsHeld.length == 0) {
                  System.out.println("Error: No books held. ");
              } else {
                  System.out.println("Checking in all books...");
                  for (Borrowable book : itemsHeld) {
                      userLibrary.checkIn(book);
                  }
                  itemsHeld = new Borrowable[0]; //Resetting back to empty after books are checked in
              }
          }
          if (choice.equals("checkout")) {
              System.out.println("Not implemented yet");
          }
          if (choice.equals("search")) {
              System.out.println("Please select search type; title, creator, or genre: ");
              String searchType = lm.input.nextLine().toLowerCase();
              System.out.println("Please enter item type: 0 for books, 1 for DVDs, 2 for CDs: ");
              byte itemType = Byte.parseByte(lm.input.nextLine());
              System.out.println("Please enter " + searchType + ": ");
              String searchTerm = lm.input.nextLine();
              String bookID = "";
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

                  if (!lib.equals(userLibrary)) {
                      System.out.println("Found in other library");
                      local = false;
                      foundBranchName = lib.getLibraryName();
                      bookID = foundBranchName+Persistence.splitter+itemType+Persistence.splitter+lib.findIndex(itemType, foundItem.getTitle(), foundItem.getCreator())[0];
                  }
              }
              if (!foundItem.equals(null)) {
                  if (local) {
                      itemsHeld = Arrays.copyOf(itemsHeld, itemsHeld.length+1);
                      itemsHeld[itemsHeld.length-1] = foundItem;
                      System.out.println(foundItem.getTitle() + " is added to held books!");
                  } else {
                      System.out.println("Sorry! That item isn't in this branch. You'll need to transfer it from " + foundBranchName +
                              "first. Use book ID " + bookID); //ID might not stay constant across different launches of the app, but good enough for this
                  }
              } else {
                  System.out.println("Item not found!");
              }
          }
          if (choice.equals("transfer")) {
              System.out.println("Please enter book ID (from search): ");
              String[] bookID = lm.input.nextLine().split(Persistence.splitter);
              String branchName = bookID[0];
              byte itemType = Byte.parseByte(bookID[1]);
              int index = Integer.parseInt(bookID[2]);
              Library branch = null;
              for (Library lib : lm.libraries) {
                  if (lib.getLibraryName().equals(branchName)) {
                      branch = lib;
                  }
              }
              lm.transfer(libName, userLibrary.getLibraryName(), branch.getIndex(itemType, index));
              System.out.println("Item transferred successfully.");
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
  public int transfer(String libname1, String libname2, Borrowable item) {
      if (item.getInOut().equals("out")) {
          return LibraryManager.ERR_NOT_IN;
      }
      Library lib1 = null;
      Library lib2 = null;
      for (Library lib : libraries) {
          if (lib.getLibraryName().equals(libname1)) {
              lib1 = lib;
          }
      }
      if (lib1 == null) {
          return LibraryManager.ERR_LIBRARY_NOT_FOUND;
      }

      for (Library lib : libraries) {
          if (lib.getLibraryName().equals(libname2)) {
              lib2 = lib;
          }
      }
      if (lib2 == null) {
          return LibraryManager.ERR_LIBRARY_NOT_FOUND;
      }

      lib1.remove(item.getType(), item.getTitle(), item.getCreator());
      lib2.add(item.getType(), item.getTitle(), item.getCreator(), item.getGenre1(), item.getGenre2());
      return 0;
  }
}
