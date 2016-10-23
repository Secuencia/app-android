package moviles.isaacs.com.isaacs;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

import moviles.isaacs.com.isaacs.models.Content;

/**
 * Created by sfrsebastian on 10/21/16.
 */

public class ContentAdapter extends BaseAdapter {
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
    public int getCount() {
        return mDataSource.size();
    }

    //2
    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    //3
    @Override
    public long getItemId(int position) {
        return position;
    }

    //4
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get view for row item

        View rowView = mInflater.inflate(R.layout.cell_text, parent, false);

        Content content = (Content) getItem(position);
        if(content.getType() == Content.PICTURE){
            rowView = mInflater.inflate(R.layout.cell_photo, parent, false);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.content_thumbnail);
            TextView dateTextView = (TextView) rowView.findViewById(R.id.date_text);
            dateTextView.setText(content.getDateCreated().toString());
            Picasso.with(mContext).load(content.getData()).resize(130,130).placeholder(R.mipmap.ic_launcher).into(imageView);
        }
        else if(content.getType() == Content.TEXT){
            TextView titleTextView = (TextView) rowView.findViewById(R.id.content_title);
            TextView subtitleTextView = (TextView) rowView.findViewById(R.id.content_subtitle);
            TextView detailTextView = (TextView) rowView.findViewById(R.id.content_detail);
            ImageView thumbnailImageView = (ImageView) rowView.findViewById(R.id.content_thumbnail);
            titleTextView.setText("Titulo " + content.getData());
            subtitleTextView.setText("Subtitulo " + content.getData());
            detailTextView.setText("Texto " + content.getData());
            Picasso.with(mContext).load(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView);
        }
        return rowView;
    }
}
