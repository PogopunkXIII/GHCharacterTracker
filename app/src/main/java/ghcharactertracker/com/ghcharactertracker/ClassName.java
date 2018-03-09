package ghcharactertracker.com.ghcharactertracker;

/**
 * Created by jesse.mailhot on 3/6/2018.
 */

public enum ClassName {
    Brute("Brute"),
    Scoundrel("Scoundrel"),
    Mindthief("Minethief"),
    Spellweaver("Spellweaver"),
    Tinkerer("Tinkerer"),
    Cragheart("Cragheart");

    private String value;

    private ClassName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
