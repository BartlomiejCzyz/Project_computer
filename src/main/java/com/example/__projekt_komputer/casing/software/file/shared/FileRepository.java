package com.example.__projekt_komputer.casing.software.file.shared;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<AppFile, Integer> {
    Optional<AppFile> findByName(String name);
    List<AppFile> findByContentContaining(String content);
}
