package com.example.__projekt_komputer.casing.hardware.components;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class CPUTest {

    @Test
    void shouldCreateCPUWithValidInput(){
        //Arrange - symulujemy scanner
        String input = "Ryzen 5\n 8\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        CPU cpu = CPU.of("Ryzen 5", 8);

        assertEquals("Ryzen 5", cpu.getName());
        assertEquals(8, cpu.getThreads());
        assertEquals(ComponentType.CPU, cpu.getType());
    }
    @Test
    void shouldCreateCPUWithValidInputFromScanner(){
        //Arrange - symulujemy scanner
        String input = "Ryzen 5\n 8\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        CPUInputReader cpuInputReader = new CPUInputReader(scanner);

        CPU cpu = cpuInputReader.readCPU();

        assertEquals("Ryzen 5", cpu.getName());
        assertEquals(8, cpu.getThreads());
        assertEquals(ComponentType.CPU, cpu.getType());
    }

    @Test
    void shouldThrowExceptionWhenThreadsAreTooHigh(){

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
           CPU.of("Xenon", 20);
        });
        assertEquals("Incorrect thread pool", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenThreadsAreZero(){
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            CPU.of("Xenon", 0);
        });
        assertEquals("Incorrect thread pool", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenThreadsAreTooLow(){
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            CPU.of("Xenon", -20);
        });
        assertEquals("Incorrect thread pool", ex.getMessage());
    }
}
