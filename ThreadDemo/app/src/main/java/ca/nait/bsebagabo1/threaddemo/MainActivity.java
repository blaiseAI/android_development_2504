package ca.nait.bsebagabo1.threaddemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity
{
    public static  final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "in onCreate()");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
//        Switch(item.getItemId()){
//        case R.id.menu_item_start_service:
//
//            break;
//    }
        switch (item.getItemId()){
            case R.id.menu_item_start_service:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        Log.d(TAG, "In CreatingOptionsMenu()");
    }
}
