package moviles.isaacs.com.isaacs.modules.Stories;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import java.util.ArrayList;

import moviles.isaacs.com.isaacs.R;
import moviles.isaacs.com.isaacs.models.Content;
import moviles.isaacs.com.isaacs.models.Story;
import moviles.isaacs.com.isaacs.services.MyDBHandler;

/**
 * Created by sfrsebastian on 10/28/16.
 */

public class StorySelectActivity extends AppCompatActivity{
    private Content content;
    private ListView mListView;
    private ArrayList<Story> stories;
    private MyDBHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_select);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            content = (Content)extras.get("content");
        }
        handler = new MyDBHandler(this, null, null, 1);
        stories = handler.getStories();
        mListView = (ListView) findViewById(R.id.story_list);

        StorySelectAdapter adapter = new StorySelectAdapter(this, stories, content);
        mListView.setAdapter(adapter);
    }

    public void checkStory(View view){
        CheckBox check = (CheckBox) view.findViewById(R.id.check);
        Story story = (Story) check.getTag(R.string.story);
        if(check.isChecked()){
            handler.addContentToStory(content, story);
            check.setChecked(true);
        }
        else{
            handler.removeContentFromStory(content,story);
            check.setChecked(false);
        }
    }
}
