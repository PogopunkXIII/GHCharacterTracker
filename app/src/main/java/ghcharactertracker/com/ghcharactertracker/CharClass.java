package ghcharactertracker.com.ghcharactertracker;

/**
 * Created by jesse.mailhot on 3/6/2018.
 */

public class CharClass {
    ClassName name;
    int[] healthVals;
    int[] lvlUpVals;

    public CharClass(ClassName name) {
        this.name = name;
        healthVals = CharClass.getHealthValues(this.name);
        lvlUpVals = new int[]{8, 9, 11, 12, 14, 15, 17, 18};
    }

    public static int[] getHealthValues(ClassName name) {
        int[] vals = null;

        switch(name) {
            case Brute:
            case Cragheart:
                vals = new int[]{10, 12, 14, 16, 18, 20, 22, 24, 26};
                break;
            case Scoundrel:
            case Tinkerer:
                vals = new int[]{8, 9, 11, 12, 14, 15, 17, 18, 20};
                break;
            case Mindthief:
            case Spellweaver:
                vals = new int[]{6, 7, 8, 9, 10, 11, 12, 13, 14};
                break;
        }

        return vals;
    }

    public static int[] getLvlUpValsValues(ClassName name) {
        int[] vals = null;

        switch(name) {
            default:
                vals = new int[]{0, 45, 95, 150, 210, 275, 345, 420, 500};
                break;
        }

        return vals;
    }

    public int getMaxHealth(int level) {
        return healthVals[level];
    }

    //returns the exp to get to the next level
    public int getLvlUpVal(int level) {
        return lvlUpVals[level];
    }
}