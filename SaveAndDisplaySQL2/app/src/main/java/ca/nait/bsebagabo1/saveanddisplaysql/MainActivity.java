package ca.nait.bsebagabo1.saveanddisplaysql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    private static final String TAG = "MainActivity";
    DatabaseHelper mDatabaseHelper;
    private Button btnAdd, btnViewData;
    private Spinner spinner;
    private EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Declare everything on create
        View view;
        spinner = findViewById(R.id.spinner);
        editText = findViewById(R.id.edit_text);
        btnAdd = findViewById(R.id.btnAdd);
        btnViewData = findViewById(R.id.btnView);
        mDatabaseHelper = new DatabaseHelper(this);
        // spinner click listener
        spinner.setOnItemSelectedListener(this);
        //loading spinner data from database
        loadSpinnerData();
        btnAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String newEntry = editText.getText().toString();
                if(editText.length() !=0){
                    AddData(newEntry);
                    editText.setText("");
                    //Loading spinner with newly added data
                    loadSpinnerData();
                }else{
                    toasMessage("You must provide a value!");
                }
            }
        });

    }

    /**
     * Function to load the spinner data from SQLite database
     */
    private void loadSpinnerData()
    {
        // database handler
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        // spinner Drop down elements
        List<String> people = db.getAllPeoples();

        // Create adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, people);

        // Drop down layout style list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    public void AddData(String newEntry){
        boolean insertData = mDatabaseHelper.addData(newEntry);
        if(insertData){
            toasMessage("Data Successfully Inserted!");
        }else{
            toasMessage("Something went wrong");
        }
    }

    /**
     * Customizable toast
     * @param message
     */
    private void toasMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        // on selection a spinner item
        String person = parent.getItemAtPosition(position).toString();
        toasMessage("You selected: "+position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }
}
