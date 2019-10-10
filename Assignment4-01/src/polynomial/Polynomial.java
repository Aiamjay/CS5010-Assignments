package polynomial;

public interface Polynomial {

  /**
   * Add a term to polynomial.
   *
   * @param coefficient coefficient for the term being added.
   * @param power       power
   */
  default void addTerm(int coefficient, int power) {

  }

  /**
   * Get the degree of the polynomial.
   *
   * @return the degree of polynomial
   */
  int getDegree();

  /**
   * Get the coefficient of the polynomial.
   *
   * @return the coefficient of the highest term
   */
  int getCoefficient(int power);

  /**
   * Using the provided argument to evaluate the polynomial.
   *
   * @param argument argument for polynomial.
   * @return the result of the polynomial
   */
  double evaluate(double argument);

  /**
   * Add a polynomial to the current polynomial, and return a new polynomial.
   *
   * @param other the polynomial being added
   * @return the result polynomial after adding.
   */
  Polynomial add(Polynomial other);

  /**
   * Multiply given polynomial to current polynomial, and return a new polynomial. Return null only
   * if there is no polynomial.
   *
   * @param other the polynomial being multiplied.
   * @return the result.
   */
  default Polynomial multiply(Polynomial other) {
    return null;
  }

  /**
   * Get the derivative polynomial of the current polynomial. Return a new polynomial.
   *
   * @return the result of derivative.
   */
  default Polynomial derivative() {
    return null;
  }
}
