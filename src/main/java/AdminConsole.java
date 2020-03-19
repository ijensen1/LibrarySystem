import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class AdminConsole {
    private ArrayList<Account> accounts;
    private ArrayList<Library> libraries;
    private static final String pass = "53fb8c10eded3d76424272f61308e0de71044d730834f9c7278ebe68a3735629631b37497f6b93da5141181aebcfaa9944a5cb2941c528a6c0cef9e002e7d7c5";
    public AdminConsole() {
        String[] libNames = Persistence.loadLibraryNames();
        libraries = new ArrayList<>(libNames.length);
        for (int i = 0; i < libraries.size(); i++) {
            libraries.set(i, new Library(libNames[i]));
        }
        accounts = new ArrayList<>();
        accounts.addAll(Arrays.asList(Persistence.loadAccounts()));
    }

    public void run() {
        Scanner input = new Scanner(System.in);
        LibraryManager lm = new LibraryManager();
        System.out.println("Welcome to the Library Administrator access panel");

        while (true) {
            System.out.print("Please enter your password: ");
            if (!lm.passToHash(input.nextLine()).equals(pass)) {
                System.out.println("Password invalid. Please try again.");
            } else {
                System.out.println("Recognized.");
                break;
            }
        }

        System.out.println("===============Actions are:================");
        System.out.println("    addbook                        quit    ");
        
    }

}
