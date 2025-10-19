package com.example.__projekt_komputer;

import com.example.__projekt_komputer.casing.exceptions.AppFileNotFoundException;
import com.example.__projekt_komputer.casing.software.file.shared.FileService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {

    private final FileService fileService;

    public AppRunner(FileService fileService) {
        this.fileService = fileService;
    }
    @Override
    public void run(String... args) throws AppFileNotFoundException {
        new Main(fileService).run();
    }
}