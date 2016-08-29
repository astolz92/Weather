package com.stolz.weatherexample;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class CityListActivity extends AbstractActivity {

    @Override
    protected Fragment createFragment() {
        return new CityListFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add:
                showAddDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add a zip code");
        builder.setMessage("Type in a zip code to add it to the list.");

        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_add_city, null);
        final EditText zipCodeEditText = (EditText) dialogLayout.findViewById(R.id.zipcode_input);

        builder.setView(dialogLayout);

        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int zipcode = Integer.valueOf(zipCodeEditText.getText().toString());
                addCity(zipcode);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void addCity(int zipcode) {
        FragmentManager manager = getSupportFragmentManager();
        if (manager != null) {
            CityListFragment cityList = (CityListFragment) manager.findFragmentByTag(Constants.CITY_LIST_FRAGMENT_TAG);
            if (cityList != null) {
                cityList.addCity(zipcode);
            }
        }
    }
}
