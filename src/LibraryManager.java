import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.*;

public class LibraryManager {
  private Account[] accounts = new Account[1];
  private final String splitter = "::";
  public LibraryManager() { 
    try {
      Scanner readAccounts = new Scanner(new File("../Data/AccountList.txt"));
      String firstAccount = readAccounts.nextLine();
      String[] firstAccountData;
      firstAccountData = firstAccount.split(splitter, 7);
      this.accounts[0] = new Account(firstAccountData[0], firstAccountData[1], firstAccountData[2],
                                firstAccountData[3], firstAccountData[4], firstAccountData[5],
                                firstAccountData[6]); //string firstName, string lastName
      while (readAccounts.hasNextLine()) {
        String acntData = readAccounts.nextLine();
        Account newaccounts[] = new Account[this.accounts.length + 1];
        for (int i = 0; i < this.accounts.length; i++){
          newaccounts[i] = this.accounts[i];
        }
        String[] accountData;
        accountData = acntData.split(splitter, 7);
        newaccounts[this.accounts.length] = new Account(accountData[0], accountData[1], accountData[2],
                                                        accountData[3], accountData[4], accountData[5],
                                                        accountData[6]); //string firstName, string lastName
        this.accounts = newaccounts;
      }
    } catch (IOException e) {
      System.out.println("Failed to load accounts list");
      System.exit(1);
    }
    try {
      File checkout = new File("../Data/CheckoutList.txt");
      Scanner read
    } catch (IOException e) {
      System.out.println("Failed to load checkout list");
      System.exit(1);
    }
  }
  
  public Account getAccount(String name) {
    for (int i = 0; i < accounts.length; i++) {
      if ((accounts[i].getFirstName() + " " + accounts[i].getLastName()).equals(name)) {
        return accounts[i];
      }
    }
    //Account not found
    System.out.println("Account " + name + " not found");
    return null;
  }
  
  public void addAccount(Account newAccount) {
    Account copyaccounts[] = new Account[this.accounts.length + 1];
    for (int i = 0; i < this.accounts.length; i++){
      copyaccounts[i] = this.accounts[i];
    }
    copyaccounts[this.accounts.length] = newAccount;
    this.accounts = copyaccounts;
    FileAppend out = new FileAppend("../Data/AccountList.txt");
    
    out.print(newAccount.getFirstName() + "::");
    out.print(newAccount.getLastName() + "::");
    out.print(newAccount.getEmail() + "::");
    out.print(newAccount.getPhone() + "::");
    
    out.println("");
    out.close();
  }
  public int getAccountsLength() {
    return this.accounts.length;
  }
  
}
