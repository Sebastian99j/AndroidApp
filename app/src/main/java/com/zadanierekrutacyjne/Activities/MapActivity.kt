package com.zadanierekrutacyjne.Activities

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import com.zadanierekrutacyjne.Functionalities.JavaMapSupport
import com.zadanierekrutacyjne.R
import kotlinx.android.synthetic.main.activity_map.*
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.drawing.MapSnapshot
import org.osmdroid.views.overlay.Marker

@Suppress("DEPRECATION")
class MapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))
        mapWorld.tileProvider.clearTileCache()
        Configuration.getInstance().cacheMapTileCount = 12
        Configuration.getInstance().cacheMapTileOvershoot = 12

        JavaMapSupport.titleSourceParam(mapWorld)

        mapWorld.setMultiTouchControls(true)
        val mapController = mapWorld.controller
        val startPoint: GeoPoint = GeoPoint(52.5, 21.5)
        mapController.setZoom(11.0)
        mapController.setCenter(startPoint)
        val context = this
        mapWorld.invalidate()
        createMarker()
    }

    private fun createMarker() {
        if (mapWorld == null) return
        val my_marker = Marker(mapWorld)
        my_marker.position = GeoPoint(52.5, 21.5)
        my_marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        my_marker.title = "Title"
        my_marker.setPanToView(true)
        mapWorld.overlays.add(my_marker)
        mapWorld.invalidate()
    }

    fun back(view: View) {
        val intent = Intent(applicationContext, DetailsActivity::class.java)
        startActivity(intent)
    }
}