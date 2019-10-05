package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import polynomial.Polynomial;
import polynomial.PolynomialImpl;

public class PolynomialTest {

  @Test(expected = IllegalArgumentException.class)
  public void testTermArgument() {
    // note test the invalid argument for polynomial impl
    String s = "3x^-4";
    Polynomial polynomial = new PolynomialImpl(s);
    polynomial = new PolynomialImpl("0");
    assertEquals("0", polynomial.toString());
    assertNull(polynomial);
  }

  @Test
  public void TestEmptyPolynomial() {
    PolynomialImpl emptyPolynomial = new PolynomialImpl();
    assertEquals("0", emptyPolynomial.toString());
  }

  @Test
  public void testCreatePolynomial() {
    String s = "+3x^4 -2x^5 -5 -2x^4 +11x^1";
    PolynomialImpl polynomial = new PolynomialImpl(s);
    assertEquals("-2x^5+1x^4+11x^1-5", polynomial.toString());

    s = "4x^3 +3x^1 -5";
    polynomial = new PolynomialImpl(s);
    assertEquals("4x^3+3x^1-5", polynomial.toString());

    s = "102";
    polynomial = new PolynomialImpl(s);
    assertEquals("102", polynomial.toString());

    s = "+3x^4 -2x^5 -5 -2x^4 +11x^1";
    polynomial = new PolynomialImpl(s);
    assertEquals("-2x^5+1x^4+11x^1-5", polynomial.toString());
  }

  @Test
  public void testAddTerm() {
    PolynomialImpl emptyPolynomial = new PolynomialImpl();
    assertEquals("0", emptyPolynomial.toString());

    emptyPolynomial.addTerm(10, 1);
    assertEquals("10x^1", emptyPolynomial.toString());

    emptyPolynomial.addTerm(10, 2);
    assertEquals("10x^2+10x^1", emptyPolynomial.toString());

    emptyPolynomial.addTerm(100, 0);
    assertEquals("10x^2+10x^1+100", emptyPolynomial.toString());

    emptyPolynomial.addTerm(-10, 1);
    assertEquals("10x^2+100", emptyPolynomial.toString());

  }

  @Test
  public void testAll() {
    testCreatePolynomial();
  }

  @Test
  public void testAdd() {
    String s1 = "100";
    String s2 = "102";
    Polynomial polynomial1 = new PolynomialImpl(s1);
    Polynomial polynomial2 = new PolynomialImpl(s2);
    Polynomial polynomial3 = polynomial1.add(polynomial2);
    assertEquals("202", polynomial3.toString());

    polynomial1 = new PolynomialImpl();
    polynomial3 = polynomial1.add(polynomial2);
    assertEquals("102", polynomial3.toString());

    String s = "+3x^4 -2x^5 -5 -2x^4 +11x^1";
    polynomial1 = new PolynomialImpl(s);

    s = "4x^3 +3x^1 -5";
    polynomial2 = new PolynomialImpl(s);
    polynomial3 = polynomial1.add(polynomial2);
    assertEquals("-2x^5+1x^4+4x^3+14x^1-10", polynomial3.toString());
  }

  @Test
  public void testDerivative() {
    String s = "+3x^4 -2x^5 -5 -2x^4 +11x^1";
    PolynomialImpl polynomial = new PolynomialImpl(s);
    assertEquals("-10x^4+4x^3+11", polynomial.derivative().toString());
  }

  @Test
  public void testMultiply() {
    String s1 = "+2x^1 +3";
    String s2 = "+3";  //+2x^3 +1x^1
    Polynomial polynomial1 = new PolynomialImpl(s1);
    Polynomial polynomial2 = new PolynomialImpl(s2);
    Polynomial polynomial3 = polynomial1.multiply(polynomial2);
    assertEquals("6x^1+9", polynomial3.toString());

    s1 = "+2x^1 +3";
    s2 = "+0";  //+2x^3 +1x^1
    polynomial1 = new PolynomialImpl(s1);
    polynomial2 = new PolynomialImpl(s2);
    polynomial3 = polynomial1.multiply(polynomial2);
    assertEquals("0", polynomial3.toString());
  }

  @Test
  public void debug() {
    String s = "+3x^1 -5";
    PolynomialImpl polynomial = new PolynomialImpl(s);
    PolynomialImpl polynomial2 = polynomial.multiplyTerm(10, 3);
    assertEquals("30x^4-50x^3", polynomial2.toString());
  }
}
