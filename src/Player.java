public class Player {
    private String name;
    private double balance;
    private int totalBets = 0;
    private int totalWins = 0;
    private int spinCount = 0;

    public Player(String name, double startingBalance) {
        this.name = name;
        this.balance = startingBalance;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBet(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            totalBets++;
        }
    }

    public void addWinnings(double amount) {
        balance += amount;
        totalWins++;
    }

    public void addSpins(int count) {
        spinCount += count;
    }

    public int getSpinCount() {
        return spinCount;
    }

    public int getTotalBets() {
        return totalBets;
    }

    public int getTotalWins() {
        return totalWins;
    }

    public boolean canBet(double amount) {
        return amount > 0 && amount <= balance;
    }
}
