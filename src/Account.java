import java.io.ObjectInputStream;

public class Account {

    //setting Class data fields
    private String firstName;
    private String lastName;
    private String borrowStatus;
    private String borrowBranch;
    private String phoneNumber;
    private String email;
    private String address;

    public Account(){

        this("John", "Smith", "in", "National Branch", "(000) 000-0000", "jsmith@email.com", "3417 State Route 30 Fultonham NY 12071");


    }

    public Account(String firstName, String lastName, String borrowStatus, String borrowBranch, String phoneNumber, String email, String address){
        this.firstName = firstName;
        this.lastName = lastName;
        this.borrowStatus = borrowStatus;
        this.borrowBranch = borrowBranch;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }

    //getters
    public String getFirstName(){
        return getFirstName();
    }

    public String getLastName(){
        return getLastName();
    }

    public String getBorrowStatus(){
        return borrowStatus;
    }

    public String getBorrowBranch(){
        return borrowBranch;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public String getEmail(){
        return email;
    }

    public String getAddress(){
        return address;
    }

    //setters
    public void setFirstName(){
        this.firstName = firstName;
    }

    public void setLastName(){
        this.lastName = lastName;
    }

    public void setBorrowStatus(){
        this.borrowStatus = borrowStatus;
    }

    public void setBorrowBranch(){
        this.borrowBranch = borrowBranch;
    }

    public void setPhoneNumber(){
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(){
        this.email = email;
    }

    public void setAddress(){
        this.address = address;
    }

}
