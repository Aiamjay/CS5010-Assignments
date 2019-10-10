package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import polynomial.Polynomial;
import polynomial.PolynomialImpl;


/**
 * This is the test class for PolynomialImpl class.
 */
public class PolynomialTest {


  Polynomial po1;
  Polynomial po2;
  Polynomial po3;

  /**
   * This is the setup function, which initialized three Polynomial objects.
   */
  @Before
  public void setup() {
    po1 = new PolynomialImpl("+3x^4 -2x^5 -5 -2x^4 +11x^1");
    po2 = new PolynomialImpl("102");
    po3 = new PolynomialImpl();
  }

  /**
   * This tests if the addTerm input for coefficient is negative.
   */
  @Test
  public void testInputPower() {
    po1.addTerm(-100, 100);
    assertEquals("-100x^100-2x^5+1x^4+11x^1-5", po1.toString());
  }

  /**
   * This tests if the input power for AddTerm, an exception should be thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInputPower1() {
    po1.addTerm(5, -2);
  }

  /**
   * This tests if add an empty term.
   */
  @Test
  public void testAddTerm() {
    po1.addTerm(0, 0);
    assertEquals("-2x^5+1x^4+11x^1-5", po1.toString());
  }

  /**
   * This tests getDegree method.
   */
  @Test
  public void testGetDegree() {
    assertEquals(5, po1.getDegree());
  }

  /**
   * This tests if an empty poly invokes the getDegree, should return 0.
   */
  @Test
  public void testGet0Degree() {
    assertEquals(0, po3.getDegree());
  }

  /**
   * This tests if a poly is just a constant number, when it invokes getDegree, should return 0.
   */
  @Test
  public void testGetConstantDegree() {
    PolynomialImpl test = new PolynomialImpl("1");
    assertEquals(0, test.getDegree());
  }

  /**
   * This tests normal getDegree method.
   */
  @Test
  public void testGetDegree1() {
    PolynomialImpl test = new PolynomialImpl("1x^3");
    assertEquals(3, test.getDegree());
  }

  /**
   * This tests if there is no such power when invoking getCoefficient, should throw an exception.
   */
  @Test
  public void testNoGetCoe() {
    assertEquals(0, po2.getCoefficient(1), 0.00001);
    //assertEquals("No such power term in the Polynomial", po2.getCoefficient(1));
  }

  /**
   * This tests if there is no such power when invoking getCoefficient, should throw an exception.
   */
  @Test
  public void testNoGetCoe1() {
    PolynomialImpl test = new PolynomialImpl("+3x^4 -2x^5 -5 -2x^8 +11x^12");
    assertEquals(0, test.getCoefficient(1), 0.0001);
    //assertEquals("No such power term in the Polynomial", test.getCoefficient(1));
  }

  /**
   * This tests get the constant's coefficient, should return itself.
   */
  @Test
  public void testGetCoe() {
    assertEquals(102, po2.getCoefficient(0));
  }

  /**
   * This tests get normal poly's coefficient.
   */
  @Test
  public void testGetCoe1() {
    assertEquals(-2, po1.getCoefficient(5));
  }

  /**
   * This tests get normal poly's coefficient.
   */
  @Test
  public void testGetCoe2() {
    assertEquals(1, po1.getCoefficient(4));
  }

  /**
   * This tests evaluate method.
   */
  @Test
  public void testEva() {
    assertEquals(5, po1.evaluate(1), 0.0001);
  }

  /**
   * This tests evaluate method.
   */
  @Test
  public void testEva1() {
    assertEquals(102, po2.evaluate(1000000), 0.0001);
  }

  /**
   * This tests Add method.
   */
  @Test
  public void testAdd() {
    assertEquals("-2x^5+1x^4+11x^1+97", po1.add(po2).toString());
  }

  /**
   * This tests when doing a lot of methods of add or multiple, the object should be immutable.
   */
  @Test
  public void testInmutable() {
    assertEquals("-2x^5+1x^4+11x^1-5", po1.toString());
  }

  /**
   * This tests add two poly.
   */
  @Test
  public void testAddGetNum() {
    PolynomialImpl test = new PolynomialImpl("1x^1 -2x^2 +3x^3 -100 +12x^7");
    assertEquals("12x^7-2x^5+1x^4+3x^3-2x^2+12x^1-105", po1.add(test).toString());
  }

  /**
   * This tests add an empty poly and a normal one.
   */
  @Test
  public void testAddEmp() {
    assertEquals("-2x^5+1x^4+11x^1-5", po1.add(po3).toString());
  }

  /**
   * This tests multiple an empty poly, should get an empty one.
   */
  @Test
  public void testMulEmp() {
    assertEquals("0", po1.multiply(po3).toString());
  }

  /**
   * This tests multiple 2 poly.
   */
  @Test
  public void testMul() {
    //  "+3x^4 -2x^5 -5 -2x^4 +11x^1"
    PolynomialImpl test = new PolynomialImpl("4x^3 +3x^1 -5");
    assertEquals("-8x^8+4x^7-6x^6+13x^5+39x^4-20x^3+33x^2-70x^1+25", po1.multiply(test).toString());
  }

  /**
   * This tests multiple 2 poly, using reverse position of testMul method.
   */
  @Test
  public void testMul1() {
    PolynomialImpl test = new PolynomialImpl("4x^3 +3x^1 -5");
    assertEquals("-8x^8+4x^7-6x^6+13x^5+39x^4-20x^3+33x^2-70x^1+25", test.multiply(po1).toString());
  }

