package com.example.__projekt_komputer.computer.hardware.components;

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
        return "";
    }

    @Override
    public ComponentType getType() {
        return null;
    }
}
