package moviles.isaacs.com.isaacs.modules.Radar;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import moviles.isaacs.com.isaacs.R;
import moviles.isaacs.com.isaacs.models.Content;

/**
 * Created by sfrsebastian on 10/29/16.
 */

public class MarkerInfoWindow implements GoogleMap.InfoWindowAdapter {

    private View view;
    private Context mContext;

    public MarkerInfoWindow(Context context){
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        view = inflater.inflate(R.layout.info_view_marker, null);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        try{
            if(!marker.getTitle().equals("Tu ubicación")) {
                Content content = (Content)marker.getTag();
                JSONObject contentData = new JSONObject(content.getData());
                TextView tvTitle = (TextView) view.findViewById(R.id.title);

                TextView tvSnippet = (TextView) view.findViewById(R.id.snippet);
                ImageView imageView = (ImageView) view.findViewById(R.id.thumbnail);
                if(content.getType() == Content.TEXT){
                    tvTitle.setText("Texto");
                    imageView.setVisibility(View.GONE);
                    tvSnippet.setText(contentData.getString("body"));
                }
                else if(content.getType() == Content.PICTURE){
                    tvTitle.setText("Imágen");
                    imageView.setVisibility(View.VISIBLE);
                    tvSnippet.setText(contentData.getString("body"));
                    Picasso.with(mContext).load(contentData.getString("picture")).resize(130,130).placeholder(R.mipmap.ic_launcher).into(imageView);
                }
                else if(content.getType() == Content.AUDIO){
                    tvTitle.setText("Audio");
                    imageView.setVisibility(View.GONE);
                }
                return view;
            }
        }
        catch(Exception e){
            Log.e("Exception", "Error en json de contenido en marker");
        }

        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}