package com.example.__projekt_komputer.casing.hardware.components.drive;
import com.example.__projekt_komputer.casing.exceptions.TextFragmentIsNullException;
import com.example.__projekt_komputer.casing.hardware.components.Components;
import com.example.__projekt_komputer.casing.software.file.shared.Capacity;
import com.example.__projekt_komputer.casing.software.file.shared.AppFile;
import com.example.__projekt_komputer.casing.exceptions.AppFileNotFoundException;

import java.util.List;

public interface Drive extends Components {
    void addFile(AppFile aapFile);
    boolean removeFile(String fileName);
    List<AppFile> getAllFiles();
    AppFile findFileByName(String fileName) throws AppFileNotFoundException;
    List<AppFile> findFileByContent(String textFragment) throws AppFileNotFoundException, TextFragmentIsNullException;
    Capacity getCapacity();
}
