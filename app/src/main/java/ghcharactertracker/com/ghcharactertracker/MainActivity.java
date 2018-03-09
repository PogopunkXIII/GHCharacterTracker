package ghcharactertracker.com.ghcharactertracker;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class MainActivity extends ListActivity {
    ArrayList<String> characters = new ArrayList<String>();

    ArrayAdapter<String> adapter;

    int clickCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                characters);
        setListAdapter(adapter);
    }

    public void addItems(View v) {
        //Character character = new Character(new CharClass(ClassName.Brute));
        Intent characterIntent = new Intent(this, CharacterActivity.class);
        startActivity(characterIntent);



        //characters.add("Clicked: " + clickCounter++);
        //adapter.notifyDataSetChanged();
    }

}
