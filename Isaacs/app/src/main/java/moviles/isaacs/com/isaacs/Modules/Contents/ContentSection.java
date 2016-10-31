package moviles.isaacs.com.isaacs.modules.Contents;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import java.util.ArrayList;

import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;
import moviles.isaacs.com.isaacs.R;
import moviles.isaacs.com.isaacs.models.Content;
import moviles.isaacs.com.isaacs.modules.Stories.StorySelectActivity;
import moviles.isaacs.com.isaacs.services.AudioManager;

/**
 * Created by sfrsebastian on 10/28/16.
 */

class ContentSection extends StatelessSection {

    private ArrayList<Content> contents;
    private int type;
    private Context mContext;
    private String title;
    public ContentSection(Context context, int type, int resource_id, ArrayList<Content> contents) {
        super(R.layout.section_header, resource_id);
        this.contents = contents;
        this.type = type;
        this.mContext = context;
        switch(type){
            case (Content.AUDIO):
                title = "Grabaciones";
                break;
            case (Content.TEXT):
                title = "Texto";
                break;
            case (Content.PICTURE):
                title = "Imágenes";
                break;
        }
    }

    @Override
    public int getContentItemsTotal() {
        return contents.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ViewWrapper(view);
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        TextView textView = (TextView)view.findViewById(R.id.tvTitle);
        textView.setText(title);
        return new ViewWrapper(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        try{
            final Content content = contents.get(position);
            JSONObject contentData = new JSONObject(content.getData());
            View view = ((ViewWrapper)holder).getView();
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
            if(type == Content.TEXT) {
                TextView bodyEditText = (TextView) view.findViewById(R.id.body_textView);
                String contentBody = contentData.getString("body");
                bodyEditText.setText(contentBody);
            }
            else if(type == Content.PICTURE){
                ImageView imageView = (ImageView) view.findViewById(R.id.content_thumbnail);
                Picasso.with(mContext).load(contentData.getString("picture")).resize(130,130).placeholder(R.mipmap.ic_launcher).into(imageView);
            }
            else if(type == Content.AUDIO){
                final String audioPath = contentData.getString("audio");
                ImageButton btnPlay = (ImageButton) view.findViewById(R.id.play_button);
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