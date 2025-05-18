package com.example.__projekt_komputer.computer.hardware.computer;

import com.example.__projekt_komputer.computer.hardware.components.Components;
import com.example.__projekt_komputer.computer.hardware.components.Headphones;
import com.example.__projekt_komputer.computer.hardware.components.Monitor;
import com.example.__projekt_komputer.computer.hardware.components.drive.Drive;
import com.example.__projekt_komputer.computer.hardware.components.usbdevice.MemoryStick;
import com.example.__projekt_komputer.computer.software.file.shared.File;
import com.example.__projekt_komputer.computer.hardware.components.usbdevice.USBDevice;
import com.example.__projekt_komputer.computer.software.file.shared.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class Computer {
    List<Components> components = new ArrayList<>();

    private static Computer instance;

    private Computer(Monitor monitor, Drive drive) {
        components.add(monitor);
        components.add(drive);
        // run();   dodać metodę uruchamiającą komputer, a w niej będzie ui (tak jakby odpali się terminal systemowy :P), jeżeli będzie w konstruktorze zostanie automatycznie wywołana podczas inicializowania instancji Computer
    }

    public static Computer getInstance(Monitor monitor, Drive drive){
        if (instance == null){
            instance = new Computer(monitor, drive);
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

    public MemoryStick getMemoryStickByName(String name){
        List<USBDevice> usbDevices = getUSBDevices();
        for (USBDevice usbDevice : usbDevices) {
            if (usbDevice instanceof MemoryStick) {
                if ( usbDevice.getName().equals(name) ) {
                    return (MemoryStick) usbDevice;
                }
            }
        }
        throw new RuntimeException("Memory stick '" + name + "' device not found");
    }

    public void addComponent(Components component) {
        components.add(component);
    }

    public void removeComponent(Components component){
        components.remove(component);
    }

    public void addFile(File file){ //TODO metody działające na plikach trzeba dostosować do możliwości posiadania różnych nośników (twardy dysk, pendrive)
        var drive = getDrive();
        drive.addFile(file);
        components.add(drive);
    }

    public void listFiles(){
        var drive = getDrive();
        drive.listFiles();
    }

    public File findFile(String fileName) {
        var drive = getDrive();
        try {
            return drive.findFile(fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private Drive getDrive() {
        for (Components component : components) {
            if (component instanceof Drive) {
                return (Drive) component;
            }
        }
        throw new RuntimeException("No component found");
    }
}
