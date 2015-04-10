package pt.ulisboa.tecnico.cmov.airdesk_g10.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pt.ulisboa.tecnico.cmov.airdesk_g10.AirDesk;
import pt.ulisboa.tecnico.cmov.airdesk_g10.R;
import pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions.AirDeskException;
import pt.ulisboa.tecnico.cmov.airdesk_g10.exceptions.UserDoesNotExistException;


public class LoginActivity extends ActionBarActivity {

    private AirDesk context;
    private EditText usernameTxt;
    private EditText passwordTxt;
    private Button loginBtn;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = (AirDesk) getApplicationContext();

        this.loginBtn = (Button) findViewById(R.id.login_btn);
        this.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loginName = usernameTxt.getText().toString();
                String loginPassword = passwordTxt.getText().toString();

                if (context.getmDBHelper().userExists(loginName)) {
                    if (context.getmDBHelper().userExists(loginName, loginPassword)) {
                        try {
                            context.setLoggedUser(context.getmDBHelper().searchUser(loginName));
                        } catch (UserDoesNotExistException u) {
                            Toast.makeText(context, u.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(context, "User password is invalid.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(context, "User does not exist.", Toast.LENGTH_LONG).show();
                }

            }
        });
        this.registerBtn = (Button) findViewById(R.id.registerlaunch_btn);
        this.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);
                   }
        });
        this.usernameTxt = (EditText) findViewById(R.id.nickname_txt);
        this.passwordTxt = (EditText) findViewById(R.id.password_txt);

        populateDomain();

    }

    public void populateDomain() {
        context.getmDBHelper().onUpgrade(context.getmDBHelper().getWritableDatabase(), context.getmDBHelper().DATABASE_VERSION, context.getmDBHelper().DATABASE_VERSION);
        try {
            this.context.getmDBHelper().addUser("p", "b", "p");
        } catch (AirDeskException u) {
            Toast.makeText(context, u.getMessage(), Toast.LENGTH_LONG).show();
        }

        try {
            //CHANGE PERMISSIONS HERE
            this.context.getmDBHelper().addWorkspace("p", "CMOV", true, 50, "", true, true, true, true);
        } catch (AirDeskException u) {
            Toast.makeText(context, u.getMessage(), Toast.LENGTH_LONG).show();
        }

        try {
            this.context.getmDBHelper().addWorkspace("p", "AASMA", true, 50, "", true, true, true, true);
        } catch (AirDeskException u) {
            Toast.makeText(context, u.getMessage(), Toast.LENGTH_LONG).show();
        }

        try {
            this.context.getmDBHelper().addSubscriberToWorkspace(
                    this.context.getmDBHelper().getWorkspaceId("CMOV", this.context.getmDBHelper().getUserId("p")),
                        this.context.getmDBHelper().getUserId("p"), true, true, true, true);
        } catch (AirDeskException u) {
            Toast.makeText(context, u.getMessage(), Toast.LENGTH_LONG).show();
        }

        try {
            this.context.getmDBHelper().addSubscriberToWorkspace(
                    this.context.getmDBHelper().getWorkspaceId("AASMA", this.context.getmDBHelper().getUserId("p")),
                    this.context.getmDBHelper().getUserId("p"), true, true, true, true);
        } catch (AirDeskException u) {
            Toast.makeText(context, u.getMessage(), Toast.LENGTH_LONG).show();
        }

        try {
            this.context.getmDBHelper().addFile("p", "p", "CMOV", "AirDesk","READ ME");
        } catch (AirDeskException u) {
            Toast.makeText(context, u.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
