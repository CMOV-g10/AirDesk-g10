package pt.ulisboa.tecnico.cmov.airdesk_g10.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import pt.ulisboa.tecnico.cmov.airdesk_g10.AirDesk;
import pt.ulisboa.tecnico.cmov.airdesk_g10.R;
import pt.ulisboa.tecnico.cmov.airdesk_g10.adapters.JoinWSListCustomAdapter;
import pt.ulisboa.tecnico.cmov.airdesk_g10.core.Workspace;
import pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions.AirDeskException;


public class SearchByTagsActivity extends ActionBarActivity {

    private AirDesk context;
   private ListView wsList;
    private EditText searchTxt;
    private Button searchBtn;
    private Button homeBtn;
    private Button backBtn;
    private Context myContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_by_tags);
        context = (AirDesk) getApplicationContext();
        myContext = this;

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

                String tag=searchTxt.getText().toString();
                if(!tag.equals("")){
                    try{
                        ArrayList<Workspace> wList =context.getmDBHelper().getWorkspaceByTags(tag);

              //  Toast.makeText(context,wList.toString(), Toast.LENGTH_LONG).show();
                        JoinWSListCustomAdapter adapter = new JoinWSListCustomAdapter(wList, myContext, context);

                        wsList.setAdapter(adapter);}
                    catch (AirDeskException a){
                        Toast.makeText(context,a.getMessage(), Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                else{
                    Toast.makeText(context,"No tags inserted", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });
        this.searchTxt = (EditText) findViewById(R.id.search_txt);
        this.wsList = (ListView)findViewById(R.id.fws_list);
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
