package com.example.__projekt_komputer.computer.hardware.computer;

import com.example.__projekt_komputer.computer.hardware.components.*;
import com.example.__projekt_komputer.computer.hardware.components.drive.Drive;
import com.example.__projekt_komputer.computer.hardware.components.usbdevice.USBDevice;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Computer {
    List<Components> components = new ArrayList<>();
    private Drive activeDrive;
    private final List<Drive> inactiveDrives = new ArrayList<>();


    private static Computer instance;
    private CPU activeCPU;

    private Computer(Monitor monitor, Drive drive, CPU cpu) {
        components.add(monitor);
        components.add(drive);
        components.add(cpu);
        this.activeCPU = cpu;
    }

    public static Computer getInstance(Monitor monitor, Drive drive, CPU cpu){
        if (instance == null){
            instance = new Computer(monitor, drive, cpu);
        }
        return instance;
    }

    public Monitor getMonitor() {
        for (Components component : components) {
            if (component instanceof Monitor) {
                return (Monitor) component;
            }
        }
        throw new RuntimeException("No component found");
    }

    public Headphones getHeadphones() {
        for (Components component : components) {
            if (component instanceof Headphones) {
                return (Headphones) component;
            }
        }
        throw new RuntimeException("No component found");
    }

    public List<USBDevice> getUSBDevices() {
        List<USBDevice> usbDevices = new ArrayList<>();
        for (Components component : components) {
            if (component instanceof USBDevice) {
                usbDevices.add((USBDevice) component);
            }
        }
        return usbDevices;
    }

    public void removeDrive(String driveName){
        if (activeDrive != null && activeDrive.getName().equals(driveName)) {
            components.remove(activeDrive);
            activeDrive = null;
            System.out.println("Active drive removed. No drive is currently active");
            return;
        }
        inactiveDrives.removeIf(drive -> drive.getName().equals(driveName));

    }
    public void listComponents(){
        System.out.println("[Monitor] \n" + "Name: " + getMonitor().getName() + ", Resolution: " + getMonitor().getResolution());
        System.out.println();
        System.out.println("[CPU] \n" + "Name: " + activeCPU.getName() + ", Threads: " + activeCPU.getThreads());
        System.out.println();
        System.out.println("[Active disk] \n" + "Name: " + activeDrive.getName() + ", Type: " + activeDrive.getType() + ", Size: " + activeDrive.getCapacity());
        System.out.println();
        System.out.println("[Inactive disks]");
        for (Drive drive : inactiveDrives){
            System.out.println("Name: " + drive.getName() + ", Type: " + drive.getType() + ", Size: " + drive.getCapacity());
        }
    }

    public void addFile(Scanner scanner){
        if (this.activeDrive == null){
            throw new IllegalStateException("No drive is currently active");
        }
       getActiveDrive().addFile(scanner);
    }
    public void removeFile(String fileName){
        if (this.activeDrive == null){
            throw new IllegalStateException("No drive is currently active");
        }
        getActiveDrive().removeFile(fileName);
    }

    public void listFiles(){
        if (this.activeDrive == null){
            throw new IllegalStateException("No drive is currently active");
        }
        getActiveDrive().listFiles();
    }

    public void setActiveDrive(Drive drive) {
        if (this.activeDrive != null) {
            inactiveDrives.add(this.activeDrive);
        }
        this.activeDrive = drive;
        components.removeIf(c -> c instanceof Drive);
        components.add(drive);
    }

    public void changeActiveDrive(String name){
        for (Drive drive : inactiveDrives){
            if (drive.getName().equals(name.toUpperCase())){
                setActiveDrive(drive);
                return;
            }
        }
        throw new IllegalStateException("No drive of this name");
    }

    public Drive getActiveDrive() {
        if (activeDrive == null) {
            throw new IllegalStateException("No drive is currently active");
        }
        return activeDrive;
    }

    public void changeCPU(CPU newCpu) {
        components.removeIf(component -> component instanceof CPU);
        components.add(newCpu);
        this.activeCPU = newCpu;

        if (activeDrive instanceof CpuAwareDrive cpuAwareDrive) {
            cpuAwareDrive.setCPU(newCpu);
        }
    }

    public CPU getActiveCPU() {
        if (activeCPU == null) {
            throw new IllegalStateException("No CPU is currently active");
        }
        return activeCPU;
    }

}
