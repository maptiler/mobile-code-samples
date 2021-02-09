package com.maptiler.simplemap

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView


class MainActivity : AppCompatActivity() {

    // snippet(MapViewMember)
    private var mapView: MapView? = null

    // snippet(MapInit)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mapTilerKey = getMapTilerKey()
        validateKey(mapTilerKey)
        val styleUrl = "https://api.maptiler.com/maps/streets/style.json?key=${mapTilerKey}";

        // Get the MapBox context
        Mapbox.getInstance(this, null)

        // Set the map view layout
        setContentView(R.layout.activity_main)

        // Create map view
        mapView = findViewById(R.id.mapView)
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync { map ->
            // Set the style after mapView was loaded
            map.setStyle(styleUrl) {
                map.uiSettings.setAttributionMargins(15, 0, 0, 15)
                // Set the map view center
                map.cameraPosition = CameraPosition.Builder()
                    .target(LatLng(47.127757, 8.579139))
                    .zoom(10.0)
                    .build()
            }
        }
    }

    // snippet(GetKey)
    private fun getMapTilerKey(): String? {
        return packageManager.getApplicationInfo(
            packageName,
            PackageManager.GET_META_DATA
        ).metaData.getString("com.maptiler.simplemap.mapTilerKey")
    }

    // snippet(onStart)
    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    // snippet(onResume)
    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    // snippet(onPause)
    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    // snippet(onStop)
    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    // snippet(onSaveInstanceState)
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }

    // snippet(onLowMemory)
    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    // snippet(onDestroy)
    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    private fun validateKey(mapTilerKey: String?) {
        if (mapTilerKey == null) {
            throw Exception("Failed to read MapTiler key from info.plist")
        }
        if (mapTilerKey.toLowerCase() == "placeholder") {
            throw Exception("Please enter correct MapTiler key in module-level gradle.build file in defaultConfig section")
        }
    }
}