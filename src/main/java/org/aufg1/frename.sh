#!/bin/bash

# -------------------------------------------------------------------------------------------

# Funktion zum Anhängen eines Strings an Dateinamen
add_string_to_filenames() {
    local string = "$1"			     # Den zu verwendenden String speichern
    for file in *; do
        if [ -f "$file" ]; then
	    new_name = "${file}${string}"    # Neuer Dateiname: Dateiname + String
	    mv "$file" "$new_name"           # Umbenennen der Datei
	    echo "Renamed $file to $new_name"
	fi
    done
}

# -------------------------------------------------------------------------------------------

# Funktion zum Entfernen eines angehängten Strings aus Dateinamen
remove_string_from_filenames() {
    local string = "$1"			     # Den zu entfernenden String speichern
    for file in *; do
	if [ -f "$file" ]; then
	    # Entfernen des Strings aus dem Dateinamen
	    new_name = "${file}${string}"
	    mv "$file" "$string"	    # Umbenennen der Datei
	    echo "Removed $string from $file. New name is $new_name"
	fi
    done
}

# -------------------------------------------------------------------------------------------

print_help() {
    echo "Usage: $0 <string> [OPTION]"
    echo "[-a]    Add a string to the filenames in the current directory"
    echo "[-r]    Remove a string from the filenames in the current directory"
}

# --------------------------------------MAIN------------------------------------------------- 

# Überprüfen, ob ein Argument übergeben wurde
if [ $# -ne 1 ]; then
	echo "Usage: $0 <string>"
	exit 1
fi


# Argumente extrahieren
option = "$1"
string = "$2"


# Je nach gewählter Option die entsprechende Funktion aufrufen
case $option in
    -a)
	add_string_to_filenames "$string"
	;;
    -r)
	remove_string_from_filenames "$string"
	;;
    *)
	echo "Unknown option: $option"
	print_help
	exit 1
	;;
esac

exit 0
# --------------------------------------END-------------------------------------------------- 
