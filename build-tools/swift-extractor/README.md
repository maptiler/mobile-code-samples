# Code Snippets extractor for Swift

The extractor was tested on MacOS only. pre-built binary is in /bin folder 

## Setup

Add folder with build tools to the path

```bash
export PATH="/path/to/project/build-tools/swift-extractor/bin:$PATH"
```

or add to your shell configuration file

```bash
nano ~/.zshrc
```

Add `export PATH="/path/to/project/build-tools/swift-extractor/bin:$PATH"` line and load new path into current session

```bash
source ~/.zshrc
```

## Usage

```bash
cd /path/to/project-root/
swift-substitute.sh -s "$(pwd)/ios-uikit/SimpleMap_UIKit/SimpleMap_UIKit/" -t "$(pwd)/ios-uikit"
```

## Debug tools

The source code structure and syntax tree can be tested by sourcekitten tool

```bash
sourcekitten structure  --file /Volumes/Src/src/MapTiler/mobile-code-samples/ios-uikit/SimpleMap_UIKit/SimpleMap_UIKit/ViewController.swift
```