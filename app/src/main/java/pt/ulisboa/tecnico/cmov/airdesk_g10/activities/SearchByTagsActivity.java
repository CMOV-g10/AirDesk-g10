package pt.ulisboa.tecnico.cmov.airdesk_g10.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import pt.ulisboa.tecnico.cmov.airdesk_g10.AirDesk;
import pt.ulisboa.tecnico.cmov.airdesk_g10.R;
import pt.ulisboa.tecnico.cmov.airdesk_g10.adapters.JoinWSListCustomAdapter;
import pt.ulisboa.tecnico.cmov.airdesk_g10.core.Workspace;


public class SearchByTagsActivity extends ActionBarActivity {

    private AirDesk context;
    private ListView wsList;
    private EditText searchTxt;
    private Button searchBtn;
    private Button homeBtn;
    private Button backBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_tags);

        this.homeBtn = (Button) findViewById(R.id.home_btn);
        this.homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchByTagsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        this.backBtn = (Button) findViewById(R.id.back_btn);
        this.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchByTagsActivity.this, ForeignWSActivity.class);
                startActivity(intent);
            }
        });
        this.searchBtn = (Button) findViewById(R.id.search_btn);
        this.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Produce search query to DB
            }
        });
        this.searchTxt = (EditText) findViewById(R.id.search_txt);

        //generate WS list
        ArrayList<Workspace> list = new ArrayList<Workspace>();
        /*list.add("WS3");
        list.add("WS4");*/

        //instantiate custom adapter
        JoinWSListCustomAdapter adapter = new JoinWSListCustomAdapter(list, this, context);

        //handle listview and assign adapter
        this.wsList = (ListView)findViewById(R.id.fws_list);
        this.wsList.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_by_tags, menu);
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
