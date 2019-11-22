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
    private final int firstNamePos = 0, lastNamePos = 1, barrowStatusPos = 2, barrowBranchPos = 3, phoneNumberPos = 4, emailPos = 5, addressPos = 6;

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
                currentLine = readPeople.nextLine();
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


    public void add(String type, String firstName, String lastName, String barrowStatus, String barrowBranch, String email, String phoneNumber, String address){
        String[][] tempArray; //temporary array for holding a modified array with the new value added to it

        if (type.equalsIgnoreCase("people")){
           //filling out tempArray with people data + new entry
           tempArray = new String[people.length + 1][tagCount];
           for (int i = 0; i < people.length; i++){
               tempArray[i] = people[i];
           }
           tempArray[people.length] = new String[]{firstName, lastName, barrowStatus, barrowBranch, email, phoneNumber, address};
           //updating people
            people = tempArray;
        } else {
            System.out.println(errorInvalidType);
        }



    }

    /**
     *
     * @param type
     * @param firstName
     * @param lastName
     * @param barrowStatus
     * @param barrowBranch
     * @param email
     * @param phoneNumber
     * @param address
     */

    public void remove(String type, String firstName, String lastName, String barrowStatus, String barrowBranch, String email, String phoneNumber, String address) {

        String[][] removeSearch, removed;

        if (type.equalsIgnoreCase("people")) {
            removeSearch = people;
        } else {
            System.out.println(errorInvalidType);
            return;
        }

        for (int entry = 0; entry < removeSearch.length; entry++) {
            if (removeSearch[entry][firstNamePos].equalsIgnoreCase(firstName) &&
                    removeSearch[entry][lastNamePos].equalsIgnoreCase(lastName) &&
                    removeSearch[entry][barrowStatusPos].equalsIgnoreCase(barrowStatus) &&
                    removeSearch[entry][barrowBranchPos].equalsIgnoreCase(barrowBranch) &&
                    removeSearch[entry][emailPos].equalsIgnoreCase(email) &&
                    removeSearch[entry][phoneNumberPos].equalsIgnoreCase(phoneNumber) &&
                    removeSearch[entry][addressPos].equalsIgnoreCase(address)){

                removed = new String[removeSearch.length - 1][tagCount];
                for (int upToRemoved = 0; upToRemoved < entry; upToRemoved++) {
                    removed[upToRemoved] = removeSearch[upToRemoved];
                }
                for (int afterRemoved = entry + 1; afterRemoved < people.length; afterRemoved++) {
                    removed[afterRemoved - 1] = removeSearch[afterRemoved];
                }

                if (type.equalsIgnoreCase("people")) {
                    people = removed;
                }
                return;
            }
            System.out.println(errorEntryNotFound);
        }

    }

    public int[] findIndex(String type, String firstName, String lastName, String barrowStatus, String barrowBranch, String email, String phoneNumber, String address){
        String[][] searchingThrough;
        int[] results = new int[0], tempArray;

        if(firstName.equalsIgnoreCase("people")){
            searchingThrough = people;
        } else {
            System.out.println(errorInvalidType);
            return null;
        }

        for (int entry = 0; entry < searchingThrough.length; entry++){
            if (firstName.equalsIgnoreCase(searchingThrough[entry][firstNamePos]) && lastName.equalsIgnoreCase(searchingThrough[entry][lastNamePos]) && barrowStatus.equalsIgnoreCase(searchingThrough[entry][barrowStatusPos]) && barrowBranch.equalsIgnoreCase(searchingThrough[entry][barrowBranchPos]) && email.equalsIgnoreCase(searchingThrough[entry][emailPos]) && phoneNumber.equalsIgnoreCase(searchingThrough[entry][phoneNumberPos]) && address.equalsIgnoreCase(searchingThrough[entry][addressPos])){

                tempArray = new int[results.length + 1];
                for (int i = 0; i < results.length; i++){
                    tempArray[i] = results[i];
                }
                tempArray[results.length] = entry;

                results = tempArray;
            }
        }
        return results;
    }

    public String[] getIndex(String type, int entry){
        if (type.equalsIgnoreCase("people")){
            return people[entry];
        } else {
            System.out.println(errorInvalidType);
            return null;
        }
    }

    public String getTag(String type, int entry, int tag){
        if (type.equalsIgnoreCase("people")){
            return people[entry][tag];
        } else {
            System.out.println(errorInvalidType);
            return null;
        }

    }

    public String[][] searchFirstName(String type, String firstName){
        String[][] searchingThrough;
        String[][] results = new String[0][tagCount], tempArray;

        if (type.equalsIgnoreCase("people")){
            searchingThrough = people;
        } else {
            System.out.println(errorInvalidType);
            return null;
        }

        for (int entry = 0; entry < searchingThrough.length; entry++){
            if (firstName.equalsIgnoreCase(searchingThrough[entry][firstNamePos])) {
                tempArray = new String[results.length + 1][tagCount];
                for (int i = 0; i < results.length; i++){
                    tempArray[i] = results[i];
                }
                tempArray[results.length] = searchingThrough[entry];
                results = tempArray;
            }

        }
        return results;
    }

    public String[][] searchLastName(String type, String lastName){
        String[][] searchingThrough;
        String[][] results = new String[0][tagCount], tempArray;

        if (type.equalsIgnoreCase("people")){
            searchingThrough = people;
        } else {
            System.out.println(errorInvalidType);
            return null;
        }

        for (int entry = 0; entry < searchingThrough.length; entry++) {
            if (lastName.equalsIgnoreCase(searchingThrough[entry][lastNamePos])) {
                tempArray = new String[results.length + 1][tagCount];
                for (int i = 0; i < results.length; i++){
                    tempArray[i] = results[i];
                }
                tempArray[results.length] = searchingThrough[entry];
                results = tempArray;
            }
        }
        return results;
    }

    public void save() throws IOException{

        PrintWriter savePeople = new PrintWriter(libraryName + "Accounts.txt");

        for (int line = 0; line < people.length; line++){
            for (int word = 0; word < tagCount; word++){
                savePeople.print(people[line][word]);
                if (word != tagCount - 1){
                    savePeople.print(splitter);
                }
            }
            savePeople.println();
        }

        savePeople.close();
    }



}

