# Flappy Flight

A polished Java Swing arcade game built with clean architecture, smooth physics, and modern UI polish. Flappy Flight is an original desktop game experience featuring a responsive bird, scrolling background, progressive difficulty, sound feedback, and persistent high score tracking.

## Features

- Smooth bird movement with gravity and responsive jump physics
- Scrolling parallax background for a more polished look
- Dynamic pipe speed and gap reduction as the player scores points
- Start screen, pause/resume support, and animated game over display
- Persistent high score saved locally to `bestscore.dat`
- Sound effects for jump, scoring, and collision events
- Clean object-oriented design with separate classes for the game panel, entities, sound, and score logic

## Controls

- `SPACE` — Start game, jump during play, restart after game over
- `P` — Pause or resume the game

## Technologies Used

- Java 17+ (or compatible JDK)
- Java Swing (`javax.swing`)
- AWT graphics (`java.awt`)
- `javax.imageio.ImageIO` for image loading
- `javax.sound.sampled` for sound generation
- File I/O for high score persistence

## Folder Structure

- `Main.java` — application entry point and window setup
- `GamePanel.java` — game loop, rendering, input handling, and state management
- `Bird.java` — bird physics, animation, and collision bounds
- `Pipe.java` — pipe spawning, movement, collision, and cleanup
- `SoundManager.java` — lightweight sound generation for game events
- `ScoreManager.java` — current score tracking and best score persistence
- `bestscore.dat` — generated at runtime to store the player’s best score
- `flappybird.png` — main bird sprite
- `flappybirdbg.png` — scrolling background texture
- `toppipe.png` / `bottompipe.png` — pipe graphics

## How to Run

1. Open the project folder in your Java IDE or terminal.
2. Compile the Java files from the project root:
   ```bash
   javac Main.java GamePanel.java Bird.java Pipe.java SoundManager.java ScoreManager.java
   ```
3. Run the game:
   ```bash
   java Main
   ```

If you are using VS Code, run `Main.java` directly using the Java extension run button.

## Future Improvements

- Add animated bird wings with dedicated sprite frames
- Add sound asset files instead of generated tones
- Implement mobile-friendly touch or click controls
- Add menus for difficulty selection and sound toggles
- Add a moving ground platform and more visual layers
- Add particle effects and transition animations
