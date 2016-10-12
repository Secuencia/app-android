package moviles.isaacs.com.isaacs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import moviles.isaacs.com.isaacs.models.Content;
import moviles.isaacs.com.isaacs.models.Story;
import moviles.isaacs.com.isaacs.services.MyDBHandler;

public class MainActivity extends AppCompatActivity {

    MyDBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new MyDBHandler(this, null, null, 1); // This way you use and call the handler

        final Button button = (Button) findViewById(R.id.content_button);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                System.out.println("ENTRE");
                Story story = new Story();
                story.setTitle("Hola");
                story.setBrief("Nada");
                db.createStory(story);
            }
        });

    }


}
