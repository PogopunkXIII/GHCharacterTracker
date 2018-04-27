package ghcharactertracker.com.ghcharactertracker;

/**
 * Created by jesse.mailhot on 3/6/2018.
 */

public class CharClass {
    private ClassName name;
    private int[] healthVals;
    private int[] lvlUpVals;

    public CharClass(ClassName name) {
        this.name = name;
        healthVals = CharClass.getHealthValues(this.name);
        lvlUpVals = CharClass.getLvlUpValsValues(this.name);
    }

    public ClassName getClassName() { return name; }

    public static int[] getHealthValues(ClassName name) {
        int[] vals = null;

        switch(name) {
            case Brute:
            case Cragheart:
                vals = new int[]{0, 10, 12, 14, 16, 18, 20, 22, 24, 26};
                break;
            case Scoundrel:
            case Tinkerer:
                vals = new int[]{0, 8, 9, 11, 12, 14, 15, 17, 18, 20};
                break;
            case Mindthief:
            case Spellweaver:
                vals = new int[]{0, 6, 7, 8, 9, 10, 11, 12, 13, 14};
                break;
        }

        return vals;
    }

    public void setName(ClassName name) { this.name = name; }
    public void setName(String name) { this.name = ClassName.valueOf(name); }
    public void setName(int i) { this.name = ClassName.values()[i]; }

    public static int[] getLvlUpValsValues(ClassName name) {
        int[] vals = null;

        switch(name) {
            default:
                vals = new int[]{0, 45, 95, 150, 210, 275, 345, 420, 500};
                break;
        }

        return vals;
    }

    public int getMaxHealthForLvl(int level) {
        return healthVals[level];
    }

    //returns the exp to get to the next level
    public int getLvlUpExp(int level) {
        return lvlUpVals[level];
    }
}
