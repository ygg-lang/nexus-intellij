package nexus.language.ast

import ai.grazie.utils.isUppercase
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.IElementType
import com.intellij.psi.util.PsiTreeUtil
import org.antlr.intellij.adaptor.psi.IdentifierDefSubtree
import valkyrie.ide.highlight.NexusHighlightColor
import valkyrie.ide.highlight.NexusHighlightElement
import valkyrie.ide.highlight.NodeHighlighter


class NexusNamepathNode(node: ASTNode, type: IElementType, val free: Boolean = false) : IdentifierDefSubtree(node, type),
    NexusHighlightElement {
    val identifiers = findChildrenByClass(NexusIdentifierNode::class.java)
    val parentIdentifier: Array<NexusIdentifierNode> = identifiers.dropLast(1).toTypedArray()
    val namespace: String = parentIdentifier.joinToString(".") { it.text }

    override fun getName(): String {
        return nameIdentifier.name
    }

    override fun getNameIdentifier(): NexusIdentifierNode {
        return identifiers.last()
    }


    companion object {
        fun find(node: PsiElement): NexusNamepathNode? {
            return PsiTreeUtil.getChildOfType(node, NexusNamepathNode::class.java)
        }
    }

    override fun on_highlight(e: NodeHighlighter) {
//        fakeTypeColor(e, nameIdentifier)
    }
}


private fun fakeTypeColor(info: NodeHighlighter, psi: NexusIdentifierNode) {
    val name = psi.name
    if (keywords.contains(name)) {
        info.register(psi, NexusHighlightColor.KEYWORD)
    } else if (functions.contains(name)) {
        info.register(psi, NexusHighlightColor.SYM_FUNCTION_FREE)
    } else if (name.startsWith('_')) {
        info.register(psi, NexusHighlightColor.SYM_ARG)
    } else if (name.isUppercase()) {
        info.register(psi, NexusHighlightColor.SYM_GENERIC)
    } else {
        val first = name.firstOrNull();
        if (first != null && first.isUpperCase()) {
            info.register(psi, NexusHighlightColor.SYM_CLASS)
        }
    }
}

private val functions = setOf(
    "print",
)


private val keywords = setOf(
    "u8", "u16", "u32", "u64", "int",
    "i8", "i16", "i32", "i64",
    "f32", "f64",
    "bool", "char", "string",
    "unit", "callcc", "go",
    "scope", "async", "await", "quote",
    "self", "Self",
    "violate", "value"
)
