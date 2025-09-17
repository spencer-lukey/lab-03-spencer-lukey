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

public class EditCityFragment extends DialogFragment {


    interface EditCityDialogListener {
        void editCity(City updatedCity, int position);
    }

    private City cityToEdit;
    private EditCityDialogListener listener;

    // Create the fragment 'constructor'
    public static EditCityFragment newInstance(City city, int position) {
        EditCityFragment fragment = new EditCityFragment();
        Bundle args = new Bundle();
        args.putSerializable("city", city);
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    // Refactor from AddCityFragment's methods:
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EditCityDialogListener) {
            listener = (EditCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement EditCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            cityToEdit = (City) getArguments().getSerializable("city");
        }

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        // Pre-fill with current city info
        editCityName.setText(cityToEdit.getName());
        editProvinceName.setText(cityToEdit.getProvince());

        return new AlertDialog.Builder(getContext())
                .setView(view)
                .setTitle("Edit city")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Save", (dialog, which) -> {
                    int position = getArguments().getInt("position");
                    cityToEdit.setName(editCityName.getText().toString());
                    cityToEdit.setProvince(editProvinceName.getText().toString());
                    listener.editCity(cityToEdit, position);
                })
                .create();
    }
}
