package ghcharactertracker.com.ghcharactertracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;

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


        charVals.put(PlayerContract.PlayerEntry.COLUMN_NAME_PLAYER_NAME, newChar.getPlayerName());
        charVals.put(PlayerContract.PlayerEntry.COLUMN_NAME_CLASS_NAME, newChar.getClassName().toString());
        charVals.put(PlayerContract.PlayerEntry.COLUMN_NAME_LEVEL, newChar.getLevel());
        charVals.put(PlayerContract.PlayerEntry.COLUMN_NAME_EXP, newChar.getCurExp());
        charVals.put(PlayerContract.PlayerEntry.COLUMN_NAME_MONEY, newChar.getMoney());
        //charVals.put(PlayerContract.PlayerEntry.COLUMN_NAME_SCENARIO_INDEX, -1);

        long charId = db.insert(PlayerContract.PlayerEntry.TABLE_NAME, null, charVals);

        newChar.setId(charId);

        return newChar;
    }

    public Scenario addScenario(Scenario newScen) {
        ContentValues scenVals = new ContentValues();

        scenVals.put(PlayerContract.ScenarioEntry.COLUMN_NAME_LEVEL, newScen.getLevel());
        scenVals.put(PlayerContract.ScenarioEntry.COLUMN_NAME_HEALTH, newScen.getHealth());
        scenVals.put(PlayerContract.ScenarioEntry.COLUMN_NAME_EXP, newScen.getExp());
        scenVals.put(PlayerContract.ScenarioEntry.COLUMN_NAME_MONEY_TOKENS, newScen.getMoneyTokens());

        long scenId = db.insert(PlayerContract.ScenarioEntry.TABLE_NAME, null, scenVals);

        newScen.setId(scenId);

        return newScen;
    }

    public ArrayList<Character> getAllCharacters(ArrayList<Character> characters) {
        String charSelectQuery = "SELECT * FROM " + PlayerContract.PlayerEntry.TABLE_NAME;
        String scenSelectQuery = "SELECT ? FROM " + PlayerContract.ScenarioEntry.TABLE_NAME;

        Cursor c = db.rawQuery(charSelectQuery, null);

        if (c.moveToFirst()) {
            do {
                //there's gotta be a better way to unpack a shitton of data like this. if this
                //db had hundreds of columns I'd be fucked
                Character newChar = new Character();
                newChar.setId(c.getInt(
                        c.getColumnIndex("id")));
                newChar.setPlayerName(c.getString(
                        c.getColumnIndex(PlayerContract.PlayerEntry.COLUMN_NAME_PLAYER_NAME)));
                newChar.setClassName(c.getString(
                        c.getColumnIndex(PlayerContract.PlayerEntry.COLUMN_NAME_CLASS_NAME)));
                newChar.setLevel(c.getInt(
                        c.getColumnIndex(PlayerContract.PlayerEntry.COLUMN_NAME_LEVEL)));
                newChar.setCurExp(c.getInt(
                        c.getColumnIndex(PlayerContract.PlayerEntry.COLUMN_NAME_EXP)));
                newChar.setMoney(c.getInt(
                        c.getColumnIndex(PlayerContract.PlayerEntry.COLUMN_NAME_MONEY)));

                /*
                //try to grab the scenario that the character has stored
                String index = String.valueOf(c.getInt(
                        c.getColumnIndex(PlayerContract.PlayerEntry.COLUMN_NAME_SCENARIO_INDEX)));
                String[] args = new String[] {index};
                Cursor scenCursor = db.rawQuery(scenSelectQuery, args);

                //only unpack the scenario if there's one saved, if there isn't one saved then
                //this character has never played a scenario
                if (scenCursor.moveToFirst() && scenCursor.getCount() == 1) {
                    Scenario newScen = new Scenario(newChar.getMaxHealth(), 0, 0);

                    newScen.setId(scenCursor.getInt(
                            scenCursor.getColumnIndex("id")));
                    newScen.setHealth(scenCursor.getInt(
                            c.getColumnIndex(PlayerContract.ScenarioEntry.COLUMN_NAME_HEALTH)));
                    newScen.setExp(scenCursor.getInt(
                            c.getColumnIndex(PlayerContract.ScenarioEntry.COLUMN_NAME_EXP)));
                    newScen.setMoneyTokens(scenCursor.getInt(
                            c.getColumnIndex(PlayerContract.ScenarioEntry.COLUMN_NAME_MONEY_TOKENS)));
                    newScen.setLevel(c.getInt(
                            c.getColumnIndex(PlayerContract.ScenarioEntry.COLUMN_NAME_MONEY_TOKENS)));

                    newChar.setCurrentScenario(newScen);
                }
                */

                characters.add(newChar);
            } while(c.moveToNext());
        }

        return characters;
    }

    public void updateCharacter(Character inChar) {
        ContentValues vals = new ContentValues();

        vals.put(PlayerContract.PlayerEntry.COLUMN_NAME_PLAYER_NAME, inChar.getPlayerName());
        vals.put(PlayerContract.PlayerEntry.COLUMN_NAME_CLASS_NAME, inChar.getClassName().toString());
        vals.put(PlayerContract.PlayerEntry.COLUMN_NAME_LEVEL, inChar.getLevel());
        vals.put(PlayerContract.PlayerEntry.COLUMN_NAME_EXP, inChar.getCurExp());
        vals.put(PlayerContract.PlayerEntry.COLUMN_NAME_MONEY, inChar.getMoney());

        //we need to make sure that this character has played a scenario. the character
        //may not have a scenario if he hasn't played one yet
        if (inChar.getCurrentScenario() != null )
            vals.put(PlayerContract.PlayerEntry.COLUMN_NAME_SCENARIO_INDEX, inChar.getCurrentScenario().getId());

        String[] id = new String[] { String.valueOf(inChar.getId()) };

        db.update(PlayerContract.PlayerEntry.TABLE_NAME, vals, "id = ?", id);
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
