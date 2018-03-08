package ghcharactertracker.com.ghcharactertracker;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jesse.mailhot on 3/6/2018.
 */

public class Character implements Parcelable{

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(level);
        out.writeInt(curExp);
        out.writeInt(money);
        //converts the enum of the class to the integer value
        out.writeInt(charClass.name.getValue());
    }

    public static final Parcelable.Creator<Character> CREATOR = new Parcelable.Creator<Character>() {
        public Character createFromParcel(Parcel in) {
            int level = in.readInt();
            int curExp = in.readInt();
            int money = in.readInt();
            //converts the integer value of the class name into the enum type
            CharClass newCharClass = new CharClass(ClassName.values()[in.readInt()]);

            Character newChar = new Character(newCharClass);
            newChar.setLevel(level);
            newChar.setCurExp(curExp);
            newChar.setMoney(money);

            return newChar;
        }

        public Character[] newArray(int size) {
            return new Character[size];
        }
    }
}
