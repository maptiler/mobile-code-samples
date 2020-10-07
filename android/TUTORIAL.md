# Tutorial template

## Test 1

```kotlin
    private fun makeStyleUrl(): String {
        val mapTilerKey = "0yQuE2U15ztIZ2qNovfS"
        return  "https://api.maptiler.com/maps/streets/style.json?key=${mapTilerKey}";
    }
```

## Test 2

```kotlin
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the Mapbox context.
        Mapbox.getInstance(this, null)

        setContentView(R.layout.activity_main)

        // We get the map view to set its style with the desired Jawg url.
        mapView = findViewById(R.id.mapView)
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync { map ->
            map.setStyle(makeStyleUrl()) {
                // Map fully loaded in this scope.
                // Update attributions position
                map.uiSettings.setAttributionMargins(15, 0, 0, 15)
            }
        }
    }
```
