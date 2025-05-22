package com.example.__projekt_komputer.computer.hardware.components.drive;
import com.example.__projekt_komputer.computer.hardware.components.Components;
import com.example.__projekt_komputer.computer.software.file.shared.File;
import com.example.__projekt_komputer.computer.software.file.shared.FileNotFoundException;

public interface Drive extends Components {
    void addFile(File file);
    void removeFile(File file);
    void listFiles();
    File findFileByName(String fileName) throws FileNotFoundException;
    File findFileByContent(String textFragment) throws FileNotFoundException;
}
