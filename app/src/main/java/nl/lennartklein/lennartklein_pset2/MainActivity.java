package nl.lennartklein.lennartklein_pset2;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // Set global variables
    String[] stories = null;
    String[] stories_titles = null;
    String[] stories_length = null;
    ListView list_stories = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the global elements
        stories = getResources().getStringArray(R.array.stories_filenames);
        stories_titles = getResources().getStringArray(R.array.stories_titles);
        stories_length = getResources().getStringArray(R.array.stories_length);
        list_stories = findViewById(R.id.list_stories);

        // Create a list of stories to choose
        populateList();
        registerClickCallback();
    }

    public void populateList() {
        ArrayAdapter<String> a = new ArrayAdapter<>(this, R.layout.list_item, stories_titles);
        list_stories.setAdapter(a);
    }

    public void registerClickCallback() {
        list_stories.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startStory(i);
            }
        });
    }

    public void startRandom(View v) {
        // Pick a random story from the array of story files
        Random random = new Random();
        int randomPick = random.nextInt(stories.length);

        startStory(randomPick);
    }

    public void startStory(int number) {
        // Create intent for the next activity
        Intent i = new Intent(this, FillBlanksActivity.class);
        i.putExtra("STORY_FILE", stories[number]);
        i.putExtra("STORY_TITLE", stories_titles[number]);
        startActivity(i);
    }
}
