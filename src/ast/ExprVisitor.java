package ast;// Generated from G:\Tsmart Projects\ParseBIP\src\Expr.g4 by ANTLR 4.1
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ast.ExprParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ExprVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ast.ExprParser#inclusive_or_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInclusive_or_expression(@NotNull ExprParser.Inclusive_or_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link ast.ExprParser#assignment_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment_expression(@NotNull ExprParser.Assignment_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link ast.ExprParser#multiplicative_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicative_expression(@NotNull ExprParser.Multiplicative_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link ast.ExprParser#relational_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelational_expression(@NotNull ExprParser.Relational_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link ast.ExprParser#exclusive_or_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExclusive_or_expression(@NotNull ExprParser.Exclusive_or_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link ast.ExprParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(@NotNull ExprParser.StatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link ast.ExprParser#logical_and_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogical_and_expression(@NotNull ExprParser.Logical_and_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link ast.ExprParser#additive_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditive_expression(@NotNull ExprParser.Additive_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link ast.ExprParser#if_then_else_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_then_else_expression(@NotNull ExprParser.If_then_else_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link ast.ExprParser#postfix_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfix_expression(@NotNull ExprParser.Postfix_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link ast.ExprParser#do_action}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDo_action(@NotNull ExprParser.Do_actionContext ctx);

	/**
	 * Visit a parse tree produced by {@link ast.ExprParser#equality_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEquality_expression(@NotNull ExprParser.Equality_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link ast.ExprParser#primary_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary_expression(@NotNull ExprParser.Primary_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link ast.ExprParser#and_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnd_expression(@NotNull ExprParser.And_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link ast.ExprParser#unary_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnary_expression(@NotNull ExprParser.Unary_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link ast.ExprParser#logical_or_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogical_or_expression(@NotNull ExprParser.Logical_or_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link ast.ExprParser#subtractive_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubtractive_expression(@NotNull ExprParser.Subtractive_expressionContext ctx);
}