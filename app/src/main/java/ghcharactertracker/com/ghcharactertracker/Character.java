package ghcharactertracker.com.ghcharactertracker;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jesse.mailhot on 3/6/2018.
 */

public class Character implements Parcelable{
    String playerName;
    int level, maxHealth, curExp, nextLevelExp, money;
    CharClass charClass;
    ScenarioModel currentScenario;

    public Character(CharClass charClass) {
        this.playerName = "";
        this.charClass = charClass;
        this.level = 0;
        this.curExp = 0;
        this.money = 0;
        this.maxHealth = 0;
        this.nextLevelExp = 0;
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

    public void setClassName(ClassName className) {
        charClass = new CharClass(className);
        this.nextLevelExp = charClass.getLvlUpVal(this.level);
        this.maxHealth = charClass.getMaxHealth(this.level);
    }

    public ClassName getClassName() { return charClass.getClassName(); }

    public String getPlayerName() { return playerName; }

    public ScenarioModel getCurrentScenario() { return currentScenario; }

    public void setCurrentScenario(ScenarioModel currentScenario) { this.currentScenario = currentScenario; }

    public void setPlayerName(String playerName) { this.playerName = playerName; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(playerName);
        out.writeString(charClass.name.toString());
        out.writeInt(level);
        out.writeInt(curExp);
        out.writeInt(money);

        out.writeInt(currentScenario.getHealth());
        out.writeInt(currentScenario.getExp());
        out.writeInt(currentScenario.getMoneyTokens());
        out.writeInt(currentScenario.getLevel());
    }

    public static final Parcelable.Creator<Character> CREATOR = new Parcelable.Creator<Character>() {
        public Character createFromParcel(Parcel in) {
            String playerName = in.readString();

            CharClass newCharClass = new CharClass(ClassName.valueOf(in.readString()));

            int level = in.readInt();
            int curExp = in.readInt();
            int money = in.readInt();

            ScenarioModel newScen = new ScenarioModel(in.readInt(), in.readInt(), in.readInt());
            newScen.setLevel(in.readInt());


            Character newChar = new Character(newCharClass);
            newChar.setPlayerName(playerName);
            newChar.setLevel(level);
            newChar.setCurExp(curExp);
            newChar.setMoney(money);
            newChar.setCurrentScenario(newScen);

            return newChar;
        }

        public Character[] newArray(int size) {
            return new Character[size];
        }
    };

    public void addExp(int scenExp) {
        this.curExp += scenExp;
    }

    public void addMoney(int scenMoney) {
        this.money += scenMoney;
    }
}
