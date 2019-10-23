package tree;

import java.util.function.Function;

public interface TreeNode<T> {
    /**
     * Get the left child of current node.
     *
     * @return left child
     */
    TreeNode<T> getLeftChild();

    /**
     * Get the right child of current node.
     *
     * @return right child
     */
    TreeNode<T> getRightChild();

    <R> TreeNode<R> map(Function<T, R> transform);

    <R> R reduce(TriFunction<T, R, R, R> combiner);

    T getData();
}
