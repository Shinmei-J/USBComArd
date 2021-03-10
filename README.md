# USBComArd

## Présentation
Ce code permet d'établir une connection entre une arduino et un appareil android.

Pour cela vous aurez besoin des éléments suivants :

* Une Arduino
* Un appareil Android 
* Un câble OTG (On the Go)
* Un câble USB pour pouvoir connecter son android en USB
* Un USB 2.0 de type A/B pour pouvoir connecter son arduino 
* Arduino IDE
* Android Studio

Ce code utilise une bibliothèque d'Omar Aflak :
https://github.com/OmarAflak/Arduino-Library
pour pouvoir établir une connection en Serial. Les instructions pour savoir comment utiliser la bibliothèque se trouve sur son lien.
d'un autre côté, on a besoin d'un morceau de code sur l'IDE d'arduino qui est le suivant 

```C
void setup() {
    Serial.begin(9600);
}

void loop() {
    if(Serial.available()){
        char c = Serial.read();
        Serial.print(c);
    }
}
```
