package com.example.__projekt_komputer.casing.hardware.components;

import java.util.Scanner;

public class CPU implements Components{

    private final String name;
    private final int threads;

    private CPU(String name, int threads) {
        this.name = name;
        this.threads = threads;
    }

    public int getThreads() {
        return threads;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public ComponentType getType() {
        return ComponentType.CPU;
    }

    public static CPU of(String cpuName, int cpuThreads){
        if(cpuThreads>0 && cpuThreads<17){
            return new CPU(cpuName, cpuThreads);
        }else {
            throw new RuntimeException("Incorrect thread pool");
        }
    }
}
