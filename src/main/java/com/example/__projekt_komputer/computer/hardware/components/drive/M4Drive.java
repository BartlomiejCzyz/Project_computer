package com.example.__projekt_komputer.computer.hardware.components.drive;

import com.example.__projekt_komputer.computer.hardware.components.ComponentType;
import com.example.__projekt_komputer.computer.software.file.shared.Capacity;
import com.example.__projekt_komputer.computer.software.file.shared.File;
import com.example.__projekt_komputer.computer.software.file.shared.FileNotFoundException;
import com.example.__projekt_komputer.computer.software.file.shared.FileService;

import java.util.List;

public class M4Drive extends AbstractDrive{
    public M4Drive(String name, Capacity storageCapacity, FileService fileService) {super(name, storageCapacity, fileService);}

    @Override
    public List<File> findFileByContent(String textFragment) throws FileNotFoundException {
        return fileService.findFilesByContent(textFragment);
    }

    @Override
    public ComponentType getType() {
        return ComponentType.M4_DRIVE;
    }
}
