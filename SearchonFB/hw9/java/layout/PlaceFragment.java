package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.xia4z.searchonfb.CustomListViewAdapter;
import com.example.xia4z.searchonfb.R;
import com.example.xia4z.searchonfb.ResultActivity;
import com.example.xia4z.searchonfb.Result_item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlaceFragment extends Fragment
        implements View.OnClickListener{

    View v;

    public PlaceFragment() {
        super();
        this.setArguments(new Bundle());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_place, container, false);

        Button button_previous = (Button) v.findViewById(R.id.place_previous);
        button_previous.setOnClickListener(this);
        Button button_next = (Button) v.findViewById(R.id.place_next);
        button_next.setOnClickListener(this);
        return v;
    }

    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.place_previous) {
            String url=this.getArguments().getString("previous");
            ((ResultActivity)getActivity()).getData(url);
        } else if (id == R.id.place_next) {
            String url=this.getArguments().getString("next");
            Log.d("new url",url);
            ((ResultActivity)getActivity()).getData(url);
        }
    }
}
