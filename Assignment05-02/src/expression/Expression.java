package expression;

/**
 * This interface represents all the operations on an expression tree,
 * including expression forms and formatting.
 */
public interface Expression {
  /**
   * Evaluate this expression.
   * @return the result of this expression as a double-precision value
   */
  double evaluate();

  /**
   * Compute and return the infix form of this expression.
   * @return the infix form of this expression as a space-separated string
   * with parentheses.
   */
  String infix();

  /**
   * Compute and return a string representation of this expression in valid
   * Scheme syntax.
   * @return the expression in Scheme as a string
   */
  String schemeExpression();
}