import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.psiUtil.endOffset
import org.jetbrains.kotlin.psi.psiUtil.startOffset

fun convertNode(node: KtDeclaration) : Node {
    return when (node) {
        is KtNamedFunction -> convertFunction(node)
        is KtParameter -> convertParameter(node)
        is KtProperty -> convertProperty(node)
        is KtClass -> convertClass(node)
        else -> error("Unable to convert node")
    }
}

fun convertFunction(node: KtNamedFunction) : FunctionNode {
    return FunctionNode(
        name = node.name!!,
        parameterNodes = node.valueParameters.map { convertParameter(it) },
        accessLevel = AccessLevel.Public,
        returnType = convertReturnType(node),
        startIndex = node.startOffset,
        endIndex = node.endOffset,
        content = node.text
    )
}

fun convertReturnType(node: KtNamedFunction) : String {
    val elm = node.typeReference?.typeElement
    if (elm is KtUserType) {
        return elm?.referencedName ?: "Unit"
    }
    if (elm is KtNullableType) {
        val innerType = elm?.innerType?.name ?: "Unit"
        return "${innerType}?"
    }
    return "Unit"
}

fun convertParameter(node: KtParameter) : ParameterNode {
    return ParameterNode(
        name = node.name!!,
        dataType = node.typeReference!!.text,
        startIndex = node.startOffset,
        endIndex = node.endOffset,
        content = node.text
    )
}

fun convertProperty(node: KtProperty) : PropertyNode {
    return PropertyNode(
        name = node.name!!,
        readOnly = node.typeReference?.isWritable ?: false,
        startIndex = node.startOffset,
        endIndex = node.endOffset,
        content = node.text
    )
}

fun convertClass(node: KtClass) : ClassNode {
    return ClassNode(
        name = node.name!!,
        properties = node.getProperties().map { convertProperty(it) },
        methods = node.declarations.filterIsInstance<KtNamedFunction>().map { convertFunction(it) },
        startIndex = node.startOffset,
        endIndex = node.endOffset,
        content = node.text
    )
}
