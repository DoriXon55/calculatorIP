import java.io.*;
import java.lang.String;

public class ipCalculatorVLSM {
    public ipCalculatorVLSM(String IP, int amountOfSubnet, String userMask)
    {
        String[] octets = IP.split("\\.");
        int firstOctet;

        if (octets.length != 4 && !octets[3].equals("0")) {
            System.out.println("INVALID IP!");
            return;
        }
        firstOctet = Integer.parseInt(octets[0]);

        System.out.println("How many host's in subnet (descending order): "); // tutaj będzie kod, który ogarnia i zapisuje ile hostów ma być w podsieci
        int[] subnetHosts = new int[0];
        
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
        calculateVSLM(octets,subnetHosts, amountOfSubnet, userMask);
    }

    public void calculateVSLM(String[] octets, int[] subnetHosts ,int amountOfSubnet, String userMask)
    {
        try{
            FileWriter fileWriter = new FileWriter("SubnetVLMS.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            int range = 256 / amountOfSubnet;
            int firstOctet = Integer.parseInt(octets[0]);
            int secondOctet = Integer.parseInt(octets[1]);
            int thirdOctet = 0, fourthOctet = 0;

            for (int i = 0; i < amountOfSubnet; i++)
            {
                int subnetHost = subnetHosts[i];
                bufferedWriter.write(STR."Subnet \{i + 1}: \{firstOctet}.\{secondOctet}.\{thirdOctet}.\{fourthOctet} \n");
                bufferedWriter.write(STR."First useful address: \{firstOctet}.\{secondOctet}.\{thirdOctet}.\{fourthOctet} \n");
                bufferedWriter.write(STR."Last useful address: \{firstOctet}.\{secondOctet}.\{thirdOctet + range - 1}.\{fourthOctet} \n");
                bufferedWriter.write(STR."Broadcast address: \{firstOctet}.\{secondOctet}.\{thirdOctet + range - 1}.\{fourthOctet} \n");
                bufferedWriter.write(STR."Net mask: \{firstOctet}.\{secondOctet}.\{thirdOctet + range - 1}.\{fourthOctet} \n");
                bufferedWriter.write("\n");
            }


        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
