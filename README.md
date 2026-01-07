# 🎰 Lucky Slots Casino - Java Slot Machine Game

A fun and interactive slot machine game built with Java! Play with colorful animations, realistic slot machine visuals, and exciting gameplay.

## ✨ Features

- **Colorful Console UI** - Vibrant ANSI colors and ASCII art for an authentic casino feel
- **Animated Spinning Reels** - Watch the reels spin with smooth animations
- **Multiple Symbols** - 6 different symbols with varying payout multipliers
- **Win Detection** - Smart win detection for 3-of-a-kind and 2-of-a-kind matches
- **Player Statistics** - Track your wins, losses, and win rate
- **Payout Table** - Built-in guide showing all possible payouts
- **Windows Console Compatible** - Works perfectly on Windows 10+

## 🎮 How to Play

### Prerequisites
- Java JDK 8 or higher installed

### Running the Game

```bash
cd slot.machine-java\src
javac *.java
java -cp .. SlotMachineGame
```

Or from the root directory:

```bash
java -cp src SlotMachineGame
```

## 📊 Game Rules

### Symbols & Payouts
- **3x CHERRY** = Bet × 10
- **3x ORANGE** = Bet × 15
- **3x LEMON** = Bet × 20
- **3x BELL** = Bet × 25
- **3x SEVEN** = Bet × 50
- **3x DIAMOND** = Bet × 100 (JACKPOT!)
- **2x Any Match** = Bet × 2

### How It Works
1. Enter your name and starting balance
2. View the payout table
3. Place your bet
4. Watch the reels spin
5. Win big with matching symbols!

## 🏗️ Project Structure

```
slot.machine-java/
├── src/
│   ├── SlotMachineGame.java    # Main game controller
│   ├── SlotMachine.java        # Slot machine logic
│   └── Player.java             # Player management
└── README.md
```

## 🔧 Classes Overview

### SlotMachineGame
Main game controller handling:
- Game loop and player interaction
- Colorful UI with ANSI color codes
- Spinning animations
- Win/loss display

### SlotMachine
Core game logic:
- Reel spinning mechanism
- Symbol management
- Win detection algorithm
- Payout calculation

### Player
Player state management:
- Balance tracking
- Statistics (wins, spins, win rate)
- Bet validation

## 🎨 Customization

You can easily customize:
- Symbol names and payouts in `SlotMachine.java`
- Colors using ANSI codes in `SlotMachineGame.java`
- Animation speed by adjusting `Thread.sleep()` values

## 📝 Future Improvements

- [ ] Add sound effects
- [ ] Implement bonus rounds
- [ ] Add difficulty levels
- [ ] Implement leaderboard
- [ ] Add special symbols/wild cards
- [ ] Create GUI version

## 🎯 Version
v2.0 - Enhanced with colors, animations, and realistic slot machine visuals

**Have fun and good luck! 🍀💰**
