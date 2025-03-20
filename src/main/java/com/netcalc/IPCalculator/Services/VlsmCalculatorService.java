package com.netcalc.IPCalculator.Services;

import com.netcalc.IPCalculator.Models.SubnetRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VlsmCalculatorService {
    public String calculateVlsm(SubnetRequest request) {
        List<Integer> hostRequirements = request.getHostRequirements();
        if (hostRequirements == null || hostRequirements.isEmpty() ||
                hostRequirements.size() != request.getAmountOfSubnets()) {
            return "Błąd: Nieprawidłowa liczba";
        }

        if (hostRequirements.stream().anyMatch(h -> h < 1)) {
            return "Błąd: Liczba hostów musi być większa niż 0";
        }

        StringBuilder result = new StringBuilder();
        result.append("Adres IP: ").append(request.getIp()).append("\n");
        result.append("Liczba podsieci: ").append(request.getAmountOfSubnets()).append("\n\n");

        List<Integer> sortedRequirements = new ArrayList<>(hostRequirements);
        sortedRequirements.sort((a, b) -> b - a);

        long currentAddress = ipToLong(request.getIp());
        int[] original = hostRequirements.stream().mapToInt(Integer::intValue).toArray();
        int[] sorted = sortedRequirements.stream().mapToInt(Integer::intValue).toArray();
        int[] order = new int[hostRequirements.size()];

        for (int i = 0; i < original.length; i++) {
            for (int j = 0; j < sorted.length; j++) {
                if (original[i] == sorted[j] && !contains(order, j, i)) {
                    order[i] = j;
                    break;
                }
            }
        }

        long[] networkAddresses = new long[hostRequirements.size()];
        int[] subnetMasks = new int[hostRequirements.size()];
        long[] broadcasts = new long[hostRequirements.size()];

        for (int i = 0; i < sortedRequirements.size(); i++) {
            int hostBits = calculateRequiredBits(sortedRequirements.get(i));
            int subnetMask = 32 - hostBits;

            networkAddresses[i] = currentAddress;
            broadcasts[i] = currentAddress + (1L << hostBits) - 1;
            subnetMasks[i] = subnetMask;
            currentAddress = broadcasts[i] + 1;
        }

        for (int i = 0; i < hostRequirements.size(); i++) {
            result.append("Podsieć ").append(i + 1).append(":\n");
            result.append("  Wymagana liczba hostów: ").append(hostRequirements.get(i)).append("\n");
            result.append("  Adres sieci: ").append(longToIp(networkAddresses[order[i]])).append("/")
                    .append(subnetMasks[order[i]]).append("\n");
            result.append("  Pierwszy użyteczny: ").append(longToIp(networkAddresses[order[i]] + 1)).append("\n");
            result.append("  Ostatni użyteczny: ").append(longToIp(broadcasts[order[i]] - 1)).append("\n");
            result.append("  Broadcast: ").append(longToIp(broadcasts[order[i]])).append("\n");
            result.append("  Dostępne hosty: ").append((1 << (32 - subnetMasks[order[i]])) - 2).append("\n\n");
        }

        return result.toString();
    }

    private boolean contains(int[] array, int value, int upTo) {
        for (int i = 0; i < upTo; i++) {
            if (array[i] == value) return true;
        }
        return false;
    }

    private int calculateRequiredBits(int hosts) {
        return (int) Math.ceil(Math.log(hosts + 2) / Math.log(2));
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