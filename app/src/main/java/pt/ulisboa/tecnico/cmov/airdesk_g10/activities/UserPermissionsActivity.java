package pt.ulisboa.tecnico.cmov.airdesk_g10.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import pt.ulisboa.tecnico.cmov.airdesk_g10.AirDesk;
import pt.ulisboa.tecnico.cmov.airdesk_g10.R;
import pt.ulisboa.tecnico.cmov.airdesk_g10.core.Subscription;
import pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions.AirDeskException;

public class UserPermissionsActivity extends ActionBarActivity {

    private AirDesk context;

    private Button editBtn;
    private Button homeBtn;
    private Button backBtn;

    private CheckBox readPermCb;
    private CheckBox writePermCb;
    private CheckBox createPermCb;
    private CheckBox deletePermCb;

    private boolean isNewWS;
    private int wsID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_permissions);

        context = (AirDesk) getApplicationContext();

        Intent intent = getIntent();

        isNewWS = intent.getBooleanExtra("NEW_WS", true);
        wsID = intent.getIntExtra("WS_ID", 0);

        this.editBtn = (Button) findViewById(R.id.edit_btn);

        this.backBtn = (Button) findViewById(R.id.back_btn);

        this.homeBtn = (Button) findViewById(R.id.home_btn);

        this.homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserPermissionsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        this.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserPermissionsActivity.this, SubscriptionListActivity.class);
                intent.putExtra("NEW_WS",isNewWS);
                intent.putExtra("WS_ID", wsID);
                startActivity(intent);
            }
        });

        this.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Subscription sub;

                try {
                    sub = context.getmDBHelper().getSubscription(wsID, context.getLoggedUser().getUserid());
                } catch (AirDeskException u){
                    Toast.makeText(context, u.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }


                boolean pub, readP, writeP, createP, deleteP;
                readP = readPermCb.isChecked();
                writeP = writePermCb.isChecked();
                createP = createPermCb.isChecked();
                deleteP = deletePermCb.isChecked();

                sub.setReadPermission(readP);
                sub.setWritePermission(writeP);
                sub.setCreatePermission(createP);
                sub.setDeletePermission(deleteP);

                try {

                   context.getmDBHelper().changeSubscriptionData(sub);
                } catch (AirDeskException u){
                    Toast.makeText(context, u.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }

                Intent intent = new Intent(UserPermissionsActivity.this, SubscriptionListActivity.class);
                intent.putExtra("NEW_WS",isNewWS);
                intent.putExtra("WS_ID", wsID);
                startActivity(intent);
            }
        });

        this.readPermCb = (CheckBox) findViewById(R.id.read_cb);
        this.writePermCb = (CheckBox) findViewById(R.id.write_cb);
        this.createPermCb = (CheckBox) findViewById(R.id.create_btn);
        this.deletePermCb = (CheckBox) findViewById(R.id.delete_cb);

        Subscription sub;
        try {
            sub = context.getmDBHelper().getSubscription(wsID, context.getLoggedUser().getUserid());
        }catch (AirDeskException u){
            Toast.makeText(context, u.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }

        readPermCb.setChecked(sub.isReadPermission());
        writePermCb.setChecked(sub.isWritePermission());
        createPermCb.setChecked(sub.isCreatePermission());
        deletePermCb.setChecked(sub.isDeletePermission());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_permissions, menu);
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
