package polynomial;

import java.util.Scanner;

/**
 * Implementation of Polynomial Interface.
 */
public class PolynomialImpl implements Polynomial {

  private static class Term {
    Term next;
    int coefficient;
    int power;

    Term(int coefficient, int power) {
      if (power < 0) {
        throw new IllegalArgumentException("Power cannot be negative!");
      }
      this.coefficient = coefficient;
      this.power = power;
    }

    @Override
    public String toString() {
      String str = ((coefficient > 0) ? "+" : "-")
              + Math.abs(coefficient) + (power > 0 ? ("x^" + power) : "");
      if (next == null) {
        return str;
      } else {
        return next.toString() + str;
      }
    }

    double evaluate(double value) {
      return coefficient * Math.pow(value, power);
    }

    @Override
    public boolean equals(Object obj) {
      if (obj instanceof Term) {
        return ((Term) obj).coefficient == coefficient && ((Term) obj).power == power;
      }
      return false;
    }

    @Override
    public int hashCode() {
      return super.hashCode();
    }

    Term derivative() {
      if (power > 0) {
        return new Term(coefficient * power, power - 1);
      } else {
        return new Term(0, 0);
      }
    }
  }

  private Term first;

  /**
   * Create a empty polynomial.
   */
  public PolynomialImpl() {
    first = new Term(0, 0);
  }

  /**
   * Create a polynomial from a polynomial string.
   *
   * @param s string
   */
  public PolynomialImpl(String s) {
    this();
    Scanner scanner = new Scanner(s);
    scanner.useDelimiter(" ");
    while (scanner.hasNext()) {
      String str = scanner.next();
      int coefficient = parseCoefficient(str);
      int power = parsePower(str);
      if (power < 0) {
        throw new IllegalArgumentException("The power cannot be negative.");
      } else if (coefficient != 0) {
        this.addTerm(coefficient, power);
      }
    }
  }

  @Override
  public void addTerm(int coefficient, int power) {
    if (coefficient == 0) {
      return;
    }
    if (isEmpty()) {
      this.first = new Term(coefficient, power);
      return;
    }
    Term head = new Term(0, 0);
    head.next = first;
    Term pointer = head;
    while (pointer.next != null && pointer.next.power < power) {
      pointer = pointer.next;
    }
    if (pointer.next == null) {
      pointer.next = new Term(coefficient, power);
    } else if (pointer.next.power > power) {
      Term save = pointer.next;
      pointer.next = new Term(coefficient, power);
      pointer.next.next = save;
    } else if (pointer.next.coefficient + coefficient == 0) {
      pointer.next = pointer.next.next;
    } else {
      pointer.next.coefficient += coefficient;
    }
    this.first = head.next == null ? new Term(0, 0) : head.next;
  }

  @Override
  public int getDegree() {
    Term pointer = this.first;
    while (pointer.next != null) {
      pointer = pointer.next;
    }
    return pointer.power;
  }

  @Override
  public int getCoefficient(int power) {
    Term pointer = this.first;
    while (pointer != null && pointer.power < power) {
      pointer = pointer.next;
    }
    if (pointer == null || pointer.power > power) {
      return 0;
    } else {
      return pointer.coefficient;
    }
  }

  @Override
  public double evaluate(double argument) {
    double result = 0;
    Term pointer = this.first;
    while (pointer != null) {
      result += pointer.evaluate(argument);
      pointer = pointer.next;
    }
    return result;
  }

  @Override
  public Polynomial add(Polynomial other) {
    if (other instanceof PolynomialImpl) {
      return ((PolynomialImpl) other).internalAdd(this);
    } else {
      throw new IllegalArgumentException("The argument provided is not a polynomialImpl object.");
    }
  }

  @Override
  public Polynomial multiply(Polynomial other) {
    if (other instanceof PolynomialImpl) {
      return ((PolynomialImpl) other).internalMultiply(this);
    } else {
      throw new IllegalArgumentException("The argument should be an instance of PolynomialImpl");
    }
  }

  @Override
  public Polynomial derivative() {
    Term head = new Term(0, 0);
    Term pointerOne = head;
    Term pointerTwo = this.first;
    while (pointerTwo != null) {
      pointerOne.next = pointerTwo.derivative();
      pointerOne = pointerOne.next;
      pointerTwo = pointerTwo.next;
    }
    while (head.next != null && head.next.coefficient == 0) {
      head = head.next;
    }
    PolynomialImpl result = new PolynomialImpl();
    if (head.next != null) {
      result.first = head.next;
    }
    return result;
  }

  private Polynomial internalMultiply(PolynomialImpl other) {
    PolynomialImpl result = new PolynomialImpl();
    Term pointerOne = other.first;
    Term pointerTwo = null;
    while (pointerOne != null) {
      pointerTwo = this.first;
      while (pointerTwo != null) {
        result.addTerm(pointerOne.coefficient * pointerTwo.coefficient,
                pointerTwo.power + pointerOne.power);
        pointerTwo = pointerTwo.next;
      }
      pointerOne = pointerOne.next;
    }
    return result;
  }

  private Polynomial internalAdd(PolynomialImpl other) {
    PolynomialImpl result = this.clone();
    Term pointer = other.first;
    while (pointer != null) {
      result.addTerm(pointer.coefficient, pointer.power);
      pointer = pointer.next;
    }
    return result;
  }

  @Override
  protected PolynomialImpl clone() {
    Term pointerOne = this.first;
    Term pointerTwo = new Term(0, 0);
    Term head = pointerTwo;
    while (pointerOne != null) {
      pointerTwo.next = new Term(pointerOne.coefficient, pointerOne.power);
      pointerTwo = pointerTwo.next;
      pointerOne = pointerOne.next;
    }
    PolynomialImpl copy = new PolynomialImpl();
    copy.first = head.next;
    return copy;
  }

  @Override
  public String toString() {
    String result = null;
    if (isEmpty()) {
      return "0";
    } else {
      result = first.toString();
    }
    return result.charAt(0) == '+' ? result.substring(1) : result;
  }

  private boolean isEmpty() {
    return this.first.next == null && this.first.coefficient == 0;
  }

  private int parseCoefficient(String s) {
    int indexOfX = s.indexOf('x');
    if (indexOfX == -1) {
      return Integer.parseInt(s);
    } else {
      String sub = s.substring(0, indexOfX);
      if (sub.equals("+") || sub.equals("-")) {
        return Integer.parseInt(sub + "1");
      } else {
        return Integer.parseInt(sub);
      }
    }
  }

  private int parsePower(String s) {
    int indexOfX = s.indexOf('x');
    return indexOfX == -1 ? 0 : Integer.parseInt(s.substring(indexOfX + 2));
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof PolynomialImpl) {
      Term pointerOne = this.first;
      Term pointerTwo = ((PolynomialImpl) obj).first;
      while (pointerOne != null && pointerTwo != null) {
        if (!pointerOne.equals(pointerTwo)) {
          return false;
        }
        pointerOne = pointerOne.next;
        pointerTwo = pointerTwo.next;
      }
      return pointerOne == null && pointerTwo == null;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }
}
