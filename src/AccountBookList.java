public class AccountBookList {
  private String name;
  private String[] bookNames;
  private String[] authorNames;
  private String[] borrowedFrom;
  public AccountBookList(String name, String[] bookNames, String[] authorNames) { 
    this.name = name;
    this.bookNames = bookNames;
    this.authorNames = authorNames;
  }
}
