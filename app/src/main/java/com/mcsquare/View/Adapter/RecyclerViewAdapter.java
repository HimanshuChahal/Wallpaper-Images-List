package com.mcsquare.View.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mcsquare.Model.ImageModel;
import com.mcsquare.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewViewHolder> {

    Context context;
    List<ImageModel> imageModels;
    public RecyclerViewAdapter(Context context, List<ImageModel> imageModels)
    {
        this.context=context;
        this.imageModels=imageModels;
    }

    @NonNull
    @Override
    public RecyclerViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewViewHolder holder, int position) {

        Picasso.with(context).load(imageModels.get(position).getImageURL()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return imageModels.size();
    }

    static class RecyclerViewViewHolder extends RecyclerView.ViewHolder
    {

        ImageView imageView;

        public RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.imageView);

        }
    }

}
