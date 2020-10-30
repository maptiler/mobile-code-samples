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

TargetFilePath="$WorkDir/TUTORIAL.md"
TemplateFilePath="$WorkDir/TUTORIAL_TEMPLATE.md"
Tokens=$(sed -n 's/^.*snippet(\(.*\))$/\1/p' $TemplateFilePath)

cp $TemplateFilePath $TargetFilePath
while IFS= read -r Token; do
    echo "$Token"
    Snippet=$( $KOTLIN_EXTRACTOR_JAVA/java -jar $KOTLIN_EXTRACTOR_DIR/kotlin-extractor-1.0-SNAPSHOT.jar $SourceCodeDir $Token)
    Template=$(cat $TargetFilePath)
    echo "${Template//snippet($Token)/$Snippet}" > $TargetFilePath
done <<< "$Tokens"