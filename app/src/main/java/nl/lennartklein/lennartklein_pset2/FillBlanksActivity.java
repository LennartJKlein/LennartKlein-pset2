package nl.lennartklein.lennartklein_pset2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Random;

public class FillBlanksActivity extends AppCompatActivity {

    // Set global variables
    String story_file = null;
    String story_title = null;
    InputStream story_stream = null;
    Story story = null;
    EditText input_word = null;
    Button button_next = null;
    TextView text_remainder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_blanks);

        // Find the global variables
        input_word = findViewById(R.id.input_word);
        button_next = findViewById(R.id.button_next);
        text_remainder = findViewById(R.id.words_remaining);

        // Fetch chosen story file from intent
        Intent i = getIntent();
        story_file = i.getStringExtra("STORY_FILE");
        story_title = i.getStringExtra("STORY_TITLE");

        try {
            story_stream = getAssets().open(story_file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        story = new Story(story_stream);

        // Initiate first word input
        setRemainder();
        getNextWord();

        // Use the "done"-button in the keyboard to submit the input field
        input_word.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    button_next.performClick();
                    return true;
                }
                return false;
            }
        });
    }

    // Set the remaining words to fill in
    public void setRemainder() {
        text_remainder.setText(String.valueOf(story.getPlaceholderRemainingCount()));
    }

    // Ask user for a word
    public void getNextWord() {
        if (!story.isFilledIn()) {
            setRemainder();
            input_word.setHint("Enter a " + story.getNextPlaceholder());
        }
    }

    // Send the word that the user entered to the story
    public void sendWord(View v) {
        String word = input_word.getText().toString();
        if (!Objects.equals(word, "")) {
            story.fillInPlaceholder(word);

            // Ask for new word, but go to next page if all placeholders are filled in
            if (!story.isFilledIn()) {
                input_word.setText("");
                getNextWord();
            } else {
                // Create intent for the next activity
                Intent i = new Intent(this, StoryActivity.class);
                i.putExtra("STORY_TEXT", story.text);
                i.putExtra("STORY_TITLE", story_title);
                startActivity(i);

                // End this activity
                finish();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("TEXT", story.text);
        outState.putInt("FILLED_IN", story.filledIn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
        story.text = inState.getString("TEXT");
        story.filledIn = inState.getInt("FILLED_IN");
        setRemainder();
        getNextWord();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
