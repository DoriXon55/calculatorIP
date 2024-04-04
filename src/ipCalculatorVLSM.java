public class ipCalculatorVLSM {
    public ipCalculatorVLSM(String IP, int amountOfSubnet)
    {
        String[] octets = IP.split("\\.");
        int firstOctet;

        if (octets.length != 4 && !octets[3].equals("0")) {
            System.out.println("INVALID IP!");
            return;
        }
        firstOctet = Integer.parseInt(octets[0]);

        System.out.println("How many host's in subnet (descending order): "); // tutaj będzie kod, który ogarnia i zapisuje ile hostów ma być w podsieci
        // tutaj jakaś tablica

        if (firstOctet >= 1 && firstOctet <= 126) {
            System.out.println("Class A");
        } else if (firstOctet >= 128 && firstOctet <= 191) {
            System.out.println("Class B");
        } else if (firstOctet >= 192 && firstOctet <= 223) {
            System.out.println("Class C");
        } else if (firstOctet >= 224 && firstOctet <= 239) {
            System.out.println("Class D");
        } else if (firstOctet >= 240 && firstOctet <= 255) {
            System.out.println("Class E");
        }
    }

    public void calculateVSLM(String[] octets, int[] subnetHosts ,int amountOfSubnet)
    {

    }

}
