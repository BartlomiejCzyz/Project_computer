package com.example.__projekt_komputer.casing.hardware.components.drive;

import com.example.__projekt_komputer.casing.exceptions.AppFileNotFoundException;
import com.example.__projekt_komputer.casing.hardware.components.ComponentType;
import com.example.__projekt_komputer.casing.software.file.shared.AppFile;
import com.example.__projekt_komputer.casing.software.file.shared.Capacity;
import com.example.__projekt_komputer.casing.software.file.shared.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AbstractDriveTest {
    private FileService fileService;
    private TestDrive drive;

    @BeforeEach
    void setUp() {
        fileService = mock(FileService.class);
        drive = new TestDrive(fileService);
    }

    // tworzenie klasy pomocniczej do testów
    static class TestDrive extends AbstractDrive {
        public TestDrive(FileService fileService) {
            super("TestDrive", Capacity.GB64, fileService);
        }

        @Override
        public List<AppFile> findFileByContent(String fragment) {
            return fileService.findFilesByContent(fragment);
        }

        @Override
        public ComponentType getType() {
            return null;
        }
    }

    @Test
    void shouldAddFileThroughFileService() {
        AppFile file = new AppFile("test", "content", "txt", 0);

        drive.addFile(file);

        verify(fileService).addFile(file);
    }

    @Test
    void shouldRemoveFileThroughFileService() {
        when(fileService.removeFile("test")).thenReturn(true);

        boolean result = drive.removeFile("test");

        assertTrue(result);
        verify(fileService).removeFile("test");
    }

    @Test
    void shouldReturnAllFilesFromFileService() {
        List<AppFile> mockFiles = List.of(new AppFile("f1", "c1", "txt", 1));
        when(fileService.listFiles()).thenReturn(mockFiles);

        List<AppFile> result = drive.getAllFiles();

        assertEquals(1, result.size());
        assertEquals("f1", result.get(0).getName());
        verify(fileService).listFiles();
    }

    @Test
    void shouldFindFileByNameIfExists() throws AppFileNotFoundException {
        AppFile file = new AppFile("exists", "content", "txt", 5);
        when(fileService.findFile("exists")).thenReturn(Optional.of(file));

        AppFile result = drive.findFileByName("exists");

        assertEquals("exists", result.getName());
        verify(fileService).findFile("exists");
    }

    @Test
    void shouldThrowIfFileNotFound() {
        when(fileService.findFile("missing")).thenReturn(Optional.empty());

        assertThrows(AppFileNotFoundException.class, () -> drive.findFileByName("missing"));
    }
}
