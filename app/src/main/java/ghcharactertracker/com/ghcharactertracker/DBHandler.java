package ghcharactertracker.com.ghcharactertracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by jesse.mailhot on 3/20/2018.
 */

public class DBHandler {
    private static DBHandler dbHandler;
    private SQLiteDatabase db;

    private DBHandler(Context context) {
        db = context.openOrCreateDatabase("DATABASE", Context.MODE_PRIVATE, null);

        db.execSQL(PlayerContract.PlayerEntry.OPEN_OR_CREATE_CHARACTERS);
        db.execSQL(PlayerContract.ScenarioEntry.OPEN_OR_CREATE_SCENARIOS);
    }

    public static DBHandler getDbHandler(Context context) {
        if(dbHandler == null)
            dbHandler = new DBHandler(context);

        return dbHandler;
    }

    public Character addCharacter(Character newChar){
        ContentValues charVals = new ContentValues();
        ContentValues scenVals = new ContentValues();

        charVals.put(PlayerContract.PlayerEntry.COLUMN_NAME_PLAYER_NAME, newChar.getPlayerName());
        charVals.put(PlayerContract.PlayerEntry.COLUMN_NAME_CLASS_NAME, newChar.getClassName().toString());
        charVals.put(PlayerContract.PlayerEntry.COLUMN_NAME_LEVEL, newChar.getLevel());
        charVals.put(PlayerContract.PlayerEntry.COLUMN_NAME_EXP, newChar.getCurExp());
        charVals.put(PlayerContract.PlayerEntry.COLUMN_NAME_MONEY, newChar.getMoney());

        Scenario newScen = newChar.getCurrentScenario();

        scenVals.put(PlayerContract.ScenarioEntry.COLUMN_NAME_LEVEL, newScen.getLevel());
        scenVals.put(PlayerContract.ScenarioEntry.COLUMN_NAME_HEALTH, newScen.getHealth());
        scenVals.put(PlayerContract.ScenarioEntry.COLUMN_NAME_EXP, newScen.getExp());
        scenVals.put(PlayerContract.ScenarioEntry.COLUMN_NAME_MONEY_TOKENS, newScen.getMoneyTokens());

        long scenId = db.insert(PlayerContract.ScenarioEntry.TABLE_NAME, null, scenVals);

        newScen.setId(scenId);
        newChar.setCurrentScenario(newScen);

        charVals.put(PlayerContract.PlayerEntry.COLUMN_NAME_SCENARIO_INDEX, scenId);

        long charId = db.insert(PlayerContract.PlayerEntry.TABLE_NAME, null, charVals);

        newChar.setId(charId);

        return newChar;
    }

    public void updateCharacter(Character inChar) {
        ContentValues vals = new ContentValues();

        vals.put(PlayerContract.PlayerEntry.COLUMN_NAME_PLAYER_NAME, inChar.getPlayerName());
        vals.put(PlayerContract.PlayerEntry.COLUMN_NAME_CLASS_NAME, inChar.getClassName().toString());
        vals.put(PlayerContract.PlayerEntry.COLUMN_NAME_LEVEL, inChar.getLevel());
        vals.put(PlayerContract.PlayerEntry.COLUMN_NAME_EXP, inChar.getCurExp());
        vals.put(PlayerContract.PlayerEntry.COLUMN_NAME_MONEY, inChar.getMoney());
        vals.put(PlayerContract.PlayerEntry.COLUMN_NAME_SCENARIO_INDEX, inChar.getCurrentScenario().getId());

        db.update(PlayerContract.PlayerEntry.TABLE_NAME, vals, "id = ?",
                new String[]{ String.valueOf(inChar.getId()) });
    }

    public void updateScenario(Scenario inScen) {
        ContentValues vals = new ContentValues();

        vals.put(PlayerContract.ScenarioEntry.COLUMN_NAME_LEVEL, inScen.getLevel());
        vals.put(PlayerContract.ScenarioEntry.COLUMN_NAME_HEALTH, inScen.getHealth());
        vals.put(PlayerContract.ScenarioEntry.COLUMN_NAME_EXP, inScen.getExp());
        vals.put(PlayerContract.ScenarioEntry.COLUMN_NAME_MONEY_TOKENS, inScen.getMoneyTokens());

        db.update(PlayerContract.ScenarioEntry.TABLE_NAME, vals, "id = ?",
                new String[]{ String.valueOf(inScen.getId()) });
    }

    public void deleteCharacter(Character inChar) {
        long charId = inChar.getId();
        long scenId = inChar.getCurrentScenario().getId();

        db.delete(PlayerContract.ScenarioEntry.TABLE_NAME, "id = ?", new String[]{String.valueOf(scenId)});
        db.delete(PlayerContract.PlayerEntry.TABLE_NAME, "id = ?", new String[]{String.valueOf(charId)});
    }
}
