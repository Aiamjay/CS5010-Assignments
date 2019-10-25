import static org.junit.Assert.assertEquals;

import expression.ExpressionTree;

import org.junit.Test;


/**
 * This is the test class for expression tree.
 */
public class TestExpressionTree {

  @Test
  public void testTreeFunction() {
    ExpressionTree tree = new ExpressionTree("1.2 5.4 *   -4.5 + ");
    assertEquals("( ( 1.2 * 5.4 ) + ( -4.5 ) )", tree.infix());
    assertEquals(1.98, tree.evaluate(), 0.01);
    assertEquals("(+ (* 1.2 5.4) -4.5)", tree.schemeExpression());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidArgument() {
    assertEquals("the expression provided to it is an invalid expression.",
            new ExpressionTree("1.2 5.4* -4.5+ "));
    assertEquals("the expression provided to it is an invalid expression.",
            new ExpressionTree("1.2 5.4 @ -4.5 + "));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyTree() {
    assertEquals("the expression provided to it is an invalid expression.",
            new ExpressionTree(""));

  }

  @Test(expected = IllegalArgumentException.class)
  public void testIncompleteTree() {
    assertEquals("the expression provided to it is an invalid expression.",
            new ExpressionTree("1.2 5.4"));
    assertEquals("the expression provided to it is an invalid expression.",
            new ExpressionTree("1.2 5.4 *     -2"));
    assertEquals("the expression provided to it is an invalid expression.",
            new ExpressionTree("1.2 5.4 + -     "));
  }

  @Test
  public void testTree() {
    String s1 = "   1 2   + 3   4 + *";
    ExpressionTree test = new ExpressionTree(s1);
    assertEquals("( ( 1.0 + 2.0 ) * ( 3.0 + 4.0 ) )", test.infix());
    assertEquals("(* (+ 1.0 2.0) (+ 3.0 4.0))", test.schemeExpression());
    assertEquals(21, test.evaluate(), 0.1);
  }

  @Test

  public 
}

