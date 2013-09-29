// Generated from G:\Tsmart Projects\ParseBIP\src\ast\Expr.g4 by ANTLR 4.1
package ast;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ExprParser}.
 */
public interface ExprListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ExprParser#inclusive_or_expression}.
	 * @param ctx the parse tree
	 */
	void enterInclusive_or_expression(@NotNull ExprParser.Inclusive_or_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#inclusive_or_expression}.
	 * @param ctx the parse tree
	 */
	void exitInclusive_or_expression(@NotNull ExprParser.Inclusive_or_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link ExprParser#assignment_expression}.
	 * @param ctx the parse tree
	 */
	void enterAssignment_expression(@NotNull ExprParser.Assignment_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#assignment_expression}.
	 * @param ctx the parse tree
	 */
	void exitAssignment_expression(@NotNull ExprParser.Assignment_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link ExprParser#multiplicative_expression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicative_expression(@NotNull ExprParser.Multiplicative_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#multiplicative_expression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicative_expression(@NotNull ExprParser.Multiplicative_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link ExprParser#relational_expression}.
	 * @param ctx the parse tree
	 */
	void enterRelational_expression(@NotNull ExprParser.Relational_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#relational_expression}.
	 * @param ctx the parse tree
	 */
	void exitRelational_expression(@NotNull ExprParser.Relational_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link ExprParser#exclusive_or_expression}.
	 * @param ctx the parse tree
	 */
	void enterExclusive_or_expression(@NotNull ExprParser.Exclusive_or_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#exclusive_or_expression}.
	 * @param ctx the parse tree
	 */
	void exitExclusive_or_expression(@NotNull ExprParser.Exclusive_or_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link ExprParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(@NotNull ExprParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(@NotNull ExprParser.StatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link ExprParser#logical_and_expression}.
	 * @param ctx the parse tree
	 */
	void enterLogical_and_expression(@NotNull ExprParser.Logical_and_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#logical_and_expression}.
	 * @param ctx the parse tree
	 */
	void exitLogical_and_expression(@NotNull ExprParser.Logical_and_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link ExprParser#additive_expression}.
	 * @param ctx the parse tree
	 */
	void enterAdditive_expression(@NotNull ExprParser.Additive_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#additive_expression}.
	 * @param ctx the parse tree
	 */
	void exitAdditive_expression(@NotNull ExprParser.Additive_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link ExprParser#if_then_else_expression}.
	 * @param ctx the parse tree
	 */
	void enterIf_then_else_expression(@NotNull ExprParser.If_then_else_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#if_then_else_expression}.
	 * @param ctx the parse tree
	 */
	void exitIf_then_else_expression(@NotNull ExprParser.If_then_else_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link ExprParser#postfix_expression}.
	 * @param ctx the parse tree
	 */
	void enterPostfix_expression(@NotNull ExprParser.Postfix_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#postfix_expression}.
	 * @param ctx the parse tree
	 */
	void exitPostfix_expression(@NotNull ExprParser.Postfix_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link ExprParser#do_action}.
	 * @param ctx the parse tree
	 */
	void enterDo_action(@NotNull ExprParser.Do_actionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#do_action}.
	 * @param ctx the parse tree
	 */
	void exitDo_action(@NotNull ExprParser.Do_actionContext ctx);

	/**
	 * Enter a parse tree produced by {@link ExprParser#equality_expression}.
	 * @param ctx the parse tree
	 */
	void enterEquality_expression(@NotNull ExprParser.Equality_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#equality_expression}.
	 * @param ctx the parse tree
	 */
	void exitEquality_expression(@NotNull ExprParser.Equality_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link ExprParser#primary_expression}.
	 * @param ctx the parse tree
	 */
	void enterPrimary_expression(@NotNull ExprParser.Primary_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#primary_expression}.
	 * @param ctx the parse tree
	 */
	void exitPrimary_expression(@NotNull ExprParser.Primary_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link ExprParser#and_expression}.
	 * @param ctx the parse tree
	 */
	void enterAnd_expression(@NotNull ExprParser.And_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#and_expression}.
	 * @param ctx the parse tree
	 */
	void exitAnd_expression(@NotNull ExprParser.And_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link ExprParser#unary_expression}.
	 * @param ctx the parse tree
	 */
	void enterUnary_expression(@NotNull ExprParser.Unary_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#unary_expression}.
	 * @param ctx the parse tree
	 */
	void exitUnary_expression(@NotNull ExprParser.Unary_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link ExprParser#logical_or_expression}.
	 * @param ctx the parse tree
	 */
	void enterLogical_or_expression(@NotNull ExprParser.Logical_or_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#logical_or_expression}.
	 * @param ctx the parse tree
	 */
	void exitLogical_or_expression(@NotNull ExprParser.Logical_or_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link ExprParser#subtractive_expression}.
	 * @param ctx the parse tree
	 */
	void enterSubtractive_expression(@NotNull ExprParser.Subtractive_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#subtractive_expression}.
	 * @param ctx the parse tree
	 */
	void exitSubtractive_expression(@NotNull ExprParser.Subtractive_expressionContext ctx);
}