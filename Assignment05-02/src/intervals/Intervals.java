package intervals;

/**
 * This interface represents all operations offered on an expression involving 1D intervals and
 * binary operations on them.
 */
public interface Intervals {
  /**
   * Evaluate this expression of intervals and return the resulting interval.
   *
   * @return the result as an Interval object
   */
  Interval evaluate();

  /**
   * Returns a string that is the textual representation of the tree structure. For example, the
   * expression "(1,2) (3,4) U" will be formatted as a string:
   * <pre>
   *   U
   *   |
   *   |
   *   |___1,2
   *   |
   *   |___3,4
   * </pre>
   * The expression "(1,2) (3,4) (4,6) U (3,5) I U will be formatted as a string:
   * <pre>
   *   U
   *   |
   *   |
   *   |___1,2
   *   |
   *   |___I
   *       |
   *       |
   *       |___U
   *       |   |
   *       |   |
   *       |   |___3,4
   *       |   |
   *       |   |___4,6
   *       |
   *       |___3,5
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
   *   symbols (U,I) and start,end respectively.</li>
   * </ul>
   *
   * @return a string in the above format
   */
  String textTree();
}