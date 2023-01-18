package valkyrie.ide.formatter

import com.intellij.formatting.service.AsyncDocumentFormattingService
import com.intellij.formatting.service.AsyncFormattingRequest
import com.intellij.formatting.service.FormattingService
import com.intellij.psi.PsiFile
import valkyrie.language.file.NexusFileNode

class ValkyrieNativeFormat : AsyncDocumentFormattingService() {
    override fun getFeatures(): MutableSet<FormattingService.Feature> {
        TODO("Not yet implemented")
    }

    override fun canFormat(file: PsiFile): Boolean {
        if (file is NexusFileNode) {
            // TODO: Check if config is true
            return false
        }
        return false
    }

    override fun createFormattingTask(formattingRequest: AsyncFormattingRequest): FormattingTask? {
        TODO("Not yet implemented")
    }

    override fun getNotificationGroupId(): String {
        TODO("Not yet implemented")
    }

    override fun getName(): String {
        TODO("Not yet implemented")
    }
}