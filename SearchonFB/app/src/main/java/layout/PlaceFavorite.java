package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xia4z.searchonfb.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlaceFavorite extends Fragment {


    public PlaceFavorite() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.favorite_place, container, false);
    }

}
