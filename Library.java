/**
 * Auto Generated Java Class.
 */
public class Library {
  private String name;
  private Inventory inv;
  private LibraryManager master;
  
  public Library(String name, LibraryManager master) { 
    this.name = name;
    this.master = master;
    try {
      inv = new Inventory(this.name);
    } catch (IOException e) {
      System.out.println("Error loading inventory");
      System.exit(1);
    }
    
  }
  
  public boolean checkIn(String name, Account account) {
    
  }
  
  public boolean checkOut(String name, Account account) {
    
  }
}
