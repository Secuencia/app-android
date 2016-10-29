package moviles.isaacs.com.isaacs.modules.Input;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import moviles.isaacs.com.isaacs.R;
import moviles.isaacs.com.isaacs.models.Content;
import moviles.isaacs.com.isaacs.services.AudioManager;
import moviles.isaacs.com.isaacs.services.MyDBHandler;

public class InputActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int PICK_IMAGE_REQUEST = 2 ;
    private String inputType;
    private ListView listView;
    private ArrayList<Content> listItems;
    private ContentInputAdapter adapter;
    private Content currentContent;
    private File pictureFile;
    private MyDBHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new MyDBHandler(this, null, null, 1);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        setContentView(R.layout.activity_input);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            inputType = extras.getString("INPUT_TYPE");
        }
        listView = (ListView) findViewById(R.id.list_view);
        listView.setItemsCanFocus(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        listItems = new ArrayList();
        adapter = new ContentInputAdapter(this, listItems);
        listView.setAdapter(adapter);
        switch(inputType){
            case("text"): insertText(null); break;
            case("photo"): insertPhoto(null);break;
            case("audio"): insertAudio(null);break;
            default: break;
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        persistCurrentContent();
    }

    public void insertText(View view){
        persistCurrentContent();
        currentContent = new Content();
        currentContent.setType(Content.TEXT);
        listItems.add(currentContent);
        adapter.notifyDataSetChanged();
    }

    public void insertFromGallery(View view){
        persistCurrentContent();
        currentContent = new Content();
        currentContent.setType(Content.PICTURE);
        dispatchPickPicture();
    }

    public void insertPhoto(View view){
        persistCurrentContent();
        currentContent = new Content();
        currentContent.setType(Content.PICTURE);
        dispatchTakePictureIntent();
    }

    public void insertAudio(View view){
        persistCurrentContent();
        currentContent = new Content();
        currentContent.setType(Content.AUDIO);
        try{
            JSONObject json = new JSONObject(currentContent.getData());
            json.put("audio", AudioManager.getInstance().getFilePath(new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".3gp"));
            currentContent.setData(json.toString());
        }
        catch(Exception e){
            Log.e("Exception", "Error en json de audio");
        }

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
                Log.e("Exception","Error creando file de imagen");
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

    private void dispatchPickPicture(){
        Intent pickIntent = new Intent();
        pickIntent.setType("image/*");
        pickIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(pickIntent, "Selecciona una imagen"), PICK_IMAGE_REQUEST);
    }

    private void persistCurrentContent() {
        if(currentContent != null){
            try{
                if(currentContent.getType() == Content.PICTURE || currentContent.getType() == Content.TEXT){
                    EditText body = (EditText) listView.getChildAt(adapter.getCount()-1).findViewById(R.id.body_editText);
                    JSONObject json = new JSONObject(currentContent.getData());
                    json.put("body", body.getText());
                    currentContent.setData(json.toString());
                    listItems.set(adapter.getCount()-1, currentContent);
                    adapter.notifyDataSetChanged();
                }
                handler.createContent(currentContent);
            }
            catch(Exception e){
                Log.e("Exception", "Error de json");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if ((requestCode == REQUEST_IMAGE_CAPTURE || requestCode == PICK_IMAGE_REQUEST) && resultCode == RESULT_OK) {
            Uri image_uri = null;
            JSONObject json = new JSONObject();
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                image_uri = Uri.fromFile(pictureFile);
            } else if (requestCode == PICK_IMAGE_REQUEST) {
                image_uri = data.getData();
            }
            if (image_uri != null) {
                try {
                    json.put("picture", image_uri.toString());
                } catch (Exception e) {
                    Log.e("Exception", "Json Exception");
                }
                currentContent.setData(json.toString());
            }
            if(resultCode == RESULT_OK){
                listItems.add(currentContent);
                adapter.notifyDataSetChanged();
            }
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
