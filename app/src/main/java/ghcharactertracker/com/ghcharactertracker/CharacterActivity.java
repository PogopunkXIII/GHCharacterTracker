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
    EditText playerName, playerLevel, playerCurExp, playerMaxHealth, playerNextLvlExp, playerMoney;
    Spinner classNameSpinner;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_sheet);

        player = new Character(new CharClass(ClassName.Brute));

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
        int scenExp = data.getIntExtra(ScenarioActivity.SCENARIO_EXP, 0);
        int scenMoney = data.getIntExtra(ScenarioActivity.SCENARIO_MONEY, 0);

        player.addMoney(scenMoney);
        player.addExp(scenExp);

        updateUI();
    }

    public void newScenario(View v) {
        Intent newScenarioIntent = new Intent(this, ScenarioActivity.class);
        newScenarioIntent.putExtra(MAX_HEALTH, player.getMaxHealth());
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
