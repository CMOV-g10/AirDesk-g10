package pt.ulisboa.tecnico.cmov.airdesk_g10.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pt.ulisboa.tecnico.cmov.airdesk_g10.AirDesk;
import pt.ulisboa.tecnico.cmov.airdesk_g10.R;
import pt.ulisboa.tecnico.cmov.airdesk_g10.adapters.SubscriptionListCustomAdapter;
import pt.ulisboa.tecnico.cmov.airdesk_g10.adapters.WSListCustomAdapter;
import pt.ulisboa.tecnico.cmov.airdesk_g10.core.User;
import pt.ulisboa.tecnico.cmov.airdesk_g10.core.Workspace;
import pt.ulisboa.tecnico.cmov.airdesk_g10.core.WorkspaceSubscriptions;
import pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions.AirDeskException;
import pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions.UserAlreadyExistsException;
import pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions.UserDoesNotExistException;
import pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions.WorkspaceDoesNotExistException;


public class SubscriptionListActivity extends ActionBarActivity {

    private AirDesk context;

    private boolean isNewWS;
    private int wsID;

    private Button homeBtn;
    private Button addBtn;
    private Button backBtn;

    private TextView nameTxt;

    private ListView subsList;

    private Context myContext;

    public int getWsID() {
        return wsID;
    }

    public boolean isNewWS() {
        return isNewWS;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_list);

        context = (AirDesk) getApplicationContext();

        Intent intent = getIntent();
        myContext = this;

        isNewWS = intent.getBooleanExtra("NEW_WS", true);
        wsID = intent.getIntExtra("WS_ID", 0);

        this.homeBtn = (Button) findViewById(R.id.home_btn);
        this.addBtn = (Button) findViewById(R.id.add_btn);
        this.backBtn = (Button) findViewById(R.id.back_btn);

        this.nameTxt = (TextView) findViewById(R.id.name_txt);

        this.subsList = (ListView) findViewById(R.id.subs_list);

        this.homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubscriptionListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        this.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String username = nameTxt.getText().toString();
              User user;
              try {
                  user = context.getmDBHelper().searchUser(username);
              } catch (AirDeskException u) {
                  Toast.makeText(context, u.getMessage(), Toast.LENGTH_LONG).show();
                  return;
              }

            Workspace ws;

            try{
                ws = context.getmDBHelper().getWorkspace(wsID);
            } catch (AirDeskException a){
                Toast.makeText(context, a.getMessage(), Toast.LENGTH_LONG).show();
                return;
            }

            try {
                context.getmDBHelper().addSubscriberToWorkspace(wsID, context.getLoggedUser().getUserid(), ws.isReadPermission(), ws.isWritePermission(), ws.isCreatePermission(), ws.isDeletePermission());
            } catch(AirDeskException a) {
                Toast.makeText(context, a.getMessage(), Toast.LENGTH_LONG).show();
                return;
            }
                WorkspaceSubscriptions list;
            try {
                list = context.getmDBHelper().getWorkspaceSubscribers(wsID);
            } catch (AirDeskException u) {
                Toast.makeText(context, u.getMessage(), Toast.LENGTH_LONG).show();
                return;
            }

                SubscriptionListCustomAdapter adapter = new SubscriptionListCustomAdapter(list, myContext, context);

                //handle listview and assign adapter
                subsList.setAdapter(adapter);
         }
        });

        this.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        WorkspaceSubscriptions list;

            try {
               list= context.getmDBHelper().getWorkspaceSubscribers(wsID);
            } catch (WorkspaceDoesNotExistException u) {
                Toast.makeText(context, u.getMessage(), Toast.LENGTH_LONG).show();
                return;
            }


        SubscriptionListCustomAdapter adapter = new SubscriptionListCustomAdapter(list, myContext, context);

        //handle listview and assign adapter
        this.subsList.setAdapter(adapter);

    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SubscriptionListActivity.this, ConfigWSActivity.class);
        intent.putExtra("NEW_WS",isNewWS);
        intent.putExtra("WS_ID", wsID);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_subscription_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
