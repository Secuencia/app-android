package moviles.isaacs.com.isaacs.modules.Stories;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

import moviles.isaacs.com.isaacs.R;
import moviles.isaacs.com.isaacs.models.Content;
import moviles.isaacs.com.isaacs.models.Story;
import moviles.isaacs.com.isaacs.modules.Contents.ViewWrapper;
import moviles.isaacs.com.isaacs.services.AudioManager;

/**
 * Created by sfrsebastian on 10/21/16.
 */

public class StorySelectAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Story> mDataSource;
    private Content mContent;

    public StorySelectAdapter(Context context, ArrayList<Story> items, Content content) {
        mContext = context;
        mDataSource = items;
        mContent = content;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int position){
        return mDataSource.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Story story = mDataSource.get(position);
        View view = mInflater.inflate(R.layout.cell_select, parent, false);
        Button delete = (Button) view.findViewById(R.id.delete);
        delete.setVisibility(View.GONE);
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(story.getTitle());
        CheckBox check = (CheckBox) view.findViewById(R.id.check);
        check.setTag(R.string.story, mDataSource.get(position));
        if(story.getContents() != null && story.hasContent(mContent)){
            check.setChecked(true);
        }
        return view;
    }
}
