package com.maptiler.maplibresamples

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [SdkExampleListActivity]
 * in two-pane mode (on tablets) or a [ExampleDetailActivity]
 * on handsets.
 */
class SimpleMapFragment : Fragment() {

    private var mapView: MapView? = null

    private fun getMapTilerKey(): String? {
        return activity!!.packageManager.getApplicationInfo(
            activity!!.packageName,
            PackageManager.GET_META_DATA
        ).metaData.getString("com.maptiler.maplibresamples.mapTilerKey")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         activity?.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)?.title = "Simple Map"
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
            throw Exception("Failed to read MapTiler key")
        }
        if (mapTilerKey.toLowerCase() == "placeholder") {
            throw Exception("Please enter correct MapTiler key in module-level gradle.build file in defaultConfig section")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapTilerKey = getMapTilerKey()
        validateKey(mapTilerKey)
        val styleUrl = "https://api.maptiler.com/maps/streets/style.json?key=${mapTilerKey}";

        // Create map view
        mapView = view.findViewById(R.id.simpleMapView)
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
//        val rootView = inflater.inflate(R.layout.simple_map, container, false)

//        // Show the dummy content as text in a TextView.
//        item?.let {
//            rootView.findViewById<TextView>(R.id.item_detail).text = it.description
//        }


        // Get the MapBox context
        Mapbox.getInstance(context!!, null)

        // Set the map view layout
        // setContentView(R.layout.simple_map)
        val rootView = inflater.inflate(R.layout.simple_map, container, false)
        return rootView
    }
}