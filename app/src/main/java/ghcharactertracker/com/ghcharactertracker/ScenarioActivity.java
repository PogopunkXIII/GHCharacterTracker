package ghcharactertracker.com.ghcharactertracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by jesse.mailhot on 3/6/2018.
 */

public class ScenarioActivity extends AppCompatActivity {
    ScenarioModel scenario;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scenario_tracker);
        scenario = new ScenarioModel(0, 0 ,0);
    }

    public void changeHealth(View v) {
        EditText healthText = (EditText) findViewById(R.id.scenarioHealth);
        scenario.setHealth(Integer.parseInt(healthText.getText().toString()));

        switch(v.getId()){
            case R.id.healthAdd:
                scenario.incHealth();
                break;
            case R.id.healthSub:
                scenario.decHealth();
                break;
        }

        healthText.setText(Integer.toString(scenario.getHealth()));
    }

    public void changeExp(View v) {
        EditText expText = (EditText) findViewById(R.id.scenarioExp);
        scenario.setExp(Integer.parseInt(expText.getText().toString()));

        switch(v.getId()){
            case R.id.expAdd:
                scenario.incExp();
                break;
            case R.id.expSub:
                scenario.decExp();
                break;
        }

        expText.setText(Integer.toString(scenario.getExp()));
    }

    public void changeMoney(View v) {
        EditText moneyText = (EditText) findViewById(R.id.scenarioMoney);
        scenario.setMoney(Integer.parseInt(moneyText.getText().toString()));

        switch(v.getId()){
            case R.id.moneyAdd:
                scenario.incMoney();
                break;
            case R.id.moneySub:
                scenario.decMoney();
                break;
        }

        moneyText.setText(Integer.toString(scenario.getMoney()));
    }
}
