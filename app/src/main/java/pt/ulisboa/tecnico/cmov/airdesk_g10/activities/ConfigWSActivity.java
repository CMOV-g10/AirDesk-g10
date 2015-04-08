package pt.ulisboa.tecnico.cmov.airdesk_g10.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import pt.ulisboa.tecnico.cmov.airdesk_g10.AirDesk;
import pt.ulisboa.tecnico.cmov.airdesk_g10.R;
import pt.ulisboa.tecnico.cmov.airdesk_g10.core.Workspace;
import pt.ulisboa.tecnico.cmov.airdesk_g10.db.AirDeskDbHelper;


public class ConfigWSActivity extends ActionBarActivity {

    private AirDesk context;

    private Button editSubsBtn;
    private Button createBtn;
    private Button homeBtn;

    private EditText nameTxt;
    private EditText quotaTxt;
    private CheckBox publicCb;
    private EditText tagsTxt;

    private boolean isNewWS;
    private int wsID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_ws);
        context = (AirDesk) getApplicationContext();

        Intent intent = getIntent();

        isNewWS = intent.getBooleanExtra("NEW_WS", true);
        wsID = intent.getIntExtra("WS_ID", 0);

        this.editSubsBtn = (Button) findViewById(R.id.editSubs_btn);
        this.createBtn = (Button) findViewById(R.id.create_btn);
        this.homeBtn = (Button) findViewById(R.id.home_btn);

        this.nameTxt = (EditText) findViewById(R.id.name_txt);
        this.quotaTxt = (EditText) findViewById(R.id.quota_txt);
        this.publicCb = (CheckBox) findViewById(R.id.public_cb);
        this.tagsTxt = (EditText) findViewById(R.id.tags_txt);

        this.editSubsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfigWSActivity.this, SubscriptionListActivity.class);
                intent.putExtra("NEW_WS",isNewWS);
                intent.putExtra("WS_ID", wsID);
                startActivity(intent);
            }
        });

        this.createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfigWSActivity.this, OwnedWSActivity.class);
                intent.putExtra("NEW_WS",isNewWS);
                intent.putExtra("WS_ID", wsID);
                startActivity(intent);
            }
        });

        this.homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfigWSActivity.this, MainActivity.class);
                intent.putExtra("NEW_WS",isNewWS);
                intent.putExtra("WS_ID", wsID);
                startActivity(intent);
            }
        });


        if(!isNewWS){
            Workspace ws = context.getmDBHelper().getWorkspace(wsID);
            nameTxt.setText(ws.getWsname(),TextView.BufferType.EDITABLE);
            quotaTxt.setText(ws.getWsquota(),TextView.BufferType.EDITABLE);
            tagsTxt.setText(ws.getWstags().toString(),TextView.BufferType.EDITABLE);
            publicCb.setChecked(ws.isWspublic());


        } else{



        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_config_w, menu);
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
