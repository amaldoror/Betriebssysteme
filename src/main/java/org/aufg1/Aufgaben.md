# Aufgaben a) bis d)

### Fragen:

a) Was enthalten die folgenden Umgebungsvariablen (Environment Variables)?
  - <code>$HOME</code><br>
  - <code>$PATH</code><br>
  - <code>$UID</code><br>
  - <code>$USER</code><br>
  
b) Was bewirkt der Befehl <code>cd $HOME</code>? Gibt es eine einfachere Alternative?

c) Was für eine Funktion haben die folgenden Eingaben?
  - :arrow_up:
  - :arrow_down:
  - <code>strg-d</code> (in leerer Zeile)

d) Was ist die Funktion der .bashrc Datei im Verzeichnis $HOME?

<br>

---

<br>

### Antworten:

a) Die Umgebungsvariablen enthalten:
  - <code>$HOME</code>: Pfad zum Home-Verzeichnis des aktuellen Benutzers.
  - <code>$PATH</code>: Eine Liste von Verzeichnissen, in denen das Betriebssystem nach ausführbaren Dateien sucht, wenn ein Befehl im Terminal eingegeben wird.
  - <code>$UID</code>: Die User-ID des aktuellen Users.
  - <code>$USER</code>: Den Username des aktuellen Users.
  
b) Der Befehl <code>cd $HOME</code> bewirkt, dass das Terminalverzeichnis in das Home-Verzeichnis des aktuellen Users wechselt. Eine einfachere Alternative wäre einfach <code>cd</code> ohne Argumente zu verwenden, was ebenfalls zum Home-Verzeichnis führt.

c) Die Funktionen der folgenden Eingaben sind:

  - :arrow_up:: Bewegt den Cursor im Terminal zur vorherigen Eingabezeile.
  - :arrow_down:: Bewegt den Cursor im Terminal zur nächsten Eingabezeile.
  - <code>strg-d</code> (in leerer Zeile): Sendet das End-of-File-Signal, um anzuzeigen, dass die Eingabe beendet ist. Wenn die Eingabezeile leer ist, führt dies normalerweise dazu, dass das Terminal geschlossen wird.
  
d) Die .bashrc Datei im Verzeichnis $HOME hat die Funktion, Befehle und Einstellungen für die Bash-Shell zu definieren. Diese Datei wird jedes Mal geladen, wenn ein neues Shell-Fenster geöffnet wird. Sie wird verwendet, um userspezifische Environment Variables, Alias-Befehle und Befehle zur Anpassung des Shell-Verhaltens festzulegen.
