package valkyrie.ide.highlight


//import valkyrie.language.psi_node.ValkyrieIdentifierNode
import com.intellij.codeInsight.daemon.impl.HighlightVisitor
import com.intellij.codeInsight.daemon.impl.analysis.HighlightInfoHolder
import com.intellij.openapi.progress.ProgressManager
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import valkyrie.language.antlr.traversal
import valkyrie.language.file.NexusFileNode
import valkyrie.language.psi.ValkyrieHighlightElement

class ValkyrieHighlighterVisitor : HighlightVisitor {
    private var _info: HighlightInfoHolder? = null
    override fun suitableForFile(file: PsiFile): Boolean {
        return file is NexusFileNode
    }

    override fun visit(element: PsiElement) {
        val writer = NodeHighlighter(_info);
        element.traversal {
            if (it is ValkyrieHighlightElement) {
                it.on_highlight(writer)
            }
            ProgressManager.checkCanceled()
            true
        }
    }

    override fun analyze(file: PsiFile, updateWholeFile: Boolean, holder: HighlightInfoHolder, action: Runnable): Boolean {
        _info = holder
        action.run()
        return true
    }

    override fun clone(): HighlightVisitor = ValkyrieHighlighterVisitor()
}

