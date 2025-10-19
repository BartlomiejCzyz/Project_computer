package com.example.__projekt_komputer.casing.software.file.shared;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FileServiceTest {

    @Mock
    private FileRepository fileRepository; //mock bazy danych

    @InjectMocks
    private FileService fileService; //service z wstrzykniętym mockiem

    @Test
    void shouldSaveFile() {
        AppFile file = new AppFile("test", "content", "txt", 0);

        fileService.addFile(file);

        verify(fileRepository).save(file);
    }

    @Test
    void shouldRemoveAppFIleIfExist(){
        AppFile appFile = new AppFile("name", "content", "txt", 0);

        when(fileRepository.findByName("name")).thenReturn(Optional.of(appFile));

        boolean result = fileService.removeFile("name");

        assertTrue(result, "Powinno zwrócić true jeśli plik istnieje");
        verify(fileRepository).delete(any());
    }
    @Test
    void shouldReturnFalseIfFileDoesNotExist() {
        // given
        when(fileRepository.findByName("missing.txt"))
                .thenReturn(Optional.empty());

        // when
        boolean result = fileService.removeFile("missing.txt");

        // then
        assertFalse(result, "Powinno zwrócić false jeśli plik nie istnieje");
        verify(fileRepository, never()).delete(any());
    }

    @Test
    void shouldFindFileByNameIfExists() {
        AppFile file = new AppFile("doc", "abc", "txt", 0);
        when(fileRepository.findByName("doc.txt"))
                .thenReturn(Optional.of(file));

        Optional<AppFile> result = fileService.findFile("doc.txt");

        assertTrue(result.isPresent());
        assertEquals(file, result.get());
    }

    @Test
    void shouldReturnEmptyWhenFileNotFound() {
        when(fileRepository.findByName("missingFile"))
                .thenReturn(Optional.empty());

        Optional<AppFile> result = fileService.findFile("missingFile");

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldListFiles() {
        AppFile f1 = new AppFile("a", "aaa", "txt", 0);
        AppFile f2 = new AppFile("b", "bbb", "txt", 0);
        when(fileRepository.findAll())
                .thenReturn(List.of(f1, f2));

        List<AppFile> result = fileService.listFiles();

        assertEquals(2, result.size());
        assertTrue(result.contains(f1));
        assertTrue(result.contains(f2));
    }

    @Test
    void shouldReturnEmptyListWhenNoFiles() {
        when(fileRepository.findAll()).thenReturn(List.of());

        List<AppFile> result = fileService.listFiles();

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldFindFilesByContent() {
        AppFile f1 = new AppFile("a", "abc def", "txt", 0);
        AppFile f2 = new AppFile("b", "xyz def", "txt", 0);
        when(fileRepository.findByContentContaining("def"))
                .thenReturn(List.of(f1, f2));

        List<AppFile> result = fileService.findFilesByContent("def");

        assertEquals(2, result.size());
        assertTrue(result.contains(f1));
        assertTrue(result.contains(f2));
    }

    @Test
    void shouldReturnEmptyListWhenNoFilesByContent() {
        when(fileRepository.findByContentContaining("zzz"))
                .thenReturn(List.of());

        List<AppFile> result = fileService.findFilesByContent("zzz");

        assertTrue(result.isEmpty());
    }

}
