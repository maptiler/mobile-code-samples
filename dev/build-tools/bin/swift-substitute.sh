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
  printf "%s\t%s\n" "-o" "The output folder"    
  printf "\n"
}

while getopts "s:t:o:" arg; do
  case $arg in
    s) SourceCodeDir=$OPTARG;;
    t) TemplateDir=$OPTARG;;
    o) OutputDir=$OPTARG;;        
  esac
done

if [[ -z "$SourceCodeDir"  ]] || [[ -z "$TemplateDir"  ]] || [[ -z "$OutputDir"  ]] ; then
    print_usage
    exit 0 
fi

mkdir -p $OutputDir

function renderTemplate() {
  Tokens=$(sed -n 's/^.*snippet(\(.*\))$/\1/p' $TemplateFilePath)
    
  local FileName="${TemplateFilePath##*/}"
  local TargetFilePath="$OutputDir/$FileName"
  
  echo "Target path:  $TargetFilePath"

  cp $TemplateFilePath $TargetFilePath

  if [[ -z "$Tokens" ]] ; then
    echo "No tokens for replacement."
  else
    while IFS= read -r Token; do
        echo "$Token"
        Snippet=$(bin/code-snippets-extractor $SourceCodeDir $Token)
        
        if [[ -z "$Snippet" ]] ; then
          echo "Could not find snippet for token $Token."
          exit 1
        fi

        echo "$Snippet"

        Template=$(cat $TargetFilePath)
        echo "${Template//snippet($Token)/$Snippet}" > $TargetFilePath
    done <<< "$Tokens"  
  fi
}

echo "Processing templates in $TemplateDir"

for TemplateFilePath in $TemplateDir/*.md; do
    [ -f "$TemplateFilePath" ] || break
    echo "Processing file: $TemplateFilePath"
    renderTemplate 
done


