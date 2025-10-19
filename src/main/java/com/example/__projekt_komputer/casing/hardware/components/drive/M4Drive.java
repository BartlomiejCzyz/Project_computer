package com.example.__projekt_komputer.casing.hardware.components.drive;

import com.example.__projekt_komputer.casing.hardware.components.ComponentType;
import com.example.__projekt_komputer.casing.software.file.shared.Capacity;
import com.example.__projekt_komputer.casing.software.file.shared.AppFile;
import com.example.__projekt_komputer.casing.exceptions.AppFileNotFoundException;
import com.example.__projekt_komputer.casing.software.file.shared.FileService;

import java.util.List;

public class M4Drive extends AbstractDrive{
    public M4Drive(String name, Capacity storageCapacity, FileService fileService) {super(name, storageCapacity, fileService);}

    @Override
    public List<AppFile> findFileByContent(String textFragment) throws AppFileNotFoundException {
        return fileService.findFilesByContent(textFragment);
    }

    @Override
    public ComponentType getType() {
        return ComponentType.M4_DRIVE;
    }
}
