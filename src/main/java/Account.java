import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;
import com.github.cliftonlabs.json_simple.Jsoner;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Data class to hold account details, as well as a list of items the account holder may have checked out.
 * Also includes makeString() for Persistance compatibility.
 */
public class Account implements Jsonable {

    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String passhash;


    private ArrayList<Borrowable> checkedOut; //List of borrowables checked out

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
        this.checkedOut = new ArrayList<>();
        this.passhash = passhash;
    }

    public Account() {
        
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

    public ArrayList<Borrowable> getCheckedOut() {
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

    public void setCheckedOut(ArrayList<Borrowable> checkedOut) {
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
        if (checkedOut.size() > 0) { //Do we even have anything to add here?

            for (int i = 0; i < checkedOut.size(); i++) {
                if (i == 0) {
                    finalString += checkedOut.get(i).makeString();//For the first Borrowable, we don't need the splitter beforehand.
                } else {
                    finalString += Persistence.splitter2 + checkedOut.get(i).makeString();
                }
            }
        }

        return finalString;
    }

    @Override
    public String toJson(){

        final StringWriter writable = new StringWriter();
        try{
            this.toJson(writable);
        } catch (final IOException e) {

        }
        return writable.toString();

    }

    @Override
    public void toJson(Writer writer) throws IOException {

        final JsonObject json = new JsonObject();
        json.put("firstName", this.getFirstName());
        json.put("lastName", this.getLastName());
        json.put("phone", this.getPhone());
        json.put("email", this.getEmail());
        json.put("checkedOut", this.getCheckedOut());
        json.put("passhash", this.getPasshash());
        json.toJson(writer);

    }

    public static Account fromJson(JsonObject obj) {
        String firstName = obj.getString(Jsoner.mintJsonKey("firstName", null));
        String lastName = obj.getString(Jsoner.mintJsonKey("lastName", null));
        String phone = obj.getString(Jsoner.mintJsonKey("phone", null));
        String email = obj.getString(Jsoner.mintJsonKey("email", null));
        ArrayList<Borrowable> checkedOut = new ArrayList<>();
        for (Object o : obj.getCollection(Jsoner.mintJsonKey("checkedOut", null))) {
            if (o instanceof JsonObject) {
                JsonObject jo = (JsonObject) o;
                checkedOut.add(Borrowable.fromJson(null, jo));
            }
        }
        String passhash = obj.getString(Jsoner.mintJsonKey("passhash", null));
        Account result = new Account(firstName, lastName, phone, email, passhash);
        result.setCheckedOut(checkedOut);
        return result;
    }
}

