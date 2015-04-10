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


public class RegisterActivity extends ActionBarActivity {
    private AirDesk context;
    private EditText emailTxt;
    private EditText nickNameTxt;
    private EditText passwordTxt;
    private EditText passwordConfirmationTxt;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context = (AirDesk) getApplicationContext();

        this.registerBtn = (Button) findViewById(R.id.register_btn);

        this.registerBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String email = emailTxt.getText().toString();
                String nickname = nickNameTxt.getText().toString();
                String pass = passwordTxt.getText().toString();
                String passconf = passwordConfirmationTxt.getText().toString();

                if (!context.getmDBHelper().userExists(email)) {
                    if (pass.equals(passconf)) {
                        try {
                            context.getmDBHelper().addUser(email, pass, nickname);
                        } catch (AirDeskException a) {
                            Toast.makeText(context, a.getMessage(), Toast.LENGTH_LONG).show();
                            return;
                        }
                    } else {
                        Toast.makeText(context, "Passwords Don't match", Toast.LENGTH_LONG).show();
                        return;
                    }
                } else {
                    Toast.makeText(context, "Email already exists.", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(context, "User " + email + " registered with sucess.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }

        });

        this.emailTxt    = (EditText) findViewById(R.id.email_txt);
        this.nickNameTxt = (EditText) findViewById(R.id.nickname_txt);
        this.passwordTxt = (EditText) findViewById(R.id.password_txt);
        this.passwordConfirmationTxt = (EditText) findViewById(R.id.passwordConfirmation_txt);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
