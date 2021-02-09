# MapTiler Tutorial for iOS UIKIt/Swift

This tutorial describes how to create a simple iOS application using Swift and UIKit.

> This tutorial was written for macOS

## Create new project

Open Xcode and create new project. Choose iOS -> App template. In the "Choose options for your new project" select Interface to "Storyboard", lifecycle to "UI App Delegate", language to "Swift"

## Add MapLibre Native SDK for iOS

Add MapLibre SDK to your project using Swift Package Manager.

1. To add a package dependency to your Xcode project, select File > Swift Packages > Add Package Dependency and enter its repository URL. You can also navigate to your target’s General pane, and in the “Frameworks, Libraries, and Embedded Content” section, click the + button, select Add Other, and choose Add Package Dependency.
1. Either add MapTiler GitHub distribution URL (https://github.com/maptiler/maplibre-gl-native-distribution) or search for `maplibre-gl-native` package.<br/>
![SwiftPackage1](SwiftPackage1.png "Searching for Swift Package")
1. Choose "next". Xcode should clone the distribution repository and download the binaries. Choose both mapBox and MapBox Mobile Events libraries.<br/>
![SwiftPackage2](SwiftPackage2.png "Add binaries")

## MapTiler Key

1. Create [MapTiler cloud](https://www.maptiler.com/cloud/) account.
1. [Obtain the api key](https://cloud.maptiler.com/account/keys).
1. Set the MapTilerKey property in `SimpleMap_UIKit/info.plist` to the value obtained in the previous step.
1. Open the `SimpleMap_UIKit.xcworkspace` in Xcode and navigate to ViewController.swift. Add code to read MapTilerKey from property list.

```swift
    func getMapTilerkey() -> String {
        let mapTilerKey = Bundle.main.object(forInfoDictionaryKey: "MapTilerKey") as? String
        validateKey(mapTilerKey)
        return mapTilerKey!
    }
```

## Initialize Map View

1. Open the `ViewController.swift` file.
1. Add the following code in the `viewDidLoad` method in order to construct map view.

```swift
    func viewDidLoad() {
        super.viewDidLoad()

        // retrieve MapTiler key from property list
        let mapTilerKey = getMapTilerkey()

        title = "Simple Map"
        // construct style URL
        let styleURL = URL(string: "https://api.maptiler.com/maps/streets/style.json?key=\(mapTilerKey)")
        // create the map view
        let mapView = MGLMapView(frame: view.bounds, styleURL: styleURL)
        mapView.autoresizingMask = [.flexibleWidth, .flexibleHeight]
        mapView.delegate = self
        mapView.logoView.isHidden = true
        // Set the map’s center coordinate and zoom level.
        mapView.setCenter(
            CLLocationCoordinate2D(latitude: 47.127757, longitude: 8.579139),
            zoomLevel: 10,
            animated: false)
        view.addSubview(mapView)
    }
```

> If you would need to respond to MapView events, for example perform an action after MapView initialization finished, place the `MGLMapViewDelegate` protocol name after the `ViewController` name and set the MapView delegate reference to ViewController
> 
>```swift
>class ViewController: UIViewController, MGLMapViewDelegate { 
>       ...
>}
>```
> 
>and 
> 
>```swift
>override func viewDidLoad() {
>   ...
>   mapView.delegate = self
>   ...
>}
>```
>

![Application Screenshot](SimpleMap_UIKit.png "Application Screenshot")
