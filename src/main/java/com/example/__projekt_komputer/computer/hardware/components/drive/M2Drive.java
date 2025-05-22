package com.example.__projekt_komputer.computer.hardware.components.drive;

import com.example.__projekt_komputer.computer.hardware.components.ComponentType;
import com.example.__projekt_komputer.computer.software.file.shared.Capacity;
import com.example.__projekt_komputer.computer.software.file.shared.File;
import com.example.__projekt_komputer.computer.software.file.shared.FileNotFoundException;

public class M2Drive extends AbstractDrive {

    public M2Drive(String name, Capacity storageCapacity) {super(name, storageCapacity);}


    @Override
    public File findFileByContent(String textFragment) throws FileNotFoundException {
        return null;
    }


    @Override
    public ComponentType getType() {
        return null;
    }
}
