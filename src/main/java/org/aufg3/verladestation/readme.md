### Aufgabe 1: Simulation einer Container-Verladestation (Semaphore und Lock)

Der Semaphore in jeder Verladerampe verhält sich wie eine Warteschlange, da er den Zugang zur Ressource koordiniert.

Im Erzeuger-Verbraucher-Problem gibt es eine explizite Warteschlange, in der die Erzeuger ihre Produkte ablegen und die
Verbraucher sie entnehmen. Die Verladerampen in diesem Fall die Verbraucher, die nur eine begrenzte Anzahl von
Containern gleichzeitig umschlagen können. Der Semaphore stellt sicher, dass immer nur ein LKW gleichzeitig an einer
Verladerampe arbeiten kann.