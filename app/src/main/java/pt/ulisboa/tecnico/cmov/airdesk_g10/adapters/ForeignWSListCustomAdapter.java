package pt.ulisboa.tecnico.cmov.airdesk_g10.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import pt.ulisboa.tecnico.cmov.airdesk_g10.AirDesk;
import pt.ulisboa.tecnico.cmov.airdesk_g10.R;
import pt.ulisboa.tecnico.cmov.airdesk_g10.activities.FileListActivity;
import pt.ulisboa.tecnico.cmov.airdesk_g10.core.UserSubscriptions;
import pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions.AirDeskException;

/**
 * Created by Pedro on 4/8/2015.
 */
public class ForeignWSListCustomAdapter extends BaseAdapter implements ListAdapter {

    private UserSubscriptions list;
    private Context context;
    private AirDesk airDesk;

    public ForeignWSListCustomAdapter(UserSubscriptions list, Context context, AirDesk airDesk) {
        this.list = list;
        this.context = context;
        this.airDesk = airDesk;
    }

    @Override
    public int getCount() {
        return list.getSubscriptions().size();
    }

    @Override
    public Object getItem(int pos) {
        return list.getSubscriptions().get(pos);
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
            view = inflater.inflate(R.layout.foreign_ws_list_item, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        listItemText.setText(list.getSubscriptions().get(position).getWorkspace().getWsname()+" by "+list.getSubscriptions().get(position).getWorkspace().getWsowner().getUseremail());

        listItemText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FileListActivity.class);
                intent.putExtra("OWNED", false);
                intent.putExtra("WS_ID", list.getSubscriptions().get(position).getWorkspace().getWsid());
                context.startActivity(intent);
            }
        });

        //Handle buttons and add onClickListeners
        final Button leaveBtn = (Button) view.findViewById(R.id.leave_btn);

        leaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int wid = list.getSubscriptions().get(position).getWorkspace().getWsid();
                int uid = airDesk.getLoggedUser().getUserid();
                try {
                   airDesk.getmDBHelper().removeSubscriptionFromUser(wid, uid);
                } catch(AirDeskException a) {
                    Toast.makeText(context, a.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }

                list.getSubscriptions().remove(position); //or some other task
                notifyDataSetChanged();
            }
        });

        return view;
    }
}

