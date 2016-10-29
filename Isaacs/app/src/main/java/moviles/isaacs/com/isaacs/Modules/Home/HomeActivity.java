package moviles.isaacs.com.isaacs.Modules.Home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import moviles.isaacs.com.isaacs.Modules.Contents.ContentsActivity;
import moviles.isaacs.com.isaacs.Modules.Input.InputActivity;
import moviles.isaacs.com.isaacs.Modules.MainActivity;
import moviles.isaacs.com.isaacs.Modules.Stories.StoriesActivity;
import moviles.isaacs.com.isaacs.R;

public class HomeActivity extends AppCompatActivity {

    private EditText editText;
    private Button photoButton;
    private Button audioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        editText =  (EditText)findViewById(R.id.text_input_field);
        photoButton = (Button)findViewById(R.id.photo_input_button);
        audioButton = (Button)findViewById(R.id.audio_input_button);
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
            case R.id.text_input_field:
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
