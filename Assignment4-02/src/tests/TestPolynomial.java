package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import polynomial.Polynomial;
import polynomial.PolynomialImpl;

/**
 * Tests for PolynomialImpl.
 */
public class TestPolynomial {

  @Test(expected = IllegalArgumentException.class)
  public void testToString() {
    String s = "+4x^3 +3x^2 +2x^1 +1x^0";
    PolynomialImpl node = new PolynomialImpl(s);
    assertEquals("4x^3+3x^2+2x^1+1", node.toString());

    s = "0";
    node = new PolynomialImpl(s);
    assertEquals("0", node.toString());

    node = new PolynomialImpl();
    assertEquals("0", node.toString());

    s = "+0x^10 -0x^1 +0 +1x^1";
    node = new PolynomialImpl(s);
    assertEquals("1x^1", node.toString());

    s = "10x^-1";
    node = new PolynomialImpl(s);
    assertEquals("0", node.toString());
  }

  @Test
  public void testAddTerm() {
    String s = "+4x^3 +3x^2 +2x^1 +1x^0";
    PolynomialImpl node = new PolynomialImpl(s);
    assertEquals("4x^3+3x^2+2x^1+1", node.toString());

    s = "+50";
    node = new PolynomialImpl(s);
    assertEquals("50", node.toString());

    node = new PolynomialImpl();
    assertEquals("0", node.toString());

    node.addTerm(10, 4);
    assertEquals("10x^4", node.toString());

    node.addTerm(-10, 4);
    assertEquals("0", node.toString());

    s = "+10x^4 -5x^3 -10x^2 +3x^1 +50";
    node = new PolynomialImpl(s);
    assertEquals("10x^4-5x^3-10x^2+3x^1+50", node.toString());

    node.addTerm(10, 0);
    assertEquals("10x^4-5x^3-10x^2+3x^1+60", node.toString());

    node.addTerm(-10, 4);
    assertEquals("-5x^3-10x^2+3x^1+60", node.toString());

    node.addTerm(5, 3);
    assertEquals("-10x^2+3x^1+60", node.toString());

    node.addTerm(10, 2);
    assertEquals("3x^1+60", node.toString());

    node.addTerm(-3, 1);
    assertEquals("60", node.toString());

    s = "+100 -100 +0x^10";
    node = new PolynomialImpl(s);
    assertEquals("0", node.toString());
  }

  @Test
  public void testAdd() {
    String s1 = "-4x^3 +6x^2 +2x^1";
    String s2 = "+6 +11x^1 +6x^2 +x^3";
    PolynomialImpl node1 = new PolynomialImpl(s1);
    PolynomialImpl node2 = new PolynomialImpl(s2);
    assertEquals("-3x^3+12x^2+13x^1+6", (node1.add(node2)).toString());

    s1 = "+5x^2 +2";
    s2 = "+4x^2 -7";
    String s3 = "-3x^2 -5";
    node1 = new PolynomialImpl(s1);
    node2 = new PolynomialImpl(s2);
    PolynomialImpl node3 = new PolynomialImpl(s3);
    Polynomial temp1 = node1.add(node2);
    temp1 = temp1.add(node3);
    assertEquals("6x^2-10", temp1.toString());

    s1 = "0";
    node1 = new PolynomialImpl();
    assertEquals("4x^2-7", (node1.add(node2)).toString());

    node2 = new PolynomialImpl();
    node1 = new PolynomialImpl("+4x^2 -7");
    assertEquals("4x^2-7", (node1.add(node2)).toString());
  }

