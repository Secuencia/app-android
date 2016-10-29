package moviles.isaacs.com.isaacs.modules.Home;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import moviles.isaacs.com.isaacs.modules.Contents.ContentsActivity;
import moviles.isaacs.com.isaacs.modules.Input.InputActivity;
import moviles.isaacs.com.isaacs.modules.MainActivity;
import moviles.isaacs.com.isaacs.modules.Stories.StoriesActivity;
import moviles.isaacs.com.isaacs.R;

public class HomeActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_ACCESS_EXTERNAL_STORAGE = 1;
    private static final int MY_PERMISSION_ACCESS_RECORD_AUDIO = 2;
    private Button photoButton;
    private Button audioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSION_ACCESS_EXTERNAL_STORAGE);
        }
        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.RECORD_AUDIO ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.RECORD_AUDIO},
                    MY_PERMISSION_ACCESS_RECORD_AUDIO);
        }
        setContentView(R.layout.activity_home);
        photoButton = (Button)findViewById(R.id.photo_input_button);
        audioButton = (Button)findViewById(R.id.audio_input_button);
        if(ContextCompat.checkSelfPermission( this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            photoButton.setEnabled(false);
            audioButton.setEnabled(false);
        }
        if(ContextCompat.checkSelfPermission( this, android.Manifest.permission.RECORD_AUDIO ) != PackageManager.PERMISSION_GRANTED){
            audioButton.setEnabled(false);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_ACCESS_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    photoButton.setEnabled(true);
                    audioButton.setEnabled(true);

                } else {

                    photoButton.setEnabled(false);
                    audioButton.setEnabled(false);
                }
                return;
            }
            case MY_PERMISSION_ACCESS_RECORD_AUDIO:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    audioButton.setEnabled(true);

                } else {
                    audioButton.setEnabled(false);
                }
                return;
        }
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
