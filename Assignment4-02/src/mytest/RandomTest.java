package mytest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Random;

import polynomial.PolynomialImpl;

public class RandomTest {

  @Test
  public void randomTestAdd() {
    Random random = new Random();
    RandomPolynomial test;
    for (int i = 0; i < random.nextInt(10); i++) {
      test = new RandomPolynomial(random.nextInt(15), 1000);
      assertTrue(test.testEqual());
    }
  }

  @Test
  public void randomMultiply() {
    Random random = new Random();
    RandomPolynomial test;
    for (int i = 0; i < random.nextInt(20); i++) {
      test = new RandomPolynomial(random.nextInt(10), 10, 1);
      test.testMultiply();
    }
  }
}

class RandomPolynomial {
  Polynomial polynomial1;
  PolynomialImpl polynomial2;
  Polynomial polynomial3;
  PolynomialImpl polynomial4;

  public RandomPolynomial(Polynomial polynomial1, PolynomialImpl polynomial2) {
    this.polynomial1 = polynomial1;
    this.polynomial2 = polynomial2;
  }

  public RandomPolynomial(int length, int range) {
    Random random = new Random();
    Polynomial polynomial1 = null;
    StringBuilder str = new StringBuilder();
    for (int index = 0; index < length; index++) {
      int sign = random.nextInt(10);
      int coefficient = random.nextInt(range) * (sign % 2 == 0 ? 1 : -1);
      int power = Math.abs(random.nextInt(range));
      str.append(createStr(coefficient, power));
      if (polynomial1 == null) {
        polynomial1 = new Polynomial(coefficient, power);
      } else {
        Polynomial temp = new Polynomial(coefficient, power);
        polynomial1 = polynomial1.plus(temp);
      }
    }
    this.polynomial1 = polynomial1;
    this.polynomial2 = new PolynomialImpl(str.toString());
  }

  public RandomPolynomial(int length, int range, int type) {
    this(length, range);

    Random random = new Random();
    Polynomial polynomial3 = null;
    StringBuilder str = new StringBuilder();
    for (int index = 0; index < length; index++) {
      int sign = random.nextInt(10);
      int coefficient = random.nextInt(range) * (sign % 2 == 0 ? 1 : -1);
      int power = Math.abs(random.nextInt(range));
      str.append(createStr(coefficient, power));
      if (polynomial3 == null) {
        polynomial3 = new Polynomial(coefficient, power);
      } else {
        Polynomial temp = new Polynomial(coefficient, power);
        polynomial3 = polynomial3.plus(temp);
      }
    }
    this.polynomial3 = polynomial3;
    this.polynomial4 = new PolynomialImpl(str.toString());
  }

  private static String createStr(int coefficient, int power) {
    if (power == 0) {
      return String.valueOf(coefficient) + " ";
    } else {
      return String.valueOf(coefficient) + "x^" + String.valueOf(power) + " ";
    }
  }

  public boolean testEqual() {
    System.out.println(polynomial1.toString());
    System.out.println(polynomial2.toString());
    return polynomial1.toString().equals(polynomial2.toString());
  }

  public void testMultiply() {
    System.out.println("first: " + polynomial1.toString());
    System.out.println("second: " + polynomial2.toString());
    assert polynomial1.toString().equals(polynomial2.toString());

    System.out.println("first: " + polynomial3.toString());
    System.out.println("second: " + polynomial4.toString());
    assert polynomial3.toString().equals(polynomial4.toString());

    Polynomial result1 = polynomial1.times(polynomial3);
    System.out.println("first: " + polynomial2.toString());
    System.out.println("second: " + polynomial4.toString());

    polynomial.Polynomial result2 = polynomial2.multiply(polynomial4);
    System.out.println("result 1: " + result1.toString());
    System.out.println("result 2: " + result2.toString());
    System.out.println(result1.toString().equals(result2.toString()));
  }
}