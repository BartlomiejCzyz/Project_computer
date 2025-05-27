package com.example.__projekt_komputer.computer.hardware.components.drive;
import com.example.__projekt_komputer.computer.software.file.shared.Capacity;
import com.example.__projekt_komputer.computer.hardware.components.ComponentType;
import com.example.__projekt_komputer.computer.software.file.shared.File;
import com.example.__projekt_komputer.computer.software.file.shared.FileNotFoundException;
import com.example.__projekt_komputer.computer.software.file.shared.FileService;

import java.util.ArrayList;
import java.util.List;

public class HDDDrive extends AbstractDrive {



    public HDDDrive(String name, Capacity storageCapacity, FileService fileService) {super(name, storageCapacity, fileService);}

    @Override
    public List<File> findFileByContent(String textFragment) throws FileNotFoundException {
        List<File> allFiles = getAllFiles();
        List<File> matchingFiles = new ArrayList<>();

        for (File file : allFiles) {
            String content = file.getContent();
            int contentLength = content.length();
            int fragmentLength = textFragment.length();

            for (int i = 0; i <= contentLength - fragmentLength; i++) {
                int j;
                for (j = 0; j < fragmentLength; j++) {
                    if (content.charAt(i + j) != textFragment.charAt(j)) {
                        break;
                    }
                }
                if (j == fragmentLength) {
                    matchingFiles.add(file);
                    break; // wystarczy jedno dopasowanie
                }
            }
        }

        if (matchingFiles.isEmpty()) {
            throw new FileNotFoundException("Nie znaleziono pliku");
        }

        return matchingFiles;
    }


    @Override
    public ComponentType getType() {
        return ComponentType.DRIVE;
    }
}
