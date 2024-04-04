import java.io.*;
import java.lang.String;

public class ipCalculator {
    public ipCalculator(String IP, int amountOfSubnet) {
        String[] octets = IP.split("\\.");
        int firstOctet;

        if (octets.length != 4 && !octets[3].equals("0")) {
            System.out.println("Podano nieprawidłowy adres IP!");
            return;
        }
        firstOctet = Integer.parseInt(octets[0]);

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
            subnetDivine(octets, amountOfSubnet);


    }

    public void subnetDivine(String[] octets, int amountOfSubnet) {
        try {
            cleanFile();
            FileWriter fileWriter = new FileWriter("PodziałSieci.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            int count = 0;
            int range = 256 / amountOfSubnet;
            int firstOctet = Integer.parseInt(octets[0]);
            int secondOctet = Integer.parseInt(octets[1]);
            int thirdOctet = Integer.parseInt(octets[2]);
            int fourthOctet;

            for (int i = 0; i < amountOfSubnet; i++) {
                fourthOctet = 0;
                bufferedWriter.write(STR."Podsieć \{i + 1}: \{firstOctet}.\{secondOctet}.\{thirdOctet}.\{fourthOctet} \n");
                fourthOctet = 1;
                bufferedWriter.write(STR."Pierwszy użyteczny adres: \{firstOctet}.\{secondOctet}.\{thirdOctet}.\{fourthOctet} \n");
                fourthOctet = 254;
                bufferedWriter.write(STR."Ostatni użyteczny adres: \{firstOctet}.\{secondOctet}.\{thirdOctet + range - 1}.\{fourthOctet} \n");
                fourthOctet = 255;
                bufferedWriter.write(STR."Adres rozgłoszeniowy: \{firstOctet}.\{secondOctet}.\{thirdOctet + range - 1}.\{fourthOctet} \n");
                bufferedWriter.write("\n");
                count += range;
                thirdOctet += range;
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void cleanFile() throws FileNotFoundException
    {
        PrintWriter writerNames = new PrintWriter("PodziałSieci.txt");
        writerNames.print("");
        writerNames.close();
    }

}

