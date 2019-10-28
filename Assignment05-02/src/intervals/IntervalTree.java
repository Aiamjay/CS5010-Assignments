package intervals;

import static utils.Utils.INTERVAL_OPERATORS;
import static utils.Utils.intervalCalculate;

import java.util.function.Function;

import tree.TreeNode;
import tree.TriFunction;
import utils.Utils;

/**
 * Interval tree for interval expression calculation.
 */
public class IntervalTree implements Intervals {
  /**
   * Calculate the final result of interval tree.
   * @return result, interval object.
   */
  @Override
  public Interval evaluate() {
    return (Interval) this.root.reduce(calculate);
  }

  /**
   * Out put the tree formatting string of interval tree.
   * @return string output
   */
  @Override
  public String textTree() {
    return (String) this.root.map(e -> e.toString()).reduce(treeExpression);
  }

  private static final String TAG = "ExpressionTree";
  private TreeNode root;

  private static final TriFunction<String, Interval, Interval, Interval> calculate =
      (arg1, arg2, arg3) -> intervalCalculate(arg1, arg2, arg3);

  private static final TriFunction<String, String, String, String> treeExpression =
      (arg1, arg2, arg3) ->
                  arg1
                          + "\n|"
                          + "\n|"
                          + "\n|___" + arg2.replace("\n", "\n|   ")
                          + "\n|"
                          + "\n|___" + arg3.replace("\n", "\n    ");

  /**
   * Constructor for interval tree.
   * @param expression a postfix string.
   */
  public IntervalTree(String expression) {
    this.root = Utils.parsingString(expression, string2Interval, INTERVAL_OPERATORS);
  }

  private static final Function<String, Interval> string2Interval = (str) -> {
    int index = str.indexOf(',');
    if (index == -1) {
      throw new IllegalArgumentException("Invalid interval expression!");
    }
    return new Interval(Integer.parseInt(str.substring(0, index)),
            Integer.parseInt(str.substring(index + 1)));
  };
}