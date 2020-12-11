package worldofzuul;

public class PlayerState {
    // creates a Player for the game

    // if the player can sleep, canSleep is true and vice versa
    private boolean canSleep = false;
    // variable for players balance
    public int balance = 1000;
    // variable for players income
    public int income = 0;
    //variable for inventory
    public Inventory inventory;
    // variable for life Quality
    public int lifeQuality = 100;

    // creates a new inventory for the player
    public PlayerState(){
        inventory = new Inventory();
    }
    //chekcs if player can sleep
    public boolean isCanSleep() {
        return canSleep;
    }
    // returns player balance
    public int getBalance() {
        return balance;
    }
    //returns player income
    public int getIncome() {
        return income;
    }
    //returns player Life quality
    public int getLifeQuality() {
        return lifeQuality;
    }
    // sets player balance
    public void setBalance(int balance) {
        this.balance = balance;
    }
    // sets can sleep
    public void setCanSleep(boolean canSleep) {
        this.canSleep = canSleep;
    }
    // sets player life quality
    public void setLifeQuality(int lifeQuality) {
        this.lifeQuality = lifeQuality;
    }
    // sets player income
    public void setIncome(int income) {
        this.income = income;
    }
}
