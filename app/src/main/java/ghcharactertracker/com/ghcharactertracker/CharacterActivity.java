package ghcharactertracker.com.ghcharactertracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by jesse.mailhot on 3/6/2018.
 */

public class CharacterActivity extends AppCompatActivity {
    public static final String MAX_HEALTH = "com.ghcharctertracker.ghcharactertracker.MAX_HEALTH";
    private static final int SCENARIO_REQUEST_CODE = 0;

    Character player;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_sheet);

        Spinner classNameSpinner = (Spinner) findViewById(R.id.classNameSpinner);
        classNameSpinner.setAdapter(new ArrayAdapter<ClassName>(this,
                android.R.layout.simple_spinner_item,
                ClassName.values()));

        player = new Character(new CharClass(ClassName.Brute));
    }

    private void unpackScenarioData(Intent data) {
        int scenExp = data.getIntExtra(ScenarioActivity.SCENARIO_EXP, 0);
        int scenMoney = data.getIntExtra(ScenarioActivity.SCENARIO_MONEY, 0);

        player.addMoney(scenMoney);
        player.addExp(scenExp);
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
}
