package edu.niu.z1829451.hiker;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    LocationListener locationListener;
    TextView latitude, longitude, accuracy, altitude, address;
    String res = "";

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            update();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latitude = findViewById(R.id.LatTvId);
        longitude = findViewById(R.id.LongTvId);
        accuracy = findViewById(R.id.AccTvId);
        altitude = findViewById(R.id.AltTvId);
        address = findViewById(R.id.AddTvId);

        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) { //when phone moves

                latitude.setText(String.valueOf(location.getLatitude()));
                longitude.setText(String.valueOf(location.getLongitude()));
                accuracy.setText(String.valueOf(location.getAccuracy()));
                altitude.setText(String.valueOf(location.getAltitude()));

                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                try {
                    List<Address> address = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                    if(address!=null && address.size()>0){
                        Log.i("place", address.get(0).getFeatureName());
                        String number = address.get(0).getFeatureName();
                        String street = address.get(0).getThoroughfare();
                        String place = address.get(0).getLocality();
                        String admin = address.get(0).getAdminArea();
                        String po = address.get(0).getPostalCode();
                        String country = address.get(0).getCountryName();

                        String res = number + " " + street + "\n" + place +" " +  admin + "\n" + po + "\n" + country;

//                        Toast.makeText(getApplicationContext(), res, Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                address.setText(res);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) { //when locationService(on/off) is changed in phone

            }

            @Override
            public void onProviderEnabled(String s) { //when locationService is on

            }

            @Override
            public void onProviderDisabled(String s) { //when locationService is off

            }
        };

        if(Build.VERSION.SDK_INT<23){
            //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //ask for permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                Location lastknownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                latitude.setText(String.valueOf(lastknownLocation.getLatitude()));
                longitude.setText(String.valueOf(lastknownLocation.getLongitude()));
                accuracy.setText(String.valueOf(lastknownLocation.getAccuracy()));
                altitude.setText(String.valueOf(lastknownLocation.getAltitude()));

                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                try {
                    List<Address> address = geocoder.getFromLocation(lastknownLocation.getLatitude(), lastknownLocation.getLongitude(), 1);

                    if(address!=null && address.size()>0){
                        Log.i("place", address.get(0).getFeatureName());
                        String number = address.get(0).getFeatureName();
                        String street = address.get(0).getThoroughfare();
                        String place = address.get(0).getLocality();
                        String admin = address.get(0).getAdminArea();
                        String po = address.get(0).getPostalCode();
                        String country = address.get(0).getCountryName();

                        res = number + " " + street + "\n" + place +" " +  admin + "\n" + po + "\n" + country;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                address.setText(res);
            }
        }
    }

    public void update(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        Location lastknownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        latitude.setText(String.valueOf(lastknownLocation.getLatitude()));
        longitude.setText(String.valueOf(lastknownLocation.getLongitude()));
        accuracy.setText(String.valueOf(lastknownLocation.getAccuracy()));
        altitude.setText(String.valueOf(lastknownLocation.getAltitude()));

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        try {
            List<Address> address = geocoder.getFromLocation(lastknownLocation.getLatitude(), lastknownLocation.getLongitude(), 1);

            if(address!=null && address.size()>0){
                Log.i("place", address.get(0).getFeatureName());
                String number = address.get(0).getFeatureName();
                String street = address.get(0).getThoroughfare();
                String place = address.get(0).getLocality();
                String admin = address.get(0).getAdminArea();
                String po = address.get(0).getPostalCode();
                String country = address.get(0).getCountryName();

                res = number + " " + street + "\n" + place +" " +  admin + "\n" + po + "\n" + country;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        address.setText(res);
    }
}
