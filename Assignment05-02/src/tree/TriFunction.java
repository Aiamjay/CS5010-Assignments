package tree;

/**
 * TriFunction to take in three parameters and return result by applying some function.
 */
@FunctionalInterface
public interface TriFunction<T, K, V, R> {
  /**
   * TriFunction for treenode reduce high-order function.
   *
   * @param middle parameter 1
   * @param left   parameter 2
   * @param right  parameter 3
   * @return result
   */
  R apply(T middle, K left, V right);
}