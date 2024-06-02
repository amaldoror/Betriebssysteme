
# Git reference

| Git-Befehl                  | Beschreibung                                     | Beispiel                                           |
|-----------------------------|--------------------------------------------------|----------------------------------------------------|
| `git init`                  | Initialisiert ein neues Git-Repository          | `git init`                                         |
| `git clone <URL>`           | Klonen eines vorhandenen Repositorys            | `git clone https://github.com/Benutzer/Repository` |
| `git add <Datei(en)>`      | Fügt Änderungen im Arbeitsverzeichnis zum Index (Staging-Bereich) hinzu | `git add datei.txt`                             |
| `git commit -m "<Nachricht>"` | Committet den Index in das Repository mit einer Nachricht | `git commit -m "Initialer Commit"`             |
| `git status`                | Zeigt den Status von Dateien im Arbeitsverzeichnis und Index an | `git status`                                      |
| `git log`                   | Zeigt eine Liste der Commit-Historie an        | `git log`                                          |
| `git pull`                  | Holt Änderungen vom Remote-Repository und führt ein Merge durch | `git pull origin master`                          |
| `git push <Remote> <Branch>`| Aktualisiert das Remote-Repository mit lokalen Commits | `git push origin master`                          |
| `git branch`                | Zeigt eine Liste der vorhandenen Branches an    | `git branch`                                      |
| `git checkout <Branch>`     | Wechselt zu einem anderen Branch                | `git checkout feature-branch`                     |
| `git merge <Branch>`        | Führt einen Merge eines anderen Branches in den aktuellen Branch durch | `git merge feature-branch`                        |
| `git remote add <Name> <URL>` | Fügt einem lokalen Repository eine Verbindung zu einem Remote-Repository hinzu | `git remote add origin https://github.com/Benutzer/Repository.git` |
| `git fetch <Remote>`        | Holt Änderungen vom Remote-Repository, lädt sie aber nicht herunter | `git fetch origin`                                |
| `git diff`                  | Zeigt die Unterschiede zwischen Arbeitsverzeichnis und Index an | `git diff`                                        |
| `git tag <Name>`            | Erstellt einen Tag für einen bestimmten Commit | `git tag v1.0`                                    |
| `git reset --hard <Commit>` | Setzt den HEAD auf einen bestimmten Commit zurück und verwirft lokale Änderungen | `git reset --hard HEAD~1`                         |
| `git revert <Commit>`       | Revertiert einen bestimmten Commit und erstellt einen neuen Commit | `git revert <Commit>`                             |
| `git stash`                 | Stasht Änderungen im Arbeitsverzeichnis, um später darauf zurückzugreifen | `git stash`                                       |
| `git cherry-pick <Commit>`  | Holt einen bestimmten Commit aus einem anderen Branch und führt ihn in den aktuellen Branch ein | `git cherry-pick <Commit>`                        |
| `git clean -f`              | Entfernt nicht nachverfolgte Dateien aus dem Arbeitsverzeichnis | `git clean -f`                                    |
| `git remote remove <Name>`  | Entfernt die Verbindung zu einem Remote-Repository | `git remote remove origin`                        |

<br><br><br>

## How to use SSO with GitHub:
### 1. Generate SSH key:

<code>id_rsa</code> should be the name of your newly generated SSH pub key.

<code>ssh-keygen -t rsa -b 4096 -C "user@gmail.com"</code><br>

### 2. Add SSH key to SSH-agent:

<code>eval "$(ssh-agent -s)"</code>

<code>ssh-add ~/.ssh/id_rsa</code>

### 3. Add SSH pub key to your GitHub-account:
- Go to your GitHub account settings
- Choose "SSH and GPG keys"
- Click on "New SSH key"
- Copy your SSH pub key and paste it into the field "key"
- Add a name for the key
- Click on "Add SSH key"


### 4. Configure git with your GitHub-account:
   
<code>git config --global user.name "Username"</code>

<code>git config --global user.email "user@gmail.com"</code>

### 5. Test the SSH connection:

<code>ssh -T git@github.com</code>
