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

Ordnerstruktur
* levels
    * *.lvl
* src
    * graphics
        * animation
            * Animation
            * AnimationController
        * ui
            * Ui
    * listener
        * KeyHandler
        * MouseHandler
    * main
        * Game
        * LevelData
        * Main
        * Timer
        * Utils
    * object
        * collision
            * Box
            * Collider
            * Collision
        * ghost
            * animation
                * GhostAnimationController
            * Ghost
        * pacman
            * animation
                * PacManAnimController
            * PacMan
        * Object
    * sprites
        * default
            * missingSprite
        * ghost
            * ghost_blue_one.png
            * ghost_blue_two.png
            * ghost_green_one.png
            * ghost_green_two.png
            * ghost_red_one.png
            * ghost_red_two.png
            * ghost_slow_blue_1.png
            * ghost_slow_blue_2.png
            * ghost_slow_white_1.png
            * ghost_slow_white_2.png
            * ghost_yellow_one.png
            * ghost_yellow_two.png
        * pacman
            * PacMan_closed_empowered_1.png
            * PacMan_closed_empowered_2.png
            * PacMan_closed.png
            * PacMan_open_down_empowered_1.png
            * PacMan_open_down_empowered_2.png
            * PacMan_open_down.png
            * PacMan_open_left_empowered_1.png
            * PacMan_open_left_empowered_2.png
            * PacMan_open_left.png
            * PacMan_open_right_empowered_1.png
            * PacMan_open_right_empowered_2.png
            * PacMan_open_right.png
            * PacMan_open_up_empowered_1.png
            * PacMan_open_up_empowered_2.png
            * PacMan_open_up.png
        * tiles
            * coin.png
            * door.png
            * empty.png
            * powerpill_1.png
            * powerpill_2.png
            * powerpill_3.png
            * powerpill_4.png
            * powerpill_5.png
            * wall.png
    
    * README.md

