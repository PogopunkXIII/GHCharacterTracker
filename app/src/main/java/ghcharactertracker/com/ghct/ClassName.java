package ghcharactertracker.com.ghct;

/**
 * Created by jesse.mailhot on 3/6/2018.
 */

public enum ClassName {
    Brute("Brute"),
    Scoundrel("Scoundrel"),
    Mindthief("Mindthief"),
    Spellweaver("Spellweaver"),
    Tinkerer("Tinkerer"),
    Cragheart("Cragheart"),
    Quartermaster("Quartermaster"),
    Soothesinger("Soothesinger"),
    Elementalist("Elementalist"),
    BeastTyrant("BeastTyrant");

    private String value;

    private ClassName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
