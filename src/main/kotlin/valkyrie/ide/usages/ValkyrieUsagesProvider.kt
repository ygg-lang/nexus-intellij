package valkyrie.ide.usages


import com.intellij.lang.cacheBuilder.DefaultWordsScanner
import com.intellij.lang.cacheBuilder.WordsScanner
import com.intellij.lang.findUsages.FindUsagesProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.TokenSet

class ValkyrieUsagesProvider : FindUsagesProvider {
    override fun getWordsScanner(): WordsScanner {

        return DefaultWordsScanner(
            nexus.language.antlr.NexusLexer(),
            nexus.language.antlr.NexusLexer.Identifiers,
            nexus.language.antlr.NexusLexer.Identifiers,
            nexus.language.antlr.NexusLexer.Comments,
            TokenSet.EMPTY
        )
    }

    override fun canFindUsagesFor(psiElement: PsiElement): Boolean {
        // return psiElement is PsiNamedElement
        return true
    }

    override fun getHelpId(psiElement: PsiElement): String {
        return "getHelpId"
    }

    override fun getType(element: PsiElement): String {
        return "getType"
    }

    override fun getDescriptiveName(element: PsiElement): String {
        return "getDescriptiveName23"
    }

    override fun getNodeText(element: PsiElement, useFullName: Boolean): String {
        return "getNodeText"
    }
}