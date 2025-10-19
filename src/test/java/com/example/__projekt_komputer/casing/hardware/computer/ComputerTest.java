package com.example.__projekt_komputer.casing.hardware.computer;


import com.example.__projekt_komputer.casing.hardware.components.CPU;
import com.example.__projekt_komputer.casing.hardware.components.CpuAwareDrive;
import com.example.__projekt_komputer.casing.hardware.components.Monitor;
import com.example.__projekt_komputer.casing.hardware.components.drive.Drive;
import com.example.__projekt_komputer.casing.hardware.components.drive.M2Drive;
import com.example.__projekt_komputer.casing.software.file.shared.AppFile;
import com.example.__projekt_komputer.casing.software.file.shared.Capacity;
import com.example.__projekt_komputer.casing.software.file.shared.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ComputerTest {
    private Monitor monitor;

    private Drive activeDrive;
    private CPU cpu;
    private Computer computer;

    @BeforeEach
    void setUp() {
        FileService fileService = mock(FileService.class);
        cpu = CPU.of("Intel", 4);
        activeDrive = new M2Drive("M2_MAIN", Capacity.GB64, fileService, cpu);
        monitor = new Monitor("Dell");

        // reset singleton przez refleksję (żeby testy nie dziedziczyły stanu)
        try {
            var instanceField = Computer.class.getDeclaredField("instance");
            instanceField.setAccessible(true);
            instanceField.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to reset Computer singleton", e);
        }

        computer = Computer.getInstance(monitor, activeDrive, cpu);
        computer.setActiveDrive(activeDrive);
    }

    @Test
    void shouldReturnSameSingletonInstance() {
        Computer another = Computer.getInstance(monitor, activeDrive, cpu);
        assertSame(computer, another);
    }

    @Test
    void shouldAddFileWhenActiveDrivePresent() {
        AppFile file = new AppFile("test", "data", "txt", 0);
        Drive driveMock = mock(Drive.class);
        computer.setActiveDrive(driveMock);

        computer.addFile(file);

        verify(driveMock).addFile(file);
    }

    @Test
    void shouldThrowWhenAddingFileWithoutActiveDrive() {
        // usuwamy aktywny dysk
        computer.removeDrive("M2_MAIN");

        AppFile file = new AppFile("test", "data", "txt", 0);

        assertThrows(RuntimeException.class, () -> computer.addFile(file));
    }

    @Test
    void shouldRemoveFileFromActiveDrive() {
        Drive driveMock = mock(Drive.class);
        when(driveMock.removeFile("test")).thenReturn(true);
        computer.setActiveDrive(driveMock);

        boolean removed = computer.removeFile("test");

        assertTrue(removed);
        verify(driveMock).removeFile("test");
    }

    @Test
    void shouldThrowWhenNoActiveDriveOnRemove() {
        computer.removeDrive("M2_MAIN");
        assertThrows(RuntimeException.class, () -> computer.removeFile("someFile"));
    }

    @Test
    void shouldChangeActiveDrive() {
        Drive newDrive = mock(Drive.class);
        when(newDrive.getName()).thenReturn("NEW");

        computer.setActiveDrive(newDrive);

        assertSame(newDrive, computer.getActiveDrive());
    }

    @Test
    void shouldMoveOldDriveToInactiveWhenSettingNewActive() {
        Drive newDrive = mock(Drive.class);
        when(newDrive.getName()).thenReturn("SECOND");

        computer.setActiveDrive(newDrive);

        assertDoesNotThrow(() -> computer.changeActiveDrive("M2_MAIN"));
        assertSame(activeDrive, computer.getActiveDrive());
    }

    @Test
    void shouldThrowWhenChangingToNonExistingDrive() {
        assertThrows(RuntimeException.class, () -> computer.changeActiveDrive("NOT_EXIST"));
    }

    @Test
    void shouldRemoveActiveDrive() {
        boolean removed = computer.removeDrive("M2_MAIN");
        assertTrue(removed);
        assertThrows(RuntimeException.class, () -> computer.getActiveDrive());
    }

    @Test
    void shouldRemoveInactiveDrive() {
        Drive anotherDrive = mock(Drive.class);
        when(anotherDrive.getName()).thenReturn("SECOND");
        computer.setActiveDrive(anotherDrive);

        boolean removed = computer.removeDrive("M2_MAIN");

        assertTrue(removed);
    }

    @Test
    void shouldChangeCPUAndUpdateCpuAwareDrives() {

        CPU newCpu = CPU.of("AMD", 8);
        computer.changeCPU(newCpu);

        assertSame(newCpu, computer.getActiveCPU()); // CPU w komputerze zmienione
        assertInstanceOf(M2Drive.class, computer.getActiveDrive());
        M2Drive updatedDrive = (M2Drive) computer.getActiveDrive();

        // sprawdzamy czy CPU w dysku też się zmieniło
        assertSame(newCpu, updatedDrive.getCPU());
    }

    @Test
    void shouldReturnActiveCPU() {
        assertSame(cpu, computer.getActiveCPU());
    }

    @Test
    void shouldThrowWhenNoActiveCPU() throws Exception {
        // resetujemy activeCPU przez refleksję
        var cpuField = Computer.class.getDeclaredField("activeCPU");
        cpuField.setAccessible(true);
        cpuField.set(computer, null);

        assertThrows(IllegalStateException.class, () -> computer.getActiveCPU());
    }
}
