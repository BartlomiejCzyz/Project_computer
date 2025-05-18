package com.example.__projekt_komputer.computer.software.file.image;

public class GIFImageFile extends AbstractImageFIle {

    public GIFImageFile(String name, int size) {
        super(name, size);
    }
    public void showAnimation(){
        System.out.println("Showing funny GIF");
    }
}
