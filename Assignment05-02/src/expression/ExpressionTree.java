package expression;

import static utils.Utils.NUMERICAL_OPERATORS;
import static utils.Utils.numericalCalculate;

import java.util.function.Function;

import tree.TreeNode;
import tree.TriFunction;
import utils.Utils;


/**
 * Expression Tree.
 */
public class ExpressionTree implements Expression {
  private static final String TAG = "ExpressionTree";
  private TreeNode root;

  private static final TriFunction<String, Double, Double, Double> calculate =
      (arg1, arg2, arg3) -> numericalCalculate(arg1, arg2, arg3);

  private static final TriFunction<String, String, String, String> inOrder = (arg1, arg2, arg3)
      -> "(" + arg2 + arg1 + (arg3.charAt(0) == '-' ? "(" + arg3 + ")" : arg3) + ")";

  private static final TriFunction<String, String, String, String> preOrder =
      (arg1, arg2, arg3) -> "(" + arg1 + " " + arg2 + " " + arg3 + ")";
  private static final TriFunction<String, String, String, String> treeString =
      (arg1, arg2, arg3) ->
                  arg1
                          + "\n|"
                          + "\n|"
                          + "\n|___" + arg2.replace("\n", "\n|   ")
                          + "\n|"
                          + "\n|___" + arg3.replace("\n", "\n|   ");

  /**
   * Constructor for expression tree.
   *
   * @param expression postfix string of a tree string.
   */
  public ExpressionTree(String expression) {
    this.root = Utils.parsingString(expression, string2Double, NUMERICAL_OPERATORS);
  }

  private static final Function<String, Double> string2Double = (str) -> {
    try {
      return Double.parseDouble(str);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid character in expression string");
    }
  };

  /**
   * Get the result of the expression.
   *
   * @return result.
   */
  @Override
  public double evaluate() {
    // note it's not elegant to cast Object to double, because root data type vary.
    return (double) this.root.reduce(calculate);
  }

  /**
   * Compute and return the infix form of this expression.
   *
   * @return the infix form of this expression as a space-separated string with parentheses.
   */
  @Override
  public String infix() {
    TreeNode<String> node = this.root.map(e -> e.toString());
    return node.reduce(inOrder);
  }

  /**
   * Compute and return a string representation of this expression in valid Scheme syntax.
   *
   * @return the expression in Scheme as a string
   */
  @Override
  public String schemeExpression() {
    TreeNode<String> node = this.root.map(e -> e.toString());
    return node.reduce(preOrder);
  }

  /**
   * Returns a string that is the textual representation of the tree structure.
   *
   * @return expression string.
   */
  @Override
  public String textTree() {
    return (String) this.root.map(e -> e.toString()).reduce(treeString);
  }
}
