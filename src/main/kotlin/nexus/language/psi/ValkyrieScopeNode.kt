@file:Suppress("UnstableApiUsage")

package nexus.language.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.model.psi.PsiSymbolDeclaration
import com.intellij.navigation.ItemPresentation
import com.intellij.psi.impl.source.tree.CompositeElement
import com.intellij.psi.impl.source.tree.SharedImplUtil
import com.intellij.psi.util.parents
import nexus.language.file.NexusFileNode
import javax.swing.Icon

/**
 * This interface acts as a tag so that we can identify nodes in
 * the PSI tree that represent symbol scopes. For example, in
 * a simple language like C with globals, functions, arguments,
 * and local blocks you could create a PSI tree with heterogeneous node
 * types such as FileSubtree, FunctionSubtree, BlockSubtree, etc...
 * Each of those should implement this interface.  If you use this
 * mechanism, then the default getContext() mechanism will work; given
 * a node, it looks upward in the PSI tree for a node that
 * implements ScopeNode.
 */
abstract class ValkyrieScopeNode(node: CompositeElement) : ASTWrapperPsiElement(node) {
    override fun getContainingFile(): NexusFileNode {
        return SharedImplUtil.getContainingFile(node) as NexusFileNode
    }

    override fun getContext(): ValkyrieScopeNode? {
        for (parent in this.parents(false)) {
            if (parent is ValkyrieScopeNode) {
                return parent
            }
        }
        return null;
    }

    override fun getLanguage(): nexus.language.NexusLanguage {
        return nexus.language.NexusLanguage
    }

    override fun getOwnDeclarations(): MutableCollection<out PsiSymbolDeclaration> {
        return mutableSetOf()
    }

    public abstract override fun getBaseIcon(): Icon;

    abstract override fun getPresentation(): ItemPresentation;
}
