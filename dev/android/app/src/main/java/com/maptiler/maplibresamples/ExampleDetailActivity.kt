package com.maptiler.maplibresamples

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMapOptions
import com.mapbox.mapboxsdk.maps.SupportMapFragment

class ExampleDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)
        setSupportActionBar(findViewById(R.id.detail_toolbar))

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don"t need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //

        Mapbox.getInstance(applicationContext, null)

        val mapFragment: SupportMapFragment
        if (savedInstanceState == null) {
//            val fragmentClass = intent.getStringExtra(ARG_FRAGMENT_CLASS)
//            val fragment = Class.forName(fragmentClass).newInstance() as Fragment
//            supportFragmentManager.beginTransaction()
//                    .add(R.id.item_detail_container, fragment)
//                    .commit()

            // Create fragment
            val transaction = supportFragmentManager.beginTransaction()

            // Build mapboxMap
            val options = MapboxMapOptions.createFromAttributes(this, null)
            options.camera(
                CameraPosition.Builder()
                .target(LatLng(-52.6885, -70.1395))
                .zoom(9.0)
                .build())

            // Create map fragment
            mapFragment = SupportMapFragment.newInstance(options)

            // Add map fragment to parent container
            transaction.add(R.id.item_detail_container, mapFragment, "com.mapbox.map")
            transaction.commit()
        } else {
            mapFragment = supportFragmentManager.findFragmentByTag("com.mapbox.map") as SupportMapFragment
        }

        val mapTilerKey = getMapTilerKey()
        validateKey(mapTilerKey)
        val styleUrl = "https://api.maptiler.com/maps/streets/style.json?key=${mapTilerKey}";

        mapFragment.getMapAsync {

                mapboxMap -> mapboxMap.setStyle(styleUrl) {

            // Map in fragment container is set up and the style has loaded.
            // Now you can add data or make other map adjustments.
        }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                android.R.id.home -> {
                    // This ID represents the Home or Up button. In the case of this
                    // activity, the Up button is shown. For
                    // more details, see the Navigation pattern on Android Design:
                    //
                    // http://developer.android.com/design/patterns/navigation.html#up-vs-back

                    navigateUpTo(Intent(this, SdkExampleListActivity::class.java))
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

    companion object {
        const val ARG_FRAGMENT_CLASS = "fragment_class"
    }

    private fun getMapTilerKey(): String? {
        return packageManager.getApplicationInfo(
            packageName,
            PackageManager.GET_META_DATA
        ).metaData.getString("com.maptiler.maplibresamples.mapTilerKey")
    }

    private fun validateKey(mapTilerKey: String?) {
        if (mapTilerKey == null) {
            throw Exception("Failed to read MapTiler key")
        }
        if (mapTilerKey.toLowerCase() == "placeholder") {
            throw Exception("Please enter correct MapTiler key in module-level gradle.build file in defaultConfig section")
        }
    }
}