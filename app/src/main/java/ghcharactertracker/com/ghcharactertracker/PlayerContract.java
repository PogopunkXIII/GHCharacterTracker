package ghcharactertracker.com.ghcharactertracker;

import android.provider.BaseColumns;

/**
 * Created by jesse.mailhot on 3/19/2018.
 */

public final class PlayerContract {

    private PlayerContract () {}

    public static class PlayerEntry implements BaseColumns {
        public static final String TABLE_NAME = "characters";
        public static final String COLUMN_NAME_PLAYER_NAME = "playerName";
        public static final String COLUMN_NAME_CLASS_NAME = "className";
        public static final String COLUMN_NAME_LEVEL = "level";
        public static final String COLUMN_NAME_EXP = "curExp";
        public static final String COLUMN_NAME_MONEY = "curMoney";
        public static final String COLUMN_NAME_SCENARIO_INDEX = "scenarioID";
    }

    public static class ScenarioEntry implements BaseColumns {
        public static final String TABLE_NAME = "scenarios";
        public static final String COLUMN_NAME_LEVEL = "level";
        public static final String COLUMN_NAME_HEALTH = "currentHealth";
        public static final String COLUMN_NAME_EXP = "currentExp";
        public static final String COLUMN_NAME_MONEY_TOKENS = "moneyTokens";
    }
}