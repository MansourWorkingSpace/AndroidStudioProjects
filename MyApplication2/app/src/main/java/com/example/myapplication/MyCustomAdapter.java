package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.ViewHolder> {

    private List<Country> country_list;
    private Context context;

    public MyCustomAdapter(List<Country> country_list, Context context) {
        this.country_list = country_list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Country c = country_list.get(position);
        holder.tvCountryName.setText(c.getCountryName());
        int imageId = context.getResources().getIdentifier(c.getCountryFlag(), "drawable", context.getPackageName());
        if (imageId != 0) {
            holder.flag.setImageResource(imageId);
        } else {
            holder.flag.setImageDrawable(null);
        }
    }

    @Override
    public int getItemCount() {
        return country_list == null ? 0 : country_list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView flag;
        TextView tvCountryName;

        ViewHolder(View itemView) {
            super(itemView);
            flag = itemView.findViewById(R.id.flag);
            tvCountryName = itemView.findViewById(R.id.text1);
        }
    }
}
