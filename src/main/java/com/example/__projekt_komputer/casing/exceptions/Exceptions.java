package com.example.__projekt_komputer.casing.exceptions;

public class Exceptions {

    private Exceptions() {}

    public static IllegalStateException NoActiveDrive(){
        return new IllegalStateException("No currently active drive, please set active drive or add new one");
    }
    public static IllegalStateException NoDriveOfGivenName(){
        return new IllegalStateException("No drive of given name");
    }
    public static AppFileNotFoundException AppFileWithGivenFragmentNotFound(){
        return new AppFileNotFoundException("No file contains given fragment");
    }
    public static AppFileNotFoundException NoAppFileFound(){
        return new AppFileNotFoundException("Currently there are no app files connected to this drive");
    }
    public static AppFileNotFoundException FileNameNotFound(String fileName){
        return new AppFileNotFoundException("No file of given name: " + fileName);
    }
    public static TextFragmentIsNullException TextFragmentIsNull(){
        return new TextFragmentIsNullException("Given text fragment is empty");
    }
}
