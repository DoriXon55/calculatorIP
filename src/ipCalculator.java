public class ipCalculator {

    private static String startIP;
    private static String endIP;
    public ipCalculator(int amountOfSubnet, String classChoice)
    {
        switch (classChoice) {
            case "A":
                classA(amountOfSubnet);
                break;
            case "B":
                classB(amountOfSubnet);
                break;
            case "C":
                classC(amountOfSubnet);
                break;
            case "D":
                classD(amountOfSubnet);
                break;
            case "E":
                classE(amountOfSubnet);
                break;
            default:
                System.out.println("Wrong class choice! Try again.");
        }
    }

    public static String getStartIP() {
        return startIP;
    }

    public static void setStartIP(String startIP) {
        ipCalculator.startIP = startIP;
    }

    public static String getEndIP() {
        return endIP;
    }

    public static void setEndIP(String endIP) {
        ipCalculator.endIP = endIP;
    }

    public void classA(int amountOfSubnet)
    {
        String resultIP;
        int i = 0;
        setStartIP("1.0.0.1");
        setEndIP("127.255.255.254");

        while(i < amountOfSubnet)
        {
            System.out.println("Working");
            i++;
        }
    }
    public void classB(int amountOfSubnet)
    {
        setStartIP("128.0.0.0");
        setEndIP("191.255.255.255");
        System.out.println("Working");
    }
    public void classC(int amountOfSubnet)
    {
        setStartIP("192.0.0.0");
        setEndIP("223.255.255.255");
        System.out.println("Working");
    }
    public void classD(int amountOfSubnet)
    {
        setStartIP("224.0.0.0");
        setEndIP("239.255.255.254");
        System.out.println("Working");
    }
    public void classE(int amountOfSubnet)
    {
        setStartIP("240.0.0.0");
        setEndIP("255.255.255.255");
        System.out.println("Working");
    }
}
