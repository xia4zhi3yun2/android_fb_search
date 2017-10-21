package layout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.xia4z.searchonfb.DetailActivity;
import com.example.xia4z.searchonfb.R;
import com.example.xia4z.searchonfb.ResultActivity;


public class UserFragment extends Fragment
        implements View.OnClickListener {

    View v;

    public UserFragment() {
        super();
        this.setArguments(new Bundle());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_user, container, false);

        Button button_previous = (Button) v.findViewById(R.id.user_previous);
        button_previous.setOnClickListener(this);
        Button button_next = (Button) v.findViewById(R.id.user_next);
        button_next.setOnClickListener(this);

        //json_string=getActivity().getIntent().getExtras().getString("json_data");
//        String json_string = ((ResultActivity)getActivity()).getJson_string();
//        if((json_string == null){
//            task.setListener(listener);
//        }
//        else
//            onComplete(object);
//    }
//        Log.d("user",json_string);
//        try {
//            JSONObject json = new JSONObject(json_string);
//            String row_string = json.getString("user");
//            row_json = new JSONObject(row_string);
//            JSONArray items = row_json.getJSONArray("data");
//            ArrayList<Result_item> user_result = ((ResultActivity) getActivity()).parseJsonResponse(items);
//
//            ListView listView = (ListView) v.findViewById(R.id.user_list);
//            adapter = new CustomListViewAdapter(getActivity(), R.layout.row_layout, user_result);
//            listView.setAdapter(adapter);
//        }catch (JSONException e) {
//            e.printStackTrace();
//        }
        //ArrayList<Result_item> user_result = ((ResultActivity)getActivity()).getUser_result();

        return v;
    }


    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.user_previous) {
            String url = this.getArguments().getString("previous");
            ((ResultActivity) getActivity()).getData(url);
        } else if (id == R.id.user_next) {
            String url = this.getArguments().getString("next");
            Log.d("new url", url);
            ((ResultActivity) getActivity()).getData(url);
        }
    }


}
