package com.example.listycitylab3;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class EditCityFragment extends DialogFragment {
    // null means add and non-null means edit
    private City city;
    // empty constructor for new cities
    public EditCityFragment() {}
    // constructor for editing existing cities
    public EditCityFragment(City city){ this.city = city; }

    interface EditCityDialogListener { void onCityEdited(); }
    private EditCityDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EditCityDialogListener) {
            listener = (EditCityDialogListener) context;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        if (city != null) {
            editCityName.setText(city.getName());
            editProvinceName.setText(city.getProvince());
        }

        return new AlertDialog.Builder(requireContext())
                .setView(view)
                .setTitle(city == null ? "Add a city" : "Edit city")
                .setNegativeButton("Cancel", null)
                .setPositiveButton(city == null ? "Add" : "Save", (dialog, which) -> {
                    String newName = editCityName.getText().toString();
                    String newProv = editProvinceName.getText().toString();

                    if (city == null) {
                    } else {
                        city.setName(newName);
                        city.setProvince(newProv);
                        if (listener != null) listener.onCityEdited();   // <-- notify to refresh
                    }
                })
                .create();
    }
}
