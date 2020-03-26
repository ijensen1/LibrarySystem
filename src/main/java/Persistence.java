import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
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
    static final String dataPath = "C:\\Users\\blue_\\IdeaProjects\\LibrarySystem2\\target\\Data", //where is data in general being stored
            accountsPath = "AccountList.json",
            borrowablesPath = "borrowables.json",
            libariesPath = "Libraries.txt";

    /**
     * Method to save an array of Borrowables to a given File.
     * @param data the Borrowables to save.
     */
    static void saveToFile(String type, Borrowable[] data) {
        try {
            FileWriter saveFile = new FileWriter(dataPath + type + ".json"); //To write data to file
            for (Borrowable borrowable : data) {
                Jsoner.serialize(List.of(data), saveFile); //Convert each borrowable into a json string and print to file
            }
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
        try {
            FileWriter saveFile = new FileWriter(dataPath + accountsPath); //To write data to file
            for (Account account : data) {
                Jsoner.serialize(List.of(data), saveFile); //Convert each account into a json string and print to file
            }
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
     * @return an array of the Borrowables in that file.
     */
    static Borrowable[] loadBorrowables(String type) {
        try {

            FileReader load = new FileReader(dataPath + type + ".json"); //To hold the file
            JsonArray objects = Jsoner.deserializeMany(load);
            Mapper mapper = new DozerBeanMapper();
            JsonArray o = (JsonArray) objects.get(0);
            List<Borrowable> result = o.stream().map(x -> mapper.map(x, Borrowable.class)).collect(Collectors.toList());


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
    static Account[] loadAccounts() throws IOException, JsonException {
        try {

            FileReader load = new FileReader(dataPath + accountsPath); //To hold the file
            JsonArray objects = Jsoner.deserializeMany(load);
            Mapper mapper = new DozerBeanMapper();
            JsonArray o = (JsonArray) objects.get(0);
            List<Account> result = o.stream().map(x -> mapper.map(x, Account.class)).collect(Collectors.toList());


            return result.toArray(new Account[0]);
        } catch (IOException | JsonException e) {
            //Some error occurred while trying to load from the file
            System.out.println("Error trying to load file: " + e.toString());
            return null;
        }
    }
}
