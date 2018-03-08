package ghcharactertracker.com.ghcharactertracker;

import android.view.View;
import android.widget.EditText;

/**
 * Created by jesse.mailhot on 3/6/2018.
 */

public class ScenarioModel {
    int health, exp, money = 0;

    public ScenarioModel(int health, int exp, int money) {
        this.health = health;
        this.exp = exp;
        this.money = money;
    }

    public ScenarioModel() {
        this.health = 0;
        this.exp = 0;
        this.money =0;
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
    }

    public void decMoney() {
        this.money--;
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

    public void setMoney(int money) { this.money = money; }
}
