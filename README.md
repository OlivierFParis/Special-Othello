# Special-Othello – Console-Based ASCII Game

## Overview
Othello (Reversi) implemented as a **console-based** Java game featuring **ASCII board rendering, AI opponents, save/load functionality, and custom game rules** (e.g., unplayable squares). The game supports both **human vs. human** and **human vs. AI** matches, and also includes a **debug mode** where AI competes against itself.

## Features
- **Play Against AI or a Friend** – Choose human vs. human or human vs. AI.
- **Multiple AI Difficulties** – AI adapts its strategy based on difficulty settings.
- **Save & Load Games** – Resume your game from a previous session.
- **ANSI Colors** – Board uses **color-coded pieces** (may not display correctly in some terminals).
- **Modified Ruleset** – The four center squares along the bottom row are **unplayable** (non-standard Othello rule).
- **Fast Facts** – Displays a random fun fact on game startup.
- **Concede & Stalemate Detection** – Players can concede, and the game detects stalemates.
- **AI vs. AI Debug Mode** – **Option 4 in the main menu** allows two AI of medium difficulty to play against each other.

## Installation & Running the Game
1. Clone the repository:
   ```sh
   git clone https://github.com/OlivierFParis/Special-Othello.git
   ```
2. Navigate to the source folder:
   ```sh
   cd Special-Othello/src/Othello
   ```
3. Compile and run the game:
   ```sh
   javac Driver.java
   java Driver
   ```
**Note:**  
- If using **Windows CMD**, ANSI colors may not display properly. Try using **Git Bash** or another ANSI-compatible terminal.
- Eclipse users can run `Driver.java` directly.

## Save Files
The `SaveFiles/` directory contains:
- **Preloaded board types** (`.txt` files).
- **User save files** (automatically generated when saving a game).
- Users can **delete old saves** or create new ones from the in-game menu.

## Modifications & Features
The original assignment required an ASCII-based Othello game with polymorphism. Key improvements include:

### **Driver Class:**
- Manages menu interactions, AI selection, and replay prompts.
- **Option 4 Debug Mode** – Allows two AI to play against each other (medium difficulty).

### **Game Class:**
- **load(int attempts)** method prevents infinite recursion when loading saves.
- **AIHumanDiff** differentiates human vs AI when loading a game.
- **Fast Facts** display a random fact on startup.

### **Board Class:**
- Enhanced **drawBoard()** for different board states.
- **countPieces()** tracks piece counts.
- **staleMate()** detects if neither player can move.

### **Player & AIPlayer Classes:**
- `commitMove()` validates and executes moves.
- AI logic varies based on difficulty.

### **PlayablePosition Class:**
- `convertPieces()` and `convertRange()` handle piece flipping.

### **Custom Exceptions:**
- `ConcedeException` (handles player resignation).
- `SaveMidGameException` (handles player saving requests).
- `ReturnToMainMenuException` (avoids infinite loops on invalid inputs).

## Class Overview
- **`Driver.java`** – Starts the game.
- **`Game.java`** – Handles turns, saving/loading, and game flow.
- **`Board.java`** – Manages board state and move validation.
- **`Player.java`** – Base class for players.
- **`AIPlayer.java`** – AI logic based on difficulty.
- **`Position.java` & `PlayablePosition.java`** – Handle move validation and piece flipping.

---

