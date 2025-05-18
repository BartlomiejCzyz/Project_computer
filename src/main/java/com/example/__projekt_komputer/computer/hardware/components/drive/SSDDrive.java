package com.example.__projekt_komputer.computer.hardware.components.drive;

import com.example.__projekt_komputer.computer.software.file.shared.Capacity;
import com.example.__projekt_komputer.computer.hardware.components.ComponentType;
import com.example.__projekt_komputer.computer.software.file.shared.File;
import com.example.__projekt_komputer.computer.software.file.shared.FileNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class SSDDrive implements Drive {
    private final Capacity capacity;
    String name;
    Map<String, File> files =new HashMap<>();

    public SSDDrive(String name, Capacity capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    @Override
    public void addFile(File file) {
        // TODO SSD Drive przechowuje pliki w HashMap a nie w List, albo to zmieniamy albo logika dodawania plików musi być oddzielna
        files.put(file.getName(), file);
    }

    @Override
    public void removeFile(File file) {
        files.remove(file.getName());
    }

    @Override
    public void listFiles() {
        for (File file : files.values()) {
            System.out.println(file.getName());
        }
    }

    @Override
    public File findFile(String fileName) throws FileNotFoundException {
        if (files.containsKey(fileName)) {
            return files.get(fileName);
        } else throw new FileNotFoundException(fileName + " not found");
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
