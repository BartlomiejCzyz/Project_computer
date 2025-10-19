package com.example.__projekt_komputer.casing.software.file.shared;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FileService {
    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public void addFile(AppFile appFile) {
        fileRepository.save(appFile);
    }

    public boolean removeFile(String name) {
        return fileRepository.findByName(name)
                .map(file -> {  //map działa jak "if" z racji że file jest Optional sprawdza czy coś zawiera czy nie
                    fileRepository.delete(file);
                    return true;
                })
                .orElse(false);
    }

    public Optional<AppFile> findFile(String name) {
        return fileRepository.findByName(name);
    }

    public List<AppFile> listFiles() {
        List<AppFile> appFileList = fileRepository.findAll();
        if (appFileList.isEmpty()){
            System.out.println("No files saved");
        }
        return appFileList;
    }
    public List<AppFile> findFilesByContent(String fragment) {
        return fileRepository.findByContentContaining(fragment);
    }
}
