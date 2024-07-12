#include <stdio.h>

int main(int argc, char* argv[]) {
    //char ch;
    /*while(read(0, &ch, 1)) {
        //printf("%c", ch);
        //printf("%c", ch);
        write(1, &ch, 1);
        write(1, &ch, 1);
    }*/

    // Diese Schleife liest Zeichen von der Standardeingabe bis zum Dateiende (EOF)
    while(!feof(stdin)) {
        int c = fgetc(stdin); // Ein Zeichen von der Standardeingabe lesen
        if (c < 0) { // Wenn das Ende der Datei erreicht ist oder ein Fehler auftritt
            return -1; // Programm beenden und Fehlercode zurückgeben
        }
        fputc((unsigned char)c, stdout); // Das gelesene Zeichen zweimal auf die Standardausgabe schreiben
        fputc((unsigned char)c, stdout);
    }
}
