package com.example.__projekt_komputer.casing.hardware.components.drive;

import com.example.__projekt_komputer.casing.exceptions.AppFileNotFoundException;
import com.example.__projekt_komputer.casing.software.file.shared.Capacity;
import com.example.__projekt_komputer.casing.software.file.shared.AppFile;
import com.example.__projekt_komputer.casing.software.file.shared.FileService;

import java.util.List;
import java.util.Optional;

public abstract class AbstractDrive implements Drive {

    protected final String name;
    protected final Capacity capacity;
    protected final FileService fileService;

    public AbstractDrive(String name, Capacity storageCapacity, FileService fileService) {
        this.name = name;
        this.capacity = storageCapacity;
        this.fileService = fileService;
    }

    @Override
    public Capacity getCapacity(){
        return capacity;
    }

    @Override
    public void addFile(AppFile appFile) {
        fileService.addFile(appFile);
    }

    @Override
    public boolean removeFile(String fileName) {
        return fileService.removeFile(fileName);
    }

    @Override
    public List<AppFile> getAllFiles(){
        return fileService.listFiles();
    }


    @Override
    public AppFile findFileByName(String fileName) throws AppFileNotFoundException {
        Optional<AppFile> file = fileService.findFile(fileName);
        return file.orElseThrow(() -> new AppFileNotFoundException("No file of given name: " + fileName));
    }

    @Override
    public String getName() {
        return name;
    }

}
