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

import java.util.ArrayList;

import pt.ulisboa.tecnico.cmov.airdesk_g10.AirDesk;
import pt.ulisboa.tecnico.cmov.airdesk_g10.R;
import pt.ulisboa.tecnico.cmov.airdesk_g10.activities.ConfigWSActivity;
import pt.ulisboa.tecnico.cmov.airdesk_g10.activities.FileListActivity;
import pt.ulisboa.tecnico.cmov.airdesk_g10.core.UserWorkspaces;
import pt.ulisboa.tecnico.cmov.airdesk_g10.core.Workspace;
import pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions.AirDeskException;

/**
 * Created by Pedro on 4/6/2015.
 */
public class WSListCustomAdapter extends BaseAdapter implements ListAdapter {

    private UserWorkspaces list;
    private Context context;
    private AirDesk airDesk;

    public WSListCustomAdapter(UserWorkspaces list, Context context, AirDesk airDesk) {
        this.list = list;
        this.context = context;
        this.airDesk = airDesk;
    }

    @Override
    public int getCount() {
        return list.getWorkspaces().size();
    }

    @Override
    public Object getItem(int pos) {
        return list.getWorkspaces().get(pos);
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
            view = inflater.inflate(R.layout.ws_list_item, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        listItemText.setText(list.getWorkspaces().get(position).getWsname());
        listItemText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FileListActivity.class);
                intent.putExtra("OWNED", true);
                intent.putExtra("WS_ID", list.getWorkspaces().get(position).getWsid());
                context.startActivity(intent);
            }
        });

        //Handle buttons and add onClickListeners
        Button deleteBtn = (Button)view.findViewById(R.id.delete_cb);
        Button editBtn = (Button)view.findViewById(R.id.edit_btn);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                try {
                    airDesk.getmDBHelper().removeWorkspace(list.getWorkspaces().get(position).getWsid());
                } catch (AirDeskException u){
                    Toast.makeText(context, u.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(context, "Workspace deleted with sucess.", Toast.LENGTH_LONG).show();
                notifyDataSetChanged();
            }
        });
        editBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ConfigWSActivity.class);
                intent.putExtra("NEW_WS", false);
                intent.putExtra("WS_ID", list.getWorkspaces().get(position).getWsid());
                context.startActivity(intent);
            }
        });

        return view;
    }
}