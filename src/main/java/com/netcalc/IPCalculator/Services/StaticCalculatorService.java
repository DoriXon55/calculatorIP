package com.netcalc.IPCalculator.Services;

import com.netcalc.IPCalculator.Models.SubnetRequest;
import org.springframework.stereotype.Service;

@Service
public class StaticCalculatorService {
    public boolean isValidIp(String ip) {
        if (ip == null || ip.isEmpty()) return false;
        String[] parts = ip.split("\\.");
        if (parts.length != 4) return false;

        try {
            for (String part : parts) {
                int value = Integer.parseInt(part);
                if (value < 0 || value > 255) return false;
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isSubnettingPossible(String ip, int subnets) {
        String[] parts = ip.split("\\.");
        int firstOctet = Integer.parseInt(parts[0]);
        int availableBits;

        if (firstOctet <= 127) availableBits = 24;
        else if (firstOctet <= 191) availableBits = 16;
        else if (firstOctet <= 223) availableBits = 8;
        else return false;

        int requiredBits = (int) Math.ceil(Math.log(subnets) / Math.log(2));
        return requiredBits <= availableBits;
    }

    public String calculateStatic(SubnetRequest request) {
        StringBuilder result = new StringBuilder();
        String[] parts = request.getIp().split("\\.");
        int networkBits = (int) Math.ceil(Math.log(request.getAmountOfSubnets()) / Math.log(2));

        int mask;
        if (Integer.parseInt(parts[0]) <= 127) mask = 8 + networkBits;
        else if (Integer.parseInt(parts[0]) <= 191) mask = 16 + networkBits;
        else mask = 24 + networkBits;

        result.append("Adres IP: ").append(request.getIp()).append("\n");
        result.append("Liczba podsieci: ").append(request.getAmountOfSubnets()).append("\n");
        result.append("Maska: /").append(mask).append("\n\n");

        int increment = (int) Math.pow(2, 32 - mask);
        long baseAddress = ipToLong(request.getIp());

        for (int i = 0; i < request.getAmountOfSubnets(); i++) {
            long subnetAddress = baseAddress + (i * increment);
            result.append("Podsieć ").append(i + 1).append(": ")
                    .append(longToIp(subnetAddress)).append("/").append(mask).append("\n");
        }

        return result.toString();
    }

    private long ipToLong(String ip) {
        String[] parts = ip.split("\\.");
        long result = 0;
        for (int i = 0; i < 4; i++) {
            result = result << 8 | Integer.parseInt(parts[i]);
        }
        return result;
    }

    private String longToIp(long ip) {
        return String.format("%d.%d.%d.%d",
                (ip >> 24) & 0xFF,
                (ip >> 16) & 0xFF,
                (ip >> 8) & 0xFF,
                ip & 0xFF);
    }
}