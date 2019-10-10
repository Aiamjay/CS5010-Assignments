package polynomial;

import com.sun.istack.internal.Nullable;

import java.util.Scanner;

import polynomial.node.Term;

public class PolynomialImpl implements Polynomial {
  private Term term;
  private Polynomial rest;
  private final static EndNode END_NODE;

  static {
    END_NODE = new EndNode();
  }

  /**
   * Constructor to create a polynomial with no term.
   */
  public PolynomialImpl() {
    this.term = new Term(0, 0);
    this.rest = END_NODE;
  }

  public PolynomialImpl(Term term, Polynomial rest) {
    this.term = term;
    this.rest = rest;
  }

  private PolynomialImpl(int coefficient, int power) {
    this.term = new Term(coefficient, power);
    this.rest = END_NODE;
  }

  @Override
  public String toString() {
    if (this.rest == END_NODE) {
      return this.term.toString();
    } else {
      String rest = this.rest.toString();
      if (rest.equals("0")) {
        return this.term.toString();
      }
      return this.term.toString()
              + (rest.charAt(0) == '-' ? "" : rest.charAt(0) == '+' ? "" : "+") + rest;
    }
  }

  public PolynomialImpl(@Nullable String s) {
    this();
    Scanner scanner = new Scanner(s);
    scanner.useDelimiter(" ");
    String temp;
    while (scanner.hasNext()) {
      temp = scanner.next();
      this.addTerm(parseCoefficient(temp), parsePower(temp));
    }
  }

  private int parseCoefficient(String s) {
    int index_of_x = s.indexOf('x');
    return Integer.parseInt(index_of_x == -1 ? s : s.substring(0, index_of_x));
  }

  private int parsePower(String s) {
    int index_of_x = s.indexOf('x');
    return index_of_x == -1 ? 0 : Integer.parseInt(s.substring(index_of_x + 2));
  }

  @Override
  public void addTerm(int coefficient, int power) {
    if (power > this.term.getPower()) {
      Term origin = this.term;
      this.term = new Term(coefficient, power);
      coefficient = origin.getCoefficient();
      power = origin.getPower();
    } else if (this.term.getPower() == power) {
      this.term = new Term(this.term.getCoefficient() + coefficient, power);
      return;
    }
    if (this.rest == END_NODE) {
      this.rest = new PolynomialImpl(coefficient, power);
    } else {
      this.rest.addTerm(coefficient, power);
    }
  }

  @Override
  public int getDegree() {
    return this.term.getPower();
  }

  @Override
  public int getCoefficient(int power) {
    if (this.term.getPower() < power) {
      return 0;
    } else if (this.term.getPower() == power) {
      return this.term.getCoefficient();
    } else {
      return this.rest.getCoefficient(power);
    }
  }

  @Override
  public double evaluate(double argument) {
    return this.term.evaluate(argument) + this.rest.evaluate(argument);
  }

  @Override
  public Polynomial add(Polynomial other) {
    if (other instanceof PolynomialImpl) {
      return this.add((PolynomialImpl) other);
    } else {
      throw new IllegalArgumentException("Other Polynomial should also be a instance of PolynomialImpl");
    }
  }

  public PolynomialImpl multiplyTerm(int coefficient, int power) {
    if (this.rest == END_NODE) {
      return new PolynomialImpl(
              new Term(this.term.getCoefficient() * coefficient,
                      this.term.getPower() + power),
              END_NODE);
    } else {
      Term newTerm = new Term(this.term.getCoefficient() * coefficient,
              this.term.getPower() + power);
      Polynomial rest = ((PolynomialImpl) this.rest).multiplyTerm(coefficient, power);
      return new PolynomialImpl(newTerm, rest);
    }
  }

  private Polynomial multiply(PolynomialImpl other) {
    if (other.rest == END_NODE) {
      return this.multiplyTerm(other.term.getCoefficient(), other.term.getPower());
    } else {
      return this.multiplyTerm(other.term.getCoefficient(), other.term.getPower())
              .add(this.multiply(other.rest));
    }
  }

  @Override
  public Polynomial multiply(Polynomial other) {
    if (other instanceof PolynomialImpl) {
      return this.multiply((PolynomialImpl) other);
    } else {
      throw new IllegalArgumentException("Must be PolynomialImpl");
    }
  }

  private Polynomial add(PolynomialImpl other) {
    if (this.getDegree() > other.getDegree()) {
      return new PolynomialImpl(this.term, this.rest.add(other));
    } else if (this.getDegree() == other.getDegree()) {
      return new PolynomialImpl(this.term.add(other.term), this.rest.add(other.rest));
    } else {
      return new PolynomialImpl(other.term, this.add(other.rest));
    }
  }

  @Override
  public boolean equals(Object obj) {
    return this.toString().equals(obj.toString());
  }

  @Override
  public Polynomial derivative() {
    if (this.rest == END_NODE) {
      return new PolynomialImpl(this.term.derivative(), END_NODE);
    } else {
      return new PolynomialImpl(this.term.derivative(), this.rest.derivative());
    }
  }
}
