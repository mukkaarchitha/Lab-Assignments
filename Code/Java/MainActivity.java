package com.example.sravan.testapplication;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.app.AlertDialog;
import android.widget.TextView;
import android.content.Intent;
import android.content.DialogInterface;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    EditText username, password;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        username = (EditText) findViewById(R.id.etUserName);
        password = (EditText)findViewById(R.id.etPassword);
        loginButton=(Button)findViewById(R.id.blogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void login()
    {

        String a1,a2;
        a1=username.getText().toString();
        a2=password.getText().toString();
        Log.d("Username",a1);
        Log.d("Password", a2);


        if((a1.length()>0 && a2.length()>0))
        {
            if (a1.equals("arch") && a2.equals("architha"))
            {
                Log.d("Message", "Successfully login");
                Intent intent = new Intent(this,HomeScreen.class);
                startActivity(intent);
            }
            else
            {
                Log.d("Incorrect","USERNAME OR PASSWORD INCORRECT");
            }
        }
        else {
            new AlertDialog.Builder(this).setTitle("ERROR").setMessage("TRY AGAIN").setNeutralButton("Close", null).show();
        }

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
