package com.example.__projekt_komputer.computer.hardware.components.drive;
import com.example.__projekt_komputer.computer.software.file.shared.Capacity;
import com.example.__projekt_komputer.computer.hardware.components.ComponentType;
import com.example.__projekt_komputer.computer.software.file.shared.File;
import com.example.__projekt_komputer.computer.software.file.shared.FileNotFoundException;
import com.example.__projekt_komputer.computer.software.file.shared.FileStorage;

public class HDDDrive implements Drive {
    private final String name;
    private final FileStorage fileStorage;


    public HDDDrive(String name, Capacity storageCapacity) {
        this.name = name;
        fileStorage = new FileStorage(storageCapacity);
    }

    @Override
    public void addFile(File file) {
        fileStorage.addFile(file);
    }

    @Override
    public void removeFile(File file) {
        fileStorage.removeFile(file);
    }

    @Override
    public void listFiles() {
        fileStorage.listFiles();
    }

    @Override
    public File findFile(String fileName) {
        try {
            return fileStorage.findFile(fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ComponentType getType() {
        return ComponentType.DRIVE;
    }
}
