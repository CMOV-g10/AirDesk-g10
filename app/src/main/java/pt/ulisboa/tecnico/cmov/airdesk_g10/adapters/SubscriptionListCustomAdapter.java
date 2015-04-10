package pt.ulisboa.tecnico.cmov.airdesk_g10.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

import pt.ulisboa.tecnico.cmov.airdesk_g10.AirDesk;
import pt.ulisboa.tecnico.cmov.airdesk_g10.R;
import pt.ulisboa.tecnico.cmov.airdesk_g10.core.User;

/**
 * Created by Pedro on 4/9/2015.
 */
public class SubscriptionListCustomAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<User> list = new ArrayList<User>();
    private Context context;
    private AirDesk airDesk;

    public SubscriptionListCustomAdapter(ArrayList<User> list, Context context, AirDesk airDesk) {
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
            view = inflater.inflate(R.layout.sub_list_item, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        listItemText.setText(list.get(position).getUseremail());

        //Handle buttons and add onClickListeners
        final ToggleButton inviteBtn = (ToggleButton) view.findViewById(R.id.invite_btn);
        inviteBtn.setChecked(true);

        inviteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean on = inviteBtn.isChecked();
                if (on){



                }else{
                    int userId = list.get(position).getUserid();


                }
                notifyDataSetChanged();
            }
        });

        return view;
    }
}
