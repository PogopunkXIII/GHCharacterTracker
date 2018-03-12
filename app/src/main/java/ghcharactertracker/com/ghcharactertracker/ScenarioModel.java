package ghcharactertracker.com.ghcharactertracker;

import android.view.View;
import android.widget.EditText;

/**
 * Created by jesse.mailhot on 3/6/2018.
 */

public class ScenarioModel {
    int level, health, exp, money = 0;
    private int totalExp, bonusExp, totalMoney, moneyMultiplier = 0;

    public ScenarioModel(int health, int exp, int money) {
        this.health = health;
        this.exp = exp;
        this.money = money;
    }

    public ScenarioModel() {
        this.health = 0;
        this.exp = 0;
        this.money = 0;
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
        this.money++;
        this.updateTotalMoney();

    }

    public void decMoney() {
        this.money--;
        this.updateTotalMoney();
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) { this.health = health; }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) { this.exp = exp; }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
        this.updateTotalMoney();
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        this.bonusExp = this.getScenarioBonusExp(this.level);
        this.moneyMultiplier = this.getScenarioMoneyMultiplier(this.level);
        this.updateTotalMoney();
        this.updateTotalExp();
    }

    private void updateTotalMoney() {
        this.totalMoney = money * moneyMultiplier;
    }

    private void updateTotalExp() {
        this.totalExp = exp + bonusExp;
    }

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

    public static int getScenarioBonusExp(int level) {
        return (4 + (level * 2));
    }

    public int getTotalMoney() {
        return totalMoney;
    }

    public int getTotalExp() {
        return totalExp;
    }
}
