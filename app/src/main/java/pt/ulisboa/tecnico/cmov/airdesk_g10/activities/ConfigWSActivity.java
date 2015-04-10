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
import android.widget.Toast;

import pt.ulisboa.tecnico.cmov.airdesk_g10.AirDesk;
import pt.ulisboa.tecnico.cmov.airdesk_g10.R;
import pt.ulisboa.tecnico.cmov.airdesk_g10.core.Workspace;
import pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions.AirDeskException;


public class ConfigWSActivity extends ActionBarActivity {

    private AirDesk context;

    private Button editSubsBtn;
    private Button createBtn;
    private Button homeBtn;
    private Button backBtn;

    private EditText nameTxt;
    private EditText quotaTxt;
    private CheckBox publicCb;
    private EditText tagsTxt;

    private CheckBox readPermCb;
    private CheckBox writePermCb;
    private CheckBox createPermCb;
    private CheckBox deletePermCb;

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

        this.backBtn = (Button) findViewById(R.id.back_btn);

        this.homeBtn = (Button) findViewById(R.id.home_btn);

        this.nameTxt = (EditText) findViewById(R.id.name_txt);
        this.quotaTxt = (EditText) findViewById(R.id.quota_txt);
        this.publicCb = (CheckBox) findViewById(R.id.public_cb);
        this.tagsTxt = (EditText) findViewById(R.id.tags_txt);

        this.readPermCb = (CheckBox) findViewById(R.id.read_cb);
        this.writePermCb = (CheckBox) findViewById(R.id.write_cb);
        this.createPermCb = (CheckBox) findViewById(R.id.create_cb);
        this.deletePermCb = (CheckBox) findViewById(R.id.delete_cb);

        this.editSubsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfigWSActivity.this, SubscriptionListActivity.class);
                intent.putExtra("NEW_WS",isNewWS);
                intent.putExtra("WS_ID", wsID);
                startActivity(intent);
            }
        });
        this.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfigWSActivity.this, OwnedWSActivity.class);
                startActivity(intent);
            }
        });
        this.createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username;
                String wsname;
                int quota;
                boolean pub, readP, writeP, createP, deleteP;
                String tags;

                if(nameTxt.getText().toString().equals("") ||
                   quotaTxt.getText().toString().equals("")) {
                    Toast.makeText(context, "Please fill name and quota.", Toast.LENGTH_LONG).show();
                    return;
                }

                username = context.getLoggedUser().getUseremail();
                wsname = nameTxt.getText().toString();
                quota = new Integer(quotaTxt.getText().toString());
                pub = publicCb.isChecked();

                readP = readPermCb.isChecked();
                writeP = writePermCb.isChecked();
                createP = createPermCb.isChecked();
                deleteP = deletePermCb.isChecked();

                tags = tagsTxt.getText().toString();

                if(isNewWS){

                    try {
                        //CHANGE PERMISSIONS HERE
                        context.getmDBHelper().addWorkspace(username, wsname, pub, quota, tags, readP, writeP, createP, deleteP);
                    } catch (AirDeskException u){
                        Toast.makeText(context, u.getMessage(), Toast.LENGTH_LONG).show();
                        return;
                    }
                    Toast.makeText(context, "Workspace created with sucess.", Toast.LENGTH_LONG).show();


                } else {

                    Workspace ws;
                    try {
                        ws = context.getmDBHelper().getWorkspace(wsID);
                    } catch (AirDeskException u) {
                        Toast.makeText(context, u.getMessage(), Toast.LENGTH_LONG).show();
                        return;
                    }


                    ws.setWsname(wsname);
                    ws.setWsquota(quota);
                    ws.setWspublic(pub);
                    ws.setWstags(tags);
                    ws.setReadPermission(readP);
                    ws.setWritePermission(writeP);
                    ws.setCreatePermission(createP);
                    ws.setDeletePermission(deleteP);

                    try {
                        context.getmDBHelper().changeWorkspaceData(ws);
                    } catch (AirDeskException u) {
                        Toast.makeText(context, u.getMessage(), Toast.LENGTH_LONG).show();
                        return;
                    }
                    Toast.makeText(context, "Workspace changed with sucess.", Toast.LENGTH_LONG).show();

                }


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
                startActivity(intent);
            }
        });

        if(!isNewWS){
            Workspace ws = context.getmDBHelper().getWorkspace(wsID);
            nameTxt.setText(ws.getWsname(),TextView.BufferType.EDITABLE);
            quotaTxt.setText(String.valueOf(ws.getWsquota()),TextView.BufferType.EDITABLE);
            tagsTxt.setText(ws.getWstags().toString(),TextView.BufferType.EDITABLE);
            publicCb.setChecked(ws.isWspublic());
            createBtn.setText("EDIT",TextView.BufferType.EDITABLE);

            readPermCb.setChecked(ws.isReadPermission());
            writePermCb.setChecked(ws.isWritePermission());
            createPermCb.setChecked(ws.isCreatePermission());
            deletePermCb.setChecked(ws.isDeletePermission());

        } else {
            editSubsBtn.setVisibility(View.INVISIBLE);
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
