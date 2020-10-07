import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer
import org.jetbrains.kotlin.com.intellij.testFramework.LightVirtualFile
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.idea.KotlinFileType
import org.jetbrains.kotlin.com.intellij.openapi.project.Project
import org.jetbrains.kotlin.com.intellij.psi.*
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.psiUtil.*
import java.io.File
import java.io.InputStream
import kotlin.text.Regex.Companion.escape

fun findTokenOffset(source: String, token: String): Int? {
    val comment = parsePsiFile(source)
        .collectDescendantsOfType<PsiComment>()
        .firstOrNull { it.text.contains(token) }
    return comment?.startOffset
}

fun parse(source: String): AbstractSyntaxTree {
    val ast = parsePsiFile(source).also { file ->
        file.collectDescendantsOfType<PsiErrorElement>()
    }
    val nodes = ast.declarations.map { convertNode(it) }
    return AbstractSyntaxTree(nodes)
}

fun findCodeSnippet(code: String, token: String): String? {
    var tokenStartIndex = findTokenOffset(code, token)
    if (tokenStartIndex != null) {
        val ast = parse(code)
        val sortedNodes = ast.nodes.sortedBy { it.startIndex }

        // if the token is within class (or within top-level block)
        val parentNode = sortedNodes.firstOrNull {
            it.startIndex < tokenStartIndex && it.endIndex > tokenStartIndex
        }
        if (parentNode != null) {
            if (parentNode is ClassNode) {
                // search among functions and properties
                val children = parentNode.methods.union(parentNode.properties).sortedBy { it.startIndex }
                val targetChild = children.firstOrNull { it.startIndex >= tokenStartIndex }
                return removeTokenAndClean(targetChild?.content, token)
            } else {
                return removeTokenAndClean(parentNode?.content, token)
            }
        } else {
            val nextNode = sortedNodes.firstOrNull {
                it.startIndex > tokenStartIndex
            }
            return removeTokenAndClean(nextNode?.content, token)
        }
    }

    return null
}

private fun removeTokenAndClean(nodeContent: String?, token: String): String? {
    if (nodeContent != null) {
        val escapedToken = escape(token)
        val pattern = "^\\s*//\\s*${escapedToken}\\s*$"
        var withoutToken = nodeContent.replace(pattern.toRegex(RegexOption.MULTILINE), "")
        if (withoutToken.startsWith("\n"))
            withoutToken = withoutToken.replaceFirst("\n","");
        return withoutToken;
    }
    return null
}

private fun project(): Project {
    return KotlinCoreEnvironment.createForProduction(
        Disposer.newDisposable(),
        CompilerConfiguration(),
        EnvironmentConfigFiles.JVM_CONFIG_FILES
    ).project
}

private fun parsePsiFile(code: String): KtFile {
    return PsiManager.getInstance(project())
        .findFile(LightVirtualFile("temp.kt", KotlinFileType.INSTANCE, code)) as KtFile
}

fun main(args: Array<String>) {
    val rootDirectory = args[0]
    val snippetToken = args[1]

    val parts = snippetToken.split("#")
    var fixUpRootDirectory = rootDirectory
    if (!fixUpRootDirectory.endsWith("/")) {
        fixUpRootDirectory += "/"
    }
    val sourceFilePath = "${fixUpRootDirectory}${parts[0]}"
    val inputStream: InputStream = File(sourceFilePath).inputStream()
    val sourceCode = inputStream.bufferedReader().use { it.readText() }

    val snippetTokenValue = parts[1]
    val commentValue = "snippet(${snippetTokenValue})"

    val result = findCodeSnippet(sourceCode, commentValue)
    if (result != null) {
        print(result)
    } else {
        throw Error("Could not find token $snippetTokenValue in $sourceFilePath")
    }
}
