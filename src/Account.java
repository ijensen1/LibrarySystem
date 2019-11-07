import java.io.ObjectInputStream;
import java.io.*;
import java.util.Scanner;

public class Account {

    //variables for account holder data
    private String[][] people = {}; //array to hold data on Account holder
    private String libraryName;

    private final String splitter = "::"; //used between tags for when reading from or saving to a file
    private final int tagCount = 7; //tags are firstName, lastName, barrowStatus, barrowBranch, phoneNumber, email, and address

    //tag position
    private final int firstNamePos = 0, lastNamePos = 1, barrowStatusPos = 2, barrowBranch = 3, phoneNumberPos = 4, emailPos = 5, addressPos = 6;

    //error messages meant for user, or Jack, depending on how he implements things
    private final String errorInvalidType = "Invalid entry type. Valid entry types are \"firstName\", \"lastName\", \"barrowStatus\", \"barrowBranch\", \"phoneNumber\", \"email\", \"address\" (case insensitive)";
    private final String errorEntryNotFound = "Could not locate any entries containing that data";

    public Account(String libraryName) throws IOException{
        this.libraryName = libraryName;
        //making files
        File peopleFile = new File(libraryName + "Accounts.txt");

        //variables for reading file data
        String currentLine;
        String[][] people;

        //checking if a file for the account's person exists
        if (peopleFile.exists()){
            Scanner readPeople = new Scanner(peopleFile);
            //reading files for people and putting that data in this.people
            while (readPeople.hasNextLine()){
                people = new String[this.people.length + 1][tagCount];
                for (int i = 0; i < this.people.length; i++){
                    people[i] = this.people[i];
                }
                people[this.people.length] = currentLine.split(splitter, tagCount);
                this.people = people;
            }
            //closing Scanner
            readPeople.close();
            System.out.println("Loaded " + this.people.length + " Accounts");
        }

    }


    public void add(String firstName, String lastName, String barrowStatus, String barrowBranch)
}

