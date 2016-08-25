package com.stolz.weatherexample;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class CityDetailActivity extends AbstractActivity {

    private static final String EXTRA_ZIPCODE = "zipcode";

    public static Intent newIntent(Context context, int zipcode) {
        Intent intent = new Intent(context, CityDetailActivity.class);
        intent.putExtra(EXTRA_ZIPCODE, zipcode);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        int zipcode = getIntent().getIntExtra(EXTRA_ZIPCODE, -1);
        return CityDetailFragment.newInstance(zipcode);
    }

}
