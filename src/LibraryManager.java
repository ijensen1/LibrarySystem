
import java.util.Scanner;
import java.util.Arrays;

/**
 * Frontend that instances the accounts, loads the libraries, and takes input.
 * Never fear, main is here!
 *
 * There could have been a separate FrontEnd class, but all it really would have been doing is calling methods from this one, so I held off.
 */
public class LibraryManager {

  public static void main(String[] args) {
      FrontEnd fe = new FrontEnd();
      fe.run();
  }

    /**
     * Base constructor for library manager. Called by main, don't worry about this.
     */
  public LibraryManager() {

  }
  /**
   * Sets active account, given email
   * @param email the email to log in with
   * @return the account selected
   */
  public Account login(String email, Account[] accounts) {
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
  public Library chooseLibrary(String libName, Library[] libraries) {
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
  public void close(Account[] accounts, Library[] libraries) {
    Persistence.saveToFile(accounts);
    for (Library lib : libraries) {
      lib.save();
    }
  }
}
