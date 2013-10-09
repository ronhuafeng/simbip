package ast;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * Created with IntelliJ IDEA.
 * User: wind
 * Date: 13-9-28
 * To change this template use File | Settings | File Templates.
 */
public class ExprWalker extends ExprBaseListener {


	@Override
	public void enterInclusive_or_expression(@NotNull ExprParser.Inclusive_or_expressionContext ctx) {
		super.enterInclusive_or_expression(ctx);    //To change body of overridden methods use File | Settings | File Templates.
	}

	@Override
	public void exitInclusive_or_expression(@NotNull ExprParser.Inclusive_or_expressionContext ctx) {
		super.exitInclusive_or_expression(ctx);    //To change body of overridden methods use File | Settings | File Templates.
	}

	/**
	 * {@inheritDoc}
	 * <p/>
	 * The default implementation does nothing.
	 */
	@Override
	public void enterObjectExpression(@NotNull ExprParser.ObjectExpressionContext ctx) {
		super.enterObjectExpression(ctx);    //To change body of overridden methods use File | Settings | File Templates.
	}

	/**
	 * {@inheritDoc}
	 * <p/>
	 * The default implementation does nothing.
	 */
	@Override
	public void exitObjectExpression(@NotNull ExprParser.ObjectExpressionContext ctx) {
		super.exitObjectExpression(ctx);    //To change body of overridden methods use File | Settings | File Templates.
	}


	@Override
	public void enterAssignment_expression(@NotNull ExprParser.Assignment_expressionContext ctx) {
		super.enterAssignment_expression(ctx);    //To change body of overridden methods use File | Settings | File Templates.
	}

	@Override
	public void exitAssignment_expression(@NotNull ExprParser.Assignment_expressionContext ctx) {
		super.exitAssignment_expression(ctx);    //To change body of overridden methods use File | Settings | File Templates.
	}

	@Override
	public void enterMultiplicative_expression(@NotNull ExprParser.Multiplicative_expressionContext ctx) {
		super.enterMultiplicative_expression(ctx);    //To change body of overridden methods use File | Settings | File Templates.
	}

	@Override
	public void exitMultiplicative_expression(@NotNull ExprParser.Multiplicative_expressionContext ctx) {
		super.exitMultiplicative_expression(ctx);    //To change body of overridden methods use File | Settings | File Templates.
	}

	@Override
	public void enterRelational_expression(@NotNull ExprParser.Relational_expressionContext ctx) {
		super.enterRelational_expression(ctx);    //To change body of overridden methods use File | Settings | File Templates.
	}

	@Override
	public void exitRelational_expression(@NotNull ExprParser.Relational_expressionContext ctx) {
		super.exitRelational_expression(ctx);    //To change body of overridden methods use File | Settings | File Templates.
	}

	/**
	 * {@inheritDoc}
	 * <p/>
	 * The default implementation does nothing.
	 */
	@Override
	public void enterFunctionCall(@NotNull ExprParser.FunctionCallContext ctx) {
		super.enterFunctionCall(ctx);    //To change body of overridden methods use File | Settings | File Templates.
	}

	/**
	 * {@inheritDoc}
	 * <p/>
	 * The default implementation does nothing.
	 */
	@Override
	public void exitFunctionCall(@NotNull ExprParser.FunctionCallContext ctx) {
		super.exitFunctionCall(ctx);    //To change body of overridden methods use File | Settings | File Templates.
	}

	@Override
	public void enterExclusive_or_expression(@NotNull ExprParser.Exclusive_or_expressionContext ctx) {
		super.enterExclusive_or_expression(ctx);    //To change body of overridden methods use File | Settings | File Templates.
	}

	@Override
	public void exitExclusive_or_expression(@NotNull ExprParser.Exclusive_or_expressionContext ctx) {
		super.exitExclusive_or_expression(ctx);    //To change body of overridden methods use File | Settings | File Templates.
	}

	/**
	 * {@inheritDoc}
	 * <p/>
	 * The default implementation does nothing.
	 */
	@Override
	public void enterStatement(@NotNull ExprParser.StatementContext ctx) {
		super.enterStatement(ctx);    //To change body of overridden methods use File | Settings | File Templates.
	}

	/**
	 * {@inheritDoc}
	 * <p/>
	 * The default implementation does nothing.
	 */
	@Override
	public void exitStatement(@NotNull ExprParser.StatementContext ctx) {
		super.exitStatement(ctx);    //To change body of overridden methods use File | Settings | File Templates.
	}


	@Override
	public void enterLogical_and_expression(@NotNull ExprParser.Logical_and_expressionContext ctx) {
		super.enterLogical_and_expression(ctx);    //To change body of overridden methods use File | Settings | File Templates.
	}

	@Override
	public void exitLogical_and_expression(@NotNull ExprParser.Logical_and_expressionContext ctx) {
		super.exitLogical_and_expression(ctx);    //To change body of overridden methods use File | Settings | File Templates.
	}

	@Override
	public void enterAdditive_expression(@NotNull ExprParser.Additive_expressionContext ctx) {
		super.enterAdditive_expression(ctx);    //To change body of overridden methods use File | Settings | File Templates.
	}

	@Override
	public void exitAdditive_expression(@NotNull ExprParser.Additive_expressionContext ctx) {
		super.exitAdditive_expression(ctx);    //To change body of overridden methods use File | Settings | File Templates.
	}

