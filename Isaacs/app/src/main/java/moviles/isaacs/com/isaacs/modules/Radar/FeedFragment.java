package moviles.isaacs.com.isaacs.modules.Radar;

/**
 * Created by sfrsebastian on 10/29/16.
 */

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import moviles.isaacs.com.isaacs.R;
import moviles.isaacs.com.isaacs.models.Content;
import moviles.isaacs.com.isaacs.modules.Contents.DividerItemDecoration;
import moviles.isaacs.com.isaacs.services.WeatherService;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class FeedFragment extends Fragment implements Callback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 3;
    private View fragment;
    private double latitude;
    private double longitude;
    private RecyclerView recyclerView;
    private SectionedRecyclerViewAdapter adapter;
    private ArrayList<Content> weatherFeed = new ArrayList<>();
    private ArrayList<Content> placesFeed = new ArrayList<>();
    private GoogleApiClient mGoogleApiClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            latitude = bundle.getDouble("latitude");
            longitude = bundle.getDouble("longitude");
        }
        mGoogleApiClient = new GoogleApiClient
                .Builder(getContext())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(getActivity(), this)
                .build();

        WeatherService.getWeather(this, getString(R.string.weather_key), latitude, longitude);
        getPlacesRecommendation();

        fragment =  inflater.inflate(R.layout.fragment_feed, container, false);
        recyclerView = (RecyclerView) fragment.findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(20, 1));
        adapter = new SectionedRecyclerViewAdapter();
        return fragment;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        Log.e("Exception", "Falla en llamado de api");
    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        Content content = createWeatherContent(response.body().string());
        response.close();
        weatherFeed.clear();
        weatherFeed.add(content);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateAdapter();
            }
        });
    }

    private void updateAdapter(){
        adapter.removeAllSections();
        if(weatherFeed.size() > 0) {
            adapter.addSection(new FeedSection(getContext(), Content.WEATHER, R.layout.cell_display_weather, weatherFeed));
        }
        if(placesFeed.size()>0){
            adapter.addSection(new FeedSection(getContext(), Content.PLACE, R.layout.cell_display_place, placesFeed));
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private Content createWeatherContent(String data){
        try{
            Content content = new Content();
            JSONObject jsonData = new JSONObject(data);
            JSONObject currently = jsonData.getJSONObject("currently");
            JSONObject contentData = new JSONObject("{}");
            content.setType(Content.PICTURE);
            contentData.put("title", jsonData.getString("timezone"));
            contentData.put("body", currently.getString("summary"));
            contentData.put("picture", currently.getString("icon"));
            content.setData(contentData.toString());
            content.setType(Content.WEATHER);
            return content;
        }
        catch(Exception e){
            Log.e("Exception", "Error con datos de respeusta de clima");
        }
        return null;
    }

    private void getPlacesRecommendation(){
        if ( ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSION_ACCESS_FINE_LOCATION);
        }
        PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi.getCurrentPlace(mGoogleApiClient, null);
        result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
            @Override
            public void onResult(PlaceLikelihoodBuffer likelyPlaces) {
                try{
                    ArrayList<Content> contents = new ArrayList<>();
                    for (int i = 0; i<likelyPlaces.getCount() && i<5; i++) {
                        JSONObject contentData = new JSONObject("{}");
                        Content content = new Content();
                        PlaceLikelihood place = likelyPlaces.get(i);
                        contentData.put("title", place.getPlace().getName());
                        contentData.put("body", place.getPlace().getAddress());
                        contentData.put("position", i+1);
                        content.setData(contentData.toString());
                        content.setType(Content.PLACE);
                        contents.add(content);
                    }
                    placesFeed = contents;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateAdapter();
                        }
                    });

                }
                catch (Exception e){
                    Log.e("Exception", "Error en places recomendados");
                }
                likelyPlaces.release();
            }
        });
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
