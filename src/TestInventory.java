import java.io.IOException;
import java.util.Scanner;

public class TestInventory {
    public static void main(String[] args) {
        Library i = null;
        try {
            i = new Library("test");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        Scanner input = new Scanner(System.in);
        String adder = input.nextLine();
        String[] addersplit = adder.split(" ", 5);
        while (!adder.equals("out")) {
            i.add(addersplit[0], addersplit[1], addersplit[2], addersplit[3], addersplit[4]);
            adder = input.nextLine();
            addersplit = adder.split(" ", 5);
        }
        try{
            i.save();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
