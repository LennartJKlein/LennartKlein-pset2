package nl.lennartklein.lennartklein_pset2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

public class StoryActivity extends AppCompatActivity {

    // Set global variables
    String story_text = null;
    String story_title = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        // Fetch story from intent
        Intent i = getIntent();
        story_text = i.getStringExtra("STORY_TEXT");
        story_title = i.getStringExtra("STORY_TITLE");

        TextView export_title = findViewById(R.id.title_story);
        export_title.setText(story_title);

        TextView export = findViewById(R.id.story_export);
        export.setText(Html.fromHtml(story_text));
    }

    // Share the story via a channel
    public void shareStory(View button) {
        String story_plain = story_text.replace("<b>", "*");
        story_plain = story_plain.replace("</b>", "*");

        String share_text = "Read this madlib I created!\n\n" +
                            "\"" + story_title + "\"\n\n" +
                            story_plain;

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, share_text);
        startActivity(Intent.createChooser(share, "Share my story on…"));
    }

    public void restartApp(View button) {
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
