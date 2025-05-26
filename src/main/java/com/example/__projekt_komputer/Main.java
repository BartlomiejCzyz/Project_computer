package com.example.__projekt_komputer;

import com.example.__projekt_komputer.computer.hardware.components.Monitor;
import com.example.__projekt_komputer.computer.hardware.components.drive.Drive;
import com.example.__projekt_komputer.computer.hardware.components.drive.HDDDrive;
import com.example.__projekt_komputer.computer.hardware.components.usbdevice.USBDevice;
import com.example.__projekt_komputer.computer.hardware.computer.Computer;
import com.example.__projekt_komputer.computer.hardware.computer.MenuIndicator;
import com.example.__projekt_komputer.computer.hardware.computer.MenuOption;
import com.example.__projekt_komputer.computer.software.file.shared.Capacity;
import com.example.__projekt_komputer.computer.software.file.shared.FileService;

import java.util.List;
import java.util.Scanner;

public class Main {
    private final FileService fileService;

    public Main(FileService fileService) {
        this.fileService = fileService;
    }
    public void run() {
        Scanner scanner = new Scanner(System.in);
        Monitor monitor = new Monitor("Dell");
        Drive   hddDrive = new HDDDrive("HDDDrive", Capacity.GB64, fileService);
        Computer computer = Computer.getInstance(monitor, hddDrive);
        List<USBDevice> usbDevices = computer.getUSBDevices();

        MenuOption userChoice;
        hddDrive.listFiles();

        do {
            System.out.println("""
                    Choose the submenu
                    1. USB devices
                    2. Files
                    3. Hardware
                    4. end <- to exit
                    """);
            userChoice = MenuOption.chosenAction(scanner.nextLine(), MenuIndicator.MAIN_MENU);

            switch (userChoice){

                case USB_DEVICES_MENU ->{
                    do{
                        System.out.println("""
                        Choose the option
                        1. Add USB device
                        2. Remove USB device
                        3. List USB devices
                        back <- to go back
                        end <- to exit
                        """);
                        userChoice = MenuOption.chosenAction(scanner.nextLine(),MenuIndicator.USB_MENU);

                        switch (userChoice){
                            case ADD_USB_DEVICE ->{
                                System.out.println("Adding USB device");
                            }
                            case REMOVE_USB_DEVICE ->{
                                System.out.println("Removing USB device");
                            }
                            case LIST_USB_DEVICES ->{
                                for (USBDevice device : usbDevices){
                                    System.out.println(device.getName());
                                }
                            }
                            case END ->{
                                System.exit(0);
                            }
                            default -> {
                                if (!userChoice.equals(MenuOption.BACK)){
                                    System.out.println("Wrong option");
                                }
                            }
                        }
                    }while (!userChoice.equals(MenuOption.BACK));
                }
                case FILES_MENU ->{
                    do{
                        System.out.println("""
                        Choose the option
                        1. Add file
                        2. Remove file
                        3. Find file by name
                        4. Fond file by content
                        5. List all files
                        back <- to go back
                        end <- to exit
                        """);
                        userChoice = MenuOption.chosenAction(scanner.nextLine(), MenuIndicator.FILE_MENU);

                        switch (userChoice){
                            case ADD_FILE ->{
                                System.out.println("Adding file");
                            }
                            case REMOVE_FILE ->{
                                System.out.println("Removing file");
                            }
                            case FIND_FILE_BY_NAME ->{
                                System.out.println("Finding file by name");
                            }
                            case FIND_FILE_BY_CONTENT -> {
                                System.out.println("Finding file by content");
                            }
                            case LIST_ALL_FILES ->{
                                computer.listFiles();
                            }
                            case END ->{
                                System.exit(0);
                            }
                            default -> {
                                if (!userChoice.equals(MenuOption.BACK)){
                                    System.out.println("Wrong option");
                                }
                            }
                        }
                    }while (!userChoice.equals(MenuOption.BACK));

                }
                case HARDWARE_MENU ->{
                    do {
                        System.out.println("""
                        Choose the option
                        1. Add hardware
                        2. Remove hardware
                        3. List hardware
                        4. Set high monitor resolution
                        5. Set low monitor resolution
                        6. Change headphone's volume
                        7. Show current headphone's volume
                        back <- to go back
                        end <- to exit
                        """);
                        userChoice = MenuOption.chosenAction(scanner.nextLine(), MenuIndicator.HARDWARE_MENU);

                        switch (userChoice){
                            case ADD_HARDWARE ->{
                                System.out.println("Adding hardware");
                            }
                            case REMOVE_HARDWARE ->{
                                System.out.println("Removing hardware");
                            }
                            case LIST_HARDWARE ->{
                                System.out.println("Listing hardware");
                            }
                            case SET_HIGH_MONITOR_RESOLUTION ->{
                                monitor.setHeightResolution();
                                System.out.println(monitor.getResolution());
                            }
                            case SET_LOW_MONITOR_RESOLUTION ->{
                                monitor.setLowResolution();
                                System.out.println(monitor.getResolution());
                            }
                            case CHANGE_HEADPHONE_VOLUME ->{
                                System.out.println("Set new volume (0-100)");
                            }
                            case SHOW_CURRENT_HEADPHONE_VOLUME->{
                                System.out.println("showing volume");
                            }
                            case END ->{
                                System.exit(0);
                            }
                            default -> {
                                if (!userChoice.equals(MenuOption.BACK)){
                                    System.out.println("Wrong option");
                                }
                            }
                        }
                    }while (!userChoice.equals(MenuOption.BACK));

                }
            }
        }while (!userChoice.equals(MenuOption.END));

        System.out.println("cos");

    }
}