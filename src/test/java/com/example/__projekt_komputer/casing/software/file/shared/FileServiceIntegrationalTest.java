package com.example.__projekt_komputer.casing.software.file.shared;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest //pełny kontekst Springa (ładuje repozytorium i serwis) po każdym teście cofa zmiany w bazie H2
@ActiveProfiles("test")

public class FileServiceIntegrationalTest {

    @Autowired
    private FileRepository fileRepository;

    @Test
    void shouldSaveAndFindFile() {
        AppFile file = new AppFile("test", "content", "txt", 0);

        fileRepository.save(file);

        Optional<AppFile> result = fileRepository.findByName("test");

        assertTrue(result.isPresent());
        assertEquals("content", result.get().getContent());
    }

    @Test
    void shouldRemoveFileFromDatabase() {
        AppFile file = new AppFile("deleteMe", "content", "txt", 0);
        fileRepository.save(file); // zapisujemy ręcznie do bazy
        fileRepository.delete(file);


        assertTrue(fileRepository.findByName("deleteMe").isEmpty());
    }

    @Test
    void shouldListAllFiles() {
        AppFile f1 = new AppFile("a", "aaa", "txt", 0);
        AppFile f2 = new AppFile("b", "bbb", "txt", 0);
        fileRepository.saveAll(List.of(f1, f2));

        List<AppFile> files = fileRepository.findAll();

        assertEquals(2, files.size());
    }

    @Test
    void shouldFindFilesByContent() {
        AppFile f1 = new AppFile("f1", "abc def", "txt", 0);
        AppFile f2 = new AppFile("f2", "xyz def", "txt", 0);
        fileRepository.saveAll(List.of(f1, f2));

        List<AppFile> results = fileRepository.findByContentContaining("def");

        assertEquals(2, results.size());
    }
}
