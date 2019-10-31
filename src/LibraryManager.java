import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.*;

public class LibraryManager {
  private Account[] accounts;
  private final String splitter = "::";
  public LibraryManager() { 
    try {
      Scanner readAccounts = new Scanner(new File("accountslist.txt"));
      int accountsNum = 0;
      while (readAccounts.hasNextLine()) {
        String acntData = readAccounts.nextLine();
        
        accounts = new Account[this.accounts.length + 1];
        for (int i = 0; i < this.accounts.length; i++){
          accounts[i] = this.accounts[i];
        }
        String[] accountData;
        accountData = acntData.split(splitter, 2);
        accounts[this.accounts.length] = new Account(accountData[0], accountData[1]); //string firstName, string lastName
        this.accounts = accounts;
        accountsNum++;
      }
    } catch (IOException e) {
      System.out.println("Failed to load accounts list");
      System.exit(1);
    }
  }
  
  public Account getAccount(String name) {
    for (int i = 0; i < accounts.length; i++) {
      if ((accounts[i].getFirstName() + " " + accounts[i].getLastName()).equals(name)) {
        return accounts[i];
      }
    }
  }
  
  
  
}
