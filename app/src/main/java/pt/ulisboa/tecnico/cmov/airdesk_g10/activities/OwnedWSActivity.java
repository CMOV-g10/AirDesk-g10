package pt.ulisboa.tecnico.cmov.airdesk_g10.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import pt.ulisboa.tecnico.cmov.airdesk_g10.AirDesk;
import pt.ulisboa.tecnico.cmov.airdesk_g10.R;
import pt.ulisboa.tecnico.cmov.airdesk_g10.adapters.WSListCustomAdapter;
import pt.ulisboa.tecnico.cmov.airdesk_g10.core.UserWorkspaces;
import pt.ulisboa.tecnico.cmov.airdesk_g10.core.Workspace;
import pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions.AirDeskException;
import pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions.UserDoesNotExistException;
import pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions.WorkspaceDoesNotExistException;


public class OwnedWSActivity extends ActionBarActivity {

    private AirDesk context;
    private ListView wsList;
    private Button addWSBtn;
    private Button homeBtn;
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owned_ws);

        context = (AirDesk) getApplicationContext();

        this.addWSBtn = (Button) findViewById(R.id.addWS_btn);
        this.addWSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnedWSActivity.this, ConfigWSActivity.class);
                intent.putExtra("NEW_WS", true);
                intent.putExtra("WS_ID", 0);
                startActivity(intent);
            }
        });
        this.homeBtn = (Button) findViewById(R.id.home_btn);
        this.homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnedWSActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        this.backBtn = (Button) findViewById(R.id.back_btn);
        this.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(OwnedWSActivity.this, MainActivity.class);
                    startActivity(intent);
            }
        });
        //generate WS list
        UserWorkspaces list;
        try {
            list = context.getmDBHelper().getUserWorkSpaces(context.getLoggedUser().getUserid());
        } catch (AirDeskException u){
            Toast.makeText(context, u.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }


        //instantiate custom adapter
        WSListCustomAdapter adapter = new WSListCustomAdapter(list, this, context);

        //handle listview and assign adapter
        this.wsList = (ListView)findViewById(R.id.ws_list);
        this.wsList.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_owned_w, menu);
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
