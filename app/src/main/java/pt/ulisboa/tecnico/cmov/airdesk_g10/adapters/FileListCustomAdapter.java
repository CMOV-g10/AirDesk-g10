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
import pt.ulisboa.tecnico.cmov.airdesk_g10.activities.FileActivity;
import pt.ulisboa.tecnico.cmov.airdesk_g10.activities.FileListActivity;
import pt.ulisboa.tecnico.cmov.airdesk_g10.core.Subscription;
import pt.ulisboa.tecnico.cmov.airdesk_g10.core.Workspace;
import pt.ulisboa.tecnico.cmov.airdesk_g10.core.WorkspaceFiles;
import pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions.AirDeskException;

/**
 * Created by luis on 4/9/15.
 */
public class FileListCustomAdapter extends BaseAdapter implements ListAdapter {


    private WorkspaceFiles list;
    private Context context;
    private AirDesk airDesk;

    public FileListCustomAdapter(WorkspaceFiles list, Context context, AirDesk airDesk) {
        this.list = list;
        this.context = context;
        this.airDesk = airDesk;
    }

    @Override
    public int getCount() {
        return list.getFiles().size();
    }

    @Override
    public Object getItem(int pos) {
        return list.getFiles().get(pos);
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
            view = inflater.inflate(R.layout.file_list_item, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        listItemText.setText(list.getFiles().get(position).getFiletitle());
        listItemText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });

        //Handle buttons and add onClickListeners
        Button deleteBtn = (Button)view.findViewById(R.id.delete_btn);
        Button editBtn = (Button)view.findViewById(R.id.edit_btn);
        Button readBtn = (Button) view.findViewById(R.id.read_btn);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something

                int ws_ID;
                try {
                    ws_ID = airDesk.getmDBHelper().getWorkspaceByFile(list.getFiles().get(position).getFileid());
                } catch (AirDeskException u){
                    Toast.makeText(context, u.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }

                Workspace ws;
                try {
                    ws = airDesk.getmDBHelper().getWorkspace(ws_ID);
                } catch (AirDeskException u){
                    Toast.makeText(context, u.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }



                //I'm the owner
                if(!(ws.getWsowner().getUserid() == airDesk.getLoggedUser().getUserid())){
                    Subscription sub = airDesk.getmDBHelper().getSubscription(ws.getWsid(), airDesk.getLoggedUser().getUserid());
                    if (!sub.isDeletePermission()){
                        Toast.makeText(context, "User don't have permission to delete.", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                try{
                    airDesk.getmDBHelper().removeFile(list.getFiles().get(position).getFileid(), ws_ID);
                } catch (AirDeskException u){
                    Toast.makeText(context, u.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }

                list.getFiles().remove(position); //or some other task
                notifyDataSetChanged();
            }
        });
        editBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int ws_ID;
                try {
                    ws_ID = airDesk.getmDBHelper().getWorkspaceByFile(list.getFiles().get(position).getFileid());
                } catch (AirDeskException u){
                    Toast.makeText(context, u.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }

                Workspace ws;
                try {
                    ws = airDesk.getmDBHelper().getWorkspace(ws_ID);
                } catch (AirDeskException u){
                    Toast.makeText(context, u.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }



                //I'm the owner
                if(!(ws.getWsowner().getUserid() == airDesk.getLoggedUser().getUserid())){
                    Subscription sub = airDesk.getmDBHelper().getSubscription(ws.getWsid(), airDesk.getLoggedUser().getUserid());
                    if (!sub.isWritePermission()){
                        Toast.makeText(context, "User don't have permission to edit.", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                Intent intent = new Intent(context, FileActivity.class);
                intent.putExtra("OP", FileActivity.OPERATION_EDIT);
                intent.putExtra("F_ID", list.getFiles().get(position).getFileid());
                intent.putExtra("OWNED", ((FileListActivity)context).isOwned());
                intent.putExtra("WS_ID", ((FileListActivity)context).getWsID());
                context.startActivity(intent);
            }
        });
        readBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int ws_ID;
                try {
                    ws_ID = airDesk.getmDBHelper().getWorkspaceByFile(list.getFiles().get(position).getFileid());
                } catch (AirDeskException u){
                    Toast.makeText(context, u.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }

                Workspace ws;
                try {
                    ws = airDesk.getmDBHelper().getWorkspace(ws_ID);
                } catch (AirDeskException u){
                    Toast.makeText(context, u.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }



                //I'm the owner
                if(!(ws.getWsowner().getUserid() == airDesk.getLoggedUser().getUserid())){
                    Subscription sub = airDesk.getmDBHelper().getSubscription(ws.getWsid(), airDesk.getLoggedUser().getUserid());
                    if (!sub.isReadPermission()){
                        Toast.makeText(context, "User don't have permission to read.", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                Intent intent = new Intent(context, FileActivity.class);
                intent.putExtra("OP", FileActivity.OPERATION_READ);
                intent.putExtra("F_ID", list.getFiles().get(position).getFileid());
                intent.putExtra("OWNED", ((FileListActivity)context).isOwned());
                intent.putExtra("WS_ID", ((FileListActivity)context).getWsID());
                context.startActivity(intent);
            }
        });


        return view;
    }

}
