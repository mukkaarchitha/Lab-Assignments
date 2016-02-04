package com.example.sravan.testapplication;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
//import http3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;



public class HomeScreen extends AppCompatActivity {

    String source_value;
    TextView output_value;
    TextView outputTextView;
    Context mContext;
    String sourceText;
    WebView weatherInfo;

    EditText ed1,ed2;
    Button convertButton;
    Button logOUT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        mContext = getBaseContext();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //outputTextView = (TextView) findViewById(R.id.result_value);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ed1=(EditText)findViewById(R.id.etEUROS);
        ed2=(EditText)findViewById(R.id.etRup);
        convertButton=(Button)findViewById(R.id.conButton);
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertAmountFunc(v);
            }
        });
        logOUT=(Button)findViewById(R.id.blogout);
        logOUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LOGOUT(v);
            }
        });
    }
    private void hideKeyboard(View editableView) {
        InputMethodManager imm = (InputMethodManager)mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editableView.getWindowToken(), 0);
    }

    public void LOGOUT(View view)
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void convertAmountFunc(View v)
    {

        String getURL = "http://api.fixer.io/latest";
        OkHttpClient client = new OkHttpClient();

        try {

            Request request = new Request.Builder()
                    .url(getURL)
                    .build();

            client.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println(e.getMessage());
                }


                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final JSONObject jsonResult;
                    final String result = response.body().string();

                    try {
                        jsonResult = new JSONObject(result);
                        JSONObject sample;
                        sample = jsonResult.getJSONObject("rates");
                        String val=sample.getString("INR");
                        Float euro=Float.parseFloat(ed1.getText().toString());
                        Float rupee=Float.parseFloat(val);
                        final Float ans=euro*rupee;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ed2.setText(ans.toString());
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });


        }  catch (Exception ex) {
            // outputTextView.setText(ex.getMessage());
        }

    }
}