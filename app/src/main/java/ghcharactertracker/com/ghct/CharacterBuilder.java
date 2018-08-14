package ghcharactertracker.com.ghct;

public class CharacterBuilder {
    private String playerName, className;
    private int id, level, exp, money;

    public CharacterBuilder() {}

    public CharacterBuilder playerName(String pName) {
        this.playerName = pName;

        return this;
    }

    public CharacterBuilder className(String cName) {
        this.className = cName;

        return this;
    }

    public CharacterBuilder id(int id) {
        this.id = id;

        return this;
    }

    public CharacterBuilder level(int lvl) {
        this.level = lvl;

        return this;
    }

    public CharacterBuilder exp(int exp) {
        this.exp = exp;

        return this;
    }

    public CharacterBuilder money(int money) {
        this.money = money;

        return this;
    }

    public Character build() {
        Character newChar = new Character();

        newChar.setPlayerName(this.playerName);
        newChar.setClassName(this.className);
        newChar.setId(this.id);
        newChar.setLevel(this.level);
        newChar.setCurExp(this.exp);
        newChar.setMoney(this.money);

        return newChar;
    }
}
