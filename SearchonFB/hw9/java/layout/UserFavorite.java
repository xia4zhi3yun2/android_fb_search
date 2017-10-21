package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.xia4z.searchonfb.CustomListViewAdapter;
import com.example.xia4z.searchonfb.FavFragment;
import com.example.xia4z.searchonfb.R;
import com.example.xia4z.searchonfb.Result_item;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFavorite extends Fragment {


    public UserFavorite() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.favorite_user, container, false);

        return v;
    }



}
