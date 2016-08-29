package com.stolz.weatherexample;

public class City {

    private int mZipcode;
    private String mName;
    private String mConditions;
    private double mTemperature;

    public City(int zipcode){
        mZipcode = zipcode;

    }

    public int getZipcode() {
        return mZipcode;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getConditions() {
        return mConditions;
    }

    public void setConditions(String mConditions) {
        this.mConditions = mConditions;
    }

    public double getTemperature() {
        return mTemperature;
    }

    public String getTemperatureWithUnit(int type) {
        if (type == Constants.TEMPERATURE_FAHRENHEIT) {
            double fahrenheit = (mTemperature * (9.0/5.0)) - 459.67;
            return String.format("%.2f", fahrenheit) + "°F";

        } else if (type == Constants.TEMPERATURE_CELSIUS) {
            double celsius = mTemperature - 273.15;
            return String.format("%.2f", celsius) + "°C";
        }
        return mTemperature + " K";
    }

    public void setTemperature(double mTemperature) {
        this.mTemperature = mTemperature;
    }
}
