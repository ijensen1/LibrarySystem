public class Account {

    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Borrowable[] checkedOut;

    public Account (String firstName, String lastName, String phone, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.checkedOut = new Borrowable[0];
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

    public String makeString(){
        String finalString = getFirstName() +Persistence.splitter+ getLastName() +Persistence.splitter+ getPhone() +Persistence.splitter+ getEmail();
        if (checkedOut.length > 0) {
            finalString += "\nCHECKED_OUT:";
            for (Borrowable item : checkedOut) {
                finalString += Persistence.splitter2 + item.makeString();
            }
        }

        return finalString;
    }
}

