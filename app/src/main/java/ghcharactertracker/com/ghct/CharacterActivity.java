package ghcharactertracker.com.ghct;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

import ghcharactertracker.com.ghct.customControls.LockableScrollView;

/**
 * Created by jesse.mailhot on 3/6/2018.
 */

public class CharacterActivity extends AppCompatActivity {
    public static final String PLAYER_CHAR = "com.ghcharctertracker.ghcharactertracker.PLAYER_CHAR";

    private static final int SCENARIO_REQUEST_CODE = 0;

    private Character character;
    private EditText playerName, playerLevel, playerCurExp, playerMaxHealth, playerNextLvlExp, playerMoney;
    private FloatingActionButton bruteButton, cragheartButton, spellweaverButton, mindthiefButton, scoundrelButton, tinkererButton, spearsButton, musicNoteButton, triforceButton;
    private ArrayList<FloatingActionButton> singleClassButtons;
    private FloatingActionMenu playerClassMenu;
    private LockableScrollView classScroller;
    private DBHandler dbHandler;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_sheet_2);

        Intent intent = getIntent();
        character = (Character) intent.getParcelableExtra(MainActivity.CHARACTER_INPUT);

        playerClassMenu = (FloatingActionMenu) findViewById(R.id.classSelect);
        classScroller = (LockableScrollView) findViewById(R.id.famScroller);

        bruteButton         = (FloatingActionButton) findViewById(R.id.bruteButton);
        cragheartButton     = (FloatingActionButton) findViewById(R.id.cragheartButton);
        spellweaverButton   = (FloatingActionButton) findViewById(R.id.spellweaverButton);
        mindthiefButton     = (FloatingActionButton) findViewById(R.id.mindthiefButton);
        scoundrelButton     = (FloatingActionButton) findViewById(R.id.scoundrelButton);
        tinkererButton      = (FloatingActionButton) findViewById(R.id.tinkererButton);
        spearsButton        = (FloatingActionButton) findViewById(R.id.spearsButton);
        musicNoteButton     = (FloatingActionButton) findViewById(R.id.musicNoteButton);
        triforceButton      = (FloatingActionButton) findViewById(R.id.triforceButton);

        singleClassButtons = new ArrayList<FloatingActionButton>();

        singleClassButtons.add(bruteButton);
        singleClassButtons.add(cragheartButton);
        singleClassButtons.add(spellweaverButton);
        singleClassButtons.add(mindthiefButton);
        singleClassButtons.add(scoundrelButton);
        singleClassButtons.add(tinkererButton);
        singleClassButtons.add(spearsButton);
        singleClassButtons.add(musicNoteButton);
        singleClassButtons.add(triforceButton);

        playerName = (EditText) findViewById(R.id.playerName);
        playerLevel = (EditText) findViewById(R.id.playerLevel);
        playerCurExp = (EditText) findViewById(R.id.playerCurrentExp);
        playerMaxHealth = (EditText) findViewById(R.id.playerMaxHealth);
        playerNextLvlExp = (EditText) findViewById(R.id.playerNextLvlExp);
        playerMoney = (EditText) findViewById(R.id.playerCurrentMoney);


        dbHandler = DBHandler.getDbHandler(this);

        addTextWatchers();
        addFAMWatchers();
        addFABWatchers();
        updateUI();
    }

    private void addFABWatchers() {
        for(FloatingActionButton fab : singleClassButtons) {
            fab.setOnClickListener(classSelectListener);
        }
    }

    private View.OnClickListener classSelectListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ClassName newClass = ClassName.Brute;

            switch (v.getId()) {
                case R.id.bruteButton:
                    newClass = ClassName.Brute;
                    break;
                case R.id.scoundrelButton:
                    newClass = ClassName.Scoundrel;
                    break;
                case R.id.cragheartButton:
                    newClass = ClassName.Cragheart;
                    break;
                case R.id.spellweaverButton:
                    newClass = ClassName.Spellweaver;
                    break;
                case R.id.mindthiefButton:
                    newClass = ClassName.Mindthief;
                    break;
                case R.id.tinkererButton:
                    newClass = ClassName.Tinkerer;
                    break;
                case R.id.spearsButton:
                    newClass = ClassName.Quartermaster;
                    break;
                case R.id.musicNoteButton:
                    newClass = ClassName.Soothesinger;
                    break;
                case R.id.triforceButton:
                    newClass = ClassName.Elementalist;
                    break;
            }

            updateModelClassName(newClass);
            updateFAMIcon(newClass);
            playerClassMenu.close(true);
        }
    };

        private void updateFAMIcon(ClassName newClass) {
            ImageView classFamImageView = playerClassMenu.getMenuIconView();
            Drawable icon = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_brute_black_36, null);

            switch(newClass){
                case Brute:
                    icon = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_brute_black_36, null);
                    break;
                case Scoundrel:
                    icon = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_scoundrel_black_36, null);
                    break;
                case Cragheart:
                    icon = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_cragheart_black_36, null);
                    break;
                case Spellweaver:
                    icon = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_spellweaver_black_36, null);
                    break;
                case Mindthief:
                    icon = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_mindthief_black_36, null);
                    break;
                case Tinkerer:
                    icon = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_tinkerer_black_36, null);
                    break;
                case Quartermaster:
                    icon = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_spears_black_36, null);
                    break;
                case Soothesinger:
                    icon = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_music_notes_black_36, null);
                    break;
                case Elementalist:
                    icon = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_triforce_black_36, null);
                    break;
            }

            classFamImageView.setImageDrawable(icon);
        }

    private void addFAMWatchers() {
        playerClassMenu.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                String text;
                if (opened) {
                    classScroller.setScrollable(true);
                } else {
                    classScroller.scrollTo(0,0);
                    classScroller.setScrollable(false);
                }
            }
        });
    }


    private void updateModelClassName(ClassName className) {
        if (className != character.getClassName()) {
            character.setClassName(className);
            updateUI();
            dbHandler.updateCharacter(character);
        }
    }

    private void updateModelPlayerName(String name) {
        if (!character.getPlayerName().equals(name)) {
            character.setPlayerName(name);
            dbHandler.updateCharacter(character);
        }
    }

    private void updateModelPlayerLevel(int level) {
        if (character.getLevel() != level && level >= 0 && level <= 9) {
            character.setLevel(level);
            updateUI();
            dbHandler.updateCharacter(character);
        }
    }

    private void updateModelPlayerCurExp(int curExp) {
        if (character.getCurExp() != curExp) {
            character.setCurExp(curExp);
            dbHandler.updateCharacter(character);
        }
    }

    private void updateModelPlayerMaxHealth(int maxHealth) {
        if (character.getMaxHealth() != maxHealth) {
            character.setMaxHealth(maxHealth);
            dbHandler.updateCharacter(character);
        }
    }

    private void updateModelPlayerNextLvlExp(int nextLvlExp) {
        if (character.getNextLevelExp() != nextLvlExp) {
            character.setNextLevelExp(nextLvlExp);
            dbHandler.updateCharacter(character);
        }
    }

    private void updateModelPlayerMoney(int money) {
        if (character.getMoney() != money) {
            character.setMoney(money);
            dbHandler.updateCharacter(character);
        }
    }

    private void updateUI() {
        playerName.setText(character.getPlayerName());
        playerLevel.setText(Integer.toString(character.getLevel()));
        playerCurExp.setText(Integer.toString(character.getCurExp()));
        playerMaxHealth.setText(Integer.toString(character.getMaxHealth()));
        playerNextLvlExp.setText(Integer.toString(character.getNextLevelExp()));
        playerMoney.setText(Integer.toString(character.getMoney()));
    }

    private void unpackScenarioData(Intent data) {

        boolean scenComp = data.getBooleanExtra(ScenarioActivity.SCENARIO_COMPLETE, false);
        boolean scenSucc = data.getBooleanExtra(ScenarioActivity.SCENARIO_SUCCESSFUL, false);
        Scenario resultScen = data.getParcelableExtra(ScenarioActivity.SCENARIO);

        if (scenComp) {
            unpackCompleteScenario(resultScen, scenSucc);
        } else {
            saveIncompleteScenario(resultScen);
        }
    }

    private void unpackCompleteScenario(Scenario scenario, boolean success) {
        character.addMoney(scenario.getLootedMoney());

        if (success) {
            character.addExp(scenario.getTotalExp());
        } else {
            character.addExp(scenario.getExp());
        }

        dbHandler.updateCharacter(character);

        //we need to make sure when we erase all the scenario data that we retain the
        //db index
        long id = scenario.getId();
        Scenario newScen = new Scenario(character.getMaxHealth(), 0, 0);
        newScen.setId(id);

        dbHandler.updateScenario(newScen);
        character.setCurrentScenario(newScen);

        updateUI();
    }

    private void saveIncompleteScenario(Scenario scenario) {
        character.setCurrentScenario(scenario);
        dbHandler.updateScenario(scenario);
    }

    public void newScenario(View v) {
        startScenarioActivity(character.getCurrentScenario());
    }

    private void startScenarioActivity(Scenario scen) {
        Intent newScenarioIntent = new Intent(this, ScenarioActivity.class);
        newScenarioIntent.putExtra(ScenarioActivity.SCENARIO, scen);
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

    private void addTextWatchers() {
        playerName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    updateModelPlayerName(editable.toString());
                }
            }
        });

        playerLevel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    updateModelPlayerLevel(Integer.parseInt(editable.toString()));
                }
            }
        });

        playerCurExp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    updateModelPlayerCurExp(Integer.parseInt(editable.toString()));
                }
            }
        });

        playerMaxHealth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    updateModelPlayerMaxHealth(Integer.parseInt(editable.toString()));
                }
            }
        });

        playerNextLvlExp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    updateModelPlayerNextLvlExp(Integer.parseInt(editable.toString()));
                }
            }
        });

        playerMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    updateModelPlayerMoney(Integer.parseInt(editable.toString()));
                }
            }
        });
    }


    @Override
    public void onBackPressed(){
        packupCharacterData();
    }

    private void packupCharacterData() {
        Intent characterResult = new Intent();

        characterResult.putExtra(PLAYER_CHAR, character);

        setResult(Activity.RESULT_OK, characterResult);

        this.finish();
    }


}
