package ghcharactertracker.com.ghcharactertracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    int health, exp, money = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void changeHealth(View v) {
        EditText healthText = (EditText) findViewById(R.id.scenarioHealth);
        health = Integer.parseInt(healthText.getText().toString());

        switch(v.getId()){
            case R.id.healthAdd:
                health++;
                break;
            case R.id.healthSub:
                health--;
                break;
        }

        healthText.setText(Integer.toString(health));
    }

    public void changeExp(View v) {
        EditText expText = (EditText) findViewById(R.id.scenarioExp);
        exp = Integer.parseInt(expText.getText().toString());

        switch(v.getId()){
            case R.id.expAdd:
                exp++;
                break;
            case R.id.expSub:
                exp--;
                break;
        }

        expText.setText(Integer.toString(exp));
    }

    public void changeMoney(View v) {
        EditText moneyText = (EditText) findViewById(R.id.scenarioMoney);
        money = Integer.parseInt(moneyText.getText().toString());

        switch(v.getId()){
            case R.id.moneyAdd:
                money++;
                break;
            case R.id.moneySub:
                money--;
                break;
        }

        moneyText.setText(Integer.toString(money));
    }


}
