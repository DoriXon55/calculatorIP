import java.util.Scanner;

public class Main implements Runnable{
    private static String classChoice;
    private static int amountOfSubnet;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("IP Calculator, please enter amount of subnet that you want: ");
        amountOfSubnet = scanner.nextInt();
        scanner.nextLine(); // scanner gets /n from previous line, so it must be there
        System.out.println(STR."Declared number of subnet: \{amountOfSubnet}");

        System.out.println("Now, please choice class of the net: (A-E)");
        classChoice = scanner.nextLine();
        System.out.println(STR."Declared net class: \{classChoice}");
    }

    public static int getAmountOfSubnet() {
        return amountOfSubnet;
    }

    public static String getClassChoice() {
        return classChoice;
    }

    ipCalculator ipCalculatorResult = new ipCalculator(getAmountOfSubnet(), getClassChoice());


    @Override
    public void run() {

    }
}