package com.example.week06;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements OnClickListener
{
    private static final String TAG = "MainActivity";
    SharedPreferences prefs;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        Button postButton = findViewById(R.id.button_post_chat);
        postButton.setOnClickListener(this);

        Log.d(TAG, "in onCreate()");
    }

    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.button_post_chat:
            {
                EditText text = (EditText) findViewById(R.id.edit_text_post_chat);
                String chat = text.getText().toString();
                pd = ProgressDialog.show(this, "", "Posting message...");
                new ChatWriter().execute(chat);
                text.setText("");
                Log.d(TAG,"posting a message menu item ");
                break;

            }
        }
    }




    private class ChatWriter extends AsyncTask<String,Void,String>
    {

        @Override
        protected String doInBackground(String... strings)
        {
            String message = strings[0];

            try
            {
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost("http://www.youcode.ca/JitterServlet");
                List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
                postParameters.add(new BasicNameValuePair("DATA", message));
                postParameters.add(new BasicNameValuePair("LOGIN_NAME", "Gerry"));
                UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters);
                request.setEntity(formEntity);
                client.execute(request);
            }
            catch(Exception e)
            {
                Log.d(TAG, "Error posting message.");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s)
        {
            pd.dismiss();
        }
    }

}
