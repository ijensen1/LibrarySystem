import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class TestInventory {
    public static void main(String[] args) {
        Inventory i = null;
        try {
            i = new Inventory("test");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        Scanner input = new Scanner(System.in);
        System.out.println(i.getTag("book", 0, 4));
        i.checkOut("book", "test", "test");
        System.out.println(i.getTag("book", 0, 4));
        System.out.println("input type, title, creator, genre1, genre2. out to stop adding");
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
