package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.xia4z.searchonfb.R;
import com.example.xia4z.searchonfb.ResultActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment
        implements View.OnClickListener{

    View v;

    public EventFragment() {
        super();
        this.setArguments(new Bundle());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_event, container, false);
        Button button_previous = (Button) v.findViewById(R.id.event_previous);
        button_previous.setOnClickListener(this);
        Button button_next = (Button) v.findViewById(R.id.event_next);
        button_next.setOnClickListener(this);
        return v;
    }

    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.event_previous) {
            String url=this.getArguments().getString("previous");
            ((ResultActivity)getActivity()).getData(url);
        } else if (id == R.id.event_next) {
            String url=this.getArguments().getString("next");
            Log.d("new url",url);
            ((ResultActivity)getActivity()).getData(url);
        }
    }

}
