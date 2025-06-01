package com.example.__projekt_komputer.computer.software.file.shared;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Integer> {
    Optional<File> findByName(String name);
    List<File> findByContentContaining(String content);
}
