package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyCustomListAdapter extends BaseAdapter {

    private List<Country> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public MyCustomListAdapter(List<Country> listData, Context context) {
        this.listData = listData;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData == null ? 0 : listData.size();
    }

    @Override
    public Country getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class MyView {
        ImageView flag;
        TextView tvCountryName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyView view;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item, parent, false);
            view = new MyView();
            view.flag = convertView.findViewById(R.id.flag);
            view.tvCountryName = convertView.findViewById(R.id.text1);
            convertView.setTag(view);
        } else {
            view = (MyView) convertView.getTag();
        }

        Country P = this.listData.get(position);
        view.tvCountryName.setText(P.getCountryName());
        int imageId = context.getResources().getIdentifier(P.getCountryFlag(), "drawable", context.getPackageName());
        if (imageId != 0) {
            view.flag.setImageResource(imageId);
        } else {
            view.flag.setImageDrawable(null);
        }

        return convertView;
    }
}
