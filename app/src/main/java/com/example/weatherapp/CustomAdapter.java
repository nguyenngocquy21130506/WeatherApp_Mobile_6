package com.example.weatherapp;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<Thoitiet> arrayList;

    public CustomAdapter(Context context, ArrayList<Thoitiet> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_lv,null);

        Thoitiet thoitiet = arrayList.get(position);
        TextView txtDay = convertView.findViewById(R.id.textViewNgay);
        TextView txtStatus = convertView.findViewById(R.id.textViewStatus);
        TextView txtMaxTemp = convertView.findViewById(R.id.textViewMaxTemp);
        TextView txtMinTemp = convertView.findViewById(R.id.textViewMinTemp);
        ImageView imgStatus = convertView.findViewById(R.id.imageViewStatus);

        txtDay.setText(thoitiet.day);
        txtStatus.setText(thoitiet.status);
        txtMaxTemp.setText(thoitiet.maxTerm);
        txtMinTemp.setText(thoitiet.minTerm);

        Picasso.get().load("//cdn.weatherapi.com/weather/64x64/night/"+thoitiet.image+".png").into(imgStatus);
        return convertView;
    }
}
