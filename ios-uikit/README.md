# Mapbox iOS SDK / MapTiler Cloud example

A live Xcode project/app that demonstrates how to use Mapbox Maps SDK for iOS with MapTiler cloud.

### Other helpful links
- [Mapbox Maps SDK for iOS API documentation](https://docs.mapbox.com/ios/api/maps/)
- [First steps with the Mapbox Maps SDK for iOS](https://docs.mapbox.com/help/tutorials/first-steps-ios-sdk/)
- [MapTiler Cloud](https://www.maptiler.com/cloud/)

## Getting started
1. If you don't have CocoaPods installed, run `sudo gem install cocoapods`
1. Run `pod install` to download and integrate dependencies using [CocoaPods](https://cocoapods.org).
1. Open `Landmarks.xcworkspace` in Xcode.
1. Create MapTiler account and [obtain the api key](https://cloud.maptiler.com/account/keys).
1. Set the MapTilerKey property in `Landmarks/info.plist` to the value obtained in the previous step.

![Preview](Landmarks.gif)