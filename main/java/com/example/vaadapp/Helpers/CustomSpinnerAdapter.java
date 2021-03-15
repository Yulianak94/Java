package com.example.vaadapp.Helpers;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.vaadapp.Models.Building;
import com.example.vaadapp.R;

import java.util.ArrayList;
import java.util.List;

public class CustomSpinnerAdapter<T> extends ArrayAdapter<T> {
    LayoutInflater layoutInflater;
    private  ArrayList<T> myarrayList;
    public CustomSpinnerAdapter(@NonNull Context context, int resource, @NonNull ArrayList<T> objects) {
        super(context, resource, objects);
        layoutInflater = LayoutInflater.from(context);
        this.myarrayList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View rowView = layoutInflater.inflate(R.layout.custom_spinner_view, null,true);
        TextView label = rowView.findViewById(R.id.spinnerTextView);
        label.setText(String.format("%s", myarrayList.get(position) != null ? myarrayList.get(position) : ""));
        return rowView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
         convertView = layoutInflater.inflate(R.layout.custom_spinner_view, null,true);
        }
        TextView label = convertView.findViewById(R.id.spinnerTextView);
        label.setText(String.format("%s", myarrayList.get(position) != null ? myarrayList.get(position) : ""));
        return convertView;
    }
}
