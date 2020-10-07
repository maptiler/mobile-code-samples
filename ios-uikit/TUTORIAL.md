# Tutorial

## Test 1

```swift
func getMapTilerkey() -> String {
        guard let mapTilerKey = UIApplication.mapTilerKey else {
            preconditionFailure("Failed to read MapTiler key from info.plist")
        }
        let result: ComparisonResult = mapTilerKey.compare("placeholder", options: NSString.CompareOptions.caseInsensitive, range: nil, locale: nil)
        if result == .orderedSame {
            preconditionFailure("Please enter correct MapTiler key in info.plist[MapTilerKey] property")
        }
        return mapTilerKey
    }
```

## Test 2

```swift
func viewDidLoad() {
        super.viewDidLoad()

        let mapTilerKey = getMapTilerkey()

        title = "Simple Map"
        let styleURL = URL(string: "https://api.maptiler.com/maps/streets/style.json?key=\(mapTilerKey)")
        let mapView = MGLMapView(frame: view.bounds, styleURL: styleURL)
        mapView.autoresizingMask = [.flexibleWidth, .flexibleHeight]
        mapView.delegate = self
        mapView.logoView.isHidden = true
        // Set the map’s center coordinate and zoom level.
        mapView.setCenter(CLLocationCoordinate2D(latitude: 47.127757, longitude: 8.579139), zoomLevel: 10, animated: false)
        view.addSubview(mapView)
    }
```
