package com.example.__projekt_komputer.computer.software.file.shared;

public abstract class AbstractFile extends File {
    protected final String name;
    protected final long size;

    public AbstractFile(String name, long size) {
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
