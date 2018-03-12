package ghcharactertracker.com.ghcharactertracker;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class MainActivity extends ListActivity {
    private static final int CHARACTER_REQUEST_CODE = 0;
    ArrayList<String> characterNames = new ArrayList<>();
    ArrayList<Character> characters = new ArrayList<>();

    ArrayAdapter<String> adapter;

    int clickCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                characterNames);
        setListAdapter(adapter);
    }

    public void addItems(View v) {
        //Character character = new Character(new CharClass(ClassName.Brute));
        Intent characterIntent = new Intent(this, CharacterActivity.class);
        startActivityForResult(characterIntent, CHARACTER_REQUEST_CODE);

        //characters.add("Clicked: " + clickCounter++);
        //adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CHARACTER_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    getCharacter(data);
                }
                break;
        }
    }

    private void getCharacter(Intent characterResult) {

    }


}
