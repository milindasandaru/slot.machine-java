import java.util.Scanner;
import java.util.Random;

public class SlotMachineGame {
    // ANSI Color codes
    private static final String RESET = "\033[0m";
    private static final String RED = "\033[91m";
    private static final String GREEN = "\033[92m";
    private static final String YELLOW = "\033[93m";
    private static final String BLUE = "\033[94m";
    private static final String MAGENTA = "\033[95m";
    private static final String CYAN = "\033[96m";
    private static final String WHITE = "\033[97m";
    private static final String BOLD = "\033[1m";
    
    private SlotMachine machine;
    private Player player;
    private Scanner scanner;
    private boolean gameRunning = true;

    public SlotMachineGame() {
        this.machine = new SlotMachine();
        this.scanner = new Scanner(System.in);
        enableWindowsColors();
    }

    /**
     * Enable ANSI colors in Windows console
     */
    private void enableWindowsColors() {
        try {
            // Enable ANSI escape codes for Windows 10+
            new ProcessBuilder("cmd", "/c", "echo").inheritIO().start().waitFor();
        } catch (Exception e) {
            // Ignore if fails
        }
    }

    /**
     * Initialize the game by getting player name and starting balance
     */
    public void initialize() {
        clearScreen();
        printWelcomeBanner();
        
        System.out.print("\n" + CYAN + "Enter your name: " + RESET);
        String name = scanner.nextLine().trim();
        
        double balance = 0;
        boolean validBalance = false;
        while (!validBalance) {
            System.out.print("Enter starting balance ($): ");
            try {
                balance = Double.parseDouble(scanner.nextLine().trim());
                if (balance > 0) {
                    validBalance = true;
                } else {
                    System.out.println(RED + "✗ Please enter a positive amount!\n" + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "✗ Invalid input! Please enter a number.\n" + RESET);
            }
        }
        
        this.player = new Player(name, balance);
    }

    /**
     * Main game loop
     */
    public void play() {
        while (gameRunning && player.getBalance() > 0) {
            clearScreen();
            printHeader();
            displayGameStatus();
            printPayoutTable();
            
            System.out.print(YELLOW + "\nEnter bet amount (or 0 to quit): $" + RESET);
            
            try {
                double bet = Double.parseDouble(scanner.nextLine().trim());
                
                if (bet == 0) {
                    endGame();
                    break;
                }
                
                if (!player.canBet(bet)) {
                    System.out.println(RED + "✗ Invalid bet! You don't have enough balance.\n" + RESET);
                    pressEnterToContinue();
                    continue;
                }
                
                playRound(bet);
                
            } catch (NumberFormatException e) {
                System.out.println(RED + "✗ Invalid input! Please enter a number.\n" + RESET);
                pressEnterToContinue();
            }
        }
        
        if (player.getBalance() <= 0) {
            System.out.println("\n" + RED + BOLD + "💸 GAME OVER! You're out of money!" + RESET);
            System.out.println(YELLOW + "Better luck next time!" + RESET + "\n");
            pressEnterToContinue();
        }
    }

    /**
     * Play a single round
     */
    private void playRound(double bet) {
        player.setBet(bet);
        
        clearScreen();
        printHeader();
        System.out.println("\n" + YELLOW + "💰 Current Bet: $" + String.format("%.2f", bet) + RESET);
        System.out.println("\n" + CYAN + ">>> SPINNING THE REELS <<<" + RESET + "\n");
        
        // Animate spinning with better visuals
        animateAdvancedSpin();
        
        machine.spin();
        
        clearScreen();
        printHeader();
        System.out.println("\n" + YELLOW + "💰 Bet Placed: $" + String.format("%.2f", bet) + RESET);
        
        // Display slot machine with ASCII art
        System.out.println(machine.getSlotMachineArt());
        
        int winAmount = machine.checkWin((int) bet);
        
        if (winAmount > 0) {
            player.addWinnings(winAmount);
            printWinAnimation();
            System.out.println("\n" + GREEN + BOLD + "   ╔════════════════════════════════════╗" + RESET);
            System.out.println(GREEN + BOLD + "   ║  ★★★ CONGRATULATIONS! ★★★        ║" + RESET);
            System.out.println(GREEN + BOLD + "   ║                                    ║" + RESET);
            System.out.println(GREEN + BOLD + "   ║  💰 YOU WON: $" + String.format("%-18d", winAmount) + " ║" + RESET);
            System.out.println(GREEN + BOLD + "   ╚════════════════════════════════════╝" + RESET + "\n");
        } else {
            System.out.println("\n" + RED + "   ✗ No match. Better luck next time!" + RESET + "\n");
        }
        
        player.addSpins(1);
        pressEnterToContinue();
    }

    /**
     * Advanced spinning animation with multiple reels
     */
    private void animateAdvancedSpin() {
        String[] spinSymbols = {"🍒", "🍊", "🍋", "🔔", "7️⃣", "💎"};
        Random rand = new Random();
        
        System.out.println("   ┌─────────┐ ┌─────────┐ ┌─────────┐");
        System.out.println("   │         │ │         │ │         │");
        
        for (int frame = 0; frame < 15; frame++) {
            String s1 = spinSymbols[rand.nextInt(spinSymbols.length)];
            String s2 = spinSymbols[rand.nextInt(spinSymbols.length)];
            String s3 = spinSymbols[rand.nextInt(spinSymbols.length)];
            
            System.out.print(String.format("\r   │   %s   │ │   %s   │ │   %s   │", s1, s2, s3));
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        System.out.println("\n   │         │ │         │ │         │");
        System.out.println("   └─────────┘ └─────────┘ └─────────┘");
        System.out.println("\n" + YELLOW + "   >>> REELS STOPPING... <<<" + RESET + "\n");
        
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Print win animation with colors
     */
    private void printWinAnimation() {
        System.out.println(YELLOW + "\n   ★━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━★" + RESET);
        System.out.println(GREEN + "        🎉 W I N N E R ! 🎉" + RESET);
        System.out.println(YELLOW + "   ★━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━★" + RESET);
    }

    /**
     * Display current game status with colors
     */
    private void displayGameStatus() {
        System.out.println("\n" + CYAN + "┌─────────────────────────────────────────┐" + RESET);
        System.out.println(CYAN + "│" + RESET + " " + BOLD + "Player:" + RESET + " " + GREEN + player.getName() + RESET);
        System.out.println(CYAN + "│" + RESET + " " + BOLD + "Balance:" + RESET + " " + YELLOW + "$" + String.format("%.2f", player.getBalance()) + RESET);
        System.out.println(CYAN + "│" + RESET + " " + BOLD + "Spins:" + RESET + " " + WHITE + player.getSpinCount() + RESET);
        System.out.println(CYAN + "│" + RESET + " " + BOLD + "Wins:" + RESET + " " + GREEN + player.getTotalWins() + RESET);
        System.out.println(CYAN + "└─────────────────────────────────────────┘" + RESET);
    }

    /**
     * Display payout table
     */
    private void printPayoutTable() {
        System.out.println("\n" + MAGENTA + "╔══════════════ PAYOUT TABLE ══════════════╗" + RESET);
        System.out.println(MAGENTA + "║" + RESET + " 3x " + RED + "🍒" + RESET + "  = Bet x 10  │ 2x Match = Bet x 2  " + MAGENTA + "║" + RESET);
        System.out.println(MAGENTA + "║" + RESET + " 3x " + YELLOW + "🍊" + RESET + "  = Bet x 15  │ Higher symbol,       " + MAGENTA + "║" + RESET);
        System.out.println(MAGENTA + "║" + RESET + " 3x " + GREEN + "🍋" + RESET + "  = Bet x 20  │ better payout!       " + MAGENTA + "║" + RESET);
        System.out.println(MAGENTA + "║" + RESET + " 3x " + CYAN + "🔔" + RESET + "  = Bet x 25  │                      " + MAGENTA + "║" + RESET);
        System.out.println(MAGENTA + "║" + RESET + " 3x " + MAGENTA + "7️⃣" + RESET + "  = Bet x 50  │ Good Luck! 🍀        " + MAGENTA + "║" + RESET);
        System.out.println(MAGENTA + "║" + RESET + " 3x " + WHITE + "💎" + RESET + "  = Bet x 100 │ JACKPOT! 💰          " + MAGENTA + "║" + RESET);
        System.out.println(MAGENTA + "╚══════════════════════════════════════════╝" + RESET);
    }

    /**
     * End game and show statistics
     */
    private void endGame() {
        clearScreen();
        printHeader();
        System.out.println("\n" + CYAN + "╔═══════════════════════════════════════════╗" + RESET);
        System.out.println(CYAN + "║" + RESET + BOLD + "          📊 GAME STATISTICS 📊" + RESET + "             " + CYAN + "║" + RESET);
        System.out.println(CYAN + "╠═══════════════════════════════════════════╣" + RESET);
        System.out.println(CYAN + "║" + RESET + " Player: " + GREEN + player.getName() + RESET);
        System.out.println(CYAN + "║" + RESET + " Final Balance: " + YELLOW + "$" + String.format("%.2f", player.getBalance()) + RESET);
        System.out.println(CYAN + "║" + RESET + " Total Spins: " + WHITE + player.getSpinCount() + RESET);
        System.out.println(CYAN + "║" + RESET + " Total Wins: " + GREEN + player.getTotalWins() + RESET);
        
        if (player.getSpinCount() > 0) {
            double winRate = (double) player.getTotalWins() / player.getSpinCount() * 100;
            System.out.println(CYAN + "║" + RESET + " Win Rate: " + MAGENTA + String.format("%.1f", winRate) + "%" + RESET);
        }
        
        System.out.println(CYAN + "╚═══════════════════════════════════════════╝" + RESET);
        System.out.println("\n" + YELLOW + "Thanks for playing at Lucky Slots Casino! 🎰" + RESET);
        System.out.println(GREEN + "Come back soon! Good luck! ★" + RESET + "\n");
        gameRunning = false;
    }

    /**
     * Print game header with colors
     */
    private void printHeader() {
        System.out.println(YELLOW + "╔═══════════════════════════════════════════╗" + RESET);
        System.out.println(YELLOW + "║" + RESET + RED + "  ★  " + RESET + BOLD + "LUCKY SLOTS CASINO" + RESET + RED + "  ★  " + RESET + YELLOW + "v2.0       ║" + RESET);
        System.out.println(YELLOW + "╚═══════════════════════════════════════════╝" + RESET);
    }

    /**
     * Print welcome banner
     */
    private void printWelcomeBanner() {
        System.out.println(YELLOW + "\n╔═══════════════════════════════════════════╗" + RESET);
        System.out.println(YELLOW + "║" + RESET + "                                           " + YELLOW + "║" + RESET);
        System.out.println(YELLOW + "║" + RESET + RED + "  ★★★  " + RESET + BOLD + WHITE + "WELCOME TO" + RESET + RED + "  ★★★" + RESET + "              " + YELLOW + "║" + RESET);
        System.out.println(YELLOW + "║" + RESET + "                                           " + YELLOW + "║" + RESET);
        System.out.println(YELLOW + "║" + RESET + GREEN + "     L U C K Y   S L O T S" + RESET + "                " + YELLOW + "║" + RESET);
        System.out.println(YELLOW + "║" + RESET + CYAN + "         C A S I N O" + RESET + "                      " + YELLOW + "║" + RESET);
        System.out.println(YELLOW + "║" + RESET + "                                           " + YELLOW + "║" + RESET);
        System.out.println(YELLOW + "║" + RESET + MAGENTA + "  🎰 Spin to Win! Good Luck! 🎰" + RESET + "          " + YELLOW + "║" + RESET);
        System.out.println(YELLOW + "║" + RESET + "                                           " + YELLOW + "║" + RESET);
        System.out.println(YELLOW + "╚═══════════════════════════════════════════╝" + RESET + "\n");
    }

    /**
     * Wait for user to press Enter
     */
    private void pressEnterToContinue() {
        System.out.print(CYAN + "\nPress Enter to continue..." + RESET);
        scanner.nextLine();
    }

    /**
     * Clear the console screen
     */
    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Main method
     */
    public static void main(String[] args) {
        SlotMachineGame game = new SlotMachineGame();
        game.initialize();
        game.play();
        
        game.scanner.close();
    }
}
