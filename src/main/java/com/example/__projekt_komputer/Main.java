package com.example.__projekt_komputer;

import com.example.__projekt_komputer.casing.exceptions.TextFragmentIsNullException;
import com.example.__projekt_komputer.casing.hardware.components.CPU;
import com.example.__projekt_komputer.casing.hardware.components.CPUInputReader;
import com.example.__projekt_komputer.casing.hardware.components.Monitor;
import com.example.__projekt_komputer.casing.hardware.components.drive.Drive;
import com.example.__projekt_komputer.casing.hardware.components.drive.DriveFactory;
import com.example.__projekt_komputer.casing.hardware.computer.Computer;
import com.example.__projekt_komputer.casing.hardware.computer.MenuIndicator;
import com.example.__projekt_komputer.casing.hardware.computer.MenuOption;
import com.example.__projekt_komputer.casing.software.file.shared.AppFile;
import com.example.__projekt_komputer.casing.exceptions.AppFileNotFoundException;
import com.example.__projekt_komputer.casing.software.file.shared.FileService;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Scanner;

public class Main {
    private final FileService fileService;

    public Main(FileService fileService) {
        this.fileService = fileService;
    }
    public void run() throws AppFileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        CPUInputReader cpuInputReader = new CPUInputReader(scanner);

        Monitor monitor = new Monitor("Dell");

        System.out.println("Computer creator: ");

        CPU cpu = null;
        while (cpu==null){
            try {
                cpu = cpuInputReader.readCPU();
                break;
            }catch (RuntimeException e){
                System.out.println("Failed to create CPU: " + e.getMessage());
                System.out.println("Try again");
            }

        }

        Drive drive = null;
        while (drive==null){
            try {
                drive = DriveFactory.createDriveFromUserInput(scanner, fileService, cpu);
                break;
            }catch (IllegalArgumentException e){
                System.out.println("Failed to create Drive: " + e.getMessage());
                System.out.println("Try again");
            }

        }



        Computer computer = Computer.getInstance(monitor, drive, cpu);
        computer.setActiveDrive(drive);

        MenuOption userChoice;


        do {
            System.out.println("""
                    Choose the submenu
                    1. USB devices
                    2. Files
                    3. Hardware
                    end <- to exit
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
                                try {
                                    System.out.println("[File creator]");
                                    System.out.println("Enter file name: ");
                                    String fileName = scanner.nextLine();
                                    System.out.println("Enter file content: ");
                                    String fileContent = scanner.nextLine();

                                    AppFile appFile = new AppFile(fileName, fileContent, "text", 0);
                                    computer.addFile(appFile);
                                } catch (IllegalStateException e){
                                    System.out.println(e.getMessage());
                                }
                            }
                            case REMOVE_FILE ->{
                                System.out.println("Enter file name to remove: ");
                                String fileName = scanner.nextLine();
                                boolean removed = false;

                                try {
                                    removed = computer.removeFile(fileName);
                                }catch (IllegalStateException e){
                                    System.out.println(e.getMessage());
                                }

                                if (removed){
                                    System.out.println("File deleted successfully");
                                }else {
                                    System.out.println("No file found");
                                }
                            }
                            case FIND_FILE_BY_NAME ->{
                                System.out.println("Enter file name: ");
                                String fileName = scanner.nextLine();
                                try {
                                    AppFile appFileByName = drive.findFileByName(fileName);
                                    System.out.println("Name: " + appFileByName.getName() + ", type: " + appFileByName.getType() + ", size: " + appFileByName.getSize() + ", content: \n" + appFileByName.getContent());
                                }catch (AppFileNotFoundException e){
                                    System.out.println(e.getMessage());
                                }
                            }
                            case FIND_FILE_BY_CONTENT -> {
                                try {
                                    Instant start = Instant.now();
                                    System.out.println("Enter fragment of text you are looking for: ");
                                    String textFragment = scanner.nextLine();
                                    List<AppFile> appFileFound = computer.getActiveDrive().findFileByContent(textFragment);
                                    Instant end = Instant.now();
                                    long duration = Duration.between(start, end).toMillis();
                                    long durationSec = duration / 1000;
                                    long durationMili = duration % 1000;

                                    for (AppFile appFile : appFileFound){
                                        System.out.println(appFile.getName());
                                    }
                                    System.out.println("Operation took: " + durationSec + " seconds i " + durationMili + " milliseconds");
                                } catch (AppFileNotFoundException | TextFragmentIsNullException e) {
                                    System.out.println(e.getMessage());
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
                                try {
                                    Drive newDrive = DriveFactory.createDriveFromUserInput(scanner, fileService, cpu);
                                    computer.setActiveDrive(newDrive);
                                }catch (IllegalArgumentException e){
                                    System.out.println("Failed to create drive: " + e.getMessage());
                                }
                            }
                            case REMOVE_DRIVE ->{
                                System.out.println("Enter drive name to remove: ");
                                String driveName = scanner.nextLine();
                                boolean removed = computer.removeDrive(driveName);
                                if (removed){
                                    System.out.println("Successfully removed Drive: " + driveName);
                                }else {
                                    System.out.println("No drive of given name: " + driveName);
                                }
                            }
                            case CHANGE_DRIVE -> {
                                System.out.println("Enter drive's name");
                                String driveName = scanner.nextLine();
                                try {
                                    computer.changeActiveDrive(driveName);
                                }catch (IllegalStateException e){
                                    System.out.println(e.getMessage());
                                }

                            }
                            case CHANGE_CPU ->{
                                try {
                                    CPU newCPU = cpuInputReader.readCPU();
                                    computer.changeCPU(newCPU);
                                }catch (RuntimeException e) {
                                    System.out.println("Failed to create CPU: " + e.getMessage());
                                }

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