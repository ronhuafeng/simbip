package ast;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.*;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: wind
 * Date: 13-9-28
 * To change this template use File | Settings | File Templates.
 */
public class Expr {

	public static void main(String [] args) throws Exception {

		File file = new File("resource/expr.bip");
		if (file.exists() == false)
		{
			System.err.println("So ...");
		}

		InputStream fileStream = new FileInputStream(file);

		ANTLRInputStream input = new ANTLRInputStream(fileStream);

		ExprLexer lexer = new ExprLexer(input);

		CommonTokenStream tokens = new CommonTokenStream(lexer);

		ExprParser parser = new ExprParser(tokens);

		ParseTree treeParser = parser.do_action();

		System.out.println(treeParser.getText());

		ParseTreeWalker walker = new ParseTreeWalker();
		walker.walk(new ExprWalker(), treeParser);



		Map<String, Object> result = (new ExprBuildTree()).visitDo_action((ExprParser.Do_actionContext) treeParser);

		System.out.println();
//		lexer (ExprLexer. input)
//		tokens (CommonTokenStream. lexer)
//		parser (ExprParser. tokens)
//		builer (ExprBuildTree.)
//		ast (.visitDo_action
//				(.do_action parser))]

		String inputStr = "";
	    ExprParser strParser = new ExprParser(new CommonTokenStream(new ExprLexer(new ANTLRInputStream(new ByteArrayInputStream(inputStr.getBytes())))));
		ExprBuildTree treeBuilder = new ExprBuildTree();
		treeBuilder.visitDo_action(strParser.do_action());


	}
	public static Map<String, Object> getAST(String action) throws Exception {
		ExprParser strParser = new ExprParser(new CommonTokenStream(new ExprLexer(new ANTLRInputStream(new ByteArrayInputStream(action.getBytes())))));
		ExprBuildTree treeBuilder = new ExprBuildTree();
		return treeBuilder.visitDo_action(strParser.do_action());
	}

	public static Boolean validateAST(String action)  {
		ExprParser strParser = null;
		Boolean result = true;

		try {
			strParser = new ExprParser(new CommonTokenStream(new ExprLexer(new ANTLRInputStream(new ByteArrayInputStream(action.getBytes())))));
			ExprBuildTree treeBuilder = new ExprBuildTree();
			try {
				treeBuilder.visitDo_action(strParser.do_action());
			} catch (Exception e) {
				e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
				result = false;
			}
		} catch (IOException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
			result = false;
		}


		return result;
	}
}
