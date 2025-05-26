package com.example.__projekt_komputer.computer.hardware.components.drive;

import com.example.__projekt_komputer.computer.software.file.shared.Capacity;
import com.example.__projekt_komputer.computer.hardware.components.ComponentType;
import com.example.__projekt_komputer.computer.software.file.shared.File;
import com.example.__projekt_komputer.computer.software.file.shared.FileNotFoundException;
import com.example.__projekt_komputer.computer.software.file.shared.FileService;

public class SSDDrive extends AbstractDrive {

    public SSDDrive(String name, Capacity storageCapacity, FileService fileService) {super(name, storageCapacity, fileService);}



    @Override
    public File findFileByContent(String textFragment) throws FileNotFoundException {
        return null;
    }

    @Override
    public ComponentType getType() {
        return ComponentType.DRIVE;
    }
}
