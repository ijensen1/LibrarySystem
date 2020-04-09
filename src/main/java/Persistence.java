import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class to allow saving and loading from files. Also holds data about saving/loading files.
 */
class Persistence {
    //constants
    static final String splitter = "::"; //used between tags for when reading from or saving to a file
    static final String splitter2 = ";;"; //used between tags on complex files (like Account's borrowables)
    static final String dataPath = "C:/Users/jganger-spivak/Documents/GitHub/LibrarySystem/target/Data/", //where is data in general being stored. Direct path, change if needed
            accountsPath = "AccountList.json",
            borrowablesPath = "borrowables.json",
            libariesPath = "Libraries.txt";

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
            FileWriter saveFile = new FileWriter(dataPath + type + ".json", false); //To write data to file
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
            FileWriter saveFile = new FileWriter(dataPath + accountsPath, false); //To write data to file
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
            File load = new File(dataPath + libariesPath); //To hold the file

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

            FileReader load = new FileReader(dataPath + type + ".json"); //To hold the file
            JsonArray objects = Jsoner.deserializeMany(load);
            JsonArray o = (JsonArray) objects.get(0);
            ArrayList<Borrowable> result = new ArrayList<>();
            for (Object obj : o) {
                if (obj instanceof JsonObject) {
                    JsonObject jo = (JsonObject) obj;
                    Borrowable part = Borrowable.fromJson(null, jo);
                    type = part.getType();
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

            return result.toArray(new Borrowable[0]);
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

            FileReader load = new FileReader(dataPath + accountsPath); //To hold the file
            JsonArray objects = Jsoner.deserializeMany(load);

            JsonArray o = (JsonArray) objects.get(0);
            ArrayList<Account> result = new ArrayList<>();
            for (Object obj : o) {
                if (obj instanceof JsonObject) {
                    JsonObject jo = (JsonObject) obj;
                    result.add(Account.fromJson(jo));
                }
            }


            return result.toArray(new Account[0]);
        } catch (IOException | JsonException e) {
            //Some error occurred while trying to load from the file
            System.out.println("Error trying to load file: " + e.toString());
            return null;
        }
    }
}
