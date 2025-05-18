package com.example.__projekt_komputer.computer.software.file.image;

import com.example.__projekt_komputer.computer.software.file.shared.AbstractFile;
import com.example.__projekt_komputer.computer.software.file.shared.FileType;

public class AbstractImageFIle extends AbstractFile {

    public AbstractImageFIle(String name, int size) {
        super(name, size);
    }
    @Override
    public FileType getType() {
        return FileType.IMAGE;
    }
}
