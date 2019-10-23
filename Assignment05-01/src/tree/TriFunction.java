package tree;

@FunctionalInterface
public interface TriFunction<T, K, V, R> {
    R apply(T arg1, K arg2, V arg3);
}