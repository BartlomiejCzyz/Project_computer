package com.example.__projekt_komputer.casing.hardware.components.drive;

import com.example.__projekt_komputer.casing.exceptions.AppFileNotFoundException;
import com.example.__projekt_komputer.casing.exceptions.TextFragmentIsNullException;
import com.example.__projekt_komputer.casing.hardware.components.CPU;
import com.example.__projekt_komputer.casing.software.file.shared.AppFile;
import com.example.__projekt_komputer.casing.software.file.shared.Capacity;
import com.example.__projekt_komputer.casing.software.file.shared.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SSDDriveTest {

    private FileService fileService;
    private CPU cpu;
    private SSDDrive ssdDrive;

    @BeforeEach
    void setUp() {
        fileService = mock(FileService.class);
        cpu = CPU.of("TestCPU", 16); // CPU z 4 wątkami
        ssdDrive = new SSDDrive("SSD1", Capacity.GB64, fileService, cpu);
    }
    @Test
    void shouldFindFileContainingFragment_singleThread() throws Exception {
        // CPU z jednym wątkiem
        SSDDrive singleThreadDrive = new SSDDrive("SSD1", Capacity.GB64, fileService, CPU.of("CPU1", 1));

        AppFile file1 = new AppFile("file1", "this is some content", "txt", 0);
        AppFile file2 = new AppFile("file2", "completely different text", "txt", 0);

        when(fileService.listFiles()).thenReturn(Arrays.asList(file1, file2));

        List<AppFile> result = singleThreadDrive.findFileByContent("some");

        assertEquals(1, result.size());
        assertEquals("file1", result.get(0).getName());
    }

    @Test
    void shouldFindMultipleFilesWithFragment_multiThread() throws Exception {
        AppFile file1 = new AppFile("file1", "alpha beta gamma", "txt", 0);
        AppFile file2 = new AppFile("file2", "beta inside text", "txt", 0);
        AppFile file3 = new AppFile("file3", "completely different", "txt", 0);

        when(fileService.listFiles()).thenReturn(Arrays.asList(file1, file2, file3));

        List<AppFile> result = ssdDrive.findFileByContent("beta");

        assertEquals(2, result.size());
        assertTrue(result.contains(file1));
        assertTrue(result.contains(file2));
    }

    @Test
    void shouldThrowWhenFragmentNotFound() {
        AppFile file1 = new AppFile("file1", "no match here", "txt", 0);
        when(fileService.listFiles()).thenReturn(Collections.singletonList(file1));

        assertThrows(AppFileNotFoundException.class,
                () -> ssdDrive.findFileByContent("missing"));
    }

    @Test
    void shouldThrowWhenFragmentIsNullOrEmpty() {
        when(fileService.listFiles()).thenReturn(Collections.emptyList());

        assertThrows(TextFragmentIsNullException.class,
                () -> ssdDrive.findFileByContent(null));

        assertThrows(TextFragmentIsNullException.class,
                () -> ssdDrive.findFileByContent(""));
    }

    @Test
    void shouldHandleCpuWithMoreThreadsThanFiles() throws Exception {
        // CPU z większą liczbą wątków niż plików
        SSDDrive manyThreadsDrive = new SSDDrive("SSD2", Capacity.GB64, fileService, CPU.of("CPU2", 16));

        AppFile file1 = new AppFile("file1", "parallel search here", "txt", 0);

        when(fileService.listFiles()).thenReturn(Collections.singletonList(file1));

        List<AppFile> result = manyThreadsDrive.findFileByContent("search");

        assertEquals(1, result.size());
        assertEquals("file1", result.get(0).getName());
    }

    @Test
    void shouldIgnoreFilesWithNullContent() throws Exception{
        AppFile file1 = new AppFile("file1", null, "txt", 0);
        AppFile file2 = new AppFile("file2", "valid text here", "txt", 0);

        when(fileService.listFiles()).thenReturn(Arrays.asList(file1, file2));

        List<AppFile> result = ssdDrive.findFileByContent("valid");

        assertEquals(1, result.size());
        assertEquals("file2", result.get(0).getName());
    }

    @Test
    void performanceTestFindFileByContent() throws Exception {
        FileService fileService = new InMemoryFileService();
        CPU newCpu = CPU.of("Xenon", 16);
        String bigContent = "x".repeat(80_000) + "needle";
        for (int i = 0; i < 100_000; i++) {
            fileService.addFile(new AppFile( "f" + i, bigContent +"content " + i, "txt", 1));
        }

        SSDDrive ssd = new SSDDrive("ssd", Capacity.GB64, fileService, newCpu);



        long start = System.currentTimeMillis();
        List<AppFile> result = ssd.findFileByContent("content 99999");
        long duration = System.currentTimeMillis() - start;

        assertFalse(result.isEmpty());
    }

}
