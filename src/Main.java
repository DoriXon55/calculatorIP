import java.util.Scanner;

public class Main {

    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);
        System.out.println("Give IP to divine: ");
        String userNet = scanner.nextLine();
        System.out.println(STR."IP: \{userNet}");


        System.out.println("Give how many subnets do you want: ");
        int amountOfSubnet = scanner.nextInt();
        System.out.println(STR."Subnets to check: \{amountOfSubnet}");

        System.out.println("Choose division method: \n 1. VLSM \n 2. Static");
        int userOption = scanner.nextInt();

        switch (userOption) {
            case 1:
                new ipCalculatorVLSM(userNet, amountOfSubnet);
                break;
            case 2:
                new ipCalculatorStatic(userNet, amountOfSubnet);
                break;
            default:
                System.out.println("Wrong option! Try again :)");
                System.out.println("Choose division method: \n 1. VLSM \n 2. Static");
                break;
        }
        clearTerminal();
    }

    public static void clearTerminal() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}