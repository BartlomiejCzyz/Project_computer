package com.example.__projekt_komputer.casing.hardware.components;

import java.util.Scanner;

public class CPUInputReader {

    private final Scanner scanner;

    public CPUInputReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public CPU readCPU(){
        System.out.println("Enter cpu name: ");
        String cpuName = scanner.nextLine();
        System.out.println("Enter the number of threads(1-16): ");

        //TODO sprawdzić czy wątki są liczbą i napisać do tego test
        int  cpuThreads = scanner.nextInt();
        scanner.nextLine();

        return CPU.of(cpuName, cpuThreads);
    }
}
