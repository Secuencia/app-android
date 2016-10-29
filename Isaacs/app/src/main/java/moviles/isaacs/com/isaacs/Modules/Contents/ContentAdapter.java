package moviles.isaacs.com.isaacs.modules.Contents;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

import moviles.isaacs.com.isaacs.R;
import moviles.isaacs.com.isaacs.models.Content;
import moviles.isaacs.com.isaacs.modules.Stories.StorySelectActivity;
import moviles.isaacs.com.isaacs.services.AudioManager;

/**
 * Created by sfrsebastian on 10/21/16.
 */

public class ContentAdapter extends RecyclerView.Adapter<ViewWrapper> {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Content> mDataSource;

    public ContentAdapter(Context context, ArrayList<Content> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    //1
    @Override
    public int getItemCount() {
        return mDataSource.size();
    }

    public Object getItem(int position){
        return mDataSource.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return mDataSource.get(position).getType();
    }

    @Override
    public ViewWrapper onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewWrapper wrapper;
        if(viewType == Content.TEXT){
            wrapper = new ViewWrapper(mInflater.inflate(R.layout.cell_display_text, parent, false));
        }
        else if(viewType == Content.PICTURE){
            wrapper = new ViewWrapper(mInflater.inflate(R.layout.cell_display_photo, parent, false));
        }
        else{
            wrapper = new ViewWrapper(mInflater.inflate(R.layout.cell_display_audio, parent, false));
        }
        return wrapper;
    }

    @Override
    public void onBindViewHolder(ViewWrapper holder, int position) {
        try{
            final Content content = (Content) getItem(position);
            JSONObject contentData = new JSONObject(content.getData());
            View view = holder.getView();
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent goToSelectStoryActivity = new Intent(mContext, StorySelectActivity.class);
                    goToSelectStoryActivity.putExtra("content", content);
                    mContext.startActivity(goToSelectStoryActivity);
                }
            });
            Button delete = (Button)view.findViewById(R.id.delete);
            delete.setTag(R.string.content, content);
            if(content.getType() == Content.TEXT) {
                TextView bodyEditText = (TextView) view.findViewById(R.id.body_textView);
                String contentBody = contentData.getString("body");
                bodyEditText.setText(contentBody);
            }
            else if(content.getType() == Content.PICTURE){
                ImageView imageView = (ImageView) view.findViewById(R.id.content_thumbnail);
                Picasso.with(mContext).load(contentData.getString("picture")).resize(130,130).placeholder(R.mipmap.ic_launcher).into(imageView);
            }
            else if(content.getType() == Content.AUDIO){
                final String audioPath = contentData.getString("audio");
                Button btnPlay = (Button) view.findViewById(R.id.play_button);
                btnPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("Test", "Play Now");
                        AudioManager.getInstance().startPlaying(audioPath);
                    }
                });
                TextView dateTextView = (TextView) view.findViewById(R.id.date_text);
                dateTextView.setText(content.getDateCreated().toString());
            }
        }
        catch(Exception e){
            Log.e("Excepcion", "Error en adaptador de contenidos");
        }
    }
}
