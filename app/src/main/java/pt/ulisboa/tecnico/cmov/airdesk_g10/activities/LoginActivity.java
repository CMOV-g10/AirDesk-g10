package pt.ulisboa.tecnico.cmov.airdesk_g10.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pt.ulisboa.tecnico.cmov.airdesk_g10.db.AirDeskContract;
import pt.ulisboa.tecnico.cmov.airdesk_g10.db.AirDeskDbHelper;
import pt.ulisboa.tecnico.cmov.airdesk_g10.R;


public class LoginActivity extends ActionBarActivity {

    private EditText usernameTxt;
    private EditText passwordTxt;
    private Button loginBtn;
    private Button registerBtn;

    private AirDeskDbHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.loginBtn = (Button) findViewById(R.id.login_btn);
        this.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkLogin()) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
        this.registerBtn = (Button) findViewById(R.id.register_btn);
        this.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        this.usernameTxt = (EditText) findViewById(R.id.username_txt);
        this.passwordTxt = (EditText) findViewById(R.id.password_txt);

        createDB();
        mDBHelper.addUser("Pedro","bananas");

    }

    public void createDB(){
        mDBHelper = new AirDeskDbHelper(getApplicationContext());

    }



    public boolean checkLogin(){

        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.

        String[] projection = {
                AirDeskContract.UserEntry._ID,
                AirDeskContract.UserEntry.COLUMN_USER_NAME,
                AirDeskContract.UserEntry.COLUMN_USER_PASSWORD
        };

        // How you want the results sorted in the resulting Cursor

        String Sql= "SELECT * FROM "+AirDeskContract.UserEntry.TABLE_NAME;
        Toast.makeText(this,Sql,Toast.LENGTH_SHORT).show();
        Cursor c = db.rawQuery(Sql, null);
        Toast.makeText(this,"QUERY",Toast.LENGTH_SHORT).show();
        String loginName = usernameTxt.getText().toString();
        String loginPassword = passwordTxt.getText().toString();

        c.moveToFirst();

        while (!c.isAfterLast()){
            String dbUserName = c.getString(c.getColumnIndexOrThrow(AirDeskContract.UserEntry.COLUMN_USER_NAME));
            String dbUserPassword = c.getString(c.getColumnIndexOrThrow(AirDeskContract.UserEntry.COLUMN_USER_PASSWORD));
            if(dbUserName.equals(loginName) && dbUserPassword.equals(loginPassword))
                return true;
            c.moveToNext();
        }
        return false;
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
