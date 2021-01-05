# Mapbox iOS SDK / UIKit / MapTiler Cloud example

A live Xcode project/app that demonstrates how to use Mapbox Maps SDK for iOS with MapTiler cloud.

### Other helpful links
- [Mapbox Maps SDK for iOS API documentation](https://docs.mapbox.com/ios/api/maps/)
- [First steps with the Mapbox Maps SDK for iOS](https://docs.mapbox.com/help/tutorials/first-steps-ios-sdk/)
- [MapTiler Cloud](https://www.maptiler.com/cloud/)

## Getting started
1. Open `SimpleMap_UIKit.xcodeproj` in Xcode.
1. Add MapLibre SDK to your project using Swift Package Manager. Select File > Swift Packages > Add Package Dependency and enter its repository URL. You can also navigate to your target’s General pane, and in the “Frameworks, Libraries, and Embedded Content” section, click the + button, select Add Other, and choose Add Package Dependency.
1. Either add MapTiler GitHub distribution URL (https://github.com/maptiler/maplibre-gl-native-distribution) or search for `maplibre-gl-native` package.
1. Choose "next". Xcode should clone the distribution repository and download the binaries. Choose both mapBox and MapBox Mobile Events libraries.
1. Create MapTiler account and [obtain the api key](https://cloud.maptiler.com/account/keys).
1. Set the MapTilerKey property in `SimpleMap_UIKit/info.plist` to the value obtained in the previous step.
