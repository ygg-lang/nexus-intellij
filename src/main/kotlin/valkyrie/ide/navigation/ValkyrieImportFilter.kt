package valkyrie.ide.navigation

import com.intellij.usages.Usage
import com.intellij.usages.UsageTarget
import com.intellij.usages.rules.ImportFilteringRule
import com.intellij.usages.rules.PsiElementUsage
import nexus.language.antlr.ancestors
import nexus.language.ast.NexusIdentifierNode

//import nexus.language.psi_node.ValkyrieIdentifierNode
//import nexus.language.psi_node.ValkyrieImportStatementNode

/**
 * 跳转结果里去掉 `using` 语句内的部分
 */
class ValkyrieImportFilter : ImportFilteringRule() {
    override fun isVisible(usage: Usage, targets: Array<out UsageTarget>): Boolean {
        for (target in targets) {
            println("isVisible3(${target})")
        }

        return super.isVisible(usage, targets)
    }

    override fun isVisible(usage: Usage): Boolean {
        if (usage is PsiElementUsage) {
            when (val element = usage.element) {
                is NexusIdentifierNode -> {
                    return !inImport(element)
                }

                else -> println("isVisible4(${element})")
            }
        }
        return true
    }

    private fun inImport(id: NexusIdentifierNode): Boolean {
        for (node in id.ancestors) {
//            if (node is ValkyrieImportStatementNode) {
//                return true
//            }
        }
        return false
    }
}