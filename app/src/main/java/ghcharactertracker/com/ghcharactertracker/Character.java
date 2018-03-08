package ghcharactertracker.com.ghcharactertracker;

/**
 * Created by jesse.mailhot on 3/6/2018.
 */

public class Character {

    int level, maxHealth, curExp, nextLevelExp, money;
    CharClass charClass;

    public Character(CharClass charClass) {
        this.charClass = charClass;
        this.level = 1;
        this.curExp = 0;
        this.money = 30;
        this.maxHealth = this.charClass.getMaxHealth(this.level);
        this.nextLevelExp = this.charClass.getLvlUpVal(this.level);
    }
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        this.maxHealth = this.charClass.getMaxHealth(this.level);
        this.nextLevelExp = this.charClass.getLvlUpVal(this.level);
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getCurExp() {
        return curExp;
    }

    public void setCurExp(int curExp) {
        this.curExp = curExp;
    }

    public int getNextLevelExp() {
        return nextLevelExp;
    }

    public void setNextLevelExp(int nextLevelExp) {
        this.nextLevelExp = nextLevelExp;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
