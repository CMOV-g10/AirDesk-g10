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
import pt.ulisboa.tecnico.cmov.airdesk_g10.adapters.ForeignWSListCustomAdapter;
import pt.ulisboa.tecnico.cmov.airdesk_g10.core.UserSubscriptions;
import pt.ulisboa.tecnico.cmov.airdesk_g10.core.Workspace;
import pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions.UserDoesNotExistException;
import pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions.WorkspaceDoesNotExistException;


public class ForeignWSActivity extends ActionBarActivity {

    private AirDesk context;
    private ListView foreignList;
    private Button searchWSBtn;
    private Button homeBtn;
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foreign_ws);

        context = (AirDesk) getApplicationContext();

        this.searchWSBtn = (Button) findViewById(R.id.searchWS_btn);
        this.searchWSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForeignWSActivity.this, SearchByTagsActivity.class);
                intent.putExtra("NEW_WS", true);
                intent.putExtra("WS_ID", 0);
                startActivity(intent);
            }
        });
        this.homeBtn = (Button) findViewById(R.id.home_btn);
        this.homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForeignWSActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        this.backBtn = (Button) findViewById(R.id.back_btn);
        this.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForeignWSActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        //generate WS list
        UserSubscriptions list = context.getmDBHelper().getUserSubscriptions(context.getLoggedUser().getUserid());

        //instantiate custom adapter
        ForeignWSListCustomAdapter adapter = new ForeignWSListCustomAdapter(list, this, context);

        //handle listview and assign adapter
        this.foreignList = (ListView)findViewById(R.id.sub_list);
        this.foreignList.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_foreign_w, menu);
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
