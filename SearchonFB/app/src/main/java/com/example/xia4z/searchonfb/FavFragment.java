package com.example.xia4z.searchonfb;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import layout.EventFavorite;
import layout.EventFragment;
import layout.GroupFavorite;
import layout.GroupFragment;
import layout.PageFavorite;
import layout.PageFragment;
import layout.PlaceFavorite;
import layout.PlaceFragment;
import layout.UserFavorite;
import layout.UserFragment;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavFragment extends Fragment {
    final int[] ICONS = new int[]{
            R.drawable.users,
            R.drawable.pages,
            R.drawable.events,
            R.drawable.places,
            R.drawable.groups
    };
    static ArrayList<Result_item> user;
    static ArrayList<Result_item> page;
    static ArrayList<Result_item> event;
    static ArrayList<Result_item> place;
    static ArrayList<Result_item> group;
    View v;
    static CustomListViewAdapter user_adapter;
    static CustomListViewAdapter page_adapter;
    static CustomListViewAdapter event_adapter;
    static CustomListViewAdapter place_adapter;
    static CustomListViewAdapter group_adapter;
    static ListView user_fav;
    static ListView event_fav;
    static ListView page_fav;
    static ListView place_fav;
    static ListView group_fav;

    //static UserFavorite userFavorite;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_fav, container, false);

        //get data
        //View view=inflater.inflate(R.layout.fragment_user,container,false);
        //Context cont=getActivity();


        //create tab
        mSectionsPagerAdapter = new FavFragment.SectionsPagerAdapter(getChildFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) v.findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) v.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(4);

        tabLayout.getTabAt(0).setIcon(ICONS[0]);
        tabLayout.getTabAt(1).setIcon(ICONS[1]);
        tabLayout.getTabAt(2).setIcon(ICONS[2]);
        tabLayout.getTabAt(3).setIcon(ICONS[3]);
        tabLayout.getTabAt(4).setIcon(ICONS[4]);


        user_adapter=new CustomListViewAdapter(getActivity(), R.layout.row_layout,user);
        page_adapter=new CustomListViewAdapter(getActivity(), R.layout.row_layout, page);
        event_adapter=new CustomListViewAdapter(getActivity(), R.layout.row_layout, event);
        place_adapter=new CustomListViewAdapter(getActivity(), R.layout.row_layout, place);
        group_adapter=new CustomListViewAdapter(getActivity(), R.layout.row_layout, group);

        tabLayout.setTabTextColors(Color.parseColor("#000000"),Color.parseColor("#000000"));
        getData();

        return v;
    }

    public void getData(){
        user=new ArrayList<Result_item>();
        page=new ArrayList<Result_item>();
        event=new ArrayList<Result_item>();
        place=new ArrayList<Result_item>();
        group=new ArrayList<Result_item>();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        Map<String,?> keys = sharedPreferences.getAll();

        for(Map.Entry<String,?> entry : keys.entrySet()){
            Result_item item=new Result_item();

            String key=entry.getKey().toString();
            String parsed_value=entry.getValue().toString();

            if(!key.contains("com.facebook")) {
                Gson gson = new Gson();
//                Log.d("PARSE",parsed_value);
                ArrayList<String> value = gson.fromJson(parsed_value, ArrayList.class);
                item.setName(value.get(1));
                item.setUrl(value.get(2));
                item.setId(value.get(0));

                if (value.get(3).equals("user")) {
                    user.add(item);
                }
                if (value.get(3).equals("page")) {
                    page.add(item);
                }
                if (value.get(3).equals("event")) {
                    event.add(item);
                }
                if (value.get(3).equals("place")) {
                    place.add(item);
                }
                if (value.get(3) .equals( "group")) {
                    group.add(item);
                }
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Favorites");

        getData();
        user_adapter = new CustomListViewAdapter(getActivity(), R.layout.row_layout, user);
        if(user_fav!=null){user_fav.setAdapter(user_adapter);}
        user_adapter.notifyDataSetChanged();

        page_adapter = new CustomListViewAdapter(getActivity(), R.layout.row_layout, page);
        if(page_fav!=null){page_fav.setAdapter(page_adapter);}
        page_adapter.notifyDataSetChanged();

        event_adapter = new CustomListViewAdapter(getActivity(), R.layout.row_layout, event);
        if(event_fav!=null){event_fav.setAdapter(event_adapter);}
        place_adapter.notifyDataSetChanged();

        place_adapter = new CustomListViewAdapter(getActivity(), R.layout.row_layout, place);
        if(place_fav!=null){place_fav.setAdapter(place_adapter);}
        event_adapter.notifyDataSetChanged();

        group_adapter = new CustomListViewAdapter(getActivity(), R.layout.row_layout, group);
        if(group_fav!=null){group_fav.setAdapter(group_adapter);}
        group_adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static FavFragment.PlaceholderFragment newInstance(int sectionNumber) {
            FavFragment.PlaceholderFragment fragment = new FavFragment.PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            if(getArguments().getInt(ARG_SECTION_NUMBER)==1){
                View rootView = inflater.inflate(R.layout.favorite_user, container, false);
                user_fav=(ListView) rootView.findViewById(R.id.user_fav);
                user_adapter = new CustomListViewAdapter(getActivity(), R.layout.row_layout, user);
                user_fav.setAdapter(user_adapter);
                user_adapter.notifyDataSetChanged();
                return rootView;

            }else if(getArguments().getInt(ARG_SECTION_NUMBER)==2){
                View rootView = inflater.inflate(R.layout.favorite_page, container, false);
                page_fav=(ListView) rootView.findViewById(R.id.page_fav);
                page_adapter = new CustomListViewAdapter(getActivity(), R.layout.row_layout, page);
                page_fav.setAdapter(page_adapter);
                page_adapter.notifyDataSetChanged();
                return rootView;

            }else if(getArguments().getInt(ARG_SECTION_NUMBER)==3){
                View rootView = inflater.inflate(R.layout.favorite_event, container, false);
                event_fav=(ListView) rootView.findViewById(R.id.event_fav);
                event_adapter = new CustomListViewAdapter(getActivity(), R.layout.row_layout, event);
                event_fav.setAdapter(event_adapter);
                event_adapter.notifyDataSetChanged();
                return rootView;
            }else if(getArguments().getInt(ARG_SECTION_NUMBER)==4){
                View rootView = inflater.inflate(R.layout.favorite_place, container, false);
                place_fav=(ListView) rootView.findViewById(R.id.place_fav);
                place_adapter = new CustomListViewAdapter(getActivity(), R.layout.row_layout, place);
                place_fav.setAdapter(place_adapter);
                place_adapter.notifyDataSetChanged();
                return rootView;

            }else{
                View rootView = inflater.inflate(R.layout.favorite_group, container, false);
                group_fav=(ListView) rootView.findViewById(R.id.group_fav);
                group_adapter = new CustomListViewAdapter(getActivity(), R.layout.row_layout, group);
                group_fav.setAdapter(group_adapter);
                group_adapter.notifyDataSetChanged();
                return rootView;
            }

        }
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
            return FavFragment.PlaceholderFragment.newInstance(position + 1);
//            switch (position) {
//                case 0:
//                    UserFragment userFragment = new UserFragment();
//                    return userFragment;
//                case 1:
//                    PageFragment pageFragment = new PageFragment();
//                    return pageFragment;
//                case 2:
//                    EventFragment eventFragment = new EventFragment();
//                    return eventFragment;
//                case 3:
//                    PlaceFragment placeFragment = new PlaceFragment();
//                    return placeFragment;
//                case 4:
//                    GroupFragment groupFragment = new GroupFragment();
//                    return groupFragment;
//            }
//            return null;
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





}
