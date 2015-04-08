package pt.ulisboa.tecnico.cmov.airdesk_g10.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import pt.ulisboa.tecnico.cmov.airdesk_g10.R;
import pt.ulisboa.tecnico.cmov.airdesk_g10.adapters.ForeignWSListCustomAdapter;
import pt.ulisboa.tecnico.cmov.airdesk_g10.core.Workspace;


public class SearchByTagsActivity extends ActionBarActivity {

    private ListView wsList;
    private EditText searchTxt;
    private Button searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_tags);

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
        ForeignWSListCustomAdapter adapter = new ForeignWSListCustomAdapter(list, this);

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
