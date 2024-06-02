/*
* mkfile ist ein C Programm, das eine neue Datei mit einem gegebenen Namen inkl. Timestamp anlegt, z.B. NewFile_20240420_221317.
* 
* Adrian Morgenthal
* 20.04.2024
*/


#include <stdio.h>    // Ein- und Ausgabe
#include <stdlib.h>   // Allgemeine Funktionen
#include <string.h>   // Zeichenketten
#include <fcntl.h>    // Dateioperationen
#include <unistd.h>   // POSIX-Systemaufrufe

#define MAX_FILENAME_LENGTH 30    // Maximale Länge des Dateinamens
#define FILE_PERMISSION 0700      // Zugriffsrechte (r,w,x für Benutzer)

int main() {
    char filename[MAX_FILENAME_LENGTH + 1];     // Char-Array zum Speichern des Dateinamens inkl. Nullzeichen
    int fd;					// Dateideskriptor

    printf("Name der neuen Datei: ");		// Eingabeaufforderung

    // Zeichenkette von stdin lesen
    if (fgets(filename, sizeof(filename), stdin) == NULL) {
	perror("Fehler beim Lesen der Eingabe.\n");
	exit(EXIT_FAILURE);
    }

    // Zeilenumbruch am Ende der Eingabe entfernen
    filename[strcspn(filename, "\n")] = '\0';

    // Datei erstellen
    fd = creat(filename, FILE_PERMISSION);
    if (fd == -1) {
	perror("Fehler beim Erstellen der Datei.");
	exit(EXIT_FAILURE);
    }

    // Datei schließen
    if (close(fd) == -1) {
	perror("Fehler beim Schließen der Datei: %s\n");
	exit(EXIT_FAILURE);
    }

    printf("Die Datei %s wurde erfolgreich angelegt!\n", filename);

    return 0;
}
