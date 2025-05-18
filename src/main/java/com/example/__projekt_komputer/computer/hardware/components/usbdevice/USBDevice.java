package com.example.__projekt_komputer.computer.hardware.components.usbdevice;

import com.example.__projekt_komputer.computer.hardware.components.ComponentType;
import com.example.__projekt_komputer.computer.hardware.components.Components;

import java.util.Scanner;

public abstract class USBDevice implements Components {
    protected String name;
    protected boolean connected = false;
    protected boolean safeDisconnect = false;

    public USBDevice(String name) {
        this.name = name;
    }

    public void connect(){
        if(connected) {
            System.out.println(this.getName() + " is already connected");
        } else {
            System.out.println(this.getName() + " connected");
            connected = true;
        }
    }

    public void disconnect(){
        Scanner scanner = new Scanner(System.in);
        String chose;
        if(safeDisconnect){
            System.out.println(this.getName() + " disconnected");
            safeDisconnect = false;
            connected = false;
        } else {
            System.out.println("Safe disconnect not ready. Are you sure you want to disconnect? [Y/N]");
            chose = scanner.next();

            switch(chose){
                case "Y" ->{
                    System.out.println(this.getName() + " force disconnected");
                    connected = false;
                }
                case "N" ->{
                    System.out.println(this.getName() + " is still connected... Preparing safe disconnect..." +
                            this.getName() + " disconnected");
                    safeDisconnect = false;
                    connected = false;
                }
            }
        }
    }

    public boolean isConnected(){
        return connected;
    }

    protected boolean isSafeDisconnect() {
        return safeDisconnect;
    }

    protected void setSafeDisconnect(boolean safeDisconnect) {
        this.safeDisconnect = safeDisconnect;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ComponentType getType() {
        return ComponentType.USB;
    }
}
