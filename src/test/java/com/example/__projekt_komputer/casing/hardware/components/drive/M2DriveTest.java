package com.example.__projekt_komputer.casing.hardware.components.drive;

import com.example.__projekt_komputer.casing.exceptions.AppFileNotFoundException;
import com.example.__projekt_komputer.casing.exceptions.TextFragmentIsNullException;
import com.example.__projekt_komputer.casing.hardware.components.CPU;
import com.example.__projekt_komputer.casing.software.file.shared.AppFile;
import com.example.__projekt_komputer.casing.software.file.shared.Capacity;
import com.example.__projekt_komputer.casing.software.file.shared.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class M2DriveTest {

    private FileService fileService;
    private M2Drive m2Drive;

    @BeforeEach
    void setUp() {
        fileService = mock(FileService.class);
        CPU cpu = CPU.of("Xenon", 4); // 4 wątki
        m2Drive = new M2Drive("M2Test", Capacity.GB64, fileService, cpu);
    }

    @Test
    void shouldFindFragmentInSingleFile() throws AppFileNotFoundException, TextFragmentIsNullException {
        AppFile f1 = new AppFile("file1", "Hello world", "txt", 0);
        when(fileService.listFiles()).thenReturn(List.of(f1));

        List<AppFile> result = m2Drive.findFileByContent("world");

        assertEquals(1, result.size());
        assertEquals("file1", result.get(0).getName());
    }

    @Test
    void shouldFindFragmentInMultipleFiles() throws AppFileNotFoundException, TextFragmentIsNullException {
        AppFile f1 = new AppFile("file1", "abc def", "txt", 0);
        AppFile f2 = new AppFile("file2", "xyz def", "txt", 0);
        AppFile f3 = new AppFile("file3", "nothing here", "txt", 0);
        when(fileService.listFiles()).thenReturn(List.of(f1, f2, f3));

        List<AppFile> result = m2Drive.findFileByContent("def");

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(f -> f.getName().equals("file1")));
        assertTrue(result.stream().anyMatch(f -> f.getName().equals("file2")));
    }

    @Test
    void shouldFindFragmentAtBeginningMiddleAndEnd() throws AppFileNotFoundException, TextFragmentIsNullException {
        AppFile f1 = new AppFile("f1", "abc...", "txt", 0);
        AppFile f2 = new AppFile("f2", "...abc...", "txt", 0);
        AppFile f3 = new AppFile("f3", "...abc", "txt", 0);
        when(fileService.listFiles()).thenReturn(List.of(f1, f2, f3));

        List<AppFile> result = m2Drive.findFileByContent("abc");

        assertEquals(3, result.size());
    }
    @Test
    void shouldThrowWhenNoFileMatches() {
        AppFile f1 = new AppFile("file1", "12345", "txt", 0);
        when(fileService.listFiles()).thenReturn(List.of(f1));

        assertThrows(AppFileNotFoundException.class,
                () -> m2Drive.findFileByContent("xyz"));
    }

    @Test
    void shouldThrowWhenTextFragmentIsNull() {
        assertThrows(TextFragmentIsNullException.class,
                () -> m2Drive.findFileByContent(null));
    }

    @Test
    void shouldThrowWhenTextFragmentIsEmpty() {
        assertThrows(TextFragmentIsNullException.class,
                () -> m2Drive.findFileByContent(""));
    }

    @Test
    void shouldSkipFilesWithNullContent() throws AppFileNotFoundException, TextFragmentIsNullException {
        AppFile f1 = new AppFile("f1", null, "txt", 0);
        AppFile f2 = new AppFile("f2", "abc def", "txt", 0);
        when(fileService.listFiles()).thenReturn(List.of(f1, f2));

        List<AppFile> result = m2Drive.findFileByContent("def");

        assertEquals(1, result.size());
        assertEquals("f2", result.get(0).getName());
    }

    @Test
    void shouldWorkWhenMoreFilesThanThreads() throws AppFileNotFoundException, TextFragmentIsNullException {
        CPU cpu = CPU.of("Xenon", 2); // 2 wątki
        m2Drive = new M2Drive("M2Test", Capacity.GB64, fileService, cpu);

        AppFile f1 = new AppFile("f1", "abc", "txt", 0);
        AppFile f2 = new AppFile("f2", "xyz abc", "txt", 0);
        AppFile f3 = new AppFile("f3", "none", "txt", 0);
        when(fileService.listFiles()).thenReturn(List.of(f1, f2, f3));

        List<AppFile> result = m2Drive.findFileByContent("abc");

        assertEquals(2, result.size());
    }

    @Test
    void shouldWorkWhenMoreThreadsThanFiles() throws AppFileNotFoundException, TextFragmentIsNullException {
        CPU cpu = CPU.of("Xenon", 8); // 8 wątków, tylko 2 pliki
        m2Drive = new M2Drive("M2Test", Capacity.GB64, fileService, cpu);

        AppFile f1 = new AppFile("f1", "lorem ipsum", "txt", 0);
        AppFile f2 = new AppFile("f2", "ipsum lorem", "txt", 0);
        when(fileService.listFiles()).thenReturn(List.of(f1, f2));

        List<AppFile> result = m2Drive.findFileByContent("ipsum");

        assertEquals(2, result.size());
    }
    @Test
    void performanceTestFindFileByContent() throws Exception {
        CPU newCpu = CPU.of("Xenon", 16);
        FileService fileService = new InMemoryFileService();
        String bigContent = "x".repeat(80_000) + "needle";
        for (int i = 0; i < 100_000; i++) {
            fileService.addFile(new AppFile("f" + i, bigContent + "content " + i, "txt", 1));
        }

        M2Drive ssd = new M2Drive("m2", Capacity.GB64, fileService, newCpu);



        long start = System.currentTimeMillis();
        List<AppFile> result = ssd.findFileByContent("content 99999");
        long duration = System.currentTimeMillis() - start;

        assertFalse(result.isEmpty());
    }

}
