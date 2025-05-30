package com.example.__projekt_komputer.computer.hardware.components.drive;

import com.example.__projekt_komputer.computer.hardware.components.CPU;
import com.example.__projekt_komputer.computer.software.file.shared.Capacity;
import com.example.__projekt_komputer.computer.hardware.components.ComponentType;
import com.example.__projekt_komputer.computer.software.file.shared.File;
import com.example.__projekt_komputer.computer.software.file.shared.FileNotFoundException;
import com.example.__projekt_komputer.computer.software.file.shared.FileService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class SSDDrive extends AbstractDrive {
    private final CPU cpu;

    public SSDDrive(String name, Capacity storageCapacity, FileService fileService, CPU cpu) {super(name, storageCapacity, fileService); this.cpu = cpu;}



    @Override
    public List<File> findFileByContent(String textFragment) throws FileNotFoundException {
        System.out.println("ssd");
        List<File> allFiles = getAllFiles();
        int threadCount = cpu.getThreads(); // CPU wstrzyknięty wcześniej

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
                    if (containsFragment(file.getContent(), textFragment)) {
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
            throw new RuntimeException("Thread execution failed: " + e.getMessage(), e);
        } finally {
            executor.shutdown();
        }

        if (matchingFiles.isEmpty()) {
            throw new FileNotFoundException("Nie znaleziono pliku z podanym fragmentem.");
        }

        return matchingFiles;

    }
    private boolean containsFragment(String content, String fragment) {
        if (content == null || fragment == null || fragment.length() > content.length()) return false;

        for (int i = 0; i <= content.length() - fragment.length(); i++) {
            int j = 0;
            while (j < fragment.length() && content.charAt(i + j) == fragment.charAt(j)) {
                j++;
            }
            if (j == fragment.length()) return true;
        }
        return false;
    }

    @Override
    public ComponentType getType() {
        return ComponentType.DRIVE;
    }
}
