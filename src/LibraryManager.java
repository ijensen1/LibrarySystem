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
          System.out.println("===============Actions are:================");
          System.out.println(" checkin  checkout  search  transfer  quit ");
          System.out.print(": ");
          choice = lm.input.nextLine();
          if (choice.equals("checkin")) {
              System.out.println("Not implemented yet");
          }
          if (choice.equals("checkout")) {
              System.out.println("Not implemented yet");
          }
          if (choice.equals("search")) {
              System.out.println("Not implemented yet");
          }
          if (choice.equals("transfer")) {
              System.out.println("Not implemented yet");
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
