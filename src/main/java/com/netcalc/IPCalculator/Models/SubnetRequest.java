package com.netcalc.IPCalculator.Models;

import java.util.List;

public class SubnetRequest {
    private String ip;
    private int amountOfSubnets;
    private String method;
    private List<Integer> hostRequirements;

    public SubnetRequest(String ip, int amountOfSubnets, String method) {
        this.ip = ip;
        this.amountOfSubnets = amountOfSubnets;
        this.method = method;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getAmountOfSubnets() {
        return amountOfSubnets;
    }

    public void setAmountOfSubnets(int amountOfSubnets) {
        this.amountOfSubnets = amountOfSubnets;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<Integer> getHostRequirements() {
        return hostRequirements;
    }

    public void setHostRequirements(List<Integer> hostRequirements) {
        this.hostRequirements = hostRequirements;
    }
}