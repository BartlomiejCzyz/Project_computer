package com.example.__projekt_komputer.casing.hardware.components.drive;
import com.example.__projekt_komputer.casing.exceptions.Exceptions;
import com.example.__projekt_komputer.casing.exceptions.TextFragmentIsNullException;
import com.example.__projekt_komputer.casing.software.file.shared.Capacity;
import com.example.__projekt_komputer.casing.hardware.components.ComponentType;
import com.example.__projekt_komputer.casing.software.file.shared.AppFile;
import com.example.__projekt_komputer.casing.exceptions.AppFileNotFoundException;
import com.example.__projekt_komputer.casing.software.file.shared.FileService;

import java.util.ArrayList;
import java.util.List;

public class HDDDrive extends AbstractDrive {



    public HDDDrive(String name, Capacity storageCapacity, FileService fileService) {super(name, storageCapacity, fileService);}

    @Override
    public List<AppFile> findFileByContent(String textFragment) throws AppFileNotFoundException, TextFragmentIsNullException {
        if (textFragment == null || textFragment.isEmpty()){
            throw Exceptions.TextFragmentIsNull();
        }
        List<AppFile> allAppFiles = getAllFiles();
        List<AppFile> matchingAppFiles = new ArrayList<>();

        for (AppFile appFile : allAppFiles) {
            String content = appFile.getContent();
            if (content == null || content.isEmpty()){
                continue;
            }
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
                    matchingAppFiles.add(appFile);
                    break; // wystarczy jedno dopasowanie
                }
            }
        }

        if (matchingAppFiles.isEmpty()) {
            throw Exceptions.AppFileWithGivenFragmentNotFound();
        }

        return matchingAppFiles;
    }


    @Override
    public ComponentType getType() {
        return ComponentType.HDD_DRIVE;
    }
}
