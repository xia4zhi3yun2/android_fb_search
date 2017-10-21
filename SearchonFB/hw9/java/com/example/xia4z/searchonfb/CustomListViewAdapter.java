package com.example.xia4z.searchonfb;

/**
 * Created by xia4z on 4/21/2017.
 */

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CustomListViewAdapter extends ArrayAdapter<Result_item> {

    private Activity activity;

    public CustomListViewAdapter(Activity activity, int resource, List<Result_item> results) {
        super(activity, resource, results);
        this.activity = activity;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        // If holder not exist then locate all view from UI file.
        if (convertView == null) {
            // inflate UI from XML file
            convertView = inflater.inflate(R.layout.row_layout, parent, false);
            // get all UI view
            holder = new ViewHolder(convertView);
            // set tag for holder
            convertView.setTag(holder);
        } else {
            // if holder created, get tag from view
            holder = (ViewHolder) convertView.getTag();
        }

        final Result_item result = getItem(position);

        holder.name.setText(result.getName());
        Picasso.with(activity).load(result.getUrl()).into(holder.url);
        holder.detail.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(activity, DetailActivity.class);
                intent.putExtra("id", result.getId());
                intent.putExtra("url",result.getUrl());
                intent.putExtra("name",result.getName());
                intent.putExtra("type",result.getType());
                activity.startActivity(intent);
            }
        });
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
//        Log.d("sharedpreference",sharedPreferences.getString(result.getId(),null));
        Log.d("sharedPreferences","refresh");
        if(sharedPreferences.contains(result.getId())) {
//            Log.d("sharedpreference","idcontains");
            holder.fav.setImageResource(R.drawable.favorites_on);
        }else{
            //holder.fav.setImageResource(R.drawable.favorites_off);
        }
        return convertView;
    }

    private static class ViewHolder {
        private TextView name;
        private ImageView url;
        private ImageView detail;
        private ImageView fav;

        public ViewHolder(View v) {
            name = (TextView) v.findViewById(R.id.name);
            url = (ImageView) v.findViewById(R.id.url);
            detail=(ImageView)v.findViewById(R.id.detail);
            fav=(ImageView)v.findViewById(R.id.fav);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}