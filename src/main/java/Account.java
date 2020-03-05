/**
 * Data class to hold account details, as well as a list of items the account holder may have checked out.
 * Also includes makeString() for Persistance compatibility.
 */
public class Account {

    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String passhash;


    private Borrowable[] checkedOut; //List of borrowables checked out

    /**
     * Constructor that assigns MOST data. Make sure to set checkedOut yourself!
     * @param firstName The account holder's first name
     * @param lastName The account holder's second name
     * @param phone The account holder's phone
     * @param email The account holder's email address. We use this for login.
     */
    public Account (String firstName, String lastName, String phone, String email, String passhash){
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.checkedOut = new Borrowable[0]; //By default this starts at zero. Make sure to set it when loading!
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail(){
        return email;
    }

    public Borrowable[] getCheckedOut() {
        return checkedOut;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setCheckedOut(Borrowable[] checkedOut) {
        this.checkedOut = checkedOut;
    }

    public String getPasshash() {
        return passhash;
    }

    public void setPasshash(String passhash) {
        this.passhash = passhash;
    }

    /**
     * Method to convert class data to string.
     * Persistence.splitter and Persistence.splitter2 are used to delineate different values and different sets of values, respectively.
     */
    public String makeString(){
        String finalString = getFirstName() +Persistence.splitter+ getLastName() +Persistence.splitter+ getPhone() +Persistence.splitter+ getEmail();
        finalString += "\nCHECKED_OUT:";//Make things a little more readable in the file
        if (checkedOut.length > 0) { //Do we even have anything to add here?

            for (int i = 0; i < checkedOut.length; i++) {
                if (i == 0) {
                    finalString += checkedOut[i].makeString();//For the first Borrowable, we don't need the splitter beforehand.
                } else {
                    finalString += Persistence.splitter2 + checkedOut[i].makeString();
                }
            }
        }

        return finalString;
    }
}

