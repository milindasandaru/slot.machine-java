import java.util.Random;

public class SlotMachine {
    // Colorful emoji symbols
    private static final String[] SYMBOLS = {"🍒", "🍊", "🍋", "🔔", "7️⃣", "💎"};
    private static final String[] SYMBOL_COLORS = {"\033[91m", "\033[93m", "\033[92m", "\033[96m", "\033[95m", "\033[97m"};
    private static final int[] PAYOUTS = {10, 15, 20, 25, 50, 100};
    private int[] reels = new int[3];
    private Random random = new Random();

    /**
     * Spin the slot machine reels
     */
    public void spin() {
        for (int i = 0; i < 3; i++) {
            reels[i] = random.nextInt(SYMBOLS.length);
        }
    }

    /**
     * Get the symbols displayed on the reels
     */
    public String[] getReels() {
        String[] result = new String[3];
        for (int i = 0; i < 3; i++) {
            result[i] = SYMBOLS[reels[i]];
        }
        return result;
    }

    /**
     * Check if the spin is a winner and calculate payout
     */
    public int checkWin(int bet) {
        // Three of a kind wins
        if (reels[0] == reels[1] && reels[1] == reels[2]) {
            int symbolIndex = reels[0];
            int multiplier = PAYOUTS[symbolIndex];
            return bet * multiplier;
        }
        // Two of a kind (partial win)
        else if ((reels[0] == reels[1]) || (reels[1] == reels[2]) || (reels[0] == reels[2])) {
            return bet * 2;
        }
        // No win
        return 0;
    }

    /**
     * Get colored symbols for display
     */
    public String[] getColoredReels() {
        String[] result = new String[3];
        for (int i = 0; i < 3; i++) {
            result[i] = SYMBOL_COLORS[reels[i]] + SYMBOLS[reels[i]] + "\033[0m";
        }
        return result;
    }

    /**
     * Get the display string for the reels
     */
    public String getDisplay() {
        String[] symbols = getColoredReels();
        return symbols[0] + " | " + symbols[1] + " | " + symbols[2];
    }

    /**
     * Get ASCII art representation of the slot machine
     */
    public String getSlotMachineArt() {
        String[] symbols = getColoredReels();
        StringBuilder art = new StringBuilder();
        
        art.append("\n");
        art.append("   ╔═══════════════════════════════════════════╗\n");
        art.append("   ║           \033[93m★ SLOT MACHINE ★\033[0m           ║\n");
        art.append("   ╠═══════════════════════════════════════════╣\n");
        art.append("   ║                                           ║\n");
        art.append("   ║     ┌─────────┐ ┌─────────┐ ┌─────────┐  ║\n");
        art.append("   ║     │         │ │         │ │         │  ║\n");
        art.append(String.format("   ║     │   %s   │ │   %s   │ │   %s   │  ║\n",
                symbols[0], symbols[1], symbols[2]));
        art.append("   ║     │         │ │         │ │         │  ║\n");
        art.append("   ║     └─────────┘ └─────────┘ └─────────┘  ║\n");
        art.append("   ║                                           ║\n");
        art.append("   ╚═══════════════════════════════════════════╝\n");
        
        return art.toString();
    }

    private String formatSymbolForBox(String coloredSymbol) {
        return coloredSymbol;
    }
}
