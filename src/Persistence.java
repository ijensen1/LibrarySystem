import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Class to allow saving and loading from files. Also holds data about saving/loading files.
 */
class Persistence {
    //constants
    static final String splitter = "::"; //used between tags for when reading from or saving to a file
    static final String dataPath = "../Data/"; //where is data in general being stored

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
            System.out.println("Error trying to save file.");
        }
    }

    /**
     * Method to save an array of Accounts to a given File.
     * @param savePath where the file to save to is located.
     * @param data the Accounts to save.
     */
    static void saveToFile(String savePath, Account[] data) {
        try {
            PrintWriter saveFile = new PrintWriter(dataPath + savePath); //To write data to file
            for (Account account : data) {
                saveFile.println(account.makeString()); //Convert each account into a string and print to file
            }
            saveFile.close(); //Close file
        } catch (IOException e) {
            //Some error occurred while trying to save to the file
            System.out.println("Error trying to save file.");
        }
    }

    /**
     * Method to load an array of Borrowables from a given File.
     * @param loadPath where the file to load from is located.
     * @return
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
                data[data.length - 1] = new Borrowable(current[0], current[1], current[2], current[3]); //Put the current Borrowable in data
            }
            return data; //Return data
        } catch (IOException e) {
            //Some error occurred while trying to load from the file
            System.out.println("Error trying to load file.");
            return null;
        }
    }

    static Account[] loadAccounts(String loadPath) {
        try {
            Account[] data = new Account[0]; //To hold data that the file contains
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
                data[data.length - 1] = new Account(current[0], current[1], current[2], current[3]); //Put the current Borrowable in data
            }
            return data; //Return data
        } catch (IOException e) {
            //Some error occurred while trying to load from the file
            System.out.println("Error trying to load file.");
            return null;
        }
    }
}
