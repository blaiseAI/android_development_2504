package ca.nait.bsebagabo1.simpleaccel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener
{

    private SensorManager sensorManager;
    TextView tvXCoor;
    TextView tvYCoor;
    TextView tvZCoor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvXCoor = findViewById(R.id.tv_x_coor);
        tvYCoor = findViewById(R.id.tv_y_coor);
        tvZCoor = findViewById(R.id.tv_z_coor);

        Context context;
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        tvXCoor.setText("X: " + x);
        tvXCoor.setText("Y: " + y);
        tvXCoor.setText("Z: " + z);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }
}
