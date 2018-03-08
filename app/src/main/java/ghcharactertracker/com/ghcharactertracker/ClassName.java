package ghcharactertracker.com.ghcharactertracker;

/**
 * Created by jesse.mailhot on 3/6/2018.
 */

public enum ClassName {
    Brute, Scoundrel, Mindthief, Spellweaver, Tinkerer, Cragheart;

    private final int value;
    private ClassName(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
