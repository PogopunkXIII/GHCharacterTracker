package ghcharactertracker.com.ghcharactertracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by jesse.mailhot on 3/6/2018.
 */

public class CharacterActivity extends AppCompatActivity {
    public static final String PLAYER_CHAR = "com.ghcharctertracker.ghcharactertracker.PLAYER_CHAR";

    private static final int SCENARIO_REQUEST_CODE = 0;

    private Character character;
    private EditText playerName, playerLevel, playerCurExp, playerMaxHealth, playerNextLvlExp, playerMoney;
    private Spinner classNameSpinner;
    private DBHandler dbHandler;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_sheet_2);

        Intent intent = getIntent();
        character = (Character) intent.getParcelableExtra(MainActivity.CHARACTER_INPUT);

        /*
        classNameSpinner = (Spinner) findViewById(R.id.classNameSpinner);
        classNameSpinner.setAdapter(new ArrayAdapter<ClassName>(this,
                android.R.layout.simple_spinner_item,
                ClassName.values()));

        playerName = (EditText) findViewById(R.id.playerName);
        playerLevel = (EditText) findViewById(R.id.playerLevel);
        playerCurExp = (EditText) findViewById(R.id.playerCurrentExp);
        playerMaxHealth = (EditText) findViewById(R.id.playerMaxHealth);
        playerNextLvlExp = (EditText) findViewById(R.id.playerNextLvlExp);
        playerMoney = (EditText) findViewById(R.id.playerCurrentMoney);


        dbHandler = DBHandler.getDbHandler(this);

        addSpinnerWatcher();
        addTextWatchers();
        updateUI();
        */
    }

    /*
    private void updateModelClassName(ClassName className) {
        if (className != character.getClassName()) {
            character.setClassName(className);
            updateUI();
            dbHandler.updateCharacter(character);
        }
    }

    private void updateModelPlayerName(String name) {
        if (!character.getPlayerName().equals(name)) {
            character.setPlayerName(name);
            dbHandler.updateCharacter(character);
        }
    }

    private void updateModelPlayerLevel(int level) {
        if (character.getLevel() != level && level >= 0 && level <= 9) {
            character.setLevel(level);
            updateUI();
            dbHandler.updateCharacter(character);
        }
    }

    private void updateModelPlayerCurExp(int curExp) {
        if (character.getCurExp() != curExp) {
            character.setCurExp(curExp);
            dbHandler.updateCharacter(character);
        }
    }

    private void updateModelPlayerMaxHealth(int maxHealth) {
        if (character.getMaxHealth() != maxHealth) {
            character.setMaxHealth(maxHealth);
            dbHandler.updateCharacter(character);
        }
    }

    private void updateModelPlayerNextLvlExp(int nextLvlExp) {
        if (character.getNextLevelExp() != nextLvlExp) {
            character.setNextLevelExp(nextLvlExp);
            dbHandler.updateCharacter(character);
        }
    }

    private void updateModelPlayerMoney(int money) {
        if (character.getMoney() != money) {
            character.setMoney(money);
            dbHandler.updateCharacter(character);
        }
    }

    private void updateUI() {
        playerName.setText(character.getPlayerName());
        playerLevel.setText(Integer.toString(character.getLevel()));
        playerCurExp.setText(Integer.toString(character.getCurExp()));
        playerMaxHealth.setText(Integer.toString(character.getMaxHealth()));
        playerNextLvlExp.setText(Integer.toString(character.getNextLevelExp()));
        playerMoney.setText(Integer.toString(character.getMoney()));

        classNameSpinner.setSelection(ClassName.valueOf(character.getClassName().toString()).ordinal());
    }

    private void unpackScenarioData(Intent data) {

        boolean scenComp = data.getBooleanExtra(ScenarioActivity.SCENARIO_COMPLETE, false);
        boolean scenSucc = data.getBooleanExtra(ScenarioActivity.SCENARIO_SUCCESSFUL, false);
        Scenario resultScen = data.getParcelableExtra(ScenarioActivity.SCENARIO);

        if (scenComp) {
            unpackCompleteScenario(resultScen, scenSucc);
        } else {
            saveIncompleteScenario(resultScen);
        }
    }

    private void unpackCompleteScenario(Scenario scenario, boolean success) {
        character.addMoney(scenario.getLootedMoney());

        if (success) {
            character.addExp(scenario.getTotalExp());
        } else {
            character.addExp(scenario.getExp());
        }

        dbHandler.updateCharacter(character);

        //we need to make sure when we erase all the scenario data that we retain the
        //db index
        long id = scenario.getId();
        Scenario newScen = new Scenario(character.getMaxHealth(), 0, 0);
        newScen.setId(id);

        dbHandler.updateScenario(newScen);
        character.setCurrentScenario(newScen);

        updateUI();
    }

    private void saveIncompleteScenario(Scenario scenario) {
        character.setCurrentScenario(scenario);
        dbHandler.updateScenario(scenario);
    }

    public void newScenario(View v) {
        startScenarioActivity(character.getCurrentScenario());
    }

    private void startScenarioActivity(Scenario scen) {
        Intent newScenarioIntent = new Intent(this, ScenarioActivity.class);
        newScenarioIntent.putExtra(ScenarioActivity.SCENARIO, scen);
        startActivityForResult(newScenarioIntent, SCENARIO_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case SCENARIO_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) { unpackScenarioData(data); };
                break;
        }
    }

    private void addSpinnerWatcher() {
        classNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                updateModelClassName(ClassName.values()[position]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    private void addTextWatchers() {
        playerName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    updateModelPlayerName(editable.toString());
                }
            }
        });

        playerLevel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    updateModelPlayerLevel(Integer.parseInt(editable.toString()));
                }
            }
        });

        playerCurExp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    updateModelPlayerCurExp(Integer.parseInt(editable.toString()));
                }
            }
        });

        playerMaxHealth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    updateModelPlayerMaxHealth(Integer.parseInt(editable.toString()));
                }
            }
        });

        playerNextLvlExp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    updateModelPlayerNextLvlExp(Integer.parseInt(editable.toString()));
                }
            }
        });

        playerMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    updateModelPlayerMoney(Integer.parseInt(editable.toString()));
                }
            }
        });
    }
    */

    @Override
    public void onBackPressed() {
        packupCharacterData();
    }

    private void packupCharacterData() {
        Intent characterResult = new Intent();

        characterResult.putExtra(PLAYER_CHAR, character);

        setResult(Activity.RESULT_OK, characterResult);

        this.finish();
    }


}
