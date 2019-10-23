package tree;

import java.util.function.Function;

public class Operand<T> extends AbstractTreeNode<T> {
    public Operand(T data) {
        super(data);
    }

    @Override
    public boolean isLeaf() {
        return true;
    }

    @Override
    public <R> TreeNode<R> map(Function<T, R> transform) {
        return new Operand<>(transform.apply(this.data));
    }

    @Override
    public <R> R reduce(TriFunction<T, R, R, R> combiner) {
        return (R) this.data;
    }
}
