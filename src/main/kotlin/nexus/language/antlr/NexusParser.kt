package nexus.language.antlr

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.lang.PsiBuilder
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.CompositeElement
import com.intellij.psi.tree.IElementType
import com.intellij.psi.util.PsiTreeUtil
import nexus.language.antlr.NexusAntlrParser.*
import nexus.language.ast.NexusIdentifierNode
import nexus.language.ast.NexusModifiedNode
import nexus.language.ast.NexusNamepathNode
import nexus.language.ast.classes.NexusClassStatement
import nexus.language.psi.types.ValkyrieModifiedType
import org.antlr.intellij.adaptor.lexer.RuleIElementType
import org.antlr.intellij.adaptor.parser.ANTLRParseTreeToPSIConverter
import org.antlr.intellij.adaptor.parser.ANTLRParserAdaptor
import org.antlr.v4.runtime.Parser
import org.antlr.v4.runtime.tree.ParseTree


class NexusParser(parser: NexusAntlrParser) : ANTLRParserAdaptor(nexus.language.NexusLanguage, parser) {
    override fun parse(parser: Parser, root: IElementType): ParseTree {
        return (parser as NexusAntlrParser).program()
    }


    override fun parse(root: IElementType, builder: PsiBuilder): ASTNode {
        return super.parse(root, builder)
    }

    override fun createListener(parser: Parser?, root: IElementType?, builder: PsiBuilder?): ANTLRParseTreeToPSIConverter {
        return super.createListener(parser, root, builder)
    }

    companion object {
        fun extractCompositeNode(node: CompositeElement): PsiElement {
            val type: RuleIElementType = node.elementType as RuleIElementType;
            return when (type.ruleIndex) {
//                RULE_import_statement -> ValkyrieImportStatement(node)
//                // annotations
                RULE_modifiers -> NexusModifiedNode(node, ValkyrieModifiedType.Pure)
                RULE_modified_identifier -> NexusModifiedNode(node, ValkyrieModifiedType.ModifiedIdentifier)
                RULE_modified_namepath -> NexusModifiedNode(node, ValkyrieModifiedType.ModifiedNamepath)
//                RULE_template_block -> ValkyrieBlockNode(node, ValkyrieBlockType.Brace)
//                RULE_where_block -> ValkyrieBlockNode(node, ValkyrieBlockType.Brace)
//                RULE_annotation -> ValkyrieAnnotation(node)
//                RULE_annotation_call_item -> ValkyrieAnnotationItem(node)
//                // class
                RULE_define_class -> NexusClassStatement(node)
//                RULE_define_generic -> ValkyrieGenericStatement(node)
//                RULE_class_block -> ValkyrieBlockNode(node, ValkyrieBlockType.Brace)
//                RULE_class_field -> ValkyrieClassFieldNode(node)
//                RULE_class_method -> ValkyrieClassMethodNode(node)
//                RULE_class_dsl -> ValkyrieClassCustomNode(node)
//                // function
//                RULE_define_function -> ValkyrieFunctionStatement(node)
//                RULE_function_parameters -> ValkyrieBlockNode(node, ValkyrieBlockType.Parenthesis)
//                RULE_parameter_item -> ValkyrieFunctionParameter(node)
//                RULE_function_block -> ValkyrieBlockNode(node, ValkyrieBlockType.Brace)
//                // variable
//                RULE_let_binding -> ValkyrieLetStatement(node)
//                RULE_let_pattern -> ValkyrieLetPattern(node)
//                RULE_let_pattern_item -> ValkyrieLetPatternItem(node)
//                // control
////                RULE_for_statement -> ValkyrieForStatement(node)
////                RULE_while_statement -> ValkyrieWhileStatement(node)
//                // pattern match
//                RULE_match_statement -> {
////                    ValkyrieParser.getChildOfType(node.psi)
//                    ValkyrieMatchStatement(node)
//                    // ValkyrieCatchBlockNode(node)
//                }
////                RULE_match_call -> {
////                    ValkyrieMatchCall(node)
////                }
//
//                RULE_lambda_name -> ValkyrieLambdaSlot(node)
//
//
//                RULE_match_block -> ValkyrieBlockNode(node, ValkyrieBlockType.Brace)
//                RULE_match_case_block -> ValkyrieBlockNode(node, ValkyrieBlockType.Indent)
//                // expression
//                RULE_macro_call -> ValkyrieCallMacro(node)
//                RULE_generic_call -> ValkyrieGenericCall(node, true)
//                RULE_generic_call_in_type -> ValkyrieGenericCall(node, false)
//                RULE_tuple_call_item -> ValkyrieCallArgument(node)
//                RULE_tuple_call_body -> ValkyrieBlockNode(node, ValkyrieBlockType.Parenthesis)
//                // operators
//                RULE_infix_map -> ValkyrieOperatorNode(node, ValkyrieOperatorKind.Infix)
//                RULE_expression -> extractExpression(node)
//                RULE_function_call -> ValkyrieCallFunction(node)
//                // atomic
                RULE_namepath_free -> NexusNamepathNode(node, type, true)
                RULE_namepath -> NexusNamepathNode(node, type)
                RULE_identifier -> NexusIdentifierNode(node)
//                RULE_string_literal -> ValkyrieStringNode(node)
//                RULE_number -> ValkyrieNumberNode(node)
                // comment


                else -> ASTWrapperPsiElement(node)
            }
        }

        fun getChildOfType(psi: PsiElement?, parserRule: Int): PsiElement? {
            if (psi != null) {
                for (child in psi.children) {
                    val type = child.node.elementType as RuleIElementType;
                    if (type.ruleIndex == parserRule) {
                        return child;
                    }
                }
            }
            return null;
        }

        inline fun <reified T> getChildrenOfType(psi: PsiElement?): List<T> where T : PsiElement {
            if (psi != null) {
                return PsiTreeUtil.getChildrenOfTypeAsList(psi, T::class.java)
            }
            return emptyList()
        }


        fun getChildrenOfType(psi: PsiElement?, parserRule: Int): List<PsiElement> {
            val output = mutableListOf<PsiElement>();
            if (psi != null) {
                for (child in psi.children) {
                    val type = child.node.elementType as RuleIElementType;
                    if (type.ruleIndex == parserRule) {
                        output.add(child)
                    }
                }
            }
            return output;
        }
    }
}

