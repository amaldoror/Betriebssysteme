#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>

#define NUM_ROUNDS 10 // Anzahl der Runden
#define TIMEOUT 30    // Zeitlimit für das Spiel in Sekunden

pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t cond = PTHREAD_COND_INITIALIZER;
int table; // Das Spielobjekt auf dem virtuellen Tisch (0: Schere, 1: Stein, 2: Papier)
int player1_wins = 0, player2_wins = 0, draws = 0; // Zähler für die Spielergebnisse

// Funktion, um eine zufällige Zahl zwischen 0 und 2 zu generieren
int get_random_choice() {
    return rand() % 3;
}

// Funktion, die das Spielobjekt auf den Tisch legt
void *player(void *id_ptr) {
    long id = (long)id_ptr;
    int choice;
    for (int i = 0; i < NUM_ROUNDS; i++) {
        choice = get_random_choice();
        pthread_mutex_lock(&mutex);
        table = choice;
        printf("Spieler %ld legt %s auf den Tisch.\n", id, (choice == 0) ? "Schere" : (choice == 1) ? "Stein" : "Papier");
        pthread_cond_signal(&cond); // Weckt den Schiedsrichter auf
        pthread_mutex_unlock(&mutex);
        sleep(1); // Simuliert Zeit für die Auswahl
    }
    pthread_exit(NULL);
}

// Funktion, die die Runden auswertet
void *referee() {
    int choice1, choice2;
    for (int i = 0; i < NUM_ROUNDS; i++) {
        pthread_mutex_lock(&mutex);
        while (table == -1) { // Warten auf Spieler
            pthread_cond_wait(&cond, &mutex);
        }
        choice1 = table;
        table = -1; // Tisch leeren
        while (table == -1) {
            pthread_cond_wait(&cond, &mutex);
        }
        choice2 = table;
        table = -1; // Tisch leeren
        pthread_mutex_unlock(&mutex);

        printf("Schiedsrichter: Spieler 1 wählt %s, Spieler 2 wählt %s.\n",
               (choice1 == 0) ? "Schere" : (choice1 == 1) ? "Stein" : "Papier",
               (choice2 == 0) ? "Schere" : (choice2 == 1) ? "Stein" : "Papier");

        // Auswertung
        if (choice1 == choice2)
            draws++;
        else if ((choice1 == 0 && choice2 == 2) || (choice1 == 1 && choice2 == 0) || (choice1 == 2 && choice2 == 1))
            player1_wins++;
        else
            player2_wins++;
    }
    pthread_exit(NULL);
}

int main() {
    pthread_t threads[3];
    srand(time(NULL));

    pthread_create(&threads[0], NULL, referee, NULL);
    pthread_create(&threads[1], NULL, player, (void *)1);
    pthread_create(&threads[2], NULL, player, (void *)2);

    sleep(TIMEOUT); // Warten auf Timeout

    // Beenden der Threads
    for (int i = 0; i < 3; i++) {
        pthread_cancel(threads[i]);
    }

    // Ausgabe der Ergebnisse
    printf("\nGesamtauswertung:\n");
    printf("Gesamtanzahl gespielter Runden: %d\n", NUM_ROUNDS);
    printf("Anzahl Unentschieden: %d\n", draws);
    printf("Anzahl Gewinne Spieler 1: %d\n", player1_wins);
    printf("Anzahl Gewinne Spieler 2: %d\n", player2_wins);

    pthread_mutex_destroy(&mutex);
    pthread_cond_destroy(&cond);

    return 0;
}
