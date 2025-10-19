package com.example.__projekt_komputer.casing.hardware.components.drive;

import com.example.__projekt_komputer.casing.exceptions.AppFileNotFoundException;
import com.example.__projekt_komputer.casing.software.file.shared.AppFile;
import com.example.__projekt_komputer.casing.software.file.shared.Capacity;
import com.example.__projekt_komputer.casing.software.file.shared.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class M4DriveTest {

    private FileService fileService;
    private M4Drive m4Drive;

    @BeforeEach
    void setUp() {
        fileService = mock(FileService.class);
        m4Drive = new M4Drive("M4Test", Capacity.GB64, fileService);
    }

    @Test
    void shouldReturnMatchingFiles() throws AppFileNotFoundException {
        AppFile f1 = new AppFile("f1", "abc def", "txt", 0);
        AppFile f2 = new AppFile("f2", "xyz def", "txt", 0);
        when(fileService.findFilesByContent("def")).thenReturn(List.of(f1, f2));

        List<AppFile> result = m4Drive.findFileByContent("def");

        assertEquals(2, result.size());
        assertTrue(result.contains(f1));
        assertTrue(result.contains(f2));
        verify(fileService, times(1)).findFilesByContent("def");
    }

    @Test
    void shouldReturnEmptyListWhenNoMatches() throws AppFileNotFoundException {
        when(fileService.findFilesByContent("notfound")).thenReturn(List.of());

        List<AppFile> result = m4Drive.findFileByContent("notfound");

        assertTrue(result.isEmpty());
        verify(fileService, times(1)).findFilesByContent("notfound");
    }

    @Test
    void shouldPropagateExceptionFromFileService() {
        when(fileService.findFilesByContent("boom"))
                .thenThrow(new AppFileNotFoundException("No match"));

        assertThrows(AppFileNotFoundException.class,
                () -> m4Drive.findFileByContent("boom"));

        verify(fileService, times(1)).findFilesByContent("boom");
    }
}
