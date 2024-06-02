#include <stdio.h>      // Standard Input/Output-Funktionalität
#include <stdlib.h>     // Standardbibliothek für allgemeine Funktionen
#include <string.h>     // String-Manipulationsfunktionen
#include <unistd.h>     // Enthält POSIX-Funktionen (hier: getcwd)
#include <sys/types.h> 	// Enthält Datentypen für Systemaufrufe (hier: pid_t)
#include <sys/wait.h>  	// Enthält Funktionen für das Warten auf Prozessstatus

#define MAX_LINE 1024  // Maximale Länge einer Eingabezeile
#define MAX_ARGS 64    // Maximale Anzahl von Argumenten in einer Befehlszeile

// Funktion, um die Shell-Eingabeaufforderung zu drucken
void print_prompt() {
    char cwd[1024]; 										// Array für den aktuellen Arbeitsverzeichnis-Pfad
    getcwd(cwd, sizeof(cwd)); 								// Aktuelles Arbeitsverzeichnis abrufen
    printf("[%s@%s]%s ", getenv("USER"),
		cwd, getcwd(cwd, sizeof(cwd))); 					// Benutzer, Rechnername und Pfad anzeigen
}

// Funktion zum Ausführen von integrierten (eingebauten) Shell-Befehlen
void execute_builtin(char *cmd) {
	
    if (strcmp(cmd, "quit") == 0) { 						// Wenn der Befehl 'quit' ist
        exit(0); 											// Programm beenden
    } else if (strcmp(cmd, "version") == 0) { 				// Wenn der Befehl 'version' ist
        printf("HAW Shell version 1.0 by Adrian Morgenthal\n"); 	// Shell-Version anzeigen
    } else if (strncmp(cmd, "~/", 2) == 0) { 				// Wenn der Befehl mit '~/' beginnt
        char path[1024]; 									// Array für den Pfad
        sprintf(path, "%s/%s", getenv("HOME"), cmd + 2); 	// Pfad erstellen
        chdir(path); 										// Ins angegebene Verzeichnis wechseln
    } else if (strcmp(cmd, "help") == 0) { 					// Wenn der Befehl 'help' ist
        printf("Available built-in commands:\n"); 			// Verfügbar gemachte integrierte Befehle anzeigen
        printf("  quit: exit the shell\n");
        printf("  version: display shell version\n");
        printf("  ~/[path]: change directory to [path] in home directory\n");
        printf("  help: display this help message\n");
    } else { 												// Wenn der Befehl unbekannt ist
        printf("Unknown built-in command: %s\n", cmd); 		// Unbekannten Befehl anzeigen
    }
}

// Funktion zum Ausführen externer Shell-Befehle
void execute_external(char *cmd, int background) {
    pid_t pid; 												// Datentyp für Prozess-ID
    char *args[MAX_ARGS]; 									// Array für Argumente des Befehls
    int i = 0;

    args[i++] = cmd; 										// Befehl selbst als erstes Argument speichern

    pid = fork(); 											// Neuen Prozess erzeugen
    if (pid == 0) { 										// Im Kindprozess
        execvp(cmd, args); 									// Befehl im Kindprozess ausführen
        perror("execvp"); 									// Fehlerbehandlung, falls execvp fehlschlägt
        exit(1); 											// Kindprozess beenden
    } else { 												// Im Elternprozess
        if (!background) { 									// Wenn der Befehl nicht im Hintergrund ausgeführt werden soll
            waitpid(pid, NULL, 0); 							// Auf Beendigung des Kindprozesses warten
        }
    }
}

// Hauptfunktion der Shell
int main() {
    char line[MAX_LINE]; 									// Array für die Eingabezeile
    char *cmd; 												// Zeiger auf den aktuellen Befehl
    int background = 0; 									// Flag, um festzustellen, ob ein Befehl im Hintergrund ausgeführt werden soll

    while (1) { 											// Endlosschleife für die Shell
        print_prompt(); 									// Shell-Eingabeaufforderung drucken
        fgets(line, MAX_LINE, stdin); 						// Eingabezeile lesen
        line[strlen(line) - 1] = '\0'; 						// Zeilenumbruch am Ende der Eingabe entfernen

        cmd = strtok(line, " "); 							// Eingabezeile in Befehle aufteilen (nach Leerzeichen)
        if (cmd == NULL) continue; 							// Wenn keine Befehle vorhanden sind, zur nächsten Iteration springen

        if (strcmp(cmd, "quit") == 0 	||					// Wenn der Befehl ein integrierter Befehl ist
		 strcmp(cmd, "version") == 0 	|| 		
            strncmp(cmd, "~/", 2) == 0 	||
			strcmp(cmd, "help") == 0) {
            execute_builtin(cmd); 							// Integrierten Befehl ausführen
        } else { 											// Wenn der Befehl extern ist
            if (strchr(cmd, '&')!= NULL) { 					// Wenn '&' (Hintergrund-Ausführung) im Befehl vorhanden ist
                background = 1; 							// Flag für Hintergrund-Ausführung setzen
                cmd[strlen(cmd) - 1] = '\0'; 				// '&' am Ende des Befehls entfernen
            }
            execute_external(cmd, background); 				// Externen Befehl ausführen
        }

        background = 0; 									// Flag für Hintergrund-Ausführung zurücksetzen
    }

    return 0; 												// Rückgabewert der Hauptfunktion
}
