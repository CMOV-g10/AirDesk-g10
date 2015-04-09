package pt.ulisboa.tecnico.cmov.airdesk_g10.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pt.ulisboa.tecnico.cmov.airdesk_g10.AirDesk;
import pt.ulisboa.tecnico.cmov.airdesk_g10.R;
import pt.ulisboa.tecnico.cmov.airdesk_g10.core.Workspace;
import pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions.AirDeskException;

/**
 * Created by Pedro on 4/9/2015.
 */
public class JoinWSListCustomAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<Workspace> list = new ArrayList<Workspace>();
    private Context context;
    private AirDesk airDesk;

    public JoinWSListCustomAdapter(ArrayList<Workspace> list, Context context, AirDesk airDesk) {
        this.list = list;
        this.context = context;
        this.airDesk = airDesk;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        //return list.get(pos).getId();
        //just return 0 if your list items do not have an Id variable.
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.join_list_item, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        listItemText.setText(list.get(position).getWsname());

        //Handle buttons and add onClickListeners
        final Button joinBtn = (Button) view.findViewById(R.id.join_btn);

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int wid = list.get(position).getWsid();
                int uid = list.get(position).getWsowner().getUserid();
                try {
                    if (airDesk.getmDBHelper().removeSubscriptionFromUser(wid, uid))
                        list.remove(position); //or some other task
                } catch(AirDeskException a) {a.getMessage();}
                //notifyDataSetChanged();
            }
        });

        return view;
    }

}
