package ghcharactertracker.com.ghct;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by jesse.mailhot on 3/6/2018.
 */

public class ScenarioActivity extends AppCompatActivity {
    public static final String SCENARIO = "com.ghcharactertracker.ghcharactertracker.SCENARIO";
    public static final String SCENARIO_COMPLETE = "com.ghcharactertracker.ghcharactertracker.SCENARIO_COMPLETE";
    public static final String SCENARIO_SUCCESSFUL = "com.ghcharactertracker.ghcharactertracker.SCENARIO_SUCCESSFUL";
    private EditText levelText, healthText, expText, moneyText;
    private Scenario scenario;
    private DBHandler dbHandler;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scenario_tracker);

        Intent intent = getIntent();
        scenario = intent.getParcelableExtra(SCENARIO);

        levelText = (EditText) findViewById(R.id.scenarioLevel);
        healthText = (EditText) findViewById(R.id.scenarioHealth);
        expText = (EditText) findViewById(R.id.scenarioExp);
        moneyText = (EditText) findViewById(R.id.scenarioMoney);

        dbHandler = DBHandler.getDbHandler(this);

        addTextWatchers();
        updateUI();
    }

    public void changeHealth(View v) {
        switch(v.getId()){
            case R.id.healthAdd:
                updateModelScenarioHealth(scenario.getHealth() + 1);
                break;
            case R.id.healthSub:
                updateModelScenarioHealth(scenario.getHealth() - 1);
                break;
        }

        updateHealthUI();
    }

    public void changeExp(View v) {
        switch(v.getId()){
            case R.id.expAdd:
                updateModelScenarioExp(scenario.getExp() + 1);
                break;
            case R.id.expSub:
                updateModelScenarioExp(scenario.getExp() - 1);
                break;
        }

        updateExpUI();
    }

    public void changeMoney(View v) {
        switch(v.getId()){
            case R.id.moneyAdd:
                updateModelScenarioMoney(scenario.getMoneyTokens() + 1);
                break;
            case R.id.moneySub:
                updateModelScenarioMoney(scenario.getMoneyTokens() - 1);
                break;
        }

        updateMoneyUI();
    }

    private void updateUI() {
        updateHealthUI();
        updateExpUI();
        updateMoneyUI();
        updateLevelUI();
    }

    private void updateHealthUI() {
        healthText.setText(Integer.toString(scenario.getHealth()));
    }

    private void updateExpUI() {
        expText.setText(Integer.toString(scenario.getExp()));
    }

    private void updateMoneyUI() {
        moneyText.setText(Integer.toString(scenario.getMoneyTokens()));
    }

    private void updateLevelUI() {
        if (scenario.getLevel() > 0) {
            levelText.setText(Integer.toString(scenario.getLevel()));
        }
    }

    private void updateModelScenarioLevel(int level) {
        if(scenario.getLevel() != level) {
            scenario.setLevel(level);
            dbHandler.updateScenario(scenario);
        }
    }

    private void updateModelScenarioHealth(int health) {
        if(scenario.getHealth() != health) {
            scenario.setHealth(health);
            dbHandler.updateScenario(scenario);
        }
    }

    private void updateModelScenarioExp(int exp) {
        if(scenario.getExp() != exp) {
            scenario.setExp(exp);
            dbHandler.updateScenario(scenario);
        }
    }

    private void updateModelScenarioMoney(int money) {
        if(scenario.getMoneyTokens() != money) {
            scenario.setMoneyTokens(money);
            dbHandler.updateScenario(scenario);
        }
    }

    private void addTextWatchers() {
        levelText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    updateModelScenarioLevel(Integer.parseInt(editable.toString()));
                }
            }
        });

        healthText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    updateModelScenarioHealth(Integer.parseInt(editable.toString()));
                }
            }
        });

        expText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    updateModelScenarioExp(Integer.parseInt(editable.toString()));
                }
            }
        });

        moneyText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    updateModelScenarioMoney(Integer.parseInt(editable.toString()));
                }
            }
        });
    }

    public void scenarioCompleted(View v) {
        saveScenarioData(true);
    }

    private void saveScenarioData(boolean scenarioCompleted) {
        //EditText scenarioLvl = (EditText) findViewById(R.id.levelText);
        CheckBox scenarioSuccessful = (CheckBox) findViewById(R.id.scenarioSuccessful);

        if (levelText.getText().toString().isEmpty()) {
            Toast.makeText(this,"Please enter a scenario Level", Toast.LENGTH_LONG).show();
            return;
        }

        boolean scenSucc = scenarioSuccessful.isChecked();

        Intent scenarioResult = new Intent();


        scenarioResult.putExtra(SCENARIO_COMPLETE, scenarioCompleted);
        scenarioResult.putExtra(SCENARIO_SUCCESSFUL, scenSucc);
        scenarioResult.putExtra(SCENARIO, scenario);

        setResult(Activity.RESULT_OK, scenarioResult);

        this.finish();
    }


    @Override
    public void onBackPressed() {
        saveScenarioData(false);
    }
}
