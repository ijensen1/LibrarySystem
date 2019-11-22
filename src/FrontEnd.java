import java.util.Scanner;

public class FrontEnd {
  
  
  public static void main(String[] args) { 
    LibraryManager master = new LibraryManager();
    Scanner input = new Scanner(System.in);
    System.out.println("What would you like to do?");
    System.out.print("Options are: testAccount, testAcntAdd");
    String cmd = input.nextLine();
    
    if (cmd.equals("testAccount")) {
      System.out.println(master.getAccount("John Smith").getAddress());
    }
    
    if (cmd.equals("testAcntAdd")) {
      System.out.println(master.getAccountsLength());
      master.addAccount(master.getAccount("John Smith"));
      System.out.println(master.getAccountsLength());
    }
  }
  
}
