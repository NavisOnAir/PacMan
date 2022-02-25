PacMan

This game is a try to recreate PacMan and learn the basics of 2d Java development on the way


Credits to RyiSnow on Youtube
His Videos of the playlist "How to Make a 2D Game in Java" helped me understand and implement a game loop and the implementation of game ticks and frames. My Animation class is inspired be his implementation of sprites as well.
Huge Thanks that people like him create this content for free.

Klassenbeschreibungen

### Game
Diese Klasse beinhaltet die Hauptfunktionen des Labyrinths und beinhaltet den Game loop. Sie bildet das Grundgerüst für alle Objekte, unter anderem Encodings für die tiles des Labyrinthes.

### LevelData
Hardcoded Level für den Fall, dass keine Leveldatei vorhanden ist.

### Main
Diese Klasse beinhaltet die main methods und erstellt das Fenster als JFrame. Sie initialisiert die Klasse Game und startet den game loop.

### Timer
Ein Timer, der unter anderem für die Spielzeit genutzt wird.

### Utils
Beinhaltet diverse Funktionen für andere Klassen, unter anderem das Lesen von Dateinen oder das Umwandeln von Strings in Leveldateien in 2d Arrays.

### Animation
Speichert eine Abfolge von Sprites um diese bei Bedarf in der richtigen Geschwindigkeit wieder zu geben.

### AnimationController
Oberklasse zur Verwaltung von Animationen für ein Object. Muss von einem Animation Controller für das spezifische Object geerbt und eingerichtet werden.

### Ui
Beinhalted das Zeichnen des kommpletten User Interfaces.

### KeyHandler
Verwaltet Nutzereingaben der Tastatur, also Pfeiltasteneingaben.

### MouseHandler
Verhaltet die Mauseingaben der Nutzers. Genutzt für Custom Buttons

### GhostAnimController
Erb von AnimationController und verwaltet die Animationen für alle Geister

### Ghost
Beinhaltet den Geist als Gegner und alle dazugehörigen Funktion mit eingeschlossen vom Bewegen und Zeichnen.

### PacManAnimController
Erb von AnimationController und verwaltet die Animationen für Pac Man.

### PacMan
Beinhaltet PacMan und alle dazugehörigen Funktionen mit eingeschlossen vom Bewegen und Zeichnen.

### Object
Oberklasse für alle bewgbaren Objecte.