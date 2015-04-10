package pt.ulisboa.tecnico.cmov.airdesk_g10.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import pt.ulisboa.tecnico.cmov.airdesk_g10.AirDesk;
import pt.ulisboa.tecnico.cmov.airdesk_g10.R;


public class MainActivity extends ActionBarActivity {

    private AirDesk context;
    private Button ownedWSBtn;
    private Button foreignWSBtn;
    private Button logoutBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = (AirDesk) getApplicationContext();

        this.ownedWSBtn = (Button) findViewById(R.id.ownedWS_btn);
        this.ownedWSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OwnedWSActivity.class);
                startActivity(intent);
            }
        });
        this.foreignWSBtn = (Button) findViewById(R.id.foreignWS_btn);
        this.foreignWSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ForeignWSActivity.class);
                startActivity(intent);
            }
        });
        this.logoutBtn = (Button) findViewById(R.id.logout_btn);
        this.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.setLoggedUser(null);
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