  @Test
  public void testMultiply() {
    String s1 = "+1x^2 +5x^1 +1";
    String s2 = "+3x^2 -10x^1 +15";
    String s3 = "+1x^1";
    PolynomialImpl node1 = new PolynomialImpl(s1);
    PolynomialImpl node2 = new PolynomialImpl(s2);
    assertEquals("3x^4+5x^3-32x^2+65x^1+15", (node1.multiply(node2)).toString());
    PolynomialImpl node3 = new PolynomialImpl(s3);
    assertEquals("3x^5+5x^4-32x^3+65x^2+15x^1", (node1.multiply(node2).multiply(node3)).toString());

    s1 = "+31x^7 -2x^3 +11x^1 +1";
    s2 = "+62x^7 +80x^3";
    node1 = new PolynomialImpl(s1);
    node2 = new PolynomialImpl(s2);
    System.out.println(node1.multiply(node2));
  }

  @Test
  public void testGetDegreeAndGetCoefficient() {
    String s = "+4x^3 +3x^2 +2x^1 +1";
    PolynomialImpl node = new PolynomialImpl(s);
    assertEquals(3, node.getDegree());
    assertEquals(4, node.getCoefficient(3));
    assertEquals(3, node.getCoefficient(2));
    assertEquals(2, node.getCoefficient(1));
    assertEquals(1, node.getCoefficient(0));

    s = "+50";
    node = new PolynomialImpl(s);
    assertEquals(0, node.getDegree());
    assertEquals(50, node.getCoefficient(0));

    node = new PolynomialImpl();
    assertEquals(0, node.getDegree());
    assertEquals(0, node.getCoefficient(0));

    node.addTerm(10, 4);
    assertEquals(4, node.getDegree());
    assertEquals(10, node.getCoefficient(4));

    node.addTerm(-10, 4);
    assertEquals(0, node.getDegree());
    assertEquals(0, node.getCoefficient(0));

    s = "+10x^4 -5x^3 -10x^2 +3x^1 +50";
    node = new PolynomialImpl(s);
    assertEquals(4, node.getDegree());
    assertEquals(10, node.getCoefficient(4));
  }

  @Test
  public void testDerivative() {
    String s = "+4x^3 +3x^2 +2x^1 +1";
    PolynomialImpl node = new PolynomialImpl(s);
    assertEquals("12x^2+6x^1+2", node.derivative().toString());

    s = "+50";
    node = new PolynomialImpl(s);
    assertEquals("0", node.derivative().toString());

    node = new PolynomialImpl();
    assertEquals("0", node.toString());

    node.addTerm(10, 4);
    Polynomial temp = node.derivative();
    assertEquals("40x^3", temp.toString());

    node.addTerm(-10, 4);
    assertEquals("0", node.derivative().toString());

    s = "+10x^4 -5x^3 -10x^2 +3x^1 +50";
    node = new PolynomialImpl(s);
    assertEquals("40x^3-15x^2-20x^1+3", node.derivative().toString());

    node.addTerm(10, 0);
    assertEquals("40x^3-15x^2-20x^1+3", node.derivative().toString());

    node.addTerm(-10, 4);
    assertEquals("-15x^2-20x^1+3", node.derivative().toString());

    node.addTerm(5, 3);
    assertEquals("-20x^1+3", node.derivative().toString());
  }

  @Test
  public void testEvaluate() {
    double x = 1.0;
    String s1 = "-4x^3 +6x^2 +2x^1";
    PolynomialImpl node = new PolynomialImpl(s1);
    assertEquals(4., node.evaluate(x), 0.001);

    x = .0;
    assertEquals(0., node.evaluate(x), 0.001);
  }

  @Test
  public void testEquals() {
    PolynomialImpl node1 = new PolynomialImpl("0");
    PolynomialImpl node2 = new PolynomialImpl();
    assertEquals(node1, node2);

    String s = "+4x^3 +3x^2 +2x^1 +1";
    node1 = new PolynomialImpl(s);

    s = "+4x^3 +3x^2 +2x^1 +2";
    node2 = new PolynomialImpl(s);
    assertNotEquals(node1, node2);

    node2.addTerm(-1, 0);
    assertEquals(node1, node2);
  }
}
