import java.io.ObjectInputStream;

public class Account {

    //setting Class data fields
    private String firstName;
    private String lastName;
    private String barrowStatus;
    private String barrowBranch;
    private String phoneNumber;
    private String email;
    private String address;

    public Account(){

        this("John", "Smith", "in", "National Branch", "(000) 000-0000", "jsmith@email.com", "3417 State Route 30 Fultonham NY 12071");


    }

    public Account(String firstName, String lastName, String barrowStatus, String barrowBranch, String phoneNumber, String email, String address){
        this.firstName = firstName;
        this.lastName = lastName;
        this.barrowStatus = barrowStatus;
        this.barrowBranch = barrowBranch;
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

    public String getBarrowStatus(){
        return barrowStatus;
    }

    public String getBarrowBranch(){
        return barrowBranch;
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

    public void setBarrowStatus(){
        this.barrowStatus = barrowStatus;
    }

    public void setBarrowBranch(){
        this.barrowBranch = barrowBranch;
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
