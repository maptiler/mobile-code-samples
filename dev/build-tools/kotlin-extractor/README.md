# Code Snippets extractor for Kotlin

The extractor was tested on MacOS only. pre-built binary is in /bin folder 

## Setup

1. Install Java 8 SDK

1. Add folder with build tools to the path and class path

    ```bash
    export KOTLIN_EXTRACTOR_DIR="/path/to/project/build-tools/swift-extractor/bin"
    export PATH="$KOTLIN_EXTRACTOR_DIR:$PATH"
    export KOTLIN_EXTRACTOR_JAVA="/path/to/java8/sdk/bin/"
    ```

    or add to your shell configuration file

    ```bash
    nano ~/.zshrc
    ```

    and add 
    
    ```
        export KOTLIN_EXTRACTOR_DIR="/path/to/project/build-tools/swift-extractor/bin"
        export PATH="$KOTLIN_EXTRACTOR_DIR:$PATH"
        export KOTLIN_EXTRACTOR_JAVA="/path/to/java8/sdk/bin/"
    ```
    
    and load new path into current session

    ```bash
    source ~/.zshrc
    ```

## Usage

```bash
cd /path/to/project-root/
swift-substitute.sh -s "$(pwd)/android/app/src/main/java/com/maptiler/simplemap" -t "$(pwd)/android"
```