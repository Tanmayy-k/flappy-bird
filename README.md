🐦 Flappy Bird — Java Swing Arcade Game

A polished desktop arcade game built using Java Swing and object-oriented programming principles.
This project recreates the classic Flappy Bird gameplay experience with smooth physics, responsive controls, dynamic difficulty scaling, sound effects, and persistent high-score tracking.

The game was designed with clean architecture and modular Java classes to demonstrate GUI development, event-driven programming, collision detection, and real-time game loop handling.

---

🎮 Features

* Smooth bird movement with gravity and responsive jump mechanics
* Dynamic pipe generation with progressive difficulty scaling
* Scrolling background for a more immersive gameplay experience
* Start screen, pause/resume support, and polished game-over UI
* Persistent high score system stored locally using file handling
* Sound effects for jumping, scoring, and collisions
* Clean object-oriented architecture with modular Java classes
* Efficient pipe cleanup and collision handling for smoother performance

---
🧠 Core Concepts Demonstrated
Object-Oriented Programming (OOP)
Event-driven programming
Collision detection
Real-time rendering
Java Swing GUI development
File handling & persistence
Timer-based game loop handling
Dynamic difficulty scaling
🛠️ Tech Stack
Category	Technology
Language	Java
GUI Framework	Java Swing
Graphics	AWT (java.awt)
Audio	javax.sound.sampled
Image Handling	ImageIO
Persistence	File I/O
Architecture	OOP-based modular design


📁 Project Structure
flappy-bird-java/
│
├── Main.java                # Application entry point
├── GamePanel.java           # Main game loop & rendering
├── Bird.java                # Bird physics and movement
├── Pipe.java                # Pipe spawning & collision
├── SoundManager.java        # Audio handling
├── ScoreManager.java        # Score & high-score persistence
│
├── assets/
│   ├── flappybird.png
│   ├── flappybirdbg.png
│   ├── toppipe.png
│   └── bottompipe.png
│
└── bestscore.dat            # Auto-generated high score file


⚙️ Gameplay Mechanics
SPACE Key Press
↓
Apply Upward Velocity
↓
Gravity Pulls Bird Down
↓
Pipes Move Horizontally
↓
Collision Detection Check
↓
├── SAFE → Continue Game
└── COLLISION
     ↓
  Game Over Screen
     ↓
Save High Score


🎯 Controls
Key	Action
SPACE	Start / Jump / Restart
P	Pause or Resume


▶️ How to Run
Compile
javac Main.java GamePanel.java Bird.java Pipe.java SoundManager.java ScoreManager.java
Run
java Main

👨‍💻 Developer
Tanmay Kshirsagar



🚀 About
A Java-based arcade game project focused on demonstrating clean OOP architecture, GUI development, game loop mechanics, and interactive desktop application design using Java Swing.
