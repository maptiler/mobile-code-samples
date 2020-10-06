import Foundation
import Slang
import ArgumentParser


struct SnippetsExtractor: ParsableCommand {

    @Argument(help: "The source code file")
    var sourcePath: String

    @Argument(help: "The token to search for")
    var snippetToken: String

    mutating func run() throws {
        let content = try! String(contentsOfFile: sourcePath, encoding: String.Encoding.utf8)
        let file: File = File(content)
        let commentValue = "snippet(\(snippetToken))"
        let disassembly: Disassembly = try! Disassembly(file)

        let snippetTokenInstance = disassembly.query.syntax
            .select(of: .comment)
            .first(where: {$0.contents.contains(commentValue)})
            .one

        if snippetTokenInstance != nil {
            func recurseChildrens(structures: [Structure], startIndex: Int) -> String?
            {
                for structure in structures {
                    if structure.range.lowerBound >= startIndex {
                        return structure.contents
                    }
                    if !structure.substructures.isEmpty {
                        return recurseChildrens(structures: structure.substructures, startIndex: startIndex)
                    }
                }
                return nil
            }
            let structure = disassembly.query.structure.one!
            let codeSnippet = recurseChildrens(structures: structure.substructures, startIndex: snippetTokenInstance!.range.upperBound)
                    
            print(codeSnippet!)
            return
        }

        throw ExtractorError.runtimeError("Could not find token \(snippetToken) in \(sourcePath)")
    }
}

enum ExtractorError: Error {
    case runtimeError(String)
}

SnippetsExtractor.main()
