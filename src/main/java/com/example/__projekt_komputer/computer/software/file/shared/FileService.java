package com.example.__projekt_komputer.computer.software.file.shared;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public void addFile(File file) {
        fileRepository.save(file);
    }

    public void removeFile(String name) {
        fileRepository.findByName(name)
                .ifPresent(fileRepository::delete);
    }

    public Optional<File> findFile(String name) {
        return fileRepository.findByName(name);
    }

    public List<File> listFiles() {
        return fileRepository.findAll();
    }

}
