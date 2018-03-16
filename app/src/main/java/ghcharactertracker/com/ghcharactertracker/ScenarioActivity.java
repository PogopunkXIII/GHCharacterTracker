package ghcharactertracker.com.ghcharactertracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    ScenarioModel scenario;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scenario_tracker);

        Intent intent = getIntent();
        scenario = intent.getParcelableExtra(SCENARIO);

        updateUI();
    }

    public void changeHealth(View v) {
        switch(v.getId()){
            case R.id.healthAdd:
                scenario.incHealth();
                break;
            case R.id.healthSub:
                scenario.decHealth();
                break;
        }

        updateHealthUI();
    }

    public void changeExp(View v) {
        switch(v.getId()){
            case R.id.expAdd:
                scenario.incExp();
                break;
            case R.id.expSub:
                scenario.decExp();
                break;
        }

        updateExpUI();
    }

    public void changeMoney(View v) {
        switch(v.getId()){
            case R.id.moneyAdd:
                scenario.incMoney();
                break;
            case R.id.moneySub:
                scenario.decMoney();
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
        EditText healthText = (EditText) findViewById(R.id.scenarioHealth);

        healthText.setText(Integer.toString(scenario.getHealth()));
    }

    private void updateExpUI() {
        EditText expText = (EditText) findViewById(R.id.scenarioExp);

        expText.setText(Integer.toString(scenario.getExp()));
    }

    private void updateMoneyUI() {
        EditText moneyText = (EditText) findViewById(R.id.scenarioMoney);

        moneyText.setText(Integer.toString(scenario.getMoneyTokens()));
    }

    private void updateLevelUI() {
        EditText levelText = (EditText) findViewById(R.id.scenarioLevel);

        if (scenario.getLevel() > 0) {
            levelText.setText(Integer.toString(scenario.getLevel()));
        }
    }

    public void scenarioCompleted(View v) {
        saveScenarioData(true);
    }

    private void saveScenarioData(boolean scenarioCompleted) {
        EditText scenarioLvl = (EditText) findViewById(R.id.scenarioLevel);
        CheckBox scenarioSuccessful = (CheckBox) findViewById(R.id.scenarioSuccessful);

        if (scenarioLvl.getText().toString().isEmpty()) {
            Toast.makeText(this,"Please enter a scenario Level", Toast.LENGTH_LONG).show();
            return;
        }

        boolean scenSucc = scenarioSuccessful.isChecked();
        int scenLvl = Integer.parseInt(scenarioLvl.getText().toString());

        //setting the level will calculate
        scenario.setLevel(scenLvl);

        Intent scenarioResult = new Intent();


        scenarioResult.putExtra(SCENARIO_COMPLETE, scenarioCompleted);
        scenarioResult.putExtra(SCENARIO_SUCCESSFUL, scenSucc);
        scenarioResult.putExtra(SCENARIO, scenario);
        /*
        scenarioResult.putExtra(SCENARIO_LEVEL, scenario.getLevel());
        scenarioResult.putExtra(SCENARIO_HEALTH, scenario.getHealth());

        if (scenSucc) {
            scenarioResult.putExtra(SCENARIO_MONEY, scenario.getLootedMoney());
        }
        else {
            scenarioResult.putExtra(SCENARIO_MONEY, scenario.getMoneyTokens());
        }


        if (scenSucc) {
            scenarioResult.putExtra(SCENARIO_EXP, scenario.getTotalExp());
        }
        else {
            scenarioResult.putExtra(SCENARIO_EXP, scenario.getExp());
        }
        */

        setResult(Activity.RESULT_OK, scenarioResult);

        this.finish();
    }


    @Override
    public void onBackPressed() {
        saveScenarioData(false);
    }
}
