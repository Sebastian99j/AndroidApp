package com.zadanierekrutacyjne.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import com.zadanierekrutacyjne.R;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.Polyline;

public class MapActivity2 extends AppCompatActivity {
    MapView mMapView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        setContentView(R.layout.activity_map);

        mMapView = (MapView) findViewById(R.id.mapWorld);
        mMapView.setTileSource(TileSourceFactory.MAPNIK);

        mMapView.setBuiltInZoomControls(true);
        mMapView.setMultiTouchControls(true);
        GeoPoint overlayCenterPoint = new GeoPoint(52.5, 21.5);
        IMapController mapController = mMapView.getController();
        mapController.setZoom(10f);
        mapController.setCenter(overlayCenterPoint);
        mMapView.setMapOrientation(0.0f);

        Polyline polyline = new Polyline();
        polyline.setColor(Color.RED);

        Polygon polygon = new Polygon();

        mMapView.getOverlays().add(polyline);

        mMapView.getOverlays().add(new MapEventsOverlay(new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                polyline.addPoint(p);
                mMapView.getOverlays().add(polyline);
                System.out.println("singla tap");
                return true;
            }
            @Override
            public boolean longPressHelper(GeoPoint p) {
                polygon.addPoint(p);
                mMapView.getOverlays().add(polygon);
                System.out.println("long press");
                return false;
            }
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    public void back(View view) {
        Intent intent = new Intent(this, DetailsActivity.class);
        startActivity(intent);
    }
}