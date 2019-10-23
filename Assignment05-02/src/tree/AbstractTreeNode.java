package tree;

import java.util.function.Function;

public abstract class AbstractTreeNode<T> implements TreeNode<T> {

    protected T data;

    public AbstractTreeNode(T data) {
        this.data = data;
    }

    @Override
    public TreeNode<T> getLeftChild() {
        return null;
    }

    @Override
    public TreeNode<T> getRightChild() {
        return null;
    }

    @Override
    public <R> TreeNode<R> map(Function<T, R> transform) {
        return null;
    }

    @Override
    public <R> R reduce(TriFunction<T, R, R, R> combiner) {
        return null;
    }

    @Override
    public T getData() {
        return this.data;
    }
}
