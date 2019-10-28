package tree;

/**
 * TriFunction to take in three parameters and return result by applying some function.
 */
@FunctionalInterface
public interface TriFunction<T, K, V, R> {
  /**
   *  TriFunction for treenode reduce high-order function.
   * @param arg1 parameter 1
   * @param arg2 parameter 2
   * @param arg3 parameter 3
   * @return
   */
  R apply(T arg1, K arg2, V arg3);
}