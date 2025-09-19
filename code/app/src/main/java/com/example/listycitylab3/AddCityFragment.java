package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {
    interface AddCityDialogListener { // forces any activity that uses this fragment to implement void methods
        void addCity(City city);
        void editCity(City oldCity, City newCity);
    }

    private AddCityDialogListener listener;
    private City existingCity; // for storing City being edited if city selected
    private static final String CITY_KEY = "city"; // key for de/serializing

    // for adding a new city
    public static AddCityFragment newInstance() {
        return new AddCityFragment();
    }

    // for editing existing city
    public static AddCityFragment newInstance(City city) {
        AddCityFragment fragment = new AddCityFragment(); // initializes the fragment
        Bundle args = new Bundle(); // creates a bundle object
        args.putSerializable(CITY_KEY, city); // puts the city object into args bundle
        fragment.setArguments(args); // sets fragment arguments to args bundle
        return fragment;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + "must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        // checks if arguments were passed to fragment
        if (getArguments() != null) {
            existingCity = (City) getArguments().getSerializable(CITY_KEY);
            // if existingCity has args -> edit
            if (existingCity != null) {
                editCityName.setText(existingCity.getCityName());
                editProvinceName.setText(existingCity.getProvinceName());
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle(existingCity == null ? "Add a city" : "Edit City")
                .setNegativeButton("Cancel", null) // maybe implement a remove button later
                .setPositiveButton(existingCity == null ? "Add" : "Confirm", (dialog, which) -> { // for dynamic text-prompt
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();

                    // implemented to prevent empty inputs creating buttons with no text
                    if (cityName.isEmpty() || provinceName.isEmpty()) {
                        return; // maybe we can use toast to notify user of 'error'
                    }

                    City newCity = new City(cityName, provinceName); // initializes new city object with user inputs
                    if (existingCity == null) {
                        listener.addCity(newCity);
                    } else {
                        listener.editCity(existingCity, newCity);
                    }
                })
                .create();
    }
}
