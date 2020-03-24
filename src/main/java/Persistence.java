import com.github.cliftonlabs.json_simple.Jsoner;

import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

/**
 * Class to allow saving and loading from files. Also holds data about saving/loading files.
 */
class Persistence {
    //constants
    static final String splitter = "::"; //used between tags for when reading from or saving to a file
    static final String splitter2 = ";;"; //used between tags on complex files (like Account's borrowables)
    static final String dataPath = "../Data/", //where is data in general being stored
            accountsPath = "AccountList.json",
            libariesPath = "Libraries.txt";

    /**
     * Method to save the list of library names.
     * @param libraries the list of libaries.
     */
    static void saveToFile(Library[] libraries) {
        try {
            PrintWriter saveFile = new PrintWriter(dataPath + libariesPath); //To write names to file
            for (Library library : libraries) {
                saveFile.println(library.getLibraryName()); //Print the name of each library to the file.
            }
            saveFile.close(); //Close file
        } catch (IOException e) {
            //Some error occurred while trying to save to the file
            System.out.println("Error trying to save file: " + e.toString());
        }
    }

    /**
     * Method to save an array of Borrowables to a given File.
     * @param savePath where the file to save to is located.
     * @param data the Borrowables to save.
     */
    static void saveToFile(String savePath, Borrowable[] data) {
        try {
            PrintWriter saveFile = new PrintWriter(dataPath + savePath); //To write data to file
            for (Borrowable borrowable : data) {
                saveFile.println(borrowable.makeString()); //Convert each borrowable into a string and print to file
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
     * @param loadPath where the file to load from is located.
     * @return an array of the Borrowables in that file.
     */
    static Borrowable[] loadBorrowables(String loadPath) {
        try {
            Borrowable[] data = new Borrowable[0]; //To hold data that the file contains
            File load = new File(dataPath + loadPath); //To hold the file

            //Check if the file exists
            if (!load.exists())
                return data; //Return a length 0 array if the file doesn't exist

            Scanner readFile = new Scanner(load); //To read from file
            String[] current; //To hold the current Borrowable being read

            //Load all lines from the Scanner
            while (readFile.hasNextLine()) {
                data = Arrays.copyOf(data, data.length + 1); //Extend data by one
                current = readFile.nextLine().split(Persistence.splitter); //Read the next Borrowable and split it into its tags
                data[data.length - 1] = new Borrowable(current[0], Byte.parseByte(current[1]), current[2], current[3], current[4], current[5]); //Put the current Borrowable in data
            }
            return data; //Return data
        } catch (IOException e) {
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
            Account[] data = new Account[0]; //To hold data that the file contains
            File load = new File(dataPath + accountsPath); //To hold the file

            //Check if the file exists
            if (!load.exists())
                return data; //Return a length 0 array if the file doesn't exist

            Scanner readFile = new Scanner(load); //To read from file
            String[] current; //To hold the current Borrowable being read

            //Load all lines from the Scanner
            while (readFile.hasNextLine()) {
                Borrowable[] items = new Borrowable[0];
                data = Arrays.copyOf(data, data.length + 1); //Extend data by one
                current = readFile.nextLine().split(Persistence.splitter); //Read the next Account and split it into its tags
                String[] checkedOut = readFile.nextLine().replace("CHECKED_OUT:", "").split(Persistence.splitter2);
                if (!checkedOut[0].equals("")) { //Checking that there are any checked out books
                    for (String item : checkedOut) {
                        String[] info = item.split(Persistence.splitter);
                        items = Arrays.copyOf(items, items.length+1);
                        items[items.length-1] = new Borrowable(info[0], Byte.parseByte(info[1]), info[2], info[3], info[4], info[5]);
                    }
                }
                data[data.length - 1] = new Account(current[0], current[1], current[2], current[3]); //Put the current Account in data
                data[data.length - 1].setCheckedOut(items);

            }
            return data; //Return data
        } catch (IOException e) {
            //Some error occurred while trying to load from the file
            System.out.println("Error trying to load file: " + e.toString());
            return null;
        }
    }
}
