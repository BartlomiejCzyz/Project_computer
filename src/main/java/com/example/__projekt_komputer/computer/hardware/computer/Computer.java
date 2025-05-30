package com.example.__projekt_komputer.computer.hardware.computer;

import com.example.__projekt_komputer.computer.hardware.components.CPU;
import com.example.__projekt_komputer.computer.hardware.components.Components;
import com.example.__projekt_komputer.computer.hardware.components.Headphones;
import com.example.__projekt_komputer.computer.hardware.components.Monitor;
import com.example.__projekt_komputer.computer.hardware.components.drive.Drive;
import com.example.__projekt_komputer.computer.hardware.components.usbdevice.USBDevice;
import com.example.__projekt_komputer.computer.software.file.shared.File;
import com.example.__projekt_komputer.computer.software.file.shared.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class Computer {
    List<Components> components = new ArrayList<>();
    private Drive activeDrive;
    private final List<Drive> inactiveDrives = new ArrayList<>();


    private static Computer instance;

    private Computer(Monitor monitor, Drive drive, CPU cpu) {
        components.add(monitor);
        components.add(drive);
        components.add(cpu);
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


    public void addComponent(Components component) {
        components.add(component);
    }

    public void removeComponent(Components component){
        components.remove(component);
    }
    public void listComponents(){
        components.forEach(System.out::println);
    }

    public void addFile(File file){
        var drive = getActiveDrive();
        drive.addFile(file);
        components.add(drive);
    }

    public void listFiles(){
        var drive = getActiveDrive();
        drive.listFiles();
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
            if (drive.getName().equals(name)){
                setActiveDrive(drive);
                break;
            }
        }
        throw new IllegalStateException("No drive of this name");
    }
    public Drive getActiveDrive() {
        if (activeDrive == null) {
            throw new IllegalStateException("No active drive is currently set");
        }
        return activeDrive;
    }
}
