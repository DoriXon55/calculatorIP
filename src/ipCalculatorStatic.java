import java.io.*;
import java.lang.String;

public class ipCalculatorStatic {
    public ipCalculatorStatic(String IP, int amountOfSubnet) {
        String[] octets = IP.split("\\.");
        int firstOctet;

        if (octets.length != 4 && !octets[3].equals("0")) {
            System.out.println("INVALID IP!");
            return;
        }
        firstOctet = Integer.parseInt(octets[0]);

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
        subnetDivine(octets, amountOfSubnet);


    }

    public void subnetDivine(String[] octets, int amountOfSubnet) {
        try {
            cleanFile();
            FileWriter fileWriter = new FileWriter("Subnet.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            int range = 256 / amountOfSubnet;
            int firstOctet = Integer.parseInt(octets[0]);
            int secondOctet = Integer.parseInt(octets[1]);
            int thirdOctet = Integer.parseInt(octets[2]);
            int fourthOctet;

            for (int i = 0; i < amountOfSubnet; i++) {
                fourthOctet = 0;
                bufferedWriter.write(STR."LAN \{i + 1}: \{firstOctet}.\{secondOctet}.\{thirdOctet}.\{fourthOctet} \n");
                fourthOctet = 1;
                bufferedWriter.write(STR."First useful address: \{firstOctet}.\{secondOctet}.\{thirdOctet}.\{fourthOctet} \n");
                fourthOctet = 254;
                bufferedWriter.write(STR."Last useful address: \{firstOctet}.\{secondOctet}.\{thirdOctet + range - 1}.\{fourthOctet} \n");
                fourthOctet = 255;
                bufferedWriter.write(STR."Broadcast address: \{firstOctet}.\{secondOctet}.\{thirdOctet + range - 1}.\{fourthOctet} \n");
                bufferedWriter.write("\n");
                thirdOctet += range;
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void cleanFile() throws FileNotFoundException {
        PrintWriter writerNames = new PrintWriter("Subnet.txt");
        writerNames.print("");
        writerNames.close();
    }
}

