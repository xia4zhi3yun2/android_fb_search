package com.example.xia4z.searchonfb;



import android.content.Context;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.location.LocationListener;




/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment
    implements View.OnClickListener{

    EditText et;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.fragment_home, container, false);

        et = (EditText) v.findViewById(R.id.keyword);
        Button button_clear = (Button) v.findViewById(R.id.clear);
        button_clear.setOnClickListener(this);
        Button button_search = (Button) v.findViewById(R.id.search);
        button_search.setOnClickListener(this);

        return v;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.clear) {
            et.setText("");
        }else if(id == R.id.search){
            // call asynctask to get data

            String keyword = et.getText().toString();
            if (TextUtils.isEmpty(keyword.trim())) {
                Toast.makeText(getActivity(), "input cannot be empty!", Toast.LENGTH_SHORT).show();
            } else {




                //locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, getActivity());

//                LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//                Location utilLocation = null;
//                List<String> providers = manager.getProviders(false);
//                for(String provider : providers){
//                    if ( ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
//                        ActivityCompat.requestPermissions( getActivity(), new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION },200 );
//                    }
//                    utilLocation = manager.getLastKnownLocation(provider);
//                }

//                Log.d("location",String.valueOf(utilLocation.getLatitude()));
//                Log.d("location",String.valueOf(utilLocation.getLongitude()));

                String latitude=((MainActivity)getActivity()).getLatitude();
                String longitude=((MainActivity)getActivity()).getLongitude();

                String url="http://Sample-env.vjzt5pgmpx.us-west-2.elasticbeanstalk.com/access_fb.php?f=all&keyword="+ keyword+ "&latitude=" + latitude + "&longtitude=" + longitude;
                Log.d("url",url);


                Intent intent = new Intent(getActivity(), ResultActivity.class);
                intent.putExtra("url",url);
                getActivity().startActivity(intent);
            }

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Search on FB");
    }





}
