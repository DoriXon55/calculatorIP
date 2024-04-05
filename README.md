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
    public void cleanFile() throws FileNotFoundException {
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

                bufferedWriter.write(STR."LAN \{i + 1}: \{firstOctet}.\{secondOctet}.\{thirdOctet}.\{fourthOctet} -> dla \{subnetHosts[i]} hostów\n");
                bufferedWriter.write(STR."Fisrt useful address \{firstOctet}.\{secondOctet}.\{thirdOctet}.\{fourthOctet + 1}\n");
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
```

### File example for VLMS
``` txt
LAN 1: 172.16.0.0 -> for 100 Hosts
First useful address 172.16.0.1
Last useful address: 172.16.0.126
Broadcast address: 172.16.0.127

LAN 2: 172.16.0.128 -> for 90 Hosts
First useful address 172.16.0.129
Last useful address: 172.16.0.254
Broadcast address: 172.16.0.255

LAN 3: 172.16.1.0 -> for 56 Hosts
First useful address 172.16.1.1
Last useful address: 172.16.1.62
Broadcast address: 172.16.1.63

LAN 4: 172.16.1.64 -> for 12 Hosts
First useful address 172.16.1.65
Last useful address: 172.16.1.78
Broadcast address: 172.16.1.79

LAN 5: 172.16.1.80 -> for 12 Hosts
First useful address 172.16.1.81
Last useful address: 172.16.1.94
Broadcast address: 172.16.1.95

LAN 6: 172.16.1.96 -> for 4 Hosts
First useful address 172.16.1.97
Last useful address: 172.16.1.102
Broadcast address: 172.16.1.103

LAN 7: 172.16.1.104 -> for 2 Hosts
First useful address 172.16.1.105
Last useful address: 172.16.1.106
Broadcast address: 172.16.1.107

LAN 8: 172.16.1.108 -> for 2 Hosts
First useful address 172.16.1.109
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

As you can see, I have range of diffrent classes and I check it with if statements.

For now. That's all
  



@Dorixon55
