package com.example.parkit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Admin_Adapter extends  RecyclerView.Adapter<Admin_Adapter.ViewHolder>{

    String titles;
    LayoutInflater inflater;
    public Admin_Adapter(Context ctx, String titles){
        this.titles = titles;
        this.inflater = LayoutInflater.from(ctx);
    }


    @NonNull
    @Override
    public Admin_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.admin_card , parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Admin_Adapter.ViewHolder holder, int position) {
        holder.title.setText(titles);
    }

    @Override
    public int getItemCount() {
        return 1;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.name);
        }
    }
}
