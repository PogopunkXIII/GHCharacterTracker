package ghcharactertracker.com.ghcharactertracker;

public class ScenarioBuilder {
    int id, level, health, exp, mTokens;

    public ScenarioBuilder() {}

    public ScenarioBuilder id(int id) {
        this.id = id;

        return this;
    }

    public ScenarioBuilder level(int lvl) {
        this.level = lvl;

        return this;
    }

    public ScenarioBuilder health(int health) {
        this.health = health;

        return this;
    }

    public ScenarioBuilder exp(int exp) {
        this.exp = exp;

        return this;
    }

    public ScenarioBuilder moneyTokens(int tokens) {
        this.mTokens = tokens;

        return this;
    }

    public Scenario build() {
        Scenario newScen = new Scenario();

        newScen.setId(this.id);
        newScen.setLevel(this.level);
        newScen.setHealth(this.health);
        newScen.setExp(this.exp);
        newScen.setMoneyTokens(this.mTokens);

        return newScen;
    }
}
