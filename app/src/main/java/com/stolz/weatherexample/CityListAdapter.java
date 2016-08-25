package com.stolz.weatherexample;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.WeatherHolder> {
    private List<City> mCities;
    private Context mContext;

    public CityListAdapter(Context context, List<City> cities) {
        mContext = context;
        mCities = cities;
    }

    @Override
    public WeatherHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.row_city_list, parent, false);
        return new WeatherHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(WeatherHolder holder, int position) {
        City city = mCities.get(position);
        holder.bindCity(city);
    }

    @Override
    public int getItemCount() {
        return mCities.size();
    }

    public static class WeatherHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private City mCity;
        public TextView mZipcodeTextView;
        public TextView mCityNameTextView;
        public TextView mConditionsTextView;
        public TextView mTemperatureTextView;

        private Context mContext;

        public WeatherHolder(Context context, View itemView) {
            super(itemView);
            mContext = context;
            itemView.setOnClickListener(this);
            mZipcodeTextView = (TextView) itemView.findViewById(R.id.city_zipcode);
            mCityNameTextView = (TextView) itemView.findViewById(R.id.city_name);
            mConditionsTextView = (TextView) itemView.findViewById(R.id.city_conditions);
            mTemperatureTextView = (TextView) itemView.findViewById(R.id.city_temperature);

        }

        public void bindCity(City city) {
            mCity = city;
            mZipcodeTextView.setText(String.valueOf(mCity.getZipcode()));
            mCityNameTextView.setText(String.valueOf(mCity.getName()));
            mConditionsTextView.setText(String.valueOf(mCity.getConditions()));
            mTemperatureTextView.setText(String.valueOf(mCity.getTemperatureWithUnit(Constants.TEMPERATURE_FAHRENHEIT)));
        }

        @Override
        public void onClick(View view) {
            Intent intent = CityDetailActivity.newIntent(mContext, mCity.getZipcode());
            mContext.startActivity(intent);
        }
    }

    public void add(City added) {
        mCities.add(added);
    }
}
