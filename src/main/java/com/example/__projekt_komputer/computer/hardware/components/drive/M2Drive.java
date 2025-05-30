package com.example.__projekt_komputer.computer.hardware.components.drive;

import com.example.__projekt_komputer.computer.hardware.components.CPU;
import com.example.__projekt_komputer.computer.hardware.components.ComponentType;
import com.example.__projekt_komputer.computer.software.file.shared.Capacity;
import com.example.__projekt_komputer.computer.software.file.shared.File;
import com.example.__projekt_komputer.computer.software.file.shared.FileNotFoundException;
import com.example.__projekt_komputer.computer.software.file.shared.FileService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class M2Drive extends AbstractDrive {
    private final CPU cpu;

    public M2Drive(String name, Capacity storageCapacity, FileService fileService, CPU cpu) {super(name, storageCapacity, fileService);
        this.cpu = cpu;
    }


    @Override
    public List<File> findFileByContent(String textFragment) throws FileNotFoundException {
        System.out.println("m2");
        List<File> allFiles = getAllFiles();
        int threadCount = cpu.getThreads();

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        List<Callable<List<File>>> tasks = new ArrayList<>();

        int partitionSize = (int) Math.ceil((double) allFiles.size() / threadCount);

        for (int i = 0; i < allFiles.size(); i += partitionSize) {
            int fromIndex = i;
            int toIndex = Math.min(i + partitionSize, allFiles.size());
            List<File> sublist = allFiles.subList(fromIndex, toIndex);

            tasks.add(() -> {
                List<File> matches = new ArrayList<>();
                for (File file : sublist) {
                    if (containsRabinKarp(file.getContent(), textFragment)) {
                        matches.add(file);
                    }
                }
                return matches;
            });
        }

        List<File> matchingFiles = new ArrayList<>();
        try {
            List<Future<List<File>>> futures = executor.invokeAll(tasks);
            for (Future<List<File>> future : futures) {
                matchingFiles.addAll(future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Błąd podczas przetwarzania wątków", e);
        } finally {
            executor.shutdown();
        }

        if (matchingFiles.isEmpty()) {
            throw new FileNotFoundException("Nie znaleziono plików zawierających podany fragment.");
        }

        return matchingFiles;
    }
    private boolean containsRabinKarp(String text, String pattern) {
        if (text == null || pattern == null || pattern.length() > text.length()) return false;

        int base = 256;
        int mod = 101; // liczba pierwsza
        int m = pattern.length();
        int n = text.length();

        int patternHash = 0;
        int textHash = 0;
        int h = 1;

        // h = base^(m-1) % mod
        for (int i = 0; i < m - 1; i++) {
            h = (h * base) % mod;
        }

        // początkowe hashe
        for (int i = 0; i < m; i++) {
            patternHash = (base * patternHash + pattern.charAt(i)) % mod;
            textHash = (base * textHash + text.charAt(i)) % mod;
        }

        // przesuwanie wzorca przez tekst
        for (int i = 0; i <= n - m; i++) {
            if (patternHash == textHash) {
                // potencjalne dopasowanie
                int j;
                for (j = 0; j < m; j++) {
                    if (text.charAt(i + j) != pattern.charAt(j)) break;
                }
                if (j == m) return true;
            }

            // przelicz hash tekstu (rolling hash)
            if (i < n - m) {
                textHash = (base * (textHash - text.charAt(i) * h) + text.charAt(i + m)) % mod;
                if (textHash < 0) textHash += mod;
            }
        }

        return false;
    }



    @Override
    public ComponentType getType() {
        return null;
    }
}
