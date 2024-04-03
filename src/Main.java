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

        new ipCalculator(userNet, amountOfSubnet);
    }

}