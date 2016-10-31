package moviles.isaacs.com.isaacs.services;

import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;

import moviles.isaacs.com.isaacs.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by sfrsebastian on 10/30/16.
 */

public class WeatherService{
    private static OkHttpClient client = new OkHttpClient();
    private static String url = "https://api.darksky.net/forecast/%s/%s,%s?lang=es";

    public static void getWeather(Callback callback, String key, double latitude, double longitude){
        String finalUrl = String.format(url, key, latitude, longitude);
        Request request = new Request.Builder()
                .url(finalUrl)
                .build();

        client.newCall(request).enqueue(callback);
    }

    public static int getImageResource(String weather){
        int iconId = R.mipmap.clear_day;

        if (weather.equals("clear-day")) {
            iconId = R.mipmap.clear_day;
        }
        else if (weather.equals("clear-night")) {
            iconId = R.mipmap.clear_night;
        }
        else if (weather.equals("rain")) {
            iconId = R.mipmap.rain;
        }
        else if (weather.equals("snow")) {
            iconId = R.mipmap.snow;
        }
        else if (weather.equals("sleet")) {
            iconId = R.mipmap.sleet;
        }
        else if (weather.equals("wind")) {
            iconId = R.mipmap.wind;
        }
        else if (weather.equals("fog")) {
            iconId = R.mipmap.fog;
        }
        else if (weather.equals("cloudy")) {
            iconId = R.mipmap.cloudy;
        }
        else if (weather.equals("partly-cloudy-day")) {
            iconId = R.mipmap.partly_cloudy;
        }
        else if (weather.equals("partly-cloudy-night")) {
            iconId = R.mipmap.cloudy_night;
        }

        return iconId;
    }
}
