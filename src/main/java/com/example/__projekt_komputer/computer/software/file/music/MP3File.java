package com.example.__projekt_komputer.computer.software.file.music;

public class MP3File extends AbstractMusicFIle{
    private final int quality;
    public MP3File(String name, int size, String bandName, String title, int quality) {
        super(name, size, bandName, title);
        this.quality = quality;
    }

    public int getQuality() {
        return quality;
    }

    @Override
    public void play() {
        System.out.println("Playing MP3 file");
    }

}
