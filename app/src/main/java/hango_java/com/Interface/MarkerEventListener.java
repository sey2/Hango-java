package hango_java.com.Interface;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;


public class MarkerEventListener implements MapView.POIItemEventListener{
    private Activity activity;
    MapPoint.GeoCoordinate currentLocation;
    double currentLatitude, currentLongitude;

    public MarkerEventListener(Activity activity){
        this.activity = activity;
    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
        // is Deprecated.
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {
        getCurrentLocation();

        MapPoint.GeoCoordinate endPoint = mapPOIItem.getMapPoint().getMapPointGeoCoord();
        String uri = "kakaomap://route?sp=" + currentLatitude + "," + currentLongitude + "&ep=" + endPoint.latitude + "," + endPoint.longitude +"&by=CAR";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        activity.startActivity(intent);
    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }

    public void getCurrentLocation(){
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        Location location = null;
        try {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }catch (SecurityException e){}

        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();
    }

}