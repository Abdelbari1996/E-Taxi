package com.zootopia.abdelbari.etaxi;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;
import java.util.Locale;
public class MainActivity extends Activity {
    TextView address,location;
    Button getLocation;
    LocationTracker tracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        address=(TextView)findViewById(R.id.address);
        location=(TextView)findViewById(R.id.location);
        getLocation=(Button)findViewById(R.id.getlocation);
        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create LocationTracker Object
                tracker=new LocationTracker(MainActivity.this);
                // check if location is available
                if(tracker.isLocationEnabled)
                {
                    double latitude=tracker.getLatitude();
                    double longitude=tracker.getLongitude();
                    location.setText("Your Location is Latitude= " + latitude + " Longitude= " + longitude);
                    String addres= getCompleteAddressString(latitude,longitude);
                    address.setText(addres);
                }
                else
                {
                    // show dialog box to user to enable location
                    tracker.askToOnLocation();
                }
            }
        });
    }
    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder
                    .getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                android.location.Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");
                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress
                            .append(returnedAddress.getAddressLine(i)).append(
                            "\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w(" location address", ""+ strReturnedAddress.toString());
            } else {
                Log.w(" location address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w(" location address", "Cannot get Address!");
        }
        return strAdd;
    }
}
