import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ipCalculatorVLSM {
    private final List<String> prefixes = new ArrayList<>();
    private final List<String> masks = new ArrayList<>();
    private final List<String> capacities = new ArrayList<>();

    public ipCalculatorVLSM(String IP, int amountOfSubnet) {

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
        calculateVSLM(octets, subnetHosts, amountOfSubnet); // calculate subnets
    }

    public void calculateVSLM(String[] octets, int[] subnetHosts, int amountOfSubnet) {
        try {
            cleanFile();
            FileWriter fileWriter = new FileWriter("SubnetVLMS.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            int firstOctet = Integer.parseInt(octets[0]);
            int secondOctet = Integer.parseInt(octets[1]);
            int thirdOctet = Integer.parseInt(octets[2]);
            int fourthOctet = Integer.parseInt(octets[3]);

            for (int i = 0; i < amountOfSubnet; i++) {
                int bits = (int) Math.ceil(Math.log(subnetHosts[i] + 2) / Math.log(2)); // Add 2 for network and broadcast addresses
                int mask = 32 - bits;

                int broadcastFourthOctet = fourthOctet + (int) Math.pow(2, bits) - 1;
                int endRangeFourthOctet = broadcastFourthOctet - 1;

                if (broadcastFourthOctet > 255) {
                    thirdOctet++;
                    broadcastFourthOctet -= 256;
                }

                if (endRangeFourthOctet > 255) {
                    thirdOctet++;
                    endRangeFourthOctet -= 256;
                }

                bufferedWriter.write(STR."LAN \{i + 1}: \{firstOctet}.\{secondOctet}.\{thirdOctet}.\{fourthOctet} -> for \{subnetHosts[i]} Hosts\n");
                bufferedWriter.write(STR."First useful address \{firstOctet}.\{secondOctet}.\{thirdOctet}.\{fourthOctet + 1}\n");
                bufferedWriter.write(STR."Last useful address: \{firstOctet}.\{secondOctet}.\{thirdOctet}.\{endRangeFourthOctet}\n");
                bufferedWriter.write(STR."Broadcast address: \{firstOctet}.\{secondOctet}.\{thirdOctet}.\{broadcastFourthOctet}\n\n");

                fourthOctet += Math.pow(2, bits);
                if (fourthOctet > 255) {
                    thirdOctet++;
                    fourthOctet -= 256;
                }
            }
            bufferedWriter.close();
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

    public void readFile() throws Exception {
        File file = new File("netMask.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] parts = line.split(" - ");
            if (parts.length == 3) {
                prefixes.add(parts[0]);
                masks.add(parts[1]);
                capacities.add(parts[2]);
            }
        }
        bufferedReader.close();
    }

    private int findClosestCapacity(int subnetHosts) {
        int closestIndex = -1;
        int minDifference = Integer.MAX_VALUE;

        for (int i = 0; i < capacities.size(); i++) {
            int capacity = Integer.parseInt(capacities.get(i));
            int difference = Math.abs(capacity - subnetHosts);

            if (difference < minDifference) {
                minDifference = difference;
                closestIndex = i;
            }
        }
        return closestIndex;
    }

    public void cleanFile() throws FileNotFoundException {
        PrintWriter writerNames = new PrintWriter("Subnet.txt");
        writerNames.print("");
        writerNames.close();
    }
}
