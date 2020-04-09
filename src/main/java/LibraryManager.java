
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Frontend that instances the accounts, loads the libraries, and takes input.
 * Never fear, main is here!
 *
 * Calls FrontEnd and exposes some utility methods for it to use.
 */
public class LibraryManager {
    /**
     * Main is here. Asks the user which mode they are using (admin or frontend?) and then passes execution to the requested class.
     * Also checks password before launching Admin mode.
     * @param args Arguments passed in from command line. Ignored here.
     */
  public static void main(String[] args) {
      Scanner input = new Scanner(System.in);
      System.out.print("Are you running in admin mode? true/false");
      boolean adminMode = Boolean.parseBoolean(input.nextLine());
      if (adminMode) {
          while (true) {
              System.out.print("Please enter your password: ");
              if (!LibraryManager.passToHash(input.nextLine()).equals(AdminConsole.PASS)) { //Checks password versus stored hash
                  System.out.println("Password invalid. Please try again.");
              } else {
                  System.out.println("Recognized.");
                  break;
              }
          }
          AdminConsole ac = new AdminConsole();
          try {
              ac.run();
          } catch (Exception e) {
              e.printStackTrace();
              System.exit(0);
          }

      } else {
          FrontEnd fe = new FrontEnd(); //Not admin mode, start the normal interface

          try {
              fe.run();
          } catch (Exception e) {
              e.printStackTrace();
              System.exit(0);
          }
      }

  }

    /**
     * Base constructor for library manager. Called by main, don't worry about this.
     */
  public LibraryManager() {

  }
  /**
   * Sets active account, given email
   * @param email the email to log in with
   * @param passhash the hash of the inputted password to check against the stored hash
   * @param accounts the list of accounts to search through
   * @return the account selected
   */
  public Account login(String email, String passhash, ArrayList<Account> accounts) {
      for (Account acnt : accounts) {
          if (acnt.getEmail().equals(email)) { //If email and password match, return the account
              if (acnt.getPasshash().equals(passhash)) {
                  return acnt;
              }
          }
      }
      return null; //Otherwise return null
  }
  /**
   * Find library from loaded list by name
   * @param libName the name to search for
   * @param libraries List of libraries to search
   * @return the library found, or null if none found
   */
  public Library chooseLibrary(String libName, ArrayList<Library> libraries) {
      for (Library lib : libraries) {
          if (lib.getLibraryName().equalsIgnoreCase(libName)) { //Find the library with the matching name, if it exists
              return lib;
          }
      }
      return null; //Otherwise return null
  }
  /**
   * Method to save all resources to files. main calls this just before exiting.
   * @param accounts list of accounts to save
   * @param libraries list of libraries to save
   */
  public void close(ArrayList<Account> accounts, ArrayList<Library> libraries) {
    Persistence.saveToFile(accounts.toArray(new Account[0])); //Passes Persistence the list of accounts to save
    for (Library lib : libraries) {
      lib.save(); //Calls save on each loaded library
    }

  }

    /**
     * Method to take a String and get the SHA-512 hash of it
     * @param password The string to hash
     * @return The hash created
     */
  public static String passToHash(String password) {
      try {
          MessageDigest digest = MessageDigest.getInstance("SHA-512"); //Get a instance to perform the hashing with
          byte[] hash = digest.digest(
                  password.getBytes(StandardCharsets.UTF_8)); //Hash the string, returns an array of bytes
          StringBuilder hexString = new StringBuilder();
          for (byte b : hash) { //Convert the bytes back into a hex string
              String hex = Integer.toHexString(0xff & b);
              if (hex.length() == 1) hexString.append('0');
              hexString.append(hex);
          }
          return hexString.toString(); //Return that
      } catch (NoSuchAlgorithmException e) { //Just in case the system doesn't support SHA-512
          System.out.println("Error! " + e.toString());
          System.exit(-1);
      }
      return null; //Something went wrong, return null
  }
}
