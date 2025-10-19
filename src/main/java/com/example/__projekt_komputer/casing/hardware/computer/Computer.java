package com.example.__projekt_komputer.casing.hardware.computer;

import com.example.__projekt_komputer.casing.exceptions.Exceptions;
import com.example.__projekt_komputer.casing.hardware.components.*;
import com.example.__projekt_komputer.casing.hardware.components.drive.Drive;
import com.example.__projekt_komputer.casing.hardware.components.usbdevice.USBDevice;
import com.example.__projekt_komputer.casing.software.file.shared.AppFile;

import java.util.ArrayList;
import java.util.List;

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
    public void listComponents() {
        System.out.println("[Monitor]");
        if (getMonitor() != null) {
            System.out.println("Name: " + getMonitor().getName() + ", Resolution: " + getMonitor().getResolution());
            System.out.println();
        } else {
            System.out.println("No monitor found.");
            System.out.println();
        }

        System.out.println("[CPU]");
        if (activeCPU != null) {
            System.out.println("Name: " + activeCPU.getName() + ", Threads: " + activeCPU.getThreads());
            System.out.println();
        } else {
            System.out.println("No CPU found.");
            System.out.println();
        }

        System.out.println("[Active disk]");
        if (activeDrive != null) {
            System.out.println("Name: " + activeDrive.getName() + ", Type: " + activeDrive.getType() + ", Size: " + activeDrive.getCapacity());
            System.out.println();
        } else {
            System.out.println("No active drive found.");
            System.out.println();
        }

        System.out.println("[Inactive disks]");
        if (inactiveDrives != null && !inactiveDrives.isEmpty()) {
            for (Drive drive : inactiveDrives) {
                System.out.println("Name: " + drive.getName() + ", Type: " + drive.getType() + ", Size: " + drive.getCapacity());
            }
        } else {
            System.out.println("No inactive drives found.");
        }
    }


    public void addFile(AppFile appFile){
        if (this.activeDrive == null){
            throw Exceptions.NoActiveDrive();
        }
       getActiveDrive().addFile(appFile);
    }
    public boolean removeFile(String fileName){
        if (this.activeDrive == null){
            throw Exceptions.NoActiveDrive();
        }
        return getActiveDrive().removeFile(fileName);
    }

    public void listFiles(){
        if (this.activeDrive == null){
            throw Exceptions.NoActiveDrive();
        }
        List<AppFile> allAppFiles = getActiveDrive().getAllFiles();
        for (AppFile appFile : allAppFiles) {
            System.out.println(appFile.getName() + ", type: " + appFile.getType() +  ", size: " + appFile.getSize() + "B");
        }
    }

    public void setActiveDrive(Drive drive) {
        if (this.activeDrive != null) {
            inactiveDrives.add(this.activeDrive);
        }
        this.activeDrive = drive;
        components.removeIf(c -> c instanceof Drive);
        components.add(drive);
        inactiveDrives.remove(drive);
    }

    public boolean removeDrive(String driveName){
        if (activeDrive != null && activeDrive.getName().equals(driveName.toUpperCase())) {
            components.remove(activeDrive);
            activeDrive = null;
            System.out.println("Active drive removed. No drive is currently active");
            return true;
        }
        return inactiveDrives.removeIf(drive -> drive.getName().equals(driveName));

    }

    public void changeActiveDrive(String name){
        for (Drive drive : inactiveDrives){
            if (drive.getName().equals(name.toUpperCase())){
                setActiveDrive(drive);
                return;
            }
        }
        throw Exceptions.NoDriveOfGivenName();
    }

    public Drive getActiveDrive() {
        if (activeDrive == null) {
            throw Exceptions.NoActiveDrive();
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
        for (Drive drive : inactiveDrives){
            if (drive instanceof CpuAwareDrive cpuAwareDrive) {
                cpuAwareDrive.setCPU(newCpu);
            }
        }
    }

    public CPU getActiveCPU() {
        if (activeCPU == null) {
            throw new IllegalStateException("No CPU is currently active");
        }
        return activeCPU;
    }

}
