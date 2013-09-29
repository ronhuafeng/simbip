// Generated from G:\Tsmart Projects\ParseBIP\src\ast\Expr.g4 by ANTLR 4.1
package ast;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNSimulator;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ExprParser extends Parser {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__10=1, T__9=2, T__8=3, T__7=4, T__6=5, T__5=6, T__4=7, T__3=8, T__2=9,
		T__1=10, T__0=11, CT_INT=12, CT_CHAR=13, CT_FLOAT=14, CT_DOUBLE=15, TRUE=16,
		FALSE=17, EXTERN=18, EXPORT=19, DEFINE=20, DATA=21, PACKAGE=22, END=23,
		DOT=24, USE=25, AS=26, SEMICOL=27, ATOM=28, COMPOUND=29, COMPONENT=30,
		ON=31, INTERNAL=32, DO=33, PROVIDED=34, INITIAL=35, PLACE=36, FROM=37,
		TO=38, PRIORITY=39, CONNECTOR=40, UP_ACTION=41, DOWN_ACTION=42, PORT=43,
		TYPE=44, LPAREN=45, RPAREN=46, COMMA=47, QUOTE=48, IF=49, THEN=50, ELSE=51,
		FI=52, ID=53, INT=54, FLOAT=55, COMMENT=56, WS=57, STRING=58, LT_OP=59,
		GT_OP=60, LE_OP=61, GE_OP=62, EQ_OP=63, NE_OP=64, AND_OP=65, OR_OP=66;
	public static final String[] tokenNames = {
		"<INVALID>", "'%'", "'&'", "'^'", "'+'", "'*'", "'-'", "'='", "'/'", "'~'",
		"'|'", "'!'", "'int'", "'char'", "'float'", "'double'", "'true'", "'false'",
		"'extern'", "'export'", "'define'", "'data'", "'package'", "'end'", "'.'",
		"'use'", "'as'", "';'", "'atom'", "'compound'", "'component'", "'on'",
		"'internal'", "'do'", "'provided'", "'initial'", "'place'", "'from'",
		"'to'", "'priority'", "'connector'", "'up'", "'down'", "'port'", "'type'",
		"'('", "')'", "','", "'''", "'if'", "'then'", "'else'", "'fi'", "ID",
		"INT", "FLOAT", "COMMENT", "WS", "STRING", "'<'", "'>'", "'<='", "'>='",
		"'=='", "'!='", "'&&'", "'||'"
	};
	public static final int
		RULE_do_action = 0, RULE_primary_expression = 1, RULE_statement = 2, RULE_if_then_else_expression = 3,
		RULE_assignment_expression = 4, RULE_logical_or_expression = 5, RULE_logical_and_expression = 6,
		RULE_inclusive_or_expression = 7, RULE_exclusive_or_expression = 8, RULE_and_expression = 9,
		RULE_equality_expression = 10, RULE_relational_expression = 11, RULE_additive_expression = 12,
		RULE_subtractive_expression = 13, RULE_multiplicative_expression = 14,
		RULE_unary_expression = 15, RULE_postfix_expression = 16;
	public static final String[] ruleNames = {
		"do_action", "primary_expression", "statement", "if_then_else_expression",
		"assignment_expression", "logical_or_expression", "logical_and_expression",
		"inclusive_or_expression", "exclusive_or_expression", "and_expression",
		"equality_expression", "relational_expression", "additive_expression",
		"subtractive_expression", "multiplicative_expression", "unary_expression",
		"postfix_expression"
	};

	@Override
	public String getGrammarFileName() { return "Expr.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public ExprParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class Do_actionContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public Do_actionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_do_action; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener) ((ExprListener)listener).enterDo_action(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener) ((ExprListener)listener).exitDo_action(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExprVisitor) return ((ExprVisitor<? extends T>)visitor).visitDo_action(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Do_actionContext do_action() throws RecognitionException {
		Do_actionContext _localctx = new Do_actionContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_do_action);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(37);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 6) | (1L << 9) | (1L << 11) | (1L << TRUE) | (1L << FALSE) | (1L << SEMICOL) | (1L << LPAREN) | (1L << IF) | (1L << ID) | (1L << INT))) != 0)) {
				{
				{
				setState(34); statement();
				}
				}
				setState(39);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Primary_expressionContext extends ParserRuleContext {
		public String tag;
		public Object value;
		public Token ID;
		public Token INT;
		public TerminalNode TRUE() { return getToken(ExprParser.TRUE, 0); }
		public TerminalNode INT() { return getToken(ExprParser.INT, 0); }
		public Logical_or_expressionContext logical_or_expression() {
			return getRuleContext(Logical_or_expressionContext.class,0);
		}
		public TerminalNode ID() { return getToken(ExprParser.ID, 0); }
		public TerminalNode FALSE() { return getToken(ExprParser.FALSE, 0); }
		public Primary_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primary_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener) ((ExprListener)listener).enterPrimary_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener) ((ExprListener)listener).exitPrimary_expression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExprVisitor) return ((ExprVisitor<? extends T>)visitor).visitPrimary_expression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Primary_expressionContext primary_expression() throws RecognitionException {
		Primary_expressionContext _localctx = new Primary_expressionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_primary_expression);
		try {
			setState(52);
			switch (_input.LA(1)) {
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(40); ((Primary_expressionContext)_localctx).ID = match(ID);
				((Primary_expressionContext)_localctx).tag = "Identifier"; ((Primary_expressionContext)_localctx).value =  (((Primary_expressionContext)_localctx).ID!=null?((Primary_expressionContext)_localctx).ID.getText():null);
				}
				break;
			case INT:
				enterOuterAlt(_localctx, 2);
				{
				setState(42); ((Primary_expressionContext)_localctx).INT = match(INT);
				((Primary_expressionContext)_localctx).tag = "Integer"; ((Primary_expressionContext)_localctx).value = Integer.parseInt((((Primary_expressionContext)_localctx).INT!=null?((Primary_expressionContext)_localctx).INT.getText():null));
				}
				break;
			case TRUE:
				enterOuterAlt(_localctx, 3);
				{
				setState(44); match(TRUE);
				((Primary_expressionContext)_localctx).tag = "Boolean"; ((Primary_expressionContext)_localctx).value = true;
				}
				break;
			case FALSE:
				enterOuterAlt(_localctx, 4);
				{
				setState(46); match(FALSE);
				((Primary_expressionContext)_localctx).tag = "Boolean"; ((Primary_expressionContext)_localctx).value = false;
				}
				break;
			case LPAREN:
				enterOuterAlt(_localctx, 5);
				{
				setState(48); match(LPAREN);
				setState(49); logical_or_expression();
				setState(50); match(RPAREN);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public String tag;
		public Object value;
		public TerminalNode SEMICOL() { return getToken(ExprParser.SEMICOL, 0); }
		public Assignment_expressionContext assignment_expression() {
			return getRuleContext(Assignment_expressionContext.class,0);
		}
		public Logical_or_expressionContext logical_or_expression() {
			return getRuleContext(Logical_or_expressionContext.class,0);
		}
		public If_then_else_expressionContext if_then_else_expression() {
			return getRuleContext(If_then_else_expressionContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener) ((ExprListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener) ((ExprListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExprVisitor) return ((ExprVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_statement);
		try {
			setState(62);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(54); assignment_expression();
				setState(55); match(SEMICOL);
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(57); logical_or_expression();
				setState(58); match(SEMICOL);
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(60); if_then_else_expression();
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(61); match(SEMICOL);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class If_then_else_expressionContext extends ParserRuleContext {
		public String tag;
		public Object value;
		public StatementContext statement;
		public List<StatementContext> then_stmts = new ArrayList<StatementContext>();
		public List<StatementContext> else_stmts = new ArrayList<StatementContext>();
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public TerminalNode THEN() { return getToken(ExprParser.THEN, 0); }
		public TerminalNode IF() { return getToken(ExprParser.IF, 0); }
		public TerminalNode FI() { return getToken(ExprParser.FI, 0); }
		public TerminalNode ELSE() { return getToken(ExprParser.ELSE, 0); }
		public Logical_or_expressionContext logical_or_expression() {
			return getRuleContext(Logical_or_expressionContext.class,0);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public If_then_else_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_if_then_else_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener) ((ExprListener)listener).enterIf_then_else_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener) ((ExprListener)listener).exitIf_then_else_expression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExprVisitor) return ((ExprVisitor<? extends T>)visitor).visitIf_then_else_expression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final If_then_else_expressionContext if_then_else_expression() throws RecognitionException {
		If_then_else_expressionContext _localctx = new If_then_else_expressionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_if_then_else_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(64); match(IF);
			setState(65); match(LPAREN);
			setState(66); logical_or_expression();
			setState(67); match(RPAREN);
			setState(68); match(THEN);
			setState(70);
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(69); ((If_then_else_expressionContext)_localctx).statement = statement();
				((If_then_else_expressionContext)_localctx).then_stmts.add(((If_then_else_expressionContext)_localctx).statement);
				}
				}
				setState(72);
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 6) | (1L << 9) | (1L << 11) | (1L << TRUE) | (1L << FALSE) | (1L << SEMICOL) | (1L << LPAREN) | (1L << IF) | (1L << ID) | (1L << INT))) != 0) );
			setState(80);
			_la = _input.LA(1);
			if (_la==ELSE) {
				{
				setState(74); match(ELSE);
				setState(76);
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(75); ((If_then_else_expressionContext)_localctx).statement = statement();
					((If_then_else_expressionContext)_localctx).else_stmts.add(((If_then_else_expressionContext)_localctx).statement);
					}
					}
					setState(78);
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 6) | (1L << 9) | (1L << 11) | (1L << TRUE) | (1L << FALSE) | (1L << SEMICOL) | (1L << LPAREN) | (1L << IF) | (1L << ID) | (1L << INT))) != 0) );
				}
			}

			setState(82); match(FI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Assignment_expressionContext extends ParserRuleContext {
		public String tag;
		public Object value;
		public Logical_or_expressionContext logical_or_expression() {
			return getRuleContext(Logical_or_expressionContext.class,0);
		}
		public Postfix_expressionContext postfix_expression() {
			return getRuleContext(Postfix_expressionContext.class,0);
		}
		public Assignment_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener) ((ExprListener)listener).enterAssignment_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener) ((ExprListener)listener).exitAssignment_expression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExprVisitor) return ((ExprVisitor<? extends T>)visitor).visitAssignment_expression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Assignment_expressionContext assignment_expression() throws RecognitionException {
		Assignment_expressionContext _localctx = new Assignment_expressionContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_assignment_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(84); postfix_expression();
			setState(85); match(7);
			setState(86); logical_or_expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Logical_or_expressionContext extends ParserRuleContext {
		public String tag;
		public Object value;
		public List<Logical_and_expressionContext> logical_and_expression() {
			return getRuleContexts(Logical_and_expressionContext.class);
		}
		public List<TerminalNode> OR_OP() { return getTokens(ExprParser.OR_OP); }
		public Logical_and_expressionContext logical_and_expression(int i) {
			return getRuleContext(Logical_and_expressionContext.class,i);
		}
		public TerminalNode OR_OP(int i) {
			return getToken(ExprParser.OR_OP, i);
		}
		public Logical_or_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logical_or_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener) ((ExprListener)listener).enterLogical_or_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener) ((ExprListener)listener).exitLogical_or_expression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExprVisitor) return ((ExprVisitor<? extends T>)visitor).visitLogical_or_expression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Logical_or_expressionContext logical_or_expression() throws RecognitionException {
		Logical_or_expressionContext _localctx = new Logical_or_expressionContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_logical_or_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(88); logical_and_expression();
			setState(93);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR_OP) {
				{
				{
				setState(89); match(OR_OP);
				setState(90); logical_and_expression();
				}
				}
				setState(95);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Logical_and_expressionContext extends ParserRuleContext {
		public String tag;
		public Object value;
		public List<Inclusive_or_expressionContext> inclusive_or_expression() {
			return getRuleContexts(Inclusive_or_expressionContext.class);
		}
		public TerminalNode AND_OP(int i) {
			return getToken(ExprParser.AND_OP, i);
		}
		public List<TerminalNode> AND_OP() { return getTokens(ExprParser.AND_OP); }
		public Inclusive_or_expressionContext inclusive_or_expression(int i) {
			return getRuleContext(Inclusive_or_expressionContext.class,i);
		}
		public Logical_and_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logical_and_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener) ((ExprListener)listener).enterLogical_and_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener) ((ExprListener)listener).exitLogical_and_expression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExprVisitor) return ((ExprVisitor<? extends T>)visitor).visitLogical_and_expression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Logical_and_expressionContext logical_and_expression() throws RecognitionException {
		Logical_and_expressionContext _localctx = new Logical_and_expressionContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_logical_and_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(96); inclusive_or_expression();
			setState(101);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND_OP) {
				{
				{
				setState(97); match(AND_OP);
				setState(98); inclusive_or_expression();
				}
				}
				setState(103);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Inclusive_or_expressionContext extends ParserRuleContext {
		public String tag;
		public Object value;
		public Exclusive_or_expressionContext exclusive_or_expression;
		public List<Exclusive_or_expressionContext> eox = new ArrayList<Exclusive_or_expressionContext>();
		public Exclusive_or_expressionContext exclusive_or_expression(int i) {
			return getRuleContext(Exclusive_or_expressionContext.class,i);
		}
		public List<Exclusive_or_expressionContext> exclusive_or_expression() {
			return getRuleContexts(Exclusive_or_expressionContext.class);
		}
		public Inclusive_or_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inclusive_or_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener) ((ExprListener)listener).enterInclusive_or_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener) ((ExprListener)listener).exitInclusive_or_expression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExprVisitor) return ((ExprVisitor<? extends T>)visitor).visitInclusive_or_expression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Inclusive_or_expressionContext inclusive_or_expression() throws RecognitionException {
		Inclusive_or_expressionContext _localctx = new Inclusive_or_expressionContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_inclusive_or_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(104); exclusive_or_expression();
			setState(109);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==10) {
				{
				{
				setState(105); match(10);
				setState(106); ((Inclusive_or_expressionContext)_localctx).exclusive_or_expression = exclusive_or_expression();
				((Inclusive_or_expressionContext)_localctx).eox.add(((Inclusive_or_expressionContext) _localctx).exclusive_or_expression);
				}
				}
				setState(111);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Exclusive_or_expressionContext extends ParserRuleContext {
		public String tag;
		public Object value;
		public And_expressionContext and_expression(int i) {
			return getRuleContext(And_expressionContext.class,i);
		}
		public List<And_expressionContext> and_expression() {
			return getRuleContexts(And_expressionContext.class);
		}
		public Exclusive_or_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exclusive_or_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener) ((ExprListener)listener).enterExclusive_or_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener) ((ExprListener)listener).exitExclusive_or_expression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExprVisitor) return ((ExprVisitor<? extends T>)visitor).visitExclusive_or_expression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Exclusive_or_expressionContext exclusive_or_expression() throws RecognitionException {
		Exclusive_or_expressionContext _localctx = new Exclusive_or_expressionContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_exclusive_or_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(112); and_expression();
			setState(117);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==3) {
				{
				{
				setState(113); match(3);
				setState(114); and_expression();
				}
				}
				setState(119);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class And_expressionContext extends ParserRuleContext {
		public String tag;
		public Object value;
		public Equality_expressionContext equality_expression(int i) {
			return getRuleContext(Equality_expressionContext.class,i);
		}
		public List<Equality_expressionContext> equality_expression() {
			return getRuleContexts(Equality_expressionContext.class);
		}
		public And_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_and_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener) ((ExprListener)listener).enterAnd_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener) ((ExprListener)listener).exitAnd_expression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExprVisitor) return ((ExprVisitor<? extends T>)visitor).visitAnd_expression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final And_expressionContext and_expression() throws RecognitionException {
		And_expressionContext _localctx = new And_expressionContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_and_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(120); equality_expression();
			setState(125);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==2) {
				{
				{
				setState(121); match(2);
				setState(122); equality_expression();
				}
				}
				setState(127);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Equality_expressionContext extends ParserRuleContext {
		public String tag;
		public Object value;
		public Token EQ_OP;
		public List<Token> operators = new ArrayList<Token>();
		public Token NE_OP;
		public TerminalNode NE_OP() { return getToken(ExprParser.NE_OP, 0); }
		public List<Relational_expressionContext> relational_expression() {
			return getRuleContexts(Relational_expressionContext.class);
		}
		public Relational_expressionContext relational_expression(int i) {
			return getRuleContext(Relational_expressionContext.class,i);
		}
		public TerminalNode EQ_OP() { return getToken(ExprParser.EQ_OP, 0); }
		public Equality_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_equality_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener) ((ExprListener)listener).enterEquality_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener) ((ExprListener)listener).exitEquality_expression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExprVisitor) return ((ExprVisitor<? extends T>)visitor).visitEquality_expression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Equality_expressionContext equality_expression() throws RecognitionException {
		Equality_expressionContext _localctx = new Equality_expressionContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_equality_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(128); relational_expression();
			setState(134);
			_la = _input.LA(1);
			if (_la==EQ_OP || _la==NE_OP) {
				{
				setState(131);
				switch (_input.LA(1)) {
				case EQ_OP:
					{
					setState(129); ((Equality_expressionContext)_localctx).EQ_OP = match(EQ_OP);
					((Equality_expressionContext)_localctx).operators.add(((Equality_expressionContext)_localctx).EQ_OP);
					}
					break;
				case NE_OP:
					{
					setState(130); ((Equality_expressionContext)_localctx).NE_OP = match(NE_OP);
					((Equality_expressionContext)_localctx).operators.add(((Equality_expressionContext)_localctx).NE_OP);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(133); relational_expression();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Relational_expressionContext extends ParserRuleContext {
		public String tag;
		public Object value;
		public Token LT_OP;
		public List<Token> operators = new ArrayList<Token>();
		public Token GT_OP;
		public Token LE_OP;
		public Token GE_OP;
		public TerminalNode LE_OP() { return getToken(ExprParser.LE_OP, 0); }
		public Additive_expressionContext additive_expression(int i) {
			return getRuleContext(Additive_expressionContext.class,i);
		}
		public TerminalNode GE_OP() { return getToken(ExprParser.GE_OP, 0); }
		public TerminalNode GT_OP() { return getToken(ExprParser.GT_OP, 0); }
		public List<Additive_expressionContext> additive_expression() {
			return getRuleContexts(Additive_expressionContext.class);
		}
		public TerminalNode LT_OP() { return getToken(ExprParser.LT_OP, 0); }
		public Relational_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relational_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener) ((ExprListener)listener).enterRelational_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener) ((ExprListener)listener).exitRelational_expression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExprVisitor) return ((ExprVisitor<? extends T>)visitor).visitRelational_expression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Relational_expressionContext relational_expression() throws RecognitionException {
		Relational_expressionContext _localctx = new Relational_expressionContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_relational_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(136); additive_expression();
			setState(144);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LT_OP) | (1L << GT_OP) | (1L << LE_OP) | (1L << GE_OP))) != 0)) {
				{
				setState(141);
				switch (_input.LA(1)) {
				case LT_OP:
					{
					setState(137); ((Relational_expressionContext)_localctx).LT_OP = match(LT_OP);
					((Relational_expressionContext)_localctx).operators.add(((Relational_expressionContext)_localctx).LT_OP);
					}
					break;
				case GT_OP:
					{
					setState(138); ((Relational_expressionContext)_localctx).GT_OP = match(GT_OP);
					((Relational_expressionContext)_localctx).operators.add(((Relational_expressionContext)_localctx).GT_OP);
					}
					break;
				case LE_OP:
					{
					setState(139); ((Relational_expressionContext)_localctx).LE_OP = match(LE_OP);
					((Relational_expressionContext)_localctx).operators.add(((Relational_expressionContext)_localctx).LE_OP);
					}
					break;
				case GE_OP:
					{
					setState(140); ((Relational_expressionContext)_localctx).GE_OP = match(GE_OP);
					((Relational_expressionContext)_localctx).operators.add(((Relational_expressionContext)_localctx).GE_OP);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(143); additive_expression();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Additive_expressionContext extends ParserRuleContext {
		public String tag;
		public Object value;
		public List<Subtractive_expressionContext> subtractive_expression() {
			return getRuleContexts(Subtractive_expressionContext.class);
		}
		public Subtractive_expressionContext subtractive_expression(int i) {
			return getRuleContext(Subtractive_expressionContext.class,i);
		}
		public Additive_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_additive_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener) ((ExprListener)listener).enterAdditive_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener) ((ExprListener)listener).exitAdditive_expression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExprVisitor) return ((ExprVisitor<? extends T>)visitor).visitAdditive_expression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Additive_expressionContext additive_expression() throws RecognitionException {
		Additive_expressionContext _localctx = new Additive_expressionContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_additive_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(146); subtractive_expression();
			setState(151);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==4) {
				{
				{
				setState(147); match(4);
				setState(148); subtractive_expression();
				}
				}
				setState(153);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Subtractive_expressionContext extends ParserRuleContext {
		public String tag;
		public Object value;
		public Multiplicative_expressionContext multiplicative_expression(int i) {
			return getRuleContext(Multiplicative_expressionContext.class,i);
		}
		public List<Multiplicative_expressionContext> multiplicative_expression() {
			return getRuleContexts(Multiplicative_expressionContext.class);
		}
		public Subtractive_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subtractive_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener) ((ExprListener)listener).enterSubtractive_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener) ((ExprListener)listener).exitSubtractive_expression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExprVisitor) return ((ExprVisitor<? extends T>)visitor).visitSubtractive_expression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Subtractive_expressionContext subtractive_expression() throws RecognitionException {
		Subtractive_expressionContext _localctx = new Subtractive_expressionContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_subtractive_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(154); multiplicative_expression();
			setState(159);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==6) {
				{
				{
				setState(155); match(6);
				setState(156); multiplicative_expression();
				}
				}
				setState(161);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Multiplicative_expressionContext extends ParserRuleContext {
		public String tag;
		public Object value;
		public Token s8;
		public List<Token> operators = new ArrayList<Token>();
		public Token s1;
		public Token s5;
		public List<Unary_expressionContext> unary_expression() {
			return getRuleContexts(Unary_expressionContext.class);
		}
		public Unary_expressionContext unary_expression(int i) {
			return getRuleContext(Unary_expressionContext.class,i);
		}
		public Multiplicative_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiplicative_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener) ((ExprListener)listener).enterMultiplicative_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener) ((ExprListener)listener).exitMultiplicative_expression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExprVisitor) return ((ExprVisitor<? extends T>)visitor).visitMultiplicative_expression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Multiplicative_expressionContext multiplicative_expression() throws RecognitionException {
		Multiplicative_expressionContext _localctx = new Multiplicative_expressionContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_multiplicative_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(162); unary_expression();
			setState(171);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 1) | (1L << 5) | (1L << 8))) != 0)) {
				{
				{
				setState(166);
				switch (_input.LA(1)) {
				case 8:
					{
					setState(163); ((Multiplicative_expressionContext)_localctx).s8 = match(8);
					((Multiplicative_expressionContext)_localctx).operators.add(((Multiplicative_expressionContext)_localctx).s8);
					}
					break;
				case 1:
					{
					setState(164); ((Multiplicative_expressionContext)_localctx).s1 = match(1);
					((Multiplicative_expressionContext)_localctx).operators.add(((Multiplicative_expressionContext)_localctx).s1);
					}
					break;
				case 5:
					{
					setState(165); ((Multiplicative_expressionContext)_localctx).s5 = match(5);
					((Multiplicative_expressionContext)_localctx).operators.add(((Multiplicative_expressionContext)_localctx).s5);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(168); unary_expression();
				}
				}
				setState(173);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Unary_expressionContext extends ParserRuleContext {
		public String tag;
		public Object value;
		public Token op;
		public Postfix_expressionContext postfix_expression() {
			return getRuleContext(Postfix_expressionContext.class,0);
		}
		public Unary_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unary_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener) ((ExprListener)listener).enterUnary_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener) ((ExprListener)listener).exitUnary_expression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExprVisitor) return ((ExprVisitor<? extends T>)visitor).visitUnary_expression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Unary_expressionContext unary_expression() throws RecognitionException {
		Unary_expressionContext _localctx = new Unary_expressionContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_unary_expression);
		try {
			setState(181);
			switch (_input.LA(1)) {
			case 6:
				enterOuterAlt(_localctx, 1);
				{
				setState(174); ((Unary_expressionContext)_localctx).op = match(6);
				setState(175); postfix_expression();
				}
				break;
			case 9:
			case 11:
			case TRUE:
			case FALSE:
			case LPAREN:
			case ID:
			case INT:
				enterOuterAlt(_localctx, 2);
				{
				setState(178);
				switch (_input.LA(1)) {
				case 9:
					{
					setState(176); ((Unary_expressionContext)_localctx).op = match(9);
					}
					break;
				case 11:
					{
					setState(177); ((Unary_expressionContext)_localctx).op = match(11);
					}
					break;
				case TRUE:
				case FALSE:
				case LPAREN:
				case ID:
				case INT:
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(180); postfix_expression();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Postfix_expressionContext extends ParserRuleContext {
		public String tag;
		public Object value;
		public Primary_expressionContext primary_expression(int i) {
			return getRuleContext(Primary_expressionContext.class,i);
		}
		public List<Primary_expressionContext> primary_expression() {
			return getRuleContexts(Primary_expressionContext.class);
		}
		public Postfix_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_postfix_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener) ((ExprListener)listener).enterPostfix_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener) ((ExprListener)listener).exitPostfix_expression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExprVisitor) return ((ExprVisitor<? extends T>)visitor).visitPostfix_expression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Postfix_expressionContext postfix_expression() throws RecognitionException {
		Postfix_expressionContext _localctx = new Postfix_expressionContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_postfix_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(183); primary_expression();
			setState(188);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT) {
				{
				{
				setState(184); match(DOT);
				setState(185); primary_expression();
				}
				}
				setState(190);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\uacf5\uee8c\u4f5d\u8b0d\u4a45\u78bd\u1b2f\u3378\3D\u00c2\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\3\2\7\2&\n\2\f\2\16\2)\13\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\5\3\67\n\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4A\n\4\3\5\3\5\3\5"+
		"\3\5\3\5\3\5\6\5I\n\5\r\5\16\5J\3\5\3\5\6\5O\n\5\r\5\16\5P\5\5S\n\5\3"+
		"\5\3\5\3\6\3\6\3\6\3\6\3\7\3\7\3\7\7\7^\n\7\f\7\16\7a\13\7\3\b\3\b\3\b"+
		"\7\bf\n\b\f\b\16\bi\13\b\3\t\3\t\3\t\7\tn\n\t\f\t\16\tq\13\t\3\n\3\n\3"+
		"\n\7\nv\n\n\f\n\16\ny\13\n\3\13\3\13\3\13\7\13~\n\13\f\13\16\13\u0081"+
		"\13\13\3\f\3\f\3\f\5\f\u0086\n\f\3\f\5\f\u0089\n\f\3\r\3\r\3\r\3\r\3\r"+
		"\5\r\u0090\n\r\3\r\5\r\u0093\n\r\3\16\3\16\3\16\7\16\u0098\n\16\f\16\16"+
		"\16\u009b\13\16\3\17\3\17\3\17\7\17\u00a0\n\17\f\17\16\17\u00a3\13\17"+
		"\3\20\3\20\3\20\3\20\5\20\u00a9\n\20\3\20\7\20\u00ac\n\20\f\20\16\20\u00af"+
		"\13\20\3\21\3\21\3\21\3\21\5\21\u00b5\n\21\3\21\5\21\u00b8\n\21\3\22\3"+
		"\22\3\22\7\22\u00bd\n\22\f\22\16\22\u00c0\13\22\3\22\2\23\2\4\6\b\n\f"+
		"\16\20\22\24\26\30\32\34\36 \"\2\2\u00cf\2\'\3\2\2\2\4\66\3\2\2\2\6@\3"+
		"\2\2\2\bB\3\2\2\2\nV\3\2\2\2\fZ\3\2\2\2\16b\3\2\2\2\20j\3\2\2\2\22r\3"+
		"\2\2\2\24z\3\2\2\2\26\u0082\3\2\2\2\30\u008a\3\2\2\2\32\u0094\3\2\2\2"+
		"\34\u009c\3\2\2\2\36\u00a4\3\2\2\2 \u00b7\3\2\2\2\"\u00b9\3\2\2\2$&\5"+
		"\6\4\2%$\3\2\2\2&)\3\2\2\2\'%\3\2\2\2\'(\3\2\2\2(\3\3\2\2\2)\'\3\2\2\2"+
		"*+\7\67\2\2+\67\b\3\1\2,-\78\2\2-\67\b\3\1\2./\7\22\2\2/\67\b\3\1\2\60"+
		"\61\7\23\2\2\61\67\b\3\1\2\62\63\7/\2\2\63\64\5\f\7\2\64\65\7\60\2\2\65"+
		"\67\3\2\2\2\66*\3\2\2\2\66,\3\2\2\2\66.\3\2\2\2\66\60\3\2\2\2\66\62\3"+
		"\2\2\2\67\5\3\2\2\289\5\n\6\29:\7\35\2\2:A\3\2\2\2;<\5\f\7\2<=\7\35\2"+
		"\2=A\3\2\2\2>A\5\b\5\2?A\7\35\2\2@8\3\2\2\2@;\3\2\2\2@>\3\2\2\2@?\3\2"+
		"\2\2A\7\3\2\2\2BC\7\63\2\2CD\7/\2\2DE\5\f\7\2EF\7\60\2\2FH\7\64\2\2GI"+
		"\5\6\4\2HG\3\2\2\2IJ\3\2\2\2JH\3\2\2\2JK\3\2\2\2KR\3\2\2\2LN\7\65\2\2"+
		"MO\5\6\4\2NM\3\2\2\2OP\3\2\2\2PN\3\2\2\2PQ\3\2\2\2QS\3\2\2\2RL\3\2\2\2"+
		"RS\3\2\2\2ST\3\2\2\2TU\7\66\2\2U\t\3\2\2\2VW\5\"\22\2WX\7\t\2\2XY\5\f"+
		"\7\2Y\13\3\2\2\2Z_\5\16\b\2[\\\7D\2\2\\^\5\16\b\2][\3\2\2\2^a\3\2\2\2"+
		"_]\3\2\2\2_`\3\2\2\2`\r\3\2\2\2a_\3\2\2\2bg\5\20\t\2cd\7C\2\2df\5\20\t"+
		"\2ec\3\2\2\2fi\3\2\2\2ge\3\2\2\2gh\3\2\2\2h\17\3\2\2\2ig\3\2\2\2jo\5\22"+
		"\n\2kl\7\f\2\2ln\5\22\n\2mk\3\2\2\2nq\3\2\2\2om\3\2\2\2op\3\2\2\2p\21"+
		"\3\2\2\2qo\3\2\2\2rw\5\24\13\2st\7\5\2\2tv\5\24\13\2us\3\2\2\2vy\3\2\2"+
		"\2wu\3\2\2\2wx\3\2\2\2x\23\3\2\2\2yw\3\2\2\2z\177\5\26\f\2{|\7\4\2\2|"+
		"~\5\26\f\2}{\3\2\2\2~\u0081\3\2\2\2\177}\3\2\2\2\177\u0080\3\2\2\2\u0080"+
		"\25\3\2\2\2\u0081\177\3\2\2\2\u0082\u0088\5\30\r\2\u0083\u0086\7A\2\2"+
		"\u0084\u0086\7B\2\2\u0085\u0083\3\2\2\2\u0085\u0084\3\2\2\2\u0086\u0087"+
		"\3\2\2\2\u0087\u0089\5\30\r\2\u0088\u0085\3\2\2\2\u0088\u0089\3\2\2\2"+
		"\u0089\27\3\2\2\2\u008a\u0092\5\32\16\2\u008b\u0090\7=\2\2\u008c\u0090"+
		"\7>\2\2\u008d\u0090\7?\2\2\u008e\u0090\7@\2\2\u008f\u008b\3\2\2\2\u008f"+
		"\u008c\3\2\2\2\u008f\u008d\3\2\2\2\u008f\u008e\3\2\2\2\u0090\u0091\3\2"+
		"\2\2\u0091\u0093\5\32\16\2\u0092\u008f\3\2\2\2\u0092\u0093\3\2\2\2\u0093"+
		"\31\3\2\2\2\u0094\u0099\5\34\17\2\u0095\u0096\7\6\2\2\u0096\u0098\5\34"+
		"\17\2\u0097\u0095\3\2\2\2\u0098\u009b\3\2\2\2\u0099\u0097\3\2\2\2\u0099"+
		"\u009a\3\2\2\2\u009a\33\3\2\2\2\u009b\u0099\3\2\2\2\u009c\u00a1\5\36\20"+
		"\2\u009d\u009e\7\b\2\2\u009e\u00a0\5\36\20\2\u009f\u009d\3\2\2\2\u00a0"+
		"\u00a3\3\2\2\2\u00a1\u009f\3\2\2\2\u00a1\u00a2\3\2\2\2\u00a2\35\3\2\2"+
		"\2\u00a3\u00a1\3\2\2\2\u00a4\u00ad\5 \21\2\u00a5\u00a9\7\n\2\2\u00a6\u00a9"+
		"\7\3\2\2\u00a7\u00a9\7\7\2\2\u00a8\u00a5\3\2\2\2\u00a8\u00a6\3\2\2\2\u00a8"+
		"\u00a7\3\2\2\2\u00a9\u00aa\3\2\2\2\u00aa\u00ac\5 \21\2\u00ab\u00a8\3\2"+
		"\2\2\u00ac\u00af\3\2\2\2\u00ad\u00ab\3\2\2\2\u00ad\u00ae\3\2\2\2\u00ae"+
		"\37\3\2\2\2\u00af\u00ad\3\2\2\2\u00b0\u00b1\7\b\2\2\u00b1\u00b8\5\"\22"+
		"\2\u00b2\u00b5\7\13\2\2\u00b3\u00b5\7\r\2\2\u00b4\u00b2\3\2\2\2\u00b4"+
		"\u00b3\3\2\2\2\u00b4\u00b5\3\2\2\2\u00b5\u00b6\3\2\2\2\u00b6\u00b8\5\""+
		"\22\2\u00b7\u00b0\3\2\2\2\u00b7\u00b4\3\2\2\2\u00b8!\3\2\2\2\u00b9\u00be"+
		"\5\4\3\2\u00ba\u00bb\7\32\2\2\u00bb\u00bd\5\4\3\2\u00bc\u00ba\3\2\2\2"+
		"\u00bd\u00c0\3\2\2\2\u00be\u00bc\3\2\2\2\u00be\u00bf\3\2\2\2\u00bf#\3"+
		"\2\2\2\u00c0\u00be\3\2\2\2\30\'\66@JPR_gow\177\u0085\u0088\u008f\u0092"+
		"\u0099\u00a1\u00a8\u00ad\u00b4\u00b7\u00be";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
