package ca.nait.bsebagabo1.alloscarreviews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

public class ViewReviewsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{

    /***
     * Code Bellow is to be implemented if using customList with row
     */
    ArrayList<HashMap<String, String>> chatter = new ArrayList<>();
    //public static final String REVIEWER = "username";
    //public static final String REVIEW = "text";
    //public static final String DATE = "myDate";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reviews);
        /* Create a spinner */
        Spinner spinner = findViewById(R.id.spinnerCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_arrays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        String queryParameter = parent.getItemAtPosition(position).toString().split(" ")[1].toLowerCase();
        if(queryParameter.contains("picture")){
            displayChatter("film");
        }
        else{
            displayChatter(queryParameter);
        }
        //Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    private void displayChatter(String parameter)
    {
       String[] keys = new String[]{"REVIEWER", "DATE", "CATEGORY", "NOMINEE", "REVIEW"};
       int[] ids = new int[]{R.id.text_view_custom_row_reviewer, R.id.text_view_custom_row_date,R.id.text_view_custom_row_category,R.id.post_nominee, R.id.text_view_custom_row_review};
        SimpleAdapter adapter = new SimpleAdapter(this, chatter, R.layout.custom_row, keys, ids);
        getFromServer(parameter);
        ListView lv = findViewById(R.id.menu_item_custom_list);
        lv.setAdapter(adapter);
    }

    private void getFromServer(String para)
    {
        // Create an endPoint variable
        String Url= "http://www.youcode.ca/Lab01Servlet?CATEGORY=";
        String uriEndPoint = Url + para;
        Toast.makeText(this, "Message "+uriEndPoint+ " from the getFromServer Method", Toast.LENGTH_SHORT).show();
        BufferedReader in = null;
        try{
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(uriEndPoint));
            HttpResponse response = client.execute(request);
            in = new BufferedReader( new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            // Clear the area
            chatter.clear();
            while((line = in.readLine()) != null){

                HashMap<String, String> tempMap = new HashMap<String, String>();
                tempMap.put("REVIEWER", line);

                line = in.readLine();
                tempMap.put("DATE", line);

                line = in.readLine();
                tempMap.put("CATEGORY", line);

                line = in.readLine();
                tempMap.put("NOMINEE", line);

                line = in.readLine();
                tempMap.put("REVIEW", line);

                chatter.add(tempMap);
            }
            in.close();
        }
        catch (Exception e){
            Toast.makeText(this, "Error "+uriEndPoint+ " from the getFromServer Method", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }
}
