package com.example.vaadapp.Helpers;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vaadapp.Models.Apartment;
import com.example.vaadapp.R;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomRecycleAdapter extends RecyclerView.Adapter<CustomRecycleAdapter.ViewHolder> {

    private List list;
    private LayoutInflater mInflater;
    private final OnItemClickListener listener;

    public CustomRecycleAdapter(Context context, ArrayList arr, OnItemClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.list = arr;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("parent igor look here", "onCreateViewHolder: " + parent+ "viewtype: " + viewType);

        View view = mInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTextView().setText(this.list.get(position).toString());
        holder.bind(this.list.get(position), listener);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            textView =  view.findViewById(R.id.apartmentText);
        }

        public TextView getTextView() {
            return textView;
        }

        public void bind(final Object item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }

    }


    public interface OnItemClickListener {
        void onItemClick(Object item);
    }
    public void filterList(ArrayList filteredList) {
        list = filteredList;
        notifyDataSetChanged();
    }

}

