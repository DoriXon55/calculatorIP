import java.io.*;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class ipCalculatorVLSM {
    public ipCalculatorVLSM(String IP, int amountOfSubnet, String userMask) {

        Scanner scanner = new Scanner(System.in);
        String[] octets = IP.split("\\.");
        int[] subnetHosts = new int[amountOfSubnet];
        int firstOctet;

        if (octets.length != 4 && !octets[3].equals("0")) {
            System.out.println("INVALID IP!");
            return;
        }
        firstOctet = Integer.parseInt(octets[0]);

        for (int i = 0; i < amountOfSubnet; i++) {
            System.out.println(STR."How many host's in subnet \{i + 1}: ");
            subnetHosts[i] = scanner.nextInt();
        }
        try {
            readFile();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        sortArrayDescending(subnetHosts); // for some reason, when someone will put number of hosts not in descending order
        checkClass(firstOctet); // checking class of IP
        calculateVSLM(octets, subnetHosts, amountOfSubnet, userMask); // calculate subnets
    }

    public void calculateVSLM(String[] octets, int[] subnetHosts, int amountOfSubnet, String userMask) {
        try {
            FileWriter fileWriter = new FileWriter("SubnetVLMS.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            int range = 256 / amountOfSubnet;
            int firstOctet = Integer.parseInt(octets[0]);
            int secondOctet = Integer.parseInt(octets[1]);
            int thirdOctet = 0, fourthOctet = 0;

            for (int i = 0; i < amountOfSubnet; i++) {
                int tempCheck2 = Integer.parseInt(octets[2]);
                int tempCheck3 = Integer.parseInt(octets[3]);
                if (tempCheck3 > 255) {
                    octets[2] = Integer.toString(tempCheck2 + 1);
                    octets[3] = "0";
                }


                bufferedWriter.write(STR."Subnet \{i + 1}: \{firstOctet}.\{secondOctet}.\{thirdOctet}.\{fourthOctet} \n");
                bufferedWriter.write(STR."First useful address: \{firstOctet}.\{secondOctet}.\{thirdOctet}.\{fourthOctet} \n");
                bufferedWriter.write(STR."Last useful address: \{firstOctet}.\{secondOctet}.\{thirdOctet + range - 1}.\{fourthOctet} \n");
                bufferedWriter.write(STR."Broadcast address: \{firstOctet}.\{secondOctet}.\{thirdOctet + range - 1}.\{fourthOctet} \n");
                bufferedWriter.write(STR."Net mask: \{firstOctet}.\{secondOctet}.\{thirdOctet + range - 1}.\{fourthOctet} \n");
                bufferedWriter.write("\n");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sortArrayDescending(int[] sortedArray) {
        for (int i = 0; i < sortedArray.length - 1; i++) {
            for (int j = 0; j < sortedArray.length - i - 1; j++) {
                if (sortedArray[j] < sortedArray[j + 1]) {
                    int temp = sortedArray[j];
                    sortedArray[j] = sortedArray[j + 1];
                    sortedArray[j + 1] = temp;
                }
            }
        }
    }
    public void checkClass(int firstOctet) {
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
    public static void readFile() throws Exception {
        File file = new File("C:/Users/doria/IdeaProjects/calculatorIP/netMask.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        List<String> prefixes = new ArrayList<>();
        List<String> masks = new ArrayList<>();
        List<String> capacities = new ArrayList<>();

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] parts = line.split(" - ");
            if (parts.length == 3) {
                prefixes.add(parts[0]);
                masks.add(parts[1]);
                capacities.add(parts[2]);
            }
        }
    }
}
