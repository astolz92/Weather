package com.stolz.weatherexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CityListFragment extends Fragment {

    private RecyclerView mWeatherRecyclerView;
    private CityListAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mLayout = inflater.inflate(R.layout.fragment_city_list, container, false);
        mWeatherRecyclerView = (RecyclerView) mLayout.findViewById(R.id.weather_recycler_view);
        mWeatherRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return mLayout;
    }

    @Override
    public void onResume() {
        super.onResume();
        WeatherInfoProvider weatherInfoProvider = WeatherInfoProvider.getInstance();
        getWeatherInfo(weatherInfoProvider.getCities());
        updateUI();
    }

    private void updateUI() {
        WeatherInfoProvider weatherInfoProvider = WeatherInfoProvider.getInstance();
        List<City> cities = weatherInfoProvider.getCities();

        if (mAdapter == null) {
            mAdapter = new CityListAdapter(getActivity(), cities);
            mWeatherRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    public void addCity(int zipcode) {
        City added = new City(zipcode);
        mAdapter.add(added);
        getWeatherInfo(added);
        mAdapter.notifyDataSetChanged();

        Intent intent = CityDetailActivity.newIntent(getContext(), added.getZipcode());
        getActivity().startActivity(intent);
    }

    public void getWeatherInfo(City city) {
        GetWeatherInfoTask task;
        task = new GetWeatherInfoTask(new WeatherResultsListener() {
                @Override
                public void weatherResultsReceived(JSONObject data) {
                    updateCityInfo(data);
                }
            },city.getZipcode());
            task.execute();
    }

    public void getWeatherInfo(List<City> cities) {
        GetWeatherInfoTask task;
        for (City current : cities) {
            task = new GetWeatherInfoTask(new WeatherResultsListener() {
                @Override
                public void weatherResultsReceived(JSONObject data) {
                    updateCityInfo(data);
                }
            },current.getZipcode());
            task.execute();
        }
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

            mAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public interface WeatherResultsListener {
        void weatherResultsReceived(JSONObject data);
    }
}
