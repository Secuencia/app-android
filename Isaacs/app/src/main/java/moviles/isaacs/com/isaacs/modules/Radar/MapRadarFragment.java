package moviles.isaacs.com.isaacs.modules.Radar;

/**
 * Created by sfrsebastian on 10/29/16.
 */

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import moviles.isaacs.com.isaacs.R;
import moviles.isaacs.com.isaacs.models.Content;
import moviles.isaacs.com.isaacs.services.MyDBHandler;


public class MapRadarFragment extends Fragment implements OnMapReadyCallback {

    private MapView mMapView;
    private TextView infoText;
    private Double latitude;
    private Double longitude;
    private MyDBHandler handler;
    private Circle mCircle;
    private GoogleMap mGoogleMap;
    private EditText inputText;


    public MapRadarFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        handler = new MyDBHandler(container.getContext(), null, null, 1);
        if (bundle != null) {
            latitude = bundle.getDouble("latitude");
            longitude = bundle.getDouble("longitude");
        }

        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        infoText = (TextView) rootView.findViewById(R.id.info_radar);
        inputText = (EditText) rootView.findViewById(R.id.radius_input);
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        if(latitude == null || longitude == null){
            infoText.setVisibility(View.VISIBLE);
            mMapView.setVisibility(View.GONE);
        }
        else{
            mMapView.onResume();

            try {
                MapsInitializer.initialize(getActivity().getApplicationContext());
            } catch (Exception e) {
                e.printStackTrace();
            }

            mMapView.getMapAsync(this);
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setInfoWindowAdapter(new MarkerInfoWindow(getContext()));
        refreshMap(null);
    }

    public void refreshMap(View view){
        mGoogleMap.clear();
        List<Content> contents = handler.getContents();
        LatLng location = new LatLng(latitude, longitude);

        mGoogleMap.addMarker(
                new MarkerOptions()
                        .position(location)
                        .title("Tu ubicación")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        String string_radius = inputText.getText().toString().equals("")?"1":inputText.getText().toString();
        double radius = Double.parseDouble(string_radius);
        drawMarkerWithCircle(location, radius);
        mCircle.setCenter(location);
        for(Content content :contents){
            float[] distance = new float[2];
            Location.distanceBetween(location.latitude, location.longitude, content.getLat(), content.getLon(), distance);
            if(content.getLat() != 0.0 && content.getLon() != 0.0 && distance[0] < mCircle.getRadius()){
                int res = 0;
                if(content.getType() == Content.PICTURE){
                    res = R.mipmap.gallery_icon;
                }
                else if(content.getType() == Content.AUDIO){
                    res = R.mipmap.play_icon;
                }
                else if(content.getType() == Content.TEXT){
                    res = R.mipmap.text_icon;
                }
                Bitmap bitmap = decodeSampledBitmapFromResource(getResources(), res , 60, 60);
                BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(bitmap);
                LatLng contentLocation = new LatLng(content.getLat(), content.getLon());
                mGoogleMap.addMarker(
                        new MarkerOptions()
                                .position(contentLocation)
                                .title("Contenido")
                                .icon(icon))
                                .setTag(content);

            }
        }
        // For zooming automatically to the location of the marker
        CameraPosition cameraPosition = new CameraPosition.Builder().target(location).zoom(14).build();
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void drawMarkerWithCircle(LatLng position, double radius){
        radius = radius*1000;
        int strokeColor = 0xffff0000; //red outline
        int shadeColor = 0x44ff0000; //opaque red fill

        CircleOptions circleOptions = new CircleOptions().center(position).radius(radius).fillColor(shadeColor).strokeColor(strokeColor).strokeWidth(8);
        mCircle = mGoogleMap.addCircle(circleOptions);
    }

    private Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    private int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
