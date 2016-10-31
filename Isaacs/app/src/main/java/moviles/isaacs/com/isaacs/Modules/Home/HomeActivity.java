package moviles.isaacs.com.isaacs.modules.Home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import moviles.isaacs.com.isaacs.modules.Contents.ContentsActivity;
import moviles.isaacs.com.isaacs.modules.Input.InputActivity;
import moviles.isaacs.com.isaacs.modules.Radar.RadarActivity;
import moviles.isaacs.com.isaacs.modules.Stories.StoriesActivity;
import moviles.isaacs.com.isaacs.R;

public class HomeActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks {

    private static final int MY_PERMISSION_ACCESS_EXTERNAL_STORAGE = 1;
    private static final int MY_PERMISSION_ACCESS_RECORD_AUDIO = 2;
    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 3;
    private GoogleApiClient mGoogleApiClient;
    private Location location;
    private ImageButton photoButton;
    private ImageButton audioButton;
    private Button radarButton;

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
        if ( ContextCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSION_ACCESS_FINE_LOCATION);
        }
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        setContentView(R.layout.activity_home);
        photoButton = (ImageButton)findViewById(R.id.photo_input_button);
        audioButton = (ImageButton)findViewById(R.id.audio_input_button);
        radarButton = (Button)findViewById(R.id.radar_button);
        if(ContextCompat.checkSelfPermission( this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            photoButton.setEnabled(false);
            audioButton.setEnabled(false);
        }
        if(ContextCompat.checkSelfPermission( this, android.Manifest.permission.RECORD_AUDIO ) != PackageManager.PERMISSION_GRANTED){
            audioButton.setEnabled(false);
        }
        if(ContextCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED){
            radarButton.setEnabled(false);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_ACCESS_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    photoButton.setEnabled(true);
                    audioButton.setEnabled(true);
                }
                else {
                    photoButton.setEnabled(false);
                    audioButton.setEnabled(false);
                }
                return;
            }
            case MY_PERMISSION_ACCESS_RECORD_AUDIO: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    audioButton.setEnabled(true);

                } else {
                    audioButton.setEnabled(false);
                }
                return;
            }
            case MY_PERMISSION_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    radarButton.setEnabled(true);

                } else {
                    audioButton.setEnabled(false);
                }
                return;
            }
        }
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        updateLocation();
    }

    private void updateLocation(){
        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSION_ACCESS_FINE_LOCATION);
        }
        location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e("Excepcion", "Suspended location service");
    }


    public void goToContents(View view) {
        Intent goToContentsActivity = new Intent(getApplicationContext(), ContentsActivity.class);
        startActivity(goToContentsActivity);
    }

    public void goToStories(View view) {
        Intent goToStoriesActivity = new Intent(getApplicationContext(), StoriesActivity.class);
        startActivity(goToStoriesActivity);
    }

    public void goToRadar(View view){
        updateLocation();
        Intent goToRadarActivity = new Intent(getApplicationContext(), RadarActivity.class);
        goToRadarActivity.putExtra("location", location);
        startActivity(goToRadarActivity);
    }

    public void goToInput(View view) {
        updateLocation();
        Intent goToInputActivity = new Intent(getApplicationContext(), InputActivity.class);
        goToInputActivity.putExtra("location", location);
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
            case R.id.gallery_input_button:
                goToInputActivity.putExtra("INPUT_TYPE", "gallery");
                break;
        }
        startActivity(goToInputActivity);
    }
}
