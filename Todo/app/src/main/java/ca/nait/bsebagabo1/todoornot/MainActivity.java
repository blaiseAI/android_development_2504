package ca.nait.bsebagabo1.todoornot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    Spinner LIST_SPINNER;
    Button ADD;
    Button UPDATE;
    Button DELETE;
    EditText EDIT_TEXT;
    ArrayList<String> LIST_NAMES = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // DATE TIME USING CALENDAR
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        TextView textViewDate = findViewById(R.id.text_view_date);
        textViewDate.setText(currentDate);

        // GET CONTROLS
        LIST_SPINNER = findViewById(R.id.List_Name_Spinner);
        ADD = findViewById(R.id.button_add_list);
        UPDATE = findViewById(R.id.button_Update_list);
        DELETE = findViewById(R.id.button_Delete_list);
        EDIT_TEXT = findViewById(R.id.Edit_Text_List_Name);

        // ADAPTER
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_single_choice, LIST_NAMES);
        LIST_SPINNER.setAdapter(adapter);

        LIST_SPINNER.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                // SET SELECTED ITEM TO EDIT TEXT
                EDIT_TEXT.setText(LIST_NAMES.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        // HANDLE ON CLICK EVENT
        ADD.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
             add();
            }
        });

        UPDATE.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                update();
            }
        });
        DELETE.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                delete();
            }
        });

        /*
         * TODO
         *  integrate update and Delete button
         *  Implement update and Delete functions
         */
    }
    // CRUD OPERATIONS

    // ADD
    private void add(){
        // GET THE VALUE THE USER HAS TYPED
        String listname = EDIT_TEXT.getText().toString();
        if(!listname.isEmpty() && listname.length()>0){
            if(!LIST_NAMES.contains(listname.toLowerCase())){
                adapter.add(listname);
                // REFRESH SPINNER
                adapter.notifyDataSetChanged();
                EDIT_TEXT.setText("");
                Toast.makeText(this, "List "+listname+" added", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "List "+listname+" already exists", Toast.LENGTH_SHORT).show();
            }

        }else
        {
            Toast.makeText(this, "!! Nothing to Add", Toast.LENGTH_SHORT).show();
        }
    }

    // UPDATE
    private void update(){
        String listname = EDIT_TEXT.getText().toString();
        // GET SELECTED ITEM POS
        int pos = LIST_SPINNER.getSelectedItemPosition();
        if(!listname.isEmpty() && listname.length()>0){
            // REMOVE
            adapter.remove(LIST_NAMES.get(pos));
            // INSERT NEW VALUE
            adapter.insert(listname, pos);
            // REFRESH
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "!! Not Updated", Toast.LENGTH_SHORT).show();
        }
    }

    // DELETE
    private void delete(){
        String listname = EDIT_TEXT.getText().toString();
        // GET SELECTED ITEM POS
        int pos = LIST_SPINNER.getSelectedItemPosition();
        if( pos > -1){
            // REMOVE
            adapter.remove(LIST_NAMES.get(pos));
            // REFRESH
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "List Removed", Toast.LENGTH_SHORT).show();
            EDIT_TEXT.setText("");
        }else{
            Toast.makeText(this, "!! List Not Deleted", Toast.LENGTH_SHORT).show();
        }
    }
}
