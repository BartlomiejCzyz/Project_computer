package com.example.__projekt_komputer.casing.hardware.components.drive;

import com.example.__projekt_komputer.casing.exceptions.Exceptions;
import com.example.__projekt_komputer.casing.exceptions.TextFragmentIsNullException;
import com.example.__projekt_komputer.casing.hardware.components.CPU;
import com.example.__projekt_komputer.casing.hardware.components.ComponentType;
import com.example.__projekt_komputer.casing.hardware.components.CpuAwareDrive;
import com.example.__projekt_komputer.casing.software.file.shared.Capacity;
import com.example.__projekt_komputer.casing.software.file.shared.AppFile;
import com.example.__projekt_komputer.casing.exceptions.AppFileNotFoundException;
import com.example.__projekt_komputer.casing.software.file.shared.FileService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class M2Drive extends AbstractDrive implements CpuAwareDrive {
    private CPU cpu;

    public M2Drive(String name, Capacity storageCapacity, FileService fileService, CPU cpu) {super(name, storageCapacity, fileService);
        this.cpu = cpu;
    }
    @Override
    public void setCPU(CPU cpu) {
        this.cpu = cpu;
    }

    @Override
    public CPU getCPU() {
        return cpu;
    }

    @Override
    public List<AppFile> findFileByContent(String textFragment) throws AppFileNotFoundException, TextFragmentIsNullException {
        if (textFragment == null || textFragment.isEmpty()) {
            throw Exceptions.TextFragmentIsNull();
        }
        List<AppFile> allAppFiles = getAllFiles();
        int threadCount = cpu.getThreads();
        System.out.println(threadCount);

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        List<Callable<List<AppFile>>> tasks = new ArrayList<>();

        int partitionSize = (int) Math.ceil((double) allAppFiles.size() / threadCount);

        for (int i = 0; i < allAppFiles.size(); i += partitionSize) {
            int fromIndex = i;
            int toIndex = Math.min(i + partitionSize, allAppFiles.size());
            List<AppFile> sublist = allAppFiles.subList(fromIndex, toIndex);

            tasks.add(() -> {
                List<AppFile> matches = new ArrayList<>();
                for (AppFile appFile : sublist) {
                    if (containsRabinKarp(appFile.getContent(), textFragment)) {
                        matches.add(appFile);
                    }
                }
                return matches;
            });
        }

        List<AppFile> matchingAppFiles = new ArrayList<>();
        try {
            List<Future<List<AppFile>>> futures = executor.invokeAll(tasks);
            for (Future<List<AppFile>> future : futures) {
                matchingAppFiles.addAll(future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error with threads", e);
        } finally {
            executor.shutdown();
        }

        if (matchingAppFiles.isEmpty()) {
            throw Exceptions.AppFileWithGivenFragmentNotFound();
        }

        return matchingAppFiles;
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
        return ComponentType.M2_DRIVE;
    }
}
