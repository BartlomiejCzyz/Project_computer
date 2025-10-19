package com.example.__projekt_komputer.casing.software.file.shared;

public abstract class AbstractAppFile extends AppFile {
    protected final String name;
    protected final long size;

    public AbstractAppFile(String name, long size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getSize() {
        return size;
    }
}
