package com.example.__projekt_komputer.computer.software.file.image;

public class JPGImageFIle extends AbstractImageFIle {
    private final int compression;

    public JPGImageFIle(String name, int size, int compression) {
        super(name, size);
        this.compression = compression;
    }

    public int getCompression() {
        return compression;
    }
    public void showImage(){
        System.out.println("Showing Image");
    }

}
