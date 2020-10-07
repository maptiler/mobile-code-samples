#!/bin/bash
set -o errexit
set -o pipefail
set -o nounset

echo "------ Updating documentation in ios-uikit project ------"
swift-substitute.sh -s "$(pwd)/../ios-uikit/SimpleMap_UIKit/SimpleMap_UIKit/" -t "$(pwd)/../ios-uikit"
echo "------ Updating documentation in ios-swiftui project ------"
swift-substitute.sh -s "$(pwd)/../ios-swiftui/SimpleMap_SwiftUI/SimpleMap_SwiftUI/" -t "$(pwd)/../ios-swiftui"
echo "------ Updating documentation in android project ------"
kotlin-substitute.sh -s "$(pwd)/../android/app/src/main/java/com/maptiler/simplemap" -t "$(pwd)/../android"