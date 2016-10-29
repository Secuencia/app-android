package moviles.isaacs.com.isaacs.modules.Stories;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import moviles.isaacs.com.isaacs.R;
import moviles.isaacs.com.isaacs.models.Content;
import moviles.isaacs.com.isaacs.models.Story;
import moviles.isaacs.com.isaacs.services.MyDBHandler;

public class StoriesActivity extends AppCompatActivity {

    /*
    What is missing in this view:
        - More robust dialog for adding stories (add brief field)
        - Custom list view cells that are stylish and more functional
        - ActionListener on list view cells to see story detail
     */

    private String newStoryTitle = "";
    private String newStoryBrief = "";

    private ListView listStories;

    private MyDBHandler handler;

    private StoryDetailAdapter adapter;

    private ArrayList<Story> stories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories);

        setTitle("Stories");

        handler = new MyDBHandler(this, null, null, 1);

        listStories = (ListView)findViewById(R.id.listStories);

        stories = handler.getStories();
        adapter = new StoryDetailAdapter(this, stories);

        listStories.setAdapter(adapter);

        FloatingActionButton addStoryButton = (FloatingActionButton) findViewById(R.id.floatingActionButtonAddStory);
        addStoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                storyDetailsInput();
            }
        });
    }

    // Example found in StackOverflow
    private void storyDetailsInput(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");

        // Set up the input
        final EditText title = new EditText(this);
        final EditText brief = new EditText(this);

        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        title.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(title);

        brief.setInputType(InputType.TYPE_CLASS_TEXT);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                newStoryTitle = title.getText().toString();
                addStory();
                System.out.println(newStoryTitle);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void addStory() {
        Story story = new Story();
        story.setTitle(newStoryTitle); newStoryTitle = "";
        story.setBrief(newStoryBrief != "" ? newStoryBrief : "No brief"); newStoryTitle = "";
        handler.createStory(story);
        stories.add(story);
        adapter.notifyDataSetChanged();
    }

    public void deleteStory(View view){
        Button button = (Button)view.findViewById(R.id.delete);
        Story story = (Story)button.getTag(R.string.story);
        handler.removeStory(story);
        stories.remove(story);
        adapter.notifyDataSetChanged();
    }
}
