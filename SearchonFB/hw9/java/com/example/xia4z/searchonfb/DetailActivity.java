package com.example.xia4z.searchonfb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import layout.AlbumFragment;
import layout.EventFragment;
import layout.GroupFragment;
import layout.PageFragment;
import layout.PlaceFragment;
import layout.PostFragment;
import layout.UserFragment;

import static com.facebook.FacebookSdk.getApplicationContext;

public class DetailActivity extends AppCompatActivity {

    Menu menu;
    final int[] ICONS = new int[]{
            R.drawable.albums,
            R.drawable.posts,
    };
    List<String> albumHeader;
    HashMap<String, List<String>> albumChild;
    String id;
    String url;
    String name;
    String type;

    CallbackManager callbackManager;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ShareDialog shareDialog;


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(1);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setIcon(ICONS[0]);
        tabLayout.getTabAt(1).setIcon(ICONS[1]);
        tabLayout.setTabTextColors(Color.parseColor("#000000"), Color.parseColor("#000000"));

        callbackManager = CallbackManager.Factory.create();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = sharedPreferences.edit();



        id = this.getIntent().getExtras().getString("id");
        name = this.getIntent().getExtras().getString("name");
        url = this.getIntent().getExtras().getString("url");
        type = this.getIntent().getExtras().getString("type");
        String url = "http://Sample-env.vjzt5pgmpx.us-west-2.elasticbeanstalk.com/access_fb.php?f=details&id=" + id;
        JSONAsyncTask task = new JSONAsyncTask();
        task.execute(url);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        this.menu = menu;
        updateMenuTitles();
        return true;
    }


    private void updateMenuTitles() {
        MenuItem bedMenuItem = menu.findItem(R.id.add_favorite);
        if (sharedPreferences.contains(id)) {
            bedMenuItem.setTitle("Remove from Favorite");
        } else {
            bedMenuItem.setTitle("Add to Favorite");
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int index = item.getItemId();

        if (index == android.R.id.home) {
            finish();
        } else if (index == R.id.post_to) {
            shareDialog = new ShareDialog(this);
            shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                public void onSuccess(Sharer.Result result) {

                    Toast.makeText(DetailActivity.this, "Done!",Toast.LENGTH_LONG).show();

                }

                @Override
                public void onCancel() {
                    Toast.makeText(DetailActivity.this, "Share canceled",Toast.LENGTH_LONG).show();
                }

                @Override
                public void onError(FacebookException e) {
                    Toast.makeText(DetailActivity.this, "Share error",Toast.LENGTH_LONG).show();
                }

            });

            if (ShareDialog.canShow(ShareLinkContent.class)) {
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setContentTitle(name)
                        .setContentUrl(Uri.parse("https://developers.facebook.com"+id))
                        .setContentDescription("SEARCH FROM FB USC CSCI571")
                        .setImageUrl(Uri.parse(url))
                        .build();
                shareDialog.show(linkContent,ShareDialog.Mode.FEED);
            }
        } else if (index == R.id.add_favorite) {

            List<String> value = new ArrayList<String>();
            value.add(0, id);
            value.add(1, name);
            value.add(2, url);
            value.add(3, type);
            String parsed_value = new Gson().toJson(value);

            if (sharedPreferences.contains(id)) {
                editor.remove(id);
            } else {
                editor.putString(id, parsed_value);
            }
            editor.commit();
            updateMenuTitles();
            //Log.d("sharedpreference",sharedPreferences.getString(id,null));
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    AlbumFragment albumFragment = new AlbumFragment();
                    return albumFragment;
                case 1:
                    PostFragment postFragment = new PostFragment();
                    return postFragment;
            }
            return null;
        }


        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Albums";
                case 1:
                    return "Posts";
            }
            return null;
        }
    }


    public class JSONAsyncTask extends AsyncTask<String, Void, String> {

        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;


        @Override
        protected String doInBackground(String... params) {
            String stringUrl = params[0];
            String result = "";
            String inputLine;

            try {
                //Create a URL object holding our url
                URL myUrl = new URL(stringUrl);
                HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();

                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                connection.connect();
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());

                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();

                while ((inputLine = reader.readLine()) != null) {
                    stringBuilder.append(inputLine);
                }
                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();
                //Set our result equal to our stringBuilder
                result = stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
                result = null;
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            JSONObject json = new JSONObject();
//            Log.d("result", result);

            try {
                json = new JSONObject(result);
            } catch (JSONException e) {
                TextView textView = (TextView) findViewById(R.id.album_text);
                textView.setVisibility(View.VISIBLE);
                TextView textView2 = (TextView) findViewById(R.id.post_text);
                textView2.setVisibility(View.VISIBLE);
            }

            try {
                if (!json.has("albums")) {
                    TextView textView = (TextView) findViewById(R.id.album_text);
                    textView.setVisibility(View.VISIBLE);
                } else {
                    String album_string = json.getString("albums");
                    ExpandableListView album_list = (ExpandableListView) findViewById(R.id.albums);
                    JSONObject row_json = new JSONObject(album_string);
                    JSONArray items = row_json.getJSONArray("data");
                    parseAlbum(items);

                    ExpandableListAdapter adapter = new ExpandableListAdapter(DetailActivity.this, DetailActivity.this, albumHeader, albumChild);
                    album_list.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

                if (!json.has("posts")) {
                    TextView textView = (TextView) findViewById(R.id.post_text);
                    textView.setVisibility(View.VISIBLE);
                } else {
                    String page_string = json.getString("posts");
                    ListView post_list = (ListView) findViewById(R.id.post_list);
                    JSONObject row_json = new JSONObject(page_string);
                    JSONArray items = row_json.getJSONArray("data");
                    ArrayList<Post_item> post_items = parsePost(items);

                    PostListViewAdapter adapter = new PostListViewAdapter(DetailActivity.this, R.layout.post_layout, post_items, name, url);
                    post_list.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void parseAlbum(JSONArray items) {
        albumHeader = new ArrayList<String>();
        albumChild = new HashMap<String, List<String>>();
        try {
            for (int i = 0; i < items.length(); i++) {
                JSONObject c = items.getJSONObject(i);

                String name = c.getString("name");
                //Log.d("name", name);
                albumHeader.add(name);

                List<String> item = new ArrayList<String>();
                JSONObject pic = c.getJSONObject("photos");
                JSONArray pic_data = pic.getJSONArray("data");
                for (int j = 0; j < pic_data.length(); j++) {
                    JSONObject b = pic_data.getJSONObject(j);
                    String pic_id = b.getString("id");
                    String url = "https://graph.facebook.com/v2.8/" + pic_id + "/picture?access_token=EAADBYyD62UIBAIL2nXaV4z5DY5b3B2oW1IVlCKszVZAuXlZCd1ZBsmZCJkKdAgSJJI8YTq37oaolrevy8O1rOTi6dV7JNK1iUYQZAH1iPicZBHsklxtnSAMgqa5Pb8q5RrkeZBs41S1Rtpz8rwbHB0JuSY6eQ4RBsIZD";
                    //Log.d("url", url);
                    item.add(url);
                }

                albumChild.put(albumHeader.get(i), item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Post_item> parsePost(JSONArray items) {
        ArrayList results = new ArrayList<Post_item>();
        try {
            for (int i = 0; i < items.length(); i++) {
                JSONObject c = items.getJSONObject(i);
                if (c.has("message")) {
                    String post = c.getString("message");
                    String time = c.getString("created_time");
                    time=time.replace("T"," ").replace("+0000","");

//                    Log.d("test", post);
                    Post_item re = new Post_item();
                    re.setPost(post);
                    re.setTime(time);
                    results.add(re);
                }
            }
            //adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return results;

    }


}
