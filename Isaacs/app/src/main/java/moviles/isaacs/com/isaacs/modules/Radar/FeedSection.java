package moviles.isaacs.com.isaacs.modules.Radar;

/**
 * Created by sfrsebastian on 10/30/16.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;
import moviles.isaacs.com.isaacs.R;
import moviles.isaacs.com.isaacs.models.Content;
import moviles.isaacs.com.isaacs.modules.Contents.ViewWrapper;
import moviles.isaacs.com.isaacs.services.WeatherService;

/**
 * Created by sfrsebastian on 10/21/16.
 */

class FeedSection extends StatelessSection {

    private ArrayList<Content> contents;
    private Context mContext;
    private String title;
    private int type;

    public FeedSection(Context context, int type, int resource_id, ArrayList<Content> contents) {
        super(R.layout.section_header, resource_id);
        this.contents = contents;
        this.mContext = context;
        this.type = type;
        if(type == Content.WEATHER){
            title = "Clima";
        }
        else if(type == Content.PLACE){
            title = "Lugares";
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
            if(type == Content.WEATHER){
                ImageView imageView = (ImageView) view.findViewById(R.id.content_thumbnail);
                TextView titleView = (TextView)view.findViewById(R.id.title);
                TextView bodyView = (TextView)view.findViewById(R.id.body);
                titleView.setText(contentData.getString("title"));
                bodyView.setText(contentData.getString("body"));
                Picasso.with(mContext).load(WeatherService.getImageResource(contentData.getString("picture"))).placeholder(R.mipmap.ic_launcher).into(imageView);
            }
            else if(type == Content.PLACE){
                TextView positionView = (TextView) view.findViewById(R.id.position);
                TextView titleView = (TextView)view.findViewById(R.id.title);
                TextView bodyView = (TextView)view.findViewById(R.id.body);
                positionView.setText(contentData.getString("position"));
                titleView.setText(contentData.getString("title"));
                bodyView.setText(contentData.getString("body"));
            }
        }
        catch(Exception e){
            Log.e("Excepcion", "Error en adaptador de contenidos");
        }
    }
}

