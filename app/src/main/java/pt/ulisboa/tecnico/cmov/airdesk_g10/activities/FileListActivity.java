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
import pt.ulisboa.tecnico.cmov.airdesk_g10.adapters.FileListCustomAdapter;
import pt.ulisboa.tecnico.cmov.airdesk_g10.adapters.WSListCustomAdapter;
import pt.ulisboa.tecnico.cmov.airdesk_g10.core.File;
import pt.ulisboa.tecnico.cmov.airdesk_g10.core.Workspace;
import pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions.UserDoesNotExistException;
import pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions.WorkspaceDoesNotExistException;


public class FileListActivity extends ActionBarActivity {

    private AirDesk context;

    private int wsID;
    private boolean isOwned;

    private Button createBtn;
    private Button homeBtn;
    private Button backBtn;

    private ListView fileList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);

        context = (AirDesk) getApplicationContext();

        Intent intent = getIntent();

        isOwned = intent.getBooleanExtra("OWNED", true);
        wsID = intent.getIntExtra("WS_ID", 0);

        this.createBtn = (Button) findViewById(R.id.new_btn);
        this.createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        this.homeBtn = (Button) findViewById(R.id.home_btn);
        this.homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        this.backBtn = (Button) findViewById(R.id.back_btn);
        this.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOwned) {
                    Intent intent = new Intent(FileListActivity.this, OwnedWSActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(FileListActivity.this, ForeignWSActivity.class);
                    startActivity(intent);
                }
            }
        });

        //generate WS list
        ArrayList<File> list = context.getmDBHelper().getWorkspaceFiles(wsID);

        //instantiate custom adapter
        FileListCustomAdapter adapter = new FileListCustomAdapter(list, this);

        //handle listview and assign adapter
        this.fileList = (ListView)findViewById(R.id.file_lst);
        this.fileList.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_file_list, menu);
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
