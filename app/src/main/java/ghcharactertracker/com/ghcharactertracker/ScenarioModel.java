package ghcharactertracker.com.ghcharactertracker;

import android.view.View;
import android.widget.EditText;

/**
 * Created by jesse.mailhot on 3/6/2018.
 */

public class ScenarioModel {
    int level, health, exp, moneyTokens = 0;
    private int totalExp, bonusExp, lootedMoney, moneyMultiplier = 0;

    public ScenarioModel(int health, int exp, int money) {
        this.health = health;
        this.exp = exp;
        this.moneyTokens = money;
    }

    public ScenarioModel() {
        this.health = 0;
        this.exp = 0;
        this.moneyTokens = 0;
    }

    public void incHealth() {
        this.health++;
    }

    public void decHealth() {
        this.health--;
    }

    public void incExp() {
        this.exp++;
    }

    public void decExp() {
        this.exp--;
    }

    public void incMoney() {
        this.moneyTokens++;
        this.updateLootedMoney();

    }

    public void decMoney() {
        this.moneyTokens--;
        this.updateLootedMoney();
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) { this.health = health; }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) { this.exp = exp; }

    public int getMoneyTokens() {
        return moneyTokens;
    }

    public void setMoneyTokens(int money) {
        this.moneyTokens = money;
        this.updateLootedMoney();
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        this.bonusExp = this.getScenarioBonusExp(this.level);
        this.moneyMultiplier = this.getScenarioMoneyMultiplier(this.level);
        this.updateLootedMoney();
        this.updateTotalExp();
    }

    private void updateLootedMoney() {
        this.lootedMoney = moneyTokens * moneyMultiplier;
    }

    private void updateTotalExp() { this.totalExp = exp + bonusExp; }

    public static int getScenarioBonusExp(int level) { return (4 + (level * 2)); }

    public int getLootedMoney() { return lootedMoney; }

    public int getTotalExp() { return totalExp; }

    public static int getScenarioMoneyMultiplier(int level) {
        switch (level) {
            default:
            case 0:
            case 1:
                return 2;
            case 2:
            case 3:
                return 3;
            case 4:
            case 5:
                return 4;
            case 6:
                return 5;
            case 7:
                return 6;
        }
    }
}
