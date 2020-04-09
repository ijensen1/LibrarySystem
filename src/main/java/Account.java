import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;
import com.github.cliftonlabs.json_simple.Jsoner;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Data class to hold account details, as well as a list of items the account holder may have checked out.
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
     * @param passhash Used for user authentication. SHA-512 hash of the password is used.
     */
    public Account (String firstName, String lastName, String phone, String email, String passhash){
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.checkedOut = new ArrayList<>();
        this.passhash = passhash;
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
     * Shorthand method that then calls the main toJson after doing a little setup.
     * @return A Json formatted String of the Account's data.
     */
    @Override
    public String toJson(){

        final StringWriter writable = new StringWriter();
        try{
            this.toJson(writable);
        } catch (final IOException e) {
            System.out.println(e.toString());
        }
        return writable.toString();

    }

    /**
     * Main toJson method that saves class data to the given Writer object using Json formatting.
     * @param writer the Writer object to store the data to.
     * @throws IOException Passes up writer's IOException.
     */
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

    /**
     * Method to take data from a JsonObject and make a new Account object from it.
     * @param obj the JsonObject that holds the data to make an Account from.
     * @return A new Account object holding the given data.
     */
    public static Account fromJson(JsonObject obj) {
        String firstName = obj.getString(Jsoner.mintJsonKey("firstName", null));
        String lastName = obj.getString(Jsoner.mintJsonKey("lastName", null));
        String phone = obj.getString(Jsoner.mintJsonKey("phone", null));
        String email = obj.getString(Jsoner.mintJsonKey("email", null));
        ArrayList<Borrowable> checkedOut = new ArrayList<>();
        for (Object o : obj.getCollection(Jsoner.mintJsonKey("checkedOut", null))) { //For every object in the checkedOut collection
            if (o instanceof JsonObject) { //If it's a JsonObject (it is)
                JsonObject jo = (JsonObject) o; //Cast
                checkedOut.add(Borrowable.fromJson(null, jo)); //And pass to Borrowable.fromJson, which will happily construct a new object for us from that JsonObject
            }
        }
        String passhash = obj.getString(Jsoner.mintJsonKey("passhash", null));
        Account result = new Account(firstName, lastName, phone, email, passhash);
        result.setCheckedOut(checkedOut); //checkedOut isn't included in constructor, so we set it here before returning
        return result;
    }
}

