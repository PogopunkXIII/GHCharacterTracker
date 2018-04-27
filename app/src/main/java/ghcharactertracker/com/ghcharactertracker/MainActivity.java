package ghcharactertracker.com.ghcharactertracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends ListActivity {
    public static final String CHARACTER_INPUT = "com.ghcharctertracker.ghcharactertracker.CHARACTER_INPUT";
    private static final String REQUEST_CODE = "com.ghcharctertracker.ghcharactertracker.REQUEST_CODE";
    private static final int NEW_CHARACTER_REQUEST_CODE = 0;
    private static final int EXISTING_CHARACTER_REQUEST_CODE = 1;

    private Character savedChar, charToDelete = null;
    private ArrayList<Character> characters = new ArrayList<>();
    private ArrayAdapter<Character> adapter;
    private DBHandler dbHandler;
    private boolean delete = false;

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


        charList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> charList, View view, int position, long id) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Are you sure you want to delete this character?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                MainActivity.this.deleteSelectedChar();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.cancel();
                            }
                        });

                AlertDialog rUSure = builder.create();
                rUSure.show();

                MainActivity.this.charToDelete = (Character) charList.getItemAtPosition(position);

                return true;
            }
        });


        dbHandler = DBHandler.getDbHandler(this);
        characters = dbHandler.getAllCharacters(characters);
        adapter.notifyDataSetChanged();
    }

    public void deleteSelectedChar() {
        characters.remove(charToDelete);
        dbHandler.deleteCharacter(charToDelete);
        adapter.notifyDataSetChanged();
    }

    public void newCharacter(View v) {
        Character character = new Character(new CharClass(ClassName.Brute));
        Scenario scenario = new Scenario();
        dbHandler.addCharacter(character);
        dbHandler.addScenario(scenario);
        character.setCurrentScenario(scenario);
        startCharacterActivity(character, true);
    }

    private void startCharacterActivity(Character input, boolean newChar) {
        Intent characterIntent = new Intent(this, CharacterActivity.class);

        characterIntent.putExtra(CHARACTER_INPUT, input);

        if (newChar) {
            characterIntent.putExtra(REQUEST_CODE, NEW_CHARACTER_REQUEST_CODE);
            startActivityForResult(characterIntent, NEW_CHARACTER_REQUEST_CODE);
        } else {
            characterIntent.putExtra(REQUEST_CODE, EXISTING_CHARACTER_REQUEST_CODE);
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
        dbHandler.updateCharacter(result);
        adapter.notifyDataSetChanged();
    }

    private void saveCharacter(Intent characterResult) {
        Character result = (Character) characterResult.getParcelableExtra(CharacterActivity.PLAYER_CHAR);
        characters.set(characters.indexOf(savedChar), result);
        dbHandler.updateCharacter(result);
        adapter.notifyDataSetChanged();
    }
}
