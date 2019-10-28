package expression;

/**
 * This interface represents all the operations on an expression tree, including expression forms
 * and formatting.
 */
public interface Expression {
  /**
   * Evaluate this expression.
   *
   * @return the result of this expression as a double-precision value
   */
  double evaluate();

  /**
   * Compute and return the infix form of this expression.
   *
   * @return the infix form of this expression as a space-separated string with parentheses.
   */
  String infix();

  /**
   * Compute and return a string representation of this expression in valid Scheme syntax.
   *
   * @return the expression in Scheme as a string
   */
  String schemeExpression();

  /**
   * Returns a string that is the textual representation of the tree structure. For example, the
   * expression "1 2 +" will be formatted as a string:
   * <pre>
   *   +
   *   |
   *   |
   *   |___1.0
   *   |
   *   |___2.0
   * </pre>
   * The expression "1 4 6 - 5 + / will be formatted as a string:
   * <pre>
   *   /
   *   |
   *   |
   *   |___1.0
   *   |
   *   |___+
   *       |
   *       |
   *       |___-
   *       |   |
   *       |   |
   *       |   |___4.0
   *       |   |
   *       |   |___6.0
   *       |
   *       |___5.0
   *
   * </pre>
   * Specifically:
   * <ul>
   *   <li>The first operand always begins three lines down and three
   *   spaces to the right of the operand</li>
   *   <li>The second operand begins two lines below the end
   *   of the first
   *   operand</li>
   *   <li>A vertical line (using | characters) exists that connects the line of
   *   an operator and its two operands</li>
   *   <li>A horizontal line (using _ characters) exists between the
   *   vertical line from the operator to the beginning of each operand</li>
   *   <li>Operators and operands are represented by their respective
   *   symbols (+,-,*,/) and operand value respectively.</li>
   * </ul>
   *
   * @return a string in the above format
   */
  String textTree();
}