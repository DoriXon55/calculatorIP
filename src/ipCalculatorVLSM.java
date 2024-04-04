public class ipCalculatorVLSM {
    public ipCalculatorVLSM(String IP, int amountOfSubnet)
    {
        String[] octets = IP.split("\\.");
        int firstOctet;

        if (octets.length != 4 && !octets[3].equals("0")) {
            System.out.println("Podano nieprawidłowy adres IP!");
            return;
        }
        firstOctet = Integer.parseInt(octets[0]);

        System.out.println("Wpisz ile hostów ma mieć każda z podsieci (malejąco): "); // tutaj będzie kod, który ogarnia i zapisuje ile hostów ma być w podsieci
        // tutaj jakaś tablica

        if (firstOctet >= 1 && firstOctet <= 126) {
            System.out.println("Klasa A");
        } else if (firstOctet >= 128 && firstOctet <= 191) {
            System.out.println("Klasa B");
        } else if (firstOctet >= 192 && firstOctet <= 223) {
            System.out.println("Klasa C");
        } else if (firstOctet >= 224 && firstOctet <= 239) {
            System.out.println("Klasa D");
        } else if (firstOctet >= 240 && firstOctet <= 255) {
            System.out.println("Klasa E");
        }
    }

    public void calculateVSLM(String[] octets, int[] subnetHosts ,int amountOfSubnet)
    {

    }

}
