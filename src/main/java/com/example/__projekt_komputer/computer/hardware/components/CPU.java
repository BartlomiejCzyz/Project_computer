package com.example.__projekt_komputer.computer.hardware.components;

import java.util.Scanner;

public class CPU implements Components{

    private final String name;
    private final int threads;

    public CPU(String name, int threads) {
        this.name = name;
        this.threads = threads;
    }

    public int getThreads() {
        return threads;
    }

    @Override
    public String getName() {
        return getName();
    }

    @Override
    public ComponentType getType() {
        return ComponentType.CPU;
    }
    public static CPU createCPU(Scanner scanner){
        System.out.println("Enter cpu name: ");
        String cpuName = scanner.nextLine();
        System.out.println("Enter the number of threads(1-16): ");
        int  cpuThreads = scanner.nextInt();
        scanner.nextLine();

        if(cpuThreads>0 && cpuThreads<17){
            return new CPU(cpuName, cpuThreads);
        }else {
            throw new RuntimeException("Incorrect thread pool");
        }


    }
}
