#!/bin/bash
set -o errexit
set -o pipefail
set -o nounset

print_usage () {
  printf "Substitute tokens.\n"
  printf "The script attempts tp read TUTORIAL_TEMPLATE.md in Root template folder and writes out TUTORIAL.md with substituted tokens.\n"
  printf "\nUsage: \n"
  printf "%s\n" "--------------------------"
  printf "%s options \n" $0
  printf "%s\t%s\n" "-s" "Root source code folder"   
  printf "%s\t%s\n" "-t" "Root template folder"    
  printf "\n"
}

while getopts "s:t:" arg; do
  case $arg in
    s) SourceCodeDir=$OPTARG;;
    t) WorkDir=$OPTARG;;
  esac
done

if [[ -z "$SourceCodeDir"  ]] || [[ -z "$WorkDir"  ]]; then
    print_usage
    exit 0 
fi

#WorkDir=/Volumes/Src/src/MapTiler/mobile-code-samples/ios-uikit/
TargetFilePath="$WorkDir/TUTORIAL.md"
TemplateFilePath="$WorkDir/TUTORIAL_TEMPLATE.md"
Tokens=$(sed -n 's/^.*snippet(\(.*\))$/\1/p' $TemplateFilePath)
#SourceCodeDir=/Volumes/Src/src/MapTiler/mobile-code-samples/ios-uikit/SimpleMap_UIKit/SimpleMap_UIKit

cp $TemplateFilePath $TargetFilePath
while IFS= read -r Token; do
    echo "$Token"
    Snippet=$( /Library/Java/JavaVirtualMachines/jdk1.8.0_261.jdk/Contents/Home/bin/java -jar /Volumes/Src/src/MapTiler/mobile-code-samples/code-snippets-extractor/kotlin-extractor/build/libs/kotlin-extractor-1.0-SNAPSHOT.jar $SourceCodeDir $Token)
    Template=$(cat $TargetFilePath)
    echo "${Template//snippet($Token)/$Snippet}" > $TargetFilePath
done <<< "$Tokens"