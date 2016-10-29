package moviles.isaacs.com.isaacs.modules;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import moviles.isaacs.com.isaacs.R;
import moviles.isaacs.com.isaacs.models.Story;
import moviles.isaacs.com.isaacs.services.MyDBHandler;

public class MainActivity extends AppCompatActivity {

    public MyDBHandler handler;

    public TextView textAction;
    public TextView textAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textAction = (TextView)findViewById(R.id.textView);
        textAll = (TextView)findViewById(R.id.textView2);
        handler = new MyDBHandler(this, null, null, 1);

    }

    public void buttonAdd(View view) {
        System.out.println("Time to add a story");
        textAction.setText("Adding");
        Story story = new Story();
        story.setTitle("My First story");
        story.setBrief("My Second Story");
        boolean created = handler.createStory(story);
        if (created) {System.out.println("Success!");} else {System.out.println("Failure!");}
    }

    public void buttonRemove(View view) {
        System.out.println("Time to remove a story");
        textAction.setText("Removing");
    }

    public void buttonViewAll(View view) {
        System.out.println("Time to view the stories");
        ArrayList<Story> stories = handler.getStories();
        for(int i = 0; i < stories.size(); i++){
            System.out.println(stories.get(i).toString());
        }
        textAction.setText("Getting all");
    }


}
