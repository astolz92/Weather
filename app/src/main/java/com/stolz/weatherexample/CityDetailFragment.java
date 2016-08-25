package com.stolz.weatherexample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CityDetailFragment extends Fragment {

    private static final String ARG_ZIPCODE = "zipcode";

    private City mWeatherInfo;
    private TextView mZipCodeText;
    private TextView mNameText;
    private TextView mConditionsText;
    private TextView mTemperatureText;


    public static CityDetailFragment newInstance(int zipcode) {
        Bundle args = new Bundle();
        args.putInt(ARG_ZIPCODE, zipcode);

        CityDetailFragment fragment = new CityDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int zipcode = getArguments().getInt(ARG_ZIPCODE);
        mWeatherInfo = WeatherInfoProvider.getInstance().getCity(zipcode);
        getWeatherInfo(mWeatherInfo);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_city_detail, container, false);

        mZipCodeText = (TextView) v.findViewById(R.id.zip_code);
        mNameText = (TextView) v.findViewById(R.id.name);
        mConditionsText = (TextView) v.findViewById(R.id.conditions);
        mTemperatureText = (TextView) v.findViewById(R.id.temperature);

        return v;
    }

    public void updateData() {
        mZipCodeText.setText(String.valueOf(mWeatherInfo.getZipcode()));
        mNameText.setText(mWeatherInfo.getName());
        mConditionsText.setText(mWeatherInfo.getConditions());
        mTemperatureText.setText(String.valueOf(mWeatherInfo.getTemperatureWithUnit(Constants.TEMPERATURE_FAHRENHEIT)));
    }

    @Override
    public void onResume() {
        super.onResume();
        updateData();
    }

    public void getWeatherInfo(City city) {
        GetWeatherInfoTask task;
        task = new GetWeatherInfoTask(new CityListFragment.WeatherResultsListener() {
            @Override
            public void weatherResultsReceived(JSONObject data) {
                updateCityInfo(data);
            }
        },city.getZipcode());
        task.execute();
    }

    public void updateCityInfo(JSONObject data) {
        if (data == null) {
            return;
        }

        try {
            int zipcode = data.getInt("ZIPCODE");
            JSONObject mainInfo = data.getJSONObject("main");
            double temp = mainInfo.getDouble("temp");
            String name = data.getString("name");
            JSONArray weather = data.getJSONArray("weather");
            JSONObject weatherDetails = weather.getJSONObject(0);
            String conditions = weatherDetails.getString("main");

            City current = WeatherInfoProvider.getInstance().getCity(zipcode);

            if (current != null) {
                current.setConditions(conditions);
                current.setName(name);
                current.setTemperature(temp);
            }

            mWeatherInfo = current;
            updateData();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
