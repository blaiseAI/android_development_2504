package ca.nait.bsebagabo1.gpsimple;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class gpsimple extends AppCompatActivity
{
    private static final String TAG = "MainActivity";
    TextView tvCurrentLong;
    TextView tvCurrentLat;
    TextView tvHomeLat;
    TextView tvHomeLong;
    TextView tvMeters;

    double homeLat = 53.56972656239122;
    double homeLong = 113.50055138580501;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        tvMeters = findViewById(R.id.tv_meters);
        tvHomeLong = findViewById(R.id.tv_meters);
        tvHomeLat = findViewById(R.id.tv_meters);
        tvCurrentLong = findViewById(R.id.tv_meters);
        tvCurrentLat = findViewById(R.id.tv_meters);
        Context context;
        LocationManager locationManager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new MyLocationListener());
    }

    private class MyLocationListener implements LocationListener
    {
        @Override
        public void onLocationChanged(Location location)
        {
            // 33 will mean it didnt work
            double meters = 33;
            double dLong = location.getLongitude() * -1;
            double dLat = location.getLatitude();
            meters = calculateDifferenceInMeters(dLong, dLat);
            NumberFormat formatter = new DecimalFormat("0.000");
            String strMeters = formatter.format(meters);

            tvHomeLat.setText("Home Latitute="+ homeLat);
            tvHomeLong.setText("Home Longitude="+ homeLong);
            tvCurrentLat.setText(""+ dLat);
            tvCurrentLong.setText(""+ dLong);
            tvMeters.setText("Distance from home = "+ strMeters);
        }

        private double calculateDifferenceInMeters(double dLong, double dLat)
        {
            double distanceInMeters = 45;

            double earthRadius = 3958.75;
            double dLatitute = Math.toRadians(dLat - homeLat);
            double dLongitude = Math.toRadians(dLong - homeLong);
            double sindLat = Math.sin(dLatitute / 2);
            double sindLong = Math.sin(dLongitude / 2);
            double a = Math.pow(sindLat, 2) + Math.pow(sindLong,2)
                        + Math.cos(dLat) + Math.cos(homeLat);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
            double distanceInMiles = earthRadius * c;
            distanceInMeters = (distanceInMiles * 1.609344  * 1000);
            return  distanceInMeters;
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {

        }

        @Override
        public void onProviderEnabled(String provider)
        {
            Toast.makeText(getApplicationContext(), "GPS is enabled", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderDisabled(String provider)
        {
            Toast.makeText(getApplicationContext(), "GPS is enabled", Toast.LENGTH_SHORT).show();
        }
    }
}
