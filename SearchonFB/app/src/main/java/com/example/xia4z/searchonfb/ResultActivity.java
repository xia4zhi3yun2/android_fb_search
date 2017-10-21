package com.example.xia4z.searchonfb;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import layout.EventFragment;
import layout.GroupFragment;
import layout.PageFragment;
import layout.PlaceFragment;
import layout.UserFragment;

import static com.example.xia4z.searchonfb.R.id.container;

public class ResultActivity extends AppCompatActivity {


    public String json_string = "";

    final int[] ICONS = new int[]{
            R.drawable.users,
            R.drawable.pages,
            R.drawable.events,
            R.drawable.places,
            R.drawable.groups
    };

    Button user_previous;
    Button user_next;
    Button page_previous;
    Button page_next;
    Button event_previous;
    Button event_next;
    Button place_previous;
    Button place_next;
    Button group_previous;
    Button group_next;

    CustomListViewAdapter user_adapter;
    CustomListViewAdapter page_adapter;
    CustomListViewAdapter event_adapter;
    CustomListViewAdapter place_adapter;
    CustomListViewAdapter group_adapter;
    ArrayList<Result_item> user_items;
    ListView user_list;
    ArrayList<Result_item> page_items;
    ListView page_list;
    ArrayList<Result_item> event_items;
    ListView event_list;
    ArrayList<Result_item> place_items;
    ListView place_list;
    ArrayList<Result_item> group_items;
    ListView group_list;



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
        setContentView(R.layout.activity_result);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setIcon(ICONS[0]);
        tabLayout.getTabAt(1).setIcon(ICONS[1]);
        tabLayout.getTabAt(2).setIcon(ICONS[2]);
        tabLayout.getTabAt(3).setIcon(ICONS[3]);
        tabLayout.getTabAt(4).setIcon(ICONS[4]);

        tabLayout.setTabTextColors(Color.parseColor("#000000"), Color.parseColor("#000000"));

        ArrayList<Result_item> result_items=new ArrayList<Result_item>();
        user_adapter=new CustomListViewAdapter(this, R.layout.row_layout, result_items);
        page_adapter=new CustomListViewAdapter(this, R.layout.row_layout, result_items);
        event_adapter=new CustomListViewAdapter(this, R.layout.row_layout, result_items);
        place_adapter=new CustomListViewAdapter(this, R.layout.row_layout, result_items);
        group_adapter=new CustomListViewAdapter(this, R.layout.row_layout, result_items);

