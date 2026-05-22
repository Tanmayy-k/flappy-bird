# Flappy Bird — Java Swing Arcade Game

A polished desktop arcade game built using Java Swing and object-oriented programming principles.
This project recreates the classic Flappy Bird gameplay experience with smooth physics, responsive controls, dynamic difficulty scaling, sound effects, and persistent high-score tracking.

The game was designed with clean architecture and modular Java classes to demonstrate GUI development, event-driven programming, collision detection, and real-time game loop handling.

---

# Features

* Smooth bird movement with gravity and responsive jump mechanics
* Dynamic pipe generation with progressive difficulty scaling
* Scrolling background for a more immersive gameplay experience
* Start screen, pause/resume support, and polished game-over UI
* Persistent high score system stored locally using file handling
* Sound effects for jumping, scoring, and collisions
* Clean object-oriented architecture with modular Java classes
* Efficient pipe cleanup and collision handling for smoother performance

---

# Controls

| Key   | Action                      |
| ----- | --------------------------- |
| SPACE | Start game / Jump / Restart |
| P     | Pause or Resume the game    |

---

# Technologies Used

* Java 17+
* Java Swing (`javax.swing`)
* AWT Graphics (`java.awt`)
* `javax.imageio.ImageIO`
* `javax.sound.sampled`
* File I/O for local score persistence
* Object-Oriented Programming (OOP)

---

# Core Concepts Demonstrated

* Real-time game loop handling
* Event-driven programming
* Collision detection
* GUI rendering with Java Swing
* Object-oriented design
* Physics simulation using gravity and velocity
* File handling and local data persistence

---

# Project Structure

```bash
Main.java               # Application entry point and window setup
GamePanel.java          # Main game loop, rendering, input handling
Bird.java               # Bird movement, physics, animation, collision
Pipe.java               # Pipe spawning, movement, cleanup logic
SoundManager.java       # Sound effects management
ScoreManager.java       # Current score and high-score persistence

assets/
│
├── flappybird.png
├── flappybirdbg.png
├── toppipe.png
└── bottompipe.png

bestscore.dat           # Auto-generated high score file
```

---

# How to Run

## Using Terminal

Compile the project:

```bash
javac Main.java GamePanel.java Bird.java Pipe.java SoundManager.java ScoreManager.java
```

Run the game:

```bash
java Main
```

---

## Using VS Code

1. Open the project folder in VS Code
2. Install the Java Extension Pack
3. Run `Main.java` using the Run button
---

# About

Flappy Bird is a Java-based desktop arcade game project focused on demonstrating clean OOP structure, real-time rendering, GUI programming, and gameplay mechanics using Java Swing.
