package ast;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: wind
 * Date: 13-9-28
 * To change this template use File | Settings | File Templates.
 */
public class Expr {
	public static Map<String, Object> getAST(String action) throws IOException {
		ExprParser strParser = new ExprParser(new CommonTokenStream(new ExprLexer(new ANTLRInputStream(new ByteArrayInputStream(action.getBytes())))));
		ExprBuildTree treeBuilder = new ExprBuildTree();
		return treeBuilder.visitDo_action(strParser.do_action());
	}
}
