package com.example.__projekt_komputer.casing.hardware.components.drive;

import com.example.__projekt_komputer.casing.software.file.shared.AppFile;
import com.example.__projekt_komputer.casing.software.file.shared.FileRepository;
import com.example.__projekt_komputer.casing.software.file.shared.FileService;

import java.util.ArrayList;
import java.util.List;

public class InMemoryFileService extends FileService {

    public InMemoryFileService() {
        super(null);
    }

    private List<AppFile> files = new ArrayList<>();


    public InMemoryFileService(FileRepository fileRepository) {
        super(fileRepository);
    }
    @Override
    public void addFile(AppFile appFile){
        files.add(appFile);
    }

    @Override
    public List<AppFile> listFiles(){
        return files;
    }
}
