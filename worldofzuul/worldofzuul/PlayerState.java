package worldofzuul;

public class PlayerState {
    private boolean canSleep = false;
    public int balance = 1000;
    public int income = 0;
    public Inventory inventory;
    public int lifeQuality = 100;


    public PlayerState(){
        inventory = new Inventory();
    }

    public boolean isCanSleep() {
        return canSleep;
    }

    public int getBalance() {
        return balance;
    }
    public int getIncome() {
        return income;
    }

    public int getLifeQuality() {
        return lifeQuality;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setCanSleep(boolean canSleep) {
        this.canSleep = canSleep;
    }

    public void setLifeQuality(int lifeQuality) {
        this.lifeQuality = lifeQuality;
    }

    public void setIncome(int income) {
        this.income = income;
    }
}
