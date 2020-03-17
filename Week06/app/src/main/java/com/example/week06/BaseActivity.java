package com.example.week06;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity
{
    private static final String TAG = "BaseActivity";
    @Override
    protected void onCreate( Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu)
    {
        MenuItem toggleItem = null;
        if(menu != null)
        {
            toggleItem = menu.findItem(R.id.menu_item_toggle_service);
            if(ChatService.bRun == false)
            {
                toggleItem.setTitle(getResources().getString(R.string.menu_item_start_service));
            }
            else
            {
                toggleItem.setTitle(getResources().getString(R.string.menu_item_stop_service));
            }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.menu_item_toggle_service:
            {
                if (ChatService.bRun == false)
                {
                    startService(new Intent(this, ChatService.class));
                }
                else
                {
                    stopService(new Intent(this, ChatService.class));
                }
                Log.d(TAG, "starting service");
                break;
            }
            case R.id.menu_item_home:
            {
                startActivity(new Intent(this, MainActivity.class) );
                Log.d(TAG, "display Home");
                break;
            }

            case R.id.menu_item_display_chatter:
            {
                startActivity(new Intent(this, DisplayActivity.class) );
                Log.d(TAG, "display chatter");
                break;
            }
            case R.id.menu_item_view_cursor:
            {
                startActivity(new Intent(this, CursorActivity.class) );
                Log.d(TAG, "view cursor");
                break;
            }
            case R.id.menu_item_view_preferences:
            {
                Intent intent = new Intent(this, PrefsActivity.class);
                startActivity(intent);
                break;
            }
        }
        return true;
    }

}
