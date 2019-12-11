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

    static void saveToFile(File saveTo, Borrowable[] data) {
        try {
            PrintWriter saveFile = new PrintWriter(saveTo); //To write data to file
            for (Borrowable borrowable : data) {
                saveFile.println(borrowable.makeString()); //Convert each borrowable into a string and print to file
            }
            saveFile.close(); //Close file
        } catch (IOException e) {
            //Some error occurred while trying to save to the file
            System.out.println("Error trying to save file.");
        }
    }

    static void saveToFile(File saveTo, Account[] data) {
        try {
            PrintWriter saveFile = new PrintWriter(saveTo); //To write data to file
            for (Account account : data) {
                saveFile.println(account.makeString()); //Convert each account into a string and print to file
            }
            saveFile.close(); //Close file
        } catch (IOException e) {
            //Some error occurred while trying to save to the file
            System.out.println("Error trying to save file.");
        }
    }

    static Borrowable[] loadBorrowables(File loadFrom) {
        try {
            Scanner readFile = new Scanner(loadFrom); //To read from file
            Borrowable[] data = new Borrowable[0]; //To hold data that the file contains
            String[] current; //To hold the current Borrowable being read
            while (readFile.hasNextLine()) { //Go through every Borrowable in the file
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

    static Account[] loadAccounts(File loadFrom) {
        try {
            Scanner readFile = new Scanner(loadFrom); //To read from file
            Account[] data = new Account[0]; //To hold data that the file contains
            String[] current; //To hold the current Account being read
            while (readFile.hasNextLine()) { //Go through every Account in the file
                data = Arrays.copyOf(data, data.length + 1); //Extend data by one
                current = readFile.nextLine().split(Persistence.splitter); //Read the next Account and split it into its tags
                data[data.length - 1] = new Account(current[0], current[1], current[2], current[3]); //Put the current Account in data
            }
            return data;//Return data
        } catch (IOException e) {
            //Some error occurred while trying to load from the file
            System.out.println("Error trying to load file.");
            return null;
        }
    }
}
