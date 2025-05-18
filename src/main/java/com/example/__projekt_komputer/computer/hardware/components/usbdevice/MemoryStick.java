package com.example.__projekt_komputer.computer.hardware.components.usbdevice;

import com.example.__projekt_komputer.computer.software.file.shared.Capacity;
import com.example.__projekt_komputer.computer.software.file.shared.File;
import com.example.__projekt_komputer.computer.software.file.shared.FileNotFoundException;
import com.example.__projekt_komputer.computer.software.file.shared.FileStorage;

public class MemoryStick extends USBDevice {

    private final FileStorage fileStorage;

    public MemoryStick(String name, Capacity storageCapacity) {
        super(name);
        fileStorage = new FileStorage(storageCapacity);
    }

    // TODO czy dodawać sprawdzanie czy pendrive jest podłączony przed każdą z operacji na plikach?
    public void addFile(File file) {
        fileStorage.addFile(file);
    }

    public void removeFile(File file) {
        fileStorage.removeFile(file);
    }

    public File findFile(String fileName) {
        try {
            return fileStorage.findFile(fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public long getStorageCapacity() {
        return fileStorage.getStorageCapacity();
    }
}
