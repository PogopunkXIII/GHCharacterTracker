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


    Character player;
    EditText playerName, playerLevel, playerCurExp, playerMaxHealth, playerNextLvlExp, playerMoney;
    Spinner classNameSpinner;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_sheet);

        Intent intent = getIntent();
        player = (Character) intent.getParcelableExtra(MainActivity.CHARACTER_INPUT);
        //player = new Character(new CharClass(ClassName.Brute));

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

        addSpinnerWatcher();
        addTextWatchers();
        updateUI();
    }

    private void updateModelClassName(ClassName className) {
        if (className != player.getClassName()) {
            player.setClassName(className);
            updateUI();
        }
    }

    private void updateModelPlayerName(String name) {
        if (!player.getPlayerName().equals(name)) {
            player.setPlayerName(name);
        }
    }

    private void updateModelPlayerLevel(int level) {
        if (player.getLevel() != level && level >= 0 && level <= 9) {
            player.setLevel(level);
            //player.getCurrentScenario().setHealth(player.getMaxHealth());
            updateUI();
        }
    }

    private void updateModelPlayerCurExp(int curExp) {
        if (player.getCurExp() != curExp) {
            player.setCurExp(curExp);
        }
    }

    private void updateModelPlayerMaxHealth(int maxHealth) {
        if (player.getMaxHealth() != maxHealth) {
            player.setMaxHealth(maxHealth);
            //player.getCurrentScenario().setHealth(maxHealth);
        }
    }

    private void updateModelPlayerNextLvlExp(int nextLvlExp) {
        if (player.getNextLevelExp() != nextLvlExp) {
            player.setNextLevelExp(nextLvlExp);
        }
    }

    private void updateModelPlayerMoney(int money) {
        if (player.getMoney() != money) {
            player.setMoney(money);
        }
    }

    private void updateUI() {
        playerName.setText(player.getPlayerName());
        playerLevel.setText(Integer.toString(player.getLevel()));
        playerCurExp.setText(Integer.toString(player.getCurExp()));
        playerMaxHealth.setText(Integer.toString(player.getMaxHealth()));
        playerNextLvlExp.setText(Integer.toString(player.getNextLevelExp()));
        playerMoney.setText(Integer.toString(player.getMoney()));

        classNameSpinner.setSelection(ClassName.valueOf(player.getClassName().toString()).ordinal());
    }

    private void unpackScenarioData(Intent data) {

        boolean scenComp = data.getBooleanExtra(ScenarioActivity.SCENARIO_COMPLETE, false);
        boolean scenSucc = data.getBooleanExtra(ScenarioActivity.SCENARIO_SUCCESSFUL, false);
        ScenarioModel resultScen = data.getParcelableExtra(ScenarioActivity.SCENARIO);

        if (scenComp) {
            unpackCompleteScenario(resultScen, scenSucc);
        } else {
            saveIncompleteScenario(resultScen);
        }
    }

    private void unpackCompleteScenario(ScenarioModel scenario, boolean success) {
        player.addMoney(scenario.getLootedMoney());

        if (success) {
            player.addExp(scenario.getTotalExp());
        } else {
            player.addExp(scenario.getExp());
        }

        player.setCurrentScenario(new ScenarioModel(player.getMaxHealth(), 0, 0));

        updateUI();
    }

    private void saveIncompleteScenario(ScenarioModel scenario) {
        player.setCurrentScenario(scenario);
    }

    public void newScenario(View v) {
        Intent newScenarioIntent = new Intent(this, ScenarioActivity.class);
        newScenarioIntent.putExtra(ScenarioActivity.SCENARIO, player.getCurrentScenario());
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

    @Override
    public void onBackPressed() {
        packupCharacterData();
    }

    private void packupCharacterData() {
        Intent characterResult = new Intent();

        characterResult.putExtra(PLAYER_CHAR, player);

        setResult(Activity.RESULT_OK, characterResult);

        this.finish();
    }


}
