/*
Erzeugen Sie sich unter Linux mit dd eine 50 MiB große Datei, die gefüllt ist mit lauter Nullzeichen.
Lesen Sie die Manpage zu dd.
Hinweis: Eine unerschöpfliche Quelle von Nullzeichen ist die Pseudodatei /dev/zero.
Erstellen Sie sich ein C-Programm, dass diese große Datei mit Hilfe von mmap() in den Speicher
einblendet.
Das Programm soll jedes n-te Zeichen in der Datei mit
einem beliebigen ASCII-Zeichen ersetzen, z. B.

$ maptest grossedatei.bin 3 'A'

-> jedes 3 Zeichen wird ein A
*/

// Compile:
// gcc -o maptest maptest.c
// Datei erzeugen:
// dd if=/dev/zero of=grossedatei.bin bs=1M count=50
// Usage:
// ./maptest grossedatei.bin 3 'A'

#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include <errno.h>
#include <string.h>

void print_error(const char *msg) {
    fprintf(stderr, "Fehler: %s - %s\n", msg, strerror(errno));
    exit(1);
}

int main(int argc, char *argv[]) {
    if (argc != 4) {
        fprintf(stderr, "Verwendung: %s <dateiname> <n> <zeichen>\n", argv[0]);
        exit(1);
    }

    const char *filename = argv[1];
    int n = atoi(argv[2]);
    char replace_char = argv[3][0];

    if (n <= 0) {
        fprintf(stderr, "n muss größer als 0 sein\n");
        exit(1);
    }

    int fd = open(filename, O_RDWR);
    if (fd == -1) {
        print_error("Kann Datei nicht öffnen");
    }

    struct stat sb;
    if (fstat(fd, &sb) == -1) {
        print_error("Kann Dateigröße nicht ermitteln");
    }

    char *addr = mmap(NULL, sb.st_size, PROT_READ | PROT_WRITE, MAP_SHARED, fd, 0);
    if (addr == MAP_FAILED) {
        print_error("Kann Datei nicht in den Speicher einblenden");
    }

    for (off_t i = n - 1; i < sb.st_size; i += n) {
        addr[i] = replace_char;
    }

    if (msync(addr, sb.st_size, MS_SYNC) == -1) {
        print_error("Kann Änderungen nicht synchronisieren");
    }

    if (munmap(addr, sb.st_size) == -1) {
        print_error("Kann Speicherabbildung nicht aufheben");
    }

    if (close(fd) == -1) {
        print_error("Kann Datei nicht schließen");
    }

    printf("Jedes %d. Zeichen wurde durch '%c' ersetzt.\n", n, replace_char);

    return 0;
}