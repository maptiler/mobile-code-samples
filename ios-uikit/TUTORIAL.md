# MapTiler Tutorial for iOS UIKIt/Swift

This tutorial describes how to create a simple iOS application using Swift and UIKit.

> This tutorial was written for macOS

## Create new project

Open Xcode and create new project. Choose iOS -> App template. In the "Choose options for your new project" select Interface to "Storyboard", lifecycle to "UI App Delegate", language to "Swift"

## Add MapBox Native SDK for iOS

Add mapBox SDK to your project using CocoaPods.

1. If you don't have CocoaPods installed, run `sudo gem install cocoapods`
1. Open a terminal window, and `cd` into your project directory.
1. Create a Podfile by running `pod init`.
1. Add MapBox dependencies to your Podfile. The file should look as follows:

    ```ruby
        # Uncomment the next line to define a global platform for your project
        platform :ios, '9.0'

        target 'SimpleMap_UIKit' do
        use_frameworks!

        # mapBox dependencies
        pod 'Mapbox-iOS-SDK', '6.2.1'	

        end
    ```

1. Run `pod install` to download and integrate dependencies.
1. The previous command will generate workspace file. Use it from now on. (`SimpleMap_UIKit.xcworkspace`).

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
