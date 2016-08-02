package com.example.shaon.desirestaurantfinder;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;


public class Downloader extends AsyncTask<String, Integer, ArrayList> {

    ResultsActivity activity;
    public static String API_KEY = "a08d1a0f0f5548cfd078adb5e8c7945d";

    public Downloader(ResultsActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activity.displayProgressBar();
    }

    @Override
    protected ArrayList doInBackground(String... params) {

        // String yqlURL = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20local.search%20where%20zip%3D%27"+params[1]+"%27%20and%20query%3D%27"+params[0]+"%27&format=json&callback=";
        String zomatoURL = "https://developers.zomato.com/api/v2.1/search?lat=" + params[0] + "&lon=" + params[1] + "&cuisines=148"+"&apikey=a08d1a0f0f5548cfd078adb5e8c7945d";
        ArrayList<Results> resultsArrayList = new ArrayList<Results>();

        try {
            URL theUrl = new URL(zomatoURL);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) theUrl.openConnection();
            httpsURLConnection.setRequestProperty("user-key", API_KEY);
            BufferedReader reader = new BufferedReader(new InputStreamReader(theUrl.openConnection().getInputStream(), "UTF-8"));
            String json = reader.readLine();

            JSONObject jsonObject = new JSONObject(json);
//            JSONObject queryObject = jsonObject.getJSONObject("query");
//           JSONObject resultsObject = queryObject.getJSONObject("results");
            JSONArray resultsArray = jsonObject.getJSONArray("restaurants");

            for (int i = 0; i < resultsArray.length(); i++) {
                publishProgress((i + 1) * 10);
                JSONObject singleObject = resultsArray.getJSONObject(i);
                String name = singleObject.getString("name");

                JSONObject addressObject=singleObject.getJSONObject("location");
                String address = addressObject.getString("address");
                String locality = addressObject.getString("locality");
                String city = addressObject.getString("city");
                String zipcode = addressObject.getString("zipcode");
                double latitude = Double.parseDouble(addressObject.getString("latitude")) ;
                double longitude = Double.parseDouble(addressObject.getString("longitude")) ;

                String cuisines = singleObject.getString("cuisines");
                String average_cost_for_two = singleObject.getString("average_cost_for_two");
                String price_range = singleObject.getString("price_range");
                String offers = singleObject.getString("offers");
                String thumb = singleObject.getString("thumb");

                JSONObject userratingObject=singleObject.getJSONObject("user_rating");
                String aggregate_rating = userratingObject.getString("aggregate_rating");
                String votes = userratingObject.getString("votes");

                String photos_url = singleObject.getString("photos_url");
                String menu_url = singleObject.getString("menu_url");
                String featured_image_url = singleObject.getString("featured_image");
                String has_online_delivery = singleObject.getString("has_online_delivery");
                String is_delivering_now = singleObject.getString("is_delivering_now");
                String events_url = singleObject.getString("events_url");
                Results result = new Results(name, address,locality, city, zipcode,latitude,longitude, cuisines, average_cost_for_two, price_range, offers, thumb,aggregate_rating,votes,photos_url,
                                              menu_url,featured_image_url,has_online_delivery,is_delivering_now,events_url);
                resultsArrayList.add(result);

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return resultsArrayList;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        activity.setProgressBarProgress(values[0]);
    }

    @Override
    protected void onPostExecute(ArrayList arrayList) {
        super.onPostExecute(arrayList);
        activity.drawListView(arrayList);
    }
}
