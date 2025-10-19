package com.example.__projekt_komputer.casing.hardware.components.drive;

import com.example.__projekt_komputer.casing.software.file.shared.AppFile;
import com.example.__projekt_komputer.casing.software.file.shared.Capacity;
import com.example.__projekt_komputer.casing.software.file.shared.FileRepository;
import com.example.__projekt_komputer.casing.software.file.shared.FileService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class M4PerformanceTest {
    @Autowired
    private FileRepository fileRepository;

    @BeforeAll
    static void setup(@Autowired FileRepository fileRepository) {

        String bigContent = "x".repeat(80_000) + "needle";
        for (int i = 0; i < 100_000; i++) {
            fileRepository.save(new AppFile("f" + i, bigContent + "content " + i, "txt", 1));
        }
    }

    @Test
    void performanceTestFindFileByContent(@Autowired FileRepository fileRepository) {
        FileService fileService = new FileService(fileRepository);
        M4Drive drive = new M4Drive("m4", Capacity.GB64, fileService);

        long start = System.currentTimeMillis();
        List<AppFile> result = drive.findFileByContent("content 99999");
        long duration = System.currentTimeMillis() - start;

        assertFalse(result.isEmpty());
        System.out.println("Duration = " + duration + " ms");
    }

}
