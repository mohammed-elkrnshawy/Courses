package s.panorama.graduationproject.Classes;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;

import java.util.List;

import s.panorama.graduationproject.R;

public class getCurrentLocation implements  android.location.LocationListener {

    private LocationManager mLocationManager;
    private Context context;

    public getCurrentLocation(Context context){
        this.context=context;
    }

    private void buildAlertMessageNoGps() {
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog
                .Builder(context);
        builder.setMessage(context.getString(R.string.pleaseOpenGPS))
                .setCancelable(false)
                .setPositiveButton(R.string.open, new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                        ((Activity)context).finish();
                    }
                });
        final android.support.v7.app.AlertDialog alert = builder.create();
        alert.show();
    }

    public Location getCurrentLocation() {
        Location location = null;
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context
                , Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            location = getLastKnownLocation();

            if (location == null) {
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
            }
        }
        else {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,  Manifest.permission.ACCESS_COARSE_LOCATION}, 3);
        }

        return location;
    }

    private Location getLastKnownLocation() {
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context
                    , Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return bestLocation;
            }
            Location l = mLocationManager.getLastKnownLocation(provider);

            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {

                bestLocation = l;
            }
        }
        if (bestLocation == null) {
            return null;
        }
        return bestLocation;
    }

    @Override
    public void onLocationChanged(Location location) {
        mLocationManager.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}