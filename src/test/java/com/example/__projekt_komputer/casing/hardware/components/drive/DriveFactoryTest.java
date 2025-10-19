package com.example.__projekt_komputer.casing.hardware.components.drive;

import com.example.__projekt_komputer.casing.hardware.components.CPU;
import com.example.__projekt_komputer.casing.software.file.shared.Capacity;
import com.example.__projekt_komputer.casing.software.file.shared.FileService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DriveFactoryTest {
    private FileService fileService;
    private CPU cpu;

    @BeforeEach
    void setUp(){
        fileService = mock(FileService.class);
        cpu = CPU.of("intel", 4);
    }

    @Test
    public void shouldCreateHDDDrive(){
        Drive testDrive = DriveFactory.createDrive("hdd", "driveName", Capacity.GB64, fileService, cpu);
        assertInstanceOf(HDDDrive.class, testDrive);
    }

    @Test
    public void shouldCreateSSDDrive(){
        Drive testDrive = DriveFactory.createDrive("ssd", "driveName", Capacity.GB64, fileService, cpu);
        assertInstanceOf(SSDDrive.class, testDrive);
    }
    @Test
    public void shouldCreateM2Drive(){
        Drive testDrive = DriveFactory.createDrive("m2", "driveName", Capacity.GB64, fileService, cpu);
        assertInstanceOf(M2Drive.class, testDrive);
    }

    @Test
    public void shouldCreateM4Drive(){
        Drive testDrive = DriveFactory.createDrive("m4", "driveName", Capacity.GB64, fileService, cpu);
        assertInstanceOf(M4Drive.class, testDrive);
    }
    @Test
    public void shouldThrowOnIncorrectDriveType(){
        assertThrows(IllegalArgumentException.class,
                () -> DriveFactory.createDrive("incorrect", "driveName", Capacity.GB64, fileService, cpu));
    }
}
