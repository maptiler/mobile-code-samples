#!/bin/bash
set -o errexit
set -o pipefail
set -o nounset

# echo "------ Updating documentation in ios-uikit project ------"
# bin/swift-substitute.sh -s "$(pwd)/../ios-uikit/SimpleMap_UIKit/SimpleMap_UIKit/" -t "$(pwd)/../ios-uikit"
# echo "------ Updating documentation in ios-swiftui project ------"
# bin/swift-substitute.sh -s "$(pwd)/../ios-swiftui/SimpleMap_SwiftUI/SimpleMap_SwiftUI/" -t "$(pwd)/../ios-swiftui"
# echo "------ Updating documentation in android project ------"
# bin/kotlin-substitute.sh -s "$(pwd)/../android/app/src/main/java/com/maptiler/simplemap" -t "$(pwd)/../android"

echo "------ Updating samples folder ------"
rsync -av \
    --exclude='SimpleMap_SwiftUI/Pods' \
    --exclude='SimpleMap_SwiftUI/*.xcworkspace' \
    --exclude='SimpleMap_SwiftUI/Podfile' \
    --exclude='SimpleMap_SwiftUI/Podfile.lock' \
    --exclude='TUTORIAL_TEMPLATE.md' \
    "$(pwd)/../ios-swiftui" "$(pwd)/../../samples/"

rsync -av \
    --exclude='SimpleMap_UIKit/Pods' \
    --exclude='SimpleMap_UIKit/*.xcworkspace' \
    --exclude='SimpleMap_UIKit/Podfile' \
    --exclude='SimpleMap_UIKit/Podfile.lock' \
    --exclude='TUTORIAL_TEMPLATE.md' \
    "$(pwd)/../ios-uikit" "$(pwd)/../../samples/"

rsync -av \
    --exclude='.gradle' \
    --exclude='.idea' \
    --exclude='.build' \
    --exclude='build' \
    --exclude='app/build' \
    --exclude='TUTORIAL_TEMPLATE.md' \
    "$(pwd)/../android" "$(pwd)/../../samples/"