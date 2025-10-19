package com.example.__projekt_komputer.casing.hardware.components.drive;

import com.example.__projekt_komputer.casing.exceptions.AppFileNotFoundException;
import com.example.__projekt_komputer.casing.exceptions.TextFragmentIsNullException;
import com.example.__projekt_komputer.casing.software.file.shared.AppFile;
import com.example.__projekt_komputer.casing.software.file.shared.Capacity;
import com.example.__projekt_komputer.casing.software.file.shared.FileRepository;
import com.example.__projekt_komputer.casing.software.file.shared.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HDDDriveTest {

    private FileService fileService;
    private HDDDrive hddDrive;

    @BeforeEach
    void setUp() {
        fileService = mock(FileService.class);
        hddDrive = new HDDDrive("HDD1", Capacity.GB64, fileService);
    }

    @Test
    void shouldFindSingleFileContainingFragment() throws AppFileNotFoundException, TextFragmentIsNullException {
        AppFile f1 = new AppFile("file1", "Hello World", "txt", 11);
        AppFile f2 = new AppFile("file2", "Another content", "txt", 15);
        when(fileService.listFiles()).thenReturn(List.of(f1, f2));

        List<AppFile> result = hddDrive.findFileByContent("World");

        assertEquals(1, result.size());
        assertEquals("file1", result.get(0).getName());
    }

    @Test
    void shouldFindMultipleFilesContainingFragment() throws AppFileNotFoundException, TextFragmentIsNullException {
        AppFile f1 = new AppFile("file1", "abc def", "txt", 7);
        AppFile f2 = new AppFile("file2", "xyz def", "txt", 7);
        AppFile f3 = new AppFile("file3", "no match here", "txt", 13);
        when(fileService.listFiles()).thenReturn(List.of(f1, f2, f3));

        List<AppFile> result = hddDrive.findFileByContent("def");

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(f -> "file1".equals(f.getName())));
        assertTrue(result.stream().anyMatch(f -> "file2".equals(f.getName())));
    }

    @Test
    void shouldThrowWhenNoFileContainsFragment() {
        AppFile f1 = new AppFile("file1", "Hello World", "txt", 11);
        when(fileService.listFiles()).thenReturn(List.of(f1));

        assertThrows(AppFileNotFoundException.class, () -> hddDrive.findFileByContent("missing"));
    }

    @Test
    void shouldThrowOnNullOrEmptyFragment() {
        AppFile f1 = new AppFile("file1", "Hello World", "txt", 11);
        when(fileService.listFiles()).thenReturn(List.of(f1));

        assertThrows(TextFragmentIsNullException.class, () -> hddDrive.findFileByContent(null));
        assertThrows(TextFragmentIsNullException.class, () -> hddDrive.findFileByContent(""));
    }

    @Test
    void shouldSkipFilesWithNullContentAndNotFail() throws AppFileNotFoundException, TextFragmentIsNullException {
        AppFile f1 = new AppFile("file1", null, "txt", 0);
        AppFile f2 = new AppFile("file2", "contains", "txt", 8);
        when(fileService.listFiles()).thenReturn(List.of(f1, f2));

        List<AppFile> result = hddDrive.findFileByContent("contains");

        assertEquals(1, result.size());
        assertEquals("file2", result.get(0).getName());
    }
    @Test
    void performanceTestFindFileByContent() throws Exception {
        FileService fileService = new InMemoryFileService();
        String bigContent = "x".repeat(80_000) + "needle";
        for (int i = 0; i < 100_000; i++) {
            fileService.addFile(new AppFile( "f" + i, bigContent +"content " + i, "txt", 1));
        }

        HDDDrive hdd = new HDDDrive("hdd", Capacity.GB64, fileService);



        long start = System.currentTimeMillis();
        List<AppFile> result = hdd.findFileByContent("content 99999");
        long duration = System.currentTimeMillis() - start;

        assertFalse(result.isEmpty());
    }
}

