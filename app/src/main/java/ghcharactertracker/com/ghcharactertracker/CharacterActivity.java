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
    public static final String MAX_HEALTH = "com.ghcharctertracker.ghcharactertracker.MAX_HEALTH";

    private static final int SCENARIO_REQUEST_CODE = 0;


    Character player;
    ScenarioModel currentScenario;
    EditText playerName, playerLevel, playerCurExp, playerMaxHealth, playerNextLvlExp, playerMoney;
    Spinner classNameSpinner;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_sheet);

        player = new Character(new CharClass(ClassName.Brute));
        currentScenario = new ScenarioModel(player.getMaxHealth(), 0, 0);

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
            currentScenario.setHealth(player.getMaxHealth());
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
            currentScenario.setHealth(maxHealth);
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
        playerLevel.setText(Integer.toString(player.getLevel()));
        playerCurExp.setText(Integer.toString(player.getCurExp()));
        playerMaxHealth.setText(Integer.toString(player.getMaxHealth()));
        playerNextLvlExp.setText(Integer.toString(player.getNextLevelExp()));
        playerMoney.setText(Integer.toString(player.getMoney()));
    }

    private void unpackScenarioData(Intent data) {

        boolean scenComp = data.getBooleanExtra(ScenarioActivity.SCENARIO_COMPLETE, false);

        if (scenComp) {
            int scenExp = data.getIntExtra(ScenarioActivity.SCENARIO_EXP, 0);
            int scenMoney = data.getIntExtra(ScenarioActivity.SCENARIO_MONEY, 0);

            unpackCompleteScenario(scenExp, scenMoney);
        }
        else {
            int scenLevel = data.getIntExtra(ScenarioActivity.SCENARIO_LEVEL, 0);
            int tempExp = data.getIntExtra(ScenarioActivity.SCENARIO_EXP, 0);
            int tempMoneyTokens = data.getIntExtra(ScenarioActivity.SCENARIO_MONEY, 0);

            saveIncompleteScenario(scenLevel, tempExp, tempMoneyTokens);
        }
    }

    private void unpackCompleteScenario(int newExp, int newMoney) {
        player.addMoney(newMoney);
        player.addExp(newExp);

        currentScenario = new ScenarioModel(player.getMaxHealth(), 0, 0);

        updateUI();
    }

    private void saveIncompleteScenario(int level, int exp, int moneyTokens) {
        currentScenario.setLevel(level);
        currentScenario.setExp(exp);
        currentScenario.setMoneyTokens(moneyTokens);
    }

    public void newScenario(View v) {
        Intent newScenarioIntent = new Intent(this, ScenarioActivity.class);
        newScenarioIntent.putExtra(ScenarioActivity.SCENARIO_LEVEL, currentScenario.getLevel());
        newScenarioIntent.putExtra(MAX_HEALTH, currentScenario.getHealth());
        newScenarioIntent.putExtra(ScenarioActivity.SCENARIO_EXP, currentScenario.getExp());
        newScenarioIntent.putExtra(ScenarioActivity.SCENARIO_MONEY, currentScenario.getMoneyTokens());
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
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updateModelClassName(ClassName.values()[i]);
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


}
