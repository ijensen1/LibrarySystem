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
        accountData = acntData.split(splitter, 7);
        accounts[this.accounts.length] = new Account(accountData[0], accountData[1], accountData[2],
                                                     accountData[3], accountData[4], accountData[5],
                                                     accountData[6]); //string firstName, string lastName
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
    //Account not found
    System.out.println("Account " + name + " not found");
    return null;
  }
  public void addAccount(Account newAccount) {
    accounts = new Account[this.accounts.length + 1];
    for (int i = 0; i < this.accounts.length; i++){
      accounts[i] = this.accounts[i];
    }
    accounts[this.accounts.length] = newAccount;
    this.accounts = accounts;
    FileAppend out = new FileAppend("accountlist.txt");
    for (Account acnt : accounts) {
      out.print(acnt.getFirstName() + "::");
      out.print(acnt.getLastName() + "::");
      out.print(acnt.getBorrowStatus() + "::");
      out.print(acnt.getBorrowBranch() + "::");
      out.print(acnt.getPhoneNumber() + "::");
      out.print(acnt.getEmail() + "::");
      out.print(acnt.getAddress());
    }
    out.println("");
  }
  
}
