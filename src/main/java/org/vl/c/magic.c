#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/stat.h>
#include <time.h>
#include <fcntl.h>
#include <unistd.h>
#include <errno.h>

#define MAGIC_SIZE 4

struct FileType {
    const char *magic;
    const char *description;
};

const struct FileType file_types[] = {
    {"\x7F\x45\x4C\x46", "ELF (Executable and Linkable Format)"},
    {"\xCA\xFE\xBA\xBE", "Java Class File"},
    {"\x89\x50\x4E\x47", "PNG Image"},
    {"\x25\x50\x44\x46", "PDF Document"},
    {"\x50\x4B\x03\x04", "ZIP Archive"},
    {"\xFF\xD8\xFF\xE0", "JPEG Image"},
    {"\x47\x49\x46\x38", "GIF Image"},
    {"\x42\x4D", "BMP Image"},
    {"\x4D\x5A", "DOS/Windows Executable"},
    {"\x52\x61\x72\x21", "RAR Archive"},
    {"\x1F\x8B\x08", "GZIP Archive"},
    {"\x37\x7A\xBC\xAF", "7-Zip Archive"},
    {"\x75\x73\x74\x61", "TAR Archive"},
    {"\x4F\x67\x67\x53", "OGG Audio/Video"},
    {"\x49\x44\x33", "MP3 Audio (ID3)"},
    {"\x66\x74\x79\x70\x69\x73\x6F\x6D", "MP4 Video"},
    {"\x1A\x45\xDF\xA3", "Matroska Video"},
    {"\x00\x00\x00\x0C\x6A\x50\x20\x20", "JPEG 2000 Image"},
    {"\x00\x00\x00\x14\x66\x74\x79\x70", "QuickTime Video"},
    {"\x46\x4C\x56\x01", "Flash Video"},
    {"\xD0\xCF\x11\xE0", "Microsoft Office Document"},
    {"\x50\x4B\x03\x04\x14\x00\x06\x00", "Microsoft Office Open XML Document"},
    {"\x1F\x8B\x08\x00", "GZIP Compressed File"},
    {"\x42\x5A\x68", "BZIP2 Compressed File"},
    {"\x28\xB5\x2F\xFD", "ZSTD Compressed File"},
    {"\x3C\x3F\x78\x6D\x6C", "XML Document"},
    {"\x7B\x5C\x72\x74\x66\x31", "RTF Document"},
    {"\x23\x21\x2F\x62\x69\x6E\x2F", "Shell Script"},
    {"\x2E\x73\x6E\x64", "AU Audio"},
    {"\x00\x01\x00\x00\x00", "TrueType Font"}
};

void print_error(const char *message) {
    fprintf(stderr, "Error: %s - %s\n", message, strerror(errno));
}

int main(int argc, char *argv[]) {
    if (argc != 2) {
        fprintf(stderr, "Usage: %s <filename>\n", argv[0]);
        return 1;
    }

    const char *filename = argv[1];
    int fd = open(filename, O_RDONLY);
    if (fd == -1) {
        print_error("Unable to open file");
        return 1;
    }

    unsigned char magic[MAGIC_SIZE];
    ssize_t bytes_read = read(fd, magic, MAGIC_SIZE);
    if (bytes_read != MAGIC_SIZE) {
        print_error("Unable to read magic numbers");
        close(fd);
        return 1;
    }

    struct stat file_stat;
    if (fstat(fd, &file_stat) == -1) {
        print_error("Unable to get file stats");
        close(fd);
        return 1;
    }

    close(fd);

    printf("Die Datei '%s' ist ", filename);

    int found = 0;
    for (size_t i = 0; i < sizeof(file_types) / sizeof(file_types[0]); i++) {
        if (memcmp(magic, file_types[i].magic, MAGIC_SIZE) == 0) {
            printf("ein %s.\n", file_types[i].description);
            found = 1;
            break;
        }
    }

    if (!found) {
        printf("von unbekanntem Typ.\n");
    }

    printf("Die identifizierenden Magic Numbers: ");
    for (int i = 0; i < MAGIC_SIZE; i++) {
        printf("%02X ", magic[i]);
    }
    printf("\n");

    printf("Dateigröße: %lld Bytes\n", (long long)file_stat.st_size);
    printf("Erstellungsdatum: %s", ctime(&file_stat.st_ctime));
    printf("Zeitpunkt des letzten Zugriffs: %s", ctime(&file_stat.st_atime));

    return 0;
}