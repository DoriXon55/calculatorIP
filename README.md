## TABLE OF CONTENTS
* [General info](#general-info)
* [Setup](#setup)

## General info
This "project" is for Computer Networks.
It divides a given network into subnets, making calculations easier.

## Setup

### ipCalculatorStatic
To run this calculator you must have Oracle OpenJDK version 22 and IDE or something where you can run this code.
When you run the code, you must give to the terminal information about network you want to divine and amount of
this divines. Your result will be save in separate file, so be careful and don't rage when you will see only
information about IP class in terminal. To change file you must change fileName in this line: 
``` java
FileWriter fileWriter = new FileWriter("PodziałSieci.txt");
```
You can change "PodzialSieci.txt" for everything you want and can.

- One of the function in this part is cleanFile. Idk if this work, because I read that on IDE this doesn't work, so I must check this on diffrent time.
``` java
    public static void cleanFile() throws FileNotFoundException
    {
        PrintWriter writerNames = new PrintWriter("Subnet.txt");
        writerNames.print("");
        writerNames.close();
    }
```


---

### ipCalculatorVLSM
This part of code handle the VLSM divine method. You can find there constructor -> this is first and main part of code. There octets are checked, hosts are entered ect.
Under the constructor you find a three functions. 
- First -> calculateVSLM -> there are all of calculating process and file overwriting.
``` java
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
```
A lot of bufferedWriter but I don't have any idea to change that so this must stay in this place for some amount of time.

- Second -> sortArrayDescending -> for invalid input. Because in VLSM method when we designate subnets, we must do it in descending way.
``` java
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
```
Simply sort function. We iterate over the array and on every itteration, we iterate whole array to check whether the elements stand well.

- Third -> readFile -> there the netMask file is read and written appropriately in parts into lists.
``` java
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
```
In this function I use list, because this is easier to change ealier. You can put diffrent file and this should be work fine.

- Fourth (I almost forgot) -> checkClass -> checking class of IP.
``` java
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
```
As you can see, I have range of diffrent classes and I check it with if statements.

For now. That's all
  



@Dorixon55
