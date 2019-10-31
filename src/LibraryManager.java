import java.io.File;
import java.io.IOException;
import java.util.Scanner;
/**
 * Auto Generated Java Class.
 */
public class LibraryManager {
  Account[] accounts;
  public LibraryManager() { 
    try {
      Scanner readAccounts = new Scanner(new File("accountslist.txt"));
      while (readAccounts.hasNextLine()) {
        String acntData = readAccounts.nextLine();
      }
    } catch (IOException e) {
      System.out.println("Failed to load accounts list");
      System.exit(1);
    }
  }
  
  
}
