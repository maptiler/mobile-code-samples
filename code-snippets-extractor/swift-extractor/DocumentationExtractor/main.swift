import Foundation
import Slang
import ArgumentParser


struct SnippetsExtractor: ParsableCommand {

    @Argument(help: "The root directory")
    var rootDirectory: String
    
    @Argument(help: "The token to search for")
    var snippetToken: String

    mutating func run() throws {
        
        let parts = snippetToken.components(separatedBy: "#")
        var fixUpRootDirectory = rootDirectory
        if !fixUpRootDirectory.hasSuffix("/") {
            fixUpRootDirectory.append("/")
        }
        let sourceFile = "\(fixUpRootDirectory)\(parts[0])"
        
        let content = try! String(contentsOfFile: sourceFile, encoding: String.Encoding.utf8)
        let file: File = File(content)
        
        
        let snippetTokenValue = parts[1]
        let commentValue = "snippet(\(snippetTokenValue))"
        let disassembly: Disassembly = try! Disassembly(file)

        let snippetTokenInstance = disassembly.query.syntax
            .select(of: .comment)
            .first(where: {$0.contents.contains(commentValue)})
            .one

        if snippetTokenInstance != nil {
            
            func recurseChildrens(structure: Structure, startIndex: Int) -> String?
            {
                // print("checking \(structure.contents)")
                if structure.range.lowerBound >= startIndex  {
                    return structure.contents
                }
                
                var retVal: String? = nil;
                if !structure.substructures.isEmpty {
                    for sub in structure.substructures {
                        retVal = recurseChildrens(structure: sub, startIndex: startIndex)
                        if (retVal != nil) { return retVal }
                    }
                }

                return nil
            }
            
            let structure = disassembly.query.structure.one!
            let codeSnippet = recurseChildrens(structure: structure, startIndex: snippetTokenInstance!.range.upperBound)
                    
            if (codeSnippet != nil) {
                print(codeSnippet!)
                return
            }
        }

        throw ExtractorError.runtimeError("Could not find token \(snippetTokenValue) in \(sourceFile)")
    }
}

enum ExtractorError: Error {
    case runtimeError(String)
}

SnippetsExtractor.main()
