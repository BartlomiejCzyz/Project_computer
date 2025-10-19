package com.example.__projekt_komputer.casing.hardware.components;

public class Headphones implements Components {

    private String name;
    private int volume;

    public void setName(String name) {
        this.name = name;
        this.volume = 50;
    }
    public void changeVolume(int volume){
        if (volume>=0 && volume<=100){
            this.volume = volume;
        }else System.out.println("Wrong volume");
    }

    public int getVolume() {
        return volume;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ComponentType getType() {
        return null;
    }
}
