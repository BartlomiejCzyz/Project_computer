Autor: Bartłomiej Czyż
Program ma przedstawiać symulator komputera program działa na zasadzie menu wyborów w konsoli

menu usb devices obecnie jest nie zaimplementowane

w menu file jest opcja dodania plików do bazy danych, usunięcia, wyświetlenia wszystkich plików, wyszukania po nazwie oraz wyszukania po fragmencie tekstu. funkcja ta jest wykonywana w inny sposób w zależności od typu aktywnego dysku

w menu hardware są funkcje umożliwiające dodanie nowego dysku który jest automatycznie aktywowany a poprzedni jest dodawany do listy dysków nie aktywnych, usuniecie dysku po podaniu jego nazwy,
zmiana dysku na dysk który obecnie jest na liście dysków nieaktywnych zmiana cpu która odpowiada za liczbe wątków w programie, oraz wyświetlenie wszystkich sprzętów.
Dostępne są 4 typy dysków: HDD, SSD, M2 i M4 największą ich róznicą jest metoda wyszukująca plik po zadanym fragmencie. W HDD jest to naiwne sprawdzanie każdej litery, W SSD podobnie z rozszerzeniem o wielowątkowość,
W M2 jest wykorzystana wielowątkowość wraz z algorytmem rolling hash (Rabin-Karp), W M4 metoda ta jest oddelegowana do bazy danych 

pliki w bazie danych są w języku angielskim
