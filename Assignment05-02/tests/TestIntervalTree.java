import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import intervals.IntervalTree;

/**
 * This is the test class for IntervalTree.
 */
public class TestIntervalTree {

  @Test
  public void testTree() {
    IntervalTree intervalTree = new IntervalTree("-4,4 2,5 U  -1,4 I ");
    assertEquals("-1,4", intervalTree.evaluate().toString());
    IntervalTree test1 = new IntervalTree("1,4 2,5 U");
    assertEquals("1,5", test1.evaluate().toString());
    IntervalTree test2 = new IntervalTree("3,7 2,6 4,10 I U");
    assertEquals("3,7", test2.evaluate().toString());
    IntervalTree test3 = new IntervalTree("3,10 5,12 U 4,4 I");
    assertEquals("4,4", test3.evaluate().toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidArgument() {
    assertEquals("the expression provided to it is an invalid expression.", new IntervalTree("-4 ,4 2,5 U"));
    assertEquals("the expression provided to it is an invalid expression.", new IntervalTree("-4,4 2,5 U I "));
    assertEquals("the expression provided to it is an invalid expression.", new IntervalTree("-4,4 2,5"));
    assertEquals("the expression provided to it is an invalid expression.", new IntervalTree("U"));
    assertEquals("the expression provided to it is an invalid expression.", new IntervalTree("-4,4 1,2 @"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyTree() {
    assertEquals("the expression provided to it is an invalid expression.", new IntervalTree(""));

  }

  @Test
  public void testTree1() {
    String s = "     -4,4    2,5       U    ";
    assertEquals("-4,5", new IntervalTree(s).evaluate().toString());
  }

}
