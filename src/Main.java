import java.util.Scanner;
public class Main{

    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);
        System.out.println("Wpisz IP sieci, którą chcesz podzielić: ");
        String userNet = scanner.nextLine();
        System.out.println(STR."Wpisane IP sieci: \{userNet}");

        System.out.println("Teraz podaj na ile podsieci chcesz podzielić sieć: ");
        int amountOfSubnet = scanner.nextInt();
        System.out.println(STR."Sieć zostanie podzielona na \{amountOfSubnet} podsieci.");

        System.out.println("Wybierz metodę podziału: \n 1. VLSM \n 2.Static");
        int userOption = scanner.nextInt();

        switch (userOption){
            case 1:
                break;
            case 2:
                new ipCalculatorStatic(userNet, amountOfSubnet);
                break;
        }


    }

}