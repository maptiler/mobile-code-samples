# MapTiler Tutorial for iOS SwiftUI/Swift

This tutorial describes how to create a simple iOS application using Swift and SwiftUI.

> This tutorial was written for macOS

## Create new project

Open Xcode and create new project. Choose iOS -> App template. In the "Choose options for your new project" select Interface to "SwiftUI", lifecycle to "SwiftUI App", language to "Swift"

## Add MapBox Native SDK for iOS

Add mapBox SDK to your project using CocoaPods.

1. If you don't have CocoaPods installed, run `sudo gem install cocoapods`
1. Open a terminal window, and `cd` into your project directory.
1. Create a Podfile by running `pod init`.
1. Add MapBox dependencies to your Podfile. The file should look as follows:

    ```ruby
        # Uncomment the next line to define a global platform for your project
        platform :ios, '9.0'

        target 'SimpleMap_SwiftUI' do
        use_frameworks!

        # mapBox dependencies
        pod 'Mapbox-iOS-SDK', '6.2.1'	

        end
    ```

1. Run `pod install` to download and integrate dependencies.
1. The previous command will generate workspace file. Use it from now on. (`SimpleMap_SwiftUI.xcworkspace`).

## Create the SwiftUI View for the map

In your project, add new SwiftUI View to the SimpleMap_SwiftUI folder and name it `MapView.swift`

## MapTiler Key

1. Create [MapTiler cloud](https://www.maptiler.com/cloud/) account.
1. [Obtain the api key](https://cloud.maptiler.com/account/keys).
1. Set the MapTilerKey property in `SimpleMap_SwiftUI/info.plist` to the value obtained in the previous step.
1. Add the following method to MapView.swift. The method will MapTilerKey from property list.

```swift
    func getMapTilerkey() -> String {
        let mapTilerKey = Bundle.main.object(forInfoDictionaryKey: "MapTilerKey") as? String
        validateKey(mapTilerKey)
        return mapTilerKey!
    }
```

## Create MapBox map view

1. In your project, add new SwiftUI View to the SimpleMap_SwiftUI folder and name it `MapView.swift`
1. In order to use native UIKit views in SwiftUI view, you must use `[UIViewRepresentable](https://developer.apple.com/documentation/swiftui/uiviewrepresentable)` wrapper. The instance of custom type which adopts `UIViewRepresentable` protocol is responsible for creation and management a UIView object in your SwiftUI interface.

    ```swift
        struct MapView: UIViewRepresentable {
            ...
        }
    ```

1. The `UIViewRepresentable` requires to implement `makeUIViewController(context:)` method that creates the instance of with the desired UIKit view. Add the following code to create map view instance

    ```swift
    func makeUIView(context: Context) -> MGLMapView {
        // read the key from property list
        let mapTilerKey = getMapTilerkey()
        validateKey(mapTilerKey)
        
        // Build the style url
        let styleURL = URL(string: "https://api.maptiler.com/maps/streets/style.json?key=\(mapTilerKey)")
        
        // create the mapview
        let mapView = MGLMapView(frame: .zero, styleURL: styleURL)
        mapView.autoresizingMask = [.flexibleWidth, .flexibleHeight]
        mapView.logoView.isHidden = true
        mapView.setCenter(
            CLLocationCoordinate2D(latitude: 47.127757, longitude: 8.579139),
            zoomLevel: 10,
            animated: false)
        mapView.delegate = context.coordinator
        return mapView
    }
    ```

1. The `UIViewRepresentable` view also requires to implement `updateUIView(_:context:)` which is used to configure the newly created instance. We dont need to configure anything so we will keep it empty.

    ```swift
        func updateUIView(_ uiView: MGLMapView, context: Context) {}
    ```


## Respond to map events

If order to to respond to map events, for example perform an action after MapView initialization finished. In SwiftUI, a `Coordinator` can be used with delegates, data sources, and user events. The `UIViewRepresentable` protocol defines `makeCoordinator()` method which creates coordinator instance. Add the following code to declare coordinator class:

```swift
    class Coordinator: NSObject, MGLMapViewDelegate {
        var control: MapView
        
        init(_ control: MapView) {
            self.control = control
        }

        func mapViewDidFinishLoadingMap(_ mapView: MGLMapView) {
            // write your custom code after map has been loaded
        }
    }
```

And then add the following method to the SwiftUI view:

```swift
    func makeCoordinator() -> MapView.Coordinator {
        Coordinator(self)
    }
```

And finally set the reference coordinator on mapbox map view

```swift
    mapView.delegate = context.coordinator
```

![Application Screenshot](SimpleMap_SwiftUI.png "Application Screenshot")
