package ca.nait.bsebagabo1.alloscarreviews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class ViewReviewsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{

    /***
     * Code Bellow is to be implemented if using customList with row
     */
    //ArrayList<HashMap<String, String>> chatter = new ArrayList<>();
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

        // Pass the text to the getFromServer method for a paramter request
        getFromServer(queryParameter);
        //Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    private void getFromServer(String queryParameter)
    {
        // Create an endPoint variable
        String Url= "http://www.youcode.ca/Lab01Servlet?CATEGORY=";
        String uriEndPoint = Url + queryParameter;
        //Toast.makeText(this, "Message "+uriEndPoint+ " from the getFromServer Method", Toast.LENGTH_SHORT).show();
        ArrayList ReviewList = new ArrayList();
        try{

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
