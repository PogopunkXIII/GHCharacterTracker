package ghcharactertracker.com.ghcharactertracker;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity {
    public static final String CHARACTER_INPUT = "com.ghcharctertracker.ghcharactertracker.CHARACTER_INPUT";
    private static final int NEW_CHARACTER_REQUEST_CODE = 0;
    private static final int EXISTING_CHARACTER_REQUEST_CODE = 1;
    Character savedChar = null;
    ArrayList<Character> characters = new ArrayList<>();
    ArrayAdapter<Character> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView charList = findViewById(android.R.id.list);

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                characters);
        setListAdapter(adapter);

        charList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> charList, View v, int position, long id) {
                Character selectedChar = (Character) charList.getItemAtPosition(position);
                savedChar = selectedChar;
                startCharacterActivity(selectedChar, false);
            }
        });
    }

    public void newCharacter(View v) {
        Character character = new Character(new CharClass(ClassName.Brute));
        startCharacterActivity(character, true);
    }

    private void startCharacterActivity(Character input, boolean newChar) {
        Intent characterIntent = new Intent(this, CharacterActivity.class);

        characterIntent.putExtra(CHARACTER_INPUT, input);

        if (newChar) {
            startActivityForResult(characterIntent, NEW_CHARACTER_REQUEST_CODE);
        } else {
            startActivityForResult(characterIntent, EXISTING_CHARACTER_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case NEW_CHARACTER_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) { addCharacter(data); }
                break;
            case EXISTING_CHARACTER_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) { saveCharacter(data); }
                break;
        }
    }

    private void addCharacter(Intent characterResult) {
        Character result = (Character) characterResult.getParcelableExtra(CharacterActivity.PLAYER_CHAR);
        characters.add(result);
        adapter.notifyDataSetChanged();
    }

    private void saveCharacter(Intent characterResult) {
        Character result = (Character) characterResult.getParcelableExtra(CharacterActivity.PLAYER_CHAR);
        characters.set(characters.indexOf(savedChar), result);
        adapter.notifyDataSetChanged();
    }
}
