package com.stolz.weatherexample;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetWeatherInfoTask extends AsyncTask<Void, Void, String> {

    private int mZipcode;
    private CityListFragment.WeatherResultsListener mWeatherResultsListener;

    public GetWeatherInfoTask(CityListFragment.WeatherResultsListener listener, int zipcode) {
        mZipcode = zipcode;
        mWeatherResultsListener = listener;
    }

    @Override
    protected String doInBackground(Void... urls) {

        try {
            URL url = new URL(Constants.API_URL + "zip=" + String.valueOf(mZipcode) + ",us" + "&apiKey=" + Constants.API_KEY);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    protected void onPostExecute(String response) {
        if (response == null) {
            Log.i("INFO", "ERROR");
        } else {
            Log.i("INFO", response);
        }

        try {
            if (response == null) {
                mWeatherResultsListener.weatherResultsReceived(null);
            } else {
                JSONObject result = new JSONObject(response);
                result.put("ZIPCODE", mZipcode);
                mWeatherResultsListener.weatherResultsReceived(result);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
