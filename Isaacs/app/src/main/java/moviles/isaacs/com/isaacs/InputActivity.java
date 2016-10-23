package moviles.isaacs.com.isaacs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import moviles.isaacs.com.isaacs.models.Content;

public class InputActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private String inputType;
    private ListView listView;
    private ArrayList<Content> listItems;
    private ContentAdapter adapter;
    private Content currentContent;
    private File pictureFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        setTheme(R.style.AppTheme);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            inputType = extras.getString("INPUT_TYPE");
        }
        listView = (ListView) findViewById(R.id.list_view);
        listItems = new ArrayList();
        adapter = new ContentAdapter(this, listItems);
        listView.setAdapter(adapter);
        switch(inputType){
            case("text"): insertText(null); break;
            case("photo"): insertPhoto(null);break;
            case("audio"): insertAudio(null);break;
            default: break;
        }
    }

    public void insertText(View view){
        currentContent = new Content();
        currentContent.setType(Content.TEXT);
        currentContent.setData("Creado texto");
        listItems.add(currentContent);
        adapter.notifyDataSetChanged();
    }

    public void insertFromGallery(View view){
        currentContent = new Content();
        currentContent.setType(Content.PICTURE);
        currentContent.setData("Creada Galeria");
        listItems.add(currentContent);
        adapter.notifyDataSetChanged();
    }

    public void insertPhoto(View view){
        currentContent = new Content();
        currentContent.setType(Content.PICTURE);
        dispatchTakePictureIntent();
    }

    public void insertAudio(View view){
        currentContent = new Content();
        currentContent.setType(Content.AUDIO);
        currentContent.setData("Creado Audio");
        listItems.add(currentContent);
        adapter.notifyDataSetChanged();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            pictureFile = null;
            try {
                pictureFile = createImageFile();
            } catch (IOException ex) {
                Log.e("Excepcion","Error creando file de imagen");
            }
            if (pictureFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        pictureFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Uri imageBitmap = Uri.fromFile(pictureFile);
            currentContent.setData(imageBitmap.toString());
            listItems.add(currentContent);
            adapter.notifyDataSetChanged();
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return image;
    }

}
