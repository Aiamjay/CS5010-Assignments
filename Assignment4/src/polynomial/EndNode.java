package polynomial;

public class EndNode implements Polynomial {

  @Override
  public int getDegree() {
    return 0;
  }

  @Override
  public int getCoefficient(int power) {
    return 0;
  }

  @Override
  public double evaluate(double argument) {
    return 0;
  }

  @Override
  public Polynomial add(Polynomial other) {
    return other;
  }

  @Override
  public String toString() {
    return "";
  }
}