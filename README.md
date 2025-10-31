# Symulator komputera

**Autor:** Bartłomiej Czyż  
**Technologie:** Java, Spring Boot, MySQL, JUnit, Mockito

## Opis projektu

Projekt przedstawia symulator komputera działającego w konsoli. Aplikacja udostępnia menu tekstowe, które umożliwia zarządzanie wirtualnymi komponentami sprzętowymi oraz plikami przechowywanymi w bazie danych.  
System wykorzystuje wzorce projektowe takie jak Factory, Singleton oraz Strategy, a także wielowątkowość i różne algorytmy wyszukiwania.

## Funkcjonalności

### File Menu
- Dodawanie plików do bazy danych
- Usuwanie plików
- Wyświetlanie wszystkich plików
- Wyszukiwanie pliku po nazwie
- Wyszukiwanie pliku po fragmencie tekstu

Sposób działania wyszukiwania zależy od aktualnie aktywnego dysku:
- HDD: naiwne sprawdzanie każdej litery
- SSD: naiwne wyszukiwanie z wykorzystaniem wielowątkowości
- M2: algorytm Rabin-Karp z wielowątkowością
- M4: wyszukiwanie delegowane do bazy danych

### Hardware Menu
- Dodawanie nowego dysku (automatyczna zmiana aktywnego dysku)
- Usuwanie dysku po nazwie
- Zmiana aktywnego dysku na inny z listy nieaktywnych
- Zmiana CPU (wpływa na liczbę dostępnych wątków)
- Wyświetlanie wszystkich komponentów

Dostępne typy dysków:
- HDD
- SSD
- M2
- M4

### USB Devices Menu
- Obecnie niezaimplementowane (przewidziane pod przyszłą rozbudowę)

## Architektura projektu

Projekt został zaprojektowany modularnie. Każdy podzespół jest reprezentowany jako osobna klasa implementująca wspólny interfejs `Components`.

Najważniejsze elementy:
- Computer – główna klasa zarządzająca programem
- CPU – przechowuje liczbę wątków dostępnych w systemie
- Drive (HDD, SSD, M2, M4) – obsługuje operacje na plikach
- FileService – pośrednik pomiędzy aplikacją a bazą danych MySQL
- Main – logika interakcji z użytkownikiem poprzez konsolę

Niektóre dyski implementują interfejs `CpuAwareDrive`, dzięki czemu mogą dynamicznie reagować na zmianę CPU.

## Testowanie

Projekt zawiera testy jednostkowe przygotowane przy użyciu JUnit 5 oraz Mockito.

Testowane są:
- klasy modelowe
- poprawność walidacji
- obsługa wyjątków
- działanie fabryk
