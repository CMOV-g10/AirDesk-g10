package pt.ulisboa.tecnico.cmov.airdesk_g10;

import android.content.ContentValues;
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


public class LoginActivity extends ActionBarActivity {

    private EditText usernameTxt;
    private EditText passwordTxt;
    private Button loginBtn;

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
        this.usernameTxt = (EditText) findViewById(R.id.username_txt);
        this.passwordTxt = (EditText) findViewById(R.id.password_txt);

        createDB();

        testInsertLogin();
    }

    public void createDB(){
        mDBHelper = new AirDeskDbHelper(getApplicationContext());
    }

    public void testInsertLogin(){
        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(AirDeskContract.UserEntry.COLUMN_USER_ID, 1);
        values.put(AirDeskContract.UserEntry.COLUMN_USER_NAME, "Pedro");
        values.put(AirDeskContract.UserEntry.COLUMN_USER_PASSWORD, "bananas");

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                AirDeskContract.UserEntry.TABLE_NAME,
                 "NULL",
                values);

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
        String sortOrder =
                AirDeskContract.UserEntry.COLUMN_USER_NAME + " DESC";

        Cursor c = db.query(
                AirDeskContract.UserEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                "*",                                    // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

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
