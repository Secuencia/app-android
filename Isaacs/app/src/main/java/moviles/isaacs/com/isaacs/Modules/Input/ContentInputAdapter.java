package moviles.isaacs.com.isaacs.modules.Input;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

import moviles.isaacs.com.isaacs.R;
import moviles.isaacs.com.isaacs.models.Content;
import moviles.isaacs.com.isaacs.services.AudioManager;

/**
 * Created by sfrsebastian on 10/21/16.
 */

public class ContentInputAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Content> mDataSource;

    public ContentInputAdapter(Context context, ArrayList<Content> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = mInflater.inflate(R.layout.cell_input_text, parent, false);
        try{
            Content content = (Content) getItem(position);
            JSONObject contentData = new JSONObject(content.getData());
            if(content.getType() == Content.PICTURE){
                rowView = mInflater.inflate(R.layout.cell_input_photo, parent, false);
                ImageView imageView = (ImageView) rowView.findViewById(R.id.content_thumbnail);
                TextView dateTextView = (TextView) rowView.findViewById(R.id.date_text);
                EditText bodyEditText = (EditText) rowView.findViewById(R.id.body_editText);
                Picasso.with(mContext).load(contentData.getString("picture")).resize(130,130).placeholder(R.mipmap.ic_launcher).into(imageView);
                dateTextView.setText(content.getDateCreated().toString());
                String contentBody = contentData.getString("body");
                bodyEditText.setText(contentBody);
                if(position < getCount()){
                    bodyEditText.setFocusable(false);
                    bodyEditText.setClickable(false);
                }
            }
            else if(content.getType() == Content.TEXT){
                EditText bodyEditText = (EditText) rowView.findViewById(R.id.body_editText);
                String contentBody = contentData.getString("body");
                bodyEditText.setText(contentBody);
                if(position < getCount()){
                    bodyEditText.setFocusable(false);
                    bodyEditText.setClickable(false);
                }
            }
            else if(content.getType() == Content.AUDIO){
                rowView = mInflater.inflate(R.layout.cell_input_audio, parent, false);
                final ImageButton btnRecord = (ImageButton)rowView.findViewById(R.id.record_button);
                final ImageButton btnPlay = (ImageButton)rowView.findViewById(R.id.play_button);
                final String audioPath = contentData.getString("audio");
                btnRecord.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("Test", "Record Now");
                        if(!AudioManager.getInstance().isRecording()){
                            AudioManager.getInstance().startRecording(audioPath);
                            btnRecord.setImageResource(R.mipmap.stop);
                            btnPlay.setEnabled(false);
                        }
                        else{
                            AudioManager.getInstance().stopRecording();
                            btnRecord.setImageResource(R.mipmap.record);
                            btnPlay.setEnabled(true);
                        }
                    }
                });
                btnPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("Test", "Play Now");
                        AudioManager.getInstance().startPlaying(audioPath);
                    }
                });
                TextView dateTextView = (TextView) rowView.findViewById(R.id.date_text);
                dateTextView.setText(content.getDateCreated().toString());
            }
            return rowView;
        }
        catch(Exception e){
            Log.e("Exception", "Exception de view");
        }
        return rowView;
    }
}
