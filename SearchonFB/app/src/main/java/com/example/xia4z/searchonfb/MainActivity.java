package com.example.xia4z.searchonfb;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,LocationListener {

    SharedPreferences sharedPreferences;
    String provider;
    private String latitude="34.019494",longitude="-118.289441";
    protected boolean gps_enabled,network_enabled;
    protected LocationManager locationManager;
    protected com.google.android.gms.location.LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        HomeFragment homeFragment=new HomeFragment();
        FragmentManager manager=getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.relative_layout,homeFragment,homeFragment.getTag()).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if ( ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
                        ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION },200 );
                }
        if( ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION },200 );
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);


//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.clear();
//        editor.commit();
    }


    public void onLocationChanged(Location location) {
//        Log.d("location","called!");
        latitude=String.valueOf(location.getLatitude());
        longitude=String.valueOf(location.getLongitude());
//        Log.d("location",String.valueOf(location.getLatitude()));
//        Log.d("location",String.valueOf(location.getLongitude()));
    }


    public void onProviderDisabled(String provider) {
//        Log.d("Latitude","disable");
    }


    public void onProviderEnabled(String provider) {
//        Log.d("Latitude","enable");
    }


    public void onStatusChanged(String provider, int status, Bundle extras) {
//        Log.d("Latitude","status");
    }

    public String getLatitude() {
        return String.valueOf(latitude);
    }

    public String getLongitude() {
        return String.valueOf(longitude);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            HomeFragment homeFragment=new HomeFragment();
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relative_layout,homeFragment,homeFragment.getTag()).commit();
        } else if (id == R.id.nav_fav) {
            FavFragment favFragment=new FavFragment();
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relative_layout,favFragment,favFragment.getTag()).commit();
        } else if(id == R.id.nav_about){
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




}

