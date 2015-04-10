package pt.ulisboa.tecnico.cmov.airdesk_g10.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

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
        final ToggleButton joinBtn = (ToggleButton) view.findViewById(R.id.join_btn);
        final int wid = list.get(position).getWsid();
        final int uid = airDesk.getLoggedUser().getUserid();

        boolean isSubscribed;
        try{
            isSubscribed = airDesk.getmDBHelper().isSubscribed(uid,wid);
        } catch (AirDeskException u){
            Toast.makeText(context, u.getMessage(), Toast.LENGTH_LONG).show();
            return view;
        }

        joinBtn.setChecked(isSubscribed);
        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean on =joinBtn.isChecked();

                Workspace w;
                try{
                    w= airDesk.getmDBHelper().getWorkspace(wid);
                }
                catch(AirDeskException a){
                    Toast.makeText(context, a.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }
                try {if(on){
                    airDesk.getmDBHelper().addSubscriberToWorkspace(wid,uid,w.isReadPermission(),w.isCreatePermission(),
                            w.isCreatePermission(),w.isDeletePermission());}
                    else{ airDesk.getmDBHelper().removeSubscriptionFromUser(wid,uid);

                }
                       // list.remove(position); //or some other task
                } catch(AirDeskException a) {a.getMessage();}
                //notifyDataSetChanged();
            }
        });

        return view;
    }

}
