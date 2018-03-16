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
    private static final int CHARACTER_REQUEST_CODE = 0;
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
                startCharacterActivity(selectedChar);
            }
        });
    }

    public void newCharacter(View v) {
        Character character = new Character(new CharClass(ClassName.Brute));
        startCharacterActivity(character);
    }

    private void startCharacterActivity(Character input) {
        Intent characterIntent = new Intent(this, CharacterActivity.class);

        characterIntent.putExtra(CHARACTER_INPUT, input);

        startActivityForResult(characterIntent, CHARACTER_REQUEST_CODE);
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
        Character result = (Character) characterResult.getParcelableExtra(CharacterActivity.PLAYER_CHAR);
        characters.add(result);
        adapter.notifyDataSetChanged();
    }
}
