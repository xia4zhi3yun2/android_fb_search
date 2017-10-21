package com.example.xia4z.searchonfb;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by xia4z on 4/25/2017.
 */

public class PostListViewAdapter extends ArrayAdapter<Post_item> {
    private Activity activity;
    private String name;
    private String url;

    public PostListViewAdapter(Activity activity, int resource, List<Post_item> results,String name,String url) {
        super(activity,resource,results);
        this.activity = activity;
        this.name=name;
        this.url=url;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PostListViewAdapter.ViewHolder holder = null;
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        // If holder not exist then locate all view from UI file.
        if (convertView == null) {
            // inflate UI from XML file
            convertView = inflater.inflate(R.layout.post_layout, parent, false);
            // get all UI view
            holder = new PostListViewAdapter.ViewHolder(convertView);
            // set tag for holder
            convertView.setTag(holder);
        } else {
            // if holder created, get tag from view
            holder = (PostListViewAdapter.ViewHolder) convertView.getTag();
        }

        final Post_item result = getItem(position);

        holder.post.setText(result.getPost());
        holder.time.setText(result.getTime());
        holder.name.setText(name);
        Picasso.with(activity).load(url).into(holder.user_pic);



        return convertView;
    }

    private static class ViewHolder {
        private TextView post;
        private TextView time;
        private TextView name;
        private ImageView user_pic;

        public ViewHolder(View v) {
            post = (TextView) v.findViewById(R.id.post);
            time=(TextView)v.findViewById(R.id.time);
            name=(TextView)v.findViewById(R.id.name);
            user_pic=(ImageView)v.findViewById(R.id.user_pic);
        }
    }


}
