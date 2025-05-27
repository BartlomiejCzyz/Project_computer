package com.example.__projekt_komputer.computer.hardware.components.drive;
import com.example.__projekt_komputer.computer.hardware.components.Components;
import com.example.__projekt_komputer.computer.software.file.shared.File;
import com.example.__projekt_komputer.computer.software.file.shared.FileNotFoundException;

import java.util.List;

public interface Drive extends Components {
    void addFile(File file);
    void removeFile(String fileName);
    void listFiles();
    File findFileByName(String fileName) throws FileNotFoundException;
    List<File> findFileByContent(String textFragment) throws FileNotFoundException;
}
