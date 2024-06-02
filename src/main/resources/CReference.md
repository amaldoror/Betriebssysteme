# C Reference

| Funktion                  | Beschreibung                                          | Beispiel                      |
|---------------------------|-------------------------------------------------------|-------------------------------|
| `printf(const char *format, ...)` | Gibt formatierte Ausgabe aus | `printf("Hello, world!\n");` |
| `scanf(const char *format, ...)` | Liest formatierte Eingabe ein | `scanf("%d", &number);` |
| `malloc(size_t size)` | Allokiert Speicher dynamisch | `int *arr = (int *)malloc(10 * sizeof(int));` |
| `free(void *ptr)` | Gibt dynamisch allokierten Speicher frei | `free(arr);` |
| `strlen(const char *str)` | Gibt die Länge einer Zeichenkette zurück | `int len = strlen("hello");` |
| `strcpy(char *dest, const char *src)` | Kopiert eine Zeichenkette | `char dest[20]; strcpy(dest, "hello");` |
| `strcmp(const char *str1, const char *str2)` | Vergleicht zwei Zeichenketten | `if (strcmp(str1, str2) == 0) { ... }` |
| `strcat(char *dest, const char *src)` | Hängt eine Zeichenkette an eine andere an | `char dest[20] = "hello"; strcat(dest, " world");` |
| `fgets(char *str, int num, FILE *stream)` | Liest eine Zeile aus einer Datei | `fgets(buffer, 100, file);` |
| `fputs(const char *str, FILE *stream)` | Schreibt eine Zeichenkette in eine Datei | `fputs("hello", file);` |
| `fprintf(FILE *stream, const char *format, ...)` | Gibt formatierte Ausgabe in eine Datei aus | `fprintf(file, "Value: %d\n", value);` |
| `fscanf(FILE *stream, const char *format, ...)` | Liest formatierte Eingabe aus einer Datei ein | `fscanf(file, "%d", &value);` |
| `fgets(char *str, int num, FILE *stream)` | Liest eine Zeile aus einer Datei | `fgets(buffer, 100, file);` |
| `fputc(int character, FILE *stream)` | Schreibt ein Zeichen in eine Datei | `fputc('a', file);` |
| `fgetc(FILE *stream)` | Liest ein Zeichen aus einer Datei | `int c = fgetc(file);` |
| `feof(FILE *stream)` | Überprüft, ob das Dateiende erreicht ist | `if (!feof(file)) { ... }` |
| `rewind(FILE *stream)` | Setzt den Dateizeiger einer Datei zurück | `rewind(file);` |
| `sprintf(char *str, const char *format, ...)` | Schreibt formatierte Ausgabe in eine Zeichenkette | `sprintf(str, "Value: %d", value);` |
| `sscanf(const char *str, const char *format, ...)` | Liest formatierte Eingabe aus einer Zeichenkette | `sscanf(str, "%d", &value);` |
| `qsort(void *base, size_t num, size_t size, int (*compare)(const void *, const void *))` | Sortiert ein Array | `qsort(arr, 10, sizeof(int), compare);` |
| `atoi(const char *str)` | Konvertiert eine Zeichenkette in eine Ganzzahl | `int number = atoi("123");` |
| `atof(const char *str)` | Konvertiert eine Zeichenkette in eine Gleitkommazahl | `float value = atof("3.14");` |
| `exit(int status)` | Beendet das Programm | `exit(0);` |
| `abs(int x)` | Gibt den absoluten Wert einer Zahl zurück | `int absolute = abs(-5);` |
| `labs(long x)` | Gibt den absoluten Wert einer langen Zahl zurück | `long absolute = labs(-1234567);` |
| `fabs(double x)` | Gibt den absoluten Wert einer Gleitkommazahl zurück | `double absolute = fabs(-3.14);` |
| `ceil(double x)` | Rundet eine Gleitkommazahl auf | `double rounded = ceil(3.14);` |
| `floor(double x)` | Rundet eine Gleitkommazahl ab | `double rounded = floor(3.14);` |
| `sqrt(double x)` | Berechnet die Quadratwurzel einer Zahl | `double square_root = sqrt(25.0);` |
| `pow(double x, double y)` | Berechnet x hoch y | `double result = pow(2.0, 3.0);` |
| `sin(double x)` | Berechnet den Sinus einer Zahl | `double sine = sin(3.14);` |
| `cos(double x)` | Berechnet den Kosinus einer Zahl | `double cosine = cos(3.14);` |
| `tan(double x)` | Berechnet den Tangens einer Zahl | `double tangent = tan(3.14);` |
| `asctime(const struct tm *timeptr)` | Wandelt eine Zeitstruktur in eine Zeichenkette um | `char *str = asctime(localtime(&t));` |
| `time_t time(time_t *timer)` | Gibt die aktuelle Zeit zurück | `time_t now = time(NULL);` |
| `struct tm *localtime(const time_t *timer)` | Wandelt einen Zeitwert in eine lokale Zeitstruktur um | `struct tm *local = localtime(&now);` |
| `void *memcpy(void *dest, const void *src, size_t n)` | Kopiert Speicherbereiche | `memcpy(dest, src, sizeof(src));` |
| `void *memset(void *s, int c, size_t n)` | Setzt Speicherbereiche auf einen Wert | `memset(buffer, 0, sizeof(buffer));` |
| `int strncmp(const char *str1, const char *str2, size_t n)` | Vergleicht die ersten n Zeichen zweier Zeichenketten | `int result = strncmp(str1, str2, 10);` |
| `void *malloc(size_t size)` | Allokiert Speicher dynamisch | `int *ptr = (int *)malloc(10 * sizeof(int));` |
| `void free(void *ptr)` | Gibt dynamisch allokierten Speicher frei | `free(ptr);` |
| `int rand(void)` | Gibt eine Zufallszahl zurück | `int random_num = rand();` |
| `srand(unsigned int seed)` | Initialisiert den Zufallszahlengenerator | `srand(time(NULL))`
| `int toupper(int c)` | Konvertiert ein Zeichen in Großbuchstaben | `char upper = toupper('a');` |
| `int tolower(int c)` | Konvertiert ein Zeichen in Kleinbuchstaben | `char lower = tolower('A');` |
| `void perror(const char *s)` | Gibt eine Fehlermeldung aus | `perror("Error");` |
| `void *calloc(size_t num, size_t size)` | Allokiert Speicher für ein Array | `int *arr = (int *)calloc(10, sizeof(int));` |
| `void exit(int status)` | Beendet das Programm | `exit(0);` |
| `FILE *fopen(const char *filename, const char *mode)` | Öffnet eine Datei | `FILE *file = fopen("example.txt", "r");` |
| `int fclose(FILE *stream)` | Schließt eine Datei | `fclose(file);` |
| `int fscanf(FILE *stream, const char *format, ...)` | Liest formatierte Eingabe aus einer Datei | `fscanf(file, "%d", &value);` |
| `int fprintf(FILE *stream, const char *format, ...)` | Gibt formatierte Ausgabe in eine Datei aus | `fprintf(file, "Value: %d\n", value);` |
| `int fseek(FILE *stream, long offset, int whence)` | Bewegt den Dateizeiger in einer Datei | `fseek(file, 0, SEEK_SET);` |
| `long ftell(FILE *stream)` | Gibt die aktuelle Position des Dateizeigers zurück | `long position = ftell(file);` |
| `void rewind(FILE *stream)` | Setzt den Dateizeiger einer Datei zurück | `rewind(file);` |
| `int feof(FILE *stream)` | Überprüft, ob das Dateiende erreicht ist | `if (!feof(file)) { ... }` |
| `int fflush(FILE *stream)` | Leert den Puffer einer Datei | `fflush(file);` |
| `void setbuf(FILE *stream, char *buffer)` | Legt den Puffer einer Datei fest | `setbuf(file, buffer);` |
| `int remove(const char *filename)` | Löscht eine Datei | `remove("file.txt");` |
| `int rename(const char *old_filename, const char *new_filename)` | Benennt eine Datei um | `rename("oldname.txt", "newname.txt");` |
| `int system(const char *command)` | Führt den Befehl in der Shell aus | `system("ls -l");` |

<br><br><br>

## How to compile a C program
### 1. Install the GNU Compiler Collection on Linux:
Make sure you have gcc installed:
<br>
<code>sudo apt install gcc</code>

### 2. Compile with shell command:
Use this shell command on Linux to compile a 'program.c' file into a program.<br>
<code>gcc -o program program.c</code>

### 3. Run program:
Make sure you're in the right directory:<br>
<code>cd [Path]</code><br>
Then run the program with this command ('program' is the name of your compiled C program):<br>
<code>./program</code>
