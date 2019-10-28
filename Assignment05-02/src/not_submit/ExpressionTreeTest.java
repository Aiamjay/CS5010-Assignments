package not_submit;

import static org.junit.Assert.assertEquals;

import expression.Expression;
import expression.ExpressionTree;
import org.junit.Test;

/**
 * Test class for ExpressionTree.
 */
public class ExpressionTreeTest {

  /**
   * Test constructor.
   */
  @Test
  public void testConstructor() {
    new ExpressionTree("1 2 +");
    new ExpressionTree("1.2 5.4 *   -4.5 + ");
    new ExpressionTree("3 2 + 65.12 -");
    new ExpressionTree("3 5 + 4 -");
    new ExpressionTree("3 2 + 65.12 - 3 *");
    new ExpressionTree("  3 2 + 65.12 - 3 *  ");
  }

  /**
   * Test constructor with empty input.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructor1() {
    new ExpressionTree("");
  }

  /**
   * Test constructor with no operators.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructor2() {
    new ExpressionTree("1 2");
  }

  /**
   * Test constructor with redundant operators.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructor3() {
    new ExpressionTree("1 2 + -");
  }

  /**
   * Test constructor with invalid operand.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructor4() {
    new ExpressionTree("1 b +");
  }

  /**
   * Test constructor with invalid operators.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructor5() {
    new ExpressionTree("1 2 b");
  }

  /**
   * Test constructor with invalid operators.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructor6() {
    new ExpressionTree("1 2 &");
  }

  /**
   * Test constructor with operators only.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructor7() {
    new ExpressionTree("+");
  }

  /**
   * Test constructor with IntervalTree's input.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructor9() {
    new ExpressionTree("1,4 2,5 U");
  }


  /**
   * Test evaluate.
   */
  @Test
  public void testEvaluate() {
    Expression tree1 = new ExpressionTree("1 2 +");
    assertEquals(3, tree1.evaluate(), 0);
    Expression tree2 = new ExpressionTree("1.2 5.4 *   -4.5 + ");
    assertEquals(1.98, tree2.evaluate(), 0.0000001);
    Expression tree3 = new ExpressionTree("3 2 + 65.12 -");
    assertEquals(-60.12, tree3.evaluate(), 0.0000001);
    Expression tree4 = new ExpressionTree("3 5 + 4 -");
    assertEquals(4, tree4.evaluate(), 0.0000001);
    Expression tree5 = new ExpressionTree("3 2 + 65.12 - 3 *");
    assertEquals(-180.36, tree5.evaluate(), 0.0000001);
    Expression tree6 = new ExpressionTree("5 0 /");
    assertEquals(Double.POSITIVE_INFINITY, tree6.evaluate(), 0.0000001);
    Expression tree7 = new ExpressionTree("-5 0 /");
    assertEquals(Double.NEGATIVE_INFINITY, tree7.evaluate(), 0.0000001);
  }

  /**
   * Test infix.
   */
  @Test
  public void testInfix() {
    Expression tree1 = new ExpressionTree("1 2 +");
    assertEquals("( 1.0 + 2.0 )", tree1.infix());
    Expression tree2 = new ExpressionTree("1 2 + 3 4 + *");
    assertEquals("( ( 1.0 + 2.0 ) * ( 3.0 + 4.0 ) )", tree2.infix());
    Expression tree3 = new ExpressionTree("1.2 5.4 *   -4.5 + ");
    assertEquals("( ( 1.2 * 5.4 ) + -4.5 )", tree3.infix());
    Expression tree4 = new ExpressionTree("1 2 + 3 *");
    assertEquals("( ( 1.0 + 2.0 ) * 3.0 )", tree4.infix());
  }

  /**
   * Test scheme.
   */
  @Test
  public void testScheme() {
    Expression tree1 = new ExpressionTree("1 3 2 * -");
    assertEquals("(- 1.0 (* 3.0 2.0))", tree1.schemeExpression());
    Expression tree2 = new ExpressionTree("1 2 + 3 4 + *");
    assertEquals("(* (+ 1.0 2.0) (+ 3.0 4.0))", tree2.schemeExpression());
    Expression tree3 = new ExpressionTree("1 2 - 3 + 4 -");
    assertEquals("(- (+ (- 1.0 2.0) 3.0) 4.0)", tree3.schemeExpression());
  }

  /**
   * Test textTree.
   */
  @Test
  public void testTextTree() {
    Expression tree1 = new ExpressionTree("1 2 +");
    String expected1 = "+\n"
            + "|\n"
            + "|\n"
            + "|___1.0\n"
            + "|\n"
            + "|___2.0\n";
    assertEquals(expected1, tree1.textTree());
    Expression tree2 = new ExpressionTree("1 4 6 - 5 + /");
    String expected2 = "/\n"
            + "|\n"
            + "|\n"
            + "|___1.0\n"
            + "|\n"
            + "|___+\n"
            + "    |\n"
            + "    |\n"
            + "    |___-\n"
            + "    |   |\n"
            + "    |   |\n"
            + "    |   |___4.0\n"
            + "    |   |\n"
            + "    |   |___6.0\n"
            + "    |\n"
            + "    |___5.0\n";
    assertEquals(expected2, tree2.textTree());
  }
}