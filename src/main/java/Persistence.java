import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import java.io.*;
import java.util.*;

/**
 * Class to allow saving and loading from files. Also holds data about saving/loading files.
 */
class Persistence {
    //constants
    static final String DATA_PATH = "C:/Users/jganger-spivak/Documents/GitHub/LibrarySystem/target/Data/", //where is data in general being stored. Direct path, change if needed
            ACCOUNTS_PATH = "AccountList.json",
            LIBARIES_PATH = "Libraries.txt";

    /**
     * Method to save an array of Borrowables to a given File.
     * @param type The type of borrowable to save (what file)
     * @param data the Borrowables to save.
     */
    static void saveToFile(String type, Borrowable[] data) {
        if (data.length == 0) {
            return; //Fix for "delete empty JSON array" issue; if there's nothing to write don't touch it
        }
        try {
            FileWriter saveFile = new FileWriter(DATA_PATH + type + ".json", false); //To write data to file
            Jsoner.serialize(List.of(data), saveFile); //Convert each borrowable into a json string and print to file

            saveFile.close(); //Close file
        } catch (IOException e) {
            //Some error occurred while trying to save to the file
            System.out.println("Error trying to save file: " + e.toString());
        }
    }

    /**
     * Method to save an array of Accounts to a given File.
     * @param data the Accounts to save.
     */
    static void saveToFile(Account[] data) {
        if (data.length == 0) {
            System.out.println("Accounts length passed in is zero. This is probably an error.");
            return; //Fix for "delete empty JSON array" issue; if there's nothing to write don't touch it
        }
        try {
            FileWriter saveFile = new FileWriter(DATA_PATH + ACCOUNTS_PATH, false); //To write data to file
            Jsoner.serialize(List.of(data), saveFile); //Convert each account into a json string and print to file

            saveFile.close(); //Close file
        } catch (IOException e) {
            //Some error occurred while trying to save to the file
            System.out.println("Error trying to save file: " + e.toString());
        }
    }

    /**
     * Method to read the names of all libraries.
     * @return a String[] of library names.
     */
    static String[] loadLibraryNames() {
        try {
            String[] names = new String[0]; //To hold the names of the libraries
            File load = new File(DATA_PATH + LIBARIES_PATH); //To hold the file

            //Check if the file exists
            if (!load.exists())
                return names; //Return a length 0 array if the file doesn't exist

            Scanner readFile = new Scanner(load); //To read from file

            //Load all lines from the Scanner
            while (readFile.hasNextLine()) {
                names = Arrays.copyOf(names, names.length + 1); //Extend names by one
                names[names.length - 1] = readFile.nextLine(); //Put the current library name in names
            }
            return names; //Return names of libraries
        } catch (IOException e) {
            //Some error occurred while trying to load from the file
            System.out.println("Error trying to load file: " + e.toString());
            return null;
        }
    }

    /**
     * Method to load an array of Borrowables from a given File.
     * @param type THe type of borrowable to look for (what file)
     * @return an array of the Borrowables in that file.
     */
    static Borrowable[] loadBorrowables(String type) {
        try {

            FileReader load = new FileReader(DATA_PATH + type + ".json"); //To hold the file. Loads from whatever file is specified in type
            JsonArray objects = Jsoner.deserializeMany(load);
            JsonArray o = (JsonArray) objects.get(0);
            ArrayList<Borrowable> result = new ArrayList<>();
            for (Object obj : o) { //For each object in collection
                if (obj instanceof JsonObject) { //Is this object a JsonObject (it is, probably)
                    JsonObject jo = (JsonObject) obj; //Cast
                    Borrowable part = Borrowable.fromJson(null, jo); //Get the borrowable from it so we can check the type
                    type = part.getType(); //Get type
                    //Now for each type call the requested constructor, pass in the Borrowable already created so we don't need to call Borrowable.fromJson again
                    if (type.equals("books")) {
                        result.add(Book.fromJson(part, jo));
                    }
                    if (type.equals("cds")) {
                        result.add(CD.fromJson(part, jo));
                    }
                    if (type.equals("dvds")) {
                        result.add(DVD.fromJson(part, jo));
                    }
                    if (type.equals("picturebooks")) {
                        result.add(PictureBook.fromJson(part, jo));
                    }

                }
            }

            return result.toArray(new Borrowable[0]); //Return everything loaded
        } catch (IOException | JsonException e) {
            //Some error occurred while trying to load from the file
            System.out.println("Error trying to load file: " + e.toString());
            return null;
        }
    }

    /**
     * Method to load an array of Accounts from a given File.
     * @return an array of the Accounts in the accounts file.
     */
    static Account[] loadAccounts() {
        try {

            FileReader load = new FileReader(DATA_PATH + ACCOUNTS_PATH); //To hold the file
            JsonArray objects = Jsoner.deserializeMany(load);

            JsonArray o = (JsonArray) objects.get(0);
            ArrayList<Account> result = new ArrayList<>();
            for (Object obj : o) {
                if (obj instanceof JsonObject) { //For every JsonObject in the JsonArray
                    JsonObject jo = (JsonObject) obj; //Cast
                    result.add(Account.fromJson(jo)); //And construct a new Account from that JsonObject
                }
            }


            return result.toArray(new Account[0]); //Return the results, if any
        } catch (IOException | JsonException e) {
            //Some error occurred while trying to load from the file
            System.out.println("Error trying to load file: " + e.toString());
            return null;
        }
    }
}
