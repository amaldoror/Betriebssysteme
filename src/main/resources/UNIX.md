| Befehl | Beschreibung | Beispiel |
|--------|--------------|----------|
| strg-C | Laufendes Programm abbrechen | - |
| alias NAME=VALUE | Zeichenersetzung („Alias“) definieren (ohne Leerzeichen!) | alias ll='ls -l' |
| bg | Programm im Hintergrund laufen lassen (ohne Benutzereingaben) | firefox & |
| cat FILE | Textdatei FILE auf Standardausgabe (stdout) ausgeben | cat textfile.txt |
| cd DIR | Verzeichnis wechseln | cd Documents/ |
| chmod [ugoa][+/-][rwx] FILE | Zugriffsrechte bzgl. Datei FILE ändern (x: ausführbar) | chmod u+x script.sh |
| cp [-i] FILE1 FILE2 | Kopiere Datei FILE1 nach FILE2 | cp file1.txt file2.txt |
| date | Datum und Zeit anzeigen | date |
| df | Information über Dateisysteme anzeigen | df -h |
| diff FILE1 FILE2 | Unterschiede zwischen Datei FILE1 und Datei FILE2 anzeigen | diff file1.txt file2.txt |
| echo STRING | Zeichenkette STRING auf Standardausgabe (stdout) ausgeben | echo "Hello, world!" |
| env | Umgebungsvariablen anzeigen | env |
| exit | Shell oder Skript beenden | exit |
| export VAR | Shell-Variable VAR an alle Kindprozesse vererben | export PATH=/usr/local/bin:$PATH |
| fg | Programm in den Vordergrund holen | fg |
| find DIR -name FILE -print | Finde eine Datei namens FILE beginnend im Verzeichnis DIR | find /home/user -name "*.txt" |
| grep [-r] STRING FILE | Suche in der Datei FILE nach der Zeichnkette STRING | grep "error" logfile.txt |
| jobs | Information über Hintergrund-Programme der aktuellen Shell | jobs |
| kill [-9 ] PID | Prozess (laufendes Programm) mit der Prozessnr. PID beenden | kill 1234 |
| ln [-s] FILE1 FILE2 | [symbolischen] Link FILE1 –> FILE2 erzeugen | ln -s /path/to/file /path/to/symlink |
| lpq | Drucke Warteschlangen-Status | lpq |
| lpr [-P QUEUE] FILE | Drucke FILE auf Drucker-Queue queue | lpr -P printer1 file.txt |
| ls [-l] [FILE] | Aktuellen Verzeichnis-Inhalt als Liste von Dateinamen ausgeben | ls -l |
| man PROG | Beschreibung des Programms PROG | man ls |
| mkdir DIR | Verzeichnis erzeugen | mkdir new_directory |
| more FILE | Textdatei FILE seitenweise anzeigen | more long_textfile.txt |
| mv QUELLE ZIEL | Datei QUELLE umbenennen bzw. verschieben | mv file1.txt directory/ |
| passwd | Ändert das Passwort des aktuellen Benutzers | passwd |
| PROG | Ausführbares Programm PROG starten (wird in den in $PATH angegebenen Verzeichnissen gesucht) | firefox |
| PROG& | Programm PROG direkt als Hintergrundprozess starten (ohne Benutzereingaben) | firefox & |
| ps [-efa] | Prozess-Informationen anzeigen | ps aux |
| pstree | Prozess-Informationen als Baumstruktur anzeigen (Eltern/Kinder) | pstree |
| pwd | Name des aktuellen Verzeichnisses ausgeben | pwd |
| rm [-i] FILE | Datei FILE löschen | rm file.txt |
| rmdir DIR | Verzeichnis DIR löschen | rmdir empty_directory |
| sleep SEC | Hält die aktuelle Shell-Ausführung um SEC Sekunden an | sleep 5 |
| time PROG | Programm PROG starten und verbrauchte CPU-Zeit ausgeben | time ls |
| touch FILE | Legt eine neue Datei FILE an oder aktualisiert den Zeitstempel | touch new_file.txt |
| VAR=VALUE | Shell-Variable VAR den Wert VALUE zuweisen | NAME="John" |
| who | Aktuelle Benutzer dieses Systems anzeigen | who |
| > FILE | Standardausgabe (stdout) auf file umlenken, file ggf. neu erzeugen oder überschreiben | echo "Hello" > output.txt |
| >> FILE | Standardausgabe (stdout) auf file umlenken, file ggf. neu erzeugen oder Ausgaben an file anhängen | echo "World" >> output.txt |
| cat << EOF | Erzeugen eines here-Files. (Beenden mit “EOF“) | cat << EOF > textfile.txt |
| $VAR | Die Zeichenkette $VAR durch den aktuellen Wert der Variablen VAR ersetzen | echo "My name is $NAME" |
| ls -l \| sort -rnk5 | Standardausgabe eines anderen Programms verbinden (siehe auch Info unten) | ls -l | sort -rnk5 |
| $1 $2 $3 ... | Zeichenkette $1, $2, $3, .. durch jeweils 1., 2., 3. .. Parameter der Befehlszeile ersetzen ($0: Programmname) | echo "First argument is $1" |
| $# | Zeichenkette $# durch Anzahl der Parameter der Befehlszeile ersetzen (Dezimalzahl) | echo "Number of arguments: $#" |
| $? | Zeichenkette $? durch return value des zuletzt aufgerufenen Programms (Vordergrundprozesses) ersetzen | echo "Last command exit status: $?" |
| $(PROG) | Das Programm PROG starten und die Zeichenkette $(PROG) durch die Ausgaben des Programms ersetzen | echo "Current directory: $(pwd)" |
| . | Zeiger auf das aktuelle Verzeichnis | . |
| .. | Zeiger auf das direkt übergeordnete Verzeichnis | .. |
| * | Metazeichen: Platzhalter für beliebig viele Zeichen | - |
