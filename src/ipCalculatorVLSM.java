import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ipCalculatorVLSM {

    List<String> mask = new ArrayList<>();
    List<String> capacity = new ArrayList<>();
    List<String> maskNumber = new ArrayList<>();

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

        sortArrayDescending(subnetHosts); // for some reason, when someone will put number of hosts not in descending order
        readFile();
        checkClass(firstOctet); // checking class of IP
        calculateVLSM(octets, subnetHosts, amountOfSubnet); // calculate subnets
    }

    public void calculateVLSM(String[] octets, int[] subnetHosts, int amountOfSubnet) {
        try {
            FileWriter fileWriter = new FileWriter("SubnetVLSM.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            int firstOctet, secondOctet, thirdOctet, fourthOctet;
            String actualCapacity;

            firstOctet = Integer.parseInt(octets[0]);
            secondOctet = Integer.parseInt(octets[1]);
            thirdOctet = Integer.parseInt(octets[2]);
            fourthOctet = Integer.parseInt(octets[3]);


            for (int i = 0; i < amountOfSubnet; i++) {
                actualCapacity = findClosestCapacity(subnetHosts[i]);

                bufferedWriter.write(STR."LAN \{i + 1}: \{firstOctet}.\{secondOctet}.\{thirdOctet}.\{fourthOctet} \n");
                fourthOctet += 1;
                bufferedWriter.write(STR."First useful address: \{firstOctet}.\{secondOctet}.\{thirdOctet}.\{fourthOctet} \n");
                fourthOctet -= 1;
                fourthOctet += Integer.parseInt(actualCapacity);
                bufferedWriter.write(STR."Last useful address: \{firstOctet}.\{secondOctet}.\{thirdOctet}.\{fourthOctet} \n");
                fourthOctet += 1;
                bufferedWriter.write(STR."Broadcast address: \{firstOctet}.\{secondOctet}.\{thirdOctet}.\{fourthOctet} \n");
                bufferedWriter.write("\n");
                fourthOctet += 1;
                if (fourthOctet > 255)
                {
                    thirdOctet ++;
                    fourthOctet = 0;
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

    public void readFile()
    {
        try {
            File file = new File("netMask.txt");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String lineRead;

            while ((lineRead = bufferedReader.readLine()) != null) {
                String[] parts = lineRead.split(" - ");
                maskNumber.add(parts[0]);
                mask.add(parts[1]);
                capacity.add(parts[2]);
            }
            bufferedReader.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    public String findClosestCapacity(int numberOfHosts) {
        int closestCapacity = Integer.MAX_VALUE;
        String closestCapacityString = "";

        for (String capacity : capacity) {
            int currentCapacity = Integer.parseInt(capacity);
            if (currentCapacity >= numberOfHosts && currentCapacity < closestCapacity) {
                closestCapacity = currentCapacity;
                closestCapacityString = capacity;
            }
        }
        return closestCapacityString;
    }
}
