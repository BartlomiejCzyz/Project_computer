package com.example.__projekt_komputer;

import com.example.__projekt_komputer.computer.hardware.components.CPU;
import com.example.__projekt_komputer.computer.hardware.components.Monitor;
import com.example.__projekt_komputer.computer.hardware.components.drive.Drive;
import com.example.__projekt_komputer.computer.hardware.components.drive.DriveFactory;
import com.example.__projekt_komputer.computer.hardware.components.drive.HDDDrive;
import com.example.__projekt_komputer.computer.hardware.components.drive.SSDDrive;
import com.example.__projekt_komputer.computer.hardware.computer.Computer;
import com.example.__projekt_komputer.computer.hardware.computer.MenuIndicator;
import com.example.__projekt_komputer.computer.hardware.computer.MenuOption;
import com.example.__projekt_komputer.computer.software.file.shared.Capacity;
import com.example.__projekt_komputer.computer.software.file.shared.File;
import com.example.__projekt_komputer.computer.software.file.shared.FileNotFoundException;
import com.example.__projekt_komputer.computer.software.file.shared.FileService;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Scanner;

public class Main {
    private final FileService fileService;

    public Main(FileService fileService) {
        this.fileService = fileService;
    }
    public void run() throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);

        Monitor monitor = new Monitor("Dell");

        System.out.println("Computer creator: ");

        //CPU cpu = new CPU("intel", 8);
        CPU cpu = CPU.createCPU(scanner);

        Drive drive = DriveFactory.createDrive(scanner, fileService, cpu);
       //Drive drive = new SSDDrive("ssdrive", Capacity.GB64, fileService, cpu);

        Computer computer = Computer.getInstance(monitor, drive, cpu);
        computer.setActiveDrive(drive);

        MenuOption userChoice;


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
                        
                        [USB devices menu]
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
                               // for (USBDevice device : usbDevices){
                                  //  System.out.println(device.getName());
                                //}
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
                        
                        [Files menu]
                        Choose the option
                        1. Add file
                        2. Remove file
                        3. Find file by name
                        4. Find file by content
                        5. List all files
                        back <- to go back
                        end <- to exit
                        """);
                        userChoice = MenuOption.chosenAction(scanner.nextLine(), MenuIndicator.FILE_MENU);

                        switch (userChoice){
                            case ADD_FILE ->{
                                computer.addFile(scanner);
                            }
                            case REMOVE_FILE ->{
                                System.out.println("Enter file name to remove: ");
                                String fileName = scanner.nextLine();
                                computer.removeFile(fileName);
                            }
                            case FIND_FILE_BY_NAME ->{
                                System.out.println("Enter file name: ");
                                String fileName = scanner.nextLine();
                                File fileByName = drive.findFileByName(fileName);
                                System.out.println("Name: " + fileByName.getName() + ", type: " + fileByName.getType() + ", size: " + fileByName.getSize() + ", content: \n" + fileByName.getContent());
                            }
                            case FIND_FILE_BY_CONTENT -> {
                                try {
                                    Instant start = Instant.now();
                                    System.out.println("Enter fragment of text you are looking for: ");
                                    String textFragment = scanner.nextLine();
                                    List<File> fileFound = computer.getActiveDrive().findFileByContent(textFragment);
                                    Instant end = Instant.now();
                                    long duration = Duration.between(start, end).toMillis();
                                    long durationSec = duration / 1000;
                                    long durationMili = duration % 1000;

                                    for (File file : fileFound){
                                        System.out.println(file.getName());
                                    }
                                    System.out.println("Operation took: " + durationSec + " seconds i " + durationMili + " milliseconds");
                                } catch (FileNotFoundException e) {
                                    throw new RuntimeException(e);
                                }
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
                        
                        [Hardware manu]
                        Choose the option
                        1. Add drive
                        2. Remove drive
                        3. Change drive
                        4. Change cpu
                        5. List hardware
                        6. Set high monitor resolution
                        7. Set low monitor resolution
                        8. Change headphone's volume
                        9. Show current headphone's volume
                        back <- to go back
                        end <- to exit
                        """);
                        userChoice = MenuOption.chosenAction(scanner.nextLine(), MenuIndicator.HARDWARE_MENU);

                        switch (userChoice){
                            case ADD_DRIVE ->{
                                Drive newDrive = DriveFactory.createDrive(scanner, fileService, cpu);
                                computer.setActiveDrive(newDrive);
                            }
                            case REMOVE_DRIVE ->{
                                System.out.println("Enter drive name to remove: ");
                                String driveName = scanner.nextLine();
                                computer.removeDrive(driveName);
                            }
                            case CHANGE_DRIVE -> {
                                System.out.println("Enter drive's name");
                                String driveName = scanner.nextLine();
                                computer.changeActiveDrive(driveName);
                            }
                            case CHANGE_CPU ->{
                                CPU newCPU = CPU.createCPU(scanner);
                                computer.changeCPU(newCPU);
                            }
                            case LIST_HARDWARE ->{
                                computer.listComponents();
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

    }
}