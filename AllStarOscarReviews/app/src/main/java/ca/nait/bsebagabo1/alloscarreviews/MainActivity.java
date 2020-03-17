package ca.nait.bsebagabo1.alloscarreviews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener
{

    // Global Declaration of controlls
    private RadioGroup CategoryGroup;
    private RadioButton category_Value;
    private String category;
    private String nominee;
    private String review;
    private Button btnSubmit;

    SharedPreferences prefs;
    View mainView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT > 9){
            StrictMode.ThreadPolicy ourPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(ourPolicy);
        }

        btnSubmit = (Button)findViewById(R.id.post_btn);
        btnSubmit.setOnClickListener(this);
        Context context;
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);
        mainView = findViewById(R.id.linear_layout_main);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId()){
            case R.id.menu_preferences:
                Intent intent = new Intent(this, PrefsActivity.class);
                this.startActivity(intent);
                //Toast.makeText(this, "Preference Selected", Toast.LENGTH_SHORT).show();
                return  true;
            case R.id.menu_view_reviews:
                Intent intentView = new Intent(this,ViewReviewsActivity.class);
                this.startActivity(intentView);
                //Toast.makeText(this, "View Reviews Selected", Toast.LENGTH_SHORT).show();
                return  true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view)
    {
        // TODO
        // Get the RadioBtn Group and get the selected value
        CategoryGroup = (RadioGroup) findViewById(R.id.Categories);
        EditText nomineeText = (EditText) findViewById(R.id.post_nominee);
        nominee = nomineeText.getText().toString();
        EditText reviewText = (EditText) findViewById(R.id.post_review);
        review = reviewText.getText().toString();
        String userName = prefs.getString("preference_user_name", "unknown");
        String password = prefs.getString("preference_user_password", "oscar275");
        switch (view.getId()){
            case R.id.post_btn:{

                int selectedId = CategoryGroup.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                category_Value = (RadioButton) findViewById(selectedId);
                String categoryParameter = category_Value.getText().toString();
                category = categoryParameter.equalsIgnoreCase("Best Picture") ? "Best Film".split(" ")[1].toLowerCase():categoryParameter.split(" ")[1].toLowerCase();
//                category = categoryParameter.split(" ")[1].toLowerCase();
                postToSever(review,userName,nominee, category, password);
                nomineeText.setText("");
                reviewText.setText("");
                //Toast.makeText(this, "You selected: "+category+" "+nominee+" "+review+"Username:"+userName+"Password:"+password, Toast.LENGTH_LONG).show();


            }
        }

    }

    private void postToSever(String review, String userName, String nominee, String category, String password)
    {
        try
        {
            HttpClient client = new DefaultHttpClient();
            String uri;
            HttpPost httpPost = new HttpPost("http://www.youcode.ca/Lab01Servlet");
            List<NameValuePair> formParameters = new ArrayList<NameValuePair>();
            formParameters.add(new BasicNameValuePair("REVIEW", review));
            formParameters.add(new BasicNameValuePair("REVIEWER", userName));
            formParameters.add(new BasicNameValuePair("NOMINEE", nominee));
            formParameters.add(new BasicNameValuePair("CATEGORY", category));
            formParameters.add(new BasicNameValuePair("PASSWORD", password));
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(formParameters);
            httpPost.setEntity(formEntity);
            client.execute(httpPost);
            review = "";
            nominee = "";
        }
        catch (Exception e){
            Toast.makeText(this, "Error:"+ e, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
        String  bgColor = prefs.getString("preference_main_bg_color", "#990000");
        mainView.setBackgroundColor(Color.parseColor(bgColor));
    }
}
