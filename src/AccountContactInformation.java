public class AccountContactInformation {

    //setting data fields
    private String phoneNumber;
    private String email;
    private String address;

    //setting methods
    public AccountContactInformation(){

        this("(000) 000-0000", "fnamelastname@email.com", "0000 State Route 0 SomeTown, ST, 00000");

    }

    public AccountContactInformation(String phoneNumber, String email, String address){

        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;

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