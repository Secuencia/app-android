package moviles.isaacs.com.isaacs.modules.Stories;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import moviles.isaacs.com.isaacs.R;
import moviles.isaacs.com.isaacs.models.Story;

/**
 * Created by sfrsebastian on 10/21/16.
 */

public class StoryDetailAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Story> mDataSource;

    public StoryDetailAdapter(Context context, ArrayList<Story> items) {
        mContext = context;
        mDataSource = items;
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
        final Story story = mDataSource.get(position);
        View view = mInflater.inflate(R.layout.cell_select, parent, false);
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(story.getTitle());
        CheckBox check = (CheckBox) view.findViewById(R.id.check);
        check.setVisibility(View.GONE);
        Button delete = (Button) view.findViewById(R.id.delete);
        delete.setTag(R.string.story, story);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToStoryDetailActivity = new Intent(mContext, StoryDetailActivity.class);
                goToStoryDetailActivity.putExtra("story", story);
                mContext.startActivity(goToStoryDetailActivity);
            }
        });
        return view;
    }
}