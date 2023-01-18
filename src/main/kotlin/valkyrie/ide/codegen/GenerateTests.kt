package valkyrie.ide.codegen

import com.intellij.navigation.ItemPresentation
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import com.intellij.testIntegration.TestCreator
import valkyrie.language.file.NexusFileNode
import valkyrie.language.file.NexusIconProvider
import javax.swing.Icon

class GenerateTests : TestCreator, ItemPresentation {
    override fun isAvailable(project: Project?, editor: Editor?, file: PsiFile?): Boolean {
        if (file !is NexusFileNode || editor == null) {
            return false
        }
        return true
    }

    override fun createTest(project: Project?, editor: Editor?, file: PsiFile?) {
        if (file !is NexusFileNode || editor == null) {
            return
        }
    }


    override fun getIcon(unused: Boolean): Icon {
        return NexusIconProvider.Instance.IMPORT
    }

    override fun getPresentableText(): String? {
        return "Coverage Methods..."
    }

    override fun getLocationString(): String? {
        return "CreateTests.getLocationString"
    }
}