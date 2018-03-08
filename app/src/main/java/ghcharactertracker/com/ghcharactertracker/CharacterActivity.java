package ghcharactertracker.com.ghcharactertracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by jesse.mailhot on 3/6/2018.
 */

public class CharacterActivity extends AppCompatActivity {
    Character player;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
