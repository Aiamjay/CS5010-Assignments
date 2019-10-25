package tree;

/**
 * TriFunction to take in three parameters and return result by applying some function.
 */
@FunctionalInterface
public interface TriFunction<T, K, V, R> {
  R apply(T arg1, K arg2, V arg3);
}