        //get json data ready for the table
        String url = this.getIntent().getExtras().getString("url");
        getData(url);


    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public static class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);
            switch (position) {
                case 0:
                    UserFragment userFragment = new UserFragment();
                    return userFragment;
                case 1:
                    PageFragment pageFragment = new PageFragment();
                    return pageFragment;
                case 2:
                    EventFragment eventFragment = new EventFragment();
                    return eventFragment;
                case 3:
                    PlaceFragment placeFragment = new PlaceFragment();
                    return placeFragment;
                case 4:
                    GroupFragment groupFragment = new GroupFragment();
                    return groupFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 5 total pages.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Users";
                case 1:
                    return "Pages";
                case 2:
                    return "Events";
                case 3:
                    return "Places";
                case 4:
                    return "Groups";
            }
            return null;
        }
    }

    public void getData(String url) {
        JSONAsyncTask task = new JSONAsyncTask();
        task.execute(url);
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
            json_string = result;
            //Log.d("result", result);

            user_previous = (Button) findViewById(R.id.user_previous);
            user_next = (Button) findViewById(R.id.user_next);
            page_previous = (Button) findViewById(R.id.page_previous);
            page_next = (Button) findViewById(R.id.page_next);
            event_previous = (Button) findViewById(R.id.event_previous);
            event_next = (Button) findViewById(R.id.event_next);
            place_previous = (Button) findViewById(R.id.place_previous);
            place_next = (Button) findViewById(R.id.place_next);
            group_previous = (Button) findViewById(R.id.group_previous);
            group_next = (Button) findViewById(R.id.group_next);

            try {
                JSONObject json = new JSONObject(json_string);

                //get all data from server
                if (json.has("user")) {
                    String user_string = json.getString("user");
                    user_list = (ListView) findViewById(R.id.user_list);
                    JSONObject user_json = new JSONObject(user_string);
                    JSONArray user_item = user_json.getJSONArray("data");
                    user_items = parseJsonResponse(user_item,"user");
                    user_adapter = new CustomListViewAdapter(ResultActivity.this, R.layout.row_layout, user_items);
                    user_list.setAdapter(user_adapter);
                    user_adapter.notifyDataSetChanged();
                    //createAdapter(user_string, user_list,"user",user_adapter);
                    createButton(user_previous, user_next, user_string,0);


                    String page_string = json.getString("page");
                    page_list = (ListView) findViewById(R.id.page_list);
                    JSONObject page_json = new JSONObject(page_string);
                    JSONArray page_item = page_json.getJSONArray("data");
                    page_items = parseJsonResponse(page_item,"page");
                    page_adapter = new CustomListViewAdapter(ResultActivity.this, R.layout.row_layout, page_items);
                    page_list.setAdapter(page_adapter);
                    page_adapter.notifyDataSetChanged();
                    //createAdapter(page_string, page_list,"page",page_adapter);
                    createButton(page_previous, page_next, page_string,1);

                    String event_string = json.getString("event");
                    event_list = (ListView) findViewById(R.id.event_list);
                    JSONObject event_json = new JSONObject(event_string);
                    JSONArray event_item = event_json.getJSONArray("data");
                    event_items = parseJsonResponse(event_item,"event");
                    event_adapter = new CustomListViewAdapter(ResultActivity.this, R.layout.row_layout, event_items);
                    event_list.setAdapter(event_adapter);
                    event_adapter.notifyDataSetChanged();
                    //createAdapter(event_string, event_list,"event",event_adapter);
                    createButton(event_previous, event_next, event_string,2);

                    String place_string = json.getString("place");
                    place_list = (ListView) findViewById(R.id.place_list);
                    JSONObject place_json = new JSONObject(place_string);
                    JSONArray place_item = place_json.getJSONArray("data");
                    place_items = parseJsonResponse(place_item,"place");
                    place_adapter = new CustomListViewAdapter(ResultActivity.this, R.layout.row_layout, place_items);
                    place_list.setAdapter(place_adapter);
                    place_adapter.notifyDataSetChanged();
                    //createAdapter(place_string, place_list,"place",place_adapter);
                    createButton(place_previous, place_next, place_string,3);


                    String group_string = json.getString("group");
                    group_list = (ListView) findViewById(R.id.group_list);
                    JSONObject group_json = new JSONObject(group_string);
                    JSONArray group_item = group_json.getJSONArray("data");
                    group_items = parseJsonResponse(group_item,"group");
                    group_adapter = new CustomListViewAdapter(ResultActivity.this, R.layout.row_layout, group_items);
                    group_list.setAdapter(group_adapter);
                    group_adapter.notifyDataSetChanged();
                    //createAdapter(group_string, group_list,"group",group_adapter);
                    createButton(group_previous, group_next, group_string,4);
                }

                //paging
                else {
                    int index = mViewPager.getCurrentItem();
                    //int index=position;
                    //Log.d("index",String.valueOf(index));
                    if (index == 0) {
                        ListView list = (ListView) findViewById(R.id.user_list);
                        createAdapter(result, list,"user",user_adapter);
                        createButton(user_previous, user_next, result,0);
                    }
                    if (index == 1) {
                        ListView list = (ListView) findViewById(R.id.page_list);
                        createAdapter(result, list,"page",page_adapter);
                        createButton(page_previous, page_next, result,1);
                    }
                    if (index == 2) {
                        ListView list = (ListView) findViewById(R.id.event_list);
                        createAdapter(result, list,"event",event_adapter);
                        createButton(event_previous, event_next, result,2);
                    }
                    if (index == 3) {
                        ListView list = (ListView) findViewById(R.id.place_list);
                        createAdapter(result, list,"place",place_adapter);
                        createButton(place_previous, place_next, result,3);
                    }
                    if (index == 4) {
                        ListView list = (ListView) findViewById(R.id.group_list);
                        createAdapter(result, list,"group",group_adapter);
                        createButton(group_previous, group_next, result,4);
                    }

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }


    public void createAdapter(String re_string, ListView listView,String type,CustomListViewAdapter adapter) {
        try {
            JSONObject row_json = new JSONObject(re_string);
            JSONArray items = row_json.getJSONArray("data");
            ArrayList<Result_item> result_items = parseJsonResponse(items,type);

            adapter = new CustomListViewAdapter(this, R.layout.row_layout, result_items);
            listView.setAdapter(adapter);

            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void createButton(Button button_previous, Button button_next, String re_string,int position) {
        try {
            JSONObject row_json = new JSONObject(re_string);
            JSONObject paging = row_json.getJSONObject("paging");

            //Log.d("paging",paging.toString());
            if (paging.has("previous")) {
                String previous = paging.getString("previous");
                button_previous.setVisibility(View.VISIBLE);

                Fragment fragment = getSupportFragmentManager().findFragmentByTag("android:switcher:" + mViewPager.getId() + ":" + position);
                // based on the current position you can then cast the page to the correct Fragment class and call some method inside that fragment to reload the data:
                if (fragment != null) {
                    fragment.getArguments().putSerializable("previous", previous);
                }
            } else {
                button_previous.setVisibility(View.GONE);
            }

            if (paging.has("next")) {
                String next = paging.getString("next");
                button_next.setVisibility(View.VISIBLE);

                Fragment fragment = getSupportFragmentManager().findFragmentByTag("android:switcher:" + mViewPager.getId() + ":" + position);
                // based on the current position you can then cast the page to the correct Fragment class and call some method inside that fragment to reload the data:
                if (fragment != null) {
                    fragment.getArguments().putSerializable("next", next);
                }
            } else {
                button_next.setVisibility(View.GONE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //parse response data after asynctask finished
    public ArrayList<Result_item> parseJsonResponse(JSONArray items,String type) {

        ArrayList results = new ArrayList<Result_item>();
        try {
            for (int i = 0; i < items.length(); i++) {
                JSONObject c = items.getJSONObject(i);

                String name = c.getString("name");
                String id = c.getString("id");
                JSONObject pic = c.getJSONObject("picture");
                JSONObject pic_data = pic.getJSONObject("data");
                String url = pic_data.getString("url");

                Result_item result = new Result_item();
                result.setName(name);
                result.setUrl(url);
                result.setId(id);
                result.setType(type);
                results.add(result);
            }
            //adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return results;
    }


    //return to previous page
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();

        user_adapter = new CustomListViewAdapter(this, R.layout.row_layout, user_items);
        if(user_list!=null){user_list.setAdapter(user_adapter);}
        user_adapter.notifyDataSetChanged();

        page_adapter = new CustomListViewAdapter(this, R.layout.row_layout, page_items);
        if(page_list!=null){page_list.setAdapter(page_adapter);}
        page_adapter.notifyDataSetChanged();

        event_adapter = new CustomListViewAdapter(this, R.layout.row_layout, event_items);
        if(event_list!=null){event_list.setAdapter(event_adapter);}
        place_adapter.notifyDataSetChanged();

        place_adapter = new CustomListViewAdapter(this, R.layout.row_layout, place_items);
        if(place_list!=null){place_list.setAdapter(place_adapter);}
        event_adapter.notifyDataSetChanged();

        group_adapter = new CustomListViewAdapter(this, R.layout.row_layout, group_items);
        if(group_list!=null){group_list.setAdapter(group_adapter);}
        group_adapter.notifyDataSetChanged();

        //Refresh your stuff here
    }

}
