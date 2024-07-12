/*
Übungsaufgabe

JoinAndSleep mit POSIX Threads 
Ziel der Aufgabe ist es drei Threads zu programmieren, die auf das Beenden des anderen Warten und dann eine Zeit schlafen.
Überlegen Sie sich eine geeignete Umsetzung in C! Falls ein Thread gegeben ist, so soll auf sein Ende gewartet werden.
Anschließend soll eine bestimmte Zeit geschlafen werden.
Fügen Sie zwischen allen Schritten Konsolenausgaben ein, um den Fortschritt zu kontrollieren.
Geben Sie hier immer auch den aktuellen Thread aus!

main() Methode
Erzeuge Thread 3: Er soll auf keinen Thread warten und dann 4000ms schlafen
Erzeuge Thread 2: Er soll auf Thread 3 warten und dann 3000ms schlafen
Erzeuge Thread 1: Er soll auf Thread 2 warten und dann 2000ms schlafen
Starten Sie Thread 1
Starten Sie Thread 2
Starten Sie Thread 3
*/

#include <stdio.h>
#include <pthread.h>
#include <unistd.h>

pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER; // Initialisierung des Mutex für die Thread-Synchronisation
pthread_cond_t cond = PTHREAD_COND_INITIALIZER;    // Initialisierung der Bedingungsvariable für die Thread-Synchronisation

// Funktion, die von den Threads ausgeführt wird
void *thread_function(void *arg) {
    int thread_num = *((int *)arg);    // Thread-Nummer extrahieren
    printf("Thread %d gestartet\n", thread_num);

    pthread_mutex_lock(&mutex);    // Mutex sperren, um kritischen Bereich zu betreten


    pthread_cond_wait(&cond, &mutex);    // Auf andere Threads warten

    pthread_mutex_unlock(&mutex);    // Mutex entsperren, um kritischen Bereich zu verlassen

    // Schlafen für eine bestimmte Zeit
    int sleep_time;
    switch (thread_num) {
        case 1:
            sleep_time = 2000;
            break;
        case 2:
            sleep_time = 3000;
            break;
        case 3:
            sleep_time = 4000;
            break;
        default:
            sleep_time = 0;
    }
    usleep(sleep_time * 1000);    // usleep verwendet Mikrosekunden

    printf("Thread %d ist aufgewacht\n", thread_num);

    pthread_exit(NULL);    // Thread beenden
}

int main() {
    pthread_t thread1, thread2, thread3;  // Thread-IDs für die drei Threads
    int thread_nums[3] = {1, 2, 3};       // Thread-Nummern für die Argumente der Threads

    // Erzeuge Thread 3, 2, 1
    pthread_create(&thread3, NULL, thread_function, &thread_nums[2]);
    pthread_create(&thread2, NULL, thread_function, &thread_nums[1]);
    pthread_create(&thread1, NULL, thread_function, &thread_nums[0]);

    printf("Starten Sie Thread 1\n");
    pthread_mutex_lock(&mutex);
    pthread_cond_signal(&cond); // Signalisiere, dass Thread 1 beginnen kann
    pthread_mutex_unlock(&mutex);

    usleep(1000); // Kleine Pause, um sicherzustellen, dass Thread 1 beginnt, bevor Thread 2 und Thread 3 gestartet werden

    printf("Starten Sie Thread 2\n");
    pthread_mutex_lock(&mutex);
    pthread_cond_signal(&cond); // Signalisiere, dass Thread 2 beginnen kann
    pthread_mutex_unlock(&mutex);

    usleep(1000); // Kleine Pause, um sicherzustellen, dass Thread 2 beginnt, bevor Thread 3 gestartet wird

    printf("Starten Sie Thread 3\n");
    pthread_mutex_lock(&mutex);
    pthread_cond_signal(&cond); // Signalisiere, dass Thread 3 beginnen kann
    pthread_mutex_unlock(&mutex);

    // Warte auf Threads
    pthread_join(thread1, NULL);
    pthread_join(thread2, NULL);
    pthread_join(thread3, NULL);

    return 0;
}
