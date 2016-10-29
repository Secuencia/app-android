package moviles.isaacs.com.isaacs.modules.Stories;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import moviles.isaacs.com.isaacs.R;
import moviles.isaacs.com.isaacs.models.Content;
import moviles.isaacs.com.isaacs.models.Story;
import moviles.isaacs.com.isaacs.modules.Contents.ContentAdapter;
import moviles.isaacs.com.isaacs.modules.Contents.DividerItemDecoration;
import moviles.isaacs.com.isaacs.services.MyDBHandler;

/**
 * Created by sfrsebastian on 10/28/16.
 */

public class StoryDetailActivity extends AppCompatActivity {
    private Story story;
    private RecyclerView recyclerView;
    private ArrayList<Content> listItems;
    private ContentAdapter adapter;
    private MyDBHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_detail);
        Bundle extras = getIntent().getExtras();
        handler = new MyDBHandler(this, null, null, 1);
        if (extras != null) {
            story = (Story)extras.get("story");
            story = handler.getStory(story.get_id());
        }
        TextView title = (TextView)findViewById(R.id.story_title);
        TextView brief = (TextView)findViewById(R.id.story_brief);
        title.setText(story.getTitle());
        brief.setText(story.getBrief());
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(20, 1));
        setAllContents();

    }

    private void setAllContents(){
        listItems = story.getContents();
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(listItems.get(position).getType() == Content.PICTURE){
                    return 1;
                }
                return 2;
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ContentAdapter(this, listItems);
        recyclerView.setAdapter(adapter);
    }

    public void deleteContent(View view){
        Button button = (Button)view.findViewById(R.id.delete);
        Content content = (Content)button.getTag(R.string.content);
        handler.removeContentFromStory(content, story);
        listItems.remove(content);
        adapter.notifyDataSetChanged();
    }
}
