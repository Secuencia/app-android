package moviles.isaacs.com.isaacs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void goToDBTesting(View view) {
        Intent goToDBTestingActivity = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(goToDBTestingActivity);
    }

    public void goToContents(View view) {
        Intent goToContentsActivity = new Intent(getApplicationContext(), ContentsActivity.class);
        startActivity(goToContentsActivity);
    }

    public void goToStories(View view) {
        Intent goToStoriesActivity = new Intent(getApplicationContext(), StoriesActivity.class);
        startActivity(goToStoriesActivity);
    }

    public void goToInput(View view) {
        Intent goToInputActivity = new Intent(getApplicationContext(), InputActivity.class);
        switch (view.getId()) {
            case R.id.text_input_button:
                goToInputActivity.putExtra("INPUT_TYPE", "text");
                break;
            case R.id.photo_input_button:
                goToInputActivity.putExtra("INPUT_TYPE", "photo");
                break;
            case R.id.audio_input_button:
                goToInputActivity.putExtra("INPUT_TYPE", "audio");
                break;
        }
        startActivity(goToInputActivity);
    }
}
