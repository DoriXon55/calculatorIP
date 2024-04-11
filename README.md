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
FileWriter fileWriter = new FileWriter("Subnet.txt");
```
You can change "Subnet.txt" for everything you want and can.




---

### ipCalculatorVLSM
This part of code handle the VLSM divine method. You can find there constructor -> this is first and main part of code. There octets are checked, hosts are entered ect.
Under the constructor you find a three functions. 
- First -> calculateVSLM -> there are all of calculating process and file overwriting.
``` java
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
- Five -> findClosestCapacity -> finds closest capacity for LAN
``` java
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
```

### File example for VLSM
``` txt
LAN 1: 172.16.0.0 
First useful address: 172.16.0.1 
Last useful address: 172.16.0.126 
Broadcast address: 172.16.0.127 

LAN 2: 172.16.0.128 
First useful address: 172.16.0.129 
Last useful address: 172.16.0.254 
Broadcast address: 172.16.0.255 

LAN 3: 172.16.1.0 
First useful address: 172.16.1.1 
Last useful address: 172.16.1.62 
Broadcast address: 172.16.1.63 

LAN 4: 172.16.1.64 
First useful address: 172.16.1.65 
Last useful address: 172.16.1.78 
Broadcast address: 172.16.1.79 

LAN 5: 172.16.1.80 
First useful address: 172.16.1.81 
Last useful address: 172.16.1.94 
Broadcast address: 172.16.1.95 

LAN 6: 172.16.1.96 
First useful address: 172.16.1.97 
Last useful address: 172.16.1.102 
Broadcast address: 172.16.1.103 

LAN 7: 172.16.1.104 
First useful address: 172.16.1.105 
Last useful address: 172.16.1.106 
Broadcast address: 172.16.1.107 

LAN 8: 172.16.1.108 
First useful address: 172.16.1.109 
Last useful address: 172.16.1.110 
Broadcast address: 172.16.1.111 
```

### File example for Static
``` txt
LAN 1: 172.16.0.0 
First useful address: 172.16.0.1 
Last useful address: 172.16.31.254 
Broadcast address: 172.16.31.255 

LAN 2: 172.16.32.0 
First useful address: 172.16.32.1 
Last useful address: 172.16.63.254 
Broadcast address: 172.16.63.255 

LAN 3: 172.16.64.0 
First useful address: 172.16.64.1 
Last useful address: 172.16.95.254 
Broadcast address: 172.16.95.255 

LAN 4: 172.16.96.0 
First useful address: 172.16.96.1 
Last useful address: 172.16.127.254 
Broadcast address: 172.16.127.255 

LAN 5: 172.16.128.0 
First useful address: 172.16.128.1 
Last useful address: 172.16.159.254 
Broadcast address: 172.16.159.255 

LAN 6: 172.16.160.0 
First useful address: 172.16.160.1 
Last useful address: 172.16.191.254 
Broadcast address: 172.16.191.255 

LAN 7: 172.16.192.0 
First useful address: 172.16.192.1 
Last useful address: 172.16.223.254 
Broadcast address: 172.16.223.255 

LAN 8: 172.16.224.0 
First useful address: 172.16.224.1 
Last useful address: 172.16.255.254 
Broadcast address: 172.16.255.255 
```

For now. That's all
  



@Dorixon55
