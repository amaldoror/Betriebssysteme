# Betriebssysteme
<code>HAW HH BAI3 BSP-02</code>

Prof.Dr.-Ing. Christian Lins<br>
Prof.Dr.-Ing. Jochen Rust

---

<br>

### Info

Dieses Repository enthält Aufgabenblätter, Shellskripte, C-Programme und Reference Sheets im Markdown-Format.

<br>

Aufgaben und Reference Sheets:
- Aufgaben.md: Aufgabenblatt 1 - Fragen & Antworten
- BAI3_BSP_Aufgabenblatt_1.pdf - Aufgabenblatt zum ersten Praktikumstermin am 21.04.2024
- UNIX.md: Reference Sheet für UNIX/Linux Befehle
  
<br>

Shellskripte:
- example.sh: Beispielhaftes Shellskript für den ersten Termin
- frename.sh:  Skript, das für alle Dateien im aktuellen Verzeichnis die Zeichenkette <string> an den aktuellen Dateinamen anhängt
- try_host.sh: Skript zum Anpingen eines Hosts / einer IP-Adresse, um auf Erreichbarkeit zu überwachen

<br>

C-Programme:
- hello.c: Willkommen im BSP!
- mkfile.c: Programm, das eine neue Datei anlegt

<br>

### frename.sh

frename.sh <string>
Hängt für alle Dateien im aktuellen Verzeichnis die Zeichenkette string an den aktuellen Dateinamen an (Umbenennung).

### mkfile.c

Das kompilierte Programm mkfile liest einen Dateinamen ein, erstellt eine leere Datei mit diesem Namen und den Zugriffsrechten 0700 (read, write, execute für Besitzer) und gibt eine entsprechende Meldung auf dem Bildschirm aus. Falls ein Fehler aufgetreten ist, wird eine allgemeine Fehlermeldung ausgegeben.