  /**
   * this tests after multiple, invoke evaluate method.
   */
  @Test
  public void testMulEva() {
    PolynomialImpl test = new PolynomialImpl("4x^3 +3x^1 -5");
    assertEquals(-8693.75, test.multiply(po1).evaluate(2.5), 0.00001);
  }

  /**
   * This tests after the multiple method, any object should be immutable.
   */
  @Test
  public void testIm() {
    assertEquals("-2x^5+1x^4+11x^1-5", po1.toString());
  }

  /**
   * This tests the derivative method.
   */
  @Test
  public void testDer() {
    assertEquals("-10x^4+4x^3+11", po1.derivative().toString());
  }

  /**
   * This tests if an empty poly derivative, should return 0.
   */
  @Test
  public void testEmpDer() {
    assertEquals("0", po3.derivative().toString());
  }

  /**
   * This tests if an constant poly derivative, should return 0.
   */
  @Test
  public void testConstDer() {
    assertEquals("0", po2.derivative().toString());
  }

  /**
   * This tests normal toString.
   */
  @Test
  public void testToString() {
    PolynomialImpl test = new PolynomialImpl("1x^3 -10x^2 +3x^3 -4x^3 +5 -10 +2 +3 +6x^1");
    assertEquals("-10x^2+6x^1", test.toString());
  }

  /**
   * This tests if there are terms coefficient is 0, should not show them.
   */
  @Test
  public void testToString1() {
    PolynomialImpl test = new PolynomialImpl("-10x^2 +3x^3 -4x^3 +5 +10x^2 +2 +3 +6x^1");
    assertEquals("-1x^3+6x^1+10", test.toString());
  }

  /**
   * This tests if the poly's first term's co is 0, should not show it.
   */
  @Test
  public void testToStringHeadzero() {
    PolynomialImpl test = new PolynomialImpl("-10x^2 +4x^3 -4x^3 +5 +10x^2 +2 +3 +6x^1 -6x^1");
    assertEquals("10", test.toString());
  }

  /**
   * This tests if the poly's term combined and all co is 0, should return 0.
   */
  @Test
  public void testToStringAllZero() {
    PolynomialImpl test = new PolynomialImpl("-10x^2 +4x^3 -4x^3 +5 +10x^2 +2 +3 +6x^1 -6x^1 -10");
    assertEquals("0", test.toString());
  }

  /**
   * This tests if the poly is an empty one, toString return 0.
   */
  @Test
  public void testEmptToString() {
    assertEquals("0", po3.toString());
  }

  @Test
  public void testEqual() {
    PolynomialImpl test = new PolynomialImpl("-10x^2 +4x^3 -4x^3 +5 +10x^2 +2 +3 +6x^1 -6x^1 -10");
    assertTrue(test.equals((PolynomialImpl) po3));
  }

//  /**
//   * This tests if two poly are same.
//   */
//  @Test
//  public void testEqual() {
//    PolynomialImpl test = new PolynomialImpl("-10x^2 +4x^3 -4x^3 +5 +10x^2 +2 +3 +6x^1 -6x^1 -10");
//    PolynomialImpl test1 = (PolynomialImpl) po3;
//    assertTrue(test.compare(test, test1));
//  }
//
//  /**
//   * This tests if two poly are same.
//   */
//  @Test
//  public void testEqual1() {
//    PolynomialImpl test = new PolynomialImpl("-2x^5 +1x^4 +11x^1 -5");
//
//    assertTrue(test.compare(test, (PolynomialImpl) po1));
//  }

  @Test
  public void tesBigM() {
    PolynomialImpl test = new PolynomialImpl("-10x^2 +4x^3 -4x^3 +5 +10x^2 +2 +3 +6x^1 -6x^1 -10 -10x^2 +4x^3 -4x^3 +5 +10x^2 +2 +3 +6x^1 -6x^1 -10 -10x^2 +4x^3 -4x^3 +5 +10x^2 +2 +3 +6x^1 -6x^1 -10");
    PolynomialImpl test1 = new PolynomialImpl("-10x^2 +4x^3 -4x^3 +5 +10x^2 +2 +3 +6x^1 -6x^1 -10 -10x^2 +4x^3 -4x^3 +5 +10x^2 +2 +3 +6x^1 -6x^1 -10 -10x^2 +4x^3 -4x^3 +5 +10x^2 +2 +3 +6x^1 -6x^1 -10");
    test.multiply(test1);
  }

  @Test
  public void testADDtal() {
    PolynomialImpl test = new PolynomialImpl("0");
    System.out.println(test.toString());
    System.out.println(po3.toString());
    System.out.println(test.equals((PolynomialImpl) po3));
  }

  @Test
  public void mix() {

    PolynomialImpl a = new PolynomialImpl("-10x^2 +4x^3 -4x^3 +5");
    PolynomialImpl test = (PolynomialImpl) po1.add(a);

    assertEquals("-2x^5+1x^4-10x^2+11x^1", test.toString());

    PolynomialImpl b = (PolynomialImpl) test.multiply(po1);
    assertEquals("4x^10-4x^9+1x^8+20x^7-54x^6+32x^5-5x^4-110x^3+171x^2-55x^1", b.toString());

    PolynomialImpl c = (PolynomialImpl) b.derivative();

    assertEquals("40x^9-36x^8+8x^7+140x^6-324x^5+160x^4-20x^3-330x^2+342x^1-55", c.toString());
  }


}
