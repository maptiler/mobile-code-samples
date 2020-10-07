enum class AccessLevel {
    Public,
    Private,
    Protected,
}

data class AbstractSyntaxTree(val nodes: List<Node>)

interface Node {
    val startIndex: Int;
    val endIndex: Int;
    val content: String
}

data class ClassNode(val name: String, val properties: List<Node>, val methods: List<Node>,
                     override val startIndex: Int, override val endIndex: Int, override val content: String) :
    Node

data class PropertyNode(val name: String, val readOnly: Boolean = true,
                        override val startIndex: Int, override val endIndex: Int, override val content: String) : Node

data class FunctionNode(
    val name: String,
    val accessLevel: AccessLevel = AccessLevel.Public,
    val returnType: String,
    val parameterNodes: List<ParameterNode>,
    override val startIndex: Int,
    override val endIndex: Int,
    override val content: String
) : Node

data class ParameterNode(val name: String, val dataType: String,
                         override val startIndex: Int, override val endIndex: Int, override val content: String) : Node
