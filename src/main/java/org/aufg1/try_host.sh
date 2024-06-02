#!/bin/bash

# Funktion zur Anzeige der Usage Message
usage() {
    echo "Usage: $0 [-h] | [-s <sec>] <hostname>|<IP-Address>"
    echo "Options:"
    echo "    -h: Show usage message"
    echo "    -s <sec>: Ping the host every <sec> seconds (default: 10 seconds)"
}

# Funktion zum Ausführen des Ping-Befehls und Auswerten des Rückgabewerts
ping_host() {
    local host = "$1"    # Der angegebene Host
    local interval = "$2"    # Das Intervall für den Ping

    # Unendliche Schleife für wiederholte Pings
    while true; do
	# Ausführen des Ping-Befehls und Überprüfen des Rückgabewerts
	if ping -c 1 "$host" &> /dev/null; then
	    echo "$host OK"    # Wenn der Ping erfolgreich ist
        else
	    echo "$host FAILED"    # Wenn der ping fehlschlägt
	fi
	sleep "$interval"    # Warten für das angegebene Intervall
    done
}

# Überprüfen der Anzahl der übergebenen Argumente
if [ $# -lt 1 ]; then
    usage
    exit 1
fi



# Verarbeiten der optionen
while getopts ":hs:" opt; do
    case $opt in
	-h)
	    usage
	    exit 0
	    ;;
	-s)
	    interval = $OPTARG
	    ;;
	*)
	    echo "Invalid option: $OPTARG"
	    usage
	    exit 1
	    ;;
    esac
done


# Variablen initialisieren
interval = 10    # Standard-Intervall für den Ping

# Verschieben der Argumentzeiger
shift $((OPTIND - 1))

# Überprüfen, ob ein Hostname oder eine IP-Adresse übergeben wurde
if [$# -ne 1 ]; then
    echo "Error: Hostname or IP address is required."
    usage
    exit 1
fi

# Hostname oder IP-Adresse extrahieren
host = "$1"

# Aufrufen der Funktion zum Ausführen des Ping-Befehls
ping_host "$host" "$interval"

exit 0