	@Override
	public void enterIf_then_else_expression(@NotNull ExprParser.If_then_else_expressionContext ctx) {
		super.enterIf_then_else_expression(ctx);    //To change body of overridden methods use File | Settings | File Templates.
		System.out.println("In IF ... " + " child-count: " + ctx.getChildCount());
		System.out.println(ctx.getText());
		System.out.println(ctx.logical_or_expression().getText());

		System.out.println("Then statements:");
		for (ExprParser.StatementContext e: ctx.then_stmts)
		{
			System.out.println(e.getText());
		}


		System.out.println("Else statements:");
		for (ExprParser.StatementContext e: ctx.else_stmts)
		{
			System.out.println(e.getText());
		}


		System.out.println();
	}

	@Override
	public void exitIf_then_else_expression(@NotNull ExprParser.If_then_else_expressionContext ctx) {
		super.exitIf_then_else_expression(ctx);    //To change body of overridden methods use File | Settings | File Templates.
	}


	@Override
	public void enterDo_action(@NotNull ExprParser.Do_actionContext ctx) {
		System.out.println("Statements count: " + ctx.statement().size());

		super.enterDo_action(ctx);    //To change body of overridden methods use File | Settings | File Templates.
	}

	@Override
	public void exitDo_action(@NotNull ExprParser.Do_actionContext ctx) {
		super.exitDo_action(ctx);    //To change body of overridden methods use File | Settings | File Templates.
	}

	@Override
	public void enterEquality_expression(@NotNull ExprParser.Equality_expressionContext ctx) {
		super.enterEquality_expression(ctx);    //To change body of overridden methods use File | Settings | File Templates.
	}

	@Override
	public void exitEquality_expression(@NotNull ExprParser.Equality_expressionContext ctx) {
		super.exitEquality_expression(ctx);    //To change body of overridden methods use File | Settings | File Templates.
	}

	/**
	 * {@inheritDoc}
	 * <p/>
	 * The default implementation does nothing.
	 */
	@Override
	public void enterPrimary_expression(@NotNull ExprParser.Primary_expressionContext ctx) {
		super.enterPrimary_expression(ctx);    //To change body of overridden methods use File | Settings | File Templates.
	}

	/**
	 * {@inheritDoc}
	 * <p/>
	 * The default implementation does nothing.
	 */
	@Override
	public void exitPrimary_expression(@NotNull ExprParser.Primary_expressionContext ctx) {
		super.exitPrimary_expression(ctx);    //To change body of overridden methods use File | Settings | File Templates.
	}


	@Override
	public void enterAnd_expression(@NotNull ExprParser.And_expressionContext ctx) {
		super.enterAnd_expression(ctx);    //To change body of overridden methods use File | Settings | File Templates.
	}

	@Override
	public void exitAnd_expression(@NotNull ExprParser.And_expressionContext ctx) {
		super.exitAnd_expression(ctx);    //To change body of overridden methods use File | Settings | File Templates.
	}

	@Override
	public void enterUnary_expression(@NotNull ExprParser.Unary_expressionContext ctx) {
		super.enterUnary_expression(ctx);    //To change body of overridden methods use File | Settings | File Templates.
	}

	@Override
	public void exitUnary_expression(@NotNull ExprParser.Unary_expressionContext ctx) {
		super.exitUnary_expression(ctx);    //To change body of overridden methods use File | Settings | File Templates.
	}

	@Override
	public void enterLogical_or_expression(@NotNull ExprParser.Logical_or_expressionContext ctx) {
		super.enterLogical_or_expression(ctx);    //To change body of overridden methods use File | Settings | File Templates.
	}

	@Override
	public void exitLogical_or_expression(@NotNull ExprParser.Logical_or_expressionContext ctx) {
		super.exitLogical_or_expression(ctx);    //To change body of overridden methods use File | Settings | File Templates.
	}

	@Override
	public void enterSubtractive_expression(@NotNull ExprParser.Subtractive_expressionContext ctx) {
		super.enterSubtractive_expression(ctx);    //To change body of overridden methods use File | Settings | File Templates.
	}

	@Override
	public void exitSubtractive_expression(@NotNull ExprParser.Subtractive_expressionContext ctx) {
		super.exitSubtractive_expression(ctx);    //To change body of overridden methods use File | Settings | File Templates.
	}

	/**
	 * {@inheritDoc}
	 * <p/>
	 * The default implementation does nothing.
	 */
	@Override
	public void enterArgument_expression_list(@NotNull ExprParser.Argument_expression_listContext ctx) {
		super.enterArgument_expression_list(ctx);    //To change body of overridden methods use File | Settings | File Templates.
	}

	/**
	 * {@inheritDoc}
	 * <p/>
	 * The default implementation does nothing.
	 */
	@Override
	public void exitArgument_expression_list(@NotNull ExprParser.Argument_expression_listContext ctx) {
		super.exitArgument_expression_list(ctx);    //To change body of overridden methods use File | Settings | File Templates.
	}


	@Override
	public void enterEveryRule(@NotNull ParserRuleContext ctx) {
		super.enterEveryRule(ctx);    //To change body of overridden methods use File | Settings | File Templates.
	}

	@Override
	public void exitEveryRule(@NotNull ParserRuleContext ctx) {
		super.exitEveryRule(ctx);    //To change body of overridden methods use File | Settings | File Templates.
	}

	@Override
	public void visitTerminal(@NotNull TerminalNode node) {
		super.visitTerminal(node);    //To change body of overridden methods use File | Settings | File Templates.
	}

	@Override
	public void visitErrorNode(@NotNull ErrorNode node) {
		super.visitErrorNode(node);    //To change body of overridden methods use File | Settings | File Templates.
	}
}
