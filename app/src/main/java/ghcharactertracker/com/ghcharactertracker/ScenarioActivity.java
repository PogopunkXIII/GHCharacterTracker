package ghcharactertracker.com.ghcharactertracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * Created by jesse.mailhot on 3/6/2018.
 */

public class ScenarioActivity extends AppCompatActivity {
    public static final String SCENARIO_EXP = "com.ghcharactertracker.ghcharactertracker.SCENARIO_EXP";
    public static final String SCENARIO_MONEY = "com.ghcharactertracker.ghcharactertracker.SCENARIO_MONEY";
    ScenarioModel scenario;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scenario_tracker);

        Intent intent = getIntent();
        int maxHealth = intent.getIntExtra(CharacterActivity.MAX_HEALTH, 0);

        scenario = new ScenarioModel(maxHealth, 0 ,0);
        updateHealthUI();
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

        moneyText.setText(Integer.toString(scenario.getMoney()));
    }

    public void saveScenario(View v) {
        EditText scenarioLvl = (EditText) findViewById(R.id.scenarioLevel);
        CheckBox scenarioComp = (CheckBox) findViewById(R.id.scenarioCompleted);
        //TODO: Throw a dialog telling the user to input a scenario level
        if (scenarioLvl.getText().toString().isEmpty()) { return; }

        boolean scenComp = scenarioComp.isChecked();
        int scenLvl = Integer.parseInt(scenarioLvl.getText().toString());

        //setting the level will calculate
        scenario.setLevel(scenLvl);

        Intent scenarioResult = new Intent();

        scenarioResult.putExtra(SCENARIO_MONEY, scenario.getTotalMoney());

        if (scenComp) {
            scenarioResult.putExtra(SCENARIO_EXP, scenario.getTotalExp());
        }
        else {
            scenarioResult.putExtra(SCENARIO_EXP, scenario.getExp());
        }

        setResult(0, scenarioResult);

        this.finish();
    }
}
