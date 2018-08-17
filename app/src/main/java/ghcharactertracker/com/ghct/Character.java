package ghcharactertracker.com.ghct;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jesse.mailhot on 3/6/2018.
 */

public class Character implements Parcelable{
    private String playerName = "";
    private int level, maxHealth, curExp, nextLevelExp, money;
    private long id;
    private CharClass charClass;
    private Scenario currentScenario;

    public Character(CharClass charClass) {
        this.setPlayerName("");
        this.setCharClass(charClass);
        this.setLevel(0);
        this.setCurExp(0);
        this.setMoney(0);
        this.setMaxHealth(0);
        this.setNextLevelExp(0);

        currentScenario = null;
    }

    public Character() {
        this.setPlayerName("");
        this.setCharClass(new CharClass(ClassName.Brute));
        this.setLevel(0);
        this.setCurExp(0);
        this.setMoney(0);
        this.setMaxHealth(0);
        this.setNextLevelExp(0);

        currentScenario = null;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        this.setMaxHealth(this.charClass.getMaxHealthForLvl(this.getLevel()));
        this.setNextLevelExp(this.charClass.getLvlUpExp(this.getLevel()));
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

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public void setClassName(ClassName className) {
        charClass = new CharClass(className);
        this.setNextLevelExp(charClass.getLvlUpExp(this.getLevel()));
        this.setMaxHealth(charClass.getMaxHealthForLvl(this.getLevel()));
    }

    public void setClassName(String name) {
        this.charClass.setName(name);
    }

    public void setCharClass(CharClass charClass) { this.charClass = charClass;}

    public ClassName getClassName() { return charClass.getClassName(); }

    public String getPlayerName() { return playerName; }

    public Scenario getCurrentScenario() { return currentScenario; }

    public void setCurrentScenario(Scenario currentScenario) { this.currentScenario = currentScenario; }

    public void setPlayerName(String playerName) { this.playerName = playerName; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(playerName);
        out.writeString(charClass.getClassName().toString());
        out.writeLong(id);
        out.writeInt(level);
        out.writeInt(curExp);
        out.writeInt(money);

        if (currentScenario == null) {
            currentScenario = new Scenario(maxHealth, 0 ,0);
        }

        out.writeParcelable(currentScenario, 0);
    }

    public static final Parcelable.Creator<Character> CREATOR = new Parcelable.Creator<Character>() {
        public Character createFromParcel(Parcel in) {
            String playerName = in.readString();

            CharClass newCharClass = new CharClass(ClassName.valueOf(in.readString()));

            long id = in.readLong();
            int level = in.readInt();
            int curExp = in.readInt();
            int money = in.readInt();

            Scenario newScen = in.readParcelable(Scenario.class.getClassLoader());

            Character newChar = new Character(newCharClass);
            newChar.setId(id);
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
        this.setCurExp( scenExp + this.getCurExp());
    }

    public void addMoney(int scenMoney) { this.money += scenMoney; }

    @Override
    public String toString() {
        return playerName + '\n' + charClass.getClassName().toString();
    }
}
