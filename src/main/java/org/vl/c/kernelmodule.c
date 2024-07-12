/*
Schreiben Sie ein Kernel-Modul, das "Hello World" in das Kernel-Log schreibt.
Das Modul soll eine Gerätedatei (Character Device) zum Lesen bereitstellen.
Wenn man z. B. mit cat von der Gerätedatei liest, soll der String "Hello World" zurückgegeben werden.
Das Modul soll eine Gerätedatei (Character Device) zum Lesen und Schreiben bereitstellen.
Das Modul soll über einen Puffer mit fester Größe im Kernel-Space verfügen.
Wenn man einen String z. B. mit echo "Hallo" > /dev/geraetedatei schreibt, so soll der String im Puffer gespeichert werden.
Liest man anschließend von der Gerätedatei, so soll der Inhalt des Puffers zurückgegeben werden und der Puffer
danach geleert werden. Hier sind die Funktion copy_to_user und copy_from_user interessant.
*/

/*
Kompilieren des Moduls:
make

Laden des Moduls:
sudo insmod kernelmodule.ko

Überprüfen, ob das Modul geladen wurde:
lsmod | grep mein_modul

Mit Gerätedatei interagieren:
echo "Hallo" > /dev/mein_geraet
cat /dev/mein_geraet

Entladen des Moduls:
sudo rmmod kernelmodule
*/
#include <linux/module.h>
#include <linux/kernel.h>
#include <linux/fs.h>
#include <linux/uaccess.h>
#include <linux/device.h>

#define DEVICE_NAME "mein_geraet"
#define CLASS_NAME "meine_klasse"
#define BUFFER_SIZE 1024

MODULE_LICENSE("GPL");
MODULE_AUTHOR("Adrian Morgenthal");
MODULE_DESCRIPTION("Ein einfaches Kernel-Modul mit Character Device");
MODULE_VERSION("0.1");

static int major_number;
static char message[BUFFER_SIZE] = {0};
static short size_of_message;
static struct class* meingeraetClass = NULL;
static struct device* meingeraetDevice = NULL;

// Prototypen
static int dev_open(struct inode *, struct file *);
static int dev_release(struct inode *, struct file *);
static ssize_t dev_read(struct file *, char *, size_t, loff_t *);
static ssize_t dev_write(struct file *, const char *, size_t, loff_t *);

static struct file_operations fops =
{
   .open = dev_open,
   .read = dev_read,
   .write = dev_write,
   .release = dev_release,
};

static int __init mein_modul_init(void) {
    printk(KERN_INFO "Hello World: Modul wird geladen\n");

    // Registrieren des Character Device
    major_number = register_chrdev(0, DEVICE_NAME, &fops);
    if (major_number < 0) {
        printk(KERN_ALERT "Registrierung des Character Device fehlgeschlagen\n");
        return major_number;
    }
    printk(KERN_INFO "Registriert mit Major Number %d\n", major_number);

    // Registrieren der Device Class
    meingeraetClass = class_create(THIS_MODULE, CLASS_NAME);
    if (IS_ERR(meingeraetClass)) {
        unregister_chrdev(major_number, DEVICE_NAME);
        printk(KERN_ALERT "Fehler bei der Registrierung der Device Class\n");
        return PTR_ERR(meingeraetClass);
    }

    // Registrieren des Device Driver
    meingeraetDevice = device_create(meingeraetClass, NULL, MKDEV(major_number, 0), NULL, DEVICE_NAME);
    if (IS_ERR(meingeraetDevice)) {
        class_destroy(meingeraetClass);
        unregister_chrdev(major_number, DEVICE_NAME);
        printk(KERN_ALERT "Fehler bei der Erstellung des Device\n");
        return PTR_ERR(meingeraetDevice);
    }
    printk(KERN_INFO "Device-Klasse erstellt korrekt\n");

    return 0;
}

static void __exit mein_modul_exit(void) {
    device_destroy(meingeraetClass, MKDEV(major_number, 0));
    class_unregister(meingeraetClass);
    class_destroy(meingeraetClass);
    unregister_chrdev(major_number, DEVICE_NAME);
    printk(KERN_INFO "Goodbye World: Modul wird entladen\n");
}

static int dev_open(struct inode *inodep, struct file *filep) {
    printk(KERN_INFO "Device wurde geöffnet\n");
    return 0;
}

static ssize_t dev_read(struct file *filep, char *buffer, size_t len, loff_t *offset) {
    int error_count = 0;

    if (*offset > 0 || size_of_message == 0) {
        return 0;
    }

    error_count = copy_to_user(buffer, message, size_of_message);

    if (error_count == 0) {
        printk(KERN_INFO "Gesendet %d Zeichen an den Benutzer\n", size_of_message);
        *offset = size_of_message;
        size_of_message = 0;  // Puffer leeren
        return size_of_message;
    } else {
        printk(KERN_INFO "Fehler beim Senden der Daten an den Benutzer\n");
        return -EFAULT;
    }
}

static ssize_t dev_write(struct file *filep, const char *buffer, size_t len, loff_t *offset) {
    if (len > BUFFER_SIZE) {
        len = BUFFER_SIZE;
    }

    if (copy_from_user(message, buffer, len)) {
        return -EFAULT;
    }

    size_of_message = len;
    printk(KERN_INFO "Empfangen %zu Zeichen vom Benutzer\n", len);
    return len;
}

static int dev_release(struct inode *inodep, struct file *filep) {
    printk(KERN_INFO "Device wurde geschlossen\n");
    return 0;
}

module_init(mein_modul_init);
module_exit(mein_modul_exit);