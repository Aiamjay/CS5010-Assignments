package polynomial.node;


public class Term {
  private int coefficient;
  private int power = 0;

  public Term(int coefficient, int power) {
    if (power < 0) {
      throw new IllegalArgumentException("The power for polynomial term cannot be negative.");
    }

    this.coefficient = coefficient;
    this.power = this.coefficient == 0 ? 0 : power;
  }

  /**
   * Get the coefficient for this term.
   *
   * @return coefficient
   */
  public int getCoefficient() {
    return coefficient;
  }

  /**
   * Get the power of the term.
   *
   * @return power
   */
  public int getPower() {
    return power;
  }

  public boolean before(Term term) {
    return this.power > term.power;
  }

  @Override
  public String toString() {
    if (coefficient == 0 && power == 0) {
      return "0";
    } else if (coefficient == 0) {
      return "";
    } else if (power == 0) {
      return String.format("%d", coefficient);
    } else {
      return coefficient
              + "x^"
              + power;
    }
  }

  /**
   * Return the evaluate value given argument.
   *
   * @param value argument for term
   * @return value
   */
  public double evaluate(double value) {
    return Math.pow(value, power) * coefficient;
  }

  /**
   * Add two term.
   *
   * @param other another term to be added.
   * @return the term after adding.
   */
  public Term add(Term other) {
    return new Term(this.coefficient + other.coefficient, this.power);
  }

  /**
   * Get the derivative of the term.
   * @return derivative;
   */
  public Term derivative() {
    if (power > 0) {
      return new Term(coefficient * power, power - 1);
    } else {
      return new Term(0, 0);
    }

  }
}
