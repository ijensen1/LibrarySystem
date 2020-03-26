
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

  public static void main(String[] args) {
      Scanner input = new Scanner(System.in);
      System.out.print("Are you running in admin mode? true/false");
      boolean adminMode = Boolean.parseBoolean(input.nextLine());
      if (adminMode) {
          while (true) {
              System.out.print("Please enter your password: ");
              if (!LibraryManager.passToHash(input.nextLine()).equals(AdminConsole.pass)) {
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
              System.out.println(e.toString());
              System.exit(0);
          }

      } else {
          FrontEnd fe = new FrontEnd();

          try {
              fe.run();
          } catch (Exception e) {
              System.out.println(e.toString());
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
   * @return the account selected
   */
  public Account login(String email, String passhash, ArrayList<Account> accounts) throws Exception {
      System.out.println("Password not implemented!");
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
  public Library chooseLibrary(String libName, ArrayList<Library> libraries) {
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
  public void close(ArrayList<Account> accounts, ArrayList<Library> libraries) {
    Persistence.saveToFile(accounts.toArray(new Account[accounts.size()]));
    for (Library lib : libraries) {
      lib.save();
    }

  }

  public static String passToHash(String password) {
      try {
          MessageDigest digest = MessageDigest.getInstance("SHA-512");
          byte[] hash = digest.digest(
                  password.getBytes(StandardCharsets.UTF_8));
          StringBuilder hexString = new StringBuilder();
          for (byte b : hash) {
              String hex = Integer.toHexString(0xff & b);
              if (hex.length() == 1) hexString.append('0');
              hexString.append(hex);
          }
          return hexString.toString();
      } catch (NoSuchAlgorithmException e) {
          System.out.println("Error! " + e.toString());
          System.exit(-1);
      }
      return null;
  }
}
