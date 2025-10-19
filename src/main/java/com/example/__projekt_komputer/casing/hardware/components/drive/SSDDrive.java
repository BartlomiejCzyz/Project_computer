package com.example.__projekt_komputer.casing.hardware.components.drive;

import com.example.__projekt_komputer.casing.exceptions.Exceptions;
import com.example.__projekt_komputer.casing.exceptions.TextFragmentIsNullException;
import com.example.__projekt_komputer.casing.hardware.components.CPU;
import com.example.__projekt_komputer.casing.hardware.components.CpuAwareDrive;
import com.example.__projekt_komputer.casing.software.file.shared.Capacity;
import com.example.__projekt_komputer.casing.hardware.components.ComponentType;
import com.example.__projekt_komputer.casing.software.file.shared.AppFile;
import com.example.__projekt_komputer.casing.exceptions.AppFileNotFoundException;
import com.example.__projekt_komputer.casing.software.file.shared.FileService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class SSDDrive extends AbstractDrive implements CpuAwareDrive {
    private CPU cpu;

    public SSDDrive(String name, Capacity storageCapacity, FileService fileService, CPU cpu) {super(name, storageCapacity, fileService); this.cpu = cpu;}

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
        if (textFragment == null || textFragment.isEmpty()){
            throw Exceptions.TextFragmentIsNull();
        }
        List<AppFile> allAppFiles = getAllFiles();
        int threadCount = Math.min(cpu.getThreads(), allAppFiles.size()); // CPU wstrzyknięty wcześniej
        if (threadCount == 0) {
            throw Exceptions.NoAppFileFound(); // brak plików w ogóle
        }

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
                    if (containsFragment(appFile.getContent(), textFragment)) {
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
            throw new RuntimeException("Thread execution failed: " + e.getMessage(), e);
        } finally {
            executor.shutdown();
        }

        if (matchingAppFiles.isEmpty()) {
            throw Exceptions.AppFileWithGivenFragmentNotFound();
        }

        return matchingAppFiles;

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
        return ComponentType.SSD_DRIVE;
    }
}